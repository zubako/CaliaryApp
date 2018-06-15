package com.example.zubako.caliary;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Statistic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic);

        HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);

        BarData data = new BarData(getDataSet());
        chart.setData(data);
        chart.animateXY(2000, 1500);
        chart.invalidate();
    }

    private BarDataSet getDataSet() {

        ArrayList<BarEntry> entries = new ArrayList();
        entries.add(new BarEntry(1f, 2));
        entries.add(new BarEntry(2f, 6));
        entries.add(new BarEntry(3f, 10));
        entries.add(new BarEntry(4f, 14));
        entries.add(new BarEntry(5f, 18));
        entries.add(new BarEntry(6f, 22));
        entries.add(new BarEntry(7f, 26));
        entries.add(new BarEntry(8f, 30));

        BarDataSet dataset = new BarDataSet(entries,"한달 통계");
        return dataset;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> labels = new ArrayList();
        labels.add("soso");
        labels.add("happy");
        labels.add("fun");
        labels.add("sad");
        labels.add("love");
        labels.add("hurt");
        labels.add("angry");
        labels.add("sleepy");
        return labels;
    }


}