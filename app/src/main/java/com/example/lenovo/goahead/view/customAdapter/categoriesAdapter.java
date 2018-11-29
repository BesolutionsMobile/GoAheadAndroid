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
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.view.cat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class categoriesAdapter extends RecyclerView.Adapter<categoriesAdapter.categoriesHolder> {
    Context context;
    ArrayList<categoriesList>mylist;

    public categoriesAdapter(Context context, ArrayList<categoriesList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public categoriesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.categoriesitem,viewGroup,false);
        categoriesHolder categoriesHolder=new categoriesHolder(view);
        return categoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull categoriesHolder viewHolder, int i) {
        Picasso.with(context).load(mylist.get(i).getImage()).into(viewHolder.background);
        viewHolder.title.setText(mylist.get(i).getName().toString());
        final int id=mylist.get(i).getId();
        viewHolder.categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass=new Intent(context,cat.class);
                pass.putExtra("id",id);
                v.getContext().startActivity(pass);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class categoriesHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView background;
        LinearLayout categories;
        public categoriesHolder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.txtback);
            background=(ImageView)itemView.findViewById(R.id.categoriesback);
            categories=(LinearLayout)itemView.findViewById(R.id.categoriesItem);
        }
    }
}
