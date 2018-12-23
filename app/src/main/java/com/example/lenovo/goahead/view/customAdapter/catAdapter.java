package com.example.lenovo.goahead.view.customAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.catList;
import com.example.lenovo.goahead.view.view.login;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class catAdapter extends RecyclerView.Adapter<catAdapter.catHolder> {

    Context context;
   ArrayList<catList>mylist;
   public static final String deleteFav="http://coderg.org/goahead_en/User/deleteFavoriteOffer/82984218/951735/";
    public static final String addFav="http://coderg.org/goahead_en/User/addFavoriteOffer/82984218/951735/";


    public catAdapter(Context context, ArrayList<catList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public catHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.catitem,viewGroup,false);
        catHolder catHolder=new catHolder(view);
        return catHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final catHolder viewHolder, final int i) {
        Picasso.with(context).load(mylist.get(i).getLogo()).into(viewHolder.Logo);
        final String url=mylist.get(i).getUrl();
        final int id=mylist.get(i).getId();
        viewHolder.GoToWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            }
        });
        if(mylist.get(i).getFavNum()==1) {
            viewHolder.fav.setImageResource(R.drawable.ic_favorite_red_24dp);
            mylist.get(i).setFav(true);
        }
        viewHolder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mylist.get(i).isFav()==false){
                    viewHolder.fav.setImageResource(R.drawable.ic_favorite_red_24dp);
                    addDeleteFav(id,addFav);
                    mylist.get(i).setFav(true);
                }else{
                    viewHolder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    mylist.get(i).setFav(false);
                    addDeleteFav(id,deleteFav);
                    }
            }
        });
        }

    @Override
    public int getItemCount() {
        return mylist.size();
    }
    public class catHolder extends RecyclerView.ViewHolder {
        ImageView Logo,fav;
        LinearLayout GoToWeb;
        public catHolder(@NonNull View itemView) {
            super(itemView);
            Logo=(ImageView)itemView.findViewById(R.id.logo);
            GoToWeb=(LinearLayout)itemView.findViewById(R.id.goToWeb);
            fav=(ImageView)itemView.findViewById(R.id.fav);
        }
    }
    public void addDeleteFav(final int id,final String Url)
    {
        final savedId savedId=new savedId();
        String AddFavourite="";
        StringRequest addFav=new StringRequest(Request.Method.GET, Url+savedId.getId(context)+"/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")) {
                        Toast.makeText(context, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }else if(jsonObject.getString("status").equals("2"))
                    {
                        Toast.makeText(context,""+jsonObject.getString("message") , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context, "catch", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                }
            });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(addFav);
    }


}
