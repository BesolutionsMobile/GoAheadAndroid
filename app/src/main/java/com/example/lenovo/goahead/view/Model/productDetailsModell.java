package com.example.lenovo.goahead.view.Model;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.interfaces.productDetailsMvps;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.view.productDetails;
import com.example.lenovo.goahead.view.view.userProfileEnglish;
import com.example.lenovo.goahead.view.view.viewimage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class productDetailsModell implements productDetailsMvps.interfaces.Model {
    @Override
    public String getdata(String user_id, final TextView name, final TextView price, final TextView descripition, final Context context, final CircleIndicator circleIndicator, final ViewPager viewPager, final ViewSwitcher viewSwitcher, final TextView Title, final Button goToSeller) {
        String  productDetail="http://coderg.org/goahead_en/Product/view/82984218/951735/";
        final progressdialog progressdialog=new progressdialog();
        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, productDetail+user_id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final JSONObject jsonObject=response.getJSONObject("product");
                    ArrayList<String>arrayList=new ArrayList<String>();
                    if(response.getString("status").equals("1"))
                    {
                        viewSwitcher.showNext();
                        progressdialog.dialogDismiss(context, R.layout.loading);
                    name.setText(jsonObject.getString("name"));
                    price.setText(jsonObject.getString("price"));
                    descripition.setText(jsonObject.getString("description"));
                    Title.setText(jsonObject.getString("name"));
                        goToSeller.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(context,userProfileEnglish.class);
                                try {
                                    intent.putExtra("id",jsonObject.getString("id_seller"));
                                    context.startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        JSONArray jsonArray=response.getJSONArray("productImages");
                        for(int index=0;index<jsonArray.length();index++)
                        {
                             arrayList.add(jsonArray.getString(index));
                        }
                        viewimage  viewimage = new viewimage(context, arrayList);
                        viewPager.setAdapter(viewimage);
                        circleIndicator.setViewPager(viewPager);

                    }

                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                    Toast.makeText(context, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        return null;
    }
}
