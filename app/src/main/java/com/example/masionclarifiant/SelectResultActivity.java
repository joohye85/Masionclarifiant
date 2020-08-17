package com.example.masionclarifiant;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SelectResultActivity extends AppCompatActivity {
    public static TextView rst_tonerText;
    public static TextView rst_lotionText;

    public static String ingredients[] = new String[]{"aloe","greentea","byeongpul", "honey", "snail", "olive"};

    public static TextView rst_aloeText;
    public static TextView rst_greenteaText;
    public static TextView rst_byeoungpulText;
    public static TextView rst_honeyText;
    public static TextView rst_snailText;
    public static TextView rst_oliveText;

    public static TextView rst_teatreeText;
    public static TextView rst_rosemaryText;
    public static TextView rst_nothingText;
    public static String skinType2 = "";
    public static String perfume2 ="";
    public static StringBuilder ingredient = new StringBuilder();

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
        if((SelectFrag1.selectedkind).equals("toner")){
            rst_tonerText.setVisibility(View.VISIBLE);
            skinType2 ="토너";
        }
        else if((SelectFrag1.selectedkind).equals("lotion")){
            rst_lotionText.setVisibility(View.VISIBLE);
            skinType2 ="로션";
        }
/*
        System.out.println("배열 0의 값"+ SelectFrag2.selectedingredients[0]);
        System.out.println("배열 1의 값"+ SelectFrag2.selectedingredients[1]);
        System.out.println("배열 2의 값"+ SelectFrag2.selectedingredients[2]);
        System.out.println("배열 3의 값"+ SelectFrag2.selectedingredients[3]);
        System.out.println("배열 4의 값"+ SelectFrag2.selectedingredients[4]);
        System.out.println("배열 5의 값"+ SelectFrag2.selectedingredients[5]);

 */

        if (SelectFrag2.selectedingredients[0].equals(ingredients[0])) {
            rst_aloeText.setVisibility(View.VISIBLE);
            if (ingredient.length() == 0){
                ingredient.append("알로에"); }
            else
                ingredient.append(",알로에");
        }
        if(SelectFrag2.selectedingredients[1].equals(ingredients[1])) {
            rst_greenteaText.setVisibility(View.VISIBLE);
            if (ingredient.length() == 0){
                ingredient.append("녹차"); }
            else
                ingredient.append(",녹차");
        }
        if (SelectFrag2.selectedingredients[2].equals(ingredients[2])) {
            rst_byeoungpulText.setVisibility(View.VISIBLE);
            if (ingredient.length() == 0){
                ingredient.append("병풀"); }
            else
                ingredient.append(",병풀");
        }
        if (SelectFrag2.selectedingredients[3].equals(ingredients[3])) {
            rst_honeyText.setVisibility(View.VISIBLE);
            if (ingredient.length() == 0){
                ingredient.append("꿀");}
            else
                ingredient.append(",꿀");
        }
        if (SelectFrag2.selectedingredients[4].equals(ingredients[4])) {
            if (ingredient.length() == 0){
                        ingredient.append("달팽이"); }
            else
                ingredient.append(",달팽이");
        }
        if (SelectFrag2.selectedingredients[5].equals(ingredients[5])) {
            rst_oliveText.setVisibility(View.VISIBLE);
            if (ingredient.length() == 0){
                ingredient.append("올리브"); }
            else
                ingredient.append(",올리브");
        }

        if((SelectFrag3.selectedscent).equals("teatree")){
            rst_teatreeText.setVisibility(View.VISIBLE);
            perfume2 = "티트리";}
        else if((SelectFrag3.selectedscent).equals("rosemary")){
            rst_rosemaryText.setVisibility(View.VISIBLE);
            perfume2 = "로즈마리";
        }
        else if((SelectFrag3.selectedscent).equals("nothing")){
            rst_nothingText.setVisibility(View.VISIBLE);
            perfume2 = "무향";
        }

        // 서버 연동
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringBuilder i_name2 = new StringBuilder(ingredient.toString());
                String i_name = i_name2.toString();
                final String perfume = perfume2.toString();
                final String skinType = skinType2.toString();
                final String userID = getIntent().getStringExtra("userID");

                Response.Listener<String> res = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SelectResultActivity.this);
                                builder.setMessage("조합이 저장되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                ingredient.delete(0,ingredient.length());
                                                i_name2.delete(0,i_name2.length());
                                                Intent intent = new Intent(SelectResultActivity.this, CombiActivity.class);
                                                //intent.putExtra("userID",userID);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                SelectResultActivity.this.finish();
                                                SelectResultActivity.this.startActivity(intent);
                                                SelectFrag2.aloebox.setChecked(false);
                                                SelectFrag2.byeongpulbox.setChecked(false);
                                                SelectFrag2.greenteabox.setChecked(false);
                                                SelectFrag2.honeybox.setChecked(false);
                                                SelectFrag2.olivebox.setChecked(false);
                                                SelectFrag2.snailbox.setChecked(false);
                                                SelectFrag2.selectedingredients = new String[]{"x", "x", "x", "x", "x", "x"};
                                            }
                                        })
                                        .create()
                                        .show();

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
                SelectRequest selectRequest = new SelectRequest(userID, skinType, i_name, perfume, res);
                selectRequest.setShouldCache(false);
                RequestQueue queue = Volley.newRequestQueue(SelectResultActivity.this);
                queue.add(selectRequest);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFrag2.aloebox.setChecked(false);
                SelectFrag2.byeongpulbox.setChecked(false);
                SelectFrag2.greenteabox.setChecked(false);
                SelectFrag2.honeybox.setChecked(false);
                SelectFrag2.olivebox.setChecked(false);
                SelectFrag2.snailbox.setChecked(false);
                SelectFrag2.selectedingredients = new String[]{"x", "x", "x", "x", "x", "x"};
                SelectResultActivity.this.finish();
                SelectResultActivity.super.onBackPressed();
            }
        });
    }
}

