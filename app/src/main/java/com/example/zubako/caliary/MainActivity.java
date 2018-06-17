package com.example.zubako.caliary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    CaliaryView calendar;

    // ---------------
    // Main 액티비티의 화끈한 시작!
    // ---------------
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        CaliaryView calendar = findViewById( R.id.caliary_calendar );

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item ){
        switch (item.getItemId()) {
            case R.id.diary: {
                Intent intent = new Intent( getApplicationContext(), DiaryActivity.class );
//                intent.putExtra( "year", calendar.getDate().get( Calendar.YEAR ) );
//                intent.putExtra( "month", calendar.getDate().get( Calendar.MONTH ) );
//                intent.putExtra( "date", calendar.getDate().get( Calendar.DATE ) );
                startActivity( intent );

                finish();

                return true;
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
