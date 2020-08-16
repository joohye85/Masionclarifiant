package com.example.masionclarifiant;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FindIngredientActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_ingredeint_page);

        final ImageView aloeimg = (ImageView)findViewById(R.id.aloeimg);
        ImageView greenteaimg = (ImageView)findViewById(R.id.greenteaimg);
        ImageView byeongpulimg = (ImageView)findViewById(R.id.byeongpulimg);
        ImageView honeyimg = (ImageView)findViewById(R.id.honeyimg);
        ImageView snailimg = (ImageView)findViewById(R.id.snailimg);
        ImageView oliveimg = (ImageView)findViewById(R.id.oliveimg);
        ImageView teatreeimg = (ImageView)findViewById(R.id.teatreimg);
        ImageView rosemaryimg = (ImageView)findViewById(R.id.rosemaryimg);
        TextView back_btn = (TextView)findViewById(R.id.back_btn);

        /*
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindIngredientActivity.this.onBackPressed();
            }
        });
        */

        aloeimg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder dlg = new AlertDialog.Builder(FindIngredientActivity.this, R.style.MyDialogTheme);
                dlg.setTitle("■ 알로에의 효능");
                dlg.setMessage(R.string.aloe);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

        greenteaimg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder dlg = new AlertDialog.Builder(FindIngredientActivity.this, R.style.MyDialogTheme);
                dlg.setTitle("■ 녹차의 효능");
                dlg.setMessage(R.string.greentea);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

        byeongpulimg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder dlg = new AlertDialog.Builder(FindIngredientActivity.this, R.style.MyDialogTheme);
                dlg.setTitle("■ 병풀의 효능");
                dlg.setMessage(R.string.byeongpul);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

       honeyimg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder dlg = new AlertDialog.Builder(FindIngredientActivity.this, R.style.MyDialogTheme);
                dlg.setTitle("■ 꿀의 효능");
                dlg.setMessage(R.string.honey);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

        snailimg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder dlg = new AlertDialog.Builder(FindIngredientActivity.this, R.style.MyDialogTheme);
                dlg.setTitle("■ 달팽이의 효능");
                dlg.setMessage(R.string.snail);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

        oliveimg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder dlg = new AlertDialog.Builder(FindIngredientActivity.this, R.style.MyDialogTheme);
                dlg.setTitle("■ 올리브의 효능");
                dlg.setMessage(R.string.olive);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

        teatreeimg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder dlg = new AlertDialog.Builder(FindIngredientActivity.this, R.style.MyDialogTheme);
                dlg.setTitle("■ 티트리의 효능");
                dlg.setMessage(R.string.teatree);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

        rosemaryimg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder dlg = new AlertDialog.Builder(FindIngredientActivity.this, R.style.MyDialogTheme);
                dlg.setTitle("■ 로즈마리의 효능");
                dlg.setMessage(R.string.rosemary);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

    }
}
