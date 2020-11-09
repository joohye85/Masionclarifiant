package com.example.masionclarifiant;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ConnectionService extends Service {
    final int STATUS_DISCONNECTED = 0;
    final int STATUS_CONNECTED = 1;
    private int status = STATUS_DISCONNECTED;
    private Socket socket = null;
    private SocketAddress socketAddress = null;
    DataOutputStream dataOutputStream = null;
    DataInputStream dataInputStream = null;
    public static String wifiModuleIp = "220.69.172.222";
    public static int wifiModulePort = 9999;
    private Handler mHandler;

    IconnectionService binder = new IconnectionService() {
        @Override
        public int getStatus() throws RemoteException {
            return status;
        }

        @Override
        public void setSocket(String ip) throws RemoteException {
            mySetSocket(ip);
        }

        @Override
        public void connect() throws RemoteException{
            myConnect();
        }

        @Override
        public void disconnect() throws RemoteException{
            myDisconnect();
        }

        @Override
        public void send() {
            mySend();
        }

        @Override
        public void receive() {
            myReceive();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ConnectionService", "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ConnectionService", "onStartCommand()");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ConnectionService", "onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("ConnectionService", "onBind()");
        return (IBinder) binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("ConnectionService", "onUnbind()");
        return super.onUnbind(intent);
    }

    void mySetSocket(String ip){
        socketAddress = new InetSocketAddress(wifiModuleIp, wifiModulePort);
        Log.d("ConnectionService", "mySetSocket()");
    }

    void myConnect(){
        Log.d("ConnectionService", "myConnect()");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket.connect(socketAddress, 5000);
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataInputStream = new DataInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                status = STATUS_CONNECTED;
            }
        }).start();
    }

    void myDisconnect(){
        try{
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        status = STATUS_DISCONNECTED;
    }

    void mySend(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "hello, world!";
                try{
                    dataOutputStream.writeUTF(msg);
                    dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void myReceive(){
        new Thread(new Runnable() {
            @Override
            public void run() {
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
        }).start();
    }
}
