package com.example.masionclarifiant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

/* 네비게이션 바 구현 필요 */
public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_page);

        //TabLayout
        TabLayout tabs = (TabLayout)findViewById(R.id.tab_layout);
        tabs.addTab(tabs.newTab().setText("종류선택"));
        tabs.addTab(tabs.newTab().setText("성분선택"));
        tabs.addTab(tabs.newTab().setText("향선택"));
        tabs.setTabGravity(tabs.GRAVITY_FILL);

        //어답터설정
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final CustomFragmentPagerAdapter myPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(myPagerAdapter);

        //탭메뉴를 클릭하면 해당 프래그먼트로 변경-싱크화
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        //String skinType = rb.getText().toString();

        Button choiceBtn = (Button)findViewById(R.id.choice);
        choiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, SelectResultActivity.class);
                SelectActivity.this.startActivity(intent);
                finish();
            }
        });
    }
}