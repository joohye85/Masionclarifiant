package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    static String logState = "login";

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.login_page);

        final EditText id_textView = (EditText) findViewById(R.id.login_id);
        final EditText password_textView = (EditText)findViewById(R.id.login_password);
        final Button login_button = (Button)findViewById(R.id.login_button);
        final TextView register = (TextView) findViewById(R.id.login_register);

        if(logState.equals("logout"))
            Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();

        login_button.setOnClickListener(new View.OnClickListener() {  //로그인 버튼 -> 메인 화면으로 이동
            @Override
            public void onClick(View v) {
                final String userID = id_textView.getText().toString();
                final String userPW = password_textView.getText().toString();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);

                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                String userID = jsonObject.getString("userID");
                                String userPW = jsonObject.getString("userPW");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPW", userPW);
                                LoginActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("아이디 또는 비밀번호가 잘못 입력되었습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPW, res);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        register.setOnClickListener(new View.OnClickListener() { //회원가입 버튼 -> register 페이지로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class); //파라메터는 현재 액티비티, 전환될 액티비티
                LoginActivity.this.startActivity(intent); //엑티비티 요청
            }
        });
    }
}
