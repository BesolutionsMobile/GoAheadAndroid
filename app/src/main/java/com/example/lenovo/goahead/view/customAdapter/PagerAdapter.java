package com.example.lenovo.goahead.view.customAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lenovo.goahead.view.fragments.generalService;
import com.example.lenovo.goahead.view.fragments.news;
import com.example.lenovo.goahead.view.fragments.resturants;
import com.example.lenovo.goahead.view.fragments.shopping;
import com.example.lenovo.goahead.view.fragments.spotrs;

import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    private List<Fragment> myFragments;

    public PagerAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs=numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                news news=new news();
                return news;
            case 1:
                resturants resturants=new resturants();
                return resturants;
            case 2:
                shopping shopping=new shopping();
                return shopping;
            case 3:
            spotrs sports=new spotrs();
            return sports;
            case 4:
                generalService generalService =new generalService();
                return generalService;
                default:
                    return null;
                }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
