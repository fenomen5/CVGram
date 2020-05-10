package com.barkov.ais.cvgram.tabsswipe.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class ctrViewPager extends ViewPager {

    boolean mSwipeState;

    public ctrViewPager(Context context) {
        super(context);
        setMyScroller();
    }

    public ctrViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }

    public void setSwipeState(boolean state) {
        mSwipeState = state;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mSwipeState) {
            super.onTouchEvent(event);
            return false;
        } else {
            return  false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mSwipeState) {
            super.onTouchEvent(event);
            return false;
        } else {
            return  false;
        }
    }

    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }
}
