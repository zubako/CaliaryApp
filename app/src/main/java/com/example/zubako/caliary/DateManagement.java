package com.example.zubako.caliary;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateManagement extends GregorianCalendar {

    // ---------------
    // Constructs
    // ---------------
    public DateManagement() {}
    public DateManagement( int year, int month, int date ) {
        this.set( Calendar.YEAR, year );
        this.set( Calendar.MONTH, year );
        this.set( Calendar.DATE, year );
    }

    // ---------------
    // get : 해당 달 1일의 요일
    // ---------------
    public int getFirstWeekDayOfMonth( GregorianCalendar calendar )
    {
        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );

        GregorianCalendar cloneCalendar = new GregorianCalendar( year, month, 1 );

        return cloneCalendar.get( Calendar.DAY_OF_WEEK );
    }

    // ---------------
    // get : 해당 달 마지막 일
    // ---------------
    public int getLastDayOfMonth( GregorianCalendar calendar ) {
        int count;
        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );

        GregorianCalendar cloneCalendar = new GregorianCalendar( year, month, 1 );
        for( count = 0; month == cloneCalendar.get( Calendar.MONTH ); count++ ) {
            cloneCalendar.add( Calendar.DATE, 1 );
        }

        return count;
    }

    // ---------------
    // get : 해당 달의 일을 적용한 날짜
    // ---------------
    public int getDay( GregorianCalendar calendar, int day, int field ) {
        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );

        GregorianCalendar cloneCalendar = new GregorianCalendar( year, month, day );

        return cloneCalendar.get( field );
    }

    // ---------------
    // get : 해당 달의 일을 더하여 적용한 날짜
    // ---------------
    public int getDayAmount( GregorianCalendar calendar, int amount, int field ) {
        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );
        int day = calendar.get( Calendar.DATE );

        GregorianCalendar cloneCalendar = new GregorianCalendar( year, month, day );
        cloneCalendar.add( Calendar.DATE, amount );

        return cloneCalendar.get( field );
    }

}
