package com.example.zubako.caliary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class CaliaryView extends View {

    private static final int EVENT = 0;
    private static final int ANNIVERSARY = 1;
    private static final int HOLIDAY = 2;
    private Context cont;
    private long viewX;
    private long viewY;
    private int viewWidth;
    private int viewHeight;
    private int viewWeekHeight;
    private int viewCellWidth;
    private int viewCellHeight;
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
    private int attrNumberColor;
    private int attrReddayColor;
    private int attrBluedayColor;
    private int attrEventdayColor;
    private int attrOutsidedayColor;
    private int attrSelectorColor;
    private boolean isTouch;
    public String sel_date;

    private Paint paint;
    private Bitmap bitmap;
    private Canvas subCanvas;
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

    private SampleEventListener mSampleEventListener;
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
            sel_date = "";
            this.cont = cont;
            viewX = 0;
            viewY = 0;
            paint = new Paint();
            dateManager = new DateManagement();
            currDateManager = new DateManagement();
            preDateManager = new DateManagement();
            nextDateManager = new DateManagement();
            OutsideDateManagerUpdate();
            detector = new GestureDetectorCompat( getContext(), new CaliaryController( this ));
            final DBHelper dbHelper = new DBHelper(cont, "Caliary.db", null, 1);
//        dbHelper.resetSchedule(); //스케줄 정보 리셋
            Selected_date.getInstance().setDbHelper(dbHelper);


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
                attrNumberColor = typedArray.getColor( R.styleable.CaliaryView_numberColor, Color.rgb( 0, 0, 0 ) );
                attrReddayColor = typedArray.getColor( R.styleable.CaliaryView_reddayColor, Color.rgb( 255, 0, 0 ) );
                attrBluedayColor = typedArray.getColor( R.styleable.CaliaryView_bluedayColor, Color.rgb( 0, 0, 255 ) );
                attrEventdayColor = typedArray.getColor( R.styleable.CaliaryView_eventdayColor, Color.rgb( 0, 255, 0 ) );
                attrOutsidedayColor = typedArray.getColor( R.styleable.CaliaryView_outsidedayColor, Color.rgb( 200, 200, 200 ) );
                attrSelectorColor = typedArray.getColor( R.styleable.CaliaryView_selectorColor, Color.rgb( 255, 0, 0 ) );
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
                Intent intent2 = new Intent(cont.getApplicationContext(), CreateSchedule.class);
                cont.startActivity( intent2 );
            }
        } );

        Log.d("sch","initCal");
        EventDateBase item = new EventDateBase();

        selectDateOriginX = ( int )viewX + ( viewWidth / 2 );
        selectDateOriginY = ( int )viewY + ( viewHeight / 2 );
        dateToSelectDate( dateManager );

        bitmap = Bitmap.createBitmap( viewWidth * 3, viewHeight * 3, Bitmap.Config.ARGB_8888 );
        subCanvas = new Canvas( bitmap );
        subCanvas.translate( viewWidth - viewX, viewY );
        drawBitmap( subCanvas );
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
                createIntent.putExtra("sel_date",sel_date);
                cont.startActivity( createIntent );
            }
        } );

        selectDateOriginX = ( int )viewX + ( viewWidth / 2 );
        selectDateOriginY = ( int )viewY + ( viewHeight / 2 );
        dateToSelectDate( dateManager );

        bitmap = Bitmap.createBitmap( viewWidth * 3, viewHeight, Bitmap.Config.ARGB_8888 );
        subCanvas = new Canvas( bitmap );
        subCanvas.translate( viewWidth - viewX, viewY );
        drawBitmap( subCanvas );
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
    // get : 현재 날짜
    // ---------------
    public DateManagement getDate() { return currDateManager; }

    // ---------------
    // getAttrs : 요일칸 높이
    // ---------------
    public int weekHeight() {
        return attrWeekHeight;
    }

    // ---------------
    // get : 달력
    // ---------------
    public DateManagement getCurrDateManager() {
        return currDateManager;
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
        bitmap.recycle();
        bitmap = Bitmap.createBitmap( viewWidth * 3, viewHeight, Bitmap.Config.ARGB_8888 );
        subCanvas = new Canvas( bitmap );
        subCanvas.translate( viewWidth - viewX, viewY );
        drawBitmap( subCanvas );
        invalidate();
    }

    // ---------------
    // 외부 달력 검토
    // ---------------
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
        int count;

        for( count = 1; viewX + ( count * viewCellWidth ) < x;  ) {
            count++;
        }
        selectDateX = count - 1;

        for( count = 1; viewY + ( ( count * viewCellHeight ) + attrWeekHeight + attrNumberSize ) < y;  ) {
            count++;
        }
        selectDateY = count - 1;

        selectMonthChange( currDateManager );
    }
    public void setSampleEventListener(SampleEventListener listener){
        this.mSampleEventListener = listener;
        Log.d("mListener","created");
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
        sel_date = ""+year+""+month+""+day;
        Selected_date.getInstance().setSel_date(sel_date);
        if(Selected_date.getInstance().getItem()==1){
            Selected_date.getInstance().setMemoView(Selected_date.getInstance().getDbHelper().getResult(sel_date));
        }

        if(mSampleEventListener!=null){
            Log.d("Listener listen",":"+sel_date);
            mSampleEventListener.onReceivedEvent();
        } else{
            Log.d("Listener not listen",":"+sel_date);
        }

        EventDateBase item = new EventDateBase();
        final ArrayList<String> schedules;
        schedules = Selected_date.getInstance().getDbHelper().getTitleResult(Selected_date.getInstance().getSel_date());
        if(schedules!=null){
            try {
                if (!schedules.get(0).equals("")) {
                    listEventDateAdapter.items.clear();
                    Collections.sort(schedules, new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                    for (int i = 0; i < schedules.size(); i++) {
                        Log.d("sch", i + ":" + schedules.get(i));
                        item = new EventDateBase();
                        String str_schs = schedules.get(i).substring(0,14)+schedules.get(i).substring(schedules.get(i).indexOf('/')+1,schedules.get(i).length());
                        item.setEventDateName(str_schs);
                        listEventDateAdapter.items.add(item);
                    }
                } else {
                    Log.d("no schedule", ":" + sel_date);
                }
            }catch (Exception e){
                try {
                    listEventDateAdapter.items.clear();
                    Log.d("error","schedules.get(0).equals(\"\"))");
                }catch (Exception e1)
                {
                    Log.d("error","listEventDateAdapter.items.clear();");
                }
            }
            try {
                listEventDateAdapter.notifyDataSetChanged();
                listEventDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent editScheduleIntent = new Intent(cont.getApplicationContext(),EditSchedule.class);
                        Selected_date.getInstance().setSch_id(Integer.parseInt(schedules.get(position).substring(14,schedules.get(position).indexOf('/'))));
                        Log.d("sch_id",":"+schedules.get(position).substring(14,schedules.get(position).indexOf('/')));
                        cont.startActivity(editScheduleIntent);
                    }
                });
            }catch (Exception e2){
                Log.d("error","listEventDateAdapter.notifyDataSetChanged();");
            }
        }
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
        Selected_date.getInstance().setSel_date(""+year+month+day);
        textToDate( dayStr );
    }

    // ---------------
    // 날짜에 대한 X 위치
    // ---------------
    public long getViewXforDate( DateManagement date, int day ) {
        int pos = date.getFirstWeekDayOfMonth( date ) - 2 + day;

        return ( pos % 7 ) * viewCellWidth;
    }

    // ---------------
    // 날짜에 대한 Y 위치
    // ---------------
    public long getViewYforDate( DateManagement date, int day ) {
        int pos = date.getFirstWeekDayOfMonth( date ) - 2 + day;

        return ( ( pos / 7 ) * viewCellHeight ) + viewWeekHeight;
    }

    // ---------------
    // 날짜 문자 설정
    // ---------------
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
        int disX = selectDateOriginX - ( viewCellWidth * selectDateX );
        int disY = selectDateOriginY - ( viewCellHeight * selectDateY );
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
            selectDateOriginX = viewCellWidth * selectDateX;
            flag = true;
        }

        if( Math.abs( disY ) > 5 ) {
            selectDateOriginY -= disY / 3;
            flag = true;
        }
        else if( Math.abs( disY ) <= 5 && disY != 0 ) {
            selectDateOriginY = viewCellHeight * selectDateY;
            flag = true;
        }

        if ( flag ) {
            invalidate();
        }
    }

    // ---------------
    // 기본 배경 객체 그리기
    // ---------------
    public void drawBitmap( Canvas canvas ) {
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

        drawDateData( canvas, currDateManager );
    }

    // ---------------
    // 달력 그리기
    // ---------------
    public void drawCalendar( Canvas canvas, DateManagement date, int offsetRow ) {
        int firstWeek = date.getFirstWeekDayOfMonth( date );
        int dayCount = firstWeek - ( ( firstWeek - 1 ) * 2 ) - 7;
        long gridX;
        long gridY;

        for( int i = 0; i < 7; i++ ) {
            for( int j = 0; j < 7; j++ ) {
                gridX = viewX + ( j * viewCellWidth ) + ( viewWidth * offsetRow );
                gridY = viewY + ( i * viewCellHeight ) - ( ( long )Math.signum( i ) * ( viewCellHeight - viewWeekHeight ) );
                paint.setColor( attrDefaultColor );
                paint.setStyle( Paint.Style.STROKE );
                canvas.drawRect( gridX, gridY, gridX + viewCellWidth - 1, gridY + viewCellHeight - 1 - ( 1 - ( long )Math.signum( i ) ) * ( viewCellHeight - viewWeekHeight ), paint );

                paint.setStyle( Paint.Style.FILL );
                if( i != 0 && ( dayCount <= 0 || dayCount > date.getLastDayOfMonth( date ) ) ) { paint.setColor( attrOutsidedayColor ); }
                else if( j == 0 ) { paint.setColor( attrReddayColor ); }
                else if( j == 6 ) { paint.setColor( attrBluedayColor ); }
                else { paint.setColor( attrNumberColor ); }

                if( i == 0 ) {
                    paint.setTextAlign( Paint.Align.CENTER );
                    DateFormatSymbols dateFormatSymbols = new DateFormatSymbols( Locale.KOREAN );
                    canvas.drawText( dateFormatSymbols.getShortWeekdays()[ j + 1 ], gridX + ( viewCellWidth / 2 ), viewY + ( ( viewWeekHeight + ( viewWeekHeight / 2 ) ) / 2 ), paint );
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
    // 이벤트 그리기
    // ---------------
    public void drawDateData( Canvas canvas, DateManagement date ) {
        if( attrViewIsCalendar ) {
            drawEventDate( canvas, date );
        }
        else {
            drawDiaryData( canvas, date );
        }
    }

    // ---------------
    // 달력에 이벤트 그리기
    // ---------------
    public void drawEventDate( Canvas canvas, DateManagement date ) {
        int month;
        int day;
        int maxPos = 2;
        int pos;

        month = date.get( Calendar.MONTH ) + 1;
        for( day = 1; day <= date.getLastDayOfMonth( date ); day++ ) {
            pos = 0;
            if( EventDateBase.ANNIVERSARY.containsKey( ( 100 * month ) + day ) ) {
                drawEvent( canvas, date, day, ANNIVERSARY, maxPos, pos );
                pos++;
            }
            if( EventDateBase.HOLIDAY.containsKey( ( 100 * month ) + day ) ) {
                drawEvent( canvas, date, day, HOLIDAY, maxPos, pos );
            }
        }
    }

    // ---------------
    // 다이어리에 데이터 그리기
    // ---------------
    public void drawDiaryData( Canvas canvas, DateManagement date ) {
        int day;

        float drawSpaceHeight = attrNumberSize + attrNumberSpaceY;
        float eventX;
        float eventY;
        float eventWidth;
        float eventHeight;

        for( day = 1; day <= date.getLastDayOfMonth( date ); day++ ) {
            eventX = viewX + getViewXforDate(date, day) + drawSpaceHeight;
            eventY = viewY + getViewYforDate(date, day) + drawSpaceHeight;
            eventWidth = viewX + getViewXforDate(date, day) + viewCellWidth - 6;
            eventHeight = viewY + getViewYforDate(date, day) + viewCellHeight - 6;

            Bitmap image = BitmapFactory.decodeResource( cont.getResources(), R.drawable.angry );
            canvas.drawBitmap( image, null, new Rect( ( int )eventX, ( int )eventY, ( int )eventWidth, ( int )eventHeight ), paint );
        }
    }

    // ---------------
    // 이벤트에 설정된 정보 표시
    // ---------------
    public void drawEvent( Canvas canvas, DateManagement date, int day, int eventKind, int maxPos, int pos ) {
        float drawSpaceHeight = attrNumberSize + attrNumberSpaceY;
        float drawHeight = ( viewCellHeight - drawSpaceHeight - 6 ) / maxPos;

        float eventX = viewX + getViewXforDate(date, day) + 6;
        float eventY = viewY + getViewYforDate(date, day) + viewCellHeight - ( drawHeight * ( pos + 1 ) );
        float eventWidth = viewX + getViewXforDate(date, day) + viewCellWidth - 6;
        float eventHeight = viewY + getViewYforDate(date, day) + viewCellHeight - ( drawHeight * pos ) - 6;

        String eventName = "";
        Drawable res = getResources().getDrawable( R.drawable.gradient_holiday);
        Bitmap image;
        Canvas c;

        if ( pos < maxPos ) {
            paint.setTextSize( eventHeight - eventY - 2 );
            paint.setColor( attrNumberColor );
            paint.setStyle( Paint.Style.FILL );
            canvas.drawRect( eventX, eventY, eventWidth, eventHeight, paint );

            switch (eventKind) {
                case ANNIVERSARY: {
                    res = getResources().getDrawable( R.drawable.gradient_anniversary );
                    eventName = EventDateBase.ANNIVERSARY.get( ( 100 * ( date.get( Calendar.MONTH ) + 1 ) ) + day );

                    break;
                }
                case HOLIDAY: {
                    res = getResources().getDrawable( R.drawable.gradient_holiday );
                    eventName = EventDateBase.HOLIDAY.get( ( 100 * ( date.get( Calendar.MONTH ) + 1 ) ) + day );

                    break;
                }
                case EVENT: {
                    res = getResources().getDrawable( R.drawable.gradient_event );


                    break;
                }

            }

            image = Bitmap.createBitmap( res.getIntrinsicWidth(), res.getIntrinsicHeight(), Bitmap.Config.ARGB_8888 );
            c = new Canvas( image );
            res.setBounds( 0, 0, res.getIntrinsicWidth(), res.getIntrinsicHeight() );
            res.draw( c );
            canvas.drawBitmap( image, null, new Rect( ( int )eventX + 1, ( int )eventY + 1, ( int )eventWidth - 1, ( int )eventHeight - 1 ), paint );

            while( paint.measureText( eventName ) > eventWidth - eventX ) {
                if ( eventName.substring( eventName.length() - 3, eventName.length() ).equals( "..." ) ) {
                    eventName = eventName.substring( 0, Math.max( 0, eventName.length() - 4 ) ) + "...";
                }
                else if( eventName.equals( "..." ) ) {
                    break;
                }
                else {
                    eventName = eventName.substring( 0, Math.max( 0, eventName.length() - 1 ) ) + "...";
                }

            }

            canvas.drawText( eventName, eventX + 1, eventY + 1 + 45, paint );
            paint.setTextSize( eventHeight - eventY - 2 );
        }
    }

    // ---------------
    // 커서 그리기
    // ---------------
    public void drawSelect( Canvas canvas ) {
        paint.setColor( attrSelectorColor );
        paint.setStyle( Paint.Style.STROKE );
        paint.setStrokeWidth( 8 );
        canvas.drawRect( viewX + selectDateOriginX,
                viewY + viewWeekHeight + selectDateOriginY,
                viewX + selectDateOriginX + viewCellWidth,
                viewY + viewWeekHeight + selectDateOriginY + viewCellHeight, paint );
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

        viewWidth = widthSize + 2;
        if( heightMode == MeasureSpec.AT_MOST ) {
            viewHeight = attrViewDefaultHeight;
        }
        else {
            viewHeight = heightSize + 2;
        }

        viewWeekHeight = attrWeekHeight + attrNumberSize;
        viewCellWidth = ( viewWidth ) / 7;
        viewCellHeight = ( viewHeight - viewWeekHeight ) / 6;

        setMeasuredDimension( viewWidth, viewHeight );
    }
    // ---------------
    // Override : 그리기
    // ---------------
    @Override
    protected void onDraw( Canvas canvas ) {
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
        canvas.drawBitmap( bitmap, viewX - viewWidth, viewY, paint );
        drawSelect( canvas );
        anim();
    }

    public interface SampleEventListener {
        void onReceivedEvent();
    }

}
