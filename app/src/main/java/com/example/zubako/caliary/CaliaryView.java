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
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class CaliaryView extends View {

    private long viewX;
    private long viewY;
    private int viewWidth;
    private int viewHeight;
    private int attrViewDefaultHeight;
    private int attrNumberSize;
    private int attrNumberSpaceX;
    private int attrNumberSpaceY;
    private int attrWeekHeight;
    private int attrDefaultColor;
    private int attrTomonthBackgroundColor;
    private int attrWeekBackgroundColor;
    private boolean isTouch;
    private Paint paint;
    private GestureDetectorCompat detector;
    private DateManagement dateManager;
    private DateManagement preDateManager;
    private DateManagement nextDateManager;
    private int viewWeek;
    private int viewDay;

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
            dateManager = new DateManagement();
            preDateManager = new DateManagement();
            nextDateManager = new DateManagement();
            preDateManager.add( Calendar.MONTH, -1 );
            nextDateManager.add( Calendar.MONTH, 1 );
            detector = new GestureDetectorCompat( getContext(), new CaliaryController( this ));

            dateManager.set( Calendar.DATE, 1 );
            viewWeek = dateManager.get( Calendar.DAY_OF_WEEK );
            viewDay = viewWeek - ( ( viewWeek - 1 ) * 2 ) - 7;

            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes( attr, R.styleable.CaliaryView, 0, 0);
            try {
                attrViewDefaultHeight = typedArray.getInteger( R.styleable.CaliaryView_viewDefaultHeight, viewHeight );
                attrNumberSize = typedArray.getInteger( R.styleable.CaliaryView_numberSize, 1 );
                attrNumberSpaceX = typedArray.getInteger( R.styleable.CaliaryView_numberSpaceX, 0 );
                attrNumberSpaceY = typedArray.getInteger( R.styleable.CaliaryView_numberSpaceY, 0 );
                attrWeekHeight = typedArray.getInteger( R.styleable.CaliaryView_weekHeight, 0 );
                attrDefaultColor = typedArray.getColor( R.styleable.CaliaryView_defaultColor, Color.rgb( 0, 0, 0 ) );
                attrTomonthBackgroundColor = typedArray.getColor( R.styleable.CaliaryView_tomonthBackgroundColor, Color.rgb( 255, 255, 255 ) );
                attrWeekBackgroundColor = typedArray.getColor( R.styleable.CaliaryView_weekBackgroundColor, Color.rgb( 255, 255, 255 ) );
            }
            catch( Exception e ) {
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
        if( Math.abs( viewX ) > 5 ) {
            viewX /= 1.5;
        }
        else {
            viewX = 0;
        }
        invalidate();
    }

    public void drawCalendar( Canvas canvas, DateManagement date, int offsetRow ) {
        int viewWeekWidth = attrWeekHeight + attrNumberSize;
        int spaceX = ( viewWidth ) / 7;
        int spaceY = ( viewHeight - viewWeekWidth ) / 6;
        long gridX;
        long gridY;
        date.set( Calendar.DATE, 1 );
        viewWeek = date.get( Calendar.DAY_OF_WEEK );
        viewDay = viewWeek - ( ( viewWeek - 1 ) * 2 ) - 7;

        for( int i = 0; i < 7; i++ ) {
            for( int j = 0; j < 7; j++ ) {
                gridX = viewX + ( j * spaceX ) + ( viewWidth * offsetRow );
                gridY = viewY + ( i * spaceY ) - ( ( long )Math.signum( i ) * ( spaceY - viewWeekWidth ) );

                paint.setStyle( Paint.Style.STROKE );
                canvas.drawRect( gridX, gridY, gridX + spaceX - 1, gridY + spaceY - 1 - ( ( 1 - ( long )Math.signum( i ) ) * ( spaceY - viewWeekWidth ) ), paint );
                paint.setStyle( Paint.Style.FILL );
                if( i == 0 ) {
                    paint.setTextAlign( Paint.Align.CENTER );
                    DateFormatSymbols dateFormatSymbols = new DateFormatSymbols( Locale.KOREAN );
                    canvas.drawText( dateFormatSymbols.getShortWeekdays()[ j + 1 ], gridX + ( spaceX / 2 ), viewY + ( ( viewWeekWidth + ( viewWeekWidth / 2 ) ) / 2 ), paint );
                }
                else if( viewDay > 0 && viewDay <= date.getLastDayOfMonth( date ) ) {
                    paint.setTextAlign( Paint.Align.LEFT );
                    canvas.drawText( String.valueOf( viewDay ), gridX + attrNumberSpaceX, gridY + attrNumberSpaceY + attrNumberSize, paint );
                }
                viewDay++;
            }
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        if( performClick() ) Toast.makeText( getContext(), "Performed", Toast.LENGTH_SHORT ).show();

        // 터치 시작 시 터치 상태 true
        if( event.getAction() == MotionEvent.ACTION_DOWN ) {
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

        this.viewWidth = widthSize + 2;
        if( heightMode == MeasureSpec.AT_MOST ) {
            this.viewHeight = attrViewDefaultHeight;
        }
        else {
            this.viewHeight = heightSize + 2;
        }

        setMeasuredDimension( this.viewWidth, this.viewHeight );
    }

    // ---------------
    // 뷰 묘화
    // ---------------
    @Override
    protected void onDraw( Canvas canvas ) {
        paint.setColor( Color.rgb( 0, 0, 0 ) );
        paint.setTextSize( attrNumberSize + attrNumberSpaceY );
        paint.setStrokeWidth( 4 );

        int weekHeight = attrWeekHeight + attrNumberSize;
        paint.setColor( attrWeekBackgroundColor );
        canvas.drawRect( viewX, viewY, viewX + viewWidth, viewY + weekHeight, paint );
        paint.setColor( attrTomonthBackgroundColor );
        canvas.drawRect( viewX, viewY + weekHeight, viewX + viewWidth, viewY + viewHeight, paint );
        paint.setColor( attrDefaultColor );

        drawCalendar( canvas, dateManager, 0 );
        drawCalendar( canvas, preDateManager, -1 );
        drawCalendar( canvas, nextDateManager, 1 );

        if( viewX != 0 && !isTouch ) {
            anim();
        }
    }

}
