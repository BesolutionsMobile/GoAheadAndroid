package com.example.lenovo.goahead.view.Model;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.view.interfaces.userData;
import com.example.lenovo.goahead.view.library.savedId;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class userDataModel implements userData.interfaces.Model{
    @Override
    public String getdata(final TextView Name, final ImageView profile, final Context context) {
        savedId savedId=new savedId();
        String userDetails="http://coderg.org/goahead_en/user/userProfile/82984218/951735/"+savedId.getId(context);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, userDetails, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                    JSONObject jsonObject=response.getJSONObject("UserProfileData");
                    Name.setText(jsonObject.getString("name"));
                    Picasso.with(context).load(jsonObject.getString("image")).into(profile);
                }
                else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(context, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(context, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                    Log.e("catch",""+e.getLocalizedMessage());
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);
        return null;
    }
}
