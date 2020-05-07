package com.example.lenovo.goahead.view.Model;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.addNewProductAdapter;
import com.example.lenovo.goahead.view.customAdapter.categoriesAdapter;
import com.example.lenovo.goahead.view.customAdapter.familiesProdAdapter;
import com.example.lenovo.goahead.view.interfaces.familiesMvp;
import com.example.lenovo.goahead.view.library.adapter;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.view.addNewProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class familiesModel implements familiesMvp.interfaces.Model {
    adapter adapter;

    @Override
    public ArrayList getdata(final RecyclerView recyclerView, final Context context) {
        final ArrayList<categoriesList>arrayList=new ArrayList<categoriesList>();
         String  categoriesUrl="http://coderg.org/goahead_en/Product_category/getAll/82984218/951735";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, categoriesUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("categories");
                        for (int index = 0; index < jsonArray.length(); index++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(index);
                            String categoriesImg = jsonObject.getString("image");
                            arrayList.add(new categoriesList(jsonObject.getString("name"), categoriesImg, jsonObject.getInt("id")));
                        }
                        adapter=new adapter();
                        adapter.griddAdapters(recyclerView,new familiesProdAdapter(context,arrayList),context,2);
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

        return arrayList;
    }
}
