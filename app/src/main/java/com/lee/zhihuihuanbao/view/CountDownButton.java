package com.lee.zhihuihuanbao.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义倒计时 Button
 * Created by 飘渺云轩 on 2017/6/26.
 */

public class CountDownButton extends AppCompatButton implements View.OnClickListener {

    private static final long SECOND = 60 * 1000;  // 倒计时，默认 90 秒
    private long length = SECOND;
    private Timer timer;   // 执行计时的类
    private TimerTask timerTask;  // 任务类
    private String beforeText = "获取验证码";
    private String afterText = "倒计时";
    private OnClickListener onClickListener;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CountDownButton.this.setText(afterText + "(" + length / 1000 + "s)");
            length -= 1000;
            if (length < 0) {
                clearTimer();
            }
        }
    };

    public CountDownButton(Context context) {
        this(context, null);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        if (!TextUtils.isEmpty(getText())) {
            beforeText = getText().toString().trim();
        }
        this.setText(beforeText);
        this.setTextColor(Color.DKGRAY);
        this.setBackgroundColor(Color.WHITE);
        setOnClickListener(this);
    }

    /**
     * 初始化时间
     */
    private void initTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };
    }

    /**
     * 设置倒计时时长
     *
     * @param length
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * 设置未点击前显示的文字
     *
     * @param beforeText
     */
    public void setBeforeText(String beforeText) {
        this.beforeText = beforeText;
    }

    /**
     * 设置点击后显示的文字
     *
     * @param afterText
     */
    public void setAfterText(String afterText) {
        this.afterText = afterText;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        if (onClickListener instanceof CountDownButton) {
            super.setOnClickListener(onClickListener);
        } else {
            this.onClickListener = onClickListener;
        }
    }

    /**
     * 开启倒计时
     */
    public void start() {
        initTimer();
        this.setText(afterText + "(" + length() / 1000 + "s)");
        this.setTextColor(Color.GRAY);
        this.setBackgroundColor(Color.rgb(241, 241, 241));
        this.setEnabled(false);
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 清除倒计时
     */
    public void clearTimer() {
        this.setEnabled(true);
        this.setText("重新" + beforeText);
        this.setTextColor(Color.DKGRAY);
        this.setBackgroundColor(Color.WHITE);
        length = SECOND;
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onClick(View view) {
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    /**
     * 记得一定要在 Activity / Fragment 销毁时清楚倒计时，否则会内存溢出
     */
    @Override
    protected void onDetachedFromWindow() {
        clearTimer();
        super.onDetachedFromWindow();
    }


}
