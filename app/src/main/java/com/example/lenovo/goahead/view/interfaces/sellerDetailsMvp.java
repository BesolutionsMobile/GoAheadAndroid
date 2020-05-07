package com.example.lenovo.goahead.view.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import de.hdodenhof.circleimageview.CircleImageView;

public interface sellerDetailsMvp {

    public interface interfaces {
        interface  View
        {

        }

        interface Model{

            String getdata(String user_id, TextView name, TextView email, TextView phone, CircleImageView imageView, Context context, RecyclerView recyclerView, ViewSwitcher viewSwitcher);

        }

        interface presenter{
            void getData();
        }
    }
}
