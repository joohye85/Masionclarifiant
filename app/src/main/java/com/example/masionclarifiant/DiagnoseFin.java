package com.example.masionclarifiant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DiagnoseFin extends AppCompatActivity {
    Button goDiagnoseResult;
    Button goHomeBtn;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_fin);
        final String userID = getIntent().getStringExtra("userID");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");

        //url 넣어서 이미지 바꾸기
        /*final Handler eye_handler = new Handler(){
            public void handleMessage(Message msg){
                final ImageView eye_picture = (ImageView)findViewById(R.id.eye_picture);
                Handler api_handler = new Handler();
                api_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        eye_picture.setImageBitmap(bm);
                    }
                });
            }
        };

        Thread eye_imgload = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://3.35.16.162/image/skin.jpg");
                    InputStream is = url.openStream();
                    bm = BitmapFactory.decodeStream(is);
                    Message handler_msg = eye_handler.obtainMessage();
                    eye_handler.sendMessage(handler_msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        eye_imgload.start();

        final Handler skin_handler = new Handler(){
            public void handleMessage(Message msg){
                final ImageView eye_picture = (ImageView)findViewById(R.id.skin_picture);
                Handler api_handler = new Handler();
                api_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        eye_picture.setImageBitmap(bm);
                    }
                });
            }
        };

        Thread skin_imgload = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://3.35.16.162/image/skin.jpg");
                    InputStream is = url.openStream();
                    bm = BitmapFactory.decodeStream(is);
                    Message handler_msg = skin_handler.obtainMessage();
                    skin_handler.sendMessage(handler_msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        skin_imgload.start();*/

        goDiagnoseResult = (Button)findViewById(R.id.go_diagnose_result);
        goDiagnoseResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseFin.this, DiagnoseResult.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        goHomeBtn = (Button)findViewById(R.id.go_home_btn);
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseFin.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
