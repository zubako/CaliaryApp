package com.example.zubako.caliary;

import android.graphics.drawable.Drawable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventDateBase {

    public static final Map< Integer, String > ANNIVERSARY = Collections.unmodifiableMap( new HashMap< Integer, String >() { {
        put( ( 100 * 2 ) + 14, "밸런타인데이" );
        put( ( 100 * 3 ) + 14, "화이트데이" );
        put( ( 100 * 4 ) + 5, "식목일" );
        put( ( 100 * 5 ) + 8, "어버이날" );
        put( ( 100 * 5 ) + 15, "스승의날" );
        put( ( 100 * 7 ) + 17, "제헌절" );
        put( ( 100 * 10 ) + 1, "제헌절" );
    } } );

    public static final Map< Integer, String > HOLIDAY = Collections.unmodifiableMap( new HashMap< Integer, String >() { {
        put( ( 100 * 1 ) + 1, "신정" );
        put( ( 100 * 3 ) + 1, "삼일절" );
        put( ( 100 * 5 ) + 5, "어린이날" );
        put( ( 100 * 5 ) + 22, "석가탄신일" );
        put( ( 100 * 6 ) + 6, "현충일" );
        put( ( 100 * 8 ) + 15, "광복절" );
        put( ( 100 * 10 ) + 3, "개천절" );
        put( ( 100 * 10 ) + 9, "한글날" );
        put( ( 100 * 12 ) + 25, "한글날" );
    } } );

    private int eventDateKind;
    private String eventDateName;
    private Drawable eventDateSet;

    public EventDateBase() {}
    public EventDateBase( int kind, String name, Drawable set ) {
        this.eventDateKind = kind;
        this.eventDateName = name;
        this.eventDateSet = set;
    }

    public void setEventDateKind( int eventDateKind ) {
        this.eventDateKind = eventDateKind;
    }

    public void setEventDateName( String eventDateName ) {
        this.eventDateName = eventDateName;
    }

    public void setEventDateSet( Drawable eventDateSet ) {
        this.eventDateSet = eventDateSet;
    }

    public int getEventDateKind() {
        return eventDateKind;
    }

    public String getEventDateName() {
        return eventDateName;
    }

    public Drawable getEventDateSet() {
        return eventDateSet;
    }

    public EventDateBase addItem( int kind, String name, Drawable set ) {
        this.eventDateKind = kind;
        this.eventDateName = name;
        this.eventDateSet = set;

        return this;
    }

}
