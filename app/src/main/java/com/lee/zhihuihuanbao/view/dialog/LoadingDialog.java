package com.lee.zhihuihuanbao.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lee.zhihuihuanbao.R;

/**
 * Created by Administrator on 2016/10/21.
 */

public class LoadingDialog {

    private Dialog mDialog;
    private Window window;
    public ProgressBar mProgressBar;
    public TextView pb_loadingbar;

    public LoadingDialog(Activity activity, String tipContent){
        initView(activity, tipContent);
    }

    private void initView(Activity activity, String tipContent) {

        mDialog = new Dialog(activity, R.style.CustomDialog);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window = mDialog.getWindow();
        window.setContentView(R.layout.dialog_loading);
        mProgressBar = (ProgressBar)window.findViewById(R.id.pb_loadingbar);
        pb_loadingbar = (TextView)window.findViewById(R.id.tv_loadingbar);
        pb_loadingbar.setText(tipContent);
        mDialog.setCancelable(false);
    }

    public void show(){
        mDialog.show();
    }

    public void setCancelable(boolean cancelable){
        mDialog.setCancelable(cancelable);
    }

    public boolean isShowing(){
        if(mDialog.isShowing()){
            return true;
        }else {
            return false;
        }
    }

    public void dissmiss(){
        mDialog.dismiss();
    }

}
