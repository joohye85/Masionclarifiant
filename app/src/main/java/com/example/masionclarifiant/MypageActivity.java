package com.example.masionclarifiant;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        //최근 3개 데이터 page 변수들
        // TextView[] textViews = new TextView[9];
        final TextView date1 = (TextView)findViewById(R.id.date1);
        final TextView date2 = (TextView)findViewById(R.id.date2);
        final TextView date3 = (TextView)findViewById(R.id.date3);
        final TextView type1 = (TextView)findViewById(R.id.type1);
        final TextView type2 = (TextView)findViewById(R.id.type2);
        final TextView type3 = (TextView)findViewById(R.id.type3);
        final TextView ingredient1 = (TextView)findViewById(R.id.ingredient1);
        final TextView ingredient2 = (TextView)findViewById(R.id.ingredient2);
        final TextView ingredient3 = (TextView)findViewById(R.id.ingredient3);
        final TextView perfume1 = (TextView)findViewById(R.id.perfume1);
        final TextView perfume2 = (TextView)findViewById(R.id.perfume2);
        final TextView perfume3 = (TextView)findViewById(R.id.perfume3);

        final TextView[][] textViews = new TextView[][]{{date1, type1,ingredient1,perfume1},{date2,type2,ingredient2,perfume2},{date3,type3,ingredient3,perfume3}};

        //만족도 조사 page 변수들
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
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        Boolean success = jsonObject2.getBoolean("success");
                        String i_name = jsonObject2.getString("i_name");
                        String createDate = jsonObject2.getString("createDate");
                        String perfume = jsonObject2.getString("perfume");
                        String skinType = jsonObject2.getString("skinType");
                        i_nameArray.add(i_name);
                        createDateArray.add(createDate);
                        perfumeArray.add(perfume);
                        skinTypeArray.add(skinType);
                       /* if(success2){
                            String i_name2 = jsonObject2.getString("i_name");

                            for(int j=0;j>=textViews.length;i++){
                                textViews[i][0].setText(createDate2);
                                textViews[i][1].setText(skinType2);
                                textViews[i][2].setText(i_name2);
                                textViews[i][3].setText(perfume2);
                            }
                        }*/
                    }
                    for(int i=0;i<textViews.length;i++){
                        for (int j=0;j<4;j++){
                            textViews[i][0].setText(createDateArray.get(i));
                            textViews[i][1].setText(skinTypeArray.get(i));
                            textViews[i][2].setText(i_nameArray.get(i));
                            textViews[i][3].setText(perfumeArray.get(i));
                        }
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        };
        Recently_three_dataRequest recently_three_dataRequest = new Recently_three_dataRequest(userID, res2);
        //mypage2Request.setShouldCache(false);
        RequestQueue queue2 = Volley.newRequestQueue(MypageActivity.this);
        queue2.add(recently_three_dataRequest);
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
        recentlyCombinationRequest.setShouldCache(false); //이전 결과 있어도 새로 요청하여 응답 보여줌
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
               /* Dialog2 dialog = new Dialog2(MypageActivity.this);
                //dialog.callFunction(feedback_save);
                dialog.callFunction();*/
                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
                                builder.setMessage("사용하신 화장품이 만족스러우셨나요?")
                                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                feedback_save = "예";
                                                Toast.makeText(getApplicationContext(), "전송되었습니다." , Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                feedback_save = "아니오";
                                                Toast.makeText(getApplicationContext(), "전송되었습니다.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), feedback_save, Toast.LENGTH_SHORT).show();
                FeedbackRequest feedbackRequest = new FeedbackRequest(userID,feedback_save,res);
                RequestQueue queue = Volley.newRequestQueue(MypageActivity.this);
                queue.add(feedbackRequest);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MypageActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
