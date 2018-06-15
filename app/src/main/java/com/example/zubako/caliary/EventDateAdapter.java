package com.example.zubako.caliary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventDateAdapter extends BaseAdapter {

    public ArrayList< EventDateBase > items = new ArrayList<>();

    @Override
    public Object getItem( int position ) {
        return items.get( position );
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        if( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if ( inflater != null ) {
                convertView = inflater.inflate(R.layout.listview_eventdate, parent, false);
            }
        }
        if( convertView != null ) {
            TextView txtEventDate = convertView.findViewById( R.id.txtEventDate );

            EventDateBase item = items.get( position );
            txtEventDate.setText( item.getEventDateName() );
        }

        return convertView;
    }

}

class EventDateBase {

    private String eventDateName;

    public void setEventDateName( String eventDateName ) {
        this.eventDateName = eventDateName;
    }

    public String getEventDateName() {
        return eventDateName;
    }

}
