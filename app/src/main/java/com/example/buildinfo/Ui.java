package com.example.buildinfo;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import com.google.android.material.color.MaterialColors;

/** UI 辅助工具：动态生成可随主题（暗色/动态色）变化的自适应背景 */
public final class Ui {

    private Ui() {}

    /** dp → px */
    public static int dp(Context ctx, float dp) {
        return Math.round(dp * ctx.getResources().getDisplayMetrics().density);
    }

    /** 解析颜色名 → 主题属性，随暗色/动态色自动变化 */
    public static int color(Context ctx, int colorRes) {
        // 颜色名 → Material 3 主题属性 id（避免 getColor 无法解析 ?attr 的问题）
        int attr = resolveAttr(colorRes);
        if (attr != 0) {
            return MaterialColors.getColor(ctx, attr, 0xFF1A73E8);
        }
        return ctx.getColor(colorRes);
    }

    private static int resolveAttr(int colorRes) {
        if (colorRes == R.color.primary) return com.google.android.material.R.attr.colorPrimary;
        if (colorRes == R.color.accent) return com.google.android.material.R.attr.colorSecondary;
        if (colorRes == R.color.background) return com.google.android.material.R.attr.colorSurface;
        if (colorRes == R.color.card_bg) return com.google.android.material.R.attr.colorSurfaceContainerLowest;
        if (colorRes == R.color.icon_bg) return com.google.android.material.R.attr.colorPrimaryContainer;
        if (colorRes == R.color.text_primary) return com.google.android.material.R.attr.colorOnSurface;
        if (colorRes == R.color.text_sub) return com.google.android.material.R.attr.colorOnSurfaceVariant;
        return 0;
    }

    /** 圆角矩形背景（颜色引用 attr，随主题自动适配） */
    public static GradientDrawable roundRectBg(Context ctx, int colorRes, float radiusDp) {
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(color(ctx, colorRes));
        bg.setCornerRadius(dp(ctx, radiusDp));
        return bg;
    }

    /** 圆形背景（颜色引用 attr，随主题自动适配） */
    public static GradientDrawable ovalBg(Context ctx, int colorRes) {
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.OVAL);
        bg.setColor(color(ctx, colorRes));
        return bg;
    }
}
