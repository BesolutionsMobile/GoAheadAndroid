package com.example.lenovo.goahead.view.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.goahead.view.Model.otherProductModel;
import com.example.lenovo.goahead.view.Model.sellerModel;
import com.example.lenovo.goahead.view.interfaces.otherProductMvp;
import com.example.lenovo.goahead.view.interfaces.sellerMvp;

public class sellerPresenter implements sellerMvp.interfaces.presenter{
    private sellerMvp.interfaces.Model model;
    private sellerMvp.interfaces.View views;
    Context context;
    RecyclerView recyclerView;
    String user_id,category_id;


    public sellerPresenter(sellerMvp.interfaces.View view, Context context, RecyclerView recyclerView,String category_id) {
        this.views = view;
        this.context=context;
        this.recyclerView=recyclerView;
        this.category_id=category_id;
        initPresenter();
    }
    private void initPresenter() {
        model = new sellerModel();

    }
    @Override
    public void getData() {
        model.getdata(recyclerView,context,category_id);
    }
}
