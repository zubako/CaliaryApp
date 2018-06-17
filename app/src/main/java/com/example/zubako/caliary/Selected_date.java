package com.example.zubako.caliary;

import android.view.MenuItem;

public class Selected_date {

    private String sel_date="";
    private  CaliaryView caliaryView;
    private int item=0;
    private String memoView="메모 내용이 없습니다.";
    private DBHelper dbHelper;
    private int sch_id=-1;

    private Selected_date () {}
    private static class Singleton {
        private static final Selected_date instance = new Selected_date();
    }

    public static Selected_date getInstance () {
        System.out.println("create instance");
        return Singleton.instance;
    }

    public void setSel_date(String sel_date) {
        this.sel_date = sel_date;
    }

    public String getSel_date() {

        return sel_date;
    }

    public void setCaliaryView(CaliaryView caliaryView) {
        this.caliaryView = caliaryView;
    }

    public CaliaryView getCaliaryView() {

        return caliaryView;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getMemoView() {
        return memoView;
    }

    public void setMemoView(String memoView) {
        if(memoView==""){
            this.memoView="메모 내용이 없습니다.";
            return;
        }
        this.memoView = memoView;
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public int getSch_id() {
        return sch_id;
    }

    public void setSch_id(int sch_id) {
        this.sch_id = sch_id;
    }
}