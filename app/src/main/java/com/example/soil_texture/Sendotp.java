package com.example.soil_texture;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Sendotp extends AppCompatActivity {
    private Button sendotp;
    private EditText phone,pincode;
    private String ph,pin;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ProgressBar p2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sendotp);
        phone=findViewById(R.id.Phonenumber);
        pincode=findViewById(R.id.pincode);
        sendotp=findViewById(R.id.sendotp);
        p2=findViewById(R.id.p7);
        p2.setVisibility(View.INVISIBLE);
        database= FirebaseDatabase.getInstance();
        reference=database.getReference("Users");
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new File("/storage/emulated/0/SaveImage").delete();
                p2.setVisibility(View.VISIBLE);
                ph=phone.getText().toString().trim();
                pin=pincode.getText().toString().trim();
                if(!TextUtils.isEmpty(ph)){
                    if(!TextUtils.isEmpty(pin)){
                        Map<String,String> map=new HashMap<>();
                        map.put("PhoneNo",ph);
                        map.put("PhoneCode",pin);
                        reference.child(ph).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Sendotp.this, "Sucessfully", Toast.LENGTH_SHORT).show();
                                    Intent move = new Intent(Sendotp.this, rectop.class);
                                    move.putExtra("Phone", ph);
                                    startActivity(move);
                                    finish();
                                } else {
                                    Toast.makeText(Sendotp.this, "UnSucessfully", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    else{
                        p2.setVisibility(View.INVISIBLE);
                        pincode.setError("");
                        return;
                    }
                }
                else{
                    p2.setVisibility(View.INVISIBLE);
                    phone.setError("");
                    return;
                }

            }
        });

    }
}