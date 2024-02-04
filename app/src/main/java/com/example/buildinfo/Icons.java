package com.example.buildinfo;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 图标分配器：为 android.os 包下的类（含嵌套类）分配语义化的 Material 图标。
 * 采用"精确匹配表 + 关键词规则"两级策略：
 * - 先查精确映射表（针对知名类）
 * - 未命中则按类名关键词匹配规则
 * - 最终兜底使用默认信息图标
 */
public final class Icons {

    /** 精确匹配：类全名 → 图标资源名 */
    private static final Map<String, String> EXACT = new HashMap<>();

    /** 关键词规则：关键词（大写）→ 图标资源名，按顺序匹配 */
    private static final String[][] RULES = {
            {"BATTERY", "ic_battery"},
            {"VIBRAT", "ic_vibration"},
            {"BINDER", "ic_swap"},
            {"IPARCEL", "ic_swap"},
            {"IINTERFACE", "ic_swap"},
            {"PARCEL", "ic_swap"},
            {"NOTIFICATION", "ic_notification"},
            {"POWER", "ic_power"},
            {"WAKELOCK", "ic_power"},
            {"DEBUG", "ic_memory"},
            {"MEMORYINFO", "ic_memory"},
            {"MEMINFO", "ic_memory"},
            {"CPU", "ic_memory"},
            {"GPU", "ic_memory"},
            {"HEADROOM", "ic_memory"},
            {"STORAGE", "ic_storage"},
            {"STATFS", "ic_storage"},
            {"ENVIRONMENT", "ic_folder"},
            {"FILE", "ic_folder"},
            {"BUNDLE", "ic_folder"},
            {"ERROR", "ic_error"},
            {"EXCEPTION", "ic_error"},
            {"STRICTMODE", "ic_lock"},
            {"CONDITION", "ic_lock"},
            {"SEMAPHORE", "ic_lock"},
            {"LOCK", "ic_lock"},
            {"USER", "ic_person"},
            {"WORKSOURCE", "ic_person"},
            {"HANDLER", "ic_clock"},
            {"LOOPER", "ic_clock"},
            {"MESSAGE", "ic_clock"},
            {"MESSENGER", "ic_clock"},
            {"SYSTEMCLOCK", "ic_clock"},
            {"CLOCK", "ic_clock"},
            {"TIMER", "ic_timer"},
            {"COUNTDOWN", "ic_timer"},
            {"ASYNCTASK", "ic_timer"},
            {"THREAD", "ic_timer"},
            {"PERFORMANCE", "ic_timer"},
            {"MEDIA", "ic_media"},
            {"AUDIO", "ic_media"},
            {"RECORD", "ic_media"},
            {"DROPBOX", "ic_settings"},
            {"SERVICEMANAGER", "ic_settings"},
            {"REGISTRY", "ic_settings"},
            {"PROPERTIES", "ic_settings"},
            {"MANAGER", "ic_settings"},
            {"SYSTEM", "ic_settings"},
            {"VERSION", "ic_history"},
            {"TRACE", "ic_bug"},
            {"BUILD", "ic_os"},
            {"HEALTH", "ic_info"},
            {"SENSOR", "ic_lightbulb"},
            {"CAMERA", "ic_lightbulb"},
            {"HARDWARE", "ic_lightbulb"},
            {"WIFI", "ic_search"},
            {"TELEPHONY", "ic_send"},
            {"PHONE", "ic_phone_android"},
            {"DEVICE", "ic_phone_android"},
    };

