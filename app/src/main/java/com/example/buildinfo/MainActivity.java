package com.example.buildinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根界面：android.os 包的全部顶层类列表。
 * 点击任意类进入其详情（静态字段 + 嵌套类，可继续逐级深入）。
 */
public class MainActivity extends Activity {

    private LinearLayout mContainer;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("android.os 包信息");
        toolbar.setSubtitle("共 " + OsClasses.TOP_LEVEL.length + " 个类 · 点击查看该类信息");

        mContainer = findViewById(R.id.container);

        // 后台线程逐个统计各类的字段数 / 嵌套类数（避免主线程卡顿）
        final AtomicInteger done = new AtomicInteger(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String[] names = OsClasses.TOP_LEVEL;
                final int[] fieldCounts = new int[names.length];
                final int[] nestedCounts = new int[names.length];
                for (int i = 0; i < names.length; i++) {
                    Class<?> cls = OsInfo.loadClass(names[i]);
                    if (cls != null) {
                        fieldCounts[i] = OsInfo.countStaticFields(cls);
                        nestedCounts[i] = cls.getClasses().length;
                    } else {
                        fieldCounts[i] = -1;
                    }
                    done.incrementAndGet();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < names.length; i++) {
                            mContainer.addView(createClassItem(names[i], fieldCounts[i], nestedCounts[i]));
                        }
                    }
                });
            }
        }).start();
    }

    /** 生成一个类条目（可点击进入详情） */
    private View createClassItem(final String className, int fieldCount, int nestedCount) {
        String shortName = className.substring(className.lastIndexOf('.') + 1);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(14), dp(10), dp(14), dp(10));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(4), 0, dp(4));
        card.setLayoutParams(lp);
        card.setBackgroundResource(R.drawable.card_bg);
        card.setElevation(dp(1));

        TypedValue ripple = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.selectableItemBackground, ripple, true);
        card.setForeground(getDrawable(ripple.resourceId));
        card.setClickable(true);

        TextView title = new TextView(this);
        title.setText(shortName);
        title.setTextSize(16);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(getColor(R.color.text_primary));

        TextView sub = new TextView(this);
        if (fieldCount >= 0) {
            sub.setText(className + "  ·  " + fieldCount + " 个静态字段 · " + nestedCount + " 个嵌套类");
        } else {
            sub.setText(className + "  ·  无法加载");
        }
        sub.setTextSize(12);
        sub.setTypeface(Typeface.MONOSPACE);
        sub.setTextColor(getColor(R.color.text_sub));
        sub.setPadding(0, dp(3), 0, 0);

        card.addView(title);
        card.addView(sub);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClassDetailActivity.class);
                intent.putExtra(ClassDetailActivity.EXTRA_CLASS, className);
                startActivity(intent);
            }
        });
        return card;
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
