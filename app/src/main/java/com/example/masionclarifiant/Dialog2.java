package com.example.masionclarifiant;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

/**
 * Created by Administrator on 2017-08-07.
 */

public class Dialog2 {
    private Context context;
    public static CheckBox box1;
    public static CheckBox box2;
    public static CheckBox box3;
    public static CheckBox box4;
    public static CheckBox box5;
    public static CheckBox box6;

    public static String selectedboxs[] = new String[]{"x","x","x","x","x","x"};

    public Dialog2(Context context) {
        this.context = context;
    }

    public void callFunction(StringBuilder stringBuilder) {
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.custom_dialog);
        if(!dlg.isShowing()) dlg.show();
        //final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Spinner spinner = (Spinner) dlg.findViewById(R.id.myskin_spinner);
        box1 = (CheckBox) dlg.findViewById(R.id.checkBox);
        box2 = (CheckBox) dlg.findViewById(R.id.checkBox2);
        box3 = (CheckBox) dlg.findViewById(R.id.checkBox3);
        box4 = (CheckBox) dlg.findViewById(R.id.checkBox4);
        box5 = (CheckBox) dlg.findViewById(R.id.checkBox5);
        box6 = (CheckBox) dlg.findViewById(R.id.checkBox6);

        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box1.isChecked()){
                    selectedboxs[0] = "수분";
                }
                else{
                    selectedboxs[0] = "x";
                }
            }
        });
        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box2.isChecked()){
                    selectedboxs[1] = "각질";
                }
                else{
                    selectedboxs[1] = "x";
                }
            }
        });
        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box3.isChecked()){
                    selectedboxs[2] = "진정";
                }
                else{
                    selectedboxs[2] = "x";
                }
            }
        });
        box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box4.isChecked()){
                    selectedboxs[3] = "탄력";
                }
                else{
                    selectedboxs[3] = "x";
                }
            }
        });
        box5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box5.isChecked()){
                    selectedboxs[4] = "미백";
                }
                else{
                    selectedboxs[4] = "x";
                }
            }
        });
        box6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box6.isChecked()){
                    selectedboxs[5] = "잡티";
                }
                else{
                    selectedboxs[5] = "x";
                }
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MypageActivity.feedback_save = message.getText().toString();
                Toast.makeText(context, "입력하신 내용이 모두 저장됐습니다!",Toast.LENGTH_SHORT).show();
                dlg.dismiss();
                //MypageActivity.tnf = true;
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        // dlg.show();
    }
}