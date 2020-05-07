package com.example.lenovo.goahead.view.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.ViewSwitcher;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.customAdapter.addNewProductAdapter;
import com.example.lenovo.goahead.view.customAdapter.categoriesAdapter;
import com.example.lenovo.goahead.view.customAdapter.multiImageAdapter;
import com.example.lenovo.goahead.view.customAdapter.navigationAdapter;
import com.example.lenovo.goahead.view.interfaces.addProduct;
import com.example.lenovo.goahead.view.interfaces.userData;
import com.example.lenovo.goahead.view.library.adapter;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.list.imageList;
import com.example.lenovo.goahead.view.list.navList;
import com.example.lenovo.goahead.view.presenter.addProductPresenter;
import com.example.lenovo.goahead.view.presenter.userPresenter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.github.yavski.fabspeeddial.FabSpeedDial;

import static android.app.Activity.RESULT_OK;

public class addNewProduct extends AppCompatActivity implements addProduct.interfaces.View, userData.interfaces.View {
   static Bitmap SelectedPhoto=null;
    static Bitmap SelectedPhotos=null;
    progressdialog progressdialog;
    int check;
    String str[] ;
    static int x=0;
    static  String imgs;
   static Bitmap bitmaps;
   static ViewSwitcher viewSwitcherr,switchimages;
  static int multiNum=0;
    byte[]b;
   static ArrayList<String> stri;

    savedId savedId;
    Context context;
    static ImageView uploadedimg;
    static ViewSwitcher viewSwitcher;
     Dialog dialog;

