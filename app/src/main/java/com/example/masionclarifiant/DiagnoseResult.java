package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class DiagnoseResult extends AppCompatActivity {
    private LineChart lineChart;
    Button goHomeBtn, goRecommendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_result);
        goHomeBtn = findViewById(R.id.go_home_btn);
        goRecommendBtn = findViewById(R.id.go_recommend_btn);

        ArrayList<Entry> entry1 = new ArrayList<>();

        lineChart = (LineChart) findViewById(R.id.age_chart);//layout의 id
        LineData chartData = new LineData();

        //아직 수정 필요 -> 그래프 선 색상, 수치 바꿈
        entry1.add(new Entry(2, 3));
        entry1.add(new Entry(8, 9));
        entry1.add(new Entry(10, 5));

        LineDataSet set1 = new LineDataSet(entry1, "피부나이");
        chartData.addDataSet(set1);

        lineChart.setData(chartData);

        lineChart.invalidate();

        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseResult.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        goRecommendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseResult.this,DiagnoseRecommend.class);
                startActivity(intent);
            }
        });
    }
}
