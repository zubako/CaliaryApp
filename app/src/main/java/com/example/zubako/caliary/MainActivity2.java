package com.example.zubako.caliary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zubako.caliary.DBHelper;
import com.example.zubako.caliary.R;

public class MainActivity2 extends AppCompatActivity { //implements View.OnClickListener

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etID, etName, etEmail;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
    }
//
//        btnAdd=(Button)findViewById(R.id.btnAdd);
//        btnAdd.setOnClickListener(this);
//
//        btnRead=(Button)findViewById(R.id.btnRead);
//        btnRead.setOnClickListener(this);
//
//        btnClear=(Button)findViewById(R.id.btnClear);
//        btnClear.setOnClickListener(this);
//
//        btnUpd=(Button)findViewById(R.id.btnUpd);
//        btnUpd.setOnClickListener(this);
//
//        btnDel=(Button)findViewById(R.id.btnDel);
//        btnDel.setOnClickListener(this);
//
//        etName=(EditText)findViewById(R.id.etName);
//        etEmail=(EditText)findViewById(R.id.etEmail);
//        etID=(EditText)findViewById(R.id.etId);
//
//        dbHelper=new DBHelper(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        String name =etName.getText().toString();
//        String email =etEmail.getText().toString();
//        String id =etID.getText().toString();
//
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//
//        switch (view.getId()){
//            case R.id.btnAdd:
//                contentValues.put(DBHelper.KEY_NAME,name);
//                contentValues.put(DBHelper.KEY_MAIL,email);
//
//                database.insert(DBHelper.TABLE_CONTACTS,null,contentValues);
//                break;
//            case R.id.btnRead:
//                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS,null,null,null,null,null,null);
//
//                if(cursor.moveToFirst()){
//                    int idIndex=cursor.getColumnIndex(DBHelper.KEY_ID);
//                    int nameIndex=cursor.getColumnIndex(DBHelper.KEY_NAME);
//                    int emailIndex=cursor.getColumnIndex(DBHelper.KEY_MAIL);
//                    do{
//                        Log.d("mLog", "ID = " + cursor.getInt(idIndex)+
//                                ", name = " + cursor.getString(nameIndex) +
//                                ", email = "+ cursor.getString(emailIndex));
//                    }while (cursor.moveToNext());
//                }
//                else
//                    Log.d("mLog","0 rows");
//                Log.d("mLog","DB="+ (dbHelper.DATABASE_NAME)/*.equals(null)*/);
//                break;
//            case R.id.btnClear:
//                database.delete(DBHelper.TABLE_CONTACTS,null,null);
//                break;
//            case R.id.btnUpd:
//                if(id.equalsIgnoreCase("")){break;}
//                contentValues.put(DBHelper.KEY_MAIL,email);
//                contentValues.put(DBHelper.KEY_NAME,name);
//                int updCount = database.update(DBHelper.TABLE_CONTACTS,contentValues,DBHelper.KEY_ID + "= ?",new String[] {id});
//                Log.d("mLog","updates rows count = " + updCount);
//                break;
//            case R.id.btnDel:
//                if (id.equalsIgnoreCase("")){break;}
//                int delCount = database.delete(DBHelper.TABLE_CONTACTS,DBHelper.KEY_ID ,null);
//                Log.d("mLog","deleted rows count = " + delCount);
//                break;
//        }
//        dbHelper.close();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d("mLog","deleted DB");
//        this.deleteDatabase(dbHelper.DATABASE_NAME);
//    }
}
