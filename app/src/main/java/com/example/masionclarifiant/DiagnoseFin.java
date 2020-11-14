package com.example.masionclarifiant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DiagnoseFin extends AppCompatActivity {
    Button goDiagnoseResult;
    Button goHomeBtn;
    Bitmap bm;
    public static int moisture = 0; //db에서 수분값 받아옴
    public static int oil = 0; //db에서 유분값 받아옴
    public static int blemish = 0; //db에서 잡티 받아옴
    public static double clean = 0; //청결도
    public static double wrinkle = 0; //주름
    public static double liver_spot = 0; //기미
    public static int skinDate2, skinDate3, skinDate1 = 1;
    public static int Skinage1, Skinage2, Skinage3 = 1;
    public static double skin_age = 0;
    String liver_spot_string;
    String wrinkle_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_fin);
        final String userID = getIntent().getStringExtra("userID");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");

        //url 넣어서 이미지 바꾸기
        final Handler eye_handler = new Handler(){
            public void handleMessage(Message msg){
                final ImageView eye_picture = (ImageView)findViewById(R.id.eye_picture);
                Handler api_handler = new Handler();
                api_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        eye_picture.setImageBitmap(bm);
                    }
                });
            }
        };

        Thread eye_imgload = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://3.35.16.162/image/eye.jpg");
                    InputStream is = url.openStream();
                    bm = BitmapFactory.decodeStream(is);
                    Message handler_msg = eye_handler.obtainMessage();
                    eye_handler.sendMessage(handler_msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        eye_imgload.start();

        final Handler skin_handler = new Handler(){
            public void handleMessage(Message msg){
                final ImageView eye_picture = (ImageView)findViewById(R.id.skin_picture);
                Handler api_handler = new Handler();
                api_handler.post(new Runnable() {
                    @Override
                    public void run() {
                        eye_picture.setImageBitmap(bm);
                    }
                });
            }
        };

        Thread skin_imgload = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://3.35.16.162/image/skin.jpg");
                    InputStream is = url.openStream();
                    bm = BitmapFactory.decodeStream(is);
                    Message handler_msg = skin_handler.obtainMessage();
                    skin_handler.sendMessage(handler_msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        skin_imgload.start();

        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    System.out.println("json-response: " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    clean = jsonObject.getInt("clean");
                    liver_spot = jsonObject.getDouble("liver_spot");
                    wrinkle = jsonObject.getDouble("wrinkle");
                    System.out.println("wrinkle");
                    skin_age = Math.round((((wrinkle/100)*2.243)*2.301)+((liver_spot/10)*3.985)+10);
                }catch (Exception e){e.printStackTrace();}
            }
        };

        SkinResultRequest skinResultRequest = new SkinResultRequest(userID, res);
        RequestQueue queue = Volley.newRequestQueue(DiagnoseFin.this);
        queue.add(skinResultRequest);

        //유수분, 여드름 불러오기
        Response.Listener<String> resp = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    moisture = jsonObject.getInt("moisture");
                    oil = jsonObject.getInt("oil");
                    blemish = jsonObject.getInt("pimple");

                }catch (Exception e){e.printStackTrace();}

            }
        };
        DiagnoseRequest diagnoseRequest = new DiagnoseRequest(userID, resp);
        RequestQueue queue2 = Volley.newRequestQueue(DiagnoseFin.this);
        queue2.add(diagnoseRequest);

        //피부나이, 측정날짜 3개 배열 불러오기
        Response.Listener<String> res2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        int skin__age = jsonObject2.getInt("skin_age");
                        String skin__Date = jsonObject2.getString("skinDate");
                        if(i==0) {
                            Skinage3 = skin__age;
                            skinDate3 = Integer.parseInt(skin__Date.substring(5,7));
                        }
                        else if(i==1) {
                            Skinage2 = skin__age;
                            skinDate2 = Integer.parseInt(skin__Date.substring(5,7));
                        }
                        else {
                            Skinage1 = skin__age;
                            skinDate1 = Integer.parseInt(skin__Date.substring(5,7));
                        }
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        };
        Recently_three_skinageRequest recently_three_skinageRequest = new Recently_three_skinageRequest(userID, res2);
        RequestQueue queue1 = Volley.newRequestQueue(DiagnoseFin.this);
        queue1.add(recently_three_skinageRequest);

        goDiagnoseResult = (Button)findViewById(R.id.go_diagnose_result);
        goDiagnoseResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String skin_ages = String.valueOf(skin_age);
                System.out.printf(skin_ages + "sadasasfasasdsadasf");
                Response.Listener<String> res3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                };
                CalcSkinAgeRequest calcSkinAgeRequest = new CalcSkinAgeRequest(userID, skin_ages, res3);
                RequestQueue queue3 = Volley.newRequestQueue(DiagnoseFin.this);
                queue3.add(calcSkinAgeRequest);
                Intent intent = new Intent(DiagnoseFin.this, DiagnoseResult.class);
                intent.putExtra("userID", userID);
                 intent.putExtra("moisture", moisture);
                intent.putExtra("oil", oil);
                intent.putExtra("blemish", blemish);
                intent.putExtra("clean", clean);
                intent.putExtra("wrinkle", wrinkle);
                intent.putExtra("liver_spot", liver_spot);
                intent.putExtra("skinDate2", skinDate2);
                intent.putExtra("skinDate3", skinDate3);
                intent.putExtra("skinDate1", skinDate1);
                intent.putExtra("Skinage1", Skinage1);
                intent.putExtra("Skinage2", Skinage2);
                intent.putExtra("Skinage3", Skinage3);
                startActivity(intent);
            }
        });

        goHomeBtn = (Button)findViewById(R.id.go_home_btn);
        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseFin.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
