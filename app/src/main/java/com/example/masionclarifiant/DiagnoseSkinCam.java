package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

        SocketService socketService = MainActivity.socketService;

        /*String url ="http://220.69.172.141:8080/stream/video.mjpeg";
        webView.loadUrl(url);*/

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
                Intent intent = new Intent(DiagnoseSkinCam.this, DiagnoseSkinCheck.class);
                startActivity(intent);
                webView.destroy();
                webView = null;
            }
        });
    }
}