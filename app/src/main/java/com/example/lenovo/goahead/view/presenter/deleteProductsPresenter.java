package com.example.lenovo.goahead.view.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.goahead.view.Model.deleteProductsModel;
import com.example.lenovo.goahead.view.Model.productsModel;
import com.example.lenovo.goahead.view.interfaces.productsMvp;

public class deleteProductsPresenter implements productsMvp.interfaces.presenter{
    private productsMvp.interfaces.Model model;
    private productsMvp.interfaces.View views;
    Context context;
    RecyclerView recyclerView;
    String user_id,seller_id;



    public deleteProductsPresenter(productsMvp.interfaces.View view, Context context, RecyclerView recyclerView, String user_id, String seller_id) {
        this.views = view;
        this.context=context;
        this.recyclerView=recyclerView;
        this.user_id=user_id;
        this.seller_id=seller_id;
        initPresenter();
    }
    private void initPresenter() {
        model = new deleteProductsModel();

    }
    @Override
    public void getData() {
        model.getdata(recyclerView,context,user_id,seller_id);
    }
}
