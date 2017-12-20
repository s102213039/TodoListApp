package com.example.yychiu.todolistapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by yychiu on 2017/12/1.
 */

public class MyPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragList = null;
    private List<String> titleList = null;
    private OnReloadListener listener;
    private FragmentManager fragmentManager;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragList , List<String> titleList) {
        super(fm);
        fragmentManager =fm;
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

    public void setPagerItems(List<Fragment> items) {
        if (items != null) {
            for (int i = 0; i < fragList.size(); i++) {
                fragmentManager.beginTransaction().remove(fragList.get(i)).commit();
            }
            fragList = items;
        }
    }

    public void reLoad(){
        if(listener!=null){
            listener.onReload();
        }
        this.notifyDataSetChanged();
    }
    public void setOnReloadListener(OnReloadListener onReloadListener){
        this.listener=onReloadListener;
    }

    public  interface OnReloadListener{
        void onReload();
    }
}
