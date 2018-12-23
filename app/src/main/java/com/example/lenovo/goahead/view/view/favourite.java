package com.example.lenovo.goahead.view.view;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.catAdapter;
import com.example.lenovo.goahead.view.customAdapter.categoriesAdapter;
import com.example.lenovo.goahead.view.customAdapter.navigationAdapter;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.navList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class favourite extends AppCompatActivity {
   RecyclerView favouriteList;
   RecyclerView.Adapter favAdapter;
    DrawerLayout drawerLayout;
    ImageView menu;
   RecyclerView.LayoutManager layoutManager;
    savedId savedId;
    RecyclerView categorieslist,navLists;
    RecyclerView.Adapter categoriesAdapter,navCategoriesAdapter;
    RecyclerView.LayoutManager catLayoutManager,navLayoutManager;
   public static final String favUrl="http://coderg.org/goahead_en/User/getFavoriteOffers/82984218/951735/";
    final static  String  categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        favouriteList=(RecyclerView)findViewById(R.id.favouriteList);
        savedId=new savedId();
        getFav();
        menu();
        GetAllData();
        }

    public void getFav()
    {
        final ArrayList<catList>arrayList=new ArrayList<catList>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, favUrl + savedId.getId(this), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("1")){
                    JSONArray favArrary=response.getJSONArray("offers");
                    for(int index=0;index<favArrary.length();index++)
                    {
                        JSONObject favObject=favArrary.getJSONObject(index);
                        String favImg=favObject.getString("image");
                        arrayList.add(new catList(favImg,favObject.getString("link"),favObject.getString("name"),favObject.getInt("id"),favObject.getInt("favorite")));
                    }
                    layoutManager=new GridLayoutManager(favourite.this,2);
                    favouriteList.setLayoutManager(layoutManager);
                    favAdapter=new catAdapter(favourite.this,arrayList);
                    favouriteList.setAdapter(favAdapter);
                    }
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(favourite.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(favourite.this, "someThing wrong", Toast.LENGTH_SHORT).show();                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(favourite.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(favourite.this);
        requestQueue.add(jsonObjectRequest);
    }


    public void GetAllData()
    {
        categorieslist=(RecyclerView)findViewById(R.id.categorieslist);
        final ArrayList<categoriesList>arrayList=new ArrayList<categoriesList>();
        final ArrayList<navList>arrayListNav=new ArrayList<navList>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, categoriesUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1"))
                    {
                        JSONArray jsonArray=response.getJSONArray("categories");
                        for (int index=0;index<jsonArray.length();index++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(index);
                            String categoriesImg = jsonObject.getString("image");
                            arrayListNav.add(new navList( jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("icon")));
                        }

                        getNavigationData(arrayListNav);
                    }

                } catch (JSONException e) {
                    Toast.makeText(favourite.this, "catch", Toast.LENGTH_SHORT).show();                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(favourite.this, "error", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(favourite.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void getNavigationData(ArrayList data)
    {
        navLists=(RecyclerView)findViewById(R.id.navlist);
        navLayoutManager=new LinearLayoutManager(favourite.this);
        navLists.setLayoutManager(navLayoutManager);
        navCategoriesAdapter=new navigationAdapter(favourite.this,data);
        navLists.setAdapter(navCategoriesAdapter);

    }

    public void menu()
    {
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        menu=(ImageView)findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);

                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
    }
}
