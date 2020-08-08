package com.example.masionclarifiant;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;



public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //어답터설정
        final ViewPager viewPager = (ViewPager) findViewById(R.id.recently_purchased_products);
        final ContentMainPageAdapter myPagerAdapter = new ContentMainPageAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(myPagerAdapter);

    }
}
