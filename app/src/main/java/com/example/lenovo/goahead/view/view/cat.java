package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.example.lenovo.goahead.view.customAdapter.PagerAdapter;
import com.example.lenovo.goahead.view.customAdapter.navigationAdapter;
import com.example.lenovo.goahead.view.fragments.news;
import com.example.lenovo.goahead.view.interfaces.userData;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.presenter.catPresenter;
import com.example.lenovo.goahead.view.presenter.userPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class cat extends AppCompatActivity implements userData.interfaces.View {

    ViewPager viewPager;
    Intent intent;
    TabLayout tabLayout;
    ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        menu=(ImageView)findViewById(R.id.menu);
        tabBarData();
        GetAllData();
        LOGOUT();
        menu();
        goToActivity();
        getDetails();
        onClick();
       }

       //tab bar data
  public void tabBarData()
  {
     final ArrayList<String> arrayList=new ArrayList<>();
      final   String  categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
      JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, categoriesUrl, null, new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
              try {
                  if(response.getString("status").equals("1"))
                  {
                      JSONArray jsonArray=response.getJSONArray("categories");
                      for(int index=0;index<jsonArray.length();index++)
                      {
                          JSONObject jsonObject=jsonArray.getJSONObject(index);
                          tabLayout.addTab(tabLayout.newTab().setText(jsonObject.getString("name")));
                          arrayList.add(jsonObject.getString("name"));
                      }
                tabBar(arrayList);

                  }
              } catch (JSONException e) {
                  e.printStackTrace();
              }

          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

          }
      });

      RequestQueue requestQueue = Volley.newRequestQueue(cat.this);
      requestQueue.add(jsonObjectRequest);
  }

  //tab bar data and change it
 public void tabBar(final ArrayList<String>tablist)
 {
     tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
     tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
     viewPager=(ViewPager)findViewById(R.id.items);

     PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tablist,cat.this,cat_id());
     viewPager.setAdapter(pagerAdapter);
     viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
     viewPager.setCurrentItem(getPosition());
     final String name=tablist.get(getPosition());

     viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int i, float v, int i1) {
             tabLayout.setScrollPosition(i,0,true);
             //change bar name
             String name=tablist.get(i);
             TextView  category=(TextView)findViewById(R.id.category);
             category.setText(name);
             tabLayout.setSelected(true);
         }

         @Override
         public void onPageSelected(int i) {
   }

         @Override
         public void onPageScrollStateChanged(int i) {
  }
     });
     tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
         @Override
         public void onTabSelected(TabLayout.Tab tab) {
             viewPager.setCurrentItem(tab.getPosition());
         }

         @Override
         public void onTabUnselected(TabLayout.Tab tab) {
  }

         @Override
         public void onTabReselected(TabLayout.Tab tab) {
   }
     });
 }

 //get navigation data
    public void GetAllData()
    {
        String categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
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
                    Toast.makeText(cat.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(cat.this, "error", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(cat.this);
        requestQueue.add(jsonObjectRequest);
    }

 //get tab position

    public int getPosition()
    {
        intent=getIntent();
        int positionTap=intent.getIntExtra("position",0);
        int id=intent.getIntExtra("id",0);
        return positionTap;
    }

    // get category id
    public String cat_id()
    {
        intent=getIntent();
        String id=intent.getStringExtra("cat_id");
        return id;
    }


//logout
    public void LOGOUT()
    {
        final progressdialog progressdialog=new progressdialog();
        LinearLayout Logout=(LinearLayout)findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBoolean();
                progressdialog.progressDialog(cat.this);
                startActivity(new Intent(cat.this,login.class));
            }
        });
    }

    //check login true or false using shared prefrences
    public void sendBoolean()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("id",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("islogin",false);
        editor.commit();
    }

    //get Data from Navigaion
    public void getNavigationData(ArrayList data)
    {
        LinearLayout addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cat.this,addNewProduct.class));
            }
        });

        RecyclerView navLists=(RecyclerView)findViewById(R.id.navlist);
       RecyclerView.LayoutManager navLayoutManager=new LinearLayoutManager(cat.this);
        navLists.setLayoutManager(navLayoutManager);
       RecyclerView.Adapter navCategoriesAdapter=new navigationAdapter(cat.this,data);
        navLists.setAdapter(navCategoriesAdapter);
        navLists.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

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

    //Bottom Navigation
    public void goToActivity()
    {
        BottomNavigationView bottomNavigationView=findViewById(R.id.nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.favourites)
                {
                    startActivity(new Intent(cat.this,favourite.class));
                }else if(menuItem.getItemId()==R.id.mistadded)
                {
                    startActivity(new Intent(cat.this,mostAdded.class));

                }else if(menuItem.getItemId()==R.id.Familiesproduced)
                {
                    startActivity(new Intent(cat.this,familiesProduces.class));
                }
                else if(menuItem.getItemId()==R.id.offers)
                {
                    startActivity(new Intent(cat.this,offers.class));

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
        userPresenter userPresenter=new userPresenter(cat.this,cat.this,name,profile);
        userPresenter.getData();
    }

    //on Click Navigation
    private void onClick()
    {
        LinearLayout  addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cat.this,addNewProduct.class));
            }
        });

        LinearLayout myProducts=(LinearLayout)findViewById(R.id.myproducts);
        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(cat.this,myProducts.class));

            }
        });
    }
}
