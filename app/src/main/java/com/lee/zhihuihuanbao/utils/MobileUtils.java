package com.lee.zhihuihuanbao.utils;

import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme  手机类
 */

public class MobileUtils {

    /**
     * 判断当前字符串是否为手机号码
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        Pattern p = null;
        Matcher m = null;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");  // 手机号码正则表达式
        m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 提示输入手机号 or 验证码
     * @param editText
     * @param i [1]手机号 , [2]验证码
     */
    public static void showEditTextHint(EditText editText, int i) {
        if (i == 1) {
            if (editText.getText().length() == 0) {
                editText.setHint("请输入手机号码");
            } else {
                editText.setText("");
                editText.setHint("请输入正确的手机号码");
            }
            editText.setHintTextColor(Color.RED);
        } else if (i == 2) {
            editText.setText("");
            editText.setHint("请输入验证码");
            editText.setHintTextColor(Color.RED);
        } else if(i == 3){
            editText.setText("");
            editText.setHint("请输入密码");
            editText.setHintTextColor(Color.RED);

        }
    }

    /**
     * 没同意协议之前不能点击
     *
     * @param isClick
     */
    public static void setEnabled(Button button, boolean isClick) {
        if (isClick) {
            button.setEnabled(true);
            button.setBackgroundColor(Color.parseColor("#ffce3d3a"));
        } else {
            button.setEnabled(false);
            button.setBackgroundColor(Color.rgb(200, 200, 200));
        }

    }

    /**
     * 提示用户输入字段
     * @param editText
     */
    public static void showMaterialHint(EditText editText){
        editText.setHintTextColor(Color.RED);
        editText.requestFocus();
    }


}
