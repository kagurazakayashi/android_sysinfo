# System Info Constants

**Language**: English | [简体中文](README.zh-CN.md) | [繁體中文](README.zh-TW.md) | [日本語](README.ja.md)

System Info Constants is an Android app that lets you browse every class in the `android.os` package of your device. It shows the name, type and current value of every public static field at runtime, and lets you navigate nested classes as deeply as you want.

This project was created to help you understand what is actually stored in the system — from the Android version and device model to battery, storage, power and messaging classes.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Building from Source](#building-from-source)
- [Privacy](#privacy)
- [License](#license)

## Introduction

The `android.os` package is one of the most important packages in Android. It contains system-level classes such as `Build` (device information), `BatteryManager`, `PowerManager`, `Process`, `Environment`, `StatFs` and many more.

This app lists all top-level classes in `android.os` (120 classes on SDK 37), and for each class it shows:

- The kind of type (class / interface / enum / annotation), its modifiers, package, superclass and interfaces
- Every public static field with its **current value** read from your real device
- Nested classes / interfaces, which you can tap to open and explore further

Each entry also shows an explanation text in your language (Simplified Chinese, Traditional Chinese, English or Japanese), plus a semantic icon.

## Features

- Browse all 120 top-level classes in the `android.os` package
- View every public static field with its real, current value at runtime
- Tap nested classes to dive deeper, at any depth
- Per-entry explanation in 4 languages, with a semantic icon
- Favorites page: star any field and open it later from the toolbar
- Global filter switch: hide entries that have neither static fields nor nested classes
- Tap any field to choose what to copy (raw name / translated name / value / full entry)
- "Copy all" button in the toolbar
- 4 UI languages (Simplified Chinese, Traditional Chinese, English, Japanese) with an in-app switcher; follows the system language by default
- Dark mode support (follows the system)
- Dynamic color on Android 12+ (follows the wallpaper theme)

## Requirements

- Android 8.0 (API level 26) or higher

## Installation

### Option 1: Install the APK directly (easiest)

1. Get the APK file from `app/build/outputs/apk/debug/app-debug.apk` (or ask the author for a release APK).
2. Copy the APK file to your phone (via USB cable, a file manager, a chat app, etc.).
3. Tap the APK file on your phone to open the installer.
4. If Android asks you to allow installing apps from unknown sources, allow it and continue.
5. Wait for the installation to finish, then open the app.

### Option 2: Install with ADB (from a computer)

1. On your phone, enable **Developer options**:
   - Open Settings, then "About phone".
   - Tap "Build number" 7 times until "You are now a developer!" appears.
2. Enable **USB debugging**:
   - Settings > System > Developer options > turn on "USB debugging".
3. Connect your phone to your computer with a USB cable and accept the debugging prompt on the phone.
4. On the computer, run:

   ```
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

5. The app will appear in your app list.

## Usage

### Home page (list of classes)

- The home page shows all top-level classes of `android.os`, each with an icon and a translated description.
- **Tap a class** to open its detail page.
- The **subtitle** shows "shown / total" (for example "120 / 120 classes").
- The **switch** at the top hides entries that have neither static fields nor nested classes. The switch applies to every level of the app.

### Detail page (class contents)

- Shows type information (kind, modifiers, package, superclass, interfaces).
- **Static fields** are listed with their current values.
  - **Tap a field** to open a menu and choose what to copy: raw name, translated name, value, or the full entry. You can also add / remove it from Favorites from the same menu.
- **Nested classes / interfaces** are listed below the fields.
  - **Tap a nested class** to open its own detail page. You can keep going deeper forever.

### Favorites page

- Open it with the star button in the home toolbar.
- Shows every field you have favorited, with the value captured at the time you favorited it.
- Tap a favorite to copy it or to remove it from Favorites.
- On first launch, four common fields are pre-added: manufacturer, model, Android version and API level.

### Toolbar buttons on the home page

- Language button: switch UI language (follow system / Simplified Chinese / Traditional Chinese / English / Japanese).
- Star button: open Favorites.
- Copy button: copy the whole class list.

## Building from Source

### With Android Studio (recommended for beginners)

1. Install [Android Studio](https://developer.android.com/studio).
2. Open Android Studio, choose "Open", and select this project folder.
3. Wait for Gradle to sync (this downloads dependencies automatically; it may take a few minutes the first time).
4. To run the app on a device: connect your phone (with USB debugging enabled), click the green "Run" button, choose your device.
5. To build an APK only: click **Build > Build Bundle(s) / APK(s) > Build APK(s)**. The APK will be at `app/build/outputs/apk/debug/app-debug.apk`.

### With the command line

Requirements:

- JDK 17 or newer
- Android SDK with `platforms;android-37` and `build-tools` (AGP 9.4.0 requires recent build tools)
- Gradle wrapper is included in the repository, so you do not need to install Gradle itself

Steps:

1. Tell Gradle where the Android SDK is. Either set an environment variable:

   ```
   export ANDROID_HOME=/path/to/your/android-sdk
   ```

   or create a file named `local.properties` in the project root with:

   ```
   sdk.dir=/path/to/your/android-sdk
   ```

2. Build the debug APK:

   ```
   ./gradlew assembleDebug
   ```

   (On Windows with Git Bash / PowerShell, use `.\gradlew.bat assembleDebug`.)

3. The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Privacy

This app is designed with privacy in mind:

- **No permissions requested.** The app does not declare or request any Android permission.
- **Fully offline.** The app contains no network code at all — it never connects to the internet, never sends data to any server, and needs no network permission.
- **Local data only.** Everything the app stores (your favorites, UI language, display filter) is kept in the app's private storage on your device (SharedPreferences). Nothing is ever uploaded anywhere.
- **Reads system information only.** The values shown (device model, Android version, battery state, etc.) are read locally from your device's `android.os` classes and displayed on your screen — they never leave the device.
- **No ads, no tracking, no analytics.** The app contains no third-party SDKs, ads or usage statistics.
- All local data is deleted automatically when you uninstall the app.

## Project Structure

```
app/src/main/java/com/example/buildinfo/
├── MainActivity.java          Home page: list of classes in android.os
├── ClassDetailActivity.java   Detail page: fields and nested classes of a class
├── FavoritesActivity.java     Favorites page
├── OsClasses.java             Generated list of android.os classes (SDK 37)
├── OsInfo.java                Reflection utilities for reading fields
├── ZhNames.java               Name translations for classes / fields (4 languages)
├── Icons.java                 Icon assignment for classes and fields
├── FavoritesStore.java        Favorites persistence (SharedPreferences + JSON)
├── LocaleManager.java         UI language management
├── Ui.java                    Shared UI helpers (colors, rounded backgrounds)
└── App.java                   Application class (applies saved language)
```

## License

This project is licensed under the [Mulan Permissive Software License, Version 2](http://license.coscl.org.cn/MulanPSL2) (Mulan PSL v2).

Copyright (c) 2024 KagurazakaYashi (KagurazakaMiyabi)

See the [LICENSE](LICENSE) file for the full license text (available in both Chinese and English).
