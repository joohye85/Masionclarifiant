package com.example.masionclarifiant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONObject;

public class MyskinActivity extends AppCompatActivity {
    public static StringBuilder stringBuilder = new StringBuilder("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myskin);

        final TextView edit =(TextView)findViewById(R.id.edit1);

        final TextView textView1 =(TextView)findViewById(R.id.textView7);
        final TextView textView2 =(TextView)findViewById(R.id.textView8);

        String userID = getIntent().getStringExtra("userID");

        //상태창  띄워주기
        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    if(success){
                        textView1.setText(jsonObject.getString("skinType"));
                        textView2.setText(jsonObject.getString("interest"));
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        };

        //변경하기
        edit.setOnClickListener(new View.OnClickListener() {
            Dialog2 dialog = new Dialog2(MyskinActivity.this);
            //dialog.callFunction(feedback_save);
            dialog.callFunction();

            @Override
            public void onClick(View v) {
                Response.Listener<String> res2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean success = jsonObject.getBoolean("success");
                            if(success){

                            }
                        }catch (Exception e){e.printStackTrace();}
                    }
                };
            }
        });

    }
}
