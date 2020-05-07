package com.example.lenovo.goahead.view.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.goahead.view.Model.familiesModel;
import com.example.lenovo.goahead.view.Model.otherProductModel;
import com.example.lenovo.goahead.view.interfaces.familiesMvp;
import com.example.lenovo.goahead.view.interfaces.otherProductMvp;

public class otherProductPresenter implements otherProductMvp.interfaces.presenter{
    private otherProductMvp.interfaces.Model model;
    private otherProductMvp.interfaces.View views;
    Context context;
    RecyclerView recyclerView;


    public otherProductPresenter(otherProductMvp.interfaces.View view, Context context, RecyclerView recyclerView) {
        this.views = view;
        this.context=context;
        this.recyclerView=recyclerView;
        initPresenter();
    }
    private void initPresenter() {
        model = new otherProductModel();

    }
    @Override
    public void getData() {
        model.getdata(recyclerView,context);
    }
}