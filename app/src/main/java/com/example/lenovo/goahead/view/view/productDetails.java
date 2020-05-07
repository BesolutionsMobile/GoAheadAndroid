package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.lenovo.goahead.view.customAdapter.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.interfaces.productDetailsMvps;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.presenter.productDetailsPresenter;

import me.relex.circleindicator.CircleIndicator;

public class productDetails extends AppCompatActivity implements productDetailsMvps.interfaces.View{

    ViewPager viewPager;
    viewimage viewimage;
   static String product_id;
    CircleIndicator circleIndicator;
    Button goToSeller;
    ViewSwitcher viewswitch;
    int count = -2;
    Intent intent;
    ImageView menu;
    productDetailsPresenter productDetailsPresenter;
    TextView name,price,descripition,Title;
    progressdialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        progressdialog=new progressdialog();
        progressdialog.dialog(productDetails.this, R.layout.loading,0.30);
        onClick();
        intent=getIntent();
         product_id=intent.getStringExtra("id");
        name=(TextView)findViewById(R.id.productname) ;
        price=(TextView)findViewById(R.id.productPrice);
        descripition=(TextView)findViewById(R.id.productDescripition);
        Title=(TextView)findViewById(R.id.Title);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        viewswitch=(ViewSwitcher)findViewById(R.id.viewswitch);
        ImageView rotate=(ImageView)findViewById(R.id.loading);
        AnimationDrawable  animationDrawable=(AnimationDrawable)rotate.getDrawable();
        animationDrawable.start();
        goToSeller=(Button)findViewById(R.id.goToSeller);
        productDetailsPresenter=new productDetailsPresenter(this,this,product_id,name,price,descripition,viewPager,circleIndicator,viewswitch,Title,goToSeller);
        productDetailsPresenter.getData();

    }

    public void onClick()
    {


        menu=(ImageView)findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
