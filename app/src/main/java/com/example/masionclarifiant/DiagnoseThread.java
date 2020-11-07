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
}
