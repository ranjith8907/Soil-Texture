package com.example.soil_texture;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class tickpage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_tickpage);
        String phone=getIntent().getStringExtra("Phone1");
        String soiltype=getIntent().getStringExtra("soiltype");
        String village=getIntent().getStringExtra("village");
        String name=getIntent().getStringExtra("name");
        String irrigation=getIntent().getStringExtra("irrigation");
        String landsize=getIntent().getStringExtra("landsize");
        String sampleid=getIntent().getStringExtra("sampleid");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(tickpage.this,Scanning.class);
                intent.putExtra("Phone",phone);
                intent.putExtra("soiltype",soiltype);
                intent.putExtra("village",village);
                intent.putExtra("name",name);
                intent.putExtra("irrigation",irrigation);
                intent.putExtra("landsize",landsize);
                intent.putExtra("sampleid",sampleid);
                startActivity(intent);
                finish();
            }
        },1500);
    }
}