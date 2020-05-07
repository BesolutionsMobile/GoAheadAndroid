package com.example.lenovo.goahead.view.presenter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.lenovo.goahead.view.Model.addProductModel;
import com.example.lenovo.goahead.view.Model.productDetailsModell;
import com.example.lenovo.goahead.view.interfaces.addProduct;
import com.example.lenovo.goahead.view.interfaces.productDetailsMvps;

import me.relex.circleindicator.CircleIndicator;

public class productDetailsPresenter implements productDetailsMvps.interfaces.presenter {
    private productDetailsMvps.interfaces.Model model;
    private productDetailsMvps.interfaces.View views;
    Context context;
    String user_id;
    ImageView imageView;
    TextView name,price,description,Title;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ViewSwitcher viewSwitcher;
    Button goToSeller;
    public productDetailsPresenter(productDetailsMvps.interfaces.View view, Context context, String user_id, TextView name,TextView price,TextView descripition,ViewPager viewPager, CircleIndicator circleIndicator,ViewSwitcher viewSwitcher,TextView Title, Button goToSeller) {
        this.views = view;
        this.context=context;
        this.user_id=user_id;
        this.name=name;
        this.price=price;
        this.description=descripition;
        this.viewPager=viewPager;
        this.circleIndicator=circleIndicator;
        this.viewSwitcher=viewSwitcher;
        this.Title=Title;
        this.goToSeller=goToSeller;
        initPresenter();
    }
    private void initPresenter() {
        model = new productDetailsModell();

    }
    @Override
    public void getData() {
        model.getdata(user_id,name,price,description,context,circleIndicator,viewPager,viewSwitcher,Title,goToSeller);
    }
}
