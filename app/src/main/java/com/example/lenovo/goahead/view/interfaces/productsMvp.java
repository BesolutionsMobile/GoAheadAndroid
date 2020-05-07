package com.example.lenovo.goahead.view.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public interface productsMvp {


        public interface interfaces {
            interface View {

            }

            interface Model {
                ArrayList getdata(RecyclerView recyclerView, Context context,String user_id,String seller_id);
            }

            interface presenter {
                void getData();
                }
        }
}
