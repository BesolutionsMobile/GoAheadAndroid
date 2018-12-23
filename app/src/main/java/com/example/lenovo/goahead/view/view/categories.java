package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.lenovo.goahead.view.customAdapter.categoriesAdapter;
import com.example.lenovo.goahead.view.customAdapter.navigationAdapter;
import com.example.lenovo.goahead.view.interfaces.interfaceMVPP;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.presenter.categoriesPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class categories extends AppCompatActivity implements interfaceMVPP.interfaces.View{
LinearLayout goToLogin,resturants,news,Sports,GeneralService,shoppings;
    Intent intent;
    DrawerLayout drawerLayout;
    ImageView menu;
    savedId savedId;
    progressdialog progressdialog;
    RecyclerView categorieslist,navLists;
    RecyclerView.Adapter categoriesAdapter,navCategoriesAdapter;
    RecyclerView.LayoutManager catLayoutManager,navLayoutManager;
    categoriesPresenter categoriesPresenter;
    String id;
    final static  String  categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        intent=new Intent(categories.this,cat.class);
        progressdialog=new progressdialog();

        menu();
     //  categoriesPresenter=new categoriesPresenter(this,this);
      // categoriesPresenter.getData();
        GetAllData();
        onClick();
        goToActivity();
        LOGOUT();
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



    @Override
    public void element() {
        categorieslist=(RecyclerView)findViewById(R.id.categorieslist);
    }

    @Override
    public void setviewdata(ArrayList data) {
        catLayoutManager=new GridLayoutManager(categories.this,2);
        categorieslist.setLayoutManager(catLayoutManager);
        categoriesAdapter=new categoriesAdapter(this,data);
        categorieslist.setAdapter(categoriesAdapter);
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
                        arrayList.add(new categoriesList(jsonObject.getString("name"), categoriesImg, jsonObject.getInt("id")));
                        arrayListNav.add(new navList( jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("icon")));
                    }
                    catLayoutManager=new GridLayoutManager(categories.this,2);
                    categorieslist.setLayoutManager(catLayoutManager);
                    categoriesAdapter=new categoriesAdapter(categories.this,arrayList);
                    categorieslist.setAdapter(categoriesAdapter);
                    getNavigationData(arrayListNav);
                    }

                } catch (JSONException e) {
                    Toast.makeText(categories.this, "catch", Toast.LENGTH_SHORT).show();                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(categories.this, "error", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(categories.this);
        requestQueue.add(jsonObjectRequest);
    }
    public void onClick()
    {
        LinearLayout goToLogin=(LinearLayout)findViewById(R.id.goToLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categories.this,login.class));
            }
        });
    }

    public String getId()
    {
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        return id;
        }

        public void goToActivity()
        {
            bottomNavigationView=findViewById(R.id.nav);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                     if(menuItem.getItemId()==R.id.favourites)
                     {
                         startActivity(new Intent(categories.this,favourite.class));
                     }else if(menuItem.getItemId()==R.id.mistadded)
                     {
                         startActivity(new Intent(categories.this,mostAdded.class));

                     }
                    return true;
                }
            });

        }

        public void LOGOUT()
        {
            ViewSwitcher viewSwitcher=(ViewSwitcher)findViewById(R.id.viewswitch);
            savedId=new savedId();
            if(savedId.getUserBoolean(categories.this)==true)
            {
               viewSwitcher.showNext();
            }
            LinearLayout Logout=(LinearLayout)findViewById(R.id.logout);
            Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendBoolean();
                    progressdialog.progressDialog(categories.this);
                    startActivity(new Intent(categories.this,login.class));
                }
            });
        }
    public void sendBoolean()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("id",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("islogin",false);
        editor.commit();
    }

    public void getNavigationData(ArrayList data)
    {
        navLists=(RecyclerView)findViewById(R.id.navlist);
        navLayoutManager=new LinearLayoutManager(categories.this);
        navLists.setLayoutManager(navLayoutManager);
        navCategoriesAdapter=new navigationAdapter(categories.this,data);
        navLists.setAdapter(navCategoriesAdapter);

    }
}
