package com.example.lenovo.goahead.view.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.catAdapter;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.catList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mostAdded extends AppCompatActivity {
 RecyclerView mostAdded;
 RecyclerView.Adapter mostAddedAdapter;
 RecyclerView.LayoutManager layoutManager;
    savedId savedId;
 public static final String mostAddedUrl="http://coderg.org/goahead_en/Category/show_most_favorites/82984218/951735/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_added);
        mostAdded=(RecyclerView)findViewById(R.id.mostAddedList);
        savedId=new savedId();
        getAllData();
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
                        layoutManager=new GridLayoutManager(mostAdded.this,2);
                        mostAdded.setLayoutManager(layoutManager);
                        mostAddedAdapter=new catAdapter(mostAdded.this,arrayList);
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
}
