package com.example.zubako.caliary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class diarycreateactivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diarycreate);

        findViewById(R.id.SaveButton).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v)
                    {
                        Intent intent_act = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent_act);
                    }
                }
        );

        final EditText DiaryWrite = (EditText) findViewById(R.id.DiaryWrite);
        Button SaveButton = (Button) findViewById(R.id.SaveButton);
        final TextView textView = (TextView) findViewById(R.id.MemoView);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(DiaryWrite.getText());
            }
        });
    }
}
