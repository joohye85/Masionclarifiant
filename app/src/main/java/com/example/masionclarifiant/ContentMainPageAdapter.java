package com.example.masionclarifiant;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class ContentMainPageAdapter extends FragmentPagerAdapter {

    int numOfTabs; // Tab 개수

    // 아답터 생성자
    public ContentMainPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;

    }

    //해당 프레그먼트 호출
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RecentlyFrag1 tab1 = new RecentlyFrag1();
                System.out.println("0선택됨");
                return tab1;
            case 1:
                RecentlyFrag2 tab2 = new RecentlyFrag2();
                System.out.println("1선택됨");
                return tab2;
            default:
                return null;
        }
        //return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
