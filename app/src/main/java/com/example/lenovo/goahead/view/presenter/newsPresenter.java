package com.example.lenovo.goahead.view.presenter;

import android.content.Context;

import com.example.lenovo.goahead.view.Model.catModel;
import com.example.lenovo.goahead.view.Model.newsModel;
import com.example.lenovo.goahead.view.interfaces.interfaceMVP;
import com.example.lenovo.goahead.view.interfaces.interfaceMVPP;

import java.util.ArrayList;

public class newsPresenter implements interfaceMVPP.interfaces.presenter {

    private interfaceMVPP.interfaces.Model model;
    private interfaceMVPP.interfaces.View views;
    Context context;

    public newsPresenter(interfaceMVPP.interfaces.View view,Context context) {
        this.views = view;
        this.context=context;
        initPresenter();
    }
    private void initPresenter() {
        model = new newsModel();
        views.element();
    }

    @Override
    public void getData() {
        ArrayList data=model.getdata(context);
        views.setviewdata(data);
    }
}
