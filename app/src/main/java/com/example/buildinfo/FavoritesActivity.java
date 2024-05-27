package com.example.buildinfo;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

/**
 * 收藏夹页面：展示用户收藏的静态字段。
 * 点击条目弹出菜单（复制各部分 / 取消收藏），保持与详情页字段一致的操作逻辑。
 */
public class FavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.favorites));
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
        render(container);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 返回时刷新（取消收藏后列表即时更新）
        LinearLayout container = findViewById(R.id.container);
        if (container != null) {
            container.removeAllViews();
            render(container);
        }
    }

    private void render(final LinearLayout container) {
        final List<FavoritesStore.Favorite> favs = FavoritesStore.getAll(this);
        if (favs.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText(R.string.favorites_empty);
            empty.setTextSize(14);
            empty.setTextColor(getColor(R.color.text_sub));
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(dp(16), dp(48), dp(16), dp(16));
            container.addView(empty);
            toolbarSubtitle("显示 0 / 0 项");
            return;
        }
        toolbarSubtitle("显示 " + favs.size() + " / " + favs.size() + " 项");
        for (final FavoritesStore.Favorite f : favs) {
            container.addView(createItem(f, container));
        }
    }

    private void toolbarSubtitle(String s) {
        Toolbar tb = findViewById(R.id.toolbar);
        if (tb != null) tb.setSubtitle(s);
    }

    /** 生成一条收藏条目 */
    private View createItem(final FavoritesStore.Favorite f, final LinearLayout container) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(12), dp(8), dp(12), dp(8));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(3), 0, dp(3));
        card.setLayoutParams(lp);
        card.setBackgroundResource(R.drawable.card_bg);
        card.setElevation(dp(1));
        TypedValue ripple = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.selectableItemBackground, ripple, true);
        card.setForeground(getDrawable(ripple.resourceId));
        card.setClickable(true);

        // 图标：仅字段图标，不在图标下方叠加收藏夹星星
        ImageView icon = new ImageView(this);
        String iconName = Icons.fieldIcon(f.fieldName);
        icon.setImageResource(getResources().getIdentifier(iconName, "drawable", getPackageName()));
        icon.setBackgroundResource(R.drawable.ic_icon_bg);
        icon.setColorFilter(getColor(R.color.primary));
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(dp(36), dp(36));
        iconLp.setMargins(0, 0, dp(12), 0);
        icon.setLayoutParams(iconLp);

        // 中部：字段名(中文) + 类名 + 值
        LinearLayout left = new LinearLayout(this);
        left.setOrientation(LinearLayout.VERTICAL);
        left.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView name = new TextView(this);
        name.setText(buildTitle(f.fieldName, ZhNames.fieldZh(f.fieldName)));
        name.setTextSize(14);
        name.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        name.setTextColor(getColor(R.color.primary));

        TextView clsName = new TextView(this);
        clsName.setText(f.className);
        clsName.setTextSize(11);
        clsName.setTypeface(Typeface.MONOSPACE);
        clsName.setTextColor(getColor(R.color.text_sub));

        TextView value = new TextView(this);
        value.setText(f.value);
        value.setTextSize(14);
        value.setTypeface(Typeface.MONOSPACE);
        value.setTextColor(getColor(R.color.text_primary));
        value.setPadding(0, dp(4), 0, 0);
        value.setTextIsSelectable(true);

        left.addView(name);
        left.addView(clsName);
        left.addView(value);

        card.addView(icon);
        card.addView(left);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFavoriteMenu(f, container);
            }
        });
        return card;
    }

    /** 收藏条目点击菜单：复制各部分 / 取消收藏 */
    private void showFavoriteMenu(final FavoritesStore.Favorite f, final LinearLayout container) {
        final String zhName = ZhNames.fieldZh(f.fieldName);
        final String[] items;
        final String[] payloads;
        if (zhName == null) {
            items = new String[]{
                    "复制原始名称",
                    "复制值",
                    "复制完整条目",
                    "取消收藏"
            };
            payloads = new String[]{
                    f.className + "." + f.fieldName,
                    f.value,
                    f.className + "." + f.fieldName + " = " + f.value,
                    null
            };
        } else {
            items = new String[]{
                    "复制原始名称",
                    "复制中文名",
                    "复制值",
                    "复制完整条目",
                    "取消收藏"
            };
            payloads = new String[]{
                    f.className + "." + f.fieldName,
                    zhName,
                    f.value,
                    f.className + "." + f.fieldName + "（" + zhName + "） = " + f.value,
                    null
            };
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle(f.fieldName)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == items.length - 1) {
                            // 最后一项：取消收藏
                            FavoritesStore.remove(FavoritesActivity.this, f.className, f.fieldName);
                            Toast.makeText(FavoritesActivity.this, "已取消收藏：" + f.fieldName, Toast.LENGTH_SHORT).show();
                            container.removeAllViews();
                            render(container);
                        } else {
                            copy(payloads[which], items[which]);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void copy(String text, String label) {
        android.content.ClipboardManager cm =
                (android.content.ClipboardManager) getSystemService(android.content.Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(android.content.ClipData.newPlainText(label, text));
        Toast.makeText(this, "已复制：" + label, Toast.LENGTH_SHORT).show();
    }

    /** 主标题：英文名 + 中文名（中文用强调色显示） */
    private CharSequence buildTitle(String en, String zh) {
        if (zh == null || zh.isEmpty()) return en;
        String text = en + "  " + zh;
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(getColor(R.color.accent)),
                en.length() + 2, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
