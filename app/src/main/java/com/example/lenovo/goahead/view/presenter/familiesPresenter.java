package com.example.lenovo.goahead.view.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.goahead.view.Model.familiesModel;
import com.example.lenovo.goahead.view.interfaces.familiesMvp;

public class familiesPresenter implements familiesMvp.interfaces.presenter{
    private familiesMvp.interfaces.Model model;
    private familiesMvp.interfaces.View views;
    Context context;
    RecyclerView recyclerView;


    public familiesPresenter(familiesMvp.interfaces.View view, Context context, RecyclerView recyclerView) {
        this.views = view;
        this.context=context;
        this.recyclerView=recyclerView;
        initPresenter();
    }
    private void initPresenter() {
        model = new familiesModel();

    }
    @Override
    public void getData() {
        model.getdata(recyclerView,context);
    }
}
