package com.example.lenovo.goahead.view.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.goahead.view.Model.otherProductModel;
import com.example.lenovo.goahead.view.Model.userDataModel;
import com.example.lenovo.goahead.view.interfaces.otherProductMvp;
import com.example.lenovo.goahead.view.interfaces.userData;

public class userPresenter implements userData.interfaces.presenter {
    private userData.interfaces.Model model;
    private userData.interfaces.View views;
    Context context;
    TextView name;
    ImageView profile;

    public userPresenter(userData.interfaces.View view, Context context, TextView Name,ImageView profile) {
        this.views = view;
        this.context=context;
        this.name=Name;
        this.profile=profile;
        initPresenter();
    }
    private void initPresenter() {
        model = new userDataModel();

    }
    @Override
    public void getData() {
        model.getdata(name,profile,context);
    }
}
