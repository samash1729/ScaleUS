package com.example.scaleus;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class search extends AppCompatActivity {

    EditText sestr;
    SQLiteDatabase sqdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sestr = findViewById(R.id.sestr);
        sqdb = openOrCreateDatabase("proddb",MODE_PRIVATE,null);
        sqdb.execSQL("CREATE TABLE IF NOT EXISTS prodtable(name VARCHAR,length VARCHAR);");
    }

    public void searchme(View view)
    {
            Cursor cr = sqdb.rawQuery("SELECT length FROM prodtable WHERE name = '"+String.valueOf(sestr.getText())+"';",null);
            cr.moveToFirst();
        Toast.makeText(getApplicationContext(),"Length of "+sestr.getText()+" : "+cr.getString(0),Toast.LENGTH_SHORT).show();
        cr.close();
    }
}
