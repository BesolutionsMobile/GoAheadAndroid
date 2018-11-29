package com.example.lenovo.goahead.view.presenter;

import com.example.lenovo.goahead.view.Model.catModel;
import com.example.lenovo.goahead.view.interfaces.interfaceMVP;

import java.util.ArrayList;

public class catPresenter implements interfaceMVP.interfaces.presenter {

    private interfaceMVP.interfaces.Model model;
    private interfaceMVP.interfaces.View views;


    public catPresenter(interfaceMVP.interfaces.View view) {
        this.views = view;
        initPresenter();
    }
    private void initPresenter() {
        model = new catModel();
        views.element();
    }

    @Override
    public void getData() {
        ArrayList data=model.getdata();
        views.setviewdata(data);
    }

}
