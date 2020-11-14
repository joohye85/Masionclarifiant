package com.example.masionclarifiant;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class DiagnoseSkinCam extends AppCompatActivity {
    WebView webView;
    Button takePictureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_skincam);
        webView = (WebView) findViewById(R.id.wv_stream);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        final String userID = getIntent().getStringExtra("userID");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");

        DiagnoseSkinCam.DrawOnTop mDraw = new DrawOnTop(this);

        addContentView(mDraw, new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        SocketService socketService = MainActivity.socketService;
        socketService.send("skincam");

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                String url ="http://192.168.137.208:8080/stream/video.mjpeg";
                webView.loadUrl(url);
            }
        }, 3000);// 라즈베리파이 서버에서 서비스 시작하는 시간 기다리는 것

        takePictureBtn = (Button) findViewById(R.id.skin_take_picture);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocketService socketService = MainActivity.socketService;
                if(socketService != null){
                    socketService.send("goskinpicture");
                    System.out.println(socketService.getSocket());
                }
                else{
                    System.out.println("못 받아옴");
                }
                Response.Listener<String> res1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                        }catch (Exception e){e.printStackTrace();}
                    }
                };

                SendSkinCamRequest sendSkinCamRequest = new SendSkinCamRequest(userID, res1);
                RequestQueue queue = Volley.newRequestQueue(DiagnoseSkinCam.this);
                queue.add(sendSkinCamRequest);
                SendEyeCamRequest sendEyeCamRequest = new SendEyeCamRequest(userID, res1);
                queue.add(sendEyeCamRequest);

                Intent intent = new Intent(DiagnoseSkinCam.this, DiagnoseCamMeasure.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                webView.destroy();
                webView = null;
            }
        });
    }

    class DrawOnTop extends View{
        public DrawOnTop(Context context){
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(4);
            canvas.drawRect(webView.getX()+webView.getWidth()/2-240, webView.getY()+webView.getHeight()/2-240, webView.getX()+webView.getWidth()/2+240, webView.getY()+webView.getHeight()/2+240, paint);
            super.onDraw(canvas);
        }
    }

    @Override
    public void onBackPressed() {
    }
}