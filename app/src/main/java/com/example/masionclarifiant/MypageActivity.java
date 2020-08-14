package com.example.masionclarifiant;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MypageActivity extends AppCompatActivity{
    public static String feedback_save = "";
    public static Boolean tnf = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        final String userID = getIntent().getStringExtra("userID");
        final ArrayList<String> createDateArray = new ArrayList<>();
        final ArrayList<String> skinTypeArray = new ArrayList<>();
        final ArrayList<String> perfumeArray = new ArrayList<>();
        final ArrayList<String> i_nameArray = new ArrayList<>();

        TextView back_btn = (TextView) findViewById(R.id.back_btn);
        TextView my_pg = (TextView) findViewById(R.id.mypage_text);
        TextView user_use_combination = (TextView)findViewById(R.id.my_pg3);
        final TextView feedback_message = (TextView)findViewById(R.id.my_pg4);

        final Button feedback = (Button)findViewById(R.id.button5);
        final TextView createDate = (TextView)findViewById(R.id.date);
        final TextView i_name = (TextView)findViewById(R.id.ingredient);
        final TextView skinType = (TextView)findViewById(R.id.skinType);
        final TextView perfume = (TextView)findViewById(R.id.perfume);
        TableLayout tableLayout = (TableLayout)findViewById(R.id.table);

        //가장 최근에 조합한 화장품 목록(3개) 가져오기
        Response.Listener<String> res2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Boolean success = jsonObject.getBoolean("success");
                        if(success){
                            System.out.printf(jsonObject.getString("skinType")+"testttttttttttttttttttttt");
                            String skinType2 = jsonObject.getString("skinType");
                            String i_name2 = jsonObject.getString("i_name");
                            String perfume2 = jsonObject.getString("perfume");
                            String createData2 = jsonObject.getString("createDate");
                            skinTypeArray.add(skinType2);
                            i_nameArray.add(i_name2);
                            perfumeArray.add(perfume2);
                            createDateArray.add(createData2);
                        }
                    }

                }catch (Exception e){e.printStackTrace();}
            }
        };

        //가장 최근에 조합한 화장품 1개 가져오기
        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    if(success){
                        createDate.setText(jsonObject.getString("createDate"));
                        i_name.setText(jsonObject.getString("i_name"));
                        skinType.setText(jsonObject.getString("skinType"));
                        perfume.setText(jsonObject.getString("perfume"));
                    }

                }catch (Exception e){e.printStackTrace();}
            }
        };
        RecentlyCombinationRequest recentlyCombinationRequest = new RecentlyCombinationRequest(userID, res);
        RequestQueue queue = Volley.newRequestQueue(MypageActivity.this);
        queue.add(recentlyCombinationRequest);
        my_pg.setText(userID + "님 환영합니다!");
        user_use_combination.setText(userID +"님이 이용하신 화장품 조합");
        feedback_message.setText(userID  +"님의 화장품 조합 만족도 조사");

        //돌아가기BTN
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MypageActivity.super.onBackPressed();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i_name.getText().toString().equals("정보가 없습니다.")){ //어차피 Not Null이라 얘만 없어도 화장품 생성 정보 없음
                    AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                    builder.setMessage("먼저 화장품 조합을 생성해주세요.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();
                    return;
                }
                Dialog2 dialog = new Dialog2(MypageActivity.this);
                //dialog.callFunction(feedback_save);
                dialog.callFunction();

                if(tnf == true){
                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                                builder.setMessage("피드백이 전송됐습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                tnf = false;
                                                MypageActivity.this.finish();
                                                MypageActivity.this.startActivity(intent);
                                            }
                                        })
                                        .create()
                                        .show();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                                builder.setMessage("피드백 전송에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }catch (Exception e){e.printStackTrace();}
                    }
                };
                MypageRequest mypageRequest = new MypageRequest(userID, feedback_save, res);
                RequestQueue queue = Volley.newRequestQueue(MypageActivity.this);
                queue.add(mypageRequest);}
        }
        });

    }
}
