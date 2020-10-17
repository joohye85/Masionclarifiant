package com.example.masionclarifiant;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;

public class DiagnoseRecommend extends AppCompatActivity {
    PieChart pieChart;
    public static int moisture = 0; //db에서 수분값 받아옴
    public static int oil = 0; //db에서 유분값 받아옴
    public static int blemish = 0; //db에서 잡티 받아옴
    public static String test ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_recommend);
        final String userID = getIntent().getStringExtra("userID");

        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    moisture = jsonObject.getInt("moisture");
                    oil = jsonObject.getInt("oil");
                    blemish = jsonObject.getInt("pimple");

                }catch (Exception e){e.printStackTrace();}

            }
        };
        DiagnoseRequest diagnoseRequest = new DiagnoseRequest(userID, res);
        RequestQueue queue = Volley.newRequestQueue(DiagnoseRecommend.this);
        queue.add(diagnoseRequest);

        String water = null; //0.3 증가인지 감소인지 보내기
        Pair[] mix = new Pair[3];

        String[] moisture_arr = {"알로에", "꿀"};
        String[] oil_arr = {"달팽이", "유자"};
        String[] blemish_arr = {"병풀", "백차"};

        Random r = new Random();
        //알로에 or 꿀, 달팽이 or 유자, 병풀 or 백차를 위에서 만든 배열에서 랜덤으로 뽑아서 변수에 대입
        String moisture_ing = moisture_arr[r.nextInt(2)];
        String oil_ing = oil_arr[r.nextInt(2)];
        String blemish_ing = blemish_arr[r.nextInt(2)];
        System.out.println(moisture_ing + ", " + oil_ing + ", " + blemish_ing);
        System.out.println(moisture + oil + blemish);
        if(moisture <= 33) {
            mix[0] = new Pair(moisture_ing, 60);
        }
        else if(moisture <= 67){
            mix[0] = new Pair(moisture_ing, 30);
        }
        else{
            mix[0] = new Pair(moisture_ing, 10);
        }

        if(oil <= 33){
            mix[1] = new Pair(oil_ing, 60);
        }
        else if(oil <= 67){
            mix[1] = new Pair(oil_ing, 30);
        }
        else{
            mix[1] = new Pair(oil_ing, 10);
        }

        if(blemish <= 33){
            mix[2] = new Pair(blemish_ing, 60);
        }
        else if(blemish <= 67){
            mix[2] = new Pair(blemish_ing, 30);
        }
        else{
            mix[2] = new Pair(blemish_ing, 10);
        }

        if(moisture <= 50){
            water = "증가";
        }
        else{
            water = "감소";
        }

        //pieChart
        pieChart = (PieChart)findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawSliceText(false);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(mix[0].getRate(),mix[0].getIngredient()));
        yValues.add(new PieEntry(mix[1].getRate(),mix[1].getIngredient()));
        yValues.add(new PieEntry(mix[2].getRate(),mix[2].getIngredient()));

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setValueTextColor(Color.YELLOW);
        dataSet.setValueFormatter(new PercentFormatter());
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);

        Legend l = pieChart.getLegend();
        l.setTextSize(15f);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        pieChart.setData(data);
    }
}
