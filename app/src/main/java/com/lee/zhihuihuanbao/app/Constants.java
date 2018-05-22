package com.lee.zhihuihuanbao.app;

import android.text.TextUtils;

/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme  常量池
 */

public class Constants {


    public static final String NIGHT_SKIN = "night.skin";
    public static final String KEY_MODE_NIGHT = "mode-night";

    public static final String SP_INFORMATION = "sp";
    public static final String SP_TOKEN = "spToken";
    public static final String SP_MEMBERID = "spMemberId";
    public static final String SP_MOBILE = "mobile";

    public static final String FORGET_PWD = "forgetPwd";
    public static final String UPDATE_PWD = "updatePwd";
    public static final String REGISTER = "register";

    public static final String USERBEAN = "userinfo";

    public static final String TACKPHOTOPATH = android.os.Environment.getExternalStorageDirectory()
            + "/zhonghangxin/tackphoto/";


     /* ------------  以下是 消息推送的参数   --------------  */

    public static final String TIME = "TIME";   // 消息推送时间
    public static final String TITLE = "TITLE";   // 课程提醒类型
    public static final String CONTENT = "CONTENT";  // 内容
    public static final String URL = "URL";  // 链接
    public static final String ID = "ID";   //  消息ID号（方便 标志为 已读）
    public static final String TYPE = "TYPE";  // 页面跳转 （1： 近期面授详情 2：会员申请）
    public static final String COURSEID = "COURSEID";


    public static  String mToken ;
    public static  String mMemberId ;
    public static  String mMobile ;


    public static boolean hasLoggedIn() {  // 判断是否已经登录
        return !TextUtils.isEmpty(mToken) && !TextUtils.isEmpty(mMemberId);
    }

}
