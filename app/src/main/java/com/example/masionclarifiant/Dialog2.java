package com.example.masionclarifiant;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

/**
 * Created by Administrator on 2017-08-07.
 */

public class Dialog2 {
    private Context context;
    public Dialog2(Context context) {
        this.context = context;
    }

    public void callFunction() {
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.custom_dialog);
        if(!dlg.isShowing()) dlg.show();
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MypageActivity.feedback_save = message.getText().toString();
                Toast.makeText(context, "입력하신 내용이 모두 저장됐습니다. FEEDBACK 버튼을 한 번 더 눌러주세요!",Toast.LENGTH_SHORT).show();
                dlg.dismiss();
                MypageActivity.tnf = true;
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