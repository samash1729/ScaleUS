package com.example.scaleus;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

public class measure extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner spin;
    String[] values2;
    ImageView imgview;
    double ref_size;
    Button lastact;
    TextView dist;
    public String Tag = "check";

    double coin_actual_length,coin_screen_dist,object_screen_dist,object_actual_length;
    int distA,distB,distC,distD,min;
    PointF pointA = new PointF(800,800);

    PointF pointB = new PointF(900,800);

    PointF pointC = new PointF(700,900);

    PointF pointD = new PointF(800,900);

    private LineView mLineView1,mLineView2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        lastact = findViewById(R.id.lastact);
        spin = findViewById(R.id.spinner2);
        values2 = new String[]{"About Us","Log Data","Search Data"};
        ArrayAdapter<String> adp2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,values2);
        spin.setAdapter(adp2);
        spin.setOnItemSelectedListener(this);
        imgview = findViewById(R.id.imageView2);
        mLineView1 =  findViewById(R.id.lineView1);
        mLineView2 =  findViewById(R.id.lineView2);
        dist = findViewById(R.id.dist);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            ref_size = extras.getDouble("refsize");
        }

        mLineView1.setPointA(pointA);

        mLineView1.setPointB(pointB);

        mLineView2.setPointA(pointC);

        mLineView2.setPointB(pointD);

        mLineView1.set_color(1);

        mLineView2.set_color(2);

        mLineView1.draw();
        mLineView2.draw();
        Toast.makeText(getApplicationContext(),"RED LINE FOR COIN(user defined object) AND BLUE LINE FOR OBJECT",Toast.LENGTH_LONG).show();
        coin_screen_dist = Math.sqrt(Math.pow(pointA.x-pointB.x,2)+Math.pow(pointA.y-pointB.y,2));
        coin_actual_length = ref_size;
        object_screen_dist = Math.sqrt(Math.pow(pointC.x-pointD.x,2)+Math.pow(pointC.y-pointD.y,2));
        object_actual_length = object_screen_dist*(coin_actual_length/coin_screen_dist);
        Log.i(Tag,"LENGTH : "+object_actual_length);
        dist.setText("LENGTH : "+String.valueOf(object_actual_length));

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            lastact.setText(values2[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(getApplicationContext(),"SELECT REFERENCE OBJECT ! ",Toast.LENGTH_SHORT).show();
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 99: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showDialog();
                } else {
                    ActivityCompat.requestPermissions(measure.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            99);
                }
            }
        }
    }

    public void chimg(View view)
    {
        if (ContextCompat.checkSelfPermission(measure.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(measure.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(measure.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    99);

        }
        else {
            showDialog();

        }
    }

    private void showDialog()
    {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(measure.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            switch(requestCode)
            {
                case 1:
                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }

                    Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                    imgview.setImageBitmap(myBitmap);

                    break;
                case 2:
                    Glide.with(measure.this).load(data.getData())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgview);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(Tag,event.getX()+" "+event.getY());
        {
            distA = (int)Math.sqrt(Math.pow((pointA.x-event.getX()),2)+Math.pow((pointA.y-event.getY()),2));
            distB = (int)Math.sqrt(Math.pow((pointB.x-event.getX()),2)+Math.pow((pointB.y-event.getY()),2));
            distC = (int)Math.sqrt(Math.pow((pointC.x-event.getX()),2)+Math.pow((pointC.y-event.getY()),2));
            distD = (int)Math.sqrt(Math.pow((pointD.x-event.getX()),2)+Math.pow((pointD.y-event.getY()),2));
            min = Integer.MAX_VALUE;
            min = min<distA?min:distA;
            min = min<distB?min:distB;
            min = min<distC?min:distC;
            min = min<distD?min:distD;
            if(min==distA) {
                pointA.x = event.getX();
                pointA.y = event.getY();
                mLineView1.draw();
            }
            else if(min==distB)
            {
                pointB.x = event.getX();
                pointB.y = event.getY();
                mLineView1.draw();
            }
            else if(min==distC)
            {
                pointC.x = event.getX();
                pointC.y = event.getY();
                mLineView2.draw();
            }
            else {
                pointD.x = event.getX();
                pointD.y = event.getY();
                mLineView2.draw();
            }

        }
        coin_screen_dist = Math.sqrt(Math.pow(pointA.x-pointB.x,2)+Math.pow(pointA.y-pointB.y,2));
        coin_actual_length = ref_size;
        object_screen_dist = Math.sqrt(Math.pow(pointC.x-pointD.x,2)+Math.pow(pointC.y-pointD.y,2));
        object_actual_length = object_screen_dist*(coin_actual_length/coin_screen_dist);
        Log.i(Tag,"LENGTH : "+object_actual_length);
        dist.setText("LENGTH : "+ String.format("%.2f",object_actual_length));
        return super.onTouchEvent(event);
    }

    public void options(View view)
    {
        if(String.valueOf(lastact.getText()).equals("About Us"))
        {
            Intent p = new Intent(measure.this,aboutus.class);
            startActivity(p);
        }
        else if(String.valueOf(lastact.getText()).equals("Log Data"))
        {
            Intent i = new Intent(measure.this,logdata.class);
            startActivity(i);
        }
        else if(String.valueOf(lastact.getText()).equals("Search Data"))
        {
            Intent j = new Intent(measure.this,search.class);
            startActivity(j);
        }
    }
}
