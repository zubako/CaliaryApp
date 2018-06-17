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

public class MainActivity extends AppCompatActivity {
//    final DBHelper dbHelper = new DBHelper(this, "Caliary.db", null, 1);//DB 실행
    // ---------------
    // Main 액티비티의 화끈한 시작!
    CaliaryView caliaryView;
    // ---------------
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
//        Selected_date.getInstance().setDbHelper(dbHelper);
        caliaryView = new CaliaryView(this);
        caliaryView = findViewById(R.id.caliary_calendar);
        Selected_date.getInstance().setCaliaryView(caliaryView);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.main_menu, menu );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch( item.getItemId() ) {
            case R.id.diary: {
                Intent intent = new Intent( getApplicationContext(), DiaryActivity.class );
                Selected_date.getInstance().setItem(1);
                startActivity( intent );
                finish();

                return true;
            }
            default: {
                return false;
            }
        }
    }

}