     int multiImage=2;
     String user_id;
    static RecyclerView imageList;
     RecyclerView.Adapter imageAdapter;
     RecyclerView.LayoutManager layoutManager;
     TextView next;
    final Bitmap bitmap=null;
    RecyclerView categorieslist;
    addProductPresenter addProductPresenter;
    RecyclerView.Adapter categoriesAdapter,navCategoriesAdapter;
    RecyclerView.LayoutManager catLayoutManager,navLayoutManager;
    final static  String  categoriesUrl="http://coderg.org/goahead_en/Product_category/getAll/82984218/951735";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        //change bar name
        TextView AddNewProduct=(TextView)findViewById(R.id.category);
        AddNewProduct.setText("Add New Product");
        GetAllData();
        progressdialog=new progressdialog();
        savedId=new savedId();
        user_id=savedId.getId(addNewProduct.this);
        menu();
        LOGOUT();
        GetAllDataNav();
        goToActivity();
        getDetails();
        onClick();
    }


  public void GetAllData()
    {
        categorieslist=(RecyclerView)findViewById(R.id.otherProductList);
        final ArrayList<categoriesList> arrayList=new ArrayList<categoriesList>();
        final ArrayList<navList>arrayListNav=new ArrayList<navList>();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, categoriesUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status").equals("1")) {
                        JSONArray jsonArray = response.getJSONArray("categories");
                        for (int index = 0; index < jsonArray.length(); index++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(index);
                            String categoriesImg = jsonObject.getString("image");
                            arrayList.add(new categoriesList(jsonObject.getString("name"), categoriesImg, jsonObject.getInt("id")));
                            arrayListNav.add(new navList(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getString("image")));
                        }
                        catLayoutManager = new GridLayoutManager(addNewProduct.this, 3);
                        categorieslist.setLayoutManager(catLayoutManager);
                        categoriesAdapter = new addNewProductAdapter(addNewProduct.this, arrayList);
                        categorieslist.setAdapter(categoriesAdapter);
                        }
                } catch (JSONException e) {
                   Log.e("catch",e.getLocalizedMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addNewProduct.this, "error", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(addNewProduct.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void switcher(ViewSwitcher viewSwitcher)
         {
             this.viewSwitcher=viewSwitcher;
         }


    ///dialog
    public void dialog(final Context context)
    {
        final progressdialog progressdialog=new progressdialog();
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.uploadimage);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.80);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        viewSwitcher=(ViewSwitcher)dialog.findViewById(R.id.viewswitcher);
        uploadedimg=(ImageView)dialog.findViewById(R.id.uploadedimg);
        TextView uplaodimage=(TextView)dialog.findViewById(R.id.uplaodimage);

        //click to upload image
        uplaodimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ((addNewProduct)context).startActivityForResult(Intent.createChooser(i, "Select Your Photo"),1);

                }
        });

        //go To Next Dialog
        TextView next=(TextView)dialog.findViewById(R.id.next);
        final addNewProduct  addNewProduct=new addNewProduct();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog.dialog(context,R.layout.loading,0.30);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        x=0;
                        dialog.dismiss();
                        addNewProduct.dialogMultiapleImages(context);

                    }
                }, 3000);
                }
        });

        //Cancel Dialog
        TextView Cancel=(TextView)dialog.findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=0;
                dialog.dismiss();
            }
        });
    }


    ///dialog multiapleImages
    public void dialogMultiapleImages(final Context context)
    {
        final progressdialog progressdialog=new progressdialog();
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.multiaple_images);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.80);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        switchimages=(ViewSwitcher)dialog.findViewById(R.id.switchimages);
        viewSwitcherr=(ViewSwitcher)dialog.findViewById(R.id.viewswitchermulti);
        TextView uplaodimage=(TextView)dialog.findViewById(R.id.uplaodimage);
        imageList=(RecyclerView)dialog.findViewById(R.id.imageList);

        //click to upload image
        uplaodimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( Intent.EXTRA_ALLOW_MULTIPLE,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                ((addNewProduct)context).startActivityForResult(Intent.createChooser(i, "Select Your Photo"),multiImage);

            }
        });

        //go To Next Dialog
        TextView next=(TextView)dialog.findViewById(R.id.next);
        final addNewProduct  addNewProduct=new addNewProduct();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog.dialog(context,R.layout.loading,0.30);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        x=0;
                        dialog.dismiss();
                    }
                }, 3000);
                addNewProduct.dialog_data(context,R.layout.complete_data,0.90);
            }
        });

        //Cancel Dialog
        TextView Cancel=(TextView)dialog.findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=0;
                dialog.dismiss();
            }
        });
    }


    //upload image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    FileOutputStream fos = null;
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                SelectedPhoto = BitmapFactory.decodeStream(imageStream);
                 bitmaps = Bitmap.createScaledBitmap(SelectedPhoto, 300, 300, true);
                uploadedimg.setImageBitmap(bitmaps);

                }
            if(x==0) {
                if (bitmaps != null) {
                    viewSwitcher.showNext();
                    x = 1;
                }

            }

