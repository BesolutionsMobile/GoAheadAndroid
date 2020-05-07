package com.example.lenovo.goahead.view.Model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.interfaces.interfaceMVP;
import com.example.lenovo.goahead.view.interfaces.interfaceMVPP;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.list.categoriesList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class newsModel implements interfaceMVPP.interfaces.Model{
    final static String newsUrl="http://webdesign.be4em.info/goahead_en/Category/view/82984218/951735/1";

    @Override
    public ArrayList getdata(final Context context) {
        final  ArrayList<catList>categoriesList=new ArrayList<catList>();
        final ArrayList[] finalArrayList = {null};
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, newsUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject = null;
                try {

                    if (response.getString("status").equals("1")) {
                        JSONArray categories = response.getJSONArray("offers");
                        for (int index = 0; index < categories.length(); index++) {
                            JSONObject categoriesObj = categories.getJSONObject(index);
                            String categoriesImg = categoriesObj.getString("image");
                            categoriesList.add(new catList(categoriesImg,categoriesObj.getString("link"),categoriesObj.getString("name"),categoriesObj.getInt("id"),categoriesObj.getInt("favorite")));

                        }
                        Toast.makeText(context, ""+categoriesList, Toast.LENGTH_SHORT).show();

                    } else if (response.getString("status").equals("2"))
                    {
                        Toast.makeText(context, ""+"dd", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, ""+"hh", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error Response", Toast.LENGTH_SHORT).show();
            }
        });
                RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return categoriesList;
        }
}
