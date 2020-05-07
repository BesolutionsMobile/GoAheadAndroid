package com.example.lenovo.goahead.view.customAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.list.imageList;

import java.util.ArrayList;

public class multiImageAdapter extends RecyclerView.Adapter<multiImageAdapter.ImageHolder> {
    Context context;
    ArrayList<imageList>mylist;

    public multiImageAdapter(Context context, ArrayList<imageList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.slider_multi,viewGroup,false);
        ImageHolder imageHolder=new ImageHolder(view);
        return imageHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder viewHolder, int i) {
      viewHolder.slider.setImageURI(mylist.get(i).getImage());

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        ImageView slider;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            slider=(ImageView)itemView.findViewById(R.id.slider);
            }
    }
}
