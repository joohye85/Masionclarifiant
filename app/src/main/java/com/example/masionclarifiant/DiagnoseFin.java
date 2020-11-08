package com.example.masionclarifiant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiagnoseFin extends AppCompatActivity {
    Button goDiagnoseResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_fin);
        final String userID = getIntent().getStringExtra("userID");
        TextView measure_text = (TextView) findViewById(R.id.measuer_text);
        measure_text.setText(userID+"님 오늘의 피부 상태를 측정해보세요!");
        goDiagnoseResult = (Button)findViewById(R.id.go_diagnose_result);
        goDiagnoseResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseFin.this, DiagnoseResult.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }
}
