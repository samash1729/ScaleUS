package com.example.scaleus;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class logdata extends AppCompatActivity {

    SQLiteDatabase sqdb;
    EditText name,len;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logdata);
        name = findViewById(R.id.name);
        len = findViewById(R.id.len);
        sqdb = openOrCreateDatabase("proddb",MODE_PRIVATE,null);
        sqdb.execSQL("CREATE TABLE IF NOT EXISTS prodtable(name VARCHAR,length VARCHAR);");
    }

    public void log_data(View view)
    {
        sqdb.execSQL("INSERT INTO prodtable VALUES('"+String.valueOf(name.getText())+"','"+String.valueOf(len.getText())+"');");
        Toast.makeText(getApplicationContext(),"INSERTED",Toast.LENGTH_SHORT).show();
    }
}
