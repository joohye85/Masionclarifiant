package com.example.masionclarifiant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DiagnoseFin extends AppCompatActivity {
    Button goDiagnoseResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_fin);
        goDiagnoseResult = (Button)findViewById(R.id.go_diagnose_result);
        goDiagnoseResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseFin.this, DiagnoseResult.class);
                startActivity(intent);
            }
        });
    }
}
