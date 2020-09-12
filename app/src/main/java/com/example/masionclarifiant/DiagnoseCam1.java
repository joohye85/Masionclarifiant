package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DiagnoseCam1 {
    Button takePictureBtn;
    public class DiagnoseMeasure extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.diagnose_cam1);
            takePictureBtn = findViewById(R.id.take_picture_btn);
            takePictureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DiagnoseMeasure.this, com.example.masionclarifiant.DiagnoseCam1.class);
                    startActivity(intent);
                }
            });
        }
    }
}
