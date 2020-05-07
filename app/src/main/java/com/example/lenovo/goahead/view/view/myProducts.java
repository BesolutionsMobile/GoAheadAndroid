package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.navigationAdapter;
import com.example.lenovo.goahead.view.interfaces.productsMvp;
import com.example.lenovo.goahead.view.interfaces.userData;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.presenter.deleteProductsPresenter;
import com.example.lenovo.goahead.view.presenter.productsPresenter;
import com.example.lenovo.goahead.view.presenter.userPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class myProducts extends AppCompatActivity implements productsMvp.interfaces.View, userData.interfaces.View {
    deleteProductsPresenter productsPresenter;
    RecyclerView FamiliesproductsList;
    Intent intent;
    LinearLayout addnewproductss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
        intent=getIntent();
        String category_id= intent.getStringExtra("category_id");
        savedId savedId=new savedId();
        String Name= intent.getStringExtra("name");
        //set Bar Name
        TextView category=(TextView)findViewById(R.id.category);
        category.setText("My Products");
        FamiliesproductsList=(RecyclerView)findViewById(R.id.FamiliesproductsList);
        productsPresenter=new deleteProductsPresenter(this,this,FamiliesproductsList,"4",savedId.getId(myProducts.this));
        productsPresenter.getData();
        LOGOUT();
        GetAllData();
        menu();
        goToActivity();
        getDetails();
        onClickNav();
    }

    //getData for navigationview
    public void GetAllData()
    {
        String categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
        final ArrayList<categoriesList> arrayList=new ArrayList<categoriesList>();
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
                    Toast.makeText(myProducts.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(myProducts.this, "error", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(myProducts.this);
        requestQueue.add(jsonObjectRequest);
    }

    //logout from navigation
    public void LOGOUT()
    {
        LinearLayout Logout=(LinearLayout)findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBoolean();
                progressdialog progressdialog=new progressdialog();
                progressdialog.progressDialog(myProducts.this);
                startActivity(new Intent(myProducts.this,login.class));
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
        LinearLayout   addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(myProducts.this,addNewProduct.class));
            }
        });

        RecyclerView    navLists=(RecyclerView)findViewById(R.id.navlist);
        RecyclerView.LayoutManager   navLayoutManager=new LinearLayoutManager(myProducts.this);
        navLists.setLayoutManager(navLayoutManager);
        RecyclerView.Adapter navCategoriesAdapter=new navigationAdapter(myProducts.this,data);
        navLists.setAdapter(navCategoriesAdapter);

    }

    //menu button
    public void menu()
    {
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        ImageView menu=(ImageView)findViewById(R.id.menu);
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

    //go to products
    public void goToActivity()
    {
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.favourites)
                {
                    startActivity(new Intent(myProducts.this,favourite.class));
                }else if(menuItem.getItemId()==R.id.mistadded)
                {
                    startActivity(new Intent(myProducts.this,mostAdded.class));

                }else if(menuItem.getItemId()==R.id.Familiesproduced)
                {
                    startActivity(new Intent(myProducts.this,familiesProduces.class));
                }
                else if(menuItem.getItemId()==R.id.offers)
                {
                    startActivity(new Intent(myProducts.this,offers.class));

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
        userPresenter userPresenter=new userPresenter(myProducts.this,myProducts.this,name,profile);
        userPresenter.getData();
    }

    //on Click Navigation
    private void onClickNav()
    {
        LinearLayout  addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myProducts.this,addNewProduct.class));
            }
        });

        LinearLayout myProducts=(LinearLayout)findViewById(R.id.myproducts);
        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myProducts.this,myProducts.class));

            }
        });
    }
}
