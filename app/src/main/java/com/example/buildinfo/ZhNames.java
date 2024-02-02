package com.example.buildinfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 中英文对照表：
 * - 为 android.os 包的全部类（含嵌套类）提供中文名称（手工翻译，与 SDK API 37 对应）
 * - 为字段名提供中文翻译：优先查特例表，未命中则按下划线分词组合
 * 所有方法返回 null 表示无翻译，调用方决定是否展示。
 */
public final class ZhNames {

    /** 类全名 → 中文名（覆盖 OsClasses.TOP_LEVEL 全部 120 个条目） */
    private static final Map<String, String> CLASS_MAP = new HashMap<>();

    /** 字段特例：精确字段名 → 中文名 */
    private static final Map<String, String> FIELD_SPECIAL = new HashMap<>();

    /** 字段词典：单词 → 中文，用于组合翻译 */
    private static final Map<String, String> FIELD_WORDS = new HashMap<>();

    static {
        // ========== 类对照表（120 个） ==========
        CLASS_MAP.put("android.os.AsyncTask", "异步任务");
        CLASS_MAP.put("android.os.AsyncTask$Status", "异步任务·状态");
        CLASS_MAP.put("android.os.BadParcelableException", "非法封包异常");
        CLASS_MAP.put("android.os.BaseBundle", "基础数据包");
        CLASS_MAP.put("android.os.BatteryManager", "电池管理器");
        CLASS_MAP.put("android.os.Binder", "进程间绑定器");
        CLASS_MAP.put("android.os.BugreportManager", "错误报告管理器");
        CLASS_MAP.put("android.os.BugreportManager$BugreportCallback", "错误报告回调");
        CLASS_MAP.put("android.os.Build", "系统构建信息");
        CLASS_MAP.put("android.os.Build$Partition", "构建分区");
        CLASS_MAP.put("android.os.Build$VERSION", "版本信息");
        CLASS_MAP.put("android.os.Build$VERSION_CODES", "版本代号");
        CLASS_MAP.put("android.os.Build$VERSION_CODES_FULL", "完整版本代号");
        CLASS_MAP.put("android.os.Bundle", "数据包（键值容器）");
        CLASS_MAP.put("android.os.CancellationSignal", "取消信号");
        CLASS_MAP.put("android.os.CancellationSignal$OnCancelListener", "取消监听器");
        CLASS_MAP.put("android.os.CombinedVibration", "组合振动");
        CLASS_MAP.put("android.os.CombinedVibration$ParallelCombination", "并行组合振动");
        CLASS_MAP.put("android.os.ConditionVariable", "条件变量");
        CLASS_MAP.put("android.os.CountDownTimer", "倒计时器");
        CLASS_MAP.put("android.os.CpuHeadroomParams", "CPU 余量参数");
        CLASS_MAP.put("android.os.CpuHeadroomParams$Builder", "CPU 余量参数构建器");
        CLASS_MAP.put("android.os.CpuUsageInfo", "CPU 使用信息");
        CLASS_MAP.put("android.os.DeadObjectException", "失效对象异常");
        CLASS_MAP.put("android.os.DeadSystemException", "系统进程失效异常");
        CLASS_MAP.put("android.os.Debug", "调试工具");
        CLASS_MAP.put("android.os.Debug$InstructionCount", "指令计数");
        CLASS_MAP.put("android.os.Debug$MemoryInfo", "内存信息");
        CLASS_MAP.put("android.os.DropBoxManager", "系统日志管理器");
        CLASS_MAP.put("android.os.DropBoxManager$Entry", "日志条目");
        CLASS_MAP.put("android.os.Environment", "存储环境");
        CLASS_MAP.put("android.os.FileObserver", "文件观察器");
        CLASS_MAP.put("android.os.FileUriExposedException", "文件 URI 暴露异常");
        CLASS_MAP.put("android.os.FileUtils", "文件工具");
        CLASS_MAP.put("android.os.FileUtils$ProgressListener", "文件进度监听器");
        CLASS_MAP.put("android.os.GpuHeadroomParams", "GPU 余量参数");
        CLASS_MAP.put("android.os.GpuHeadroomParams$Builder", "GPU 余量参数构建器");
        CLASS_MAP.put("android.os.Handler", "消息处理器");
        CLASS_MAP.put("android.os.Handler$Callback", "消息处理回调");
        CLASS_MAP.put("android.os.HandlerThread", "处理器线程");
        CLASS_MAP.put("android.os.HardwarePropertiesManager", "硬件属性管理器");
        CLASS_MAP.put("android.os.IBinder", "绑定器接口");
        CLASS_MAP.put("android.os.IBinder$DeathRecipient", "进程死亡通知");
        CLASS_MAP.put("android.os.IBinder$FrozenStateChangeCallback", "冻结状态变更回调");
        CLASS_MAP.put("android.os.IInterface", "服务接口");
        CLASS_MAP.put("android.os.LimitExceededException", "超出限制异常");
        CLASS_MAP.put("android.os.LocaleList", "语言环境列表");
        CLASS_MAP.put("android.os.Looper", "消息循环器");
        CLASS_MAP.put("android.os.MemoryFile", "共享内存文件");
        CLASS_MAP.put("android.os.Message", "消息");
        CLASS_MAP.put("android.os.MessageQueue", "消息队列");
        CLASS_MAP.put("android.os.MessageQueue$IdleHandler", "空闲处理器");
        CLASS_MAP.put("android.os.MessageQueue$OnFileDescriptorEventListener", "文件描述符事件监听器");
        CLASS_MAP.put("android.os.Messenger", "跨进程信使");
        CLASS_MAP.put("android.os.NetworkOnMainThreadException", "主线程网络异常");
        CLASS_MAP.put("android.os.OperationCanceledException", "操作取消异常");
        CLASS_MAP.put("android.os.OutcomeReceiver", "结果接收器");
        CLASS_MAP.put("android.os.Parcel", "数据封包");
        CLASS_MAP.put("android.os.ParcelFileDescriptor", "文件描述符");
        CLASS_MAP.put("android.os.ParcelFileDescriptor$AutoCloseInputStream", "自动关闭输入流");
        CLASS_MAP.put("android.os.ParcelFileDescriptor$AutoCloseOutputStream", "自动关闭输出流");
        CLASS_MAP.put("android.os.ParcelFileDescriptor$FileDescriptorDetachedException", "文件描述符脱离异常");
        CLASS_MAP.put("android.os.ParcelFileDescriptor$OnCloseListener", "关闭监听器");
        CLASS_MAP.put("android.os.ParcelFormatException", "封包格式异常");
        CLASS_MAP.put("android.os.ParcelUuid", "封包 UUID");
        CLASS_MAP.put("android.os.Parcelable", "可序列化接口");
        CLASS_MAP.put("android.os.Parcelable$ClassLoaderCreator", "类加载器创建器");
        CLASS_MAP.put("android.os.Parcelable$Creator", "创建器");
        CLASS_MAP.put("android.os.PatternMatcher", "模式匹配器");
        CLASS_MAP.put("android.os.PerformanceHintManager", "性能提示管理器");
        CLASS_MAP.put("android.os.PerformanceHintManager$Session", "性能会话");
        CLASS_MAP.put("android.os.PersistableBundle", "可持久化数据包");
        CLASS_MAP.put("android.os.PowerManager", "电源管理器");
        CLASS_MAP.put("android.os.PowerManager$OnThermalHeadroomChangedListener", "热余量变更监听器");
        CLASS_MAP.put("android.os.PowerManager$OnThermalStatusChangedListener", "热状态变更监听器");
        CLASS_MAP.put("android.os.PowerManager$WakeLock", "唤醒锁");
        CLASS_MAP.put("android.os.PowerManager$WakeLockStateListener", "唤醒锁状态监听器");
        CLASS_MAP.put("android.os.PowerMonitor", "电源监视器");
        CLASS_MAP.put("android.os.PowerMonitorReadings", "电源监视读数");
        CLASS_MAP.put("android.os.Process", "进程");
        CLASS_MAP.put("android.os.ProfilingManager", "性能分析管理器");
        CLASS_MAP.put("android.os.ProfilingResult", "性能分析结果");
        CLASS_MAP.put("android.os.ProfilingTrigger", "性能分析触发器");
        CLASS_MAP.put("android.os.ProfilingTrigger$Builder", "触发器构建器");
        CLASS_MAP.put("android.os.ProxyFileDescriptorCallback", "代理文件描述符回调");
        CLASS_MAP.put("android.os.RecoverySystem", "恢复系统");
        CLASS_MAP.put("android.os.RecoverySystem$ProgressListener", "恢复进度监听器");
        CLASS_MAP.put("android.os.RemoteCallbackList", "远程回调列表");
        CLASS_MAP.put("android.os.RemoteCallbackList$Builder", "远程回调构建器");
        CLASS_MAP.put("android.os.RemoteCallbackList$Builder$InterfaceDiedCallback", "接口死亡回调");
        CLASS_MAP.put("android.os.RemoteException", "远程异常");
        CLASS_MAP.put("android.os.ResultReceiver", "结果接收器");
        CLASS_MAP.put("android.os.SecurityStateManager", "安全状态管理器");
        CLASS_MAP.put("android.os.SharedMemory", "共享内存");
        CLASS_MAP.put("android.os.StatFs", "文件系统状态");
        CLASS_MAP.put("android.os.StrictMode", "严格模式");
        CLASS_MAP.put("android.os.StrictMode$OnThreadViolationListener", "线程违规监听器");
        CLASS_MAP.put("android.os.StrictMode$OnVmViolationListener", "虚拟机违规监听器");
        CLASS_MAP.put("android.os.StrictMode$ThreadPolicy", "线程策略");
        CLASS_MAP.put("android.os.StrictMode$ThreadPolicy$Builder", "线程策略构建器");
        CLASS_MAP.put("android.os.StrictMode$VmPolicy", "虚拟机策略");
        CLASS_MAP.put("android.os.StrictMode$VmPolicy$Builder", "虚拟机策略构建器");
        CLASS_MAP.put("android.os.SystemClock", "系统时钟");
        CLASS_MAP.put("android.os.TestLooperManager", "测试消息循环管理器");
        CLASS_MAP.put("android.os.TokenWatcher", "令牌监视器");
        CLASS_MAP.put("android.os.Trace", "性能跟踪");
        CLASS_MAP.put("android.os.TransactionTooLargeException", "事务过大异常");
        CLASS_MAP.put("android.os.UserHandle", "用户句柄");
        CLASS_MAP.put("android.os.UserManager", "用户管理器");
        CLASS_MAP.put("android.os.UserManager$UserOperationException", "用户操作异常");
        CLASS_MAP.put("android.os.VibrationAttributes", "振动属性");
        CLASS_MAP.put("android.os.VibrationAttributes$Builder", "振动属性构建器");
        CLASS_MAP.put("android.os.VibrationEffect", "振动效果");
        CLASS_MAP.put("android.os.VibrationEffect$BasicEnvelopeBuilder", "基本包络构建器");
        CLASS_MAP.put("android.os.VibrationEffect$Composition", "振动组合");
        CLASS_MAP.put("android.os.VibrationEffect$WaveformEnvelopeBuilder", "波形包络构建器");
        CLASS_MAP.put("android.os.Vibrator", "振动器");
        CLASS_MAP.put("android.os.VibratorManager", "振动管理器");
        CLASS_MAP.put("android.os.WorkDuration", "工作耗时");
        CLASS_MAP.put("android.os.WorkSource", "电量来源");

        // ========== 字段特例表 ==========
        // Build.VERSION
        FIELD_SPECIAL.put("RELEASE", "系统版本");
        FIELD_SPECIAL.put("INCREMENTAL", "增量版本号");
        FIELD_SPECIAL.put("SDK_INT", "SDK 版本号");
        FIELD_SPECIAL.put("SECURITY_PATCH", "安全补丁级别");
        FIELD_SPECIAL.put("BASE_OS", "基础系统版本");
        FIELD_SPECIAL.put("CODENAME", "开发代号");
        FIELD_SPECIAL.put("PREVIEW_SDK_INT", "预览版 SDK 版本");
        FIELD_SPECIAL.put("MEDIA_PERFORMANCE_CLASS", "媒体性能等级");
        FIELD_SPECIAL.put("MIN_SUPPORTED_PLATFORM_VERSION", "最低支持平台版本");
        FIELD_SPECIAL.put("DEVICE_INITIAL_SDK_INT", "设备初始 SDK 版本");
        // Build
        FIELD_SPECIAL.put("BOARD", "主板");
        FIELD_SPECIAL.put("BOOTLOADER", "引导程序版本");
        FIELD_SPECIAL.put("BRAND", "品牌");
        FIELD_SPECIAL.put("CPU_ABI", "CPU 指令集（旧）");
        FIELD_SPECIAL.put("CPU_ABI2", "CPU 指令集 2（旧）");
        FIELD_SPECIAL.put("DEVICE", "设备名称");
        FIELD_SPECIAL.put("DISPLAY", "显示版本");
        FIELD_SPECIAL.put("FINGERPRINT", "构建指纹");
        FIELD_SPECIAL.put("HARDWARE", "硬件");
        FIELD_SPECIAL.put("HOST", "构建主机");
        FIELD_SPECIAL.put("ID", "构建标识");
        FIELD_SPECIAL.put("IS_DEBUGGABLE", "是否可调试");
        FIELD_SPECIAL.put("IS_EMULATOR", "是否模拟器");
        FIELD_SPECIAL.put("IS_TREBLE_ENABLED", "是否支持 Treble");
        FIELD_SPECIAL.put("MANUFACTURER", "制造商");
        FIELD_SPECIAL.put("MODEL", "型号");
        FIELD_SPECIAL.put("PRODUCT", "产品名称");
        FIELD_SPECIAL.put("RADIO", "基带版本（旧）");
        FIELD_SPECIAL.put("SERIAL", "序列号（旧）");
        FIELD_SPECIAL.put("SUPPORTED_32_BIT_ABIS", "支持的 32 位 ABI");
        FIELD_SPECIAL.put("SUPPORTED_64_BIT_ABIS", "支持的 64 位 ABI");
        FIELD_SPECIAL.put("SUPPORTED_ABIS", "支持的 ABI 列表");
        FIELD_SPECIAL.put("TAGS", "构建标签");
        FIELD_SPECIAL.put("TIME", "构建时间");
        FIELD_SPECIAL.put("TYPE", "构建类型");
        FIELD_SPECIAL.put("UNKNOWN", "未知");
        FIELD_SPECIAL.put("USER", "构建用户");
        // VERSION_CODES 甜点代号
        FIELD_SPECIAL.put("CUPCAKE", "Android 1.5 纸杯蛋糕");
        FIELD_SPECIAL.put("DONUT", "Android 1.6 甜甜圈");
        FIELD_SPECIAL.put("ECLAIR", "Android 2.0 松饼");
        FIELD_SPECIAL.put("FROYO", "Android 2.2 冻酸奶");
        FIELD_SPECIAL.put("GINGERBREAD", "Android 2.3 姜饼");
        FIELD_SPECIAL.put("HONEYCOMB", "Android 3.0 蜂巢");
        FIELD_SPECIAL.put("ICE_CREAM_SANDWICH", "Android 4.0 冰淇淋三明治");
        FIELD_SPECIAL.put("JELLY_BEAN", "Android 4.1 果冻豆");
        FIELD_SPECIAL.put("KITKAT", "Android 4.4 奇巧巧克力");
        FIELD_SPECIAL.put("KITKAT_WATCH", "Android 4.4W 手表版");
        FIELD_SPECIAL.put("LOLLIPOP", "Android 5.0 棒棒糖");
        FIELD_SPECIAL.put("LOLLIPOP_MR1", "Android 5.1 棒棒糖");
        FIELD_SPECIAL.put("M", "Android 6.0 棉花糖");
        FIELD_SPECIAL.put("N", "Android 7.0 牛轧糖");
        FIELD_SPECIAL.put("N_MR1", "Android 7.1 牛轧糖");
        FIELD_SPECIAL.put("O", "Android 8.0 奥利奥");
        FIELD_SPECIAL.put("O_MR1", "Android 8.1 奥利奥");
        FIELD_SPECIAL.put("P", "Android 9.0 馅饼");
        FIELD_SPECIAL.put("Q", "Android 10");
        FIELD_SPECIAL.put("R", "Android 11");
        FIELD_SPECIAL.put("S", "Android 12");
        FIELD_SPECIAL.put("S_V2", "Android 12L");
        FIELD_SPECIAL.put("TIRAMISU", "Android 13 提拉米苏");
        FIELD_SPECIAL.put("UPSIDE_DOWN_CAKE", "Android 14 颠倒蛋糕");
        FIELD_SPECIAL.put("VANILLA_ICE_CREAM", "Android 15 香草冰淇淋");
        FIELD_SPECIAL.put("CUR_DEVELOPMENT", "当前开发版本");
        // Process
        FIELD_SPECIAL.put("MY_PID", "当前进程 ID");
        FIELD_SPECIAL.put("MY_TID", "当前线程 ID");
        FIELD_SPECIAL.put("MY_UID", "当前用户 ID");
        // StatFs
        FIELD_SPECIAL.put("BLOCK_SIZE", "块大小（字节）");
        FIELD_SPECIAL.put("BLOCK_COUNT", "块总数");
        FIELD_SPECIAL.put("FREE_BLOCKS", "空闲块数");
        FIELD_SPECIAL.put("AVAILABLE_BLOCKS", "可用块数");
        FIELD_SPECIAL.put("FREE_BYTES", "空闲字节数");
        FIELD_SPECIAL.put("AVAILABLE_BYTES", "可用字节数");
        FIELD_SPECIAL.put("TOTAL_BYTES", "总字节数");

        // ========== 字段分词词典 ==========
        FIELD_WORDS.put("SDK", "SDK");
        FIELD_WORDS.put("VERSION", "版本");
        FIELD_WORDS.put("SECURITY", "安全");
        FIELD_WORDS.put("PATCH", "补丁");
        FIELD_WORDS.put("BASE", "基础");
        FIELD_WORDS.put("OS", "系统");
        FIELD_WORDS.put("CODENAME", "代号");
        FIELD_WORDS.put("PREVIEW", "预览");
        FIELD_WORDS.put("INT", "整数");
        FIELD_WORDS.put("MEDIA", "媒体");
        FIELD_WORDS.put("PERFORMANCE", "性能");
        FIELD_WORDS.put("CLASS", "等级");
        FIELD_WORDS.put("MIN", "最小");
        FIELD_WORDS.put("SUPPORTED", "支持");
        FIELD_WORDS.put("PLATFORM", "平台");
        FIELD_WORDS.put("DEVICE", "设备");
        FIELD_WORDS.put("INITIAL", "初始");
        FIELD_WORDS.put("BOOTLOADER", "引导程序");
        FIELD_WORDS.put("BRAND", "品牌");
        FIELD_WORDS.put("CPU", "CPU");
        FIELD_WORDS.put("ABI", "ABI");
        FIELD_WORDS.put("ABIS", "ABI");
        FIELD_WORDS.put("FINGERPRINT", "指纹");
        FIELD_WORDS.put("HARDWARE", "硬件");
        FIELD_WORDS.put("HOST", "主机");
        FIELD_WORDS.put("MANUFACTURER", "制造商");
        FIELD_WORDS.put("MODEL", "型号");
        FIELD_WORDS.put("PRODUCT", "产品");
        FIELD_WORDS.put("RADIO", "基带");
        FIELD_WORDS.put("SERIAL", "序列号");
        FIELD_WORDS.put("TAGS", "标签");
        FIELD_WORDS.put("TIME", "时间");
        FIELD_WORDS.put("TYPE", "类型");
        FIELD_WORDS.put("USER", "用户");
        FIELD_WORDS.put("UNKNOWN", "未知");
        FIELD_WORDS.put("DEBUGGABLE", "可调试");
        FIELD_WORDS.put("EMULATOR", "模拟器");
        FIELD_WORDS.put("TREBLE", "Treble");
        FIELD_WORDS.put("ENABLED", "启用");
        FIELD_WORDS.put("BOARD", "主板");
        FIELD_WORDS.put("DISPLAY", "显示");
        FIELD_WORDS.put("IS", "是否");
        FIELD_WORDS.put("CUR", "当前");
        FIELD_WORDS.put("DEVELOPMENT", "开发");
        FIELD_WORDS.put("BLOCK", "块");
        FIELD_WORDS.put("SIZE", "大小");
        FIELD_WORDS.put("COUNT", "数量");
        FIELD_WORDS.put("FREE", "空闲");
        FIELD_WORDS.put("AVAILABLE", "可用");
        FIELD_WORDS.put("BYTES", "字节");
        FIELD_WORDS.put("TOTAL", "总");
        FIELD_WORDS.put("MY", "当前");
        FIELD_WORDS.put("PID", "进程 ID");
        FIELD_WORDS.put("TID", "线程 ID");
        FIELD_WORDS.put("UID", "用户 ID");
        FIELD_WORDS.put("SYSTEM", "系统");
        FIELD_WORDS.put("PHONE", "电话");
        FIELD_WORDS.put("APP", "应用");
        FIELD_WORDS.put("ROOT", "根");
        FIELD_WORDS.put("SHELL", "Shell");
        FIELD_WORDS.put("DIRECTORY", "目录");
        FIELD_WORDS.put("DOWNLOADS", "下载");
        FIELD_WORDS.put("PICTURES", "图片");
        FIELD_WORDS.put("MOVIES", "影片");
        FIELD_WORDS.put("MUSIC", "音乐");
        FIELD_WORDS.put("DOCUMENTS", "文档");
        FIELD_WORDS.put("DCIM", "相机照片");
        FIELD_WORDS.put("ALARMS", "闹钟");
        FIELD_WORDS.put("NOTIFICATIONS", "通知");
        FIELD_WORDS.put("PODCASTS", "播客");
        FIELD_WORDS.put("RINGTONES", "铃声");
        FIELD_WORDS.put("DATA", "数据");
        FIELD_WORDS.put("EXTERNAL", "外部");
        FIELD_WORDS.put("INTERNAL", "内部");
        FIELD_WORDS.put("STORAGE", "存储");
        FIELD_WORDS.put("CACHE", "缓存");
        FIELD_WORDS.put("OBB", "OBB");
        FIELD_WORDS.put("TRACE", "跟踪");
        FIELD_WORDS.put("TAG", "标签");
        FIELD_WORDS.put("ACTIVITY", "活动");
        FIELD_WORDS.put("GRAPHICS", "图形");
        FIELD_WORDS.put("INPUT", "输入");
        FIELD_WORDS.put("VIEW", "视图");
        FIELD_WORDS.put("WEBVIEW", "网页视图");
        FIELD_WORDS.put("WINDOW", "窗口");
        FIELD_WORDS.put("AUDIO", "音频");
        FIELD_WORDS.put("VIDEO", "视频");
        FIELD_WORDS.put("CAMERA", "相机");
        FIELD_WORDS.put("HAL", "HAL");
        FIELD_WORDS.put("MEMORY", "内存");
        FIELD_WORDS.put("BATTERY", "电池");
        FIELD_WORDS.put("PROPERTY", "属性");
        FIELD_WORDS.put("CAPACITY", "容量");
        FIELD_WORDS.put("CHARGING", "充电中");
        FIELD_WORDS.put("ENERGY", "能量");
        FIELD_WORDS.put("COUNTER", "计数");
        FIELD_WORDS.put("CURRENT", "电流");
        FIELD_WORDS.put("VOLTAGE", "电压");
        FIELD_WORDS.put("TEMPERATURE", "温度");
        FIELD_WORDS.put("STATUS", "状态");
        FIELD_WORDS.put("HEALTH", "健康");
        FIELD_WORDS.put("LEVEL", "电量");
        FIELD_WORDS.put("SCALE", "刻度");
        FIELD_WORDS.put("TECHNOLOGY", "技术");
        FIELD_WORDS.put("CHARGE", "充电");
        FIELD_WORDS.put("PLUGGED", "已插电");
        FIELD_WORDS.put("FLAG", "标志");
        FIELD_WORDS.put("PERMISSION", "权限");
        FIELD_WORDS.put("READ", "读取");
        FIELD_WORDS.put("WRITE", "写入");
        FIELD_WORDS.put("INTERNET", "网络");
        FIELD_WORDS.put("WAKE", "唤醒");
        FIELD_WORDS.put("LOCK", "锁");
        FIELD_WORDS.put("PARTIAL", "部分");
        FIELD_WORDS.put("FULL", "完整");
        FIELD_WORDS.put("SCREEN", "屏幕");
        FIELD_WORDS.put("BRIGHT", "变亮");
        FIELD_WORDS.put("DIM", "变暗");
        FIELD_WORDS.put("ON", "开启");
        FIELD_WORDS.put("OFF", "关闭");
        FIELD_WORDS.put("AFTER", "之后");
        FIELD_WORDS.put("LONG", "长");
        FIELD_WORDS.put("SHORT", "短");
        FIELD_WORDS.put("VIBRATE", "振动");
        FIELD_WORDS.put("PROXIMITY", "距离感应");
        FIELD_WORDS.put("WAIT", "等待");
        FIELD_WORDS.put("KEEP", "保持");
        FIELD_WORDS.put("TIMEOUT", "超时");
        FIELD_WORDS.put("HEADROOM", "余量");
        FIELD_WORDS.put("PARAMS", "参数");
        FIELD_WORDS.put("USAGE", "使用");
        FIELD_WORDS.put("INFO", "信息");
        FIELD_WORDS.put("READINGS", "读数");
        FIELD_WORDS.put("MONITOR", "监视");
        FIELD_WORDS.put("WORK", "工作");
        FIELD_WORDS.put("DURATION", "耗时");
        FIELD_WORDS.put("SOURCE", "来源");
        FIELD_WORDS.put("RESULT", "结果");
        FIELD_WORDS.put("DEFAULT", "默认");
        FIELD_WORDS.put("LIMIT", "限制");
        FIELD_WORDS.put("MAX", "最大");
        FIELD_WORDS.put("MINIMUM", "最小");
    }

