package com.example.lenovo.goahead.view.presenter;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import com.example.lenovo.goahead.view.Model.addProductModel;
import com.example.lenovo.goahead.view.interfaces.addProduct;

import java.util.ArrayList;

public class addProductPresenter implements addProduct.interfaces.presenter {

    private addProduct.interfaces.Model model;
    private addProduct.interfaces.View views;
    Context context;
    String user_id,category_id,title,descripition,price;
    String  imageView;
    Dialog dialog;
    ArrayList<String>arrayList;
    public addProductPresenter(addProduct.interfaces.View view, Context context, String user_id, String category_id, String title, String descripition, String price, String imageView, Dialog dialog,ArrayList<String> arrayList) {
        this.views = view;
        this.context=context;
        this.dialog=dialog;
        this.user_id=user_id;
        this.category_id=category_id;
        this.title=title;
        this.descripition=descripition;
        this.price=price;
        this.imageView=imageView;
        this.arrayList=arrayList;
        initPresenter();
    }
    private void initPresenter() {
        model = new addProductModel();

    }
    @Override
    public void getData() {
      model.getdata(user_id,category_id,title,descripition,price,context,imageView,dialog,arrayList);
    }
}
