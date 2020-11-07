package com.example.masionclarifiant;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketService extends Service {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    public static String wifiModuleIp = "220.69.172.66";
    public static int wifiModulePort = 9999;
    private Handler mHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        connect();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void connect(){
        mHandler = new Handler();
        Thread checkUpdate = new Thread() {
            public void run() {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getByName(wifiModuleIp);
                    socket = new java.net.Socket(inetAddress,wifiModulePort);
                    if(socket != null){
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        dataInputStream = new DataInputStream(socket.getInputStream());
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        checkUpdate.start();
    }

    void disconnect(){
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void send(){
        Thread sendThread = new Thread() {
            public void run() {
                while(true){
                    String start_msg = DiagnoseStart.getMsg();
                    Log.d("SocketConnect", "socket-start_msg: " + start_msg);
                    String cam_msg = DiagnoseCam1.getMsg();
                    Log.d("SocketConnect", "socket-cam_msg: " + cam_msg);
                    if(cam_msg != null){
                        try{
                            Log.d("SocketConnect", "Cam 메시지 보냄");
                            dataOutputStream.writeUTF(cam_msg);
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(start_msg != null){
                        try{
                            Log.d("SocketConnect", "Start 메시지 보냄");
                            dataOutputStream.writeUTF(start_msg);
                            dataOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        sendThread.start();
    }

    void receive(){
        Thread receiveThred = new Thread() {
            public void run() {String line="";
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
        };

        receiveThred.start();
    }
}
