package com.example.lenovo.goahead.view.interfaces;

import android.content.Context;

import java.util.ArrayList;

public interface interfaceMVPP {
    public interface interfaces {
        interface  View
        {
            void element();
            void setviewdata(ArrayList data);
        }
        interface Model{
            ArrayList getdata(Context context);
        }
        interface presenter{
            void getData();
        }

    }
}
