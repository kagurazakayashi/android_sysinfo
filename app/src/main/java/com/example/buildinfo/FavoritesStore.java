package com.example.buildinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏夹存储：把收藏的静态字段（类名 + 字段名 + 收藏时的值）持久化到 SharedPreferences。
 * 收藏粒度 = 字段，key = 类名 + 字段名，value 缓存收藏当时的字段值。
 */
public final class FavoritesStore {

    private static final String PREFS = "favorites";
    private static final String KEY_ITEMS = "items";
    private static final String KEY_DEFAULTS_INIT = "defaults_initialized";

    /** 首次启动预置的默认收藏：制造商 / 型号 / Android 版本号 / API 版本号 */
    public static void ensureDefaults(Context c) {
        SharedPreferences p = prefs(c);
        if (p.getBoolean(KEY_DEFAULTS_INIT, false)) return;
        p.edit().putBoolean(KEY_DEFAULTS_INIT, true).apply();
        if (!getAll(c).isEmpty()) return; // 已有收藏则不覆盖
        add(c, "android.os.Build", "MANUFACTURER", String.valueOf(Build.MANUFACTURER));
        add(c, "android.os.Build", "MODEL", String.valueOf(Build.MODEL));
        add(c, "android.os.Build$VERSION", "RELEASE", String.valueOf(Build.VERSION.RELEASE));
        add(c, "android.os.Build$VERSION", "SDK_INT", String.valueOf(Build.VERSION.SDK_INT));
    }

    /** 一条收藏记录 */
    public static final class Favorite {
        public final String className;   // 完整限定类名，如 android.os.Build$VERSION
        public final String fieldName;   // 字段名，如 SDK_INT
        public final String value;       // 收藏时的字段值（缓存展示）

        public Favorite(String className, String fieldName, String value) {
            this.className = className;
            this.fieldName = fieldName;
            this.value = value;
        }
    }

    private FavoritesStore() {
    }

    private static SharedPreferences prefs(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    /** 全部收藏（按收藏时间倒序：新收藏在前） */
    public static synchronized List<Favorite> getAll(Context c) {
        List<Favorite> out = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(prefs(c).getString(KEY_ITEMS, "[]"));
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                out.add(new Favorite(
                        o.optString("className"),
                        o.optString("fieldName"),
                        o.optString("value")));
            }
        } catch (Throwable ignored) {
        }
        return out;
    }

    /** 是否已收藏该字段 */
    public static synchronized boolean contains(Context c, String className, String fieldName) {
        for (Favorite f : getAll(c)) {
            if (f.className.equals(className) && f.fieldName.equals(fieldName)) return true;
        }
        return false;
    }

    /** 添加收藏（重复添加则移到最前并更新值） */
    public static synchronized void add(Context c, String className, String fieldName, String value) {
        List<Favorite> filtered = new ArrayList<>();
        for (Favorite f : getAll(c)) {
            if (!(f.className.equals(className) && f.fieldName.equals(fieldName))) {
                filtered.add(f);
            }
        }
        filtered.add(0, new Favorite(className, fieldName, value));
        save(c, filtered);
    }

    /** 取消收藏 */
    public static synchronized void remove(Context c, String className, String fieldName) {
        List<Favorite> filtered = new ArrayList<>();
        for (Favorite f : getAll(c)) {
            if (!(f.className.equals(className) && f.fieldName.equals(fieldName))) {
                filtered.add(f);
            }
        }
        save(c, filtered);
    }

    private static void save(Context c, List<Favorite> list) {
        try {
            JSONArray arr = new JSONArray();
            for (Favorite f : list) {
                JSONObject o = new JSONObject();
                o.put("className", f.className);
                o.put("fieldName", f.fieldName);
                o.put("value", f.value);
                arr.put(o);
            }
            prefs(c).edit().putString(KEY_ITEMS, arr.toString()).apply();
        } catch (Throwable ignored) {
        }
    }
}
