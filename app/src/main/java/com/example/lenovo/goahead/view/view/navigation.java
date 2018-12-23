package com.example.lenovo.goahead.view.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.navigationAdapter;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.presenter.categoriesPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class navigation extends AppCompatActivity  {
    RecyclerView navLists;
    RecyclerView.Adapter navCategoriesAdapter;
    RecyclerView.LayoutManager navLayoutManager;
    final static  String  categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        getNavData();
        }

   public void getNavData()
   {
       final ArrayList<navList>arrayList=new ArrayList<navList>();
       JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, categoriesUrl, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               JSONArray jsonArray= null;
               try {
                   if(response.getString("status").equals("1")){
                   jsonArray = response.getJSONArray("categories");
                   for (int index=0;index<jsonArray.length();index++)
                   {
                       JSONObject jsonObject=jsonArray.getJSONObject(index);
                       String categoriesImg = jsonObject.getString("image");
                       arrayList.add(new navList(jsonObject.getInt("id"),jsonObject.getString("name"),categoriesImg));
                       }
                       getNavigationData(arrayList);
                   }
                   else  if(response.getString("status").equals("2"))
                   {
                       Toast.makeText(navigation.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                   }
                   else  if(response.getString("status").equals("3"))
                   {
                       Toast.makeText(navigation.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                   }
               } catch (JSONException e) {
                   Toast.makeText(navigation.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();               }
                   }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(navigation.this, "No Connection", Toast.LENGTH_SHORT).show();
           }
       });
       RequestQueue requestQueue = Volley.newRequestQueue(navigation.this);
       requestQueue.add(jsonObjectRequest);
   }


    public void getNavigationData(ArrayList data)
    {
        navLists=(RecyclerView)findViewById(R.id.navlist);
        navLayoutManager=new LinearLayoutManager(navigation.this);
        navLists.setLayoutManager(navLayoutManager);
        navCategoriesAdapter=new navigationAdapter(navigation.this,data);
        navLists.setAdapter(navCategoriesAdapter);

    }
}
