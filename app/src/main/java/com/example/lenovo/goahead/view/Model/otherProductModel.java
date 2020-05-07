package com.example.lenovo.goahead.view.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.lenovo.goahead.view.customAdapter.otherProductsAdapter;
import com.example.lenovo.goahead.view.interfaces.otherProductMvp;
import com.example.lenovo.goahead.view.library.adapter;
import com.example.lenovo.goahead.view.list.otherProductList;
import com.example.lenovo.goahead.view.list.productList;

import java.util.ArrayList;

public class otherProductModel implements otherProductMvp.interfaces.Model {
    @Override
    public ArrayList getdata(RecyclerView recyclerView, Context context) {
        ArrayList<otherProductList>arrayList=new ArrayList<otherProductList>();
        arrayList.add(new otherProductList("1","https://cnet1.cbsistatic.com/img/z70G7HNyTRJ5TAm4vLBkqrySIgs=/936x527/2018/04/26/da817b98-516f-4213-9fa8-959378b900e4/pubgmobilejp.jpg","Drones","The Mavic Air is the most portable DJI drone to house a 3-axis mechanical gimbal, with its angular"));
        arrayList.add(new otherProductList("1","https://cnet1.cbsistatic.com/img/z70G7HNyTRJ5TAm4vLBkqrySIgs=/936x527/2018/04/26/da817b98-516f-4213-9fa8-959378b900e4/pubgmobilejp.jpg","Drones","The Mavic Air is the most portable DJI drone to house a 3-axis mechanical gimbal, with its angular"));
        arrayList.add(new otherProductList("1","https://cnet1.cbsistatic.com/img/z70G7HNyTRJ5TAm4vLBkqrySIgs=/936x527/2018/04/26/da817b98-516f-4213-9fa8-959378b900e4/pubgmobilejp.jpg","Drones","The Mavic Air is the most portable DJI drone to house a 3-axis mechanical gimbal, with its angular"));
        adapter adapter=new adapter();
        adapter.Horozintal(recyclerView,new otherProductsAdapter(context,arrayList),context);
        return null;
    }
}
