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

import androidx.appcompat.app.AppCompatActivity;

public class DiagnoseMeasure extends AppCompatActivity {
    TextView measure_state;
    Button go_pic_skin;
    ImageView loadingImg;
    SocketService socketService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_measure);
        loadingImg = (ImageView)findViewById(R.id.loading_img);

        socketService = MainActivity.socketService;
        System.out.println("measure: " + socketService);
        socketService.send("moisture");
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

        final String userID = getIntent().getStringExtra("userID");
        loadingImg.setAnimation(animation);
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");
        measure_state = (TextView)findViewById(R.id.measuer_state);
        go_pic_skin = (Button)findViewById(R.id.go_pic_skin);

        final Handler handler = new Handler(){
            public void handleMessage(Message msg){
                go_pic_skin.setVisibility(View.VISIBLE);
                measure_state.setText("측정이 완료되었습니다.");
                loadingImg.clearAnimation();
                loadingImg.setVisibility(View.GONE);
            }
        };

        Thread checkUpdate = new Thread() {
            public void run() {
                while (true) {
                    String msg = socketService.receive();
                    System.out.println("measure: " + msg);
                    if (msg.equals("measurefin")) {
                        Message handler_msg = handler.obtainMessage();
                        handler.sendMessage(handler_msg);
                        break;
                    }
                }
            }
        };
        checkUpdate.start();

        go_pic_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socketService.send("eyecam");
                Intent intent = new Intent(DiagnoseMeasure.this, DiagnoseEyeCam.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

}
