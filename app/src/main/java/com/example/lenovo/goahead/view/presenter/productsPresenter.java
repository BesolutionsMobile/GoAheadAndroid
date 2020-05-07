package com.example.lenovo.goahead.view.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.lenovo.goahead.view.Model.familiesModel;
import com.example.lenovo.goahead.view.Model.productsModel;
import com.example.lenovo.goahead.view.interfaces.familiesMvp;
import com.example.lenovo.goahead.view.interfaces.productsMvp;

public class productsPresenter implements productsMvp.interfaces.presenter{
    private productsMvp.interfaces.Model model;
    private productsMvp.interfaces.View views;
    Context context;
    RecyclerView recyclerView;
    String user_id,seller_id;



    public productsPresenter(productsMvp.interfaces.View view, Context context, RecyclerView recyclerView,String user_id,String seller_id) {
        this.views = view;
        this.context=context;
        this.recyclerView=recyclerView;
        this.user_id=user_id;
        this.seller_id=seller_id;
        initPresenter();
    }
    private void initPresenter() {
        model = new productsModel();

    }
    @Override
    public void getData() {
        model.getdata(recyclerView,context,user_id,seller_id);
    }
}
