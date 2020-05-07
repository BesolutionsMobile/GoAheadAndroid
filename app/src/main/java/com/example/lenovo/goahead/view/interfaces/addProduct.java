package com.example.lenovo.goahead.view.interfaces;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.ArrayList;

public interface addProduct {

    public interface interfaces {
        interface  View
        {

        }

        interface Model{
            String getdata(String user_id, String category_id, String title, String Description, String price, Context context, final String ImageView, Dialog dialog, ArrayList<String> arrayList);
        }

        interface presenter{
            void getData();
        }

    }
}
