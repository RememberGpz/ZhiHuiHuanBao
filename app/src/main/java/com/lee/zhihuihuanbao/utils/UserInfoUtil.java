package com.lee.zhihuihuanbao.utils;

import com.google.gson.Gson;
import com.lee.zhihuihuanbao.app.AppManager;
import com.lee.zhihuihuanbao.app.Constants;
import com.lee.zhihuihuanbao.app.MyApp;
import com.lee.zhihuihuanbao.module.mine.bean.UserBean;


/**
 * @Author Lee
 * @Time 2017/9/5
 * @Theme 用户个人信息保存
 */

public class UserInfoUtil {

    public static void saveUserInfo(UserBean infoBean) {  // 保存信息

        if (infoBean != null) {

            // 保存登录后个人信息
            // 1. 保存到本次打开应用
            MyApp.getInstance().setUser(infoBean);
            // 2. 永久保存（方便下次登录获取个人信息）
            Gson gson = new Gson();
            String userInformation = gson.toJson(infoBean);
            SPUtil.putString(Constants.SP_INFORMATION, userInformation);

            // 单独保存 (方便下次进入应用获取)
            SPUtil.putString(Constants.SP_TOKEN, infoBean.getResult().getToken());
            SPUtil.putString(Constants.SP_MEMBERID, infoBean.getResult().getId() + "");
            SPUtil.putString(Constants.SP_MOBILE, infoBean.getResult().getMobile() + "");

            Constants.mToken = infoBean.getResult().getToken();
            Constants.mMemberId = infoBean.getResult().getId() + "";
            Constants.mMobile = infoBean.getResult().getMobile();

        }
    }

    public static void clearUserInfo() {   // 清除信息

        MyApp.getInstance().setUser(null);

        SPUtil.putString(Constants.SP_INFORMATION, "");
        SPUtil.putString(Constants.SP_MEMBERID, "");
        SPUtil.putString(Constants.SP_TOKEN, "");

        AppManager.getAppManager().finishAllActivity();

    }

}
