package com.example.zubako.caliary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class CaliaryView extends View {

    private Context cont;
    private DisplayMetrics metrics = new DisplayMetrics();
    private long viewX;
    private long viewY;
    private int viewWidth;
    private int viewHeight;
    private int selectDateX;
    private int selectDateY;
    private int selectDateOriginX;
    private int selectDateOriginY;
    private boolean attrViewIsCalendar;
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

    private TextView txtDate;
    private TextView txtEventDate;
    public ImageButton imgPreButton;
    public ImageButton imgNextButton;
    public ListView listEventDate;
    public EventDateAdapter listEventDateAdapter;
    public FloatingActionButton btnAddEventDate;
    // -----
    private TextView Date;
    private TextView DiaryView;
    public ImageView Left;
    public ImageView Right;
    public FloatingActionButton AddButton;

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
            viewWidth = ( int )( metrics.widthPixels / metrics.density );
            paint = new Paint();
            dateManager = new DateManagement();
            currDateManager = new DateManagement();
            preDateManager = new DateManagement();
            nextDateManager = new DateManagement();
            OutsideDateManagerUpdate();
            detector = new GestureDetectorCompat( getContext(), new CaliaryController( this ));

            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes( attr, R.styleable.CaliaryView, 0, 0);
            try {
                attrViewIsCalendar = typedArray.getBoolean( R.styleable.CaliaryView_viewIsCalendar, true );
                attrViewDefaultHeight = typedArray.getInteger( R.styleable.CaliaryView_viewDefaultHeight, viewWidth );
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
    // 캘린더 초기화
    // ---------------
    public void initCalendar() {
        txtDate = ( ( Activity )cont ).findViewById( R.id.txtDate );
        txtEventDate = ( ( Activity )cont ).findViewById( R.id.txtEventDate );
        imgPreButton = ( ( Activity )cont ).findViewById( R.id.imgPreButton );
        imgNextButton = ( ( Activity )cont ).findViewById( R.id.imgNextButton );
        listEventDate = ( ( Activity )cont ).findViewById( R.id.listEventDate );
        btnAddEventDate = ( ( Activity )cont ).findViewById( R.id.btnAddEventDate );

        imgPreButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                monthChange( -1 );
            }
        } );
        imgNextButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                monthChange( 1 );
            }
        } );
        listEventDateAdapter = new EventDateAdapter();
        listEventDate.setAdapter( listEventDateAdapter );
        btnAddEventDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Toast.makeText( cont.getApplicationContext(), "추가 화면으로 이동합니다.", Toast.LENGTH_SHORT ).show();
            }
        } );

        for( int i = 0; i < 16; i++ )
        {
            EventDateBase item = new EventDateBase();
            item.setEventDateName( "오픈소스 팀 프로젝트 미팅" + i + "번쨹" );
            listEventDateAdapter.items.add( item );
        }

        selectDateOriginX = ( int )viewX + ( viewWidth / 2 );
        selectDateOriginY = ( int )viewY + ( viewHeight / 2 );
        dateToSelectDate( dateManager );
    }

    // ---------------
    // 다이어리 초기화
    // ---------------
    public void initDiary() {
        Date = ( ( Activity )cont ).findViewById( R.id.Date );
        DiaryView = ( ( Activity )cont ).findViewById( R.id.DiaryView );
        Left = ( ( Activity )cont ).findViewById( R.id.Left );
        Right = ( ( Activity )cont ).findViewById( R.id.Right );
        AddButton = ( ( Activity )cont ).findViewById( R.id.AddButton );

        Left.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                monthChange( -1 );
            }
        } );
        Right.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                monthChange( 1 );
            }
        } );
        AddButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Toast.makeText( cont.getApplicationContext(), "추가 화면으로 이동합니다.", Toast.LENGTH_SHORT ).show();
                Intent createIntent = new Intent( cont.getApplicationContext(), diarycreateactivity.class );
                cont.startActivity( createIntent );
            }
        } );

        selectDateOriginX = ( int )viewX + ( viewWidth / 2 );
        selectDateOriginY = ( int )viewY + ( viewHeight / 2 );
        dateToSelectDate( dateManager );
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
        int day = currDateManager.get( Calendar.DATE );

        currDateManager.set( Calendar.DATE, 1 );
        currDateManager.add( Calendar.MONTH, amount );
        currDateManager.set( Calendar.DATE, Math.min( day, currDateManager.getLastDayOfMonth( currDateManager ) ) );
        OutsideDateManagerUpdate();
        viewX += viewWidth * amount;
        selectDateOriginX -= viewWidth * amount;

        dateToSelectDate( currDateManager );
        invalidate();
    }

    public void OutsideDateManagerUpdate() {
        preDateManager.set( Calendar.DATE, 1 );
        nextDateManager.set( Calendar.DATE, 1 );
        preDateManager.set( Calendar.MONTH, currDateManager.get( Calendar.MONTH ) - 1 );
        nextDateManager.set( Calendar.MONTH, currDateManager.get( Calendar.MONTH ) + 1 );
        preDateManager.set( Calendar.YEAR, currDateManager.get( Calendar.YEAR ) );
        nextDateManager.set( Calendar.YEAR, currDateManager.get( Calendar.YEAR ) );
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
    }

    // ---------------
    // 좌표 커서 위치 날짜로 설정
    // ---------------
    public void dateToSelectDate( DateManagement date ) {
        int year;
        int month;
        int day;
        int pos;
        String dayStr;

        pos = date.getFirstWeekDayOfMonth( date ) - 2 + date.get( Calendar.DATE );
        setSelectDateX( pos % 7 );
        setSelectDateY( pos / 7 );

        year = date.get( Calendar.YEAR );
        month = date.get( Calendar.MONTH ) + 1;
        day = date.get( Calendar.DATE );
        dayStr = year + "년 " + month + "월 " + day + "일";
        textToDate( dayStr );
    }

    // ---------------
    // 날짜 바깥 선택에 의한 이동인지 확인
    // ---------------
    public void selectMonthChange( DateManagement date ) {
        int year;
        int month;
        int day;
        int amount;
        String dayStr;

        year = date.get( Calendar.YEAR );
        month = date.get( Calendar.MONTH );
        day = ( selectDateX + 2 + ( selectDateY * 7 ) ) - date.getFirstWeekDayOfMonth( date );
        if( date.getDay( date, day, Calendar.MONTH ) != month ) {
            amount = date.getDay( date, day, Calendar.MONTH ) - month;
            if( Math.abs( amount ) > 1 ) {
                amount = ( int )Math.signum( amount ) * ( -1 );
            }
            monthChange( amount );
        }
        date.set( Calendar.DATE, day );
        date.set( Calendar.MONTH, month );
        date.set( Calendar.YEAR, year );
        dateToSelectDate( date );

        year = date.get( Calendar.YEAR );
        month = date.get( Calendar.MONTH ) + 1;
        day = date.get( Calendar.DATE );
        dayStr = year + "년 " + month + "월 " + day + "일";
        textToDate( dayStr );
    }

    public void textToDate( String text ) {
        if( attrViewIsCalendar ) {
            txtDate.setText( text );
            txtEventDate.setText( Html.fromHtml( "<u>" + text + "</u>" ) );
        }
        else {
            Date.setText( text );
            DiaryView.setText( Html.fromHtml( "<u>" + text + "</u>" ) );
        }
    }

    // ---------------
    // 동작 묘화
    // ---------------
    public void anim() {
        int viewWeekHeight = attrWeekHeight + attrNumberSize;
        int spaceX = ( viewWidth ) / 7;
        int spaceY = ( viewHeight - viewWeekHeight ) / 6;
        int disX = selectDateOriginX - ( spaceX * selectDateX );
        int disY = selectDateOriginY - ( spaceY * selectDateY );
        boolean flag = false;

        if( Math.abs( viewX ) > 5 && !isTouch ) {
            viewX /= 1.5;
            flag = true;
        }
        else if( Math.abs( viewX ) <= 5 && viewX != 0 ) {
            viewX = 0;
            flag = true;
        }

        if( Math.abs( disX ) > 5 ) {
            selectDateOriginX -= disX / 3;
            flag = true;
        }
        else if( Math.abs( disX ) <= 5 && disX != 0 ) {
            selectDateOriginX = spaceX * selectDateX;
            flag = true;
        }

        if( Math.abs( disY ) > 5 ) {
            selectDateOriginY -= disY / 3;
            flag = true;
        }
        else if( Math.abs( disY ) <= 5 && disY != 0 ) {
            selectDateOriginY = spaceY * selectDateY;
            flag = true;
        }

        if ( flag ) {
            invalidate();
        }
    }

    // ---------------
    // 달력 그리기
    // ---------------
    public void drawCalendar( Canvas canvas, DateManagement date, int offsetRow ) {
        int viewWeekHeight = attrWeekHeight + attrNumberSize;
        int firstWeek = date.getFirstWeekDayOfMonth( date );
        int dayCount = firstWeek - ( ( firstWeek - 1 ) * 2 ) - 7;
        int spaceX = ( viewWidth ) / 7;
        int spaceY = ( viewHeight - viewWeekHeight ) / 6;
        long gridX;
        long gridY;
        int numberColor = Color.rgb( 0, 0, 0 );
        int blueColor = Color.rgb( 0, 0, 255 );
        int redColor = Color.rgb( 255, 0, 0 );
        int diffColor = Color.rgb( 200, 200, 200 );

        for( int i = 0; i < 7; i++ ) {
            for( int j = 0; j < 7; j++ ) {
                gridX = viewX + ( j * spaceX ) + ( viewWidth * offsetRow );
                gridY = viewY + ( i * spaceY ) - ( ( long )Math.signum( i ) * ( spaceY - viewWeekHeight ) );
                paint.setColor( attrDefaultColor );
                paint.setStyle( Paint.Style.STROKE );
                canvas.drawRect( gridX, gridY, gridX + spaceX - 1, gridY + spaceY - 1 - ( 1 - ( long )Math.signum( i ) ) * ( spaceY - viewWeekHeight ), paint );

                paint.setStyle( Paint.Style.FILL );
                if( dayCount <= 0 || dayCount > date.getLastDayOfMonth( date ) ) { paint.setColor( diffColor ); }
                else if( j == 0 ) { paint.setColor( redColor ); }
                else if( j == 6 ) { paint.setColor( blueColor ); }
                else { paint.setColor( numberColor ); }

                if( i == 0 ) {
                    paint.setTextAlign( Paint.Align.CENTER );
                    DateFormatSymbols dateFormatSymbols = new DateFormatSymbols( Locale.KOREAN );
                    canvas.drawText( dateFormatSymbols.getShortWeekdays()[ j + 1 ], gridX + ( spaceX / 2 ), viewY + ( ( viewWeekHeight + ( viewWeekHeight / 2 ) ) / 2 ), paint );
                }
                else {
                    paint.setTextAlign( Paint.Align.LEFT );
                    canvas.drawText( String.valueOf( date.getDay( date, dayCount, Calendar.DATE ) ), gridX + attrNumberSpaceX, gridY + attrNumberSpaceY + attrNumberSize, paint );
                }
                dayCount++;
            }
        }
    }

    // ---------------
    // 커서 그리기
    // ---------------
    public void drawSelect( Canvas canvas ) {
        int viewWeekHeight = attrWeekHeight + attrNumberSize;
        int spaceX = ( viewWidth ) / 7;
        int spaceY = ( viewHeight - viewWeekHeight ) / 6;
        int selectColor = Color.rgb( 255, 0, 0 );

        paint.setColor( selectColor );
        paint.setStyle( Paint.Style.STROKE );
        paint.setStrokeWidth( 8 );
        canvas.drawRect( viewX + selectDateOriginX,
                viewY + viewWeekHeight + selectDateOriginY,
                viewX + selectDateOriginX + spaceX,
                viewY + viewWeekHeight + selectDateOriginY + spaceY, paint );

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
            invalidate();
            isTouch = false;
        }

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

        paint.setTextSize( attrNumberSize + attrNumberSpaceY );
        paint.setStyle( Paint.Style.FILL );
        paint.setStrokeWidth( 4 );

        paint.setColor( attrWeekBackgroundColor );
        canvas.drawRect( viewX, viewY, viewX + viewWidth, viewY + viewWeekHeight, paint );
        paint.setColor( attrTomonthBackgroundColor );
        canvas.drawRect( viewX, viewY + viewWeekHeight, viewX + viewWidth, viewY + viewHeight, paint );

        drawCalendar( canvas, currDateManager, 0 );
        drawCalendar( canvas, preDateManager, -1 );
        drawCalendar( canvas, nextDateManager, 1 );

        drawSelect( canvas );

        anim();
        if( attrViewIsCalendar ) {
            if( txtDate == null ) {
                initCalendar();
            }
        }
        else {
            if( Date == null ) {
                initDiary();
            }
        }

    }

}
