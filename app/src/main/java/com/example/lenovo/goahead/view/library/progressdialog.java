package com.example.lenovo.goahead.view.library;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.view.addNewProduct;
import com.example.lenovo.goahead.view.view.productDetails;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static android.app.Activity.RESULT_OK;

public class progressdialog {
    Bitmap SelectedPhoto;
    Context context;
    AnimationDrawable animationDrawable;
    TextView title,descripition,price;
    public void progressDialog(Context context)
    {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.show();
    }

    public void dialog(final Context context, int resource,double widthh)
    {
        this.context=context;
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resource);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*widthh);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView rotate=(ImageView)dialog.findViewById(R.id.loading);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        animationDrawable=(AnimationDrawable)rotate.getDrawable();
        animationDrawable.start();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },3000,3500);
        }

    public void dialogDismiss(final Context context, int resource)
    {
        this.context=context;
        final Dialog dialog = new Dialog(context);
        dialog.dismiss();
    }


    public void serverDialog(final Context context, int resource,double widthh,int num)
    {
        this.context=context;
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resource);
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*widthh);
        int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
        ImageView rotate=(ImageView)dialog.findViewById(R.id.loading);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        animationDrawable=(AnimationDrawable)rotate.getDrawable();
        animationDrawable.start();
        if(num==0)
        {
            dialog.dismiss();
        }
        }

 //loading server
 public void loading(final Context context, int resource,double widthh)
 {
     this.context=context;
     final Dialog dialog = new Dialog(context);
     dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
     dialog.setContentView(resource);
     int width = (int)(context.getResources().getDisplayMetrics().widthPixels*widthh);
     int height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
     ImageView rotate=(ImageView)dialog.findViewById(R.id.loading);
     dialog.getWindow().setLayout(width, height);
     dialog.show();
     animationDrawable=(AnimationDrawable)rotate.getDrawable();
     animationDrawable.start();

 }
}
