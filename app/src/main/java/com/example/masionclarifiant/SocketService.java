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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketService extends Service {
    private Socket socket;
    public static String wifiModuleIp = "220.69.172.218";
    public static int wifiModulePort = 9999;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
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
                    socket = new Socket(inetAddress, wifiModulePort);
                    System.out.println("connct-socket: " + socket);
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
                    System.out.println("send-socket: " + socket);
                    dataOutputStream = new DataOutputStream(getSocket().getOutputStream());
                    if(dataOutputStream != null){
                        dataOutputStream.writeUTF(msg);
                        dataOutputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        sendThread.start();
    }

    String receive(){
        System.out.println("receive!!!!!");
        String msg ="";
        byte[] receiver = new byte[10];
        try {
            dataInputStream = new DataInputStream(getSocket().getInputStream());
            if(dataInputStream != null){
                dataInputStream.read(receiver);
                if(receiver != null){
                    msg = new String(receiver);
                    Pattern pattern = Pattern.compile(".[a-z\\s]+"); //이상한 문자들도 들어와서 정규식 적용하여 필터링 함
                    Matcher matcher = pattern.matcher(msg);
                    while(matcher.find()) {
                        msg = matcher.group();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msg;
    }
}