    static {
        // ========== 精确匹配表（知名类） ==========
        EXACT.put("android.os.Build", "ic_os");
        EXACT.put("android.os.Build$VERSION", "ic_history");
        EXACT.put("android.os.Build$VERSION_CODES", "ic_history");
        EXACT.put("android.os.Build$VERSION_CODES_FULL", "ic_history");
        EXACT.put("android.os.Build$Partition", "ic_storage");
        EXACT.put("android.os.AsyncTask", "ic_timer");
        EXACT.put("android.os.AsyncTask$Status", "ic_timer");
        EXACT.put("android.os.BatteryManager", "ic_battery");
        EXACT.put("android.os.Binder", "ic_swap");
        EXACT.put("android.os.Environment", "ic_folder");
        EXACT.put("android.os.Process", "ic_memory");
        EXACT.put("android.os.Handler", "ic_clock");
        EXACT.put("android.os.SystemClock", "ic_clock");
        EXACT.put("android.os.PowerManager", "ic_power");
        EXACT.put("android.os.PowerManager$WakeLock", "ic_power");
        EXACT.put("android.os.PowerManager$ThermalStatus", "ic_power");
        EXACT.put("android.os.Debug", "ic_memory");
        EXACT.put("android.os.Debug$MemoryInfo", "ic_memory");
        EXACT.put("android.os.StatFs", "ic_storage");
        EXACT.put("android.os.StorageManager", "ic_storage");
        EXACT.put("android.os.StorageVolume", "ic_storage");
        EXACT.put("android.os.Vibrator", "ic_vibration");
        EXACT.put("android.os.VibrationEffect", "ic_vibration");
        EXACT.put("android.os.CombinedVibration", "ic_vibration");
        EXACT.put("android.os.VibratorManager", "ic_vibration");
        EXACT.put("android.os.UserHandle", "ic_person");
        EXACT.put("android.os.UserManager", "ic_person");
        EXACT.put("android.os.Message", "ic_send");
        EXACT.put("android.os.Messenger", "ic_send");
        EXACT.put("android.os.Parcel", "ic_swap");
        EXACT.put("android.os.HwBinder", "ic_swap");
        EXACT.put("android.os.Trace", "ic_bug");
        EXACT.put("android.os.StrictMode", "ic_lock");
        EXACT.put("android.os.ConditionVariable", "ic_lock");
        EXACT.put("android.os.Semaphore", "ic_lock");
        EXACT.put("android.os.Bundle", "ic_folder");
        EXACT.put("android.os.BaseBundle", "ic_folder");
        EXACT.put("android.os.PersistableBundle", "ic_folder");
        EXACT.put("android.os.CancellationSignal", "ic_error");
        EXACT.put("android.os.DropBoxManager", "ic_settings");
        EXACT.put("android.os.FileUtils", "ic_folder");
        EXACT.put("android.os.FileObserver", "ic_folder");
        EXACT.put("android.os.CountDownTimer", "ic_timer");
        EXACT.put("android.os.CpuUsageInfo", "ic_memory");
        EXACT.put("android.os.HealthManager", "ic_info");
        EXACT.put("android.os.Looper", "ic_clock");
        EXACT.put("android.os.HandlerThread", "ic_timer");
        EXACT.put("android.os.MessageQueue", "ic_clock");
        EXACT.put("android.os.RecoverySystem", "ic_os");
        EXACT.put("android.os.RemoteException", "ic_error");
        EXACT.put("android.os.ServiceManager", "ic_settings");
        EXACT.put("android.os.SystemProperties", "ic_settings");
        EXACT.put("android.os.VintfObject", "ic_os");
        EXACT.put("android.os.WorkSource", "ic_person");
    }

