package com.lee.zhihuihuanbao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * @Author Lee
 * @Time 2017/11/3
 * @Theme  重写ExpandableListView以解决ScrollView嵌套ExpandableListView
 *<br> 导致ExpandableListView显示不正常的问题
 */

public class MyExpandableListView  extends ExpandableListView {

    public MyExpandableListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyExpandableListView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
