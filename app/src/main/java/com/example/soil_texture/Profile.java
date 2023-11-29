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

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private Button tickmove;
    private EditText name,village,landsize,soiltype,irrigation;
    private String name1,village1,landsize1,soiltype1,irrigation1;
    private ProgressBar p4;
    private FirebaseDatabase database,database1;
    private DatabaseReference reference,sampleid;
    String id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile);
        String phone=getIntent().getStringExtra("Phone");
        name=findViewById(R.id.PerName);
        village=findViewById(R.id.village);
        landsize=findViewById(R.id.Landsize);
        soiltype=findViewById(R.id.Soiltype);
        irrigation=findViewById(R.id.Irrigation);
        tickmove=findViewById(R.id.tickmove);
        p4=findViewById(R.id.p4);
        database= FirebaseDatabase.getInstance();
        database1= FirebaseDatabase.getInstance();
        reference=database.getReference("Users");
        p4.setVisibility(View.INVISIBLE);
        database1=FirebaseDatabase.getInstance();
        database1.getReference("Reference").child("sample").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                String value= (String) datasnapshot.getValue();
                id = value;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tickmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int incsam=Integer.parseInt(id);
                System.out.println(incsam);
                incsam+=1;
                database1.getReference("Reference").child("sample").setValue(String.valueOf(incsam)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {

                    }
                });
                p4.setVisibility(View.VISIBLE);
                name1 = name.getText().toString().trim();
                village1 = village.getText().toString().trim();
                landsize1 = landsize.getText().toString().trim();
                soiltype1 = soiltype.getText().toString().trim();
                irrigation1 = irrigation.getText().toString().trim();
                if (!TextUtils.isEmpty(name1)) {
                    if (!TextUtils.isEmpty(village1)) {
                        if (!TextUtils.isEmpty(landsize1)) {
                            if (!TextUtils.isEmpty(soiltype1)) {
                                if (!TextUtils.isEmpty(irrigation1)) {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("PhoneNo", phone);
                                    map.put("Name", name1);
                                    map.put("Village", village1);
                                    map.put("LandSize", landsize1);
                                    map.put("SoilType", soiltype1);
                                    map.put("Irrigation", irrigation1);
                                    map.put("Sampleid",id);
                                    reference.child(phone).child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Profile.this, "Sucessfully", Toast.LENGTH_SHORT).show();
                                                Intent move = new Intent(Profile.this, tickpage.class);
                                                move.putExtra("Phone1", phone);
                                                move.putExtra("soiltype", soiltype1);
                                                move.putExtra("village", village1);
                                                move.putExtra("name", name1);
                                                move.putExtra("irrigation", irrigation1);
                                                move.putExtra("landsize", landsize1);
                                                move.putExtra("sampleid", id);
                                                startActivity(move);
                                                finish();
                                            } else {
                                                Toast.makeText(Profile.this, "UnSucessfully", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                } else {
                                    p4.setVisibility(View.INVISIBLE);
                                    irrigation.setError("");
                                    return;
                                }
                            } else {
                                p4.setVisibility(View.INVISIBLE);
                                soiltype.setError("");
                                return;
                            }
                        } else {
                            p4.setVisibility(View.INVISIBLE);
                            landsize.setError("");
                            return;
                        }
                    } else {
                        p4.setVisibility(View.INVISIBLE);
                        village.setError("");
                        return;
                    }
                } else {
                    p4.setVisibility(View.INVISIBLE);
                    name.setError("");
                    return;
                }
            }
        });
    }
}