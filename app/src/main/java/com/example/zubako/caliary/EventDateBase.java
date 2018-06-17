package com.example.zubako.caliary;

import android.graphics.drawable.Drawable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventDateBase {

    public static final Map< Integer, String > ANNIVERSARY = Collections.unmodifiableMap( new HashMap< Integer, String >() { {
        put( ( 100 * 5 ) + 1, "근로자의 날" );
        put( ( 100 * 5 ) + 8, "어버이 날" );
        put( ( 100 * 5 ) + 15, "스승의 날" );
        put( ( 100 * 6 ) + 1, "의병의 날" );
    } } );

    public static final Map< Integer, String > HOLIDAY = Collections.unmodifiableMap( new HashMap< Integer, String >() { {
        put( ( 100 * 5 ) + 5, "어린이날" );
        put( ( 100 * 5 ) + 22, "석가탄신일" );
        put( ( 100 * 6 ) + 6, "현충일" );
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