// upload multiImages try to convert arraylist string
        } if (resultCode == RESULT_OK) {
            if (requestCode == multiImage) {
                ClipData clipData=data.getClipData();
                stri=new ArrayList<String>();
                if (clipData==null)
                {
                    Toast.makeText(addNewProduct.this, "Please Select More Than Image", Toast.LENGTH_LONG).show();

                }
               else if(clipData!=null)
                {
                    if(multiNum==0)
                    {
                        switchimages.showNext();
                        viewSwitcherr.showNext();
                        multiNum=1;
                    }
                    ArrayList<imageList>arrayList=new ArrayList<imageList>();
                    for (int index=0;index<clipData.getItemCount();index++)
                    {
                        ClipData.Item item = clipData.getItemAt(index);
                        Uri uri = item.getUri();
                        try {
                            InputStream    imageStreams = getContentResolver().openInputStream(uri);
                            SelectedPhotos = BitmapFactory.decodeStream(imageStreams);
                            Bitmap bitmap = Bitmap.createScaledBitmap(SelectedPhotos, 300, 300, true);
                            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
                            byte[] bs = os.toByteArray();
                            String Image= Base64.encodeToString(bs,Base64.DEFAULT);
                            stri.add(Image);
                            arrayList.add(new imageList(uri));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        }
                    adapter adapter=new adapter();
                    adapter.griddAdapters(imageList,new multiImageAdapter(addNewProduct.this,arrayList),addNewProduct.this,5);

                } }
                }
                }


        //completeDataDialog

    public void dialog_data(final Context context, int resource, double widthh)
    {
        this.context=context;
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resource);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*widthh);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        ImageView close=(ImageView)dialog.findViewById(R.id.cancel);
        Button submit=(Button)dialog.findViewById(R.id.submit);
       final TextView title=(TextView)dialog.findViewById(R.id.productname);
        final TextView   descripition=(TextView)dialog.findViewById(R.id.productDescripition);
        final TextView  price=(TextView)dialog.findViewById(R.id.price);

        //go to details
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressdialog=new progressdialog();
                progressdialog.loading(context,R.layout.loading,0.30);
                getData(title.getText().toString(),descripition.getText().toString(),price.getText().toString(),context,dialog);

            }
        });

        //close
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }, 3000);
        dialog.dismiss();


    }

    public void getData(String title,String descripition,String price,Context context,Dialog dialog)
    {
       savedId savedId=new savedId();
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), SelectedPhoto, "Title", null);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmaps.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmaps.compress(Bitmap.CompressFormat.PNG,100,os);
        b = os.toByteArray();
        String Image= Base64.encodeToString(b,Base64.DEFAULT);
     addProductPresenter =new addProductPresenter(addNewProduct.this,context,savedId.getId(context),savedId.getNumSwithcer(context),title,descripition,price,Image,dialog,stri);
     addProductPresenter.getData();

    }

    //getData for navigationview
    public void GetAllDataNav()
    {
        String categoriesUrl="http://coderg.org/goahead_en/Category/getAll/82984218/951735";
        final ArrayList<categoriesList> arrayList=new ArrayList<categoriesList>();
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
                    Toast.makeText(addNewProduct.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(addNewProduct.this, "error", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue= Volley.newRequestQueue(addNewProduct.this);
        requestQueue.add(jsonObjectRequest);
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
                progressdialog.progressDialog(addNewProduct.this);
                startActivity(new Intent(addNewProduct.this,login.class));
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

    public void getNavigationData(ArrayList data)
    {
        LinearLayout   addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(addNewProduct.this,addNewProduct.class));
            }
        });

        RecyclerView    navLists=(RecyclerView)findViewById(R.id.navlist);
        RecyclerView.LayoutManager   navLayoutManager=new LinearLayoutManager(addNewProduct.this);
        navLists.setLayoutManager(navLayoutManager);
        RecyclerView.Adapter navCategoriesAdapter=new navigationAdapter(addNewProduct.this,data);
        navLists.setAdapter(navCategoriesAdapter);

    }

    //menu button
    public void menu()
    {
        final DrawerLayout   drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        ImageView  menu=(ImageView)findViewById(R.id.menu);
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
                    startActivity(new Intent(addNewProduct.this,favourite.class));
                }else if(menuItem.getItemId()==R.id.mistadded)
                {
                    startActivity(new Intent(addNewProduct.this,mostAdded.class));

                }else if(menuItem.getItemId()==R.id.Familiesproduced)
                {
                    startActivity(new Intent(addNewProduct.this,familiesProduces.class));
                }
                else if(menuItem.getItemId()==R.id.offers)
                {
                    startActivity(new Intent(addNewProduct.this,offers.class));

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
        userPresenter userPresenter=new userPresenter(addNewProduct.this,addNewProduct.this,name,profile);
        userPresenter.getData();
    }

    //on Click Navigation
    private void onClick()
    {
        LinearLayout  addnewproducts=(LinearLayout)findViewById(R.id.addnewproduct);
        addnewproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addNewProduct.this,addNewProduct.class));
            }
        });

        LinearLayout myProducts=(LinearLayout)findViewById(R.id.myproducts);
        myProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addNewProduct.this,myProducts.class));

            }
        });
    }
}


