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
import com.example.lenovo.goahead.view.customAdapter.deleteProductAdapter;
import com.example.lenovo.goahead.view.customAdapter.productsAdapter;
import com.example.lenovo.goahead.view.interfaces.productsMvp;
import com.example.lenovo.goahead.view.library.adapter;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.productList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class deleteProductsModel implements productsMvp.interfaces.Model{
    adapter adapter;
 @Override
    public ArrayList getdata(final RecyclerView recyclerView, final Context context,String user_id,String seller_id) {
     savedId savedId=new savedId();

        final ArrayList<productList>arrayList=new ArrayList<productList>();
        String productUrl="http://coderg.org/goahead_en/Product/view_seller/82984218/951735/"+savedId.getId(context);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, productUrl,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        JSONArray jsonArray=response.getJSONArray("products");
                        for (int index=0;index<jsonArray.length();index++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(index);
                            arrayList.add(new productList(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("description"),jsonObject.getString("image"),jsonObject.getString("price")));
                        }
                        adapter=new adapter();
                        adapter.Adapter(recyclerView,new deleteProductAdapter(context,arrayList),context);
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
                    Log.e("myproducts",e.getLocalizedMessage());
                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
     RequestQueue requestQueue= Volley.newRequestQueue(context);
     requestQueue.add(jsonObjectRequest);
        return null;
    }
}
