package com.example.zubako.caliary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성

        db.execSQL("CREATE TABLE MEMO (memo_date TEXT PRIMARY KEY, memo TEXT, emoticon TEXT)");
        db.execSQL("CREATE TABLE SCHEDULE (sch_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, sch_date TEXT, sch_time TEXT, d_day TEXT, content TEXT, mark INTEGER DEFAULT 0, anniversary INTEGER DEFAULT 0, area TEXT)");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String memo_date, String memo, String emoticon){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MEMO VALUES(null,'"+ memo_date+"','"  + memo + "', '" + emoticon +  "');");
        db.close();
    }

    public void insert(Schedule s){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SCHEDULE VALUES(null,'"+ s.title +"', '" + s.sch_date + "', '" + s.sch_time + "', '" + s.d_day + "', '" + s.content + "', " + s.mark + ", " + s.anniversary+", '" + s.area + "');");
        db.close();
    }

    public void insert(String title, String sch_date, String sch_time, String d_day, String content, int mark, int anniversary, String area){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SCHEDULE VALUES(null,'"+ title +"', '" + sch_date + "', '" + sch_time + "', '" + d_day + "', '" + content + "', " + mark + ","+ anniversary+", '" + area + "');");
        db.close();
    }

    public void update(String mMemo_date, String mMemo, String mEmoticon) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE MEMO SET update  memo = '" +mMemo+ "', update emoticon = '"+ mEmoticon+"'," + "WHERE memo_date = '"+ mMemo_date+"');");
        db.close();
    }

    public void update(int sch_id, String title, String sch_date, String sch_time, String d_day, String content, int mark, int anniversary, String spot){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("UPDATE SCHEDULE SET update title'"+ title +"', update sch_date = '" + sch_date + "', update sch_time = '" + sch_time + "', " +
                "update d_day = '" + d_day + "', update content = '" + content + "', update mark = " + mark + ", update anniversary = " + anniversary +", update spot = '" + spot +"'," +
                " WHERE  sch_id = '"+ sch_id+"');");
        db.close();
    }

    public void delete(int num, String mMemo_date) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("DELETE FROM MEMO WHERE memo_date = '"+ mMemo_date+"');");
        db.close();
    }

    public void delete(int sch_id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("DELETE FROM SCHEDULE WHERE sch_id = '"+ sch_id+"');");
        db.close();
    }

    public String getResult(String memo_date) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MONEYBOOK WHERE memo_date = '"+memo_date+"'", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " , emocotion = "
                    + cursor.getInt(2)
                    + "\n";
        }

        return result;
    }

    public String getResult(int num, String sch_date) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULE ", null);//WHERE sch_date = '"+sch_date+"'
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + "\n"
                    + cursor.getString(1)
                    + " : "
                    + "\n"
                    + cursor.getString(2)
                    + " : "
                    + "\n"
                    + cursor.getString(3)
                    + " : "
                    + "\n"
                    + cursor.getString(4)
                    + " : "
                    + "\n"
                    + cursor.getString(5)
                    + " : "
                    + "\n"
                    + cursor.getString(6)
                    + " : "
                    + "\n"
                    + cursor.getString(7)
                    + " : "
                    + "\n"
                    + cursor.getString(7)
                    + "\n";
        }

        return result;
    }
}