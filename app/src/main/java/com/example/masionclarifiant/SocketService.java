package com.example.masionclarifiant;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

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
    public static String wifiModuleIp = "220.69.172.141";
    public static int wifiModulePort = 9999;
    private Handler mHandler;
    final IBinder mBinder = new SocketBinder();

    class SocketBinder extends Binder {
        SocketService getService(){
            System.out.println("getService 불려짐");
            return SocketService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    void connect(){
        mHandler = new Handler();
        Thread checkUpdate = new Thread() {
            public void run() {
                try{
                    InetAddress inetAddress = InetAddress.getByName(wifiModuleIp);
                    socket = new java.net.Socket(inetAddress, wifiModulePort);
                    System.out.println("socekt: " + socket);
                    send("yes");
                }catch (UnknownHostException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };

        checkUpdate.start();
    }

    Socket getSocket(){
        return socket;
    }

    void disconnect(){
        if(dataInputStream != null){
            System.out.println("들어옴");
        }
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("send에서 예외");
            e.printStackTrace();
        }
    }

    void send(final String msg){
        Thread sendThread = new Thread() {
            public void run() {
                try{
                    System.out.println("send");
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    System.out.println("send-con");
                    dataOutputStream.writeUTF(msg);
                    System.out.println("send-success");
                    dataOutputStream.flush();
                } catch (IOException e) {
                    System.out.println("exceptioin1");
                    e.printStackTrace();
                }
            }
        };

        sendThread.start();
    }

    String receive(){
        String msg ="";
        while(true){
            byte[] receiver = new byte[35];
            try {
                dataInputStream.read(receiver);
                System.out.println(receiver);
                msg = new String(receiver);
                if(msg != null){
                    return msg;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
