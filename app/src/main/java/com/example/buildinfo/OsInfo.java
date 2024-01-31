package com.example.buildinfo;

import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 反射工具：加载 android.os 相关类、读取静态字段信息。
 * 通过 getFields() 读取（含继承的 public 字段），与"实际对象列表"一致。
 */
public final class OsInfo {

    /** 加载类（不初始化静态块，避免副作用）。加载失败返回 null。 */
    public static Class<?> loadClass(String name) {
        try {
            return Class.forName(name, false, OsInfo.class.getClassLoader());
        } catch (Throwable t) {
            return null;
        }
    }

    /** 统计某个类的 public 静态字段数量（含继承）。加载失败返回 -1。 */
    public static int countStaticFields(Class<?> cls) {
        int count = 0;
        try {
            Field[] fields = cls.getFields();
            for (Field f : fields) {
                if (Modifier.isStatic(f.getModifiers())) count++;
            }
        } catch (Throwable ignored) {
        }
        return count;
    }

    /** 读取某个类的全部 public 静态字段（含继承），并取出当前值。 */
    public static java.util.List<FieldInfo> readStaticFields(Class<?> cls) {
        java.util.List<FieldInfo> out = new java.util.ArrayList<>();
        try {
            Field[] fields = cls.getFields();
            java.util.Arrays.sort(fields, new java.util.Comparator<Field>() {
                @Override
                public int compare(Field a, Field b) {
                    return a.getName().compareTo(b.getName());
                }
            });
            for (Field f : fields) {
                if (!Modifier.isStatic(f.getModifiers())) continue;
                String value;
                try {
                    value = formatValue(f.get(null), f.getType());
                } catch (Throwable t) {
                    value = "<无法读取>";
                }
                out.add(new FieldInfo(f.getName(), f.getType().getSimpleName(), value));
            }
        } catch (Throwable ignored) {
        }
        return out;
    }

    /** 一条字段信息 */
    public static final class FieldInfo {
        public final String name;
        public final String type;
        public final String value;

        public FieldInfo(String name, String type, String value) {
            this.name = name;
            this.type = type;
            this.value = value;
        }
    }

    /** 把反射值格式化为可展示文本 */
    public static String formatValue(Object v, Class<?> type) {
        if (v == null) return "null";
        if (v instanceof String) return (String) v;
        if (v instanceof CharSequence) return v.toString();
        if (type != null && type.isArray()) {
            if (v instanceof String[]) return Arrays.toString((String[]) v);
            if (v instanceof int[]) return Arrays.toString((int[]) v);
            if (v instanceof long[]) return Arrays.toString((long[]) v);
            if (v instanceof boolean[]) return Arrays.toString((boolean[]) v);
            if (v instanceof float[]) return Arrays.toString((float[]) v);
            if (v instanceof double[]) return Arrays.toString((double[]) v);
            if (v instanceof short[]) return Arrays.toString((short[]) v);
            if (v instanceof byte[]) return Arrays.toString((byte[]) v);
            if (v instanceof char[]) return Arrays.toString((char[]) v);
            return Arrays.toString((Object[]) v);
        }
        return v.toString();
    }
}
