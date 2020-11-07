package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DiagnoseStart extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;
    static String socket_msg;
    private Button backBtn, nextBtn, diagnoseBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_start);
        final String userID = getIntent().getStringExtra("userID");
        Intent intent = new Intent(DiagnoseStart.this, SocketService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent);

        backBtn = findViewById(R.id.slide_back);
        nextBtn = findViewById(R.id.slide_next);
        diagnoseBtn = findViewById(R.id.intro_diagnose_btn);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDotLayout.removeAllViews();
                addDotsIndicator(1);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDotLayout.removeAllViews();
                addDotsIndicator(0);
            }
        });

        diagnoseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socket_msg = "wngp0805";
                Intent intent = new Intent(DiagnoseStart.this, DiagnoseMeasure.class);
                //intent.putExtra("socket", diagnoseThread.socket);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void addDotsIndicator(int position){
        if(position == 0){
            nextBtn.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.INVISIBLE);
            diagnoseBtn.setVisibility(View.GONE);
        }
        if(position == 1) {
            nextBtn.setVisibility(View.INVISIBLE);
            backBtn.setVisibility(View.VISIBLE);
            diagnoseBtn.setVisibility(View.VISIBLE);
        }
        mSlideViewPager.setCurrentItem(position);
        mDotLayout.removeAllViews();
        mDots = new TextView[2];
        for(int i=0; i<mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(40);
            mDots[i].setTextColor(getResources().getColor(R.color.colorGray));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0)
            mDots[position].setTextColor(getResources().getColor(R.color.colorDarkPink));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DiagnoseStart.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /*
    public class DiagnoseThread extends Thread{
        public Socket socket;
        public String wifiModuleIp = "220.69.172.66";
        public int wifiModulePort = 9999;
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        private Handler mHandler;

        @Override
        public void run() {
            try{
                InetAddress inetAddress = InetAddress.getByName(wifiModuleIp);
                socket = new java.net.Socket(inetAddress, wifiModulePort);
                if(socket != null){
                    System.out.println("소켓 연결됨");
                    dataOutputStream = null;
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataInputStream = new DataInputStream(socket.getInputStream());
                }
            }catch (UnknownHostException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        public void sendMessage(String msg){
            try {
                dataOutputStream.writeUTF(msg);
                System.out.println("보내짐");
            } catch (IOException e) {
                System.out.println("못 보냄");
                e.printStackTrace();
            }
        }

        public void closeMessage() throws IOException {
            dataOutputStream.close();
        }

        public void receiveMessage(){
            String line="";
            while(true){
                byte[] receiver = new byte[35];
                try {
                    dataInputStream.read(receiver);
                    System.out.println(receiver);
                    line = new String(receiver);
                    if(line.equals("exit")){
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    static String getMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(socket_msg != null){
                        break;
                    }
                }
            }
        }).start();

        System.out.println("START- 메시지 보내: " + socket_msg);
        return socket_msg;
    }
}