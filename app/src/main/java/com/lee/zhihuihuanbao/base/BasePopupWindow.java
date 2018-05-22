package com.lee.zhihuihuanbao.base;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.lee.zhihuihuanbao.R;


/**
 * @Author Lee
 * @Time 2017/9/11
 * @Theme  对话框基础类
 */

public abstract class BasePopupWindow implements View.OnClickListener{

    protected Context context;
    protected PopupWindow popupWindow;
    private View view;
    private View popupView;
    private OnPopDismissListener listener;

    /**
     *
     * @param context
     * @param view 事件触发的view
     * @param layoutId 要显示的view的id
     */
    public BasePopupWindow(Context context , View view, int layoutId){
        this.view = view;
        this.context = context;
        creatPopouWindow(layoutId);
    }

    /**
     * 用于重新设置popupWindow的属性，包括进出动画，背景，布局大小等
     * @return
     */
    public PopupWindow getPopupWindow(){
        return popupWindow;
    }

    public Context getContext(){
        return context;
    }
    public View getView(){
        return view;
    }

    private void creatPopouWindow(int id) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        popupView = (View) inflater.inflate(id, null,true);
        //占满全屏
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        layoutParams.gravity = Gravity.CENTER;

        //设进出动画，默认是从下往上弹出

        popupWindow.setAnimationStyle(R.style.timepopwindow_anim_style);
//        popupWindow.setAnimationStyle(R.style.dialog_no_bg);//没有动画
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        //设置背景颜色
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.color.transparent));
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) context)
                        .getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) context).getWindow().setAttributes(lp);

                if(listener != null){
                    listener.onDismiss();
                }
            }
        });
        initView(popupView);
    }

    protected abstract void initView(View popupView);

    public void showDefault(View view) {
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //设置全屏背景
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = 0.7f;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    public void showGravityCenter(View view){
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //设置全屏背景
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = 0.7f;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
    }

    public interface OnPopDismissListener{
        void onDismiss();
    }
    public void setOnPopDismissListener(OnPopDismissListener listener){
        this.listener = listener;
    }
}
