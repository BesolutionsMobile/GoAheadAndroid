package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.PagerAdapter;
import com.example.lenovo.goahead.view.presenter.catPresenter;

public class cat extends AppCompatActivity{
    RecyclerView catList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    catPresenter catPresenter;
    ViewPager viewPager;
    Intent intent;
TabLayout tabLayout;
ImageView menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        menu=(ImageView)findViewById(R.id.menu);
        menu.setImageResource(R.drawable.logo);
        tabBar();

    }
    public void tabBar()
    {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.news));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.resturants));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.shopping));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sports));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.generalservice));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager=(ViewPager)findViewById(R.id.items);
        PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(getPosition());
        Toast.makeText(this, ""+getPosition(), Toast.LENGTH_SHORT).show();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                tabLayout.setScrollPosition(i,0,true);
                tabLayout.setSelected(true);
            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public int getPosition()
    {
        intent=getIntent();
        int positionTap=intent.getIntExtra("id",0)-1;
        return positionTap;
    }




}
