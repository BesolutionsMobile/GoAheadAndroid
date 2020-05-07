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
import com.example.lenovo.goahead.view.customAdapter.categoriesAdapter;
import com.example.lenovo.goahead.view.customAdapter.navigationAdapter;
import com.example.lenovo.goahead.view.interfaces.userData;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.presenter.userPresenter;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class favourite extends AppCompatActivity implements userData.interfaces.View {
   RecyclerView favouriteList;
   RecyclerView.Adapter favAdapter;
    DrawerLayout drawerLayout;
    ImageView menu;
    ShimmerFrameLayout shimmerContainer;
   RecyclerView.LayoutManager layoutManager;
    savedId savedId;
    RecyclerView categorieslist,navLists;
    RecyclerView.Adapter categoriesAdapter,navCategoriesAdapter;
    progressdialog progressdialog;
    static  ViewSwitcher viewSwitcher;


    RecyclerView.LayoutManager catLayoutManager,navLayoutManager;
   public static final String favUrl="http://coderg.org/goahead_en/User/getFavoriteOffers/82984218/951735/";
    final static  String  categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        //change bar name
        TextView category=(TextView)findViewById(R.id.category);
        category.setText("My Favourite");

        shimmerContainer = (ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);
        shimmerContainer.startShimmerAnimation();
        favouriteList=(RecyclerView)findViewById(R.id.favouriteList);
        savedId=new savedId();
        getFav();
        menu();
        GetAllData();
        LOGOUT();
        addNewProduct();
        goToActivity();
        getDetails();
        onClickNav();
        }

        //get favourite data
    public void getFav()
    {
        final ArrayList<catList>arrayList=new ArrayList<catList>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, favUrl + savedId.getId(this), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    viewSwitcher=(ViewSwitcher)findViewById(R.id.my_switcher);
                    viewSwitcher.showNext();
                    if (response.getString("status").equals("1")){
                    JSONArray favArrary=response.getJSONArray("offers");
                    for(int index=0;index<favArrary.length();index++)
                    {
                        JSONObject favObject=favArrary.getJSONObject(index);
                        String favImg=favObject.getString("image");
                        arrayList.add(new catList(favImg,favObject.getString("link"),favObject.getString("name"),favObject.getInt("id"),favObject.getInt("favorite")));
                    }
                        GridLayoutManager layoutManager = new GridLayoutManager(favourite.this, 2);
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
                        });
                        favouriteList.setLayoutManager(layoutManager);
                    favAdapter=new catAdapter(500,favourite.this,arrayList);
                    favouriteList.setAdapter(favAdapter);
                    }
                    else if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(favourite.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(response.getString("status").equals("3"))
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
//click menu  to open navigation
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
                progressdialog.progressDialog(favourite.this);
                startActivity(new Intent(favourite.this,login.class));
            }
        });
    }

 //check if true or false
    public void sendBoolean()
    {
        progressdialog progressdialog=new progressdialog();
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
                startActivity(new Intent(favourite.this,addNewProduct.class));
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
                    startActivity(new Intent(favourite.this,favourite.class));
                }else if(menuItem.getItemId()==R.id.mistadded)
                {
                    startActivity(new Intent(favourite.this,mostAdded.class));

                }else if(menuItem.getItemId()==R.id.Familiesproduced)
                {
                    startActivity(new Intent(favourite.this,familiesProduces.class));
                }
                else if(menuItem.getItemId()==R.id.offers)
                {
                    startActivity(new Intent(favourite.this,offers.class));

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
        userPresenter userPresenter=new userPresenter(favourite.this,favourite.this,name,profile);
        userPresenter.getData();
    }

    //on Click Navigation
    private void onClickNav()
    {
        LinearLayout  addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(favourite.this,addNewProduct.class));
            }
        });

        LinearLayout myProducts=(LinearLayout)findViewById(R.id.myproducts);
        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(favourite.this,myProducts.class));

            }
        });
    }

}
