package com.example.lenovo.goahead.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.catAdapter;
import com.example.lenovo.goahead.view.interfaces.interfaceMVP;
import com.example.lenovo.goahead.view.presenter.shoppingPersenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class shopping extends Fragment implements interfaceMVP.interfaces.View  {
    View v;
    RecyclerView catList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    shoppingPersenter spotrsPersenter;
    RecyclerView shopping;

    public shopping() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_shopping, container, false);
        spotrsPersenter=new shoppingPersenter(this);
        spotrsPersenter.getData();
        return v;
    }

    @Override
    public void element() {
        shopping=(RecyclerView)v.findViewById(R.id.shopping);
    }

    @Override
    public void setviewdata(ArrayList data) {
        layoutManager=new GridLayoutManager(getActivity(),2);
        shopping.setLayoutManager(layoutManager);
        adapter=new catAdapter(getActivity(),data);
        shopping.setAdapter(adapter);
    }
}
