package com.barkov.ais.cvgram.google.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.maps.MapView;

public class ScrollGoogleMap extends MapView {

    private View.OnTouchListener mListener;

    public ScrollGoogleMap(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                System.out.println("unlocked");
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;

            case MotionEvent.ACTION_DOWN:
                System.out.println("locked");
                this.getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
