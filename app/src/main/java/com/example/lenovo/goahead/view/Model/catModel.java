package com.example.lenovo.goahead.view.Model;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.interfaces.interfaceMVP;
import com.example.lenovo.goahead.view.interfaces.productDetailsMvps;
import com.example.lenovo.goahead.view.library.adapter;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.list.productList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class catModel implements productDetailsMvps.interfaces.Model{
    adapter adapter;
    @Override
    public String getdata(String user_id, TextView name, TextView price, TextView descripition, Context context, CircleIndicator circleIndicator, ViewPager viewPager, ViewSwitcher viewSwitcher, TextView textView, Button goToSeller) {
        final ArrayList<productList>arrayList=new ArrayList<productList>();
        String productUrl="http://coderg.org//goahead_en/Product_category/view/82984218/951735/";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, productUrl+user_id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        JSONArray jsonArray=response.getJSONArray("offers");
                        for (int index=0;index<jsonArray.length();index++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(index);
                            arrayList.add(new productList(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("description"),jsonObject.getString("image"),jsonObject.getString("price")));
                        }
                        adapter=new adapter();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return null;    }
}
