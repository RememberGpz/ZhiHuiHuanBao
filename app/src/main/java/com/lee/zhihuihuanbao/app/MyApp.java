package com.lee.zhihuihuanbao.app;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.StrictMode;

import com.lee.zhihuihuanbao.UserBean;
import com.umeng.socialize.PlatformConfig;

import java.util.List;
import cn.jpush.android.api.JPushInterface;

/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme  全局配置文件
 */


public class MyApp extends android.app.Application {

    private static MyApp myApp;

    private static MyApp instance;
    private List<Activity> allActivities;
    private UserBean user;

    public static MyApp getInstance() {
        return myApp;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;

        initTextSize();

        // 初始化JPUSH（极光推送）
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        // (1) 初始化bugly
      //  CrashReport.initCrashReport(getApplicationContext(), "c54abcabea", true);
//      CrashReport.testJavaCrash();

        // (2) 初始化友盟sdk
      /*  UMShareAPI.get(this);
        Config.DEBUG = true;*/


        // (3) 在程序入口配置正确的ID号和秘钥（换成项目的id号和secrect秘钥）
        PlatformConfig.setWeixin("wxb87e5855284ef62b", "9f8becb864b1d4470b2189375c54348f");  //微信分享的ID号和秘钥需要用对应应用包申请随影配置信息
        PlatformConfig.setQQZone("1106319170", "0GOzLADimt6flQkU");


        // 在Android7.1.1下选择图片闪退，
        /*从Android 7.0开始，一个应用提供自身文件给其它应用使用时，如果给出一个file://格式的URI的话，
          应用会抛出FileUriExposedException。
          而相册选择器恰恰是这样的机制所以会抛出FileUriExposedException*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

    }

    /**
     * 使其系统更改字体大小无效
     */
    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }


    // 设置个人全部信息
    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {

        if(user != null){
            Constants.mMemberId = user.getResult().getId() + "";
            Constants.mToken = user.getResult().getToken();
        }else{
            Constants.mMemberId = "";
            Constants.mToken = "";
        }
    }


}
