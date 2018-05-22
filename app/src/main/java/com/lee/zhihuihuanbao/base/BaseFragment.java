package com.lee.zhihuihuanbao.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme  Fragment 基础类
 */

public abstract class BaseFragment<T extends BasePresenter> extends
        Fragment implements BaseView {

    protected T mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected boolean isInited = false;
    private boolean haveShowNetView = false;


    protected abstract T initPresenter();

    protected abstract View bindingView(LayoutInflater inflater, ViewGroup container,
                                        Bundle savedInstanceState);

    protected abstract void initView();
    protected abstract void initEventAndData();

    @Override
    public void onAttach(Context context) {  // 与Activity关联
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 类似Acticity中的onCreate()方法
        mView = bindingView(inflater, container, savedInstanceState);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {  // 在onCreateView（）执行后立马执行
        super.onViewCreated(view, savedInstanceState);
        mPresenter = initPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this);
        initView();
        initEventAndData();
    }


    protected void setToolBar(Toolbar toolbar) {
        setHasOptionsMenu(true);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        if (mPresenter != null)
            mPresenter.detachView();
    }



}
