package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        /*
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);
        */

        //어답터설정
        final ViewPager viewPager = (ViewPager) findViewById(R.id.recently_purchased_products);
        final ContentMainPageAdapter myPagerAdapter = new ContentMainPageAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(myPagerAdapter);


        TextView selected_inf = (TextView) findViewById(R.id.selected_inf);
        selected_inf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}
