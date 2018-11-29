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
import com.example.lenovo.goahead.view.interfaces.interfaceMVPP;
import com.example.lenovo.goahead.view.presenter.newsPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class news extends Fragment implements interfaceMVPP.interfaces.View {

    View v;
    RecyclerView newsList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    com.example.lenovo.goahead.view.presenter.newsPresenter newsPresenter;

    public news() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_news, container, false);
        newsPresenter=new newsPresenter(this,getActivity());
        newsPresenter.getData();
        return v;
    }

    @Override
    public void element() {
        newsList=(RecyclerView)v.findViewById(R.id.newss);
    }

    @Override
    public void setviewdata(ArrayList data) {
        layoutManager=new GridLayoutManager(getActivity(),2);
        newsList.setLayoutManager(layoutManager);
        adapter=new catAdapter(getActivity(),data);
        newsList.setAdapter(adapter);
    }
}
