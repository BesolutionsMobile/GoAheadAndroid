package com.example.lenovo.goahead.view.interfaces;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import me.relex.circleindicator.CircleIndicator;

public interface productDetailsMvps {
    public interface interfaces {
        interface  View
        {

        }

        interface Model{

            String getdata(String user_id, TextView name, TextView price, TextView descripition, Context context, CircleIndicator circleIndicator, ViewPager viewPager, ViewSwitcher viewSwitcher,TextView Title,Button goToSeller);

        }

        interface presenter{
            void getData();
        }
    }
}
