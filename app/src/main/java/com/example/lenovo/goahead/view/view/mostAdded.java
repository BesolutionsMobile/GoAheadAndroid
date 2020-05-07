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
import com.example.lenovo.goahead.view.customAdapter.catAdapter;
import com.example.lenovo.goahead.view.customAdapter.navigationAdapter;
import com.example.lenovo.goahead.view.interfaces.userData;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.presenter.userPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mostAdded extends AppCompatActivity implements userData.interfaces.View {
 RecyclerView mostAdded;
 RecyclerView.Adapter mostAddedAdapter;
 RecyclerView.LayoutManager layoutManager;
    DrawerLayout drawerLayout;
    savedId savedId;
    ImageView menu;
 public static final String mostAddedUrl="http://coderg.org/goahead_en/Category/show_most_favorites/82984218/951735/";
    RecyclerView categorieslist,navLists;
    RecyclerView.Adapter navCategoriesAdapter;
    com.example.lenovo.goahead.view.library.progressdialog progressdialog;
    RecyclerView.LayoutManager navLayoutManager;
    final static  String  categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_added);

        //change bar name
        TextView category=(TextView)findViewById(R.id.category);
        category.setText("Most Added");

        mostAdded=(RecyclerView)findViewById(R.id.mostAddedList);
        savedId=new savedId();
        getAllData();
        GetAllDatas();
        LOGOUT();
        menu();
        addNewProduct();
        goToActivity();
        getDetails();
        onClickNav();
    }

    public void getAllData()
    {
       final ArrayList<catList>arrayList=new ArrayList<catList>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, mostAddedUrl+savedId.getId(mostAdded.this), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("status").equals("1"))
                    {
                        JSONArray jsonArray=response.getJSONArray("offers");
                        for(int index=0;index<jsonArray.length();index++)
                        {
                            JSONObject jsonObject=jsonArray.getJSONObject(index);
                            String mostAddedImage=jsonObject.getString("image");
                            arrayList.add(new catList(mostAddedImage,jsonObject.getString("link"),jsonObject.getString("name"),jsonObject.getInt("id"),jsonObject.getInt("favorite")));
                        }
                        GridLayoutManager layoutManager = new GridLayoutManager(mostAdded.this, 2);
                        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                // 5 is the sum of items in one repeated section
                                switch (position % 5) {
                                    // first two items span 3 columns each
                                    case 0:
                                        return 2;

                                    case 1:
                                        // next 3 items span 2 columns each
                                    case 2:
                                        return 1;

                                    case 3:
                                    case 4:
                                        return 1;

                                }
                               throw new IllegalStateException("internal error");
                            }
                        });                        mostAdded.setLayoutManager(layoutManager);
                        mostAddedAdapter=new catAdapter(500,mostAdded.this,arrayList);
                        mostAdded.setAdapter(mostAddedAdapter);
                    }
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(mostAdded.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(mostAdded.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mostAdded.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(mostAdded.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void GetAllDatas()
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
                    Toast.makeText(mostAdded.this, "catch", Toast.LENGTH_SHORT).show();                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mostAdded.this, "error", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(mostAdded.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void getNavigationData(ArrayList data)
    {
        navLists=(RecyclerView)findViewById(R.id.navlist);
        navLayoutManager=new LinearLayoutManager(mostAdded.this);
        navLists.setLayoutManager(navLayoutManager);
        navCategoriesAdapter=new navigationAdapter(mostAdded.this,data);
        navLists.setAdapter(navCategoriesAdapter);

    }

    //clik button to open navigation
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

    //logout from application
    public void LOGOUT()
    {
       final progressdialog progressdialog=new progressdialog();
        LinearLayout Logout=(LinearLayout)findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBoolean();
                progressdialog.progressDialog(mostAdded.this);
                startActivity(new Intent(mostAdded.this,login.class));
            }
        });
    }

    //check if is login or not using sharedprefrences
    public void sendBoolean()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("id",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("islogin",false);
        editor.commit();
    }

    //لgo to add product
    public void addNewProduct()
    {
        LinearLayout addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mostAdded.this,addNewProduct.class));
            }
        });
    }

//navigation bottom drawer
    public void goToActivity()
    {
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.favourites)
                {
                    startActivity(new Intent(mostAdded.this,favourite.class));
                }else if(menuItem.getItemId()==R.id.mistadded)
                {
                    startActivity(new Intent(mostAdded.this,mostAdded.class));

                }else if(menuItem.getItemId()==R.id.Familiesproduced)
                {
                    startActivity(new Intent(mostAdded.this,familiesProduces.class));
                }
                else if(menuItem.getItemId()==R.id.offers)
                {
                    startActivity(new Intent(mostAdded.this,offers.class));

                }
                return true;
            }
        });

    }

    //get userDetails
    private void getDetails()
    {
        TextView name=(TextView)findViewById(R.id.userName);
        ImageView profile=(ImageView)findViewById(R.id.userprofile);
        userPresenter userPresenter=new userPresenter(mostAdded.this,mostAdded.this,name,profile);
        userPresenter.getData();
    }

    //on Click Navigation
    private void onClickNav()
    {
        LinearLayout  addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mostAdded.this,addNewProduct.class));
            }
        });

        LinearLayout myProducts=(LinearLayout)findViewById(R.id.myproducts);
        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mostAdded.this,myProducts.class));

            }
        });
    }
}

