package com.example.zubako.caliary;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateManagement extends GregorianCalendar {

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

}