    private ZhNames() {}

    /** 类的中文名；未知返回 null */
    public static String classZh(String className) {
        String zh = CLASS_MAP.get(className);
        if (zh != null) return zh;
        return null;
    }

    /** 嵌套类中文名（按名称拆词组合）；未知返回 null */
    public static String nestedZh(String shortName) {
        // 优先尝试拆 CamelCase 单词组合
        String zh = composeCamel(shortName);
        return zh == null ? null : zh;
    }

    /** 字段中文名；未知返回 null */
    public static String fieldZh(String fieldName) {
        String zh = FIELD_SPECIAL.get(fieldName);
        if (zh != null) return zh;
        String[] parts = fieldName.split("_");
        StringBuilder sb = new StringBuilder();
        int hit = 0;
        for (String p : parts) {
            if (p.isEmpty()) continue;
            String w = FIELD_WORDS.get(p);
            if (w != null) {
                sb.append(w);
                hit++;
            } else {
                sb.append(p);
            }
        }
        return hit == 0 ? null : sb.toString();
    }

    /** 把 CamelCase 拆词并组合翻译，如 WakeLock → 唤醒锁 */
    private static String composeCamel(String name) {
        StringBuilder words = new StringBuilder();
        int start = 0;
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c) && Character.isLowerCase(name.charAt(i - 1))) {
                words.append(name, start, i).append(' ');
                start = i;
            }
        }
        words.append(name.substring(start));
        String[] parts = words.toString().trim().split(" ");
        StringBuilder sb = new StringBuilder();
        int hit = 0;
        for (String p : parts) {
            if (p.isEmpty()) continue;
            String w = FIELD_WORDS.get(p);
            if (w != null) {
                sb.append(w);
                hit++;
            } else {
                sb.append(p);
            }
        }
        return hit == 0 ? null : sb.toString();
    }
}
