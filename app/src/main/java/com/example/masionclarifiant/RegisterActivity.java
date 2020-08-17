package com.example.masionclarifiant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    public static String btn_text = "";
    public static StringBuilder checkboxs = new StringBuilder("");
    public static  CheckBox checkBox;
    public static  CheckBox checkBox2;
    public static  CheckBox checkBox3;
    public static  CheckBox checkBox4;
    public static  CheckBox checkBox5;
    public static  CheckBox checkBox6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText pwText = (EditText) findViewById(R.id.passwordText);
        final RadioGroup gender = (RadioGroup) findViewById(R.id.radiogroup);
        final Spinner spinner = (Spinner) findViewById(R.id.register_page_spinner_age);
        final EditText pw2Text = (EditText)findViewById(R.id.password2);
        final Button check = (Button)findViewById(R.id.id_check);

        final Button dryBtn = (Button)findViewById(R.id.dryBtn);
        final Button oilBtn = (Button)findViewById(R.id.oilBtn);
        final Button sensitiveBtn = (Button)findViewById(R.id.sensitiveBtn);
        final Button complexBtn = (Button)findViewById(R.id.complexBtn);

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) findViewById(R.id.checkBox6);


        dryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_text = "건성";
                dryBtn.setPressed(true);
                Toast.makeText(getApplicationContext(), btn_text + " 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        oilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_text = "지성";
                dryBtn.setPressed(true);
                Toast.makeText(getApplicationContext(), btn_text + " 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        sensitiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_text = "민감성";
                dryBtn.setPressed(true);
                Toast.makeText(getApplicationContext(), btn_text + " 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        complexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_text = "복합성";
                dryBtn.setPressed(true);
                Toast.makeText(getApplicationContext(), btn_text + " 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //checkbox
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox.isChecked()) checkboxs.append("수분");
                }else{
                    if(checkBox.isChecked()) checkboxs.append(", 수분");
                }
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox2.isChecked()) checkboxs.append("각질");
                }else{
                    if(checkBox2.isChecked()) checkboxs.append(", 각질");
                }
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox3.isChecked()) checkboxs.append("진정");
                }else{
                    if(checkBox3.isChecked()) checkboxs.append(", 진정");
                }
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox4.isChecked()) checkboxs.append("탄력");
                }else{
                    if(checkBox4.isChecked()) checkboxs.append(", 탄력");
                }
            }
        });
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox5.isChecked()) checkboxs.append("미백");
                }else{
                    if(checkBox5.isChecked()) checkboxs.append(", 미백");
                }
            }
        });
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxs.length() == 0){
                    if(checkBox6.isChecked()) checkboxs.append("잡티");
                }else{
                    if(checkBox6.isChecked()) checkboxs.append(", 잡티");
                }
            }
        });

        String[] list = getResources().getStringArray(R.array.age_array);
        Button joinbtn = (Button) findViewById(R.id.joinbtn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, list);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final String[] age = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age[0] = (String) spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = idText.getText().toString();
                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            Boolean check = jsonObject.getBoolean("userID");
                            if(check){
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("중복 아이디 입니다.")
                                        .setNegativeButton("확인", null)
                                        .create()
                                        .show();
                            }
                        }catch (Exception e){e.printStackTrace();}
                    }
                };
                CheckRequest checkRequest = new CheckRequest(userID, res);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(checkRequest);
            }
        });

        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final StringBuilder stringBuilder = new StringBuilder(checkboxs.toString());
                checkBox.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                checkBox6.setChecked(false);
                checkboxs.delete(0, checkboxs.length());
                String interest = stringBuilder.toString();
                if(!pwText.getText().toString().equals(pw2Text.getText().toString())){
                    Toast.makeText(getApplicationContext(), "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    pw2Text.setText("");
                    pw2Text.requestFocus();
                    return;
                }
                String userID = idText.getText().toString();
                String userPW = pwText.getText().toString();
                RadioButton rb = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
                String gender = rb.getText().toString();
                /*if(idText.getText().toString().isEmpty() || pwText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "빈 항목이 존재합니다.", Toast.LENGTH_SHORT).show();
                    idText.requestFocus();
                    return;
                }*/
                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                System.out.println("회원 등록 성공");
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                RegisterActivity.this.startActivity(intent);
                                            }
                                        })
                                        .create()
                                        .show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPW, gender, age[0], btn_text, interest,  res);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
