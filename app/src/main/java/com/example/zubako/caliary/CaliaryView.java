package com.example.zubako.caliary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CaliaryView extends View {

    private long viewX;
    private long viewY;
    private int viewWidth;
    private int viewHeight;
    private int viewDefaultHeight;
    private boolean isTouch;
    private Paint paint;
    private GestureDetectorCompat detector;

    // ---------------
    // Constructor
    // ---------------
    public CaliaryView( Context cont ) {
        this( cont, null );
    }
    public CaliaryView( Context cont, AttributeSet attr ) {
        this( cont, attr, 0 );
    }
    public CaliaryView( Context cont, AttributeSet attr, int defStyle ) {
        super( cont, attr, defStyle );
        initialize( attr );
    }

    // ---------------
    // 초기화
    // ---------------
    public void initialize( AttributeSet attr ) {
        if( attr != null ) {
            viewX = 0;
            viewY = 0;
            paint = new Paint();
            detector = new GestureDetectorCompat( getContext(), new CaliaryController( this ));

            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes( attr, R.styleable.CaliaryView, 0, 0);
            try {
                viewDefaultHeight = typedArray.getInteger( R.styleable.CaliaryView_defaultHeight, viewHeight );
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
            finally {
                typedArray.recycle();
            }
        }
    }

    public long getViewX() {
        return viewX;
    }

    public void setViewX( long posX ) {
        this.viewX = posX;
    }

    public void anim() {
        if ( Math.abs( viewX ) > 5 )
        {
            viewX /= 1.5;
        }
        else
        {
            viewX = 0;
        }
        invalidate();
    }

    @Override
    public boolean performClick()
    {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        if( performClick() ) Toast.makeText( getContext(), "Performed", Toast.LENGTH_SHORT ).show();

        // 터치 시작 시 터치 상태 true
        if( event.getAction() == MotionEvent.ACTION_DOWN )
        {
            isTouch = true;
        }

        // 터치 끝날 시 또는 취소버튼 눌릴 시 터치 상태 false와 터치 구역 해제
        if( event.getAction() == MotionEvent.ACTION_CANCEL ||
                event.getAction() == MotionEvent.ACTION_UP ) {
            getParent().requestDisallowInterceptTouchEvent( false );
            isTouch = false;
        }
        invalidate();

        return detector.onTouchEvent( event );
    }

    // ---------------
    // 뷰 크기 설정
    // ---------------
    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );

        int widthSize = MeasureSpec.getSize( widthMeasureSpec );
        int heightMode = MeasureSpec.getMode( heightMeasureSpec );
        int heightSize = MeasureSpec.getSize( heightMeasureSpec );

        this.viewWidth = widthSize;
        if ( heightMode == MeasureSpec.AT_MOST ) {
            this.viewHeight = viewDefaultHeight;
        }
        else {
            this.viewHeight = heightSize;
        }

        setMeasuredDimension( this.viewWidth, this.viewHeight );
    }

    // ---------------
    // 뷰 묘화
    // ---------------
    @Override
    protected void onDraw( Canvas canvas ) {
        int spaceX = viewWidth / 7;
        int spaceY = viewHeight / 6;
        paint.setColor( Color.rgb( 0, 0, 0 ) );
        paint.setTextSize( 64 );
        paint.setStyle( Paint.Style.STROKE );
        paint.setStrokeWidth( 4 );

        for ( int i = 0; i < 6; i++ )
        {
            for ( int j = 0; j < 7; j++ )
            {
                canvas.drawRect( viewX + ( j * spaceX ),
                        viewY + ( i * spaceY ),
                        viewX + ( j * spaceX ) + spaceX,
                        viewY + ( i * spaceY ) + spaceY,
                        paint );
            }
        }

        paint.setStyle( Paint.Style.FILL );
        canvas.drawText( "1", viewX + 100, viewY + 100, paint );
        canvas.drawText( "10", viewX + 100, viewY + 200, paint );
        canvas.drawText( "100", viewX + 100, viewY + 300, paint );

        if ( viewX != 0 && !isTouch ) anim();
    }

}
