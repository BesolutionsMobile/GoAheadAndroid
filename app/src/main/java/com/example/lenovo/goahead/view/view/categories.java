package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
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
import android.widget.RelativeLayout;
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
import com.example.lenovo.goahead.view.interfaces.userData;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.presenter.categoriesPresenter;
import com.example.lenovo.goahead.view.presenter.userPresenter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class categories extends AppCompatActivity implements interfaceMVPP.interfaces.View, userData.interfaces.View{
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
    RelativeLayout indexfour;
    String id;
    ViewSwitcher viewSwitcher;
    AnimationDrawable  animationDrawable;
    LinearLayout addnewproducts;
    final static  String  categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
    BottomNavigationView bottomNavigationView;
    TextView category;
    userPresenter userPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        //change bar name
        category=(TextView)findViewById(R.id.category);
        category.setText("Select Category");
        //Go To subcategory
        intent=new Intent(categories.this,cat.class);
        progressdialog=new progressdialog();
        ImageView rotate=(ImageView)findViewById(R.id.loading);
          animationDrawable=(AnimationDrawable)rotate.getDrawable();
        animationDrawable.start();
        menu();
        GetAllData();
        onClick();
        goToActivity();
        LOGOUT();
        getDetails();
        onClickNav();
    }

    //menu button to open navigation drawer
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

        // gaet categories data and navigation
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
                        animationDrawable.stop();
                        viewSwitcher=(ViewSwitcher)findViewById(R.id.viewswitch);
                        viewSwitcher.showNext();
                        JSONArray jsonArray=response.getJSONArray("categories");
                    for (int index=0;index<jsonArray.length();index++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(index);
                        String categoriesImg = jsonObject.getString("image");
                        arrayList.add(new categoriesList(jsonObject.getString("name"), categoriesImg, jsonObject.getInt("id")));
                        arrayListNav.add(new navList( jsonObject.getInt("id"),jsonObject.getString("name"),jsonObject.getString("icon")));
                    }
                    catLayoutManager=new LinearLayoutManager(categories.this);
                    categorieslist.setLayoutManager(catLayoutManager);
                    categoriesAdapter=new categoriesAdapter(categories.this,arrayList);
                    categorieslist.setAdapter(categoriesAdapter);
                    getNavigationData(arrayListNav);
                    }

                } catch (JSONException e) {
                    Toast.makeText(categories.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                animationDrawable.stop();
                viewSwitcher=(ViewSwitcher)findViewById(R.id.viewswitch);
                viewSwitcher.showNext();
                Toast.makeText(categories.this, "No Connection", Toast.LENGTH_SHORT).show();
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

                     }else if(menuItem.getItemId()==R.id.Familiesproduced)
                     {
                         startActivity(new Intent(categories.this,familiesProduces.class));
                     }
                     else if(menuItem.getItemId()==R.id.offers)
                     {
                         startActivity(new Intent(categories.this,offers.class));

                     }
                    return true;
                }
            });

        }

        //logout from application
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

        //check if lofin or false by using sharedperefrences
    public void sendBoolean()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("id",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("islogin",false);
        editor.commit();
    }

    //get navigation data
    public void getNavigationData(ArrayList data)
    {
        addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categories.this,addNewProduct.class));
            }
        });

        navLists=(RecyclerView)findViewById(R.id.navlist);
        navLayoutManager=new LinearLayoutManager(categories.this);
        navLists.setLayoutManager(navLayoutManager);
        navCategoriesAdapter=new navigationAdapter(categories.this,data);
        navLists.setAdapter(navCategoriesAdapter);

    }

    //get userDetails
    private void getDetails()
    {
     TextView name=(TextView)findViewById(R.id.userName);
     ImageView profile=(ImageView)findViewById(R.id.userprofile);
        userPresenter   userPresenter=new userPresenter(categories.this,categories.this,name,profile);
        userPresenter.getData();
    }

    //on Click Navigation
    private void onClickNav()
    {
        LinearLayout  addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categories.this,addNewProduct.class));
            }
        });

        LinearLayout myProducts=(LinearLayout)findViewById(R.id.myproducts);
        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(categories.this,myProducts.class));

            }
        });
    }
}
