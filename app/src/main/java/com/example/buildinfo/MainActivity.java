package com.example.buildinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        // 顶部栏返回按钮（主界面：点击退出应用；使用系统主题的标准 Material 返回箭头）
        TypedValue navArrow = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.homeAsUpIndicator, navArrow, true)) {
            toolbar.setNavigationIcon(navArrow.resourceId);
        }
        toolbar.setNavigationContentDescription("返回");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
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

        // 左侧：标题 + 副标题
        LinearLayout left = new LinearLayout(this);
        left.setOrientation(LinearLayout.VERTICAL);
        left.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        TextView title = new TextView(this);
        title.setText(buildTitle(shortName, ZhNames.classZh(className)));
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

        left.addView(title);
        left.addView(sub);

        // 右侧：进入箭头
        ImageView arrow = new ImageView(this);
        arrow.setImageResource(R.drawable.ic_chevron_right);
        arrow.setColorFilter(getColor(R.color.text_sub));
        LinearLayout.LayoutParams arrowLp = new LinearLayout.LayoutParams(dp(24), dp(24));
        arrowLp.setMargins(dp(8), 0, 0, 0);
        arrow.setLayoutParams(arrowLp);

        card.addView(left);
        card.addView(arrow);
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

    /** 主标题：英文名 + 中文名（中文用强调色显示） */
    private CharSequence buildTitle(String en, String zh) {
        if (zh == null || zh.isEmpty()) return en;
        String text = en + "  " + zh;
        SpannableString ss = new SpannableString(text);
        ss.setSpan(new ForegroundColorSpan(getColor(R.color.accent)),
                en.length() + 2, text.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }
}
