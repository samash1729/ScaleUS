package com.example.scaleus;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spin;
    String[] values;
    double ref_size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spin = findViewById(R.id.spinner3);
        values = new String[]{"User Defined Object","Value 1 Coin","Value 2 Coin","Value 5 Coin","Value 10 Coin"};
        ArrayAdapter<String> adp = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,values);
        spin.setAdapter(adp);
        spin.setOnItemSelectedListener(this);
    }

    public void enter(View view)
    {
        Intent i = new Intent(MainActivity.this,measure.class);
        i.putExtra("refsize",ref_size);
        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),values[i]+" SELECTED",Toast.LENGTH_SHORT).show();
            switch (i) {
                case 0:
                    Toast.makeText(getApplicationContext(),"HI",Toast.LENGTH_SHORT).show();
                    showInputDialog();
                    break;
                case 1:
                    ref_size = 2.5;
                    Toast.makeText(getApplicationContext(), "Reference Value Changed to " + ref_size, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    ref_size = 2.5;
                    Toast.makeText(getApplicationContext(), "Reference Value Changed to " + ref_size, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    ref_size = 2.5;
                    Toast.makeText(getApplicationContext(), "Reference Value Changed to " + ref_size, Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    ref_size = 2.5;
                    Toast.makeText(getApplicationContext(), "Reference Value Changed to " + ref_size, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        @SuppressLint("InflateParams") View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String ans = String.valueOf(editText.getText());
                        try
                        {
                            ref_size = Double.parseDouble(ans);
                            Toast.makeText(getApplicationContext(),"Reference Value changed to "+ref_size,Toast.LENGTH_SHORT).show();
                        }
                        catch(NumberFormatException e)
                        {
                            Toast.makeText(getApplicationContext(),"Enter Valid Numerical Values",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
