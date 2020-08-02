package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends MainActivity {
    TextView id_textView;
    TextView password_textView;
    Button login_button;
    TextView find_id;
    TextView register;
    static String logState = "login";

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.login_page);

        id_textView = (TextView)findViewById(R.id.login_id);
        password_textView = (TextView)findViewById(R.id.login_password);
        login_button = (Button)findViewById(R.id.login_button);
        find_id = (TextView) findViewById(R.id.login_find_id);
        register = (TextView) findViewById(R.id.login_register);
        final String str = "admin";

        if(logState.equals("logout"))
            Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();

        login_button.setOnClickListener(new View.OnClickListener() {  //로그인 버튼 -> 메인 화면으로 이동
            @Override
            public void onClick(View v) {
                if((id_textView.getText().toString().equals(str)) && (password_textView.getText().toString().equals(str))){ //문자열 비교 equals(), getText() 가 반환하는 것은 문자열이 아님
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class); //파라메터는 현재 액티비티, 전환될 액티비티
                    startActivity(intent); //엑티비티 요청
                }
                else if((id_textView.getText().toString().equals("")) || password_textView.getText().toString().equals("")){
                    Toast myToast = Toast.makeText(getApplicationContext(),"아이디나 패스워드를 입력하지 않았습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    Toast myToast = Toast.makeText(getApplicationContext(),"아이디와 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });

        find_id.setOnClickListener(new View.OnClickListener() { //아이디/비밀번호 찾기 버튼
            @Override
            public void onClick(View v) {
                Toast myToast = Toast.makeText(getApplicationContext(),"아이디/비밀번호 페이지로 이동합니다.", Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() { //회원가입 버튼 -> register 페이지로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class); //파라메터는 현재 액티비티, 전환될 액티비티
                startActivity(intent); //엑티비티 요청
            }
        });
    }
}
