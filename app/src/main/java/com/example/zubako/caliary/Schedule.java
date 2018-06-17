package com.example.zubako.caliary;

import java.io.Serializable;

public class Schedule implements Serializable {
    public int sch_id;
    public String title;
    public String sch_date;
    public String sch_time;

    public void setSch_id(int sch_id) {
        this.sch_id = sch_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSch_date(String sch_date) {
        this.sch_date = sch_date;
    }

    public void setSch_time(String sch_time) {
        this.sch_time = sch_time;
    }

    public void setD_day(String d_day) {
        this.d_day = d_day;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setAnniversary(int anniversary) {
        this.anniversary = anniversary;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getSch_id() {
        return sch_id;
    }

    public String getTitle() {
        return title;
    }

    public String getSch_date() {
        return sch_date;
    }

    public String getSch_time() {
        return sch_time;
    }

    public String getD_day() {
        return d_day;
    }

    public String getContent() {
        return content;
    }

    public int getMark() {
        return mark;
    }

    public int getAnniversary() {
        return anniversary;
    }

    public String getArea() {
        return area;
    }

    public String d_day;
    public String content;
    public int mark;
    public int anniversary;
    public String area;

    public Schedule(){

    }

    public Schedule(String title, String sch_date, String sch_time, String d_day, String content, int mark,  int anniversary, String area){
        this.title = title;//일정제목
        this.sch_date = sch_date;//일정 년 월 일
        this.sch_time = sch_time;//일정 시 분
        this.d_day = d_day;// 알람 시작일
        this.content = content;//일정세부내용
        this.mark = mark;//중요도
        this.anniversary = anniversary;//매년울릴것인가
        this.area = area;//장소
    }

}

