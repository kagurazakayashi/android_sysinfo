package com.example.buildinfo;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

/**
 * 语言管理：持久化用户选择并应用（AppCompatDelegate.setApplicationLocales 官方方案）。
 * 语言值：""(跟随系统) / "zh-rCN" / "zh-rTW" / "en" / "ja"
 */
public final class LocaleManager {

    private static final String PREFS = "locale_prefs";
    private static final String KEY = "locale";

    private LocaleManager() {}

    /** 读取当前保存的语言值；空字符串表示跟随系统 */
    public static String getSavedLocale(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY, "");
    }

    /** 保存并立即应用语言（"" = 跟随系统） */
    public static void setLocale(Context context, String locale) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putString(KEY, locale).apply();
        apply(context);
        // 同步刷新 ZhNames 的语言模式
        ZhNames.init(context);
    }

    /** 应用当前语言（启动时调用） */
    public static void apply(Context context) {
        String locale = getSavedLocale(context);
        if (locale == null || locale.isEmpty()) {
            // 跟随系统
            AppCompatDelegate.setApplicationLocales(
                    androidx.core.os.LocaleListCompat.getEmptyLocaleList());
        } else if (locale.equals("zh-rCN")) {
            AppCompatDelegate.setApplicationLocales(
                    androidx.core.os.LocaleListCompat.forLanguageTags("zh-CN"));
        } else if (locale.equals("zh-rTW")) {
            AppCompatDelegate.setApplicationLocales(
                    androidx.core.os.LocaleListCompat.forLanguageTags("zh-TW"));
        } else if (locale.equals("ja")) {
            AppCompatDelegate.setApplicationLocales(
                    androidx.core.os.LocaleListCompat.forLanguageTags("ja"));
        } else {
            AppCompatDelegate.setApplicationLocales(
                    androidx.core.os.LocaleListCompat.forLanguageTags("en"));
        }
        ZhNames.init(context);
    }

    /** 用户当前生效的语言标签；返回 Locale（用于 ZhNames 判断） */
    public static Locale currentLocale(Context context) {
        String locale = getSavedLocale(context);
        if (locale == null || locale.isEmpty()) {
            return Locale.getDefault();
        }
        if (locale.equals("zh-rCN")) return Locale.SIMPLIFIED_CHINESE;
        if (locale.equals("zh-rTW")) return Locale.TRADITIONAL_CHINESE;
        if (locale.equals("ja")) return Locale.JAPANESE;
        return Locale.ENGLISH;
    }
}
