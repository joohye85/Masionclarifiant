package com.example.masionclarifiant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DiagnoseEyeCheck extends AppCompatActivity {
    Button goSkinCam;
    Bitmap bm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_eye_check);
        goSkinCam = (Button)findViewById(R.id.go_skin_cam);
        final String userID = getIntent().getStringExtra("userID");
        System.out.println("eyecheck 들어옴");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");

        final Handler handler = new Handler(){
            public void handleMessage(Message msg){
                final ImageView eye_picture = (ImageView)findViewById(R.id.wv_picture);
                Handler api_handler = new Handler();
                api_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        eye_picture.setImageBitmap(bm);
                    }
                });
            }
        };

        Thread imgload = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://3.35.16.162/image/skin.jpg");
                    InputStream is = url.openStream();
                    bm = BitmapFactory.decodeStream(is);
                    Message handler_msg = handler.obtainMessage();
                    handler.sendMessage(handler_msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        imgload.start();

        goSkinCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocketService socketService = MainActivity.socketService;
                socketService.send("skincam");
                Intent intent = new Intent(DiagnoseEyeCheck.this, DiagnoseSkinCam.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
