package com.example.lenovo.goahead.view.customAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.goahead.view.fragments.generalService;
import com.example.lenovo.goahead.view.fragments.news;
import com.example.lenovo.goahead.view.fragments.resturants;
import com.example.lenovo.goahead.view.fragments.shopping;
import com.example.lenovo.goahead.view.fragments.spotrs;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    Context context;
    ArrayList<String>mylist;
    String id;

    private ArrayList<Fragment> myFragments=new ArrayList<Fragment>();

    public PagerAdapter(FragmentManager fm,ArrayList<String>mylist,Context context,String id) {
        super(fm);
        this.mylist=mylist;
        this.context=context;
        this.id=id;
    }

    @Override
    public Fragment getItem(int position) {

              news fragment = new news();
              Bundle bundle = new Bundle();
              bundle.putString("position", "" + position);
              fragment.setArguments(bundle);
              return fragment;
              }

    @Override
    public int getCount() {
        return mylist.size();
    }
    }
