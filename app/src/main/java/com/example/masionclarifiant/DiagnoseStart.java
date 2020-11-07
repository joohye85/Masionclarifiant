package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class DiagnoseStart extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    public static DiagnoseThread diagnoseThread;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button backBtn, nextBtn, diagnoseBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnose_start);

        backBtn = findViewById(R.id.slide_back);
        nextBtn = findViewById(R.id.slide_next);
        diagnoseBtn = findViewById(R.id.intro_diagnose_btn);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDotLayout.removeAllViews();
                addDotsIndicator(1);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDotLayout.removeAllViews();
                addDotsIndicator(0);
            }
        });

        diagnoseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diagnoseThread = new DiagnoseThread();
                diagnoseThread.start();
                diagnoseThread.sendMessage("wngp0805");
                Intent intent = new Intent(DiagnoseStart.this, DiagnoseMeasure.class);
                startActivity(intent);
            }
        });

        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
    }

    public void addDotsIndicator(int position){
        if(position == 0){
            nextBtn.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.INVISIBLE);
            diagnoseBtn.setVisibility(View.GONE);
        }
        if(position == 1) {
            nextBtn.setVisibility(View.INVISIBLE);
            backBtn.setVisibility(View.VISIBLE);
            diagnoseBtn.setVisibility(View.VISIBLE);
        }
        mSlideViewPager.setCurrentItem(position);
        mDotLayout.removeAllViews();
        mDots = new TextView[2];
        for(int i=0; i<mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(40);
            mDots[i].setTextColor(getResources().getColor(R.color.colorGray));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0)
            mDots[position].setTextColor(getResources().getColor(R.color.colorDarkPink));
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DiagnoseStart.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}