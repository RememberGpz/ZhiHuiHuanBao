package com.lee.zhihuihuanbao.base;

/**
 * Created by Administrator on 2016/12/13.
 */

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.lee.zhihuihuanbao.R;
import com.lee.zhihuihuanbao.app.AppManager;
import com.lee.zhihuihuanbao.utils.NetUtil;
import com.lee.zhihuihuanbao.view.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme  界面基础类
 */

public abstract class BaseActivity<T extends BasePresenter>
          extends AppCompatActivity implements BaseView {

    protected T mPresenter;
    protected Activity mContext;
    private boolean haveShowNetView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        bindingView();
        mContext = this;
        mPresenter = initPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this);
        AppManager.addActivity(this);
        initView();
        initEventAndData();
    }

    protected abstract void bindingView();

    /**
     * 设置沉浸式菜单，无标题，屏幕方向等
     */
    private void doBeforeSetcontentView() {

        // 无标题
         requestWindowFeature(Window.FEATURE_NO_TITLE);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //添加到activity栈
        AppManager.getAppManager().addActivity(this);
        SetStatusBarColor();
    }

    protected void setToolBar(Toolbar toolbar) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
     protected void SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorTheme));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        AppManager.getAppManager().finishActivity(this);
    }

    protected abstract T initPresenter();

    protected abstract void initView();

    protected abstract void initEventAndData();


    //监听editText,是否关闭输入法
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    //是否关闭输入法
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                v.requestFocus( );
                ((EditText) v).setCursorVisible(true);
                ((EditText) v).setSelection(((EditText) v).getText().length());
                return false;
            } else {
                v.clearFocus();
                ((EditText) v).setCursorVisible(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // initNoNetView(R.id.xrv, R.id.xrv, R.id.xrv_recent_face_face, R.id.xrv_recent_face_all, R.id.xrv_recent_recorded);
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
    public void onReNetRefreshData() {

        Toast.makeText(BaseActivity.this, "有网络啦", Toast.LENGTH_SHORT).show();


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
