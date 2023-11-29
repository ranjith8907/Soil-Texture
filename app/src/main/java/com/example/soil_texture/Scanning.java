package com.example.soil_texture;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Scanning extends AppCompatActivity {
    private Button scanpage;
    private TextView temperature,area;
    private TextView Resultview;
    private String village="";
    private String fileContents1;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
        phone=getIntent().getStringExtra("Phone");
        String soiltype=getIntent().getStringExtra("soiltype");
        village=getIntent().getStringExtra("village");
        String name=getIntent().getStringExtra("name");
        String irrigation=getIntent().getStringExtra("irrigation");
        String landsize=getIntent().getStringExtra("landsize");
        String sampleid=getIntent().getStringExtra("sampleid");
        scanpage=findViewById(R.id.Scanpage);
        temperature=findViewById(R.id.temperature);
        Resultview=findViewById(R.id.Resultview);
        area=findViewById(R.id.area);

        String customFilePath1 = "/storage/emulated/0/SaveImage/Result.txt";
        scanpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move=new Intent(Scanning.this,Camera.class);
                move.putExtra("Phone1",phone);
                move.putExtra("soiltype",soiltype);
                move.putExtra("sampleid",sampleid);
                startActivity(move);
            }
        });
        get();
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
                Resultview.setText("    Id: "+sampleid+"\n    Name: "+name+"\n    Village: "+village+"\n    Land Size: "+landsize+"\n    Irrigation: "+irrigation+"\n    Result: "+fileContents1);
                // Now you can use "fileContents" as needed
            } else {
                Toast.makeText(Scanning.this,"Does Not files",Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void get(){
        String apikey="ddbf071c3cd3d6e5a1a9373851c1b8fe";
        String city=village;
        String uri="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, uri, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject object=response.getJSONObject("main");
                    String temp=object.getString("temp");
                    double val=Double.parseDouble(temp);
                    val=((val-32)*5)/9;
                    int value1 = (int)Math.round(val);
                    area.setText(String.valueOf(city));
                    temperature.setText(String.valueOf(value1));
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Scanning.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.download:
                Intent intent=new Intent(Scanning.this,download.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                break;
            default:
                Toast.makeText(Scanning.this,"",Toast.LENGTH_LONG).show();
        }
        return true;
    }
}