package com.example.soil_texture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class operation extends AppCompatActivity {
    private Button agumented,Rename,processing,Result,prediction;
    private ProgressBar p5;
    private static String INPUT_PATH="";
    private static String OUTPUT_PATH="";
    private static final int NUM_SAMPLES = 15;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        agumented=findViewById(R.id.Agumented);
        Rename=findViewById(R.id.Rename);
        processing=findViewById(R.id.Processing);
        Result=findViewById(R.id.Result);
        prediction=findViewById(R.id.prediction);

        String phone=getIntent().getStringExtra("Phone");
        String soiltype=getIntent().getStringExtra("soiltype");
        String sampleid=getIntent().getStringExtra("sampleid");

        INPUT_PATH = Environment.getExternalStorageDirectory()+"/SaveImage/Test/"+soiltype+"/";
        OUTPUT_PATH = Environment.getExternalStorageDirectory()+"/SaveImage/Train/"+soiltype+"/";
        p5=findViewById(R.id.p5);
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python = Python.getInstance();
        agumented.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PyObject module = python.getModule("augmentation");
                    PyObject function = module.get("augment_images");
                    function.call(INPUT_PATH, OUTPUT_PATH, NUM_SAMPLES);
                    Toast.makeText(operation.this, "Image augmentation completed", Toast.LENGTH_SHORT).show();
                    agumented.setText("✔ Completed");
                }catch (Exception e){
                    Toast.makeText(operation.this,"Please Try again",Toast.LENGTH_LONG).show();
                }
            }
        });
        Rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PyObject module = python.getModule("rename");
                    PyObject function = module.get("renamechange");
                    function.call(OUTPUT_PATH);
                    Toast.makeText(operation.this, "Image Rename Completed", Toast.LENGTH_SHORT).show();
                    Rename.setText("✔ Completed");

                }
                catch (Exception e){
                    Toast.makeText(operation.this,"Please Try again",Toast.LENGTH_LONG).show();
                }
            }
        });
        processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                        PyObject module = python.getModule("soil_texture_analysis");
                        PyObject function = module.get("imageprocessing");
                        function.call(soiltype);
                        Toast.makeText(operation.this, "Image Processing Completed", Toast.LENGTH_SHORT).show();
                        processing.setText("✔ Completed");
                }catch (Exception e){
                    Toast.makeText(operation.this,"Please Try again",Toast.LENGTH_LONG).show();
                }
            }
        });
        prediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                     Toast.makeText(operation.this, "Soil Prediction Completed", Toast.LENGTH_SHORT).show();
                     prediction.setText("✔ Completed");
                }catch (Exception e){
                    Toast.makeText(operation.this,"Please Try again",Toast.LENGTH_LONG).show();
                }
            }
        });

        Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move=new Intent(operation.this,Result.class);
                try {
                    move.putExtra("Phone", phone);
                    move.putExtra("soiltype", soiltype);
                    move.putExtra("sampleid",sampleid);
                    startActivity(move);
                }
                catch (Exception e){
                    Toast.makeText(operation.this,"Please Try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}