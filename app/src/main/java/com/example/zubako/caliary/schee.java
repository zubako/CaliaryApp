package com.example.zubako.caliary;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class schee extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> ShowList = new ArrayList<String>();
        ShowList.add("일정 제목");
        ShowList.add("일정 장소");
        ShowList.add("일정 시간");
        ShowList.add("일정 내용");
        ShowList.add("D-Day 알림 설정");

        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,ShowList);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(Adapter);

        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setDivider(new ColorDrawable(Color.WHITE));
        list.setDividerHeight(2);
    }
}
