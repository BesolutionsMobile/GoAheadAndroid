package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.interfaces.otherProductMvp;
import com.example.lenovo.goahead.view.interfaces.sellerDetailsMvp;
import com.example.lenovo.goahead.view.presenter.otherProductPresenter;
import com.example.lenovo.goahead.view.presenter.sellerDetailsPresenter;

import de.hdodenhof.circleimageview.CircleImageView;


public class userProfileEnglish extends AppCompatActivity implements sellerDetailsMvp.interfaces.View {
    sellerDetailsPresenter sellerDetailsPresenter;
    RecyclerView otherProductList;
    CircleImageView profileImg;
    ImageView menu;
    ViewSwitcher viewswitch;
 TextView sellerName,sellerPhone,sellerMail;
 Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        String id=intent.getStringExtra("id");

        setContentView(R.layout.activity_user_profile_english);
        //set Text For Bar
        TextView titleBar=(TextView)findViewById(R.id.pd);
        titleBar.setText("About Seller");
         profileImg=(CircleImageView)findViewById(R.id.image);
         sellerName=(TextView)findViewById(R.id.sellername);
         sellerPhone=(TextView)findViewById(R.id.sellerphone);
         sellerMail=(TextView)findViewById(R.id.sellermail);
         otherProductList=(RecyclerView)findViewById(R.id.otherProductList);
        viewswitch=(ViewSwitcher)findViewById(R.id.viewswitch);
        ImageView rotate=(ImageView)findViewById(R.id.loading);
        AnimationDrawable animationDrawable=(AnimationDrawable)rotate.getDrawable();
        animationDrawable.start();
        sellerDetailsPresenter=new sellerDetailsPresenter(this,this,id,sellerName,sellerMail,sellerPhone,profileImg,otherProductList,viewswitch);
        sellerDetailsPresenter.getData();
        onClick();
        }

        //click  to finish
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
