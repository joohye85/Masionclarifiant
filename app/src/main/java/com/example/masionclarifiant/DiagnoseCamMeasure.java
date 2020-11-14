package com.example.masionclarifiant;

import android.content.Intent;
import android.graphics.BitmapFactory;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DiagnoseCamMeasure extends AppCompatActivity {
    Button goSeeResult;
    ImageView loadingImg;
    TextView camMeasureState;
    public static String finish_str = ""; //0.3 증가인지 감소인지 보내기
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

        SocketService socketService = MainActivity.socketService;
        socketService.send("exit");

        final Handler cam_handler = new Handler(){
            public void handleMessage(Message msg){
                goSeeResult.setVisibility(View.VISIBLE);
                camMeasureState.setText("측정이 완료되었습니다.");
                loadingImg.clearAnimation();
                loadingImg.setVisibility(View.GONE);
            }
        };


        Thread finishMeasure = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    //php에서 받은 문자열이 measurefin 이랑 같으면 - if
                    Response.Listener<String> res  = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                finish_str = jsonObject.getString("finish_str");
                            }catch (Exception e){e.printStackTrace();}
                        }
                    };
                    ThreadRequest threadRequest = new ThreadRequest(userID, res);
                    RequestQueue queue2 = Volley.newRequestQueue(DiagnoseCamMeasure.this);
                    queue2.add(threadRequest);
                    System.out.println("testtttttttttt" + finish_str);
                    if(finish_str.equals("finish")) {
                        Message handler_msg = cam_handler.obtainMessage();
                        cam_handler.sendMessage(handler_msg);
                        break;
                    }
                }
            }
        });
        finishMeasure.start();
        Response.Listener<String> res2  = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                }catch (Exception e){e.printStackTrace();}
            }
        };
        ThreadInitRequest threadInitRequest = new ThreadInitRequest(userID, res2);
        RequestQueue queue3 = Volley.newRequestQueue(DiagnoseCamMeasure.this);
        queue3.add(threadInitRequest);

        Thread camThread = new Thread() {
            public void run() {
                while(true){
                    SocketService socketService = MainActivity.socketService;
                    String msg = socketService.receive();
                    System.out.println("cam_measure: " + msg);
                    //opencv랑 통신해야됨
                    if(msg.equals("allfinish")){
                        socketService.disconnect();
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

    @Override
    public void onBackPressed() {
    }
}
