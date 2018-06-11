package com.example.zubako.caliary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zubako.caliary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SchItemList adapter;
    ArrayList<Schedule> schedules = new ArrayList<>();

    final String LOG_TAG="myLogs";
    com.example.zubako.caliary.CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth= new SimpleDateFormat("MMMM- yyyy", Locale.ENGLISH);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.sch_recycler);
        setData();
        setRecyclerView();

        final ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        compactCalendar=(com.example.zubako.caliary.CompactCalendarView)findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        Event ev1=new Event(Color.RED, 1486749600000L,"Teachers' Professional Day");
        compactCalendar.addEvent(ev1);

        compactCalendar.setListener(new com.example.zubako.caliary.CompactCalendarView.CompactCalendarViewListener(){
            @Override
            public void onDayClick(Date dateClicked){
                Context context =getApplicationContext();
                if(dateClicked.toString().compareTo("Fri Feb 10 18:00:00 AST 2017")==0){// 요일 월 일 시:분:초: AST 년
                    Toast.makeText(context,"Teachers' Professional Day",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"No Events Planned for that day",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent( context, com.example.zubako.caliary.CSActivity.class );
                    startActivity( intent );
                    Log.d(LOG_TAG,""+dateClicked.toString().compareTo("Fri Feb 10 18:00:00 AST 2017"));
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }


    void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SchItemList(getApplicationContext(),schedules);
        recyclerView.setAdapter(adapter);
    }
    void setData(){
        Date currentTime = new Date ();
        Schedule AA = new Schedule();
        schedules.add(AA);
        schedules.add(AA);
        schedules.add(AA);
        schedules.add(AA);
        schedules.add(AA);
        schedules.add(AA);

    }
}
