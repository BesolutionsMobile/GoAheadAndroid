package com.example.lenovo.goahead.view.interfaces;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import me.relex.circleindicator.CircleIndicator;

public interface userData {
    public interface interfaces {
        interface  View
        {

        }

        interface Model{

            String getdata(TextView Name, ImageView profile,Context context);

        }

        interface presenter{
            void getData();
        }
    }
}
