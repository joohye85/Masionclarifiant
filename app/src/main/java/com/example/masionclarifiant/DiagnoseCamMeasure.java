package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DiagnoseCamMeasure extends AppCompatActivity {
    Button goSeeResult;
    ImageView loadingImg;
    TextView camMeasureState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_cam_measure);
        camMeasureState = (TextView)findViewById(R.id.cam_measure_state);
        final String userID = getIntent().getStringExtra("userID");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");

        loadingImg = (ImageView)findViewById(R.id.loading_img);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        loadingImg.setAnimation(animation);

        final Handler cam_handler = new Handler(){
            public void handleMessage(Message msg){
                goSeeResult.setVisibility(View.VISIBLE);
                camMeasureState.setText("측정이 완료되었습니다.");
                loadingImg.clearAnimation();
                loadingImg.setVisibility(View.GONE);
            }
        };

        Thread camThread = new Thread() {
            public void run() {
                while(true){
                    SocketService socketService = MainActivity.socketService;
                    String msg = socketService.receive();
                    System.out.println("cam_measure: " + msg);
                    //opencv랑 통신해야됨
                    if(msg.equals("camerafin")){
                        Message handler_msg = cam_handler.obtainMessage();
                        cam_handler.sendMessage(handler_msg);
                        break;
                    }
                }
            }
        };
        camThread.start();

        goSeeResult = (Button)findViewById(R.id.go_see_result);
        goSeeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseCamMeasure.this, DiagnoseFin.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
