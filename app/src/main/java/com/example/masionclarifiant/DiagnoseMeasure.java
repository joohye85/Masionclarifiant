package com.example.masionclarifiant;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
//import com.pnikosis.materialishprogress.ProgressWheel;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DiagnoseMeasure extends AppCompatActivity {
    private Socket socket;
    public static String wifiModuleIp = "220.69.172.222";
    public static int wifiModulePort = 9999;
    TextView measure_state;
    Button go_pic_skin;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_measure);
        //ProgressWheel wheel = new ProgressWheel(getApplicationContext());
        CircularProgressView progressView = (CircularProgressView) findViewById(R.id.progress_view);
        progressView.startAnimation();
        measure_state = (TextView)findViewById(R.id.measuer_state);
        go_pic_skin = (Button)findViewById(R.id.go_pic_skin);
        go_pic_skin.setVisibility(View.VISIBLE);

        go_pic_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseMeasure.this, DiagnoseCam1.class);
                startActivity(intent);
            }
        });
        Thread checkUpdate = new Thread() {
            public void run() {
                try{
                    InetAddress inetAddress = InetAddress.getByName(DiagnoseCam1.wifiModuleIp);
                    socket = new java.net.Socket(inetAddress, DiagnoseCam1.wifiModulePort);
                    if(socket != null){
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeUTF("wngp0805");
                        dataOutputStream.close();
                        //objectOutputStream.close();
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        String line = "";
                        while(true){
                            byte[] receiver = new byte[35];
                            dataInputStream.read(receiver);
                            System.out.println(receiver);
                            line = new String(receiver);
                            System.out.print(line);
                            if(line.equals("finish measure")){
                                measure_state.setText("측정이 완료되었습니다.");
                                go_pic_skin.setVisibility(View.VISIBLE);
                                socket.close();
                            }
                        }
                    }
                }catch (UnknownHostException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        checkUpdate.start();

        Thread checkUpdate2 = new Thread() {
            public void run() {
                try{
                    InetAddress inetAddress = InetAddress.getByName(DiagnoseCam1.wifiModuleIp);
                    socket = new java.net.Socket(inetAddress, DiagnoseCam1.wifiModulePort);
                    if(socket != null){
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataOutputStream.writeUTF("go camera");
                        dataOutputStream.close();
                        //objectOutputStream.close();
                    }
                }catch (UnknownHostException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        checkUpdate2.start();
    }

}
