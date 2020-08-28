package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MyskinActivity extends AppCompatActivity {
    public static CheckBox checkBox;
    public static  CheckBox checkBox2;
    public static  CheckBox checkBox3;
    public static  CheckBox checkBox4;
    public static  CheckBox checkBox5;
    public static  CheckBox checkBox6;
    public static StringBuilder checkboxs = new StringBuilder("");
    public static String interest_text ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myskin);
        //userID 정보 받아오기
        final String userID = getIntent().getStringExtra("userID");
        final TextView edit =(TextView)findViewById(R.id.textView15);
        //button 기능 구현

        Button changeBtn = (Button) findViewById(R.id.changebtn);
        Button backBtn = (Button)findViewById(R.id.backbtn);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyskinActivity.this, Mypage2Activity.class);
                intent.putExtra("userID",userID);
                MyskinActivity.this.startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyskinActivity.this, MainActivity.class);
                intent.putExtra("userID", userID);
                MyskinActivity.this.startActivity(intent);
            }
        });
        //타이틀 설정
        TextView type_title = (TextView)findViewById(R.id.type_title);
        type_title.setText(userID +"님의 피부타입");

        //피부타입 버튼으로 보이게하기
        final Button dryBtn = (Button)findViewById(R.id.dryBtn);
        final Button complexBtn = (Button)findViewById(R.id.complexBtn);
        final Button oilBtn = (Button)findViewById(R.id.oilBtn);
        final Button sensitiveBtn = (Button)findViewById(R.id.sensitiveBtn);

        //체크박스 선택되게하기
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);
        checkBox.setClickable(false);
        checkBox2.setClickable(false);
        checkBox3.setClickable(false);
        checkBox4.setClickable(false);
        checkBox5.setClickable(false);
        checkBox6.setClickable(false);

        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    String skinType_text = jsonObject.getString("skinType");
                    interest_text = jsonObject.getString("interest");
                    if(success){
                        if(skinType_text.equals("건성")) dryBtn.setVisibility(View.VISIBLE);
                        if(skinType_text.equals("지성")) oilBtn.setVisibility(View.VISIBLE);
                        if(skinType_text.equals("복합성")) complexBtn.setVisibility(View.VISIBLE);
                        if(skinType_text.equals("민감성")) sensitiveBtn.setVisibility(View.VISIBLE);
                        interest_text.replace(",","");
                        interest_text.replace(" ","");
                        if(interest_text.contains("수분")) checkBox.setChecked(true);
                        if(interest_text.contains("각질")) checkBox2.setChecked(true);
                        if(interest_text.contains("진정")) checkBox3.setChecked(true);
                        if(interest_text.contains("탄력")) checkBox4.setChecked(true);
                        if(interest_text.contains("미백")) checkBox5.setChecked(true);
                        if(interest_text.contains("잡티")) checkBox6.setChecked(true);
                    }

                }catch (Exception e){e.printStackTrace();}
            }
        };
        MyskinRequest myskinRequest = new MyskinRequest(userID, res);
        RequestQueue queue = Volley.newRequestQueue(MyskinActivity.this);
        queue.add(myskinRequest);
    }
}
