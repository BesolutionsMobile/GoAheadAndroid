package com.example.lenovo.goahead.view.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.lenovo.goahead.view.Model.addProductModel;
import com.example.lenovo.goahead.view.Model.sellerDetailsmodel;
import com.example.lenovo.goahead.view.interfaces.addProduct;
import com.example.lenovo.goahead.view.interfaces.sellerDetailsMvp;

import de.hdodenhof.circleimageview.CircleImageView;

public class sellerDetailsPresenter implements sellerDetailsMvp.interfaces.presenter {
    private sellerDetailsMvp.interfaces.Model model;
    private sellerDetailsMvp.interfaces.View views;
    Context context;
    String user_id,category_id,title,descripition,price;
    String  productId;
    TextView name,email,phone;
    CircleImageView imageView;
    RecyclerView recyclerView;
    ViewSwitcher viewSwitcher;


    public sellerDetailsPresenter(sellerDetailsMvp.interfaces.View view, Context context,String productId,TextView name,TextView email,TextView phone,CircleImageView imageView,RecyclerView recyclerView,ViewSwitcher viewSwitcher) {
        this.views = view;
        this.context=context;
        this.name=name;
        this.email=email;
        this.imageView=imageView;
        this.phone=phone;
        this.productId=productId;
        this.recyclerView=recyclerView;
        this.viewSwitcher=viewSwitcher;
        initPresenter();
    }
    private void initPresenter() {
        model = new sellerDetailsmodel();

    }
    @Override
    public void getData() {
        model.getdata(productId,name,email,phone,imageView,context,recyclerView,viewSwitcher);
    }
}
