package com.example.lenovo.goahead.view.interfaces;

import java.util.ArrayList;

public interface interfaceMVP {
    public interface interfaces {
        interface  View
        {
            void element();
            void setviewdata(ArrayList data);
        }
        interface Model{
            ArrayList getdata();
        }
        interface presenter{
            void getData();
        }

    }

}
