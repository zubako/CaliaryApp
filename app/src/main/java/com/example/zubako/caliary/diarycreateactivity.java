package com.example.zubako.caliary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.zubako.caliary.R.id.second_group;

public class diarycreateactivity extends AppCompatActivity {
    EditText DiaryWrite;
    private RadioGroup firstrow;
    private RadioGroup secondrow;
    private boolean Check = true;
    private int CheckCompare = R.id.soso;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diarycreate);

        firstrow = (RadioGroup) findViewById(R.id.first_group);
        secondrow = (RadioGroup) findViewById(R.id.second_group);


        firstrow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checking) {
                if (checking != -1 && Check) {
                    Check = false;
                    secondrow.clearCheck();
                    CheckCompare= checking;
                }
                Check = true;
            }
        });

        secondrow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && Check) {
                    Check = false;
                    firstrow.clearCheck();
                    CheckCompare = checkedId;
                }
                Check = true;
            }
        });

        DiaryWrite = (EditText) findViewById(R.id.DiaryWrite);
        Button SaveButton = (Button) findViewById(R.id.SaveButton);
        Button CancelButton = (Button) findViewById(R.id.CancelButton);
        TextView textView = (TextView) findViewById(R.id.MemoView);
        findViewById(R.id.SaveButton).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        RadioGroup rgf = (RadioGroup) findViewById(R.id.first_group);
                        RadioGroup rgfg = (RadioGroup)findViewById(R.id.first_group); // 라디오그룹 객체 맵핑
                        RadioButton selectedRdo; // = (RadioButton)findViewById(R.id.soso); // rg 라디오그룹의 체크된(getCheckedRadioButtonId) 라디오버튼 객체 맵핑
                        RadioGroup rgs = (RadioGroup) findViewById(R.id.second_group);
                        RadioGroup rgfs = (RadioGroup)findViewById(R.id.second_group);
                        RadioButton selectedRdo2 = (RadioButton)findViewById(R.id.soso);
                        if ( rgf.getCheckedRadioButtonId() != -1 )
                        {
                            selectedRdo = (RadioButton)findViewById(rgf.getCheckedRadioButtonId());
                            selectedRdo2 = (RadioButton)findViewById(rgf.getCheckedRadioButtonId());
                        }
                        else
                        {
                            selectedRdo = (RadioButton)findViewById(rgs.getCheckedRadioButtonId());
                            selectedRdo2 = (RadioButton)findViewById(rgs.getCheckedRadioButtonId());
                        }
                        String selectedValue = selectedRdo.getText().toString(); // 해당 라디오버튼 객체의 값 가져오기
                        selectedValue = selectedValue.equals("무한") ? "00" : selectedValue; // 삼항연산자 (체크된 값이 "무한" 이 참이면 "00"으로, 거짓이면 원래 selectedValue 그대로)

                        String selectedValue2 = selectedRdo2.getText().toString();
                        selectedValue2 = selectedRdo2.getText().toString();// 해당 라디오버튼 객체의 값 가져오기
                        selectedValue2 = selectedValue2.equals("무한") ? "00" : selectedValue2; // 삼항연산자 (체크된 값이 "무한" 이 참이면 "00"으로, 거짓이면 원래 selectedValue 그대로)

                        Intent intent_act = new Intent(getApplicationContext(), DiaryActivity.class);
                        intent_act.putExtra("Memo", DiaryWrite.getText().toString());

                        rgf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @Override

                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                RadioButton radio_btn = (RadioButton) findViewById(checkedId);

                                switch (checkedId) {

                                    case R.id.soso:

                                        break;

                                    case R.id.happy:

                                        break;

                                    case R.id.fun:

                                        break;

                                    case R.id.sad:

                                        break;

                                }

                            }

                        });


                        Intent intent = new Intent(diarycreateactivity.this, DiaryActivity.class);    // 보내는 클래스, 받는 클래스
                        intent.putExtra("TIME", selectedValue); // "TIME"이란 키 값으로 selectedValue를 넘김
                        intent.putExtra("Memo", DiaryWrite.getText().toString());
                        startActivity(intent);

                        rgs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @Override

                            public void onCheckedChanged(RadioGroup group, int checkedId) {

                                RadioButton radio_btn = (RadioButton) findViewById(checkedId);

                                switch (checkedId) {

                                    case R.id.love:

                                        break;

                                    case R.id.hurt:

                                        break;

                                    case R.id.angry:

                                        break;

                                    case R.id.sleepy:

                                        break;

                                }

                            }

                        });
                        Intent intent2 = new Intent(diarycreateactivity.this, DiaryActivity.class);
                        intent.putExtra("TIME", selectedValue2); // "TIME"이란 키 값으로 selectedValue를 넘김
                        startActivity(intent);
                    }
                }
        );

        findViewById(R.id.CancelButton).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent_act2 = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivity(intent_act2);
                    }
                }
        );

        Intent memoview1 = getIntent();
        DiaryWrite.setText( memoview1.getStringExtra( "Memo" ) );


    }

    public void showType(View view) {
        if (CheckCompare == R.id.soso) {
            Toast.makeText(this, "soso", Toast.LENGTH_SHORT).show();
        } else if (CheckCompare == R.id.happy) {
            Toast.makeText(this, "happy", Toast.LENGTH_SHORT).show();
        } else if (CheckCompare == R.id.fun) {
            Toast.makeText(this, "fun", Toast.LENGTH_SHORT).show();
        } else if (CheckCompare == R.id.sad) {
            Toast.makeText(this, "sad", Toast.LENGTH_SHORT).show();
        } else if (CheckCompare == R.id.love) {
            Toast.makeText(this, "love", Toast.LENGTH_SHORT).show();
        } else if (CheckCompare == R.id.hurt) {
            Toast.makeText(this, "hurt", Toast.LENGTH_SHORT).show();
        } else if (CheckCompare == R.id.angry) {
            Toast.makeText(this, "angry", Toast.LENGTH_SHORT).show();
        } else if (CheckCompare == R.id.sleepy) {
            Toast.makeText(this, "sleepy", Toast.LENGTH_SHORT).show();
        }

    }
}
