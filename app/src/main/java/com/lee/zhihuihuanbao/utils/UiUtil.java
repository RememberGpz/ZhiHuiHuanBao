package com.lee.zhihuihuanbao.utils;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * Created by hjx on 0016 5-16.
 * You can make it better
 */

public class UiUtil {

    /**
     * 获取指定dp
     * @param context
     * @param value dp值
     * @return
     */
    public static float getDp(Context context, int value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.getResources().getDisplayMetrics());
    }


    public static String[] getStringArray(Context context, int stringArray) {
        return context.getResources().getStringArray(stringArray);
    }


    public static void accessActivity(Context context, Class cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);

    }

    /**
     * 找到界面中的button和imagebutton设置点击事件
     *
     * @param view
     * @param listener
     */
    public static void findButtonAndSetOnClickListener(View view, View.OnClickListener listener) {
        if (view instanceof ViewGroup) {
            ViewGroup rootView = (ViewGroup) view;
            int childCount = rootView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = rootView.getChildAt(i);
                if (childView instanceof Button || childView instanceof ImageButton) {
                    childView.setOnClickListener(listener);
                } else {
                    findButtonAndSetOnClickListener(childView, listener);
                }

            }
        }
    }


    public static String getText(Object t){
        if(t instanceof EditText){
            return ((EditText)t).getText().toString().trim();
        }else if(t instanceof TextView){
            return ((TextView)t).getText().toString().trim();
        }

        return null;
    }

    public static String int2String(int count) {
        return String.valueOf(count);
    }
}
