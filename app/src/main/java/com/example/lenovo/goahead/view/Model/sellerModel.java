package com.example.lenovo.goahead.view.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.view.customAdapter.sellerAdapter;
import com.example.lenovo.goahead.view.interfaces.sellerMvp;
import com.example.lenovo.goahead.view.library.adapter;
import com.example.lenovo.goahead.view.list.sellerList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class sellerModel implements sellerMvp.interfaces.Model {
    @Override
    public ArrayList getdata(final RecyclerView recyclerView, final Context context, String category_id) {
        final ArrayList<sellerList>arrayList=new ArrayList<sellerList>();
        String sellerurl="http://coderg.org/goahead_en/Product_category/getAllSellersByCategoryId/82984218/951735/"+category_id;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, sellerurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        JSONArray jsonArray=response.getJSONArray("sellers");
                        for (int index=0;index<jsonArray.length();index++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(index);
                            arrayList.add(new sellerList(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("image"),jsonObject.getString("id_category")));
                            }
                        adapter adapter=new adapter();
                        adapter.griddAdapters(recyclerView,new sellerAdapter(context,arrayList),context,2);

                    }
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(context, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(context, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("sellercatch",""+e.getLocalizedMessage());
                }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("sellererrorlistner",""+error.getLocalizedMessage());
                }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return null;
    }
}
