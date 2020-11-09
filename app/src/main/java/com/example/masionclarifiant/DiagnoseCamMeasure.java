package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import org.w3c.dom.Text;

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

        Thread checkUpdate = new Thread() {
            public void run() {
                while(true){
                    SocketService socketService = DiagnoseStart.socketService;
                    String msg = socketService.receive();
                    System.out.println("cam_measure: " + msg);
                    if(msg.equals("finish measure")){
                        goSeeResult.setVisibility(View.VISIBLE);
                        camMeasureState.setText("측정이 완료되었습니다.");
                        loadingImg.clearAnimation();
                        loadingImg.setVisibility(View.GONE);
                    }
                }
            }
        };
        checkUpdate.start();

        /*Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                goSeeResult.setVisibility(View.VISIBLE);
                camMeasureState.setText("측정이 완료되었습니다.");
                loadingImg.clearAnimation();
                loadingImg.setVisibility(View.GONE);
            }
        }, 3000);*/

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
