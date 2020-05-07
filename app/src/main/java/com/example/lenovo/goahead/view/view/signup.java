package com.example.lenovo.goahead.view.view;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.lenovo.goahead.view.Premission;
import com.example.lenovo.goahead.view.library.savedId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.example.lenovo.goahead.view.view.addNewProduct.SelectedPhoto;
import static com.example.lenovo.goahead.view.view.addNewProduct.SelectedPhotos;
import static com.example.lenovo.goahead.view.view.addNewProduct.uploadedimg;

public class signup extends AppCompatActivity {
  EditText userName,Email,Password,phone;
  ImageView addImage;
  Button Signup;
  public static final String RegistUrl="http://coderg.org/goahead_en/User/register/82984218/951735/";
  LinearLayout Login;
  Premission premission;
    static  String Imagez;
    int storage_premission_code=1;
    static byte[] bs;
    byte[]b;
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
                startActivity(new Intent(signup.this,categories.class));
            }
        });
        addImage=(ImageView)findViewById(R.id.addimage);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grantedOrNot();


                }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                SelectedPhoto = BitmapFactory.decodeStream(imageStream);
                Bitmap bitmap = Bitmap.createScaledBitmap(SelectedPhoto, 300, 300, true);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
                 bs = os.toByteArray();
                 Imagez= Base64.encodeToString(bs,Base64.DEFAULT);
                Log.e("erroorr",Imagez);
                addImage.setImageBitmap(bitmap);
                } } }


    public void fillData() {
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest Regist = new StringRequest(Request.Method.POST, RegistUrl+userName.getText().toString()+"/"+Email.getText().toString()+"/"+phone.getText().toString()+"/"+Password.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("1")) {

                                Toast.makeText(signup.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                id(jsonObject.getString("user_id"));
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
                        Toast.makeText(signup.this, "No Internet Connection"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("signuperror", error.getLocalizedMessage());

                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("image", Imagez);
                        return params;
                    }
                }
                        ;
                RequestQueue requestQueue = Volley.newRequestQueue(signup.this);
                requestQueue.add(Regist);

                }
        });
    }

    //saved id for user
    public void id(String id)
    {
        SharedPreferences sharedPreferences=getSharedPreferences("id",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("islogin",true);
        editor.putString("id", id);
        editor.commit();

        }

        private void grantedOrNot()
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale((signup) signup.this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                new AlertDialog.Builder(signup.this).setTitle("Premission To Open Gallery").setMessage("If you need to upload image you must do this premission").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    //positive
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((signup) signup.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},storage_premission_code);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent( Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(i, "Select Your Photo"),1);
                    }
                }).create().show();
            }else {
                ActivityCompat.requestPermissions((signup) signup.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},storage_premission_code);
            }
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        if(requestCode==storage_premission_code)
        {
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent( Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(i, "Select Your Photo"),1);
            }
            else {
                Toast.makeText(signup.this, "not Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
