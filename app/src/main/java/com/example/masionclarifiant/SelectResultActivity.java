package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONObject;

public class SelectResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_result_page);
        Button yes = (Button) findViewById(R.id.button3);
        Button no = (Button) findViewById(R.id.button4);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String i_name;
                final String skinType;
                final String perfume;
                System.out.printf("TEST"); //버튼 작동 테스트용

                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt(response);
                            if(success==0){
                                String i_name = jsonObject.getString("i_name");
                                String perfume = jsonObject.getString("perfume");
                                String skinType = jsonObject.getString("skinType");

                                Intent intent = new Intent(SelectResultActivity.this, MainActivity.class);
                                SelectResultActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelectResultActivity.this);
                                builder.setMessage("다시 시도해주세요.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }catch (Exception e){e.printStackTrace();}
                    }
                };
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectResultActivity.super.onBackPressed();
            }
        });
    }
}

