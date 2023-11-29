package com.example.soil_texture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lang extends AppCompatActivity {
    private Button movepro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_lang);
        String phone=getIntent().getStringExtra("Phone1");
        movepro=findViewById(R.id.movepro);
        movepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move=new Intent(Lang.this,Profile.class);
                move.putExtra("Phone",phone);
                startActivity(move);
            }
        });
    }
}