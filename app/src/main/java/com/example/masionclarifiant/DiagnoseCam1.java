package com.example.masionclarifiant;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DiagnoseCam1 extends AppCompatActivity{
    public static String wifiModuleIp = "220.69.172.78";
    public static int wifiModulePort = 9999;
    WebView webView;
    Button takePictureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_cam1);
        webView = (WebView) findViewById(R.id.wv_stream);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        DrawOnTop mDraw = new DrawOnTop(this);

        addContentView(mDraw, new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        String url ="http://220.69.172.78:8080/stream/video.mjpeg";
        webView.loadUrl(url);

        takePictureBtn = findViewById(R.id.take_picture_btn);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Socket_AsyncTask cmd_start_motor = new Socket_AsyncTask();
                cmd_start_motor.execute();
                Intent intent = new Intent(DiagnoseCam1.this, DiagnoseCam2.class);
                startActivity(intent);
                webView.destroy();
                webView = null;
            }
        });


    }

    public class Socket_AsyncTask extends AsyncTask<Void, Void, Void>
    {
        Socket socket;

        @Override
        protected Void doInBackground(Void... params) {
            try{
                InetAddress inetAddress = InetAddress.getByName(DiagnoseCam1.wifiModuleIp);
                socket = new java.net.Socket(inetAddress, DiagnoseCam1.wifiModulePort);
                if(socket != null){
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF("picture from android (i'm jihyeon)");
                    dataOutputStream.close();
                    //objectOutputStream.close();
                    socket.close();
                }
            }catch (UnknownHostException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
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
            canvas.drawRect(webView.getX()+60, webView.getY()+20, webView.getX()+780, webView.getY()+400, paint);
            super.onDraw(canvas);
        }
    }
}
