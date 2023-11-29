package com.example.soil_texture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startnow extends AppCompatActivity {
   private Button climate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_startnow);

        climate=findViewById(R.id.Cropnext);
        climate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move=new Intent(startnow.this,Climate.class);
                startActivity(move);
                finish();
            }
        });

    }
}