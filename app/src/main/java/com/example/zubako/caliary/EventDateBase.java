package com.example.zubako.caliary;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EventDateBase {

    static final Map< Integer, String > ANNIVERSARY = Collections.unmodifiableMap( new HashMap< Integer, String >() { {
        put( ( 100 * 5 ) + 1, "근로자의 날" );
        put( ( 100 * 5 ) + 6, "노는 날날날sdfslkfjsldfjsdfsdj" );
        put( ( 100 * 5 ) + 8, "어버이 날abadhkajhkafh" );
        put( ( 100 * 5 ) + 15, "스승의 날멀까요오" );
        put( ( 100 * 6 ) + 1, "의병의 날지요요" );
    } } );

    static final Map< Integer, String > HOLIDAY = Collections.unmodifiableMap( new HashMap< Integer, String >() { {
        put( ( 100 * 5 ) + 5, "어린이날" );
        put( ( 100 * 5 ) + 22, "석가탄신일" );
        put( ( 100 * 6 ) + 6, "현충일" );
    } } );

    private String eventDateName;

    public void setEventDateName( String eventDateName ) {
        this.eventDateName = eventDateName;
    }

    public String getEventDateName() {
        return eventDateName;
    }

}
