package com.example.soil_texture;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class download extends AppCompatActivity {
    private Button downloadbtn;
    private EditText Reference;
    private ProgressBar p7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        downloadbtn=findViewById(R.id.btndownload);
        Reference=findViewById(R.id.sampleid);
        p7=findViewById(R.id.p7);
        String phone=getIntent().getStringExtra("phone");
        p7.setVisibility(View.INVISIBLE);
        downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sample= Reference.getText().toString().trim();
                if(!TextUtils.isEmpty(sample)){

                }
                else {
                    p7.setVisibility(View.VISIBLE);
                    Reference.setError("");
                    return;
                }
            }
        });

    }
}