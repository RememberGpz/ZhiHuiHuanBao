package com.lee.zhihuihuanbao.utils;

import android.widget.Toast;

import com.lee.zhihuihuanbao.app.MyApp;

/**
 * @Author Lee
 * @Time 2017/9/5
 * @Theme  单例Toast
 */

public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApp.getInstance(), text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }
}
