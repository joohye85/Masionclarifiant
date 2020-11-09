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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DiagnoseCam1 extends AppCompatActivity{
    //public static String wifiModuleIp = "220.69.172.66";
    //public static int wifiModulePort = 9999;
    WebView webView;
    Button takePictureBtn;
    static String socket_msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_cam1);
        webView = (WebView) findViewById(R.id.wv_stream);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        final String userID = getIntent().getStringExtra("userID");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");
        System.out.println("들어왔음");

        /*Socket_AsyncTask cmd_start_motor = new Socket_AsyncTask();
        cmd_start_motor.execute();*/

        DrawOnTop mDraw = new DrawOnTop(this);

        addContentView(mDraw, new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));

        String url ="http://220.69.172.82:8080/stream/video.mjpeg";
        webView.loadUrl(url);

        takePictureBtn = findViewById(R.id.take_picture_btn);
        takePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocketService socketService = DiagnoseStart.socketService;
                if(socketService != null){
                    socketService.send("go picture");
                    System.out.println(socketService.getSocket());
                }
                else{
                    System.out.println("못 받아옴");
                }
                Intent intent = new Intent(DiagnoseCam1.this, DiagnoseCam2.class);
                startActivity(intent);
                webView.destroy();
                webView = null;
            }
        });
    }

    /*public class Socket_AsyncTask extends AsyncTask<Void, Void, Void>
    {
        Socket socket;

        @Override
        protected Void doInBackground(Void... params) {
            try{
                InetAddress inetAddress = InetAddress.getByName("220.69.17.82");
                socket = new java.net.Socket(inetAddress, 9999);
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
    }*/

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
