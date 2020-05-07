package com.example.lenovo.goahead.view.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.lenovo.goahead.view.interfaces.interfaceMVPP;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.presenter.newsPresenter;
import com.example.lenovo.goahead.view.view.cat;
import com.example.lenovo.goahead.view.view.favourite;
import com.example.lenovo.goahead.view.view.login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class news extends Fragment implements interfaceMVPP.interfaces.View {


    View v;
    RecyclerView newsList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    savedId savedId;
    Activity cat;
    String id;
    com.example.lenovo.goahead.view.presenter.newsPresenter newsPresenter;

    public news() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_news, container, false);
        //newsPresenter=new newsPresenter(this,getActivity());
        //newsPresenter.getData();
        savedId=new savedId();
        getIdd();
        return v;
    }

    @Override
    public void element() {
    }

    @Override
    public void setviewdata(ArrayList data) {

    }


    public void getIdd()
    {
        String position = getArguments().getString("position");
        final int pos=Integer.parseInt(position);
        String url="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        JSONArray jsonArray=response.getJSONArray("categories");
                    JSONObject jsonObject=jsonArray.getJSONObject(pos);
                    String id=jsonObject.getString("id");

                     getAllData(id);
                 }else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(getActivity(), ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(getActivity(), ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                }
                 catch (JSONException e) {
                     Toast.makeText(getActivity(), "something error", Toast.LENGTH_SHORT).show();                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.cat=activity;
    }

    void getAllData(String id)
    {
        newsList=(RecyclerView)v.findViewById(R.id.newss);
        savedId=new savedId();

        final ArrayList<catList>arrayList=new ArrayList<catList>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://coderg.org/goahead_en/Category/view/82984218/951735/"+id+"/"+savedId.getId(cat), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1")) {

                        JSONArray jsonArray = response.getJSONArray("offers");
                        for (int index = 0; index < jsonArray.length(); index++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(index);
                            String img = jsonObject.getString("image");
                            arrayList.add(new catList(img, jsonObject.getString("link"), jsonObject.getString("name"), jsonObject.getInt("id"),jsonObject.getInt("favorite")));
                            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(int position) {
                                    // 5 is the sum of items in one repeated section
                                    switch (position % 5) {
                                        // first two items span 3 columns each
                                        case 0:
                                            return 2;

                                        case 1:
                                            // next 3 items span 2 columns each
                                        case 2:
                                            return 1;

                                        case 3:
                                        case 4:
                                            return 1;

                                    }
                                    throw new IllegalStateException("internal error");
                                }
                            });
                            newsList.setLayoutManager(layoutManager);
                                adapter = new catAdapter(500,getActivity(), arrayList);
                                newsList.setAdapter(adapter);
                                }

                    }
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(cat, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(cat, ""+response.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    Toast.makeText(cat, "someThing went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cat, "No Internet ", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(cat);
        requestQueue.add(jsonObjectRequest);

    }
}
