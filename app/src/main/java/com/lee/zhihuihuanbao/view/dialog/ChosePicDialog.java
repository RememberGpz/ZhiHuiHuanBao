package com.lee.zhihuihuanbao.view.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.lee.zhihuihuanbao.R;
import com.lee.zhihuihuanbao.base.BaseDialogFragment;
import com.lee.zhihuihuanbao.utils.ImageUtil;


/**
 * Created by lpb on 0015 12-15.
 * You can make it better
 */

public class ChosePicDialog extends BaseDialogFragment {

    private TextView mTvTakePic;
    private TextView mTvChoosePic;
    private TextView mTvCancel;

    @Override
    protected void initLocationAndSize(Window window, WindowManager.LayoutParams params) {
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getViewRes() {
        return R.layout.dialog_choose_pic;
    }

    @Override
    protected void initListener() {
        mTvTakePic.setOnClickListener(this);
        mTvChoosePic.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        mTvTakePic = mRootView.findViewById(R.id.tv_take_pic);
        mTvChoosePic = mRootView.findViewById(R.id.tv_choose_pic);
        mTvCancel = mRootView.findViewById(R.id.tv_cancel);


    }

    @Override
    protected void onClick(View v, int id) {
        switch (id) {
            case R.id.tv_take_pic:
                if(mCheckListener != null){
                    mCheckListener.onTakePicClick();
                }else {
                    ImageUtil.intoCameraList(getContext());
                }
                dismiss();
                break;
            case R.id.tv_choose_pic:
                ImageUtil.intoPhotoList(getContext());
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


     public interface OnPicCheckListener{
        void onTakePicClick();
    }

    private OnPicCheckListener mCheckListener;

    public void setCheckListener(OnPicCheckListener checkListener) {
        mCheckListener = checkListener;
    }
}
