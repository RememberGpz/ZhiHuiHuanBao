package com.lee.zhihuihuanbao.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme  网络层基础类
 */

public abstract class BasePresenter<V extends BaseView> {

    protected Reference<V> mViewRef;
    //建立关联
    public void attachView(V view){
        mViewRef=new WeakReference<V>(view);
    }
    //获取view
    protected V getView(){
        return mViewRef.get();
    }
    //判断是否与View建立了关联
    public boolean isViewAttached(){
        return mViewRef!=null && mViewRef.get()!=null;
    }

    //该方法在activity或者Fragment的onDestory中调用
    public void detachView(){
        if(mViewRef!=null){
            mViewRef.clear();
            mViewRef=null;
        }
    }


}
