package com.example.lenovo.goahead.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.library.savedId;
import com.example.lenovo.goahead.view.view.addNewProduct;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Premission extends Activity {
    int storage_premission_code=1;
    Context context;
    savedId savedId;
    ViewSwitcher viewSwitcher;
    int RequestCode=1;
    addNewProduct addNewProduct;
    //if premission granted

    public void checkPremissionSignup(Context context)
    {
        this.context=context;
        if(ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
        {
                  }
        else {
            premissionYorN();
        }
    }
    public void checkPremission(Context context)
    {
        this.context=context;
        if(ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
        {
            addNewProduct addNewProduct=new addNewProduct();
            addNewProduct.dialog(context);        }
        else {
            premissionYorN();
        }
    }

    //dialog premission
    public void premissionYorN()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale((addNewProduct) context,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(context).setTitle("Premission To Open Gallery").setMessage("If you need to upload image you must do this premission").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                //positive
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions((addNewProduct) context,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},storage_premission_code);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        }else {
            ActivityCompat.requestPermissions((addNewProduct) context,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},storage_premission_code);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
     if(requestCode==storage_premission_code)
     {
         if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
             addNewProduct addNewProduct=new addNewProduct();
             addNewProduct.dialog(context);
         }
         else {
             Toast.makeText(context, "not Granted", Toast.LENGTH_SHORT).show();
         }
     }
    }




}
