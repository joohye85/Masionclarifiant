package com.example.masionclarifiant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MypageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        String userID = getIntent().getStringExtra("userID");

        TextView back_btn = (TextView) findViewById(R.id.back_btn);
        TextView my_pg = (TextView) findViewById(R.id.mypage_text);
        TextView user_use_combination = (TextView)findViewById(R.id.my_pg3);
        TextView feedback_message = (TextView)findViewById(R.id.my_pg4);

        Button feedback = (Button)findViewById(R.id.button5);

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

            }
        });
    }
}
