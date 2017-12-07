package com.example.yychiu.todolistapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by yychiu on 2017/12/1.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragList = null;
    private List<String> titleList = null;


    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragList , List<String> titleList) {
        super(fm);
        this.fragList = fragList;
        this.titleList = titleList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }
}
