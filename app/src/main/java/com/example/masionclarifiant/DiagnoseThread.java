package com.example.masionclarifiant;

import android.os.Handler;
import android.view.View;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DiagnoseThread extends Thread{
    public static Socket socket;
    public static String wifiModuleIp = "220.69.172.66";
    public static int wifiModulePort = 9999;
    private Handler mHandler;
    @Override
    public void run() {
        try{
            InetAddress inetAddress = InetAddress.getByName(wifiModuleIp);
            socket = new java.net.Socket(inetAddress, wifiModulePort);
            if(socket != null){
                System.out.println("소켓 연결됨");
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
                }
            }
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
