package com.example.zubako.caliary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
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
        try {
            db.execSQL("INSERT INTO MEMO VALUES('" + memo_date + "','" + memo + "', '" + emoticon + "');");
        } catch (Exception e){
            Log.d("already in memo at date",":"+memo_date);
        }
        db.close();
    }

    public void insert(Schedule s){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        try {
            db.execSQL("INSERT INTO SCHEDULE VALUES(null,'" + s.title + "', '" + s.sch_date + "', '" + s.sch_time + "', '" + s.d_day + "', '" + s.content + "', " + s.mark + ", " + s.anniversary + ", '" + s.area + "');");
        } catch (Exception e){
            Log.d("insert Error","schedule error");
        }
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
        db.execSQL("UPDATE MEMO SET memo='"+mMemo +"'" + " WHERE  Memo_date = '"+ mMemo_date+"';");
        db.execSQL("UPDATE MEMO SET emoticon='"+ mEmoticon +"'" + " WHERE  Memo_date = '"+ mMemo_date+"';");
        db.close();
    }

    public void update(int sch_id, String title, String sch_date, String sch_time, String d_day, String content, int mark, int anniversary, String area, int id){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("UPDATE SCHEDULE SET title='"+title +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET sch_date='"+ sch_date +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET sch_time='"+ sch_time +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET d_day='"+ d_day +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET content='"+ content +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET mark="+ mark  + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET anniversary="+ anniversary  + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET area='"+ area +"'" + " WHERE  sch_id = "+ id+";");
        db.close();
    }
    public void update(Schedule schedule, int id){
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("UPDATE SCHEDULE SET title='"+ schedule.title +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET sch_date='"+ schedule.sch_date +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET sch_time='"+ schedule.sch_time +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET d_day='"+ schedule.d_day +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET content='"+ schedule.content +"'" + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET mark="+ schedule.mark + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET anniversary="+ schedule.anniversary  + " WHERE  sch_id = "+ id+";");
        db.execSQL("UPDATE SCHEDULE SET area='"+ schedule.area +"'" + " WHERE  sch_id = "+ id+";");

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
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM MEMO WHERE memo_date = '" + memo_date + "'", null);
            if(cursor == null)
                return result;
            while (cursor.moveToNext()) {
                result += cursor.getString(1);
            }
        } catch (Exception e){
            return result;
        }


        return result;
    }

    public String getMemoContent(String memo_date){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";
        Cursor cursor = db.rawQuery("SELECT * FROM MEMO WHERE memo_date = '"+memo_date+"'", null);
        if(cursor == null)
            return result;
        while (cursor.moveToNext()) {
            result += cursor.getString(1);
        }
        return result;
    }

    public ArrayList<String> getTitleResult(String sch_date) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> results = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULE WHERE sch_date = '"+sch_date+"'", null);//
            while (cursor.moveToNext()) {
                String result="";
                if(cursor.getString(3).length()==3) {
                    result += "시간:0"+cursor.getString(3).substring(0,1)
                            +"시"+cursor.getString(3).substring(1,3)+"분 일정: "
                            +cursor.getInt(0)+"/"
                            + cursor.getString(1);
                    results.add(result);
                } else {
                    result += "시간:"+cursor.getString(3).substring(0,2)
                            +"시"+cursor.getString(3).substring(2,4)+"분 일정: "
                            +cursor.getInt(0)+"/"
                            + cursor.getString(1);
                    results.add(result);
                }
            }
        }catch (Exception e){
            Log.d("get Schedule:","error");
            return null;
        }

        return results;
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
    public String getEmoticon(String memo_date) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM MEMO WHERE memo_date = '" + memo_date + "'", null);
            if(cursor == null)
                return result;
            while (cursor.moveToNext()) {
                result += cursor.getString(2);
            }
        } catch (Exception e){
            return result;
        }
        return result;
    }
    public String getTitle(int sch_id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result="";
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULE WHERE sch_id = "+sch_id, null);//
            while (cursor.moveToNext()) {
                result += cursor.getString(1);
                }
            } catch (Exception e){
            Log.d("get title:","error");
            return result;
        }
        return result;
    }
    public String getArea(int sch_id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result="";
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULE WHERE sch_id = "+sch_id, null);//
            while (cursor.moveToNext()) {
                result += cursor.getString(8);
            }
        } catch (Exception e){
            Log.d("get Area:","error");
            return result;
        }
        return result;
    }
    public String getContent(int sch_id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result="";
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULE WHERE sch_id = "+sch_id, null);//
            while (cursor.moveToNext()) {
                result += cursor.getString(5);
            }
        } catch (Exception e){
            Log.d("get Content:","error");
            return result;
        }
        return result;
    }
    public int getAnniversary(int sch_id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        int result=0;
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULE WHERE sch_id = "+sch_id, null);//
            while (cursor.moveToNext()) {
                result = cursor.getInt(7);
            }
        } catch (Exception e){
            Log.d("get Anniversary:","error");
            return result;
        }
        return result;
    }
    public int getMark(int sch_id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        int result=0;
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULE WHERE sch_id = "+sch_id, null);//
            while (cursor.moveToNext()) {
                result = cursor.getInt(6);
            }
        } catch (Exception e){
            Log.d("get Mark:","error");
            return result;
        }
        return result;
    }
    public String getD_day(int sch_id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result="";
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULE WHERE sch_id = "+sch_id, null);//
            while (cursor.moveToNext()) {
                result += cursor.getString(4);
            }
        } catch (Exception e){
            Log.d("get D_day:","error");
            return result;
        }
        return result;
    }
    public String getTime(int sch_id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result="";
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM SCHEDULE WHERE sch_id = "+sch_id, null);//
            while (cursor.moveToNext()) {
                result += cursor.getString(3);
            }
        } catch (Exception e){
            Log.d("get time:","error");
            return result;
        }
        return result;
    }

    public void resetSchedule(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE SCHEDULE");
        db.execSQL("CREATE TABLE SCHEDULE (sch_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, sch_date TEXT, sch_time TEXT, d_day TEXT, content TEXT, mark INTEGER DEFAULT 0, anniversary INTEGER DEFAULT 0, area TEXT)");
        db.close();
    }

    public void getEmoticon(){

    }
}