package com.example.lenovo.goahead.view.customAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.productList;
import com.example.lenovo.goahead.view.view.productDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class deleteProductAdapter extends RecyclerView.Adapter<deleteProductAdapter.productHolder> {
    Context context;
    ArrayList<productList>mylist;

    public deleteProductAdapter(Context context, ArrayList<productList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.deleteproductitem,viewGroup,false);
        productHolder productHolder=new productHolder(view);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull productHolder viewHolder, final int i) {
       viewHolder.Title.setText(mylist.get(i).getTitle().toString());
       viewHolder.Descripition.setText(mylist.get(i).getDescripition().toString());
       viewHolder.Price.setText(mylist.get(i).getPrice().toString());
       Picasso.with(context).load(mylist.get(i).getImage()).into(viewHolder.productImg);

       // delete item from list
       viewHolder.delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

               // set title

               alertDialogBuilder.setTitle("Delete Product");

               // set dialog message
               alertDialogBuilder
                       .setMessage("Are You Sure You Need To Delete This Product")
                       .setCancelable(false)
                       .setPositiveButton("yes",new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog,int id) {
                               deletingProduct(mylist.get(i).getId().toString());
                               mylist.remove(i);
                               notifyItemRemoved(i);
                               notifyItemRangeChanged(i,mylist.size());

                           }
                       })
                       .setNegativeButton("No",new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog,int id) {
                               // if this button is clicked, just close
                               // the dialog box and do nothing
                               dialog.cancel();
                           }
                       });

               // create alert dialog
               AlertDialog alertDialog = alertDialogBuilder.create();
               // show it
               alertDialog.show();


           }
       });
       viewHolder.viewProductsLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context,productDetails.class);
               intent.putExtra("id",mylist.get(i).getId().toString());
               v.getContext().startActivity(intent);
           }
       });

       viewHolder.viewProduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context,productDetails.class);
               intent.putExtra("id",mylist.get(i).getId().toString());
               intent.putExtra("name",mylist.get(i).getTitle().toString());
               v.getContext().startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return mylist.size();
        }
    public class productHolder extends RecyclerView.ViewHolder {
        ImageView productImg,delete;
        TextView Title,Descripition,Price;
        Button viewProduct;
        LinearLayout viewProductsLayout;
        public productHolder(@NonNull View itemView) {
            super(itemView);
            productImg=(ImageView)itemView.findViewById(R.id.productImg);
            Title=(TextView)itemView.findViewById(R.id.productTitle);
            Descripition=(TextView)itemView.findViewById(R.id.productDescripition);
            Price=(TextView)itemView.findViewById(R.id.productPrice);
            viewProduct=(Button)itemView.findViewById(R.id.viewproduct);
            viewProductsLayout=(LinearLayout)itemView.findViewById(R.id.viewproductLayout);
            delete=(ImageView)itemView.findViewById(R.id.delete);

        }
    }

    //deleting Product From Server
    private void deletingProduct(String Product_id)
    {
        savedId savedId=new savedId();
        String deletingUrl="http://coderg.org/goahead_en/Product_category/deleteProduct/82984218/951735/"+Product_id+"/"+savedId.getId(context);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, deletingUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("status").equals("1"))
                    {
                        Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonObject.getString("status").equals("2"))
                    {
                        Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                    else if (jsonObject.getString("status").equals("3"))
                    {
                        Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.e("deletingProduct",e.getLocalizedMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
