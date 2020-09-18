package com.example.masionclarifiant;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pnikosis.materialishprogress.ProgressWheel;

public class DiagnoseCamMeasure extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_cam_measure);
        ProgressWheel wheel = new ProgressWheel(getApplicationContext());
    }
}
