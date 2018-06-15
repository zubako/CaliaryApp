package com.example.zubako.caliary;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class CaliaryController extends GestureDetector.SimpleOnGestureListener {

    private CaliaryView view;

    // ---------------
    // Constructor
    // ---------------
    CaliaryController( CaliaryView view ) {
        this.view = view;
    }

    // ---------------
    // Override : 터치 개시
    // ---------------
    @Override
    public boolean onDown( MotionEvent e ) {
        return true;
    }

    // ---------------
    // Override : 한번의 터치로 날짜 선택
    // ---------------
    @Override
    public boolean onSingleTapUp( MotionEvent e ) {
        if( e.getY() > view.weekHeight() && e.getX() > view.getViewX() && e.getX() < view.getViewX() + view.getViewWidth() ) {
            view.pointToSelectDate( e.getX(), e.getY() );
        }

        return true;
    }

    // ---------------
    // Override : 가속 터치로 달력 이동
    // ---------------
    @Override
    public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY ) {
        if( Math.abs( velocityX ) > 1000 ) {
            view.monthChange( ( int )( velocityX / Math.abs( velocityX ) ) * ( -1 ) );
        }

        return true;
    }

    // ---------------
    // Override : 스크롤 터치로 달력 이동
    // ---------------
    @Override
    public boolean onScroll( MotionEvent e1, MotionEvent e2, float distanceX, float distanceY ) {
        view.getParent().requestDisallowInterceptTouchEvent( true );
        view.setViewX( ( int )( view.getViewX() - distanceX ) );
        view.invalidate();

        return true;
    }

}