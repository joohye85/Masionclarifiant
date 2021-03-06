package com.example.masionclarifiant;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DiagnoseEyeCam extends AppCompatActivity{
    WebView webView;
    Button takePictureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_eyecam);
        webView = (WebView) findViewById(R.id.wv_stream);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        final String userID = getIntent().getStringExtra("userID");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");

        DrawOnTop mDraw = new DrawOnTop(this);

        addContentView(mDraw, new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        String url ="http://192.168.137.30:8080/stream/video.mjpeg";
        webView.loadUrl(url);

        takePictureBtn = (Button) findViewById(R.id.eye_take_picture);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocketService socketService = MainActivity.socketService;
                if(socketService != null){
                    socketService.send("goeyepicture");
                    System.out.println(socketService.getSocket());
                }
                webView.destroy();
                webView = null;
                Intent intent = new Intent(DiagnoseEyeCam.this, DiagnoseSkinCam.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
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
            canvas.drawRect(webView.getX()+webView.getWidth()/2-240, webView.getY()+webView.getHeight()/2-130, webView.getX()+webView.getWidth()/2+240, webView.getY()+webView.getHeight()/2+130, paint);
            super.onDraw(canvas);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
