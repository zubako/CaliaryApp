package com.example.zubako.caliary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreateSchedule  extends AppCompatActivity {
    private int year1,month1,day1,hour1,minute1;//여기는 일정 변수
    private int year2,month2,day2;//디데이 일정 변수

    TextView DATE,TIME,DATE2; //텍스트 뷰로 나오게 하기
    private EditText ed_TITLE, ed_AREA, ed_content;
    private CheckBox annivesary, mark;
    private Button bt_Save;
    private Button cancel_btn_sch;
    private Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_create);

        ed_TITLE = (EditText) findViewById(R.id.editText10);//title
        ed_AREA = (EditText) findViewById(R.id.editText2);//area
        ed_content = (EditText) findViewById(R.id.editText);//content
        DATE =  findViewById(R.id.A_DATE);//sch_date
        TIME =  findViewById(R.id.A_TIME);//sch_time
        DATE2 =  findViewById(R.id.B_DATE);//d_day
        annivesary = findViewById(R.id.checkBox1);//anniversary
        mark = findViewById(R.id.checkBox2);//mark
        bt_Save = findViewById(R.id.save_btn);
        cancel_btn_sch = findViewById(R.id.cancel_btn_sch);
        // 텍스트 뷰에 대한 속성 지정

        Calendar cal = new GregorianCalendar();
        if(Selected_date.getInstance().getSel_date().length()==7){
            year1 = Integer.parseInt(Selected_date.getInstance().getSel_date().substring(0,4));
            month1 = Integer.parseInt(Selected_date.getInstance().getSel_date().substring(4,5))-1;
            day1 = Integer.parseInt(Selected_date.getInstance().getSel_date().substring(5,7));
        } else if(Selected_date.getInstance().getSel_date().length()==8){
            year1 = Integer.parseInt(Selected_date.getInstance().getSel_date().substring(0,4));
            month1 = Integer.parseInt(Selected_date.getInstance().getSel_date().substring(4,6))-1;
            day1 = Integer.parseInt(Selected_date.getInstance().getSel_date().substring(6,8));
        }

        hour1 = cal.get(Calendar.HOUR_OF_DAY);
        minute1 = cal.get(Calendar.MINUTE);
        year2 = year1;
        month2 = month1;
        day2 = day1;
        // 시간들에 대한 속성 지정
        UpdateNow();

        bt_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String title = ed_TITLE.getText().toString();
                if(title.equals("")){
                    Toast.makeText( getApplicationContext(), "일정 제목이 입력되지 않았습니다.", Toast.LENGTH_SHORT ).show();
                } else{
                    String sch_date = ""+year1+(month1+1)+ day1;
                    String sch_time;
                    if(minute1<10)
                    {
                        sch_time = ""+ hour1 + "0"+ minute1;
                    } else {
                        sch_time =""+ hour1 + minute1;
                    }
                    String d_day = ""+ year2+ (month2 + 1)+day2;
                    String content = ed_content.getText().toString();
                    int v_mark;
                    if( mark.isChecked()){
                        v_mark = 1;
                    }
                    else{
                        v_mark = 0;
                    }

                    int v_anniversary;
                    if(annivesary.isChecked()){
                        v_anniversary = 1;
                    }else{
                        v_anniversary =0;
                    }
                    String area = ed_AREA.getText().toString();
                    schedule = new Schedule(title,sch_date,sch_time,d_day,content,v_mark,v_anniversary,area);
                    Toast.makeText( getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT ).show();
                    Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                    Selected_date.getInstance().getDbHelper().insert(schedule);
                    startActivity(intent1);
                }
            }
        });

        cancel_btn_sch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                Toast.makeText( getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT ).show();
                startActivity(intent1);
            }
        });
    }

    void UpdateNow() {
        DATE.setText(String.format("날짜:%d/%d/%d", year1, month1 + 1, day1));
        TIME.setText(String.format("시간:%2d:%2d", hour1, minute1));
        DATE2.setText(String.format("날짜:%d/%d/%d", year2, month2 + 1, day2));

    }
    public void mOnclick(View v) {
        if (v.getId() == R.id.A_DATE) {
            Toast.makeText(this,"날짜를 입력해 주세요.",Toast.LENGTH_LONG).show();
            new
                    DatePickerDialog(CreateSchedule.this, A_DATEset, year1, month1, day1).show();
        }
    }
    public void mOnclick1(View v) {
        if (v.getId() == R.id.B_DATE) {
            Toast.makeText(this,"날짜를 입력해 주세요.",Toast.LENGTH_LONG).show();
            new
                    DatePickerDialog(CreateSchedule.this, B_DATEset, year2, month2, day2).show();
        }
    }
    public void mOnclick2(View v) {
        if (v.getId() == R.id.A_TIME) {
            Toast.makeText(this,"시간을 입력해 주세요.",Toast.LENGTH_LONG).show();
            new
                    TimePickerDialog(CreateSchedule.this, A_TIMEset, hour1, minute1, false).show();
        }
    }
    public DatePickerDialog.OnDateSetListener A_DATEset = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year1 = year;
            month1 = month;
            day1 = dayOfMonth;
            UpdateNow();

        }
    };
    public DatePickerDialog.OnDateSetListener B_DATEset = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year2 = year;
            month2 = month;
            day2 = dayOfMonth;
            UpdateNow();

        }
    };
    public TimePickerDialog.OnTimeSetListener A_TIMEset = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour1 = hourOfDay;
            minute1 = minute;
            UpdateNow();
        }
    };
}