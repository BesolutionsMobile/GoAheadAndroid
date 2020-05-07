package com.example.lenovo.goahead.view.library;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.goahead.view.customAdapter.categoriesAdapter;
import com.example.lenovo.goahead.view.view.categories;

public class adapter {

        public void griddAdapters(RecyclerView recyclerView, RecyclerView.Adapter dd, Context context,int num)
    {
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(context,num);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter= dd;
        recyclerView.setAdapter(adapter);
    }
    public void Adapter(RecyclerView recyclerView, RecyclerView.Adapter dd, Context context)
    {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter= dd;
        recyclerView.setAdapter(adapter);
    }
    public void Horozintal(RecyclerView recyclerView, RecyclerView.Adapter dd, Context context)
    {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter= dd;
        recyclerView.setAdapter(adapter);
    }
}
