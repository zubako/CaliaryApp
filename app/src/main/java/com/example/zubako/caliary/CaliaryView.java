package com.example.zubako.caliary;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

public class CaliaryView extends View {

    private int[] color = new int[3];
    private int viewWidth;
    private int viewHeight;
    public int viewPosX;
    public int viewPosY;
    private boolean isTouch;
    private Context cont;
    private Paint paint = new Paint();
    private GestureDetectorCompat touchDr;

    public CaliaryView( Context cont ) { this( cont, null ); }
    public CaliaryView( Context cont, AttributeSet attr ) { this( cont, attr, 0 ); }
    public CaliaryView( Context cont, AttributeSet attr, int defStyle ) {

        super( cont, attr, defStyle );
        viewPosX = 0;
        viewPosY = 0;
        if ( attr != null && cont != null ) {
            TypedArray typeArr = cont.obtainStyledAttributes( attr, R.styleable.CaliaryView, defStyle, 0 );
            color[0] = typeArr.getColor( R.styleable.CaliaryView_caliaryColor1, Color.rgb( 255, 0, 255 ) );
            color[1] = typeArr.getColor( R.styleable.CaliaryView_caliaryColor2, Color.rgb( 0, 255, 0 ) );
            color[2] = typeArr.getColor( R.styleable.CaliaryView_caliaryColor3, Color.rgb( 100, 100, 100 ) );
            TouchDr touchDrL = new TouchDr( this );
            touchDr = new GestureDetectorCompat( getContext(), touchDrL );
            typeArr.recycle();
        }
        this.cont = getContext();

    }

    public void anim()
    {

        if ( Math.abs( viewPosX ) > 5 ) viewPosX /= 1.5;
        else viewPosX = 0;
        invalidate();

    }

    @Override
    public boolean performClick() { return super.performClick(); }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if( performClick() ) Toast.makeText( cont, "퍼폼 클릭", Toast.LENGTH_LONG ).show();

        if( event.getAction() == MotionEvent.ACTION_DOWN ) isTouch = true;
        if( event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP ) {

            getParent().requestDisallowInterceptTouchEvent( false );

            TimePickerDialog dialog = new TimePickerDialog( cont, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet( TimePicker view, int hourOfDay, int minute ) {
                    Toast.makeText( cont, hourOfDay + " - " + minute, Toast.LENGTH_LONG ).show();
                }
            }, 10, 25, false );
            dialog.setTitle( "시간을 설정하세요." );
            dialog.show();
            isTouch = false;
        }
        invalidate();
        return touchDr.onTouchEvent( event );

    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {

        this.viewWidth = MeasureSpec.getSize( widthMeasureSpec );
        this.viewHeight = MeasureSpec.getSize( heightMeasureSpec );
        setMeasuredDimension( this.viewWidth, this.viewHeight );

    }

    @Override
    protected void onDraw( Canvas canvas ) {
        // Init
        int spaceX = viewWidth / 7;
        int spaceY = viewHeight / 5;
        paint.setColor( color[0] );
        paint.setTextSize( 100 );
        paint.setTextAlign( Paint.Align.CENTER );
        paint.setStyle( Paint.Style.FILL );
        paint.setStrokeWidth( 16 );

        // 구분선 그리기
        canvas.drawRect( viewPosX, viewPosY, viewPosX + viewWidth, viewPosY + viewHeight, paint );
        paint.setColor( color[1] );
        canvas.drawCircle( viewWidth / 2, viewHeight / 2, viewHeight / 2 + viewPosX / 2, paint );
        paint.setColor( color[2] );
        paint.setStyle( Paint.Style.STROKE );

        // 격자 그리기
        for ( int i = 0; i < 5; i++ )
        {
            for ( int j = 0; j < 7; j++ )
            {
                canvas.drawRect( viewPosX + j * spaceX, viewPosY + i * spaceY, viewPosX + j * spaceX + spaceX, viewPosY + i * spaceY + spaceY, paint );
            }
        }

        // 날짜 그리기
        paint.setStyle( Paint.Style.FILL );
        canvas.drawText( "1", viewPosX + 100, viewPosY + 100, paint );
        canvas.drawText( "10", viewPosX + 100, viewPosY + 200, paint );
        canvas.drawText( "100", viewPosX + 100, viewPosY + 300, paint );

        // 후처리
       if ( viewPosX != 0 && !isTouch ) anim();

    }
}

class TouchDr extends GestureDetector.SimpleOnGestureListener {

    private CaliaryView view;

    TouchDr( CaliaryView view ) {
        this.view = view;
    }

    @Override
    public boolean onDown( MotionEvent e ) { return true; }

    @Override
    public boolean onScroll( MotionEvent e1, MotionEvent e2, float distanceX, float distanceY ) {
        view.getParent().requestDisallowInterceptTouchEvent( true );
        view.viewPosX -= distanceX;
        view.invalidate();
        return true;
    }

}