    /** 字段图标规则：关键词（大写）→ 图标资源名，按顺序匹配 */
    private static final String[][] FIELD_RULES = {
            // 版本/代号类
            {"RELEASE", "ic_history"},
            {"SDK", "ic_history"},
            {"CODENAME", "ic_history"},
            {"INCREMENTAL", "ic_history"},
            {"SECURITY_PATCH", "ic_history"},
            {"BASE_OS", "ic_history"},
            {"PREVIEW", "ic_history"},
            {"VERSION", "ic_history"},
            // 电池/电源类
            {"BATTERY", "ic_battery"},
            {"VOLTAGE", "ic_battery"},
            {"TEMPERATURE", "ic_battery"},
            {"CURRENT", "ic_battery"},
            {"ENERGY", "ic_battery"},
            {"CHARGE", "ic_battery"},
            {"POWER", "ic_power"},
            {"WAKELOCK", "ic_power"},
            // 内存/CPU类
            {"MEMORY", "ic_memory"},
            {"MEMINFO", "ic_memory"},
            {"MEM", "ic_memory"},
            {"HEAP", "ic_memory"},
            {"CPU", "ic_memory"},
            {"KERNEL", "ic_memory"},
            {"ZONE", "ic_memory"},
            // 存储/文件类
            {"STORAGE", "ic_storage"},
            {"SPACE", "ic_storage"},
            {"BLOCK", "ic_storage"},
            {"INODE", "ic_storage"},
            {"BYTES", "ic_storage"},
            {"SIZE", "ic_storage"},
            {"FREE", "ic_storage"},
            {"FILE", "ic_folder"},
            {"DIRECTORY", "ic_folder"},
            {"PATH", "ic_folder"},
            // 时间/时钟类
            {"ELAPSED", "ic_clock"},
            {"UPTIME", "ic_clock"},
            {"TIME", "ic_clock"},
            {"NANOS", "ic_clock"},
            {"MILLIS", "ic_clock"},
            {"MICROS", "ic_clock"},
            {"SECONDS", "ic_timer"},
            {"MINUTES", "ic_timer"},
            {"HOURS", "ic_timer"},
            {"DAYS", "ic_timer"},
            {"TIMER", "ic_timer"},
            {"COUNTDOWN", "ic_timer"},
            // 进程/线程/用户类
            {"PID", "ic_person"},
            {"UID", "ic_person"},
            {"GID", "ic_person"},
            {"USER", "ic_person"},
            {"THREAD", "ic_person"},
            {"PROCESS", "ic_person"},
            {"SIGNAL", "ic_swap"},
            {"KILL", "ic_swap"},
            {"WAIT", "ic_swap"},
            // 系统/设置类
            {"PROPERTY", "ic_settings"},
            {"CONFIG", "ic_settings"},
            {"FLAG", "ic_settings"},
            {"MODE", "ic_settings"},
            {"DEFAULT", "ic_settings"},
            {"STATE", "ic_settings"},
            {"STATUS", "ic_settings"},
            {"ENABLED", "ic_settings"},
            {"SUPPORTED", "ic_settings"},
            {"IS_", "ic_info"},
            {"HAS_", "ic_info"},
            {"CAN_", "ic_info"},
            // 错误/锁类
            {"ERROR", "ic_error"},
            {"EXCEPTION", "ic_error"},
            {"FAILED", "ic_error"},
            {"LOCK", "ic_lock"},
            {"STRICT", "ic_lock"},
            // 媒体/通信类
            {"MEDIA", "ic_media"},
            {"AUDIO", "ic_media"},
            {"RECORD", "ic_media"},
            {"NOTIFICATION", "ic_notification"},
            {"MESSAGE", "ic_send"},
            {"WIFI", "ic_search"},
            {"PHONE", "ic_phone_android"},
            {"SENSOR", "ic_lightbulb"},
            {"CAMERA", "ic_lightbulb"},
    };

    private Icons() {}

    /**
     * 根据类全名返回图标资源 ID。
     * 参数用资源名（如 "ic_info"），由调用方通过 getIdentifier 或反射获取 ID。
     * 返回 null 表示无匹配（调用方使用默认值）。
     */
    public static String iconName(String className) {
        if (className == null) return "ic_info";
        // 1) 精确匹配
        String exact = EXACT.get(className);
        if (exact != null) return exact;
        // 2) 嵌套类：去掉外层包名后取短名再查关键词
        String simple = className;
        int dollar = className.lastIndexOf('$');
        if (dollar >= 0) {
            simple = className.substring(dollar + 1);
        } else {
            int dot = className.lastIndexOf('.');
            if (dot >= 0) simple = className.substring(dot + 1);
        }
        String upper = simple.toUpperCase(Locale.US);
        // 3) 关键词规则
        for (String[] rule : RULES) {
            if (upper.contains(rule[0])) {
                return rule[1];
            }
        }
        // 4) 兜底
        return "ic_info";
    }

    /**
     * 根据静态字段名分配语义图标。
     * 返回图标资源名；未命中兜底 ic_info。
     */
    public static String fieldIcon(String fieldName) {
        if (fieldName == null) return "ic_info";
        String upper = fieldName.toUpperCase(Locale.US);
        for (String[] rule : FIELD_RULES) {
            if (upper.contains(rule[0])) {
                return rule[1];
            }
        }
        return "ic_info";
    }
}
