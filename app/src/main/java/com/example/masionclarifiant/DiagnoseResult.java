package com.example.masionclarifiant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;

public class DiagnoseResult extends AppCompatActivity {
    private LineChart lineChart;
    private BarChart barChart;
    Button goHomeBtn, goRecommendBtn;
    float barWidth = 0.3f;
    float barSpace = 0f;
    float groupSpace = 0.4f;
    int groupCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_result);
        goHomeBtn = findViewById(R.id.go_home_btn);
        goRecommendBtn = findViewById(R.id.go_recommend_btn);

        barChart = (BarChart)findViewById(R.id.barChart);
        barChart.setDescription(null);
        barChart.setScaleEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        ArrayList xVals = new ArrayList();
        xVals.add("주름");
        xVals.add("기미");
        xVals.add("청결");
        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();
        yVals1.add(new BarEntry(1, (float) 1));
        yVals2.add(new BarEntry(1, (float) 2));
        yVals1.add(new BarEntry(2, (float) 3));
        yVals2.add(new BarEntry(2, (float) 4));
        yVals1.add(new BarEntry(3, (float) 5));
        yVals2.add(new BarEntry(3, (float) 6));

        BarDataSet bar_set1, bar_set2;
        bar_set1 = new BarDataSet(yVals1, "내 피부");
        bar_set1.setColor(ContextCompat.getColor(this, R.color.colorDarkPink));
        bar_set2 = new BarDataSet(yVals2, "연령 평균");
        bar_set2.setColor(ContextCompat.getColor(this, R.color.colorBabyPink));
        BarData barData = new BarData(bar_set1, bar_set2);
        barData.setValueFormatter(new LargeValueFormatter());
        barChart.setData(barData);
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.getData().setHighlightEnabled(false);
        barChart.invalidate();
        XAxis bar_xAxis = barChart.getXAxis();
        bar_xAxis.setGranularity(1f);
        bar_xAxis.setGranularityEnabled(true);
        bar_xAxis.setCenterAxisLabels(true);
        bar_xAxis.setDrawGridLines(false);
        bar_xAxis.setAxisMaximum(3);
        bar_xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bar_xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));

        barChart.getAxisRight().setEnabled(false);
        YAxis bar_leftAxis = barChart.getAxisLeft();
        bar_leftAxis.setValueFormatter(new LargeValueFormatter());
        bar_leftAxis.setDrawGridLines(false);
        bar_leftAxis.setSpaceTop(35f);
        bar_leftAxis.setAxisMinimum(0f);

        Legend l = barChart.getLegend();
        l.setTextSize(15f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);

        ArrayList<Entry> entry1 = new ArrayList<>();

        lineChart = (LineChart) findViewById(R.id.age_chart);//layout의 id

        LineData chartData = new LineData();

        //아직 수정 필요 -> 그래프 선 색상, 수치 바꿈
        entry1.add(new Entry(2, 33));
        entry1.add(new Entry(8, 22));
        entry1.add(new Entry(10, 40));

        LineDataSet list_set1 = new LineDataSet(entry1, "피부나이");
        list_set1.setColor(Color.rgb(0, 0, 0));
        list_set1.setValueTextSize(10);
        list_set1.setCircleColor(ContextCompat.getColor(this, R.color.colorDarkPink));
        list_set1.setCircleRadius(10);
        chartData.addDataSet(list_set1);

        YAxis line_yAxisLeft = lineChart.getAxisLeft();
        line_yAxisLeft.setAxisMaximum(50);
        line_yAxisLeft.setAxisMinimum(20);

        XAxis line_xAxis = lineChart.getXAxis();
        line_xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        line_xAxis.setAxisMinimum(1);
        line_xAxis.setAxisMaximum(12);

        line_xAxis.setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.setData(chartData);
        lineChart.invalidate();

        goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiagnoseResult.this,MainActivity.class);
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