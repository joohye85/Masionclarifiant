package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DiagnoseSkinCheck extends AppCompatActivity {
    Button goSkinMeasure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_skin_check);
        goSkinMeasure = (Button)findViewById(R.id.go_skin_cam);
        final String userID = getIntent().getStringExtra("userID");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");
        goSkinMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SocketService socketService = MainActivity.socketService;
                //socketService.send("exit");

                Intent intent = new Intent(DiagnoseSkinCheck.this, DiagnoseCamMeasure.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}