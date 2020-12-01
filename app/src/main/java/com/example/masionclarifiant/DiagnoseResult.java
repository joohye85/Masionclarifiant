package com.example.masionclarifiant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
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
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DiagnoseResult extends AppCompatActivity {
    private LineChart lineChart;
    private BarChart barChart;
    private CircularProgressBar moistureProgressbar, oilProgressbar;
    Button goHomeBtn, goRecommendBtn;
    float barWidth = 0.3f;
    float barSpace = 0f;
    float groupSpace = 0.4f;
    int groupCount = 3;
    public static int moisture = 0; //db에서 수분값 받아옴
    public static int oil = 0; //db에서 유분값 받아옴
    public static int blemish = 0; //db에서 잡티 받아옴
    public static double clean = 0; //청결도
    public static double wrinkle = 0; //주름
    public static double liver_spot = 0; //기미
    public static int skinDate2, skinDate3, skinDate1 = 1;
    public static int Skinage1, Skinage2, Skinage3 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_result);
        moistureProgressbar = (CircularProgressBar)findViewById(R.id.moistureProgressBar);
        oilProgressbar = (CircularProgressBar)findViewById(R.id.oilProgressBar);
        TextView moi = (TextView) findViewById(R.id.moi);
        TextView oi = (TextView) findViewById(R.id.oi);
        goHomeBtn = findViewById(R.id.go_home_btn);
        goRecommendBtn = findViewById(R.id.go_recommend_btn);
        final String userID = getIntent().getStringExtra("userID");
        final String jsonResult = getIntent().getStringExtra("jsonResult");
        System.out.println("jsonResult: " + jsonResult);
        if(jsonResult != null) {
            JSONObject jsonObject = null;
            System.out.println("jsonResult not null");
            try {
                jsonObject = new JSONObject(jsonResult);
                clean = jsonObject.getDouble("clean");
                Skinage1 = jsonObject.getInt("skin_age1");
                Skinage2 = jsonObject.getInt("skin_age2");
                Skinage3 = jsonObject.getInt("skin_age3");
                skinDate1 = Integer.parseInt(jsonObject.getString("skinDate1").substring(5, 7));
                skinDate2 = Integer.parseInt(jsonObject.getString("skinDate2").substring(5, 7));
                skinDate3 = Integer.parseInt(jsonObject.getString("skinDate3").substring(5, 7));
                liver_spot = jsonObject.getDouble("liver_spot");
                wrinkle = jsonObject.getDouble("wrinkle");
                moisture = jsonObject.getInt("moisture");
                oil = jsonObject.getInt("oil");
                blemish = jsonObject.getInt("blemish");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (clean <= 2) {
            clean = 1;
        } else if (clean <= 4) {
            System.out.println("여기");
            clean = 2;
        } else if (clean <= 7) {
            clean = 3;
        } else if (clean <= 11) {
            clean = 4;
        } else if (clean <= 15) {
            clean = 5;
        } else if (clean <= 20) {
            clean = 6;
        } else if (clean <= 25) {
            clean = 7;
        } else if (clean <= 30) {
            clean = 8;
        } else if (clean <= 40) {
            clean = 9;
        }
        else{
            clean = 10;
        }

        if (wrinkle <= 60) {
            wrinkle = 1;
        } else if (wrinkle <=  130) {
            wrinkle = 2;
        } else if (wrinkle <=  200) {
            wrinkle = 3;
        } else if (wrinkle <= 270) {
            wrinkle = 4;
        } else if (wrinkle <=  340) {
            wrinkle = 5;
        } else if (wrinkle <=  430) {
            wrinkle = 6;
        } else if (wrinkle <=  500) {
            wrinkle = 7;
        } else if (wrinkle <=  600) {
            wrinkle = 8;
        } else if (wrinkle <=  750) {
            wrinkle = 9;
        } else{
            wrinkle = 10;
        }

        moi.setText("수분\n\n" + moisture + "%");
        oi.setText("유분\n\n" + oil + "%");
        moistureProgressbar.setProgress(moisture);
        oilProgressbar.setProgress(oil);

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
        yVals1.add(new BarEntry(1, (float) wrinkle));
        yVals2.add(new BarEntry(1, (float) 3));
        yVals1.add(new BarEntry(2, (float) liver_spot));
        yVals2.add(new BarEntry(2, (float) 3));
        yVals1.add(new BarEntry(3, (float) clean));
        yVals2.add(new BarEntry(3, (float) 2));

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
        bar_leftAxis.setAxisMaximum(10f);

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

        entry1.add(new Entry(skinDate3, Skinage3));
        entry1.add(new Entry(skinDate2, Skinage2));
        entry1.add(new Entry(skinDate1, Skinage1));

        LineDataSet list_set1 = new LineDataSet(entry1, "피부나이");
        list_set1.setColor(Color.rgb(0, 0, 0));
        list_set1.setValueTextSize(10);
        list_set1.setCircleColor(ContextCompat.getColor(this, R.color.colorDarkPink));
        list_set1.setCircleRadius(10);
        chartData.addDataSet(list_set1);

        YAxis line_yAxisLeft = lineChart.getAxisLeft();
        line_yAxisLeft.setAxisMaximum(70);
        line_yAxisLeft.setAxisMinimum(10);

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
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        goRecommendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String userID2 = getIntent().getStringExtra("userID");
                Intent intent = new Intent(DiagnoseResult.this,DiagnoseRecommend.class);
                System.out.println("goRecommendBtn: " + moisture + ", oil: " + oil + ", blemish: " + blemish);
                intent.putExtra("moisture", moisture);
                intent.putExtra("oil", oil);
                intent.putExtra("blemish", blemish);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        String userID3 = getIntent().getStringExtra("userID");
        Intent intent = new Intent(DiagnoseResult.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("userID", userID3);

        startActivity(intent);
    }

}
