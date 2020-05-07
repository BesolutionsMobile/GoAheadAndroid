package com.example.lenovo.goahead.view.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.view.customAdapter.otherProductsAdapter;
import com.example.lenovo.goahead.view.interfaces.sellerDetailsMvp;
import com.example.lenovo.goahead.view.library.adapter;
import com.example.lenovo.goahead.view.list.otherProductList;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class sellerDetailsmodel implements sellerDetailsMvp.interfaces.Model {
    @Override
    public String getdata(final String user_id, final TextView name, final TextView email, final TextView phone, final CircleImageView imageView, final Context context, final RecyclerView recyclerView, final ViewSwitcher viewSwitcher) {
        String sellerUrl="http://coderg.org/goahead_en/Product/view_seller/82984218/951735/";
        final ArrayList<otherProductList>arrayList=new ArrayList<otherProductList>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, sellerUrl+user_id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        viewSwitcher.showNext();
                        JSONArray jsonArray=response.getJSONArray("products");
                        name.setText(response.getString("name"));
                        email.setText(response.getString("mail"));
                        phone.setText(response.getString("phone"));
                        Picasso.with(context).load(response.getString("photo")).into(imageView);
                        for (int index=0;index<jsonArray.length();index++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(index);
                            arrayList.add(new otherProductList(jsonObject.getString("id"),jsonObject.getString("image"),jsonObject.getString("name"),jsonObject.getString("description")));

                        }
                        adapter adapter=new adapter();
                        adapter.Horozintal(recyclerView,new otherProductsAdapter(context,arrayList),context);
                    }
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return null;
    }
}
