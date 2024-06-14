package com.example.buildinfo;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Locale;
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

    /** 类全名 → 英文说明（只收录有附加价值的；自明单词不收录，英文界面不显示冗余标注） */
    private static final Map<String, String> CLASS_EN = new HashMap<>();

    /** 类全名 → 日文说明 */
    private static final Map<String, String> CLASS_JA = new HashMap<>();

    /** 字段特例：字段名 → 英文说明 */
    private static final Map<String, String> FIELD_SPECIAL_EN = new HashMap<>();

    /** 字段特例：字段名 → 日文说明 */
    private static final Map<String, String> FIELD_SPECIAL_JA = new HashMap<>();

    /** 字段词典：单词 → 英文，用于组合翻译 */
    private static final Map<String, String> FIELD_WORDS_EN = new HashMap<>();

    /** 字段词典：单词 → 日文，用于组合翻译 */
    private static final Map<String, String> FIELD_WORDS_JA = new HashMap<>();

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

        // ========== 类 → 英文说明 ==========
        CLASS_EN.put("android.os.AsyncTask", "Background async task");
        CLASS_EN.put("android.os.AsyncTask$Status", "Async task status");
        CLASS_EN.put("android.os.BadParcelableException", "Invalid Parcelable exception");
        CLASS_EN.put("android.os.BaseBundle", "Base key-value bundle");
        CLASS_EN.put("android.os.BatteryManager", "Battery manager");
        CLASS_EN.put("android.os.Binder", "IPC binder");
        CLASS_EN.put("android.os.BugreportManager", "Bug report manager");
        CLASS_EN.put("android.os.BugreportManager$BugreportCallback", "Bug report callback");
        CLASS_EN.put("android.os.Build", "System build info");
        CLASS_EN.put("android.os.Build$Partition", "Build partition");
        CLASS_EN.put("android.os.Build$VERSION", "Version info");
        CLASS_EN.put("android.os.Build$VERSION_CODES", "Version codes");
        CLASS_EN.put("android.os.Build$VERSION_CODES_FULL", "Full version codes");
        CLASS_EN.put("android.os.Bundle", "Key-value bundle");
        CLASS_EN.put("android.os.CancellationSignal", "Cancellation signal");
        CLASS_EN.put("android.os.CancellationSignal$OnCancelListener", "Cancel listener");
        CLASS_EN.put("android.os.CombinedVibration", "Combined vibration");
        CLASS_EN.put("android.os.CombinedVibration$ParallelCombination", "Parallel vibration combo");
        CLASS_EN.put("android.os.ConditionVariable", "Condition variable");
        CLASS_EN.put("android.os.CountDownTimer", "Countdown timer");
        CLASS_EN.put("android.os.CpuHeadroomParams", "CPU headroom params");
        CLASS_EN.put("android.os.CpuHeadroomParams$Builder", "CPU headroom params builder");
        CLASS_EN.put("android.os.CpuUsageInfo", "CPU usage info");
        CLASS_EN.put("android.os.DeadObjectException", "Dead object exception");
        CLASS_EN.put("android.os.DeadSystemException", "Dead system exception");
        CLASS_EN.put("android.os.Debug", "Debug utilities");
        CLASS_EN.put("android.os.Debug$InstructionCount", "Instruction count");
        CLASS_EN.put("android.os.Debug$MemoryInfo", "Memory info");
        CLASS_EN.put("android.os.DropBoxManager", "System log manager");
        CLASS_EN.put("android.os.DropBoxManager$Entry", "Log entry");
        CLASS_EN.put("android.os.Environment", "Storage environment");
        CLASS_EN.put("android.os.FileObserver", "File observer");
        CLASS_EN.put("android.os.FileUriExposedException", "File URI exposure exception");
        CLASS_EN.put("android.os.FileUtils", "File utilities");
        CLASS_EN.put("android.os.FileUtils$ProgressListener", "File progress listener");
        CLASS_EN.put("android.os.GpuHeadroomParams", "GPU headroom params");
        CLASS_EN.put("android.os.GpuHeadroomParams$Builder", "GPU headroom params builder");
        CLASS_EN.put("android.os.Handler", "Message handler");
        CLASS_EN.put("android.os.Handler$Callback", "Message handler callback");
        CLASS_EN.put("android.os.HandlerThread", "Handler thread");
        CLASS_EN.put("android.os.HardwarePropertiesManager", "Hardware properties manager");
        CLASS_EN.put("android.os.IBinder", "Binder interface");
        CLASS_EN.put("android.os.IBinder$DeathRecipient", "Process death notification");
        CLASS_EN.put("android.os.IBinder$FrozenStateChangeCallback", "Frozen state change callback");
        CLASS_EN.put("android.os.IInterface", "Service interface");
        CLASS_EN.put("android.os.LimitExceededException", "Limit exceeded exception");
        CLASS_EN.put("android.os.LocaleList", "Locale list");
        CLASS_EN.put("android.os.Looper", "Message looper");
        CLASS_EN.put("android.os.MemoryFile", "Shared memory file");
        CLASS_EN.put("android.os.Message", "Message");
        CLASS_EN.put("android.os.MessageQueue", "Message queue");
        CLASS_EN.put("android.os.MessageQueue$IdleHandler", "Idle handler");
        CLASS_EN.put("android.os.MessageQueue$OnFileDescriptorEventListener", "FD event listener");
        CLASS_EN.put("android.os.Messenger", "Cross-process messenger");
        CLASS_EN.put("android.os.NetworkOnMainThreadException", "Network on main thread");
        CLASS_EN.put("android.os.OperationCanceledException", "Operation canceled");
        CLASS_EN.put("android.os.OutcomeReceiver", "Outcome receiver");
        CLASS_EN.put("android.os.Parcel", "Data parcel");
        CLASS_EN.put("android.os.ParcelFileDescriptor", "File descriptor");
        CLASS_EN.put("android.os.ParcelFileDescriptor$AutoCloseInputStream", "Auto-close input stream");
        CLASS_EN.put("android.os.ParcelFileDescriptor$AutoCloseOutputStream", "Auto-close output stream");
        CLASS_EN.put("android.os.ParcelFileDescriptor$FileDescriptorDetachedException", "FD detached exception");
        CLASS_EN.put("android.os.ParcelFileDescriptor$OnCloseListener", "Close listener");
        CLASS_EN.put("android.os.ParcelFormatException", "Parcel format exception");
        CLASS_EN.put("android.os.ParcelUuid", "Parcelable UUID");
        CLASS_EN.put("android.os.Parcelable", "Serializable interface");
        CLASS_EN.put("android.os.Parcelable$ClassLoaderCreator", "Class loader creator");
        CLASS_EN.put("android.os.Parcelable$Creator", "Creator");
        CLASS_EN.put("android.os.PatternMatcher", "Pattern matcher");
        CLASS_EN.put("android.os.PerformanceHintManager", "Performance hint manager");
        CLASS_EN.put("android.os.PerformanceHintManager$Session", "Performance session");
        CLASS_EN.put("android.os.PersistableBundle", "Persistable bundle");
        CLASS_EN.put("android.os.PowerManager", "Power manager");
        CLASS_EN.put("android.os.PowerManager$OnThermalHeadroomChangedListener", "Thermal headroom listener");
        CLASS_EN.put("android.os.PowerManager$OnThermalStatusChangedListener", "Thermal status listener");
        CLASS_EN.put("android.os.PowerManager$WakeLock", "Wake lock");
        CLASS_EN.put("android.os.PowerManager$WakeLockStateListener", "Wake lock state listener");
        CLASS_EN.put("android.os.PowerMonitor", "Power monitor");
        CLASS_EN.put("android.os.PowerMonitorReadings", "Power monitor readings");
        CLASS_EN.put("android.os.Process", "Process");
        CLASS_EN.put("android.os.ProfilingManager", "Profiling manager");
        CLASS_EN.put("android.os.ProfilingResult", "Profiling result");
        CLASS_EN.put("android.os.ProfilingTrigger", "Profiling trigger");
        CLASS_EN.put("android.os.ProfilingTrigger$Builder", "Profiling trigger builder");
        CLASS_EN.put("android.os.ProxyFileDescriptorCallback", "Proxy FD callback");
        CLASS_EN.put("android.os.RecoverySystem", "Recovery system");
        CLASS_EN.put("android.os.RecoverySystem$ProgressListener", "Recovery progress listener");
        CLASS_EN.put("android.os.RemoteCallbackList", "Remote callback list");
        CLASS_EN.put("android.os.RemoteCallbackList$Builder", "Remote callback list builder");
        CLASS_EN.put("android.os.RemoteCallbackList$Builder$InterfaceDiedCallback", "Interface died callback");
        CLASS_EN.put("android.os.RemoteException", "Remote exception");
        CLASS_EN.put("android.os.ResultReceiver", "Result receiver");
        CLASS_EN.put("android.os.SecurityStateManager", "Security state manager");
        CLASS_EN.put("android.os.SharedMemory", "Shared memory");
        CLASS_EN.put("android.os.StatFs", "File system status");
        CLASS_EN.put("android.os.StrictMode", "Strict mode");
        CLASS_EN.put("android.os.StrictMode$OnThreadViolationListener", "Thread violation listener");
        CLASS_EN.put("android.os.StrictMode$OnVmViolationListener", "VM violation listener");
        CLASS_EN.put("android.os.StrictMode$ThreadPolicy", "Thread policy");
        CLASS_EN.put("android.os.StrictMode$ThreadPolicy$Builder", "Thread policy builder");
        CLASS_EN.put("android.os.StrictMode$VmPolicy", "VM policy");
        CLASS_EN.put("android.os.StrictMode$VmPolicy$Builder", "VM policy builder");
        CLASS_EN.put("android.os.SystemClock", "System clock");
        CLASS_EN.put("android.os.TestLooperManager", "Test looper manager");
        CLASS_EN.put("android.os.TokenWatcher", "Token watcher");
        CLASS_EN.put("android.os.Trace", "Performance trace");
        CLASS_EN.put("android.os.TransactionTooLargeException", "Transaction too large");
        CLASS_EN.put("android.os.UserHandle", "User handle");
        CLASS_EN.put("android.os.UserManager", "User manager");
        CLASS_EN.put("android.os.UserManager$UserOperationException", "User operation exception");
        CLASS_EN.put("android.os.VibrationAttributes", "Vibration attributes");
        CLASS_EN.put("android.os.VibrationAttributes$Builder", "Vibration attributes builder");
        CLASS_EN.put("android.os.VibrationEffect", "Vibration effect");
        CLASS_EN.put("android.os.VibrationEffect$BasicEnvelopeBuilder", "Basic envelope builder");
        CLASS_EN.put("android.os.VibrationEffect$Composition", "Vibration composition");
        CLASS_EN.put("android.os.VibrationEffect$WaveformEnvelopeBuilder", "Waveform envelope builder");
        CLASS_EN.put("android.os.Vibrator", "Vibrator");
        CLASS_EN.put("android.os.VibratorManager", "Vibrator manager");
        CLASS_EN.put("android.os.WorkDuration", "Work duration");
        CLASS_EN.put("android.os.WorkSource", "Power source");

        // ========== 类 → 日文说明 ==========
        CLASS_JA.put("android.os.AsyncTask", "非同期タスク");
        CLASS_JA.put("android.os.AsyncTask$Status", "非同期タスクの状態");
        CLASS_JA.put("android.os.BadParcelableException", "不正なParcelable例外");
        CLASS_JA.put("android.os.BaseBundle", "基本バンドル");
        CLASS_JA.put("android.os.BatteryManager", "バッテリーマネージャー");
        CLASS_JA.put("android.os.Binder", "バインダー（IPC）");
        CLASS_JA.put("android.os.BugreportManager", "バグレポートマネージャー");
        CLASS_JA.put("android.os.BugreportManager$BugreportCallback", "バグレポートコールバック");
        CLASS_JA.put("android.os.Build", "システムビルド情報");
        CLASS_JA.put("android.os.Build$Partition", "ビルドパーティション");
        CLASS_JA.put("android.os.Build$VERSION", "バージョン情報");
        CLASS_JA.put("android.os.Build$VERSION_CODES", "バージョンコード");
        CLASS_JA.put("android.os.Build$VERSION_CODES_FULL", "完全バージョンコード");
        CLASS_JA.put("android.os.Bundle", "バンドル（キー値コンテナ）");
        CLASS_JA.put("android.os.CancellationSignal", "キャンセルシグナル");
        CLASS_JA.put("android.os.CancellationSignal$OnCancelListener", "キャンセルリスナー");
        CLASS_JA.put("android.os.CombinedVibration", "複合振動");
        CLASS_JA.put("android.os.CombinedVibration$ParallelCombination", "並列振動コンビネーション");
        CLASS_JA.put("android.os.ConditionVariable", "条件変数");
        CLASS_JA.put("android.os.CountDownTimer", "カウントダウンタイマー");
        CLASS_JA.put("android.os.CpuHeadroomParams", "CPUヘッドルームパラメータ");
        CLASS_JA.put("android.os.CpuHeadroomParams$Builder", "CPUヘッドルームパラメータビルダー");
        CLASS_JA.put("android.os.CpuUsageInfo", "CPU使用情報");
        CLASS_JA.put("android.os.DeadObjectException", "デッドオブジェクト例外");
        CLASS_JA.put("android.os.DeadSystemException", "システムプロセス死亡例外");
        CLASS_JA.put("android.os.Debug", "デバッグユーティリティ");
        CLASS_JA.put("android.os.Debug$InstructionCount", "命令カウント");
        CLASS_JA.put("android.os.Debug$MemoryInfo", "メモリ情報");
        CLASS_JA.put("android.os.DropBoxManager", "システムログマネージャー");
        CLASS_JA.put("android.os.DropBoxManager$Entry", "ログエントリ");
        CLASS_JA.put("android.os.Environment", "ストレージ環境");
        CLASS_JA.put("android.os.FileObserver", "ファイルオブザーバー");
        CLASS_JA.put("android.os.FileUriExposedException", "ファイルURI公開例外");
        CLASS_JA.put("android.os.FileUtils", "ファイルユーティリティ");
        CLASS_JA.put("android.os.FileUtils$ProgressListener", "ファイル進捗リスナー");
        CLASS_JA.put("android.os.GpuHeadroomParams", "GPUヘッドルームパラメータ");
        CLASS_JA.put("android.os.GpuHeadroomParams$Builder", "GPUヘッドルームパラメータビルダー");
        CLASS_JA.put("android.os.Handler", "メッセージハンドラー");
        CLASS_JA.put("android.os.Handler$Callback", "メッセージハンドラーコールバック");
        CLASS_JA.put("android.os.HandlerThread", "ハンドラースレッド");
        CLASS_JA.put("android.os.HardwarePropertiesManager", "ハードウェアプロパティマネージャー");
        CLASS_JA.put("android.os.IBinder", "バインダーインターフェース");
        CLASS_JA.put("android.os.IBinder$DeathRecipient", "プロセス死亡通知");
        CLASS_JA.put("android.os.IBinder$FrozenStateChangeCallback", "フリーズ状態変更コールバック");
        CLASS_JA.put("android.os.IInterface", "サービスインターフェース");
        CLASS_JA.put("android.os.LimitExceededException", "上限超過例外");
        CLASS_JA.put("android.os.LocaleList", "ロケールリスト");
        CLASS_JA.put("android.os.Looper", "メッセージルーパー");
        CLASS_JA.put("android.os.MemoryFile", "共有メモリファイル");
        CLASS_JA.put("android.os.Message", "メッセージ");
        CLASS_JA.put("android.os.MessageQueue", "メッセージキュー");
        CLASS_JA.put("android.os.MessageQueue$IdleHandler", "アイドルハンドラー");
        CLASS_JA.put("android.os.MessageQueue$OnFileDescriptorEventListener", "ファイル記述子イベントリスナー");
        CLASS_JA.put("android.os.Messenger", "プロセス間メッセンジャー");
        CLASS_JA.put("android.os.NetworkOnMainThreadException", "メインスレッドネットワーク例外");
        CLASS_JA.put("android.os.OperationCanceledException", "操作キャンセル例外");
        CLASS_JA.put("android.os.OutcomeReceiver", "結果レシーバー");
        CLASS_JA.put("android.os.Parcel", "データパーセル");
        CLASS_JA.put("android.os.ParcelFileDescriptor", "ファイル記述子");
        CLASS_JA.put("android.os.ParcelFileDescriptor$AutoCloseInputStream", "自動クローズ入力ストリーム");
        CLASS_JA.put("android.os.ParcelFileDescriptor$AutoCloseOutputStream", "自動クローズ出力ストリーム");
        CLASS_JA.put("android.os.ParcelFileDescriptor$FileDescriptorDetachedException", "ファイル記述子分離例外");
        CLASS_JA.put("android.os.ParcelFileDescriptor$OnCloseListener", "クローズリスナー");
        CLASS_JA.put("android.os.ParcelFormatException", "パーセル形式例外");
        CLASS_JA.put("android.os.ParcelUuid", "パーセルUUID");
        CLASS_JA.put("android.os.Parcelable", "シリアライズ可能インターフェース");
        CLASS_JA.put("android.os.Parcelable$ClassLoaderCreator", "クラスローダークリエーター");
        CLASS_JA.put("android.os.Parcelable$Creator", "クリエーター");
        CLASS_JA.put("android.os.PatternMatcher", "パターンマッチャー");
        CLASS_JA.put("android.os.PerformanceHintManager", "パフォーマンスヒントマネージャー");
        CLASS_JA.put("android.os.PerformanceHintManager$Session", "パフォーマンスセッション");
        CLASS_JA.put("android.os.PersistableBundle", "永続バンドル");
        CLASS_JA.put("android.os.PowerManager", "パワーマネージャー");
        CLASS_JA.put("android.os.PowerManager$OnThermalHeadroomChangedListener", "熱ヘッドルーム変更リスナー");
        CLASS_JA.put("android.os.PowerManager$OnThermalStatusChangedListener", "熱状態変更リスナー");
        CLASS_JA.put("android.os.PowerManager$WakeLock", "ウェイクロック");
        CLASS_JA.put("android.os.PowerManager$WakeLockStateListener", "ウェイクロック状態リスナー");
        CLASS_JA.put("android.os.PowerMonitor", "パワーモニター");
        CLASS_JA.put("android.os.PowerMonitorReadings", "パワーモニター測定値");
        CLASS_JA.put("android.os.Process", "プロセス");
        CLASS_JA.put("android.os.ProfilingManager", "プロファイリングマネージャー");
        CLASS_JA.put("android.os.ProfilingResult", "プロファイリング結果");
        CLASS_JA.put("android.os.ProfilingTrigger", "プロファイリングトリガー");
        CLASS_JA.put("android.os.ProfilingTrigger$Builder", "プロファイリングトリガービルダー");
        CLASS_JA.put("android.os.ProxyFileDescriptorCallback", "プロキシファイル記述子コールバック");
        CLASS_JA.put("android.os.RecoverySystem", "リカバリシステム");
        CLASS_JA.put("android.os.RecoverySystem$ProgressListener", "リカバリ進捗リスナー");
        CLASS_JA.put("android.os.RemoteCallbackList", "リモートコールバックリスト");
        CLASS_JA.put("android.os.RemoteCallbackList$Builder", "リモートコールバックリストビルダー");
        CLASS_JA.put("android.os.RemoteCallbackList$Builder$InterfaceDiedCallback", "インターフェース死亡コールバック");
        CLASS_JA.put("android.os.RemoteException", "リモート例外");
        CLASS_JA.put("android.os.ResultReceiver", "結果レシーバー");
        CLASS_JA.put("android.os.SecurityStateManager", "セキュリティ状態マネージャー");
        CLASS_JA.put("android.os.SharedMemory", "共有メモリ");
        CLASS_JA.put("android.os.StatFs", "ファイルシステム状態");
        CLASS_JA.put("android.os.StrictMode", "ストリクトモード");
        CLASS_JA.put("android.os.StrictMode$OnThreadViolationListener", "スレッド違反リスナー");
        CLASS_JA.put("android.os.StrictMode$OnVmViolationListener", "VM違反リスナー");
        CLASS_JA.put("android.os.StrictMode$ThreadPolicy", "スレッドポリシー");
        CLASS_JA.put("android.os.StrictMode$ThreadPolicy$Builder", "スレッドポリシービルダー");
        CLASS_JA.put("android.os.StrictMode$VmPolicy", "VMポリシー");
        CLASS_JA.put("android.os.StrictMode$VmPolicy$Builder", "VMポリシービルダー");
        CLASS_JA.put("android.os.SystemClock", "システムクロック");
        CLASS_JA.put("android.os.TestLooperManager", "テストルーパーマネージャー");
        CLASS_JA.put("android.os.TokenWatcher", "トークンウォッチャー");
        CLASS_JA.put("android.os.Trace", "パフォーマンストレース");
        CLASS_JA.put("android.os.TransactionTooLargeException", "トランザクション過大例外");
        CLASS_JA.put("android.os.UserHandle", "ユーザーハンドル");
        CLASS_JA.put("android.os.UserManager", "ユーザーマネージャー");
        CLASS_JA.put("android.os.UserManager$UserOperationException", "ユーザー操作例外");
        CLASS_JA.put("android.os.VibrationAttributes", "振動属性");
        CLASS_JA.put("android.os.VibrationAttributes$Builder", "振動属性ビルダー");
        CLASS_JA.put("android.os.VibrationEffect", "振動効果");
        CLASS_JA.put("android.os.VibrationEffect$BasicEnvelopeBuilder", "基本エンベロープビルダー");
        CLASS_JA.put("android.os.VibrationEffect$Composition", "振動コンポジション");
        CLASS_JA.put("android.os.VibrationEffect$WaveformEnvelopeBuilder", "波形エンベロープビルダー");
        CLASS_JA.put("android.os.Vibrator", "バイブレーター");
        CLASS_JA.put("android.os.VibratorManager", "バイブレーターマネージャー");
        CLASS_JA.put("android.os.WorkDuration", "作業時間");
        CLASS_JA.put("android.os.WorkSource", "電力消費源");

        // ========== 字段特例 → 英文（只收录有附加价值的） ==========
        FIELD_SPECIAL_EN.put("RELEASE", "Release version");
        FIELD_SPECIAL_EN.put("INCREMENTAL", "Incremental version");
        FIELD_SPECIAL_EN.put("SDK_INT", "SDK version (int)");
        FIELD_SPECIAL_EN.put("SECURITY_PATCH", "Security patch level");
        FIELD_SPECIAL_EN.put("BASE_OS", "Base OS");
        FIELD_SPECIAL_EN.put("CODENAME", "Codename");
        FIELD_SPECIAL_EN.put("PREVIEW_SDK_INT", "Preview SDK version");
        FIELD_SPECIAL_EN.put("MEDIA_PERFORMANCE_CLASS", "Media performance class");
        FIELD_SPECIAL_EN.put("MIN_SUPPORTED_PLATFORM_VERSION", "Min supported platform");
        FIELD_SPECIAL_EN.put("DEVICE_INITIAL_SDK_INT", "Device initial SDK");
        FIELD_SPECIAL_EN.put("IS_DEBUGGABLE", "Is debuggable");
        FIELD_SPECIAL_EN.put("IS_EMULATOR", "Is emulator");
        FIELD_SPECIAL_EN.put("IS_TREBLE_ENABLED", "Treble enabled");
        FIELD_SPECIAL_EN.put("SUPPORTED_32_BIT_ABIS", "Supported 32-bit ABIs");
        FIELD_SPECIAL_EN.put("SUPPORTED_64_BIT_ABIS", "Supported 64-bit ABIs");
        FIELD_SPECIAL_EN.put("SUPPORTED_ABIS", "Supported ABIs");
        FIELD_SPECIAL_EN.put("CUPCAKE", "Android 1.5");
        FIELD_SPECIAL_EN.put("DONUT", "Android 1.6");
        FIELD_SPECIAL_EN.put("ECLAIR", "Android 2.0");
        FIELD_SPECIAL_EN.put("FROYO", "Android 2.2");
        FIELD_SPECIAL_EN.put("GINGERBREAD", "Android 2.3");
        FIELD_SPECIAL_EN.put("HONEYCOMB", "Android 3.0");
        FIELD_SPECIAL_EN.put("ICE_CREAM_SANDWICH", "Android 4.0");
        FIELD_SPECIAL_EN.put("JELLY_BEAN", "Android 4.1");
        FIELD_SPECIAL_EN.put("KITKAT", "Android 4.4");
        FIELD_SPECIAL_EN.put("KITKAT_WATCH", "Android 4.4W");
        FIELD_SPECIAL_EN.put("LOLLIPOP", "Android 5.0");
        FIELD_SPECIAL_EN.put("LOLLIPOP_MR1", "Android 5.1");
        FIELD_SPECIAL_EN.put("M", "Android 6.0");
        FIELD_SPECIAL_EN.put("N", "Android 7.0");
        FIELD_SPECIAL_EN.put("N_MR1", "Android 7.1");
        FIELD_SPECIAL_EN.put("O", "Android 8.0");
        FIELD_SPECIAL_EN.put("O_MR1", "Android 8.1");
        FIELD_SPECIAL_EN.put("P", "Android 9.0");
        FIELD_SPECIAL_EN.put("Q", "Android 10");
        FIELD_SPECIAL_EN.put("R", "Android 11");
        FIELD_SPECIAL_EN.put("S", "Android 12");
        FIELD_SPECIAL_EN.put("S_V2", "Android 12L");
        FIELD_SPECIAL_EN.put("TIRAMISU", "Android 13");
        FIELD_SPECIAL_EN.put("UPSIDE_DOWN_CAKE", "Android 14");
        FIELD_SPECIAL_EN.put("VANILLA_ICE_CREAM", "Android 15");
        FIELD_SPECIAL_EN.put("CUR_DEVELOPMENT", "Current development");
        FIELD_SPECIAL_EN.put("MY_PID", "My process ID");
        FIELD_SPECIAL_EN.put("MY_TID", "My thread ID");
        FIELD_SPECIAL_EN.put("MY_UID", "My user ID");
        FIELD_SPECIAL_EN.put("BLOCK_SIZE", "Block size (bytes)");
        FIELD_SPECIAL_EN.put("BLOCK_COUNT", "Block count");
        FIELD_SPECIAL_EN.put("FREE_BLOCKS", "Free blocks");
        FIELD_SPECIAL_EN.put("AVAILABLE_BLOCKS", "Available blocks");
        FIELD_SPECIAL_EN.put("FREE_BYTES", "Free bytes");
        FIELD_SPECIAL_EN.put("AVAILABLE_BYTES", "Available bytes");
        FIELD_SPECIAL_EN.put("TOTAL_BYTES", "Total bytes");

        // ========== 字段特例 → 日文 ==========
        FIELD_SPECIAL_JA.put("RELEASE", "リリースバージョン");
        FIELD_SPECIAL_JA.put("INCREMENTAL", "増分バージョン");
        FIELD_SPECIAL_JA.put("SDK_INT", "SDKバージョン（整数）");
        FIELD_SPECIAL_JA.put("SECURITY_PATCH", "セキュリティパッチレベル");
        FIELD_SPECIAL_JA.put("BASE_OS", "ベースOS");
        FIELD_SPECIAL_JA.put("CODENAME", "コードネーム");
        FIELD_SPECIAL_JA.put("PREVIEW_SDK_INT", "プレビューSDKバージョン");
        FIELD_SPECIAL_JA.put("MEDIA_PERFORMANCE_CLASS", "メディアパフォーマンスクラス");
        FIELD_SPECIAL_JA.put("MIN_SUPPORTED_PLATFORM_VERSION", "最小サポートプラットフォーム");
        FIELD_SPECIAL_JA.put("DEVICE_INITIAL_SDK_INT", "デバイス初期SDK");
        FIELD_SPECIAL_JA.put("BOARD", "ボード");
        FIELD_SPECIAL_JA.put("BOOTLOADER", "ブートローダーバージョン");
        FIELD_SPECIAL_JA.put("BRAND", "ブランド");
        FIELD_SPECIAL_JA.put("CPU_ABI", "CPU ABI（旧）");
        FIELD_SPECIAL_JA.put("CPU_ABI2", "CPU ABI 2（旧）");
        FIELD_SPECIAL_JA.put("DEVICE", "デバイス名");
        FIELD_SPECIAL_JA.put("DISPLAY", "表示バージョン");
        FIELD_SPECIAL_JA.put("FINGERPRINT", "ビルドフィンガープリント");
        FIELD_SPECIAL_JA.put("HARDWARE", "ハードウェア");
        FIELD_SPECIAL_JA.put("HOST", "ビルドホスト");
        FIELD_SPECIAL_JA.put("ID", "ビルドID");
        FIELD_SPECIAL_JA.put("IS_DEBUGGABLE", "デバッグ可能か");
        FIELD_SPECIAL_JA.put("IS_EMULATOR", "エミュレーターか");
        FIELD_SPECIAL_JA.put("IS_TREBLE_ENABLED", "Treble対応");
        FIELD_SPECIAL_JA.put("MANUFACTURER", "メーカー");
        FIELD_SPECIAL_JA.put("MODEL", "機種名");
        FIELD_SPECIAL_JA.put("PRODUCT", "製品名");
        FIELD_SPECIAL_JA.put("RADIO", "無線版（旧）");
        FIELD_SPECIAL_JA.put("SERIAL", "シリアル番号（旧）");
        FIELD_SPECIAL_JA.put("SUPPORTED_32_BIT_ABIS", "対応32ビットABI");
        FIELD_SPECIAL_JA.put("SUPPORTED_64_BIT_ABIS", "対応64ビットABI");
        FIELD_SPECIAL_JA.put("SUPPORTED_ABIS", "対応ABI一覧");
        FIELD_SPECIAL_JA.put("TAGS", "ビルドタグ");
        FIELD_SPECIAL_JA.put("TIME", "ビルド時間");
        FIELD_SPECIAL_JA.put("TYPE", "ビルドタイプ");
        FIELD_SPECIAL_JA.put("UNKNOWN", "不明");
        FIELD_SPECIAL_JA.put("USER", "ビルドユーザー");
        FIELD_SPECIAL_JA.put("CUPCAKE", "Android 1.5 カップケーキ");
        FIELD_SPECIAL_JA.put("DONUT", "Android 1.6 ドーナツ");
        FIELD_SPECIAL_JA.put("ECLAIR", "Android 2.0 エクレア");
        FIELD_SPECIAL_JA.put("FROYO", "Android 2.2 フローズンヨーグルト");
        FIELD_SPECIAL_JA.put("GINGERBREAD", "Android 2.3 ジンジャーブレッド");
        FIELD_SPECIAL_JA.put("HONEYCOMB", "Android 3.0 ハニカム");
        FIELD_SPECIAL_JA.put("ICE_CREAM_SANDWICH", "Android 4.0 アイスクリームサンドイッチ");
        FIELD_SPECIAL_JA.put("JELLY_BEAN", "Android 4.1 ジェリービーン");
        FIELD_SPECIAL_JA.put("KITKAT", "Android 4.4 キットカット");
        FIELD_SPECIAL_JA.put("KITKAT_WATCH", "Android 4.4W ウォッチ");
        FIELD_SPECIAL_JA.put("LOLLIPOP", "Android 5.0 ロリポップ");
        FIELD_SPECIAL_JA.put("LOLLIPOP_MR1", "Android 5.1 ロリポップ");
        FIELD_SPECIAL_JA.put("M", "Android 6.0 マシュマロ");
        FIELD_SPECIAL_JA.put("N", "Android 7.0 ヌガー");
        FIELD_SPECIAL_JA.put("N_MR1", "Android 7.1 ヌガー");
        FIELD_SPECIAL_JA.put("O", "Android 8.0 オレオ");
        FIELD_SPECIAL_JA.put("O_MR1", "Android 8.1 オレオ");
        FIELD_SPECIAL_JA.put("P", "Android 9.0 パイ");
        FIELD_SPECIAL_JA.put("Q", "Android 10");
        FIELD_SPECIAL_JA.put("R", "Android 11");
        FIELD_SPECIAL_JA.put("S", "Android 12");
        FIELD_SPECIAL_JA.put("S_V2", "Android 12L");
        FIELD_SPECIAL_JA.put("TIRAMISU", "Android 13 ティラミス");
        FIELD_SPECIAL_JA.put("UPSIDE_DOWN_CAKE", "Android 14 アップサイドダウンケーキ");
        FIELD_SPECIAL_JA.put("VANILLA_ICE_CREAM", "Android 15 バニラアイスクリーム");
        FIELD_SPECIAL_JA.put("CUR_DEVELOPMENT", "現在の開発版");
        FIELD_SPECIAL_JA.put("MY_PID", "自分のプロセスID");
        FIELD_SPECIAL_JA.put("MY_TID", "自分のスレッドID");
        FIELD_SPECIAL_JA.put("MY_UID", "自分のユーザーID");
        FIELD_SPECIAL_JA.put("BLOCK_SIZE", "ブロックサイズ（バイト）");
        FIELD_SPECIAL_JA.put("BLOCK_COUNT", "ブロック数");
        FIELD_SPECIAL_JA.put("FREE_BLOCKS", "空きブロック数");
        FIELD_SPECIAL_JA.put("AVAILABLE_BLOCKS", "利用可能ブロック数");
        FIELD_SPECIAL_JA.put("FREE_BYTES", "空きバイト数");
        FIELD_SPECIAL_JA.put("AVAILABLE_BYTES", "利用可能バイト数");
        FIELD_SPECIAL_JA.put("TOTAL_BYTES", "総バイト数");

        // ========== 分词词典 → 英文 ==========
        FIELD_WORDS_EN.put("SDK", "SDK"); FIELD_WORDS_EN.put("VERSION", "version");
        FIELD_WORDS_EN.put("SECURITY", "security"); FIELD_WORDS_EN.put("PATCH", "patch");
        FIELD_WORDS_EN.put("BASE", "base"); FIELD_WORDS_EN.put("OS", "OS");
        FIELD_WORDS_EN.put("CODENAME", "codename"); FIELD_WORDS_EN.put("PREVIEW", "preview");
        FIELD_WORDS_EN.put("INT", "int"); FIELD_WORDS_EN.put("MEDIA", "media");
        FIELD_WORDS_EN.put("PERFORMANCE", "performance"); FIELD_WORDS_EN.put("CLASS", "class");
        FIELD_WORDS_EN.put("MIN", "min"); FIELD_WORDS_EN.put("SUPPORTED", "supported");
        FIELD_WORDS_EN.put("PLATFORM", "platform"); FIELD_WORDS_EN.put("DEVICE", "device");
        FIELD_WORDS_EN.put("INITIAL", "initial"); FIELD_WORDS_EN.put("BOOTLOADER", "bootloader");
        FIELD_WORDS_EN.put("BRAND", "brand"); FIELD_WORDS_EN.put("CPU", "CPU");
        FIELD_WORDS_EN.put("ABI", "ABI"); FIELD_WORDS_EN.put("ABIS", "ABIs");
        FIELD_WORDS_EN.put("FINGERPRINT", "fingerprint"); FIELD_WORDS_EN.put("HARDWARE", "hardware");
        FIELD_WORDS_EN.put("HOST", "host"); FIELD_WORDS_EN.put("MANUFACTURER", "manufacturer");
        FIELD_WORDS_EN.put("MODEL", "model"); FIELD_WORDS_EN.put("PRODUCT", "product");
        FIELD_WORDS_EN.put("RADIO", "radio"); FIELD_WORDS_EN.put("SERIAL", "serial");
        FIELD_WORDS_EN.put("TAGS", "tags"); FIELD_WORDS_EN.put("TIME", "time");
        FIELD_WORDS_EN.put("TYPE", "type"); FIELD_WORDS_EN.put("USER", "user");
        FIELD_WORDS_EN.put("UNKNOWN", "unknown"); FIELD_WORDS_EN.put("DEBUGGABLE", "debuggable");
        FIELD_WORDS_EN.put("EMULATOR", "emulator"); FIELD_WORDS_EN.put("TREBLE", "Treble");
        FIELD_WORDS_EN.put("ENABLED", "enabled"); FIELD_WORDS_EN.put("BOARD", "board");
        FIELD_WORDS_EN.put("DISPLAY", "display"); FIELD_WORDS_EN.put("IS", "is");
        FIELD_WORDS_EN.put("CUR", "current"); FIELD_WORDS_EN.put("DEVELOPMENT", "development");
        FIELD_WORDS_EN.put("BLOCK", "block"); FIELD_WORDS_EN.put("SIZE", "size");
        FIELD_WORDS_EN.put("COUNT", "count"); FIELD_WORDS_EN.put("FREE", "free");
        FIELD_WORDS_EN.put("AVAILABLE", "available"); FIELD_WORDS_EN.put("BYTES", "bytes");
        FIELD_WORDS_EN.put("TOTAL", "total"); FIELD_WORDS_EN.put("MY", "my");
        FIELD_WORDS_EN.put("PID", "process ID"); FIELD_WORDS_EN.put("TID", "thread ID");
        FIELD_WORDS_EN.put("UID", "user ID"); FIELD_WORDS_EN.put("SYSTEM", "system");
        FIELD_WORDS_EN.put("PHONE", "phone"); FIELD_WORDS_EN.put("APP", "app");
        FIELD_WORDS_EN.put("ROOT", "root"); FIELD_WORDS_EN.put("SHELL", "shell");
        FIELD_WORDS_EN.put("DIRECTORY", "directory"); FIELD_WORDS_EN.put("DOWNLOADS", "downloads");
        FIELD_WORDS_EN.put("PICTURES", "pictures"); FIELD_WORDS_EN.put("MOVIES", "movies");
        FIELD_WORDS_EN.put("MUSIC", "music"); FIELD_WORDS_EN.put("DOCUMENTS", "documents");
        FIELD_WORDS_EN.put("DCIM", "camera photos"); FIELD_WORDS_EN.put("ALARMS", "alarms");
        FIELD_WORDS_EN.put("NOTIFICATIONS", "notifications"); FIELD_WORDS_EN.put("PODCASTS", "podcasts");
        FIELD_WORDS_EN.put("RINGTONES", "ringtones"); FIELD_WORDS_EN.put("DATA", "data");
        FIELD_WORDS_EN.put("EXTERNAL", "external"); FIELD_WORDS_EN.put("INTERNAL", "internal");
        FIELD_WORDS_EN.put("STORAGE", "storage"); FIELD_WORDS_EN.put("CACHE", "cache");
        FIELD_WORDS_EN.put("OBB", "OBB"); FIELD_WORDS_EN.put("TRACE", "trace");
        FIELD_WORDS_EN.put("TAG", "tag"); FIELD_WORDS_EN.put("ACTIVITY", "activity");
        FIELD_WORDS_EN.put("GRAPHICS", "graphics"); FIELD_WORDS_EN.put("INPUT", "input");
        FIELD_WORDS_EN.put("VIEW", "view"); FIELD_WORDS_EN.put("WEBVIEW", "webview");
        FIELD_WORDS_EN.put("WINDOW", "window"); FIELD_WORDS_EN.put("AUDIO", "audio");
        FIELD_WORDS_EN.put("VIDEO", "video"); FIELD_WORDS_EN.put("CAMERA", "camera");
        FIELD_WORDS_EN.put("HAL", "HAL"); FIELD_WORDS_EN.put("MEMORY", "memory");
        FIELD_WORDS_EN.put("BATTERY", "battery"); FIELD_WORDS_EN.put("PROPERTY", "property");
        FIELD_WORDS_EN.put("CAPACITY", "capacity"); FIELD_WORDS_EN.put("CHARGING", "charging");
        FIELD_WORDS_EN.put("ENERGY", "energy"); FIELD_WORDS_EN.put("COUNTER", "counter");
        FIELD_WORDS_EN.put("CURRENT", "current"); FIELD_WORDS_EN.put("VOLTAGE", "voltage");
        FIELD_WORDS_EN.put("TEMPERATURE", "temperature"); FIELD_WORDS_EN.put("STATUS", "status");
        FIELD_WORDS_EN.put("HEALTH", "health"); FIELD_WORDS_EN.put("LEVEL", "level");
        FIELD_WORDS_EN.put("SCALE", "scale"); FIELD_WORDS_EN.put("TECHNOLOGY", "technology");
        FIELD_WORDS_EN.put("CHARGE", "charge"); FIELD_WORDS_EN.put("PLUGGED", "plugged");
        FIELD_WORDS_EN.put("FLAG", "flag"); FIELD_WORDS_EN.put("PERMISSION", "permission");
        FIELD_WORDS_EN.put("READ", "read"); FIELD_WORDS_EN.put("WRITE", "write");
        FIELD_WORDS_EN.put("INTERNET", "internet"); FIELD_WORDS_EN.put("WAKE", "wake");
        FIELD_WORDS_EN.put("LOCK", "lock"); FIELD_WORDS_EN.put("PARTIAL", "partial");
        FIELD_WORDS_EN.put("FULL", "full"); FIELD_WORDS_EN.put("SCREEN", "screen");
        FIELD_WORDS_EN.put("BRIGHT", "bright"); FIELD_WORDS_EN.put("DIM", "dim");
        FIELD_WORDS_EN.put("ON", "on"); FIELD_WORDS_EN.put("OFF", "off");
        FIELD_WORDS_EN.put("AFTER", "after"); FIELD_WORDS_EN.put("LONG", "long");
        FIELD_WORDS_EN.put("SHORT", "short"); FIELD_WORDS_EN.put("VIBRATE", "vibrate");
        FIELD_WORDS_EN.put("PROXIMITY", "proximity"); FIELD_WORDS_EN.put("WAIT", "wait");
        FIELD_WORDS_EN.put("KEEP", "keep"); FIELD_WORDS_EN.put("TIMEOUT", "timeout");
        FIELD_WORDS_EN.put("HEADROOM", "headroom"); FIELD_WORDS_EN.put("PARAMS", "params");
        FIELD_WORDS_EN.put("USAGE", "usage"); FIELD_WORDS_EN.put("INFO", "info");
        FIELD_WORDS_EN.put("READINGS", "readings"); FIELD_WORDS_EN.put("MONITOR", "monitor");
        FIELD_WORDS_EN.put("WORK", "work"); FIELD_WORDS_EN.put("DURATION", "duration");
        FIELD_WORDS_EN.put("SOURCE", "source"); FIELD_WORDS_EN.put("RESULT", "result");
        FIELD_WORDS_EN.put("DEFAULT", "default"); FIELD_WORDS_EN.put("LIMIT", "limit");
        FIELD_WORDS_EN.put("MAX", "max"); FIELD_WORDS_EN.put("MINIMUM", "minimum");

        // ========== 分词词典 → 日文 ==========
        FIELD_WORDS_JA.put("SDK", "SDK"); FIELD_WORDS_JA.put("VERSION", "バージョン");
        FIELD_WORDS_JA.put("SECURITY", "セキュリティ"); FIELD_WORDS_JA.put("PATCH", "パッチ");
        FIELD_WORDS_JA.put("BASE", "ベース"); FIELD_WORDS_JA.put("OS", "OS");
        FIELD_WORDS_JA.put("CODENAME", "コードネーム"); FIELD_WORDS_JA.put("PREVIEW", "プレビュー");
        FIELD_WORDS_JA.put("INT", "整数"); FIELD_WORDS_JA.put("MEDIA", "メディア");
        FIELD_WORDS_JA.put("PERFORMANCE", "パフォーマンス"); FIELD_WORDS_JA.put("CLASS", "クラス");
        FIELD_WORDS_JA.put("MIN", "最小"); FIELD_WORDS_JA.put("SUPPORTED", "対応");
        FIELD_WORDS_JA.put("PLATFORM", "プラットフォーム"); FIELD_WORDS_JA.put("DEVICE", "デバイス");
        FIELD_WORDS_JA.put("INITIAL", "初期"); FIELD_WORDS_JA.put("BOOTLOADER", "ブートローダー");
        FIELD_WORDS_JA.put("BRAND", "ブランド"); FIELD_WORDS_JA.put("CPU", "CPU");
        FIELD_WORDS_JA.put("ABI", "ABI"); FIELD_WORDS_JA.put("ABIS", "ABI");
        FIELD_WORDS_JA.put("FINGERPRINT", "フィンガープリント"); FIELD_WORDS_JA.put("HARDWARE", "ハードウェア");
        FIELD_WORDS_JA.put("HOST", "ホスト"); FIELD_WORDS_JA.put("MANUFACTURER", "メーカー");
        FIELD_WORDS_JA.put("MODEL", "機種"); FIELD_WORDS_JA.put("PRODUCT", "製品");
        FIELD_WORDS_JA.put("RADIO", "無線"); FIELD_WORDS_JA.put("SERIAL", "シリアル");
        FIELD_WORDS_JA.put("TAGS", "タグ"); FIELD_WORDS_JA.put("TIME", "時間");
        FIELD_WORDS_JA.put("TYPE", "タイプ"); FIELD_WORDS_JA.put("USER", "ユーザー");
        FIELD_WORDS_JA.put("UNKNOWN", "不明"); FIELD_WORDS_JA.put("DEBUGGABLE", "デバッグ可能");
        FIELD_WORDS_JA.put("EMULATOR", "エミュレーター"); FIELD_WORDS_JA.put("TREBLE", "Treble");
        FIELD_WORDS_JA.put("ENABLED", "有効"); FIELD_WORDS_JA.put("BOARD", "ボード");
        FIELD_WORDS_JA.put("DISPLAY", "表示"); FIELD_WORDS_JA.put("IS", "か");
        FIELD_WORDS_JA.put("CUR", "現在"); FIELD_WORDS_JA.put("DEVELOPMENT", "開発");
        FIELD_WORDS_JA.put("BLOCK", "ブロック"); FIELD_WORDS_JA.put("SIZE", "サイズ");
        FIELD_WORDS_JA.put("COUNT", "数"); FIELD_WORDS_JA.put("FREE", "空き");
        FIELD_WORDS_JA.put("AVAILABLE", "利用可能"); FIELD_WORDS_JA.put("BYTES", "バイト");
        FIELD_WORDS_JA.put("TOTAL", "総"); FIELD_WORDS_JA.put("MY", "自分の");
        FIELD_WORDS_JA.put("PID", "プロセスID"); FIELD_WORDS_JA.put("TID", "スレッドID");
        FIELD_WORDS_JA.put("UID", "ユーザーID"); FIELD_WORDS_JA.put("SYSTEM", "システム");
        FIELD_WORDS_JA.put("PHONE", "電話"); FIELD_WORDS_JA.put("APP", "アプリ");
        FIELD_WORDS_JA.put("ROOT", "ルート"); FIELD_WORDS_JA.put("SHELL", "シェル");
        FIELD_WORDS_JA.put("DIRECTORY", "ディレクトリ"); FIELD_WORDS_JA.put("DOWNLOADS", "ダウンロード");
        FIELD_WORDS_JA.put("PICTURES", "画像"); FIELD_WORDS_JA.put("MOVIES", "動画");
        FIELD_WORDS_JA.put("MUSIC", "音楽"); FIELD_WORDS_JA.put("DOCUMENTS", "ドキュメント");
        FIELD_WORDS_JA.put("DCIM", "カメラ写真"); FIELD_WORDS_JA.put("ALARMS", "アラーム");
        FIELD_WORDS_JA.put("NOTIFICATIONS", "通知"); FIELD_WORDS_JA.put("PODCASTS", "ポッドキャスト");
        FIELD_WORDS_JA.put("RINGTONES", "着信音"); FIELD_WORDS_JA.put("DATA", "データ");
        FIELD_WORDS_JA.put("EXTERNAL", "外部"); FIELD_WORDS_JA.put("INTERNAL", "内部");
        FIELD_WORDS_JA.put("STORAGE", "ストレージ"); FIELD_WORDS_JA.put("CACHE", "キャッシュ");
        FIELD_WORDS_JA.put("OBB", "OBB"); FIELD_WORDS_JA.put("TRACE", "トレース");
        FIELD_WORDS_JA.put("TAG", "タグ"); FIELD_WORDS_JA.put("ACTIVITY", "アクティビティ");
        FIELD_WORDS_JA.put("GRAPHICS", "グラフィックス"); FIELD_WORDS_JA.put("INPUT", "入力");
        FIELD_WORDS_JA.put("VIEW", "ビュー"); FIELD_WORDS_JA.put("WEBVIEW", "ウェブビュー");
        FIELD_WORDS_JA.put("WINDOW", "ウィンドウ"); FIELD_WORDS_JA.put("AUDIO", "オーディオ");
        FIELD_WORDS_JA.put("VIDEO", "ビデオ"); FIELD_WORDS_JA.put("CAMERA", "カメラ");
        FIELD_WORDS_JA.put("HAL", "HAL"); FIELD_WORDS_JA.put("MEMORY", "メモリ");
        FIELD_WORDS_JA.put("BATTERY", "バッテリー"); FIELD_WORDS_JA.put("PROPERTY", "プロパティ");
        FIELD_WORDS_JA.put("CAPACITY", "容量"); FIELD_WORDS_JA.put("CHARGING", "充電中");
        FIELD_WORDS_JA.put("ENERGY", "エネルギー"); FIELD_WORDS_JA.put("COUNTER", "カウンター");
        FIELD_WORDS_JA.put("CURRENT", "電流"); FIELD_WORDS_JA.put("VOLTAGE", "電圧");
        FIELD_WORDS_JA.put("TEMPERATURE", "温度"); FIELD_WORDS_JA.put("STATUS", "状態");
        FIELD_WORDS_JA.put("HEALTH", "ヘルス"); FIELD_WORDS_JA.put("LEVEL", "レベル");
        FIELD_WORDS_JA.put("SCALE", "スケール"); FIELD_WORDS_JA.put("TECHNOLOGY", "技術");
        FIELD_WORDS_JA.put("CHARGE", "充電"); FIELD_WORDS_JA.put("PLUGGED", "接続中");
        FIELD_WORDS_JA.put("FLAG", "フラグ"); FIELD_WORDS_JA.put("PERMISSION", "権限");
        FIELD_WORDS_JA.put("READ", "読み取り"); FIELD_WORDS_JA.put("WRITE", "書き込み");
        FIELD_WORDS_JA.put("INTERNET", "ネットワーク"); FIELD_WORDS_JA.put("WAKE", "ウェイク");
        FIELD_WORDS_JA.put("LOCK", "ロック"); FIELD_WORDS_JA.put("PARTIAL", "部分");
        FIELD_WORDS_JA.put("FULL", "完全"); FIELD_WORDS_JA.put("SCREEN", "画面");
        FIELD_WORDS_JA.put("BRIGHT", "明るく"); FIELD_WORDS_JA.put("DIM", "暗く");
        FIELD_WORDS_JA.put("ON", "オン"); FIELD_WORDS_JA.put("OFF", "オフ");
        FIELD_WORDS_JA.put("AFTER", "後"); FIELD_WORDS_JA.put("LONG", "長い");
        FIELD_WORDS_JA.put("SHORT", "短い"); FIELD_WORDS_JA.put("VIBRATE", "振動");
        FIELD_WORDS_JA.put("PROXIMITY", "近接"); FIELD_WORDS_JA.put("WAIT", "待機");
        FIELD_WORDS_JA.put("KEEP", "維持"); FIELD_WORDS_JA.put("TIMEOUT", "タイムアウト");
        FIELD_WORDS_JA.put("HEADROOM", "ヘッドルーム"); FIELD_WORDS_JA.put("PARAMS", "パラメータ");
        FIELD_WORDS_JA.put("USAGE", "使用量"); FIELD_WORDS_JA.put("INFO", "情報");
        FIELD_WORDS_JA.put("READINGS", "測定値"); FIELD_WORDS_JA.put("MONITOR", "モニター");
        FIELD_WORDS_JA.put("WORK", "作業"); FIELD_WORDS_JA.put("DURATION", "時間");
        FIELD_WORDS_JA.put("SOURCE", "ソース"); FIELD_WORDS_JA.put("RESULT", "結果");
        FIELD_WORDS_JA.put("DEFAULT", "デフォルト"); FIELD_WORDS_JA.put("LIMIT", "制限");
        FIELD_WORDS_JA.put("MAX", "最大"); FIELD_WORDS_JA.put("MINIMUM", "最小");
    }

    private ZhNames() {}

    // ---- 多语言支持：根据当前 UI 语言决定中文输出 ----------------

    /** 语言偏好键（与 MainActivity 的 LocaleManager 共用） */
    private static final String PREFS_LOCALE = "locale_prefs";
    private static final String KEY_LOCALE = "locale";

    /** 当前是否为简体中文 UI；默认（跟随系统）时按系统语言判断 */
    private static volatile boolean mIsZhCN = true;

    /**
     * 由 Application 初始化：根据当前生效的语言设置语言模式。
     * @param context 任意 Context
     */
    public static void init(Context context) {
        String locale = context.getSharedPreferences(PREFS_LOCALE, Context.MODE_PRIVATE)
                .getString(KEY_LOCALE, "");
        if (locale == null || locale.isEmpty()) {
            // 跟随系统：按系统语言判断
            String lang = Locale.getDefault().getLanguage();
            mIsZhCN = "zh".equals(lang);
        } else {
            mIsZhCN = "zh-rCN".equals(locale) || "zh".equals(locale);
        }
    }

    /** 当前生效的 UI 语言标签；优先取应用级语言（AppCompat），否则系统默认 */
    private static String effectiveLangTag() {
        try {
            androidx.core.os.LocaleListCompat list =
                    androidx.appcompat.app.AppCompatDelegate.getApplicationLocales();
            if (list != null && !list.isEmpty() && list.get(0) != null) {
                return list.get(0).toLanguageTag();
            }
        } catch (Throwable ignored) {
        }
        return Locale.getDefault().toLanguageTag();
    }

    /** 是否应显示中文标注（简体 / 繁体） */
    public static boolean isChineseUi() {
        String tag = effectiveLangTag();
        return tag != null && tag.startsWith("zh");
    }

    /** 当前 UI 是否为繁体中文 */
    private static boolean isTraditionalUi() {
        String tag = effectiveLangTag();
        return tag != null && (tag.contains("TW") || tag.contains("HK")
                || tag.contains("MO") || tag.startsWith("zh-Hant"));
    }

    /** 简体 → 繁体转换表（覆盖词表与类名翻译中出现的中文字符） */
    private static final String S2T_SRC =
            "产仅从价优体储充先儿兑内写决冻结出分划别创制务动区单压参发取变台号名后含听启告命品唤商器回图块型基增声处备复外大失奇奥奶媒存安完定导封屏属嵌工巧已带常幕并序应广开异式当录影待循态性总恢户所技把报拆拉拟持指按振据接描提插播操支收效数整文无旧时明显暗暴更有服术机权条来松构析果柄查标根档模步死段池注测济浏浑温点热版牌特状环理电画监盖目相短础硬确示离称程空窗签管类精系级纸纹组绑结络统缓网翻耗能脱节花苏草语误读调豆象距跟跨踪轨载输过远违运进选适递部配酸醒钟铃锁错键长闭闲间队限集露音页预频颠风驻验高";
    private static final String S2T_DST =
            "產僅從價優體儲充先兒兌內寫決凍結出分劃別創製務動區單壓參發取變臺號名後含聽啟告命品喚商器回圖塊型基增聲處備複外大失奇奧奶媒存安完定導封屏屬嵌工巧已帶常幕並序應廣開異式當錄影待循態性總恢戶所技把報拆拉擬持指按振據接描提插播操支收效數整文無舊時明顯暗暴更有服術機權條來鬆構析果柄查標根檔模步死段池註測濟瀏渾溫點熱版牌特狀環理電畫監蓋目相短礎硬確示離稱程空窗籤管類精係級紙紋組綁結絡統緩網翻耗能脫節花蘇草語誤讀調豆象距跟跨蹤軌載輸過遠違運進選適遞部配酸醒鐘鈴鎖錯鍵長閉閒間隊限集露音頁預頻顛風駐驗高";

    /** 把简体中文转换为繁体（未覆盖的字原样返回） */
    private static String toTraditional(String s) {
        if (s == null) return null;
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int idx = S2T_SRC.indexOf(c);
            sb.append(idx >= 0 ? S2T_DST.charAt(idx) : c);
        }
        return sb.toString();
    }

    /** 根据当前语言格式化名称：返回对应语言的翻译（无翻译时返回 null） */
    private static String localized(String zh, String en, String ja) {
        if (isJapaneseUi()) {
            return ja != null ? ja : zh;   // 日文优先，回退中文
        }
        if (isChineseUi()) {
            if (zh == null) return null;
            return isTraditionalUi() ? toTraditional(zh) : zh;
        }
        // 英文（默认）
        return en;
    }

    /** 当前 UI 是否为日语 */
    private static boolean isJapaneseUi() {
        String tag = effectiveLangTag();
        return tag != null && tag.startsWith("ja");
    }

    /** 类的中文名（按当前语言返回对应翻译；无翻译返回 null） */
    public static String classZh(String className) {
        return localized(CLASS_MAP.get(className), CLASS_EN.get(className), CLASS_JA.get(className));
    }

    /** 嵌套类中文名（按名称拆词组合）；无翻译返回 null */
    public static String nestedZh(String shortName) {
        return composeLocalized(shortName);
    }

    /** 字段中文名（特例优先，未命中拆词组合）；无翻译返回 null */
    public static String fieldZh(String fieldName) {
        return fieldLocalized(fieldName);
    }

    /** 把 CamelCase 拆词并按当前语言组合翻译，如 WakeLock → 唤醒锁 / Wake lock / ウェイクロック */
    private static String composeLocalized(String name) {
        if (name == null) return null;
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

        if (isJapaneseUi()) {
            StringBuilder sb = new StringBuilder();
            int hit = 0;
            for (String p : parts) {
                if (p.isEmpty()) continue;
                String w = FIELD_WORDS_JA.get(p);
                if (w != null) { sb.append(w); hit++; } else { sb.append(p); }
            }
            return hit == 0 ? null : sb.toString();
        }
        if (isChineseUi()) {
            StringBuilder sb = new StringBuilder();
            int hit = 0;
            for (String p : parts) {
                if (p.isEmpty()) continue;
                String w = FIELD_WORDS.get(p);
                if (w != null) { sb.append(w); hit++; } else { sb.append(p); }
            }
            String zh = hit == 0 ? null : sb.toString();
            if (zh == null) return null;
            return isTraditionalUi() ? toTraditional(zh) : zh;
        }
        // 英文：按原词保留（英文界面下英文标注无附加价值，返回 null）
        return null;
    }

    /** 字段中文名（按语言特例表 + 分词词典）；无翻译返回 null */
    private static String fieldLocalized(String fieldName) {
        if (fieldName == null) return null;
        String zh = FIELD_SPECIAL.get(fieldName);
        if (zh != null) {
            if (isJapaneseUi()) return FIELD_SPECIAL_JA.getOrDefault(fieldName, zh);
            if (isChineseUi()) return isTraditionalUi() ? toTraditional(zh) : zh;
            return FIELD_SPECIAL_EN.get(fieldName);   // 英文
        }

        String[] parts = fieldName.split("_");
        if (isJapaneseUi()) {
            StringBuilder sb = new StringBuilder();
            int hit = 0;
            for (String p : parts) {
                if (p.isEmpty()) continue;
                String w = FIELD_WORDS_JA.get(p);
                if (w != null) { sb.append(w); hit++; } else { sb.append(p); }
            }
            return hit == 0 ? null : sb.toString();
        }
        if (isChineseUi()) {
            StringBuilder sb = new StringBuilder();
            int hit = 0;
            for (String p : parts) {
                if (p.isEmpty()) continue;
                String w = FIELD_WORDS.get(p);
                if (w != null) { sb.append(w); hit++; } else { sb.append(p); }
            }
            String r = hit == 0 ? null : sb.toString();
            if (r == null) return null;
            return isTraditionalUi() ? toTraditional(r) : r;
        }
        // 英文
        StringBuilder sb = new StringBuilder();
        int hit = 0;
        for (String p : parts) {
            if (p.isEmpty()) continue;
            String w = FIELD_WORDS_EN.get(p);
            if (w != null) { sb.append(w).append(' '); hit++; }
        }
        if (hit == 0) return null;
        String s = sb.toString().trim();
        // 英文标注（首字母大写）
        if (!s.isEmpty()) {
            s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
        return s;
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
