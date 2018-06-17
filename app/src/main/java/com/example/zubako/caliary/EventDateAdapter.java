package com.example.zubako.caliary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
            ImageView imgPri = convertView.findViewById( R.id.imgPri );
            TextView txtEventDate = convertView.findViewById( R.id.txtEventDate );
            ImageView imgSet = convertView.findViewById( R.id.imageView );

            EventDateBase item = items.get( position );
            switch( item.getEventDateKind() ) {
                case 1: {
                    imgPri.setImageDrawable( convertView.getContext().getDrawable( R.drawable.symbol_anniversary ) );

                    break;
                }
                case 2: {
                    imgPri.setImageDrawable( convertView.getContext().getDrawable( R.drawable.symbol_holiday ) );

                    break;
                }
                default: {
                    imgPri.setImageDrawable( convertView.getContext().getDrawable( R.drawable.symbol_event ) );

                    break;
                }
            }
            if( item.getEventDateName() != null ) {
                txtEventDate.setText( item.getEventDateName() );
            }
            imgSet.setImageDrawable( item.getEventDateSet() );
        }

        return convertView;
    }



}
