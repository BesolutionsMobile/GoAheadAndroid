package com.example.lenovo.goahead.view.customAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.list.productList;
import com.example.lenovo.goahead.view.view.productDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class productsAdapter extends RecyclerView.Adapter<productsAdapter.productHolder> {
    Context context;
    ArrayList<productList>mylist;

    public productsAdapter(Context context, ArrayList<productList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.productlist_item,viewGroup,false);
        productHolder productHolder=new productHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull productHolder viewHolder, final int i) {
       viewHolder.Title.setText(mylist.get(i).getTitle().toString());
       viewHolder.Descripition.setText(mylist.get(i).getDescripition().toString());
       viewHolder.Price.setText(mylist.get(i).getPrice().toString());
       Picasso.with(context).load(mylist.get(i).getImage()).into(viewHolder.productImg);
       viewHolder.viewProductsLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context,productDetails.class);
               intent.putExtra("id",mylist.get(i).getId().toString());
               v.getContext().startActivity(intent);
           }
       });

       viewHolder.viewProduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context,productDetails.class);
               intent.putExtra("id",mylist.get(i).getId().toString());
               intent.putExtra("name",mylist.get(i).getTitle().toString());
               v.getContext().startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
        }
    public class productHolder extends RecyclerView.ViewHolder {
        ImageView productImg;
        TextView Title,Descripition,Price;
        Button viewProduct;
        LinearLayout viewProductsLayout;
        public productHolder(@NonNull View itemView) {
            super(itemView);
            productImg=(ImageView)itemView.findViewById(R.id.productImg);
            Title=(TextView)itemView.findViewById(R.id.productTitle);
            Descripition=(TextView)itemView.findViewById(R.id.productDescripition);
            Price=(TextView)itemView.findViewById(R.id.productPrice);
            viewProduct=(Button)itemView.findViewById(R.id.viewproduct);
            viewProductsLayout=(LinearLayout)itemView.findViewById(R.id.viewproductLayout);

        }
    }
}
