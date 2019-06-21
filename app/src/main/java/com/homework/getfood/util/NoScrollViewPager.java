package com.homework.getfood.util;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不可滑动的ViewPager
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isScroll;
    public NoScrollViewPager(Context context,AttributeSet attrs){
        super(context, attrs);
    }
    public NoScrollViewPager(Context context) {
        super(context);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll){
            return super.onTouchEvent(ev);
        }else {
            return true;
        }
    }
    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}

