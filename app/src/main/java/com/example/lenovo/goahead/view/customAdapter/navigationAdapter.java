package com.example.lenovo.goahead.view.customAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.list.navList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class navigationAdapter extends RecyclerView.Adapter<navigationAdapter.navigationHolder>{

        Context context;
        ArrayList<navList>mylist;

    public navigationAdapter(Context context, ArrayList<navList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public navigationHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View  view=LayoutInflater.from(context).inflate(R.layout.navigationitem,viewGroup,false);
        navigationHolder navigationHolder=new navigationHolder(view);
        return navigationHolder;
    }

    @Override
    public void onBindViewHolder(navigationHolder viewHolder, int i) {
              viewHolder.title.setText(mylist.get(i).getTitle().toString());
              Picasso.with(context).load(mylist.get(i).getIcon());
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class navigationHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        public navigationHolder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            icon=(ImageView)itemView.findViewById(R.id.img);
        }
    }
}
