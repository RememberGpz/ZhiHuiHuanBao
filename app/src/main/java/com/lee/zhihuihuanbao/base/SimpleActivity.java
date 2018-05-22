package com.lee.zhihuihuanbao.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lee.zhihuihuanbao.R;
import com.lee.zhihuihuanbao.app.AppManager;
import com.lee.zhihuihuanbao.utils.NetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme  无MVP的activity基类，简单activity直接继承该类
 */

public abstract class SimpleActivity extends AppCompatActivity {

    private boolean haveShowNetView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.addActivity(this);
        bindingView();
        initSavedInstanceState(savedInstanceState);
        initView();
        initEventAndData();
    }

    protected void initSavedInstanceState(Bundle savedInstanceState) {

    }

    protected void setToolBar(Toolbar toolbar) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.removeActivity(this);
    }

    protected abstract void bindingView();
    protected abstract void initView();
    protected abstract void initEventAndData();


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


    private void showHaveNetView(List<View> viewList, View noNetView) {
        for (View view : viewList) {
            if (view == null) {
                continue;
            }
            haveShowNetView = false;
            transView(noNetView, view);
            onReNetRefreshData();
            break;
        }
    }

    private void showNoNetView(List<View> viewList, View noNetView) {
        for (View view : viewList) {
            if (view == null) {
                continue;
            }
            haveShowNetView = true;
            transView(view, noNetView);
            break;
        }
    }


    /**
     * 没有网络 重新刷新
     */
    public  void onReNetRefreshData() {

        Toast.makeText(SimpleActivity.this, "有网络啦", Toast.LENGTH_SHORT).show();


    }


    /**
     * 切换 有网络界面 和 无网络界面
     * @param defaultView
     * @param replaceView
     */
    protected void transView(final View defaultView, View replaceView) {


        final int index = ((ViewGroup) defaultView.getParent()).indexOfChild(defaultView);
        ViewGroup.LayoutParams params = defaultView.getLayoutParams();
        ViewGroup parent = (ViewGroup) defaultView.getParent();
        parent.removeView(defaultView);
        parent.addView(replaceView, index, params);
    }

}
