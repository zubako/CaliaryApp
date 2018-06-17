package com.example.zubako.caliary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.media.CamcorderProfile.get;

public class DiaryActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    CaliaryView ccc;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.ENGLISH);
    TextView memoview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ccc = findViewById( R.id.Calendar );

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        memoview = (TextView)findViewById( R.id.MemoView );
        final FloatingActionButton AddButton = (FloatingActionButton)findViewById(R.id.AddButton);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_act2 = new Intent(getApplicationContext(), diarycreateactivity.class);
                intent_act2.putExtra("Memo", memoview.getText().toString());
                startActivity(intent_act2);
            }
        });

        Intent memoview1 = getIntent();
        String date_memo = Selected_date.getInstance().getSel_date();
        String memo_content = memoview1.getStringExtra("Memo");
        String emoticon = memoview1.getStringExtra("TIME");
        if(memo_content!=null){
            if(Selected_date.getInstance().getDbHelper().getEmoticon(Selected_date.getInstance().getSel_date())==null){
                Selected_date.getInstance().getDbHelper().insert(date_memo,memo_content,emoticon);
                Toast.makeText(this,"저장되었습니다.", Toast.LENGTH_LONG).show();
            } else{
                Selected_date.getInstance().getDbHelper().update(date_memo,memo_content,emoticon);
                Toast.makeText(this,"수정되었습니다.", Toast.LENGTH_LONG).show();
            }
        }
        memoview.setText("내용이 없습니다.");
        String game_time_sc = memoview1.getStringExtra("TIME"); // MainActivity에서 "TIME"이란 키로 넘낀 인탠트값 가져오기

        ccc.setSampleEventListener(new CaliaryView.SampleEventListener() {
            @Override
            public void onReceivedEvent() {
                Log.d("listener ccc",":");
                try{
                memoview.setText(Selected_date.getInstance().getMemoView() );
                } catch (Exception e){
                    Log.d("listener error",":"+e.toString());
                }
            }
        });
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
                Selected_date.getInstance().setItem(0);
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

//
//    @Override
//    public void onClick(View v) {
//        Intent intent_act2 = new Intent(getApplicationContext(), diarycreateactivity.class);
//        intent_act2.putExtra("year",ccc.getCurrDateManager().get(Calendar.YEAR));
//        intent_act2.putExtra("month",ccc.getCurrDateManager().get(Calendar.MONTH));
//        intent_act2.putExtra("date",ccc.getCurrDateManager().get(Calendar.DATE));
//        intent_act2.putExtra("Memo", memoview.getText().toString());
//        startActivity(intent_act2);
//    }
}
