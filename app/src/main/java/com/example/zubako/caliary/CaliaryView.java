package com.example.zubako.caliary;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class CaliaryView extends View {

    private Context cont;
    private long viewX;
    private long viewY;
    private long viewOriginX;
    private int viewWidth;
    private int viewHeight;
    private int viewWeek;
    private int viewDay;
    private int selectDateX;
    private int selectDateY;
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
    private DateManagement currDateManager;
    private DateManagement preDateManager;
    private DateManagement nextDateManager;

    // ---------------
    // Constructor( Overroads )
    // ---------------
    public CaliaryView( Context cont ) {
        this( cont, null );
    }
    public CaliaryView( Context cont, AttributeSet attr ) {
        this( cont, attr, 0 );
    }
    public CaliaryView( Context cont, AttributeSet attr, int defStyle ) {
        super( cont, attr, defStyle );
        initialize( cont, attr );
    }

    // ---------------
    // 초기화
    // ---------------
    public void initialize( Context cont, AttributeSet attr ) {
        if( attr != null ) {
            this.cont = cont;
            viewX = 0;
            viewY = 0;
            viewOriginX = viewX;
            paint = new Paint();
            dateManager = new DateManagement();
            currDateManager = new DateManagement();
            preDateManager = new DateManagement();
            nextDateManager = new DateManagement();
            currDateManager.set( Calendar.DATE, 1 );
            preDateManager.add( Calendar.MONTH, -1 );
            nextDateManager.add( Calendar.MONTH, 1 );
            viewWeek = currDateManager.get( Calendar.DAY_OF_WEEK );
            viewDay = viewWeek - ( ( viewWeek - 1 ) * 2 ) - 7;
            dateToSelectDate( currDateManager );
            detector = new GestureDetectorCompat( getContext(), new CaliaryController( this ));

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

    // ---------------
    // get : 달력 표시 X좌표
    // ---------------
    public long getViewX() {
        return viewX;
    }

    // ---------------
    // set : 달력 표시 X좌표
    // ---------------
    public void setViewX( long posX ) {
        this.viewX = posX;
    }

    // ---------------
    // get : 달력 표시 너비
    // ---------------
    public int getViewWidth() {
        return viewWidth;
    }

    // ---------------
    // set : 선택 날짜 X좌표( 일정 간격 )
    // ---------------
    public void setSelectDateX( int x ) {
        selectDateX = x;
    }

    // ---------------
    // set : 선택 날짜 Y좌표( 일정 간격 )
    // ---------------
    public void setSelectDateY( int y ) {
        selectDateY = y;
    }

    // ---------------
    // getAttrs : 요일칸 높이
    // ---------------
    public int weekHeight() {
        return attrWeekHeight;
    }

    // ---------------
    // 표시 달 변경
    // ---------------
    public void monthChange( int amount ) {
        currDateManager.add( Calendar.MONTH, amount );
        preDateManager.add( Calendar.MONTH, amount );
        nextDateManager.add( Calendar.MONTH, amount );
        viewX += viewWidth * amount;

        invalidate();
    }

    // ---------------
    // 좌표 커서 위치 설정
    // ---------------
    public void pointToSelectDate( float x, float y ) {
        long spaceX = viewWidth / 7;
        long spaceY = ( viewHeight - attrWeekHeight ) / 6;
        int count;

        for( count = 0; viewX + ( count * spaceX ) < x;  ) {
            count++;
        }
        selectDateX = count - 1;

        for( count = 0; viewY + ( ( count * spaceY ) + attrWeekHeight ) < y;  ) {
            count++;
        }
        selectDateY = count - 1;

        selectMonthChange( currDateManager );
        invalidate();
    }

    // ---------------
    // 좌표 커서 위치 날짜로 설정
    // ---------------
    public void dateToSelectDate( DateManagement date ) {
        int month;
        int pos;

        pos = date.getFirstWeekDayOfMonth( date ) - 2 + date.get( Calendar.DATE );
        setSelectDateX( pos % 7 );
        setSelectDateY( pos / 7 );
        month = date.get( Calendar.MONTH );
        date.set( Calendar.DATE, date.get( Calendar.DATE ) );
        if( date.get( Calendar.MONTH ) != month ) {
            date.set( Calendar.DATE, 1 );
            date.set( Calendar.MONTH, month );
            date.set( Calendar.DATE, date.getLastDayOfMonth( date ) );
        }

        invalidate();
    }

    // ---------------
    // 날짜 바깥 선택에 의한 이동 확인
    // ---------------
    public void selectMonthChange( DateManagement date ) {
        int month;
        int day;
        int balance;
        int amount;

        month = date.get( Calendar.MONTH );
        date.set( Calendar.DATE, ( selectDateX + 2 + ( selectDateY * 7 ) ) - date.getFirstWeekDayOfMonth( date ) );
        day = date.get( Calendar.DATE );
        if( date.get( Calendar.MONTH ) != month ) {
            date.set( Calendar.DATE, 1 );
            balance = month - date.get( Calendar.MONTH );
            amount = date.get( Calendar.MONTH ) - month;
            date.add( Calendar.MONTH, balance );
            monthChange( amount );
            date.set( Calendar.DATE, day );
        }

        TextView txt = ( TextView )( ( Activity )cont ).findViewById( R.id.txtDate );
        txt.setText( date.get( Calendar.YEAR ) + "년 " + ( date.get( Calendar.MONTH ) + 1 ) + "월 " + date.get( Calendar.DATE ) + "일" );

        invalidate();
    }

    // ---------------
    // 동작 묘화
    // ---------------
    public void anim() {
        if( Math.abs( viewX ) > 5 && viewX != 0 ) {
            viewX /= 1.5;
        }
        else {
            viewX = 0;
        }

        invalidate();
    }

    // ---------------
    // 달력 그리기
    // ---------------
    public void drawCalendar( Canvas canvas, DateManagement date, int offsetRow ) {
        int viewWeekHeight = attrWeekHeight + attrNumberSize;
        int spaceX = ( viewWidth ) / 7;
        int spaceY = ( viewHeight - viewWeekHeight ) / 6;
        long gridX;
        long gridY;
        date.set( Calendar.DATE, 1 );
        viewWeek = date.get( Calendar.DAY_OF_WEEK );
        viewDay = viewWeek - ( ( viewWeek - 1 ) * 2 ) - 7;

        for( int i = 0; i < 7; i++ ) {
            for( int j = 0; j < 7; j++ ) {
                gridX = viewX + ( j * spaceX ) + ( viewWidth * offsetRow );
                gridY = viewY + ( i * spaceY ) - ( ( long )Math.signum( i ) * ( spaceY - viewWeekHeight ) );
                paint.setColor( attrDefaultColor );
                paint.setStyle( Paint.Style.STROKE );
                canvas.drawRect( gridX, gridY, gridX + spaceX - 1, gridY + spaceY - 1 - ( 1 - ( long )Math.signum( i ) ) * ( spaceY - viewWeekHeight ), paint );
                paint.setStyle( Paint.Style.FILL );

                if( j == 0 ) {
                    paint.setColor( Color.rgb( 255, 0, 0 ) );
                }
                else if( j == 6 ) {
                    paint.setColor( Color.rgb( 0, 0, 255 ) );
                }
                else {
                    paint.setColor( attrDefaultColor );
                }

                if( i == 0 ) {
                    paint.setTextAlign( Paint.Align.CENTER );
                    DateFormatSymbols dateFormatSymbols = new DateFormatSymbols( Locale.KOREAN );
                    canvas.drawText( dateFormatSymbols.getShortWeekdays()[ j + 1 ], gridX + ( spaceX / 2 ), viewY + ( ( viewWeekHeight + ( viewWeekHeight / 2 ) ) / 2 ), paint );
                }
                else if( viewDay > 0 && viewDay <= date.getLastDayOfMonth( date ) ) {
                    paint.setTextAlign( Paint.Align.LEFT );
                    canvas.drawText( String.valueOf( viewDay ), gridX + attrNumberSpaceX, gridY + attrNumberSpaceY + attrNumberSize, paint );
                }
                viewDay++;
            }
        }
    }

    // ---------------
    // Override :
    // ---------------
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    // ---------------
    // Override : 터치 상태 확인
    // ---------------
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
    // Override : 그리기 구역 설정
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
    // Override : 그리기
    // ---------------
    @Override
    protected void onDraw( Canvas canvas ) {
        int viewWeekHeight = attrWeekHeight + attrNumberSize;
        int spaceX = ( viewWidth ) / 7;
        int spaceY = ( viewHeight - viewWeekHeight ) / 6;
        paint.setColor( Color.rgb( 0, 0, 0 ) );
        paint.setTextSize( attrNumberSize + attrNumberSpaceY );
        paint.setStyle( Paint.Style.FILL );
        paint.setStrokeWidth( 4 );

        paint.setColor( attrWeekBackgroundColor );
        canvas.drawRect( viewX, viewY, viewX + viewWidth, viewY + viewWeekHeight, paint );
        paint.setColor( attrTomonthBackgroundColor );
        canvas.drawRect( viewX, viewY + viewWeekHeight, viewX + viewWidth, viewY + viewHeight, paint );
        paint.setColor( attrDefaultColor );

        drawCalendar( canvas, currDateManager, 0 );
        drawCalendar( canvas, preDateManager, -1 );
        drawCalendar( canvas, nextDateManager, 1 );

        paint.setStyle( Paint.Style.STROKE );
        paint.setStrokeWidth( 8 );
        paint.setColor( Color.rgb( 255, 0, 0 ) );
        canvas.drawRect( viewX + ( spaceX * selectDateX ),
                viewY + viewWeekHeight + ( spaceY * selectDateY ),
                viewX + ( spaceX * selectDateX ) + spaceX,
                viewY + viewWeekHeight + ( spaceY * selectDateY ) + spaceY, paint );

        if( !isTouch && viewX != 0 ) {
            anim();
        }
    }

}
