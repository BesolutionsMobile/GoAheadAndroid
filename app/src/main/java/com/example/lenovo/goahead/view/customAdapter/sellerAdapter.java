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
import com.example.lenovo.goahead.view.list.sellerList;
import com.example.lenovo.goahead.view.view.familiesProducts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class sellerAdapter extends RecyclerView.Adapter<sellerAdapter.sellerHolder> {
    Context context;
    ArrayList<sellerList>mylist;

    public sellerAdapter(Context context, ArrayList<sellerList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public sellerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.seller_item,viewGroup,false);
        sellerHolder sellerHolder=new sellerHolder(view);
        return sellerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull sellerHolder viewHolder, final int i) {
        Picasso.with(context).load(mylist.get(i).getImage()).into(viewHolder.circleImageView);
        viewHolder.name.setText(mylist.get(i).getName().toString());
        viewHolder.seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,familiesProducts.class);
                intent.putExtra("seller_id",mylist.get(i).getId().toString());
                intent.putExtra("category_id",mylist.get(i).getCategory_id().toString());
                intent.putExtra("name",mylist.get(i).getName().toString());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class sellerHolder extends RecyclerView.ViewHolder {
        ImageView circleImageView;
        TextView name;
        LinearLayout seller;
        public sellerHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=(ImageView)itemView.findViewById(R.id.sellerprofile);
            name=(TextView)itemView.findViewById(R.id.sellerName);
            seller=(LinearLayout)itemView.findViewById(R.id.seller);

        }
    }
}
