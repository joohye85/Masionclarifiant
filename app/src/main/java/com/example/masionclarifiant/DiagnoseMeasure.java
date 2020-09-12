package com.example.masionclarifiant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pnikosis.materialishprogress.ProgressWheel;

public class DiagnoseMeasure extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_measure);
        ProgressWheel wheel = new ProgressWheel(getApplicationContext());
        //wheel.setBarColor(Col);
    }
}
