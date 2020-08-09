package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;

import org.json.JSONObject;

public class SelectResultActivity extends AppCompatActivity {
    public static TextView rst_tonerText;
    public static TextView rst_lotionText;

    public static String ingredients[] = new String[]{"aloe","greentea","byeongpul", "hoeny", "snail", "olive"};

    public static TextView rst_aloeText;
    public static TextView rst_greenteaText;
    public static TextView rst_byeoungpulText;
    public static TextView rst_honeyText;
    public static TextView rst_snailText;
    public static TextView rst_oliveText;

    public static TextView rst_teatreeText;
    public static TextView rst_rosemaryText;
    public static TextView rst_nothingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_result_page);

        rst_tonerText = (TextView) findViewById(R.id.rst_toner);
        rst_lotionText = (TextView) findViewById(R.id.rst_lotion);

        rst_aloeText = (TextView) findViewById(R.id.rst_aloe);
        rst_greenteaText = (TextView) findViewById(R.id.rst_greentea);
        rst_byeoungpulText = (TextView) findViewById(R.id.rst_byeoungpul);
        rst_honeyText = (TextView) findViewById(R.id.rst_honey);
        rst_snailText = (TextView) findViewById(R.id.rst_snail);
        rst_oliveText = (TextView) findViewById(R.id.rst_olive);

        rst_teatreeText = (TextView) findViewById(R.id.rst_teatree);
        rst_rosemaryText = (TextView) findViewById(R.id.rst_rosemary);
        rst_nothingText = (TextView) findViewById(R.id.rst_nothing);

        Button yes = (Button) findViewById(R.id.button3);
        Button no = (Button) findViewById(R.id.button4);

        //select_result_page.xml 에 텍스트 띄워주기
        if((SelectFrag1.selectedkind).equals("toner"))
            rst_tonerText.setVisibility(View.VISIBLE);
        else if((SelectFrag1.selectedkind).equals("lotion"))
            rst_lotionText.setVisibility(View.VISIBLE);

        /* 콘솔 확인용
        System.out.println("배열 0의 값"+ SelectFrag2.selectedingredients[0]);
        System.out.println("배열 1의 값"+ SelectFrag2.selectedingredients[1]);
        System.out.println("배열 2의 값"+ SelectFrag2.selectedingredients[2]);
        System.out.println("배열 3의 값"+ SelectFrag2.selectedingredients[3]);
        System.out.println("배열 4의 값"+ SelectFrag2.selectedingredients[4]);
        System.out.println("배열 5의 값"+ SelectFrag2.selectedingredients[5]);
        */

        if(SelectFrag2.selectedingredients[0].equals(ingredients[0]))
            rst_aloeText.setVisibility(View.VISIBLE);
        else if(SelectFrag2.selectedingredients[1].equals(ingredients[1]))
            rst_greenteaText.setVisibility(View.VISIBLE);
        else if(SelectFrag2.selectedingredients[2].equals(ingredients[2]))
            rst_byeoungpulText.setVisibility(View.VISIBLE);
        else if(SelectFrag2.selectedingredients[3].equals(ingredients[3]))
            rst_honeyText.setVisibility(View.VISIBLE);
        else if(SelectFrag2.selectedingredients[4].equals(ingredients[4]))
            rst_snailText.setVisibility(View.VISIBLE);
        else if(SelectFrag2.selectedingredients[5].equals(ingredients[5]))
            rst_oliveText.setVisibility(View.VISIBLE);

        if((SelectFrag3.selectedscent).equals("teatree"))
            rst_teatreeText.setVisibility(View.VISIBLE);
        else if((SelectFrag3.selectedscent).equals("rosemary"))
            rst_rosemaryText.setVisibility(View.VISIBLE);
        else if((SelectFrag3.selectedscent).equals("nothing"))
            rst_nothingText.setVisibility(View.VISIBLE);


        // 서버 연동
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String i_name;
                final String skinType;
                final String perfume;
                System.out.printf("TEST"); //버튼 작동 테스트용

                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt(response);
                            if(success==0){
                                String i_name = jsonObject.getString("i_name");
                                String perfume = jsonObject.getString("perfume");
                                String skinType = jsonObject.getString("skinType");

                                Intent intent = new Intent(SelectResultActivity.this, MainActivity.class);
                                SelectResultActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelectResultActivity.this);
                                builder.setMessage("다시 시도해주세요.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }catch (Exception e){e.printStackTrace();}
                    }
                };
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectResultActivity.super.onBackPressed();
            }
        });
    }
}

