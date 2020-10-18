package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.pnikosis.materialishprogress.ProgressWheel;

public class DiagnoseCamMeasure extends AppCompatActivity {
    Button goSeeResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_cam_measure);
        goSeeResult = (Button)findViewById(R.id.go_see_result);
        goSeeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseCamMeasure.this, DiagnoseFin.class);
                startActivity(intent);
            }
        });
    }
}
