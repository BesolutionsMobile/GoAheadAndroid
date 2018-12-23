package com.example.lenovo.goahead.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.catAdapter;
import com.example.lenovo.goahead.view.interfaces.interfaceMVP;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.presenter.catPresenter;
import com.example.lenovo.goahead.view.view.cat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class resturants extends Fragment implements interfaceMVP.interfaces.View {
    View v;
    RecyclerView catList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    com.example.lenovo.goahead.view.presenter.catPresenter catPresenter;


    public resturants() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_resturants, container, false);
        //catPresenter=new catPresenter(this);
       // catPresenter.getData();
        getAllData();
        return v;
    }

    @Override
    public void element() {
        catList=(RecyclerView)v.findViewById(R.id.resturant);
    }

    @Override
    public void setviewdata(ArrayList data) {
        layoutManager=new GridLayoutManager(getActivity(),2);
        catList.setLayoutManager(layoutManager);
        adapter=new catAdapter(getActivity(),data);
        catList.setAdapter(adapter);
    }

    void getAllData()
    {
        catList=(RecyclerView)v.findViewById(R.id.resturant);

        final ArrayList<com.example.lenovo.goahead.view.list.catList>arrayList=new ArrayList<catList>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://webdesign.be4em.info/goahead_en/Category/view/82984218/951735/1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("offers");
                    for (int index=0;index<jsonArray.length();index++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(index);
                        String img=jsonObject.getString("image");
                        arrayList.add(new catList(img,jsonObject.getString("link"),jsonObject.getString("name"),jsonObject.getInt("id"),jsonObject.getInt("favorite")));
                    }
                    layoutManager=new GridLayoutManager(getActivity(),2);
                    catList.setLayoutManager(layoutManager);
                    adapter=new catAdapter(getActivity(),arrayList);
                    catList.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "ef", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "nooo", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);

    }
}
