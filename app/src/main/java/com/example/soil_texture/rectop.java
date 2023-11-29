package com.example.soil_texture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class rectop extends AppCompatActivity {
    private Button movelang;
    private EditText f_otp,s_otp,t_otp,fo_otp;
    private String f_otp1,s_otp1,t_otp1,fo_otp1;
    private ProgressBar p3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_rectop);
        String phone=getIntent().getStringExtra("Phone");
        movelang=findViewById(R.id.langmove);
        f_otp=findViewById(R.id.f_otp);
        s_otp=findViewById(R.id.s_otp);
        t_otp=findViewById(R.id.t_otp);
        fo_otp=findViewById(R.id.fo_otp);
        p3=findViewById(R.id.p3);
        p3.setVisibility(View.INVISIBLE);
        movelang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p3.setVisibility(View.VISIBLE);
                f_otp1=f_otp.getText().toString().trim();
                s_otp1=s_otp.getText().toString().trim();
                t_otp1=t_otp.getText().toString().trim();
                fo_otp1=fo_otp.getText().toString().trim();
                if(!TextUtils.isEmpty(f_otp1) && !TextUtils.isEmpty(s_otp1) && !TextUtils.isEmpty(t_otp1) && !TextUtils.isEmpty(fo_otp1)) {
                    Intent move = new Intent(rectop.this, Lang.class);
                    move.putExtra("Phone1",phone);
                    startActivity(move);
                    finish();
                }
                else{
                    p3.setVisibility(View.INVISIBLE);
                    fo_otp.setError("");
                    return;
                }
            }
        });
    }
}