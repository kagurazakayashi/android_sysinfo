# 系統資訊常數

**語言**: [English](README.md) | [简体中文](README.zh-CN.md) | 繁體中文 | [日本語](README.ja.md)

系統資訊常數是一款 Android 應用程式，用於瀏覽裝置 `android.os` 套件中的所有類別。它會在執行時期讀取每個公開靜態欄位的名稱、型別與**目前值**，並支援無限層級地深入瀏覽巢狀類別。

本專案旨在幫助你了解系統裡究竟存放了什麼——從 Android 版本、裝置型號，到電池、儲存、電源、訊息等各類系統常數。

## 目錄

- [介紹](#介紹)
- [功能特性](#功能特性)
- [執行需求](#執行需求)
- [安裝方式](#安裝方式)
- [使用說明](#使用說明)
- [編譯方式](#編譯方式)
- [授權條款](#授權條款)

## 介紹

`android.os` 是 Android 最重要的套件之一，包含系統層級類別，例如 `Build`（裝置資訊）、`BatteryManager`（電池）、`PowerManager`（電源）、`Process`（程序）、`Environment`（儲存環境）、`StatFs`（檔案系統狀態）等等。

本應用程式列出 `android.os` 下的全部頂層類別（SDK 37 下共 120 個），並為每個類別顯示：

- 型別種類（類別 / 介面 / 列舉 / 註解）、修飾詞、所屬套件、父類別、實作的介面
- 每個公開靜態欄位及其在**真實裝置上讀取到的目前值**
- 巢狀類別 / 介面，點按即可進入並繼續探索

每個條目還附帶目前語言的說明文字（繁體中文、簡體中文、英文或日文）與語意圖示。

## 功能特性

- 瀏覽 `android.os` 套件全部 120 個頂層類別
- 檢視每個公開靜態欄位的即時目前值
- 點按巢狀類別可無限深入
- 每個條目帶 4 種語言的說明與語意圖示
- 我的最愛：收藏任意欄位，隨時從頂端工具列開啟
- 全域過濾開關：隱藏既無靜態欄位也無巢狀類別的空條目
- 點按欄位可自由選擇複製內容（原始名稱 / 翻譯名稱 / 值 / 完整條目）
- 頂端工具列「複製全部」按鈕
- 4 種介面語言（繁體中文、簡體中文、英文、日文），應用程式內一鍵切換，預設跟隨系統
- 深色模式（跟隨系統）
- Android 12+ 動態取色（跟隨桌布主題）

## 執行需求

- Android 8.0（API 26）及以上版本

## 安裝方式

### 方式一：直接安裝 APK（最簡單）

1. 取得 APK 檔案：位於 `app/build/outputs/apk/debug/app-debug.apk`（或向作者索取發行版 APK）。
2. 將 APK 檔案傳到手機（傳輸線、檔案管理員、即時通訊軟體等均可）。
3. 在手機上點按該 APK 檔案，開啟安裝程式。
4. 若系統提示允許安裝「不明來源應用程式」，請允許後繼續。
5. 等待安裝完成，開啟應用程式即可。

### 方式二：用 ADB 從電腦安裝

1. 在手機上開啟**開發人員選項**：
   - 開啟「設定」，進入「關於手機」。
   - 連續點按「版本號碼」7 次，直到出現「您已進入開發人員模式」。
2. 開啟**USB 偵錯**：
   - 設定 > 系統 > 開發人員選項 > 開啟「USB 偵錯」。
3. 用傳輸線連接手機與電腦，並在手機上允許偵錯授權。
4. 在電腦上執行：

   ```
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

5. 應用程式會出現在你的應用程式清單中。

## 使用說明

### 首頁（類別清單）

- 首頁顯示 `android.os` 的全部頂層類別，每個類別帶圖示與翻譯說明。
- **點按類別**進入其詳細頁面。
- 副標題顯示「顯示 / 總計」（例如「120 / 120 個類別」）。
- 頂部**開關**用於隱藏既無靜態欄位也無巢狀類別的條目，該開關對應用程式所有層級生效。

### 詳細頁面（類別的內容）

- 顯示型別資訊（種類、修飾詞、套件、父類別、介面）。
- **靜態欄位**列出並附目前值。
  - **點按欄位**跳出選單，選擇要複製的內容：原始名稱、翻譯名稱、值或完整條目；同一選單也可收藏 / 取消收藏。
- 欄位下方是**巢狀類別 / 介面**。
  - **點按巢狀類別**進入其詳細頁面，可以一直深入下去。

### 我的最愛頁面

- 透過首頁頂端工具列的星形按鈕開啟。
- 顯示所有收藏的欄位及其收藏時的值。
- 點按收藏項目可複製內容或取消收藏。
- 首次啟動時預置 4 個常用欄位：製造商、型號、Android 版本號、API 版本號。

### 首頁頂端工具列按鈕

- 語言按鈕：切換介面語言（跟隨系統 / 简体中文 / 繁體中文 / English / 日本語）。
- 星形按鈕：開啟我的最愛。
- 複製按鈕：複製整個類別清單。

## 編譯方式

### 使用 Android Studio（新手推薦）

1. 安裝 [Android Studio](https://developer.android.com/studio)。
2. 開啟 Android Studio，選擇「Open」，選取本專案資料夾。
3. 等待 Gradle 同步完成（會自動下載依賴，首次可能耗時數分鐘）。
4. 在裝置上執行：連接手機（開啟 USB 偵錯），點選綠色「Run」按鈕，選擇你的裝置。
5. 僅建置 APK：點選 **Build > Build Bundle(s) / APK(s) > Build APK(s)**，APK 產生於 `app/build/outputs/apk/debug/app-debug.apk`。

### 使用命令列

前置需求：

- JDK 17 或更高版本
- Android SDK（包含 `platforms;android-37` 與較新的 `build-tools`，AGP 9.4.0 要求較新版本）
- 儲存庫已包含 Gradle wrapper，無需單獨安裝 Gradle

步驟：

1. 告訴 Gradle Android SDK 的位置。可以設定環境變數：

   ```
   export ANDROID_HOME=/你的/android-sdk路徑
   ```

   或在專案根目錄建立 `local.properties` 檔案，寫入：

   ```
   sdk.dir=/你的/android-sdk路徑
   ```

2. 建置偵錯版 APK：

   ```
   ./gradlew assembleDebug
   ```

   （Windows 上使用 Git Bash / PowerShell 時，請使用 `.\gradlew.bat assembleDebug`。）

3. APK 產生於 `app/build/outputs/apk/debug/app-debug.apk`。

## 專案結構

```
app/src/main/java/com/example/buildinfo/
├── MainActivity.java          首頁：android.os 類別清單
├── ClassDetailActivity.java   詳細頁：類別的欄位與巢狀類別
├── FavoritesActivity.java     我的最愛頁面
├── OsClasses.java             產生的 android.os 類別清單（SDK 37）
├── OsInfo.java                反射讀取欄位的工具類別
├── ZhNames.java               類別 / 欄位名稱翻譯（4 種語言）
├── Icons.java                 類別與欄位的圖示分配
├── FavoritesStore.java        收藏持久化（SharedPreferences + JSON）
├── LocaleManager.java         介面語言管理
├── Ui.java                    介面輔助（顏色、圓角背景）
└── App.java                   Application 類別（套用已儲存的語言）
```

## 授權條款

本專案採用[木蘭公共授權條款，第2版](http://license.coscl.org.cn/MulanPSL2)（Mulan PSL v2）授權。

Copyright (c) 2024 KagurazakaYashi (KagurazakaMiyabi)

完整的授權文字見 [LICENSE](LICENSE) 檔案（同時包含中文與英文文字）。
