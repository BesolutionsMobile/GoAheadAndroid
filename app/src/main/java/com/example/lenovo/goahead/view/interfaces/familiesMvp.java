package com.example.lenovo.goahead.view.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public interface familiesMvp  {
    public interface interfaces {
        interface  View
        {

        }

        interface Model{
            ArrayList getdata(RecyclerView recyclerView, Context context);
        }

        interface presenter{
            void getData();
        }
    }
}
