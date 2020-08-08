package com.example.masionclarifiant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Queue;

public class FindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        final TextView login_id = (TextView) findViewById(R.id.login_id);
        final Button find_btn = (Button) findViewById(R.id.find_btn);

        find_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = login_id.getText().toString();

                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String userPW = jsonObject.getString("userPW");
                            if(userPW != "null"){
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                            builder.setTitle("비밀번호 찾기");
                            builder.setMessage(userID + "회원님의 패스워드는 " + userPW +"입니다.");
                            builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                                    @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(FindActivity.this, LoginActivity.class);
                                    FindActivity.this.startActivity(intent);
                                }
                            });
                            builder.show();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                                builder.setMessage("없는 회원입니다.")
                                        .setNegativeButton("다시 시도",null)
                                        .create()
                                        .show();
                            }

                        }catch (Exception e){e.printStackTrace();}
                    }
                };
                FindRequest findRequest = new FindRequest(userID, res);
                RequestQueue queue = Volley.newRequestQueue(FindActivity.this);
                queue.add(findRequest);
            }
        });
    }
}
