package com.example.lenovo.goahead.view.view;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
  EditText userName,Email,Password,phone;
  Button Signup;
  public static final String RegistUrl="http://webdesign.be4em.info/goahead_en/User/register/82984218/951735/";
  LinearLayout Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userName=(EditText)findViewById(R.id.username);
        Email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.password);
        phone=(EditText)findViewById(R.id.phone);
        Signup=(Button)findViewById(R.id.signup);
        fillData();
        onClick();
    }

    public void fillData() {
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest Regist = new StringRequest(Request.Method.GET, RegistUrl + userName.getText().toString()+"/"+Email.getText().toString()+"/"+phone.getText().toString()+"/"+Password.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("1")) {
                                Toast.makeText(signup.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                goToMain();

                            } else if (jsonObject.getString("status").equals("2")) {
                                Toast.makeText(signup.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(signup.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(signup.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                )
            ;
                RequestQueue requestQueue = Volley.newRequestQueue(signup.this);
                requestQueue.add(Regist);


            }
        });
    }
    public void goToMain()
    {
        startActivity(new Intent(signup.this,categories.class));

    }
    public void onClick()
    {
        Login=(LinearLayout)findViewById(R.id.Login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signup.this,login.class));
            }
        });
    }
}
