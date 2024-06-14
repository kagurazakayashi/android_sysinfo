package com.example.buildinfo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 类详情页：展示某个类的类型信息、全部 public 静态字段及当前值、
 * 以及嵌套类/接口列表（点击可继续深入查看）。
 */
public class ClassDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CLASS = "class_name";

    /** 全局过滤开关的偏好键（与 MainActivity 保持一致） */
    private static final String PREFS_FILTER = "main_filter";
    private static final String KEY_HIDE_EMPTY = "hide_empty";

    private String mClassName;
    private List<OsInfo.FieldInfo> mFields;
    private List<Class<?>> mNested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mClassName = getIntent().getStringExtra(EXTRA_CLASS);
        if (mClassName == null) {
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 大标题 = 当前包；小标题在字段/嵌套类统计完成后更新为“显示 X / Y 项”
        String pkgName = mClassName.contains(".") ? mClassName.substring(0, mClassName.lastIndexOf('.')) : mClassName;
        toolbar.setTitle(pkgName);
        toolbar.setSubtitle(getString(R.string.loading));
        // 系统标准返回箭头（由 AppCompat 主题自动提供，点击返回上一级）
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final LinearLayout container = findViewById(R.id.container);

        Class<?> cls = OsInfo.loadClass(mClassName);
        if (cls == null) {
            showMessage(container, getString(R.string.cannot_load_class, mClassName));
            return;
        }

        // 类型信息
        String kind = cls.isInterface() ? getString(R.string.kind_interface)
                : (cls.isEnum() ? getString(R.string.kind_enum) : getString(R.string.kind_class));
        int mods = cls.getModifiers();
        StringBuilder modText = new StringBuilder();
        if (Modifier.isPublic(mods)) modText.append("public ");
        if (Modifier.isAbstract(mods) && !cls.isInterface()) modText.append("abstract ");
        if (Modifier.isFinal(mods)) modText.append("final ");
        if (cls.isAnnotation()) kind = getString(R.string.kind_annotation);
        container.addView(sectionTitle(getString(R.string.type_info)));
        container.addView(infoRow(getString(R.string.row_kind), kind));
        container.addView(infoRow(getString(R.string.row_modifiers),
                modText.toString().trim().isEmpty() ? getString(R.string.value_none) : modText.toString().trim()));
        container.addView(infoRow(getString(R.string.row_package),
                cls.getPackage() == null ? getString(R.string.value_none) : cls.getPackage().getName()));
        Class<?> superC = cls.getSuperclass();
        container.addView(infoRow(getString(R.string.row_superclass),
                superC == null ? getString(R.string.value_none) : superC.getName()));
        Class<?>[] ifaces = cls.getInterfaces();
        container.addView(infoRow(getString(R.string.row_interfaces),
                ifaces.length == 0 ? getString(R.string.value_none_en) : joinNames(ifaces)));

        // 静态字段
        mFields = OsInfo.readStaticFields(cls);
        container.addView(sectionTitle(getString(R.string.section_fields, mFields.size())));
        if (mFields.isEmpty()) {
            container.addView(emptyHint(getString(R.string.no_public_fields)));
        } else {
            for (OsInfo.FieldInfo f : mFields) {
                container.addView(createFieldView(f));
            }
        }

        // 嵌套类
        try {
            Class<?>[] nested = cls.getClasses();
            mNested = new ArrayList<>(Arrays.asList(nested));
            Collections.sort(mNested, new Comparator<Class<?>>() {
                @Override
                public int compare(Class<?> a, Class<?> b) {
                    return a.getName().compareTo(b.getName());
                }
            });
        } catch (Throwable t) {
            mNested = new ArrayList<>();
        }

        // 全局开关：过滤“静态字段”和“嵌套类/接口”都为 0 的嵌套类条目
        final boolean hideEmpty = getSharedPreferences(PREFS_FILTER, Context.MODE_PRIVATE)
                .getBoolean(KEY_HIDE_EMPTY, false);
        List<Class<?>> shownNested = new ArrayList<>();
        if (hideEmpty) {
            for (Class<?> n : mNested) {
                if (!isEmptyEntry(n)) shownNested.add(n);
            }
        } else {
            shownNested.addAll(mNested);
        }

        // 小标题：显示 / 总计（字段 + 嵌套类）
        int totalItems = mFields.size() + mNested.size();
        int shownItems = mFields.size() + shownNested.size();
        toolbar.setSubtitle(getString(R.string.subtitle_items, shownItems, totalItems));

        container.addView(sectionTitle(getString(R.string.section_nested, shownNested.size())));
        if (shownNested.isEmpty()) {
            container.addView(emptyHint(mNested.isEmpty()
                    ? getString(R.string.no_nested)
                    : getString(R.string.all_hidden_by_filter)));
        } else {
            for (final Class<?> n : shownNested) {
                container.addView(createNestedView(n));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_copy_all) {
            copyAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** 分段标题 */
    private View sectionTitle(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(14);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextColor(Ui.color(this, R.color.accent));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(16), 0, dp(4));
        tv.setLayoutParams(lp);
        return tv;
    }

    /** 普通键值行（类型信息用） */
    private View infoRow(String key, String value) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(dp(4), dp(3), dp(4), dp(3));
        TextView k = new TextView(this);
        k.setText(key);
        k.setTextSize(13);
        k.setTextColor(Ui.color(this, R.color.text_sub));
        k.setLayoutParams(new LinearLayout.LayoutParams(dp(80), ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView v = new TextView(this);
        v.setText(value);
        v.setTextSize(13);
        v.setTypeface(Typeface.MONOSPACE);
        v.setTextColor(Ui.color(this, R.color.text_primary));
        v.setTextIsSelectable(true);
        row.addView(k);
        row.addView(v);
        return row;
    }

    /** 字段卡片：点击弹出复制菜单（带图标 + 中文标题） */
    private View createFieldView(final OsInfo.FieldInfo f) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(12), dp(8), dp(12), dp(8));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(3), 0, dp(3));
        card.setLayoutParams(lp);
        card.setBackground(Ui.roundRectBg(this, R.color.card_bg, 12));
        card.setElevation(dp(1));
        TypedValue ripple = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.selectableItemBackground, ripple, true);
        card.setForeground(getDrawable(ripple.resourceId));
        card.setClickable(true);

        // 左侧：图标（按字段名分配语义图标）
        ImageView icon = new ImageView(this);
        String iconName = Icons.fieldIcon(f.name);
        icon.setImageResource(getResources().getIdentifier(iconName, "drawable", getPackageName()));
        icon.setBackground(Ui.ovalBg(this, R.color.icon_bg));
        icon.setColorFilter(Ui.color(this, R.color.primary));
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(dp(36), dp(36));
        iconLp.setMargins(0, 0, dp(12), 0);
        icon.setLayoutParams(iconLp);

        // 中部：名称(英文+中文) + 类型 + 值
        LinearLayout left = new LinearLayout(this);
        left.setOrientation(LinearLayout.VERTICAL);
        left.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView name = new TextView(this);
        name.setText(buildTitle(f.name, ZhNames.fieldZh(f.name)));
        name.setTextSize(14);
        name.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        name.setTextColor(Ui.color(this, R.color.primary));

        TextView type = new TextView(this);
        type.setText(getString(R.string.field_type, f.type));
        type.setTextSize(11);
        type.setTextColor(Ui.color(this, R.color.text_sub));

        TextView value = new TextView(this);
        value.setText(f.value);
        value.setTextSize(14);
        value.setTypeface(Typeface.MONOSPACE);
        value.setTextColor(Ui.color(this, R.color.text_primary));
        value.setPadding(0, dp(4), 0, 0);
        value.setTextIsSelectable(true);

        left.addView(name);
        left.addView(type);
        left.addView(value);

        card.addView(icon);
        card.addView(left);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFieldCopyMenu(f);
            }
        });
        return card;
    }

    /** 字段点击：弹出选择菜单，让用户选择复制哪部分内容（含收藏） */
    private void showFieldCopyMenu(final OsInfo.FieldInfo f) {
        final String fullName = mClassName + "." + f.name;  // 含完整包名类名前缀
        final String shortName = f.name;                     // 仅字段名
        final String zhName = ZhNames.fieldZh(f.name);       // 中文名（可能为 null）
        final String val = f.value;                          // 当前值

        final boolean isFav = FavoritesStore.contains(this, mClassName, f.name);
        final String favLabel = getString(isFav ? R.string.menu_remove_favorite : R.string.menu_add_favorite);

        final String[] items;
        final String[] payloads;
        if (zhName == null) {
            items = new String[]{
                    getString(R.string.menu_copy_raw_name),
                    getString(R.string.menu_copy_value),
                    getString(R.string.menu_copy_full_entry),
                    favLabel
            };
            payloads = new String[]{
                    fullName,
                    val,
                    fullName + " = " + val,
                    null
            };
        } else {
            items = new String[]{
                    getString(R.string.menu_copy_raw_name),
                    getString(R.string.menu_copy_zh_name),
                    getString(R.string.menu_copy_value),
                    getString(R.string.menu_copy_full_entry),
                    favLabel
            };
            payloads = new String[]{
                    fullName,
                    zhName,
                    val,
                    fullName + "（" + zhName + "） = " + val,
                    null
            };
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle(f.name)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == items.length - 1) {
                            // 最后一项：收藏 / 取消收藏
                            if (isFav) {
                                FavoritesStore.remove(ClassDetailActivity.this, mClassName, f.name);
                                Toast.makeText(ClassDetailActivity.this,
                                        getString(R.string.toast_unfavorited, f.name), Toast.LENGTH_SHORT).show();
                            } else {
                                FavoritesStore.add(ClassDetailActivity.this, mClassName, f.name, f.value);
                                Toast.makeText(ClassDetailActivity.this,
                                        getString(R.string.toast_favorited, f.name), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            copy(payloads[which], items[which]);
                        }
                    }
                })
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .show();
    }

    /** 嵌套类条目：点击进入该类的详情页 */
    private View createNestedView(final Class<?> n) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(12), dp(8), dp(12), dp(8));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(3), 0, dp(3));
        card.setLayoutParams(lp);
        card.setBackground(Ui.roundRectBg(this, R.color.card_bg, 12));
        card.setElevation(dp(1));
        TypedValue ripple = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.selectableItemBackground, ripple, true);
        card.setForeground(getDrawable(ripple.resourceId));
        card.setClickable(true);

        String shortName = n.getName().substring(n.getName().lastIndexOf('.') + 1);
        String kind = n.isInterface() ? getString(R.string.kind_interface)
                : (n.isEnum() ? getString(R.string.kind_enum) : getString(R.string.kind_class));
        String zh = ZhNames.classZh(n.getName());
        if (zh == null) zh = ZhNames.nestedZh(shortName);

        // 左侧：图标（圆形背景 + 主色 Material 图标）
        ImageView icon = new ImageView(this);
        String iconName = Icons.iconName(n.getName());
        icon.setImageResource(getResources().getIdentifier(iconName, "drawable", getPackageName()));
        icon.setBackground(Ui.ovalBg(this, R.color.icon_bg));
        icon.setColorFilter(Ui.color(this, R.color.primary));
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(dp(36), dp(36));
        iconLp.setMargins(0, 0, dp(12), 0);
        icon.setLayoutParams(iconLp);

        // 中部：名称 + 全名
        LinearLayout left = new LinearLayout(this);
        left.setOrientation(LinearLayout.VERTICAL);
        left.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView name = new TextView(this);
        name.setText(buildTitle(shortName, zh) + "  (" + kind + ")");
        name.setTextSize(14);
        name.setTypeface(Typeface.DEFAULT_BOLD);
        name.setTextColor(Ui.color(this, R.color.text_primary));

        TextView full = new TextView(this);
        full.setText(n.getName());
        full.setTextSize(11);
        full.setTypeface(Typeface.MONOSPACE);
        full.setTextColor(Ui.color(this, R.color.text_sub));

        left.addView(name);
        left.addView(full);

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
                Intent intent = new Intent(ClassDetailActivity.this, ClassDetailActivity.class);
                intent.putExtra(EXTRA_CLASS, n.getName());
                startActivity(intent);
            }
        });
        return card;
    }

    /** 判断条目是否为空：静态字段数 = 0 且 嵌套类数 = 0（与主页开关同规则） */
    private boolean isEmptyEntry(Class<?> n) {
        int fc = OsInfo.countStaticFields(n);
        int nc;
        try {
            nc = n.getClasses().length;
        } catch (Throwable t) {
            nc = 0;
        }
        return fc >= 0 && fc == 0 && nc == 0;
    }

    private View emptyHint(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(13);
        tv.setTextColor(Ui.color(this, R.color.text_sub));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(4), 0, dp(8));
        tv.setLayoutParams(lp);
        return tv;
    }

    private void showMessage(LinearLayout container, String msg) {
        TextView tv = new TextView(this);
        tv.setText(msg);
        tv.setTextSize(14);
        tv.setTextColor(Ui.color(this, R.color.text_primary));
        tv.setPadding(dp(16), dp(24), dp(16), dp(24));
        container.addView(tv);
    }

    private String joinNames(Class<?>[] arr) {
        StringBuilder sb = new StringBuilder();
        for (Class<?> c : arr) {
            if (sb.length() > 0) sb.append("\n");
            sb.append(c.getName());
        }
        return sb.toString();
    }

    private void copy(String text, String label) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(label, text));
        Toast.makeText(this, getString(R.string.toast_copied, label), Toast.LENGTH_SHORT).show();
    }

    /** 复制本类全部静态字段（含类型信息）到剪贴板 */
    private void copyAll() {
        StringBuilder sb = new StringBuilder();
        sb.append(mClassName).append('\n');
        for (OsInfo.FieldInfo f : mFields) {
            sb.append("  ").append(mClassName).append('.').append(f.name)
                    .append(" (").append(f.type).append(") = ").append(f.value).append('\n');
        }
        copy(sb.toString(), mClassName);
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

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
