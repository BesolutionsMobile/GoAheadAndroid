package com.example.lenovo.goahead.view.customAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.list.otherProductList;
import com.example.lenovo.goahead.view.view.productDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class otherProductsAdapter extends RecyclerView.Adapter<otherProductsAdapter.otherProductHolder> {
    Context context;
    ArrayList<otherProductList>mylist;

    public otherProductsAdapter(Context context, ArrayList<otherProductList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public otherProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view=LayoutInflater.from(context).inflate(R.layout.otherproductsitem,viewGroup,false);
            otherProductHolder otherProductHolder=new otherProductHolder(view);
            return otherProductHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final otherProductHolder viewHolder, final int i) {
       viewHolder.Title.setText(mylist.get(i).getTitle().toString());
       viewHolder.Descripition.setText(mylist.get(i).getDescripition().toString());
        Picasso.with(context).load(mylist.get(i).getImg()).into(viewHolder.imgProduct);
       viewHolder.otherproduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context,productDetails.class);
               intent.putExtra("id",mylist.get(i).getId().toString());
               v.getContext().startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public  class otherProductHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView Title,Descripition;
        LinearLayout otherproduct;
        public otherProductHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=(ImageView)itemView.findViewById(R.id.otherProductImg);
            Title=(TextView)itemView.findViewById(R.id.otherproducttitle);
            Descripition=(TextView)itemView.findViewById(R.id.otherproductdescripition);
            otherproduct=(LinearLayout)itemView.findViewById(R.id.otherproduct);
        }
    }
}
