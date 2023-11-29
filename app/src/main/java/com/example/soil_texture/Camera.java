package com.example.soil_texture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class Camera extends AppCompatActivity {
    private static final int Request_code = 100;
    OutputStream outputStream;
    ImageButton mImagebutton;
    ImageView mImageView;
    String phone = "", soiltype = "",sampleid;
    private static int I_name = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        phone = getIntent().getStringExtra("Phone1");
        soiltype = getIntent().getStringExtra("soiltype");
        sampleid = getIntent().getStringExtra("sampleid");
        mImagebutton = findViewById(R.id.caputrepic);
        mImageView = findViewById(R.id.Imageview);
        I_name = 0;
        if (ContextCompat.checkSelfPermission(Camera.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Camera.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        mImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 100);
                }catch(Exception e){
                    Toast.makeText(Camera.this,"Please try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100) {
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mImageView.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(Camera.this,"Please Try again",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.save:
                if(ContextCompat.checkSelfPermission(Camera.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                    SaveImage();
                }else{
                    askPermission();
                }
                break;
            case R.id.process:
                Intent move=new Intent(Camera.this,operation.class);
                move.putExtra("Phone",phone);
                move.putExtra("soiltype",soiltype);
                move.putExtra("sampleid",sampleid);
                startActivity(move);
                break;
            default:
                Toast.makeText(Camera.this,"",Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Camera.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},Request_code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==Request_code){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    SaveImage();

            }else{
                Toast.makeText(Camera.this,"Please provide required permissions",Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void SaveImage() {
        try {

            String customFilePath = Environment.getExternalStorageDirectory()+"/SaveImage/Test/"+soiltype+"/";
            File customDirectory = new File(customFilePath);
            if (!customDirectory.exists()) {
                customDirectory.mkdirs(); // Create the custom directory if it doesn't exist.
            }


            String imageName = String.valueOf(I_name)+ ".jpg";
            File imageFile = new File(customDirectory, imageName);


            BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();


            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            I_name++;
            Toast.makeText(this, "Image Saved Sucessfully", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Image Not Saved Sucessfully", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}