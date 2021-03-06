package com.example.yychiu.todolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.yychiu.todolistapp.MyPagerAdapter.OnReloadListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private String[] titleStr = {"待辦事項", "已完成"};

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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateActivity.class));
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
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragList, titleList);
        pagerAdapter.setOnReloadListener(new OnReloadListener() {
            @Override
            public void onReload() {
                fragList = null;
                List<Fragment> list = new ArrayList<>();
                list.add(new TodoFragment());
                list.add(new DoneFragment());
                pagerAdapter.setPagerItems(list);
                pager.setAdapter(pagerAdapter);
            }
        });
        pager.setAdapter(pagerAdapter);
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

    public MyPagerAdapter getAdapter() {
        return pagerAdapter;
    }
}
