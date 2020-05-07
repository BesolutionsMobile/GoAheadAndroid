package com.example.lenovo.goahead.view.Model;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.view.interfaces.addProduct;
import com.example.lenovo.goahead.view.interfaces.interfaceMVP;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.view.addNewProduct;
import com.example.lenovo.goahead.view.view.categories;
import com.example.lenovo.goahead.view.view.productDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import uk.me.hardill.volley.multipart.MultipartRequest;


public class addProductModel implements addProduct.interfaces.Model{
    savedId savedId=new savedId();
    @Override
    public String getdata(String user_id, final String category_id, String title, String Description, String price, final Context context, final String imageView, final Dialog dialog, final ArrayList<String> imgs) {
        final ArrayList<String>gallery=imgs;
       Log.e("future",""+imgs);

        String categoryModel="http://coderg.org/goahead_en/Product/add2/82984218/951735/"+user_id+"/"+category_id+"/"+title+"/"+Description+"/"+price;
      final StringRequest stringRequest=new StringRequest(Request.Method.POST, categoryModel, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              try {
                  JSONObject jsonObject = new JSONObject(response);
                  if(jsonObject.getString("status").equals("1"))
                  {
                      dialog.dismiss();
                      ((addNewProduct) context).finish();
                      Intent intent=new Intent(context,productDetails.class);
                      intent.putExtra("id",jsonObject.getString("id_product"));
                      context.startActivity(intent);
                      Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                  }
                  else if(jsonObject.getString("status").equals("2"))
                  {
                      Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                  }
                  else if(jsonObject.getString("status").equals("3"))
                  {
                      Toast.makeText(context, ""+jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                  }
              } catch (JSONException e) {
                  Log.e("ssss",e.getLocalizedMessage());

              }
          }
      }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              Toast.makeText(context, ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
          }
      }) {

          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
              Map<String, String> params = new HashMap<String, String>();
              params.put("main_image", imageView);
              for (int index=0;index<imgs.size();index++) {
                  params.put("gallery["+index+"]", imgs.get(index));
              }
              return params;
              }
              }
          ;

      RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return null;

    }
}
