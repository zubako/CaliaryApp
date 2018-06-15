package com.example.zubako.caliary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zubako.caliary.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DiaryActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    com.example.zubako.caliary.CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.ENGLISH);
    CaliaryView diary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        final TextView memoview = (TextView)findViewById( R.id.MemoView );
        final FloatingActionButton AddButton = (FloatingActionButton)findViewById(R.id.AddButton);
        diary = (CaliaryView)findViewById( R.id.Calendar );

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_act2 = new Intent(getApplicationContext(), diarycreateactivity.class);
                intent_act2.putExtra("Memo", memoview.getText().toString());
                startActivity(intent_act2);
            }
        });


        Intent memoview1 = getIntent();
        memoview.setText( memoview1.getStringExtra( "Memo" ) );

        String game_time_sc = memoview1.getStringExtra("TIME"); // MainActivity에서 "TIME"이란 키로 넘낀 인탠트값 가져오기
//        ImageView emoticon = (ImageView)findViewById(R.id.Emoticon);
//        switch (game_time_sc+""){
//            case"soso":
//            {
//                emoticon.setImageDrawable(getDrawable(R.drawable.soso));
//                break;
//            }
//            case"happy":
//            {
//                emoticon.setImageDrawable(getDrawable(R.drawable.happy));
//                break;
//            }
//            case"fun":
//            {
//                emoticon.setImageDrawable(getDrawable(R.drawable.fun));
//                break;
//            }
//            case"sad":
//            {
//                emoticon.setImageDrawable(getDrawable(R.drawable.sad));
//                break;
//            }
//            case"sleepy":
//            {
//                emoticon.setImageDrawable(getDrawable(R.drawable.sleepy));
//                break;
//            }
//            case"hurt":
//            {
//                emoticon.setImageDrawable(getDrawable(R.drawable.hurt));
//                break;
//            }
//            case"love":
//            {
//                emoticon.setImageDrawable(getDrawable(R.drawable.love));
//                break;
//            }
//            case"angry":
//            {
//                emoticon.setImageDrawable(getDrawable(R.drawable.angry));
//                break;
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.diary_menu, menu );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch( item.getItemId() ) {
            case R.id.calendar: {
                Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                startActivity( intent );
                finish();

                return true;
            }
            case R.id.statistic: {
                Intent Intent = new Intent( getApplicationContext(), Statistic.class );
                startActivity( Intent );
            }
            default: {
                return false;
            }
        }
    }

}
