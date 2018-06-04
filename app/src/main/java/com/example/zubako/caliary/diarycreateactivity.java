package com.example.zubako.caliary;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class diarycreateactivity extends AppCompatActivity {
    EditText DiaryWrite;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diarycreate);

        DiaryWrite = (EditText) findViewById(R.id.DiaryWrite);
        Button SaveButton = (Button) findViewById(R.id.SaveButton);
        TextView textView = (TextView) findViewById(R.id.MemoView);
        findViewById(R.id.SaveButton).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v)
                    {
                        Intent intent_act = new Intent(getApplicationContext(), MainActivity.class);
                        intent_act.putExtra( "Memo", DiaryWrite.getText().toString() );
                        startActivity(intent_act);
                    }
                }
        );
        findViewById(R.id.CancelButton).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v)
                    {
                        Intent Cancel = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(Cancel);
                    }
                }
        );



    }
}
