# 系统信息常量

**语言**: [English](README.md) | 简体中文 | [繁體中文](README.zh-TW.md) | [日本語](README.ja.md)

系统信息常量是一款 Android 应用，用于浏览设备 `android.os` 包中的所有类。它会在运行时读取每个公开静态字段的名称、类型和**当前值**，并支持无限层级地深入浏览嵌套类。

本项目旨在帮助你了解系统里到底存放了什么——从 Android 版本、设备型号，到电池、存储、电源、消息等各类系统常量。

## 目录

- [介绍](#介绍)
- [功能特性](#功能特性)
- [运行要求](#运行要求)
- [安装方式](#安装方式)
- [使用说明](#使用说明)
- [编译方式](#编译方式)
- [许可协议](#许可协议)

## 介绍

`android.os` 是 Android 最重要的包之一，包含系统级类，如 `Build`（设备信息）、`BatteryManager`（电池）、`PowerManager`（电源）、`Process`（进程）、`Environment`（存储环境）、`StatFs`（文件系统状态）等等。

本应用列出 `android.os` 下的全部顶层类（SDK 37 下共 120 个），并为每个类展示：

- 类型种类（类 / 接口 / 枚举 / 注解）、修饰符、所属包、父类、实现的接口
- 每个公开静态字段及其在**真实设备上读取到的当前值**
- 嵌套类 / 接口，点按即可进入并继续探索

每个条目还附带当前语言的解释文字（简体中文、繁体中文、英语或日语）和语义图标。

## 功能特性

- 浏览 `android.os` 包全部 120 个顶层类
- 查看每个公开静态字段的实时当前值
- 点按嵌套类可无限深入
- 每个条目带 4 种语言的解释和语义图标
- 收藏夹：收藏任意字段，随时从顶栏打开
- 全局过滤开关：隐藏既无静态字段也无嵌套类的空条目
- 点按字段可自由选择复制内容（原始名称 / 翻译名 / 值 / 完整条目）
- 顶栏"复制全部"按钮
- 4 种界面语言（简体中文、繁体中文、英语、日语），应用内一键切换，默认跟随系统
- 暗色模式（跟随系统）
- Android 12+ 动态取色（跟随壁纸主题）

## 运行要求

- Android 8.0（API 26）及以上版本

## 安装方式

### 方式一：直接安装 APK（最简单）

1. 获取 APK 文件：位于 `app/build/outputs/apk/debug/app-debug.apk`（或向作者索要发布版 APK）。
2. 把 APK 文件传到手机（数据线、文件管理器、聊天软件等均可）。
3. 在手机上点按该 APK 文件，打开安装器。
4. 若系统提示允许"安装未知来源应用"，请允许后继续。
5. 等待安装完成，打开应用即可。

### 方式二：用 ADB 从电脑安装

1. 在手机上开启**开发者选项**：
   - 打开"设置"，进入"关于手机"。
   - 连续点按"版本号"7 次，直到出现"您已进入开发者模式"。
2. 开启**USB 调试**：
   - 设置 > 系统 > 开发者选项 > 打开"USB 调试"。
3. 用数据线连接手机与电脑，并在手机上允许调试授权。
4. 在电脑上执行：

   ```
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

5. 应用会出现在你的应用列表中。

## 使用说明

### 主页（类列表）

- 主页展示 `android.os` 的全部顶层类，每个类带图标和翻译说明。
- **点按类**进入其详情页。
- 副标题显示"显示 / 总计"（如"120 / 120 个类"）。
- 顶部**开关**用于隐藏既无静态字段也无嵌套类的条目，该开关对应用所有层级生效。

### 详情页（类的内容）

- 展示类型信息（种类、修饰符、包、父类、接口）。
- **静态字段**列出并附当前值。
  - **点按字段**弹出菜单，选择要复制的内容：原始名称、翻译名、值或完整条目；同一菜单也可收藏 / 取消收藏。
- 字段下方是**嵌套类 / 接口**。
  - **点按嵌套类**进入其详情页，可以一直深入下去。

### 收藏夹页面

- 通过主页顶栏的星形按钮打开。
- 展示所有收藏的字段及其收藏时的值。
- 点按收藏项可复制内容或取消收藏。
- 首次启动时预置 4 个常用字段：制造商、型号、Android 版本号、API 版本号。

### 主页顶栏按钮

- 语言按钮：切换界面语言（跟随系统 / 简体中文 / 繁體中文 / English / 日本語）。
- 星形按钮：打开收藏夹。
- 复制按钮：复制整个类列表。

## 编译方式

### 使用 Android Studio（新手推荐）

1. 安装 [Android Studio](https://developer.android.com/studio)。
2. 打开 Android Studio，选择"Open"，选中本项目文件夹。
3. 等待 Gradle 同步完成（会自动下载依赖，首次可能耗时几分钟）。
4. 在设备上运行：连接手机（开启 USB 调试），点击绿色"运行"按钮，选择你的设备。
5. 仅构建 APK：点击 **Build > Build Bundle(s) / APK(s) > Build APK(s)**，APK 生成于 `app/build/outputs/apk/debug/app-debug.apk`。

### 使用命令行

前置要求：

- JDK 17 或更高版本
- Android SDK（包含 `platforms;android-37` 和较新的 `build-tools`，AGP 9.4.0 要求较新版本）
- 仓库已包含 Gradle wrapper，无需单独安装 Gradle

步骤：

1. 告诉 Gradle Android SDK 的位置。可以设置环境变量：

   ```
   export ANDROID_HOME=/你的/android-sdk路径
   ```

   或在项目根目录创建 `local.properties` 文件，写入：

   ```
   sdk.dir=/你的/android-sdk路径
   ```

2. 构建调试版 APK：

   ```
   ./gradlew assembleDebug
   ```

   （Windows 上使用 Git Bash / PowerShell 时，请用 `.\gradlew.bat assembleDebug`。）

3. APK 生成于 `app/build/outputs/apk/debug/app-debug.apk`。

## 项目结构

```
app/src/main/java/com/example/buildinfo/
├── MainActivity.java          主页：android.os 类列表
├── ClassDetailActivity.java   详情页：类的字段与嵌套类
├── FavoritesActivity.java     收藏夹页面
├── OsClasses.java             生成的 android.os 类清单（SDK 37）
├── OsInfo.java                反射读取字段的工具类
├── ZhNames.java               类 / 字段名称翻译（4 种语言）
├── Icons.java                 类与字段的图标分配
├── FavoritesStore.java        收藏持久化（SharedPreferences + JSON）
├── LocaleManager.java         界面语言管理
├── Ui.java                    界面辅助（颜色、圆角背景）
└── App.java                   Application 类（应用已保存的语言）
```

## 许可协议

本项目采用[木兰公共许可证，第2版](http://license.coscl.org.cn/MulanPSL2)（Mulan PSL v2）授权。

Copyright (c) 2024 KagurazakaYashi (KagurazakaMiyabi)

完整的许可文本见 [LICENSE](LICENSE) 文件（同时包含中文与英文文本）。
