package com.example.buildinfo;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根界面：android.os 包的全部顶层类列表。
 * 点击任意类进入其详情（静态字段 + 嵌套类，可继续逐级深入）。
 */
public class MainActivity extends AppCompatActivity {

    private static final String PREFS = "main_filter";
    private static final String KEY_HIDE_EMPTY = "hide_empty";

    /** GitHub 仓库主页 */
    private static final String GITHUB_URL = "https://github.com/kagurazakayashi/android_sysinfo";

    /** 语言偏好键（与 LocaleManager 一致） */
    private static final String PREFS_LOCALE = "locale_prefs";
    private static final String KEY_LOCALE = "locale";

    /** 类条目数据：用于开关过滤 */
    private static class Entry {
        final String className;
        final int fieldCount;
        final int nestedCount;
        final View view;
        Entry(String className, int fieldCount, int nestedCount, View view) {
            this.className = className;
            this.fieldCount = fieldCount;
            this.nestedCount = nestedCount;
            this.view = view;
        }
        /** 是否为空项（无静态字段且无嵌套类）；无法加载的类不视为空 */
        boolean isEmpty() {
            return fieldCount >= 0 && fieldCount == 0 && nestedCount == 0;
        }
    }

    private LinearLayout mContainer;
    private final List<Entry> mEntries = new ArrayList<>();
    private boolean mHideEmpty;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 首次启动预置默认收藏（制造商/型号/Android版本号/API版本号）
        FavoritesStore.ensureDefaults(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("android.os");
        toolbar.setSubtitle(getString(R.string.loading));
        // 主界面导航按钮：关闭叉（✕），点击退出应用
        toolbar.setNavigationIcon(R.drawable.ic_close);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 语言切换后重建界面（让所有 TextView 重新加载资源）
        LocaleManager.apply(this);

        mContainer = findViewById(R.id.container);
        mHideEmpty = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getBoolean(KEY_HIDE_EMPTY, false);

        // 后台线程逐个统计各类的字段数 / 嵌套类数（避免主线程卡顿）
        final AtomicInteger done = new AtomicInteger(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String[] names = OsClasses.TOP_LEVEL;
                final int[] fieldCounts = new int[names.length];
                final int[] nestedCounts = new int[names.length];
                for (int i = 0; i < names.length; i++) {
                    Class<?> cls = OsInfo.loadClass(names[i]);
                    if (cls != null) {
                        fieldCounts[i] = OsInfo.countStaticFields(cls);
                        nestedCounts[i] = cls.getClasses().length;
                    } else {
                        fieldCounts[i] = -1;
                    }
                    done.incrementAndGet();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mEntries.clear();
                        for (int i = 0; i < names.length; i++) {
                            View v = createClassItem(names[i], fieldCounts[i], nestedCounts[i]);
                            mContainer.addView(v);
                            mEntries.add(new Entry(names[i], fieldCounts[i], nestedCounts[i], v));
                        }
                        applyFilter();
                    }
                });
            }
        }).start();
    }

    /** 根据开关状态过滤空项 */
    private void applyFilter() {
        int visible = 0;
        for (Entry e : mEntries) {
            boolean show = !(mHideEmpty && e.isEmpty());
            e.view.setVisibility(show ? View.VISIBLE : View.GONE);
            if (show) visible++;
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle(getString(R.string.subtitle_main, visible, mEntries.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_copy_all) {
            copyAll();
            return true;
        }
        if (item.getItemId() == R.id.action_settings) {
            showSettingsDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** 复制全部类名到剪贴板 */
    private void copyAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.copy_all_header, OsClasses.TOP_LEVEL.length)).append('\n');
        for (String name : OsClasses.TOP_LEVEL) {
            String zh = ZhNames.classZh(name);
            sb.append(name);
            if (zh != null) sb.append("  ").append(zh);
            sb.append('\n');
        }
        android.content.ClipboardManager cm =
                (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cm.setPrimaryClip(android.content.ClipData.newPlainText("android.os 类列表", sb.toString()));
        Toast.makeText(this, getString(R.string.toast_copied_all_classes), Toast.LENGTH_SHORT).show();
    }

    /** 设置对话框：语言选择 + 全局开关（“仅显示含静态字段或嵌套类的项”） */
    private void showSettingsDialog() {
        // 构建对话框内容：一行“语言”条目 + 一行“隐藏空项”开关
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        int padH = dp(20);
        int padV = dp(8);
        root.setPadding(padH, dp(12), padH, 0);

        // 行 1：语言（点击打开语言选择对话框）
        LinearLayout rowLang = new LinearLayout(this);
        rowLang.setOrientation(LinearLayout.HORIZONTAL);
        rowLang.setGravity(Gravity.CENTER_VERTICAL);
        rowLang.setPadding(0, padV, 0, padV);
        rowLang.setClickable(true);
        TypedValue ripple = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.selectableItemBackground, ripple, true);
        rowLang.setForeground(getDrawable(ripple.resourceId));

        ImageView langIcon = new ImageView(this);
        langIcon.setImageResource(R.drawable.ic_language);
        langIcon.setColorFilter(Ui.color(this, R.color.primary));
        LinearLayout.LayoutParams langIconLp = new LinearLayout.LayoutParams(dp(24), dp(24));
        langIconLp.setMargins(0, 0, dp(16), 0);
        langIcon.setLayoutParams(langIconLp);

        TextView langLabel = new TextView(this);
        langLabel.setText(R.string.settings_language);
        langLabel.setTextSize(15);
        langLabel.setTextColor(Ui.color(this, R.color.text_primary));
        LinearLayout.LayoutParams langLabelLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        langLabel.setLayoutParams(langLabelLp);

        TextView langValue = new TextView(this);
        langValue.setText(currentLocaleLabel());
        langValue.setTextSize(14);
        langValue.setTextColor(Ui.color(this, R.color.text_sub));
        langValue.setPadding(dp(8), 0, 0, 0);

        rowLang.addView(langIcon);
        rowLang.addView(langLabel);
        rowLang.addView(langValue);
        rowLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });

        // 分隔线
        View divider = new View(this);
        divider.setBackgroundColor(Ui.color(this, R.color.divider));
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));

        // 行 2：全局开关
        LinearLayout rowSwitch = new LinearLayout(this);
        rowSwitch.setOrientation(LinearLayout.HORIZONTAL);
        rowSwitch.setGravity(Gravity.CENTER_VERTICAL);
        rowSwitch.setPadding(0, dp(4), 0, dp(4));

        TextView switchLabel = new TextView(this);
        switchLabel.setText(R.string.filter_label);
        switchLabel.setTextSize(14);
        switchLabel.setTextColor(Ui.color(this, R.color.text_primary));
        LinearLayout.LayoutParams switchLabelLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        switchLabelLp.setMargins(0, 0, dp(16), 0);
        switchLabel.setLayoutParams(switchLabelLp);

        MaterialSwitch sw = new MaterialSwitch(this);
        sw.setChecked(mHideEmpty);
        sw.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
                mHideEmpty = isChecked;
                getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                        .edit().putBoolean(KEY_HIDE_EMPTY, isChecked).apply();
                applyFilter();
            }
        });

        rowSwitch.addView(switchLabel);
        rowSwitch.addView(sw);

        // 分隔线 2
        View divider2 = new View(this);
        divider2.setBackgroundColor(Ui.color(this, R.color.divider));
        divider2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));

        // 行 3：关于（点击打开“关于”对话框）
        LinearLayout rowAbout = new LinearLayout(this);
        rowAbout.setOrientation(LinearLayout.HORIZONTAL);
        rowAbout.setGravity(Gravity.CENTER_VERTICAL);
        rowAbout.setPadding(0, padV, 0, padV);
        rowAbout.setClickable(true);
        rowAbout.setForeground(getDrawable(ripple.resourceId));

        ImageView aboutIcon = new ImageView(this);
        aboutIcon.setImageResource(R.drawable.ic_info);
        aboutIcon.setColorFilter(Ui.color(this, R.color.primary));
        LinearLayout.LayoutParams aboutIconLp = new LinearLayout.LayoutParams(dp(24), dp(24));
        aboutIconLp.setMargins(0, 0, dp(16), 0);
        aboutIcon.setLayoutParams(aboutIconLp);

        TextView aboutLabel = new TextView(this);
        aboutLabel.setText(R.string.settings_about);
        aboutLabel.setTextSize(15);
        aboutLabel.setTextColor(Ui.color(this, R.color.text_primary));
        LinearLayout.LayoutParams aboutLabelLp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        aboutLabel.setLayoutParams(aboutLabelLp);

        rowAbout.addView(aboutIcon);
        rowAbout.addView(aboutLabel);
        rowAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAboutDialog();
            }
        });

        root.addView(rowLang);
        root.addView(divider);
        root.addView(rowSwitch);
        root.addView(divider2);
        root.addView(rowAbout);

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.action_settings)
                .setView(root)
                .setNegativeButton(R.string.dialog_cancel, null)
                .show();
    }

    /** “关于”对话框：版本号 + 简介 + GitHub 按钮 */
    private void showAboutDialog() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        int padH = dp(24);
        root.setPadding(padH, dp(8), padH, 0);

        // 应用名 + 版本号
        TextView title = new TextView(this);
        title.setText(getString(R.string.app_name) + "\n" + getString(R.string.about_version, getVersionName()));
        title.setTextSize(18);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(Ui.color(this, R.color.text_primary));
        root.addView(title);

        // 简介
        TextView summary = new TextView(this);
        summary.setText(R.string.about_summary);
        summary.setTextSize(14);
        summary.setTextColor(Ui.color(this, R.color.text_sub));
        LinearLayout.LayoutParams summaryLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        summaryLp.setMargins(0, dp(8), 0, dp(20));
        summary.setLayoutParams(summaryLp);
        root.addView(summary);

        // GitHub 按钮
        MaterialButton githubBtn = new MaterialButton(this);
        githubBtn.setText(R.string.about_github);
        githubBtn.setIconResource(R.drawable.ic_github);
        githubBtn.setIconGravity(MaterialButton.ICON_GRAVITY_START);
        githubBtn.setIconTint(ColorStateList.valueOf(Color.WHITE));
        githubBtn.setBackgroundColor(Ui.color(this, R.color.primary));
        githubBtn.setCornerRadius(dp(20));
        githubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGitHub();
            }
        });
        root.addView(githubBtn);

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.settings_about)
                .setView(root)
                .setNegativeButton(R.string.dialog_cancel, null)
                .show();
    }

    /** 读取构建版本号 */
    private String getVersionName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /** 在浏览器中打开 GitHub 仓库 */
    private void openGitHub() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.toast_no_browser, Toast.LENGTH_SHORT).show();
        }
    }

    /** 当前语言的显示名称 */
    private String currentLocaleLabel() {
        final String current = getSharedPreferences(PREFS_LOCALE, Context.MODE_PRIVATE)
                .getString(KEY_LOCALE, "");
        if (current == null || current.isEmpty()) return getString(R.string.lang_system);
        if (current.startsWith("zh-rCN")) return getString(R.string.lang_zh_cn);
        if (current.startsWith("zh-rTW")) return getString(R.string.lang_zh_tw);
        if (current.startsWith("en")) return getString(R.string.lang_en);
        if (current.startsWith("ja")) return getString(R.string.lang_ja);
        return getString(R.string.lang_system);
    }

    /** 语言切换对话框（在设置菜单中点击“语言”时弹出） */
    private void showLanguageDialog() {
        final String current = getSharedPreferences(PREFS_LOCALE, Context.MODE_PRIVATE)
                .getString(KEY_LOCALE, "");
        final String[] labels = {
                getString(R.string.lang_system),
                getString(R.string.lang_zh_cn),
                getString(R.string.lang_zh_tw),
                getString(R.string.lang_en),
                getString(R.string.lang_ja)
        };
        final String[] values = {"", "zh-rCN", "zh-rTW", "en", "ja"};

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_language)
                .setSingleChoiceItems(labels, indexOf(values, current), new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (values[which].equals(current)) return;
                        LocaleManager.setLocale(MainActivity.this, values[which]);
                        // 重建当前界面（语言切换后刷新所有文案）
                        recreate();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null)
                .show();
    }

    private static int indexOf(String[] arr, String v) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(v)) return i;
        }
        return 0;
    }

    /** 生成一个类条目（可点击进入详情） */
    private View createClassItem(final String className, int fieldCount, int nestedCount) {
        String shortName = className.substring(className.lastIndexOf('.') + 1);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(14), dp(10), dp(14), dp(10));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(4), 0, dp(4));
        card.setLayoutParams(lp);
        card.setBackground(Ui.roundRectBg(this, R.color.card_bg, 12));
        card.setElevation(dp(1));

        TypedValue ripple = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.selectableItemBackground, ripple, true);
        card.setForeground(getDrawable(ripple.resourceId));
        card.setClickable(true);

        // 左侧：图标（圆形背景 + 主色 Material 图标）
        ImageView icon = new ImageView(this);
        String iconName = Icons.iconName(className);
        icon.setImageResource(getResources().getIdentifier(iconName, "drawable", getPackageName()));
        icon.setBackground(Ui.ovalBg(this, R.color.icon_bg));
        icon.setColorFilter(Ui.color(this, R.color.primary));
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(dp(40), dp(40));
        iconLp.setMargins(0, 0, dp(12), 0);
        icon.setLayoutParams(iconLp);

        // 中部：标题 + 副标题
        LinearLayout left = new LinearLayout(this);
        left.setOrientation(LinearLayout.VERTICAL);
        left.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView title = new TextView(this);
        title.setText(buildTitle(shortName, ZhNames.classZh(className)));
        title.setTextSize(16);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(Ui.color(this, R.color.text_primary));

        TextView sub = new TextView(this);
        if (fieldCount >= 0) {
            sub.setText(className + "  ·  " + getString(R.string.copy_all_footer, fieldCount, nestedCount));
        } else {
            sub.setText(className + "  ·  " + getString(R.string.cannot_load));
        }
        sub.setTextSize(12);
        sub.setTypeface(Typeface.MONOSPACE);
        sub.setTextColor(Ui.color(this, R.color.text_sub));
        sub.setPadding(0, dp(3), 0, 0);

        left.addView(title);
        left.addView(sub);

        // 右侧：进入箭头
        ImageView arrow = new ImageView(this);
        arrow.setImageResource(R.drawable.ic_chevron_right);
        arrow.setColorFilter(Ui.color(this, R.color.text_sub));
        LinearLayout.LayoutParams arrowLp = new LinearLayout.LayoutParams(dp(24), dp(24));
        arrowLp.setMargins(dp(8), 0, 0, 0);
        arrow.setLayoutParams(arrowLp);

        card.addView(icon);
        card.addView(left);
        card.addView(arrow);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClassDetailActivity.class);
                intent.putExtra(ClassDetailActivity.EXTRA_CLASS, className);
                startActivity(intent);
            }
        });
        return card;
    }

    /** 主标题：英文名 + 中文名（中文用强调色显示） */
    private CharSequence buildTitle(String en, String zh) {
        if (zh == null || zh.isEmpty()) return en;
        String text = en + "  " + zh;
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(Ui.color(this, R.color.accent)),
                en.length() + 2, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 系统语言变化时刷新界面
        LocaleManager.apply(this);
        recreate();
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
