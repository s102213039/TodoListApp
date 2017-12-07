package com.example.yychiu.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
/*
    private FragmentManager fragmentManager;
    private TodoFragment todoFragment;
*/

    private ViewPager pager = null;
    private MyPagerAdapter pagerAdapter = null;
    private View[] views = new View[2];
    private int[] viewId = {R.layout.fragment_todo, R.layout.fragment_done};
    private String[] titleStr = {"待辦事項","已完成"};

    private List<View> viewList = null;
    private List<String> titleList = null;

    private PagerTabStrip tab = null;
    private List<Fragment> fragList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        tab.setDrawFullUnderline(false);
        pager.setAdapter(pagerAdapter);
/*
        todoFragment = TodoFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container,todoFragment);
        transaction.commit();
*/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreateActivity.class));
            }
        });
    }

    private void initViews() {
        pager = findViewById(R.id.vpager);
        tab = findViewById(R.id.vtab);
        fragList = new ArrayList<>();
        viewList = new ArrayList<>();
        titleList = new ArrayList<>();

        for (int i = 0; i < viewId.length; i++) {
            views[i] = View.inflate(this, viewId[i], null);
            viewList.add(views[i]);
        }
        Collections.addAll(titleList, titleStr);

        fragList.add(new TodoFragment());
        fragList.add(new DoneFragment());
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),fragList,titleList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
