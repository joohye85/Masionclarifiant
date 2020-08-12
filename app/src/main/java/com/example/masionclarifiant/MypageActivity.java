package com.example.masionclarifiant;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MypageActivity extends AppCompatActivity{
    public static String feedback_save = "";
    public static Boolean tnf = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        final String userID = getIntent().getStringExtra("userID");

        TextView back_btn = (TextView) findViewById(R.id.back_btn);
        TextView my_pg = (TextView) findViewById(R.id.mypage_text);
        TextView user_use_combination = (TextView)findViewById(R.id.my_pg3);
        final TextView feedback_message = (TextView)findViewById(R.id.my_pg4);

        final Button feedback = (Button)findViewById(R.id.button5);

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
