package com.example.lenovo.goahead.view.customAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.list.catList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class catAdapter extends RecyclerView.Adapter<catAdapter.catHolder> {

    Context context;
   ArrayList<catList>mylist;

    public catAdapter(Context context, ArrayList<catList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public catHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.catitem,viewGroup,false);
        catHolder catHolder=new catHolder(view);
        return catHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final catHolder viewHolder, final int i) {
        Picasso.with(context).load(mylist.get(i).getLogo()).into(viewHolder.Logo);
        final String url=mylist.get(i).getUrl();
        viewHolder.GoToWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            }
        });
        viewHolder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mylist.get(i).isFav()==false){
                    viewHolder.fav.setImageResource(R.drawable.ic_favorite_red_24dp);
                    Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
                    mylist.get(i).setFav(true);
                }else{
                    viewHolder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                    mylist.get(i).setFav(false);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
    public class catHolder extends RecyclerView.ViewHolder {
        ImageView Logo,fav;
        LinearLayout GoToWeb;
        public catHolder(@NonNull View itemView) {
            super(itemView);
            Logo=(ImageView)itemView.findViewById(R.id.logo);
            GoToWeb=(LinearLayout)itemView.findViewById(R.id.goToWeb);
            fav=(ImageView)itemView.findViewById(R.id.fav);
        }
    }
}
