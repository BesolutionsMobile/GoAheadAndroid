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
import com.example.lenovo.goahead.view.interfaces.sellerMvp;
import com.example.lenovo.goahead.view.interfaces.userData;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.presenter.sellerPresenter;
import com.example.lenovo.goahead.view.presenter.userPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class seller extends AppCompatActivity  implements sellerMvp.interfaces.View, userData.interfaces.View{
    sellerPresenter sellerPresenter;
    savedId savedId;
    Intent intent;
    RecyclerView navLists;
    DrawerLayout drawerLayout;
    RecyclerView.Adapter navCategoriesAdapter;
    LinearLayout addnewproducts;
    RecyclerView.LayoutManager navLayoutManager;
    final static  String  categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);




        //get category id
        intent=getIntent();
        String category_id=intent.getStringExtra("id");
        String Name=intent.getStringExtra("name");
        //change bar name
        TextView category=(TextView)findViewById(R.id.category);
        category.setText(Name);

        RecyclerView sellerList=(RecyclerView)findViewById(R.id.sellerlist);
        sellerPresenter=new sellerPresenter(seller.this,seller.this,sellerList,category_id);
        sellerPresenter.getData();
        getNavData();
        menu();
        LOGOUT();
        goToActivity();
        getDetails();
        onClick();
    }

    public void getNavData()
    {
        final ArrayList<navList> arrayList=new ArrayList<navList>();
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
                            String categoriesImg = jsonObject.getString("icon");
                            arrayList.add(new navList(jsonObject.getInt("id"),jsonObject.getString("name"),categoriesImg));
                        }
                        getNavigationData(arrayList);
                    }
                    else  if(response.getString("status").equals("2"))
                    {
                        Toast.makeText(seller.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else  if(response.getString("status").equals("3"))
                    {
                        Toast.makeText(seller.this, ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(seller.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(seller.this, "No Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(seller.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void getNavigationData(ArrayList data)
    {
        navLists=(RecyclerView)findViewById(R.id.navlist);
        navLayoutManager=new LinearLayoutManager(seller.this);
        navLists.setLayoutManager(navLayoutManager);
        navCategoriesAdapter=new navigationAdapter(seller.this,data);
        navLists.setAdapter(navCategoriesAdapter);

    }


    public void menu()
    {
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
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

    //logout from navigation
    public void LOGOUT()
    {

        LinearLayout Logout=(LinearLayout)findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBoolean();
                progressdialog progressdialog=new progressdialog();
                progressdialog.progressDialog(seller.this);
                startActivity(new Intent(seller.this,login.class));
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

    //navigation bottom drawer
    public void goToActivity()
    {
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.favourites)
                {
                    startActivity(new Intent(seller.this,favourite.class));
                }else if(menuItem.getItemId()==R.id.mistadded)
                {
                    startActivity(new Intent(seller.this,mostAdded.class));

                }else if(menuItem.getItemId()==R.id.Familiesproduced)
                {
                    startActivity(new Intent(seller.this,familiesProduces.class));
                }
                else if(menuItem.getItemId()==R.id.offers)
                {
                    startActivity(new Intent(seller.this,offers.class));

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
        userPresenter userPresenter=new userPresenter(seller.this,seller.this,name,profile);
        userPresenter.getData();
    }

    //on Click Navigation
    private void onClick()
    {
        addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(seller.this,addNewProduct.class));
            }
        });

        LinearLayout myProducts=(LinearLayout)findViewById(R.id.myproducts);
        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(seller.this,myProducts.class));

            }
        });
    }
}
