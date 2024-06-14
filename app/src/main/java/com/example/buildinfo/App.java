package com.example.buildinfo;

import android.app.Application;
import android.content.Context;

/**
 * Application：启动时应用保存的语言并初始化 ZhNames。
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 应用保存的语言（启动时调用一次）
        LocaleManager.apply(this);
        ZhNames.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 提前应用语言（让资源在最早阶段就使用正确语言）
        LocaleManager.apply(base);
        ZhNames.init(base);
    }
}
