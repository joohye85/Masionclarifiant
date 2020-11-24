package com.example.masionclarifiant;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class CombiActivity extends AppCompatActivity {
    //Socket
    //String ipaddress = "220.69.172.235";
    //Socket myAppSocket = null;
    EditText ipaddress;
    public static String wifiModuleIp = "";
    public static int wifiModulePort = 0;
    public static ArrayList<String> list = new ArrayList<>();
    public static String CMD = "0";

    public static String skintype;
    public static String i_Name ="";
    public static String Perfume;

    Button motorStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combi);

        ipaddress = (EditText) findViewById(R.id.ipadress);
        ipaddress.setText("192.168.137.36");
        final String userID = getIntent().getStringExtra("userID");

        Response.Listener<String> res = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //boolean success = jsonObject.getBoolean("success");
                        String costype = jsonObject.getString("costype");
                        String skinType = jsonObject.getString("skinType");
                        String i_name = jsonObject.getString("i_name");
                        String perfume = jsonObject.getString("perfume");
                        if (costype.equals("직접배합")) {
                            if (skinType.equals("토너")) skintype = ",toner";
                            else skintype = ",lotion";

                            if (i_name.contains("알로에")) i_Name += ",aloe";
                            if (i_name.contains("꿀")) i_Name += ",honey";
                            if (i_name.contains("병풀")) i_Name += ",byeongpul";
                            if (i_name.contains("녹차")) i_Name += ",greentea";
                            if (i_name.contains("달팽이")) i_Name += ",snail";
                            if (i_name.contains("올리브")) i_Name += ",olive";
                            if (i_name.contains("유자")) i_Name += ",citron";
                            if (i_name.contains("백차")) i_Name += ",whitetea";

                            if (perfume.equals("티트리")) Perfume = ",teatree";
                            else if (perfume.equals("로즈마리")) Perfume = ",rosemary";
                            else Perfume = ",no_perfume";
                            Toast.makeText(getApplicationContext(), i_Name + skintype + Perfume, Toast.LENGTH_SHORT).show();
                        } else {
                            if (skinType.equals("토너")) skintype = ",toner";
                            else skintype = ",lotion";

                            i_Name = i_name.replace("꿀","h").replace("병풀","b").replace("알로에","a")
                                    .replace("유자","c").replace("백차","w").replace("달팽이","s");
                            Perfume = ",no_perfume";
                            Toast.makeText(getApplicationContext(), i_Name + skintype + Perfume, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            CombiRequest combiRequest = new CombiRequest(userID, res);
            RequestQueue requestQueue = Volley.newRequestQueue(CombiActivity.this);
            requestQueue.add(combiRequest);

        TextView reset_btn = (TextView) findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CombiActivity.this, MainActivity.class);
                CombiActivity.this.finish();
                i_Name ="";
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        motorStart = (Button)findViewById(R.id.motorStart);
        motorStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "배합을 시작합니다.", Toast.LENGTH_SHORT).show();
                    getIPandPort();
                    CMD = "start";
                    Socket_AsyncTask cmd_start_motor = new Socket_AsyncTask();
                    cmd_start_motor.execute();
                }
            });
    }
    public void getIPandPort()
    {
        String ipaddress2 = ipaddress.getText().toString();
        Log.d("MYTEST", "IP String: " + ipaddress2);
        wifiModuleIp = ipaddress2;
        wifiModulePort = 5001;
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
                socket = new Socket(inetAddress, CombiActivity.wifiModulePort);
                //ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                //objectOutputStream.writeObject(list);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.writeBytes(skintype);
                dataOutputStream.writeBytes(i_Name);
                dataOutputStream.writeBytes(Perfume);
                dataOutputStream.close();
                //objectOutputStream.close();
                socket.close();
            }catch (UnknownHostException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        String userID = getIntent().getStringExtra("userID");
        Intent intent = new Intent(CombiActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }
}
