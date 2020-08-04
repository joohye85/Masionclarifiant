package com.example.masionclarifiant;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    int numOfTabs; // Tab 개수

    // 아답터 생성자
    public CustomFragmentPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;

    }

    //해당 프레그먼트 호출
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SelectFrag1 tab1 = new SelectFrag1();
                System.out.println("0선택됨");
                return tab1;
            case 1:
                SelectFrag2 tab2 = new SelectFrag2();
                System.out.println("1선택됨");
                return tab2;
            case 2:
                SelectFrag3 tab3 = new SelectFrag3();
                System.out.println("2선택됨");
                return tab3;
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
