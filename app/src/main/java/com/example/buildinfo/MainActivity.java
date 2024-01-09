package com.example.buildinfo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面：展示 android.os.Build.VERSION 中的各项系统版本信息，
 * 每条同时显示原对象名字段（Build.VERSION.xxx）与对应的中文名称。
 */
public class MainActivity extends Activity {

    /** 一条版本信息：中文名称 + 原字段名 + 实际值 */
    private static final class InfoItem {
        final String chineseName;
        final String fieldName;
        final String value;

        InfoItem(String chineseName, String fieldName, String value) {
            this.chineseName = chineseName;
            this.fieldName = fieldName;
            this.value = value;
        }
    }

    private LinearLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainer = findViewById(R.id.container);

        TextView deviceInfo = findViewById(R.id.device_info);
        deviceInfo.setText("设备：" + Build.MANUFACTURER + " " + Build.MODEL
                + " · Android " + Build.VERSION.RELEASE
                + " (API " + Build.VERSION.SDK_INT + ")");

        List<InfoItem> items = buildItems();
        for (InfoItem item : items) {
            mContainer.addView(createItemView(item));
        }

        Button copyBtn = findViewById(R.id.copy_btn);
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyAll(items);
            }
        });
    }

    /** 收集 Build.VERSION 各字段（中文名 + 原字段名 + 值） */
    private List<InfoItem> buildItems() {
        List<InfoItem> items = new ArrayList<>();
        items.add(new InfoItem("基础操作系统", "Build.VERSION.BASE_OS",
                textOf(Build.VERSION.BASE_OS)));
        items.add(new InfoItem("开发代号", "Build.VERSION.CODENAME",
                textOf(Build.VERSION.CODENAME)));
        items.add(new InfoItem("增量版本号（内部构建号）", "Build.VERSION.INCREMENTAL",
                textOf(Build.VERSION.INCREMENTAL)));
        items.add(new InfoItem("预览版 SDK 编号", "Build.VERSION.PREVIEW_SDK_INT",
                intOf(Build.VERSION.PREVIEW_SDK_INT)));
        items.add(new InfoItem("发布版本号（Android 版本）", "Build.VERSION.RELEASE",
                textOf(Build.VERSION.RELEASE)));
        items.add(new InfoItem("发布版本或开发代号", "Build.VERSION.RELEASE_OR_CODENAME",
                textOf(Build.VERSION.RELEASE_OR_CODENAME)));
        items.add(new InfoItem("发布/预览显示名称", "Build.VERSION.RELEASE_OR_PREVIEW_DISPLAY",
                textOf(Build.VERSION.RELEASE_OR_PREVIEW_DISPLAY)));
        items.add(new InfoItem("SDK 版本（字符串）", "Build.VERSION.SDK",
                textOf(Build.VERSION.SDK)));
        items.add(new InfoItem("SDK 整型编号", "Build.VERSION.SDK_INT",
                intOf(Build.VERSION.SDK_INT)));
        items.add(new InfoItem("安全补丁级别", "Build.VERSION.SECURITY_PATCH",
                textOf(Build.VERSION.SECURITY_PATCH)));
        items.add(new InfoItem("媒体性能等级", "Build.VERSION.MEDIA_PERFORMANCE_CLASS",
                intOf(Build.VERSION.MEDIA_PERFORMANCE_CLASS)));
        return items;
    }

    /** 字符串为空时给出提示文案 */
    private static String textOf(String value) {
        return (value == null || value.isEmpty()) ? "—（此系统未提供）" : value;
    }

    /** 整型为 0（未定义）时给出提示文案 */
    private static String intOf(int value) {
        return value == 0 ? "0（此系统未提供）" : String.valueOf(value);
    }

    /** 动态生成一条“卡片”样式的信息条目 */
    private View createItemView(InfoItem item) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(14), dp(12), dp(14), dp(12));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(5), 0, dp(5));
        card.setLayoutParams(lp);
        card.setBackgroundResource(R.drawable.card_bg);
        card.setElevation(dp(1));

        TextView title = new TextView(this);
        title.setText(item.chineseName);
        title.setTextSize(16);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(getColor(R.color.text_primary));

        TextView field = new TextView(this);
        field.setText(item.fieldName);
        field.setTextSize(12);
        field.setTypeface(Typeface.MONOSPACE);
        field.setTextColor(getColor(R.color.text_sub));
        field.setPadding(0, dp(3), 0, 0);

        TextView value = new TextView(this);
        value.setText(item.value);
        value.setTextSize(15);
        value.setTypeface(Typeface.MONOSPACE);
        value.setTextColor(getColor(R.color.text_primary));
        value.setPadding(0, dp(6), 0, 0);
        value.setTextIsSelectable(true);

        card.addView(title);
        card.addView(field);
        card.addView(value);
        return card;
    }

    /** 把全部信息复制到剪贴板 */
    private void copyAll(List<InfoItem> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("Android Build.VERSION 系统版本信息\n");
        sb.append("设备: ").append(Build.MANUFACTURER).append(' ').append(Build.MODEL).append('\n');
        sb.append("Android 版本: ").append(Build.VERSION.RELEASE)
                .append(" (API ").append(Build.VERSION.SDK_INT).append(")\n\n");
        for (InfoItem item : items) {
            sb.append("【").append(item.chineseName).append("】\n");
            sb.append("  ").append(item.fieldName).append(" = ").append(item.value).append('\n');
        }
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("Build.VERSION", sb.toString()));
        Toast.makeText(this, "已复制全部信息到剪贴板", Toast.LENGTH_SHORT).show();
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
