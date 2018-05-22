package com.lee.zhihuihuanbao.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme   6.0（sdk23）以后申请动态权限 （静态授权 + 动态申请）
 */

public class DynamicPermissionsUtils {

    /*(1) ContextCompat.checkSelfPermission，主要用于检测某个权限是否已经被授予，方法返回值为
         PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了。*/
    /*  (2) ActivityCompat.requestPermissions，该方法是异步的，第一个参数是Context；
        第二个参数是需要申请的权限的字符串数组；第三个参数为requestCode，主要用于回调的时候检测。
        可以从方法名requestPermissions以及第二个参数看出，是支持一次性申请多个权限的，
         系统会通过对话框逐一询问用户是否授权。*/


    /**
     *
     * @param activity  上下文环境
     * @param permissionList  需要动态申请的权限数组（支持一次性申请多个）
     * @param requestCode   主要用于回调的时候检测
     */
    // 读取手机通讯录
    public static void getDynamicPermissions(Activity activity, String[] permissionList, int requestCode){

        for(int i =0; i<permissionList.length; i++){

            if (ContextCompat.checkSelfPermission(activity,permissionList[i])
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        permissionList,
                        requestCode);
            }
        }

    }

}
