package com.example.lenovo.goahead.view.presenter;

import com.example.lenovo.goahead.view.Model.catModel;
import com.example.lenovo.goahead.view.Model.generalServiceModel;
import com.example.lenovo.goahead.view.interfaces.interfaceMVP;

import java.util.ArrayList;

public class generalServicePresenter implements interfaceMVP.interfaces.presenter {
    private interfaceMVP.interfaces.Model model;
    private interfaceMVP.interfaces.View views;


    public generalServicePresenter(interfaceMVP.interfaces.View view) {
        this.views = view;
        initPresenter();
    }
    private void initPresenter() {
        model = new generalServiceModel();
        views.element();
    }

    @Override
    public void getData() {
        ArrayList data=model.getdata();
        views.setviewdata(data);
    }

}
