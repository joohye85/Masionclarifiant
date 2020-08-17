package com.example.masionclarifiant;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Mypage2Activity extends AppCompatActivity{
    public static String btn_text = "";
    public static StringBuilder checkboxs = new StringBuilder("");
    public static  CheckBox checkBox;
    public static  CheckBox checkBox2;
    public static  CheckBox checkBox3;
    public static  CheckBox checkBox4;
    public static  CheckBox checkBox5;
    public static  CheckBox checkBox6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage2);

        final Button dryBtn = (Button)findViewById(R.id.dryBtn);
        final Button complexBtn = (Button)findViewById(R.id.complexBtn);
        final Button oilBtn = (Button)findViewById(R.id.oilBtn);
        final Button sensitiveBtn = (Button)findViewById(R.id.sensitiveBtn);

        final Button changeBtn = (Button) findViewById(R.id.changebtn);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);

        dryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_text = "건성";
                dryBtn.setPressed(true);
                Toast.makeText(getApplicationContext(), btn_text + " 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        oilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_text = "지성";
                dryBtn.setPressed(true);
                Toast.makeText(getApplicationContext(), btn_text + " 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        sensitiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_text = "민감성";
                dryBtn.setPressed(true);
                Toast.makeText(getApplicationContext(), btn_text + " 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        complexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_text = "복합성";
                dryBtn.setPressed(true);
                Toast.makeText(getApplicationContext(), btn_text + " 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //checkbox
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox.isChecked()) checkboxs.append("수분");
                }else{
                    if(checkBox.isChecked()) checkboxs.append(", 수분");
                }
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox2.isChecked()) checkboxs.append("각질");
                }else{
                    if(checkBox2.isChecked()) checkboxs.append(", 각질");
                }
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox3.isChecked()) checkboxs.append("진정");
                }else{
                    if(checkBox3.isChecked()) checkboxs.append(", 진정");
                }
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox4.isChecked()) checkboxs.append("탄력");
                }else{
                    if(checkBox4.isChecked()) checkboxs.append(", 탄력");
                }
            }
        });
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox5.isChecked()) checkboxs.append("미백");
                }else{
                    if(checkBox5.isChecked()) checkboxs.append(", 미백");
                }
            }
        });
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox6.isChecked()) checkboxs.append("잡티");
                }else{
                    if(checkBox6.isChecked()) checkboxs.append(", 잡티");
                }
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringBuilder stringBuilder = new StringBuilder(checkboxs.toString());
                checkBox.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                checkBox6.setChecked(false);
                checkboxs.delete(0, checkboxs.length());
                String interest = stringBuilder.toString();
                final String userID = getIntent().getStringExtra("userID");

                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            if(success){
                                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Mypage2Activity.this);
                                builder.setMessage("변경되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Mypage2Activity.this, MainActivity.class);
                                                intent.putExtra("userID", userID);
                                                Mypage2Activity.this.startActivity(intent);
                                            }
                                        })
                                        .create()
                                        .show();

                            }else{
                                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Mypage2Activity.this);
                                builder.setMessage("변경에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }catch (Exception e){e.printStackTrace();}
                    }
                };
                SkincareRequest skincareRequest = new SkincareRequest(userID, btn_text, interest, res);
                RequestQueue queue = Volley.newRequestQueue(Mypage2Activity.this);
                queue.add(skincareRequest);
            }
        });

    }
}
