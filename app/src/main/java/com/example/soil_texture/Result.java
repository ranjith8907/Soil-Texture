package com.example.soil_texture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Result extends AppCompatActivity {
    private TextView textView;
    private Button upload;
    StorageReference reference;
    private String fileContents,fileContents1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView=findViewById(R.id.Resultview);
        upload=findViewById(R.id.upload);
        String phone = getIntent().getStringExtra("Phone");
        String sampleid=getIntent().getStringExtra("sampleid");
        String customFilePath = "/storage/emulated/0/SaveImage/output.txt";
        String customFilePath1 = "/storage/emulated/0/SaveImage/Result.txt";
        reference= FirebaseStorage.getInstance().getReference().child(phone);
        File customFile = new File(customFilePath);
        try {
            // Check if the file exists
            if (customFile.exists()) {
                FileInputStream inputStream = new FileInputStream(customFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }

                // Close the file and handle the text
                inputStream.close();
                fileContents = stringBuilder.toString();
                textView.setText(fileContents);
                // Now you can use "fileContents" as needed
            } else {
               Toast.makeText(Result.this,"Does Not files",Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File customFile1 = new File(customFilePath1);
        try {
            // Check if the file exists
            if (customFile1.exists()) {
                FileInputStream inputStream = new FileInputStream(customFile1);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }

                // Close the file and handle the text
                inputStream.close();
                fileContents1 = stringBuilder.toString();
                // Now you can use "fileContents" as needed
            } else {
                Toast.makeText(Result.this,"Does Not files",Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   reference.child(sampleid).child("output.txt").putBytes(fileContents.getBytes()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           Toast.makeText(Result.this, "File Uploaded", Toast.LENGTH_LONG).show();
                       }
                   });
                reference.child(sampleid).child("Result.txt").putBytes(fileContents1.getBytes()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Result.this, "File Uploaded", Toast.LENGTH_LONG).show();
                        Intent move=new Intent(Result.this,startnow.class);
                        startActivity(move);
                        finish();
                    }
                });

            }
        });


    }
}