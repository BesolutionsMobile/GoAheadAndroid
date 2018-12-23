package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.PagerAdapter;
import com.example.lenovo.goahead.view.fragments.news;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.presenter.catPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class cat extends AppCompatActivity{

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
        menu.setImageResource(R.drawable.logo);
        tabBarData();
       }

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

 public void tabBar(ArrayList<String>tablist)
 {
     tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
     tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
     viewPager=(ViewPager)findViewById(R.id.items);

     PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tablist,cat.this);
     viewPager.setAdapter(pagerAdapter);
     viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
     viewPager.setCurrentItem(getPosition());

     viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         @Override
         public void onPageScrolled(int i, float v, int i1) {
             tabLayout.setScrollPosition(i,0,true);
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

    public int getPosition()
    {
        intent=getIntent();
        int positionTap=intent.getIntExtra("position",0);
        int id=intent.getIntExtra("id",0);
        return positionTap;
    }


}
