package com.example.lenovo.goahead.view.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public interface sellerMvp  {
    public interface interfaces {
        interface View {

        }

        interface Model {
            ArrayList getdata(RecyclerView recyclerView, Context context,String category_id);
        }

        interface presenter {
            void getData();
        }
    }
}
