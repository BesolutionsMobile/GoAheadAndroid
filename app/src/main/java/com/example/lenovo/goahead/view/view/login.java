package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    CallbackManager callbackManager;
    Button signin;
    TextView regist;
    String Emaill,Passwordd;
    EditText Email,Password;
    progressdialog progressdialog;
    boolean isLogin=false;
    String id;
    savedId savedId;

    public static final String login="http://webdesign.be4em.info/goahead_en/User/login/82984218/951735/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        regist=(TextView)findViewById(R.id.regist);
        progressdialog=new progressdialog();
        signin=(Button)findViewById(R.id.signin);
        ifLogin();
        goToRegist();
        CheckLogin();
        }


    public void EditText()
    {
        Email=(EditText)findViewById(R.id.username);
        Password=(EditText)findViewById(R.id.password);

    }


        public void CheckLogin()
        {

            EditText();
            Emaill = Email.getText().toString();
            signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringRequest Login=new StringRequest(Request.Method.GET, "http://webdesign.be4em.info/goahead_en/User/login/82984218/951735/"+Email.getText().toString()+"/"+Password.getText().toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if(jsonObject.getString("status").equals("1"))
                                {
                                    Toast.makeText(login.this,"successful login", Toast.LENGTH_SHORT).show();
                                     JSONObject jsonObject1=jsonObject.getJSONObject("user_data");
                                     id = jsonObject1.getString("id");
                                    progressdialog.progressDialog(login.this);
                                    goToMain(id);
                                    id();


                                }else if(jsonObject.getString("status").equals("2"))
                                {
                                    Toast.makeText(login.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    Email.setError("Error");
                                }
                                else if (jsonObject.getString("status").equals("3"))
                                {
                                    Toast.makeText(login.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(login.this,"SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(login.this,"No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                    }
                    )
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("mail", Emaill);
                            params.put("password", Passwordd);

                            return params;
                        }};

                    RequestQueue requestQueue = Volley.newRequestQueue(login.this);
                    requestQueue.add(Login);

                }

            });

        }
    public void goToRegist()
    {
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,signup.class));
                CheckLogin();
            }
        });
    }
     public void goToMain(String id)
     {


         Intent intent=new Intent(login.this,categories.class);
         intent.putExtra("id",id);
         startActivity(intent);
     }

    public void id()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("id",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("islogin",true);
        editor.putString("id", id);
        editor.commit();
    }

    public void ifLogin() {
        savedId = new savedId();
        if (savedId.getUserBoolean(login.this) == true) {
            startActivity(new Intent(login.this, categories.class));
        }
    }

}
