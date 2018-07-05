package com.yong.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ExcelView extends FrameLayout {

    public ExcelView(@android.support.annotation.NonNull Context context) {
        super(context);
        initView();
    }

    public ExcelView(@android.support.annotation.NonNull Context context, @android.support.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ExcelView(@android.support.annotation.NonNull Context context, @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExcelView(@android.support.annotation.NonNull Context context, @android.support.annotation.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    ListView lvGroupTitle;

    ListView lvGroupContent;

    LinearLayout llGroupType;

    HorizontalScrollView hsGroupContent;

    private void initView() {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.view_excel, this, false);
        lvGroupTitle = layout.findViewById(R.id.lvGroupTitle);
        lvGroupTitle.setAdapter(mTitleAdapter);
        lvGroupContent = layout.findViewById(R.id.lvGroupContent);
        lvGroupContent.setAdapter(mContentAdapter);
        hsGroupContent = layout.findViewById(R.id.hsGroupContainer);
        llGroupType = layout.findViewById(R.id.llGroupType);
        addView(layout);
    }

    private BaseAdapter mTitleAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_group_title, parent, false);
        }
    };

    private BaseAdapter mContentAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return 15;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_group_content, parent, false);
        }
    };

    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(), location[1] + view.getHeight());
    }

    private RectF mTitleRectF;

    private RectF mContentRectF;

    private RectF mTypeRectF;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mTitleRectF == null) {
            mTitleRectF = calcViewScreenLocation(lvGroupTitle);
        }
        if (mContentRectF == null) {
            mContentRectF = calcViewScreenLocation(lvGroupContent);
        }
        if (mTypeRectF == null) {
            mTypeRectF = calcViewScreenLocation(llGroupType);
        }
        float x = event.getRawX(); // 获取相对于屏幕左上角的 x 坐标值
        float y = event.getRawY(); // 获取相对于屏幕左上角的 y 坐标值
        if (mTitleRectF.contains(x, y)) {
            lvGroupTitle.dispatchTouchEvent(event);
            lvGroupContent.dispatchTouchEvent(event);
        } else if (mContentRectF.contains(x, y)) {
            lvGroupTitle.dispatchTouchEvent(event);
            lvGroupContent.dispatchTouchEvent(event);
            hsGroupContent.dispatchTouchEvent(event);
        } else if (mTypeRectF.contains(x, y)) {
            hsGroupContent.dispatchTouchEvent(event);
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
