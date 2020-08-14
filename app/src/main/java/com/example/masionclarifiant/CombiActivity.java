package com.example.masionclarifiant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CombiActivity extends AppCompatActivity {
    //Socket
    //String ipaddress = "220.69.172.235";
    //Socket myAppSocket = null;
    EditText ipaddress;
    public static String wifiModuleIp = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";
    Button motorStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combi);

        ipaddress = (EditText) findViewById(R.id.ssssssss);
        ipaddress.setText("220.69.172.235");

        motorStart = (Button)findViewById(R.id.motorStart);
        motorStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "배합을 시작합니다.", Toast.LENGTH_SHORT).show();
                    getIPandPort();
                    CMD = "Start";
                    Socket_AsyncTask cmd_start_motor = new Socket_AsyncTask();
                    cmd_start_motor.execute();
                }
            });
    }
    /*public void getTest(){
        for(int i=0;i>=SelectFrag2.selectedingredients.length;i++){
            if(SelectFrag2.selectedingredients[i].equals("x")){

            }
        }

    }*/
    public void getIPandPort()
    {
        String ipaddress2 = ipaddress.getText().toString();
        Log.d("MYTEST", "IP String: " + ipaddress2);
        wifiModuleIp = ipaddress2;
        wifiModulePort = 5000;
        Log.d("MY TEST","IP:" + wifiModuleIp);
        Log.d("MY TEST", "PORT" + wifiModulePort);
    }
    public class Socket_AsyncTask extends AsyncTask<Void, Void, Void>
    {
        Socket socket;

        @Override
        protected Void doInBackground(Void... params) {
            try{
                InetAddress inetAddress = InetAddress.getByName(CombiActivity.wifiModuleIp);
                socket = new java.net.Socket(inetAddress, CombiActivity.wifiModulePort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.close();
                socket.close();
            }catch (UnknownHostException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
