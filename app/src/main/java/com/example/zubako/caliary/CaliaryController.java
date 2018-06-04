package com.example.zubako.caliary;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class CaliaryController extends GestureDetector.SimpleOnGestureListener {

    private CaliaryView view;

    public CaliaryController( CaliaryView view ) {
        this.view = view;
    }

    @Override
    public boolean onDown( MotionEvent e ) {
        return true;
    }

    @Override
    public boolean onScroll( MotionEvent e1, MotionEvent e2, float distanceX, float distanceY ) {
        view.getParent().requestDisallowInterceptTouchEvent( true );
        view.setViewX( ( int )( view.getViewX() - distanceX ) );
        view.invalidate();
        return true;
    }

}