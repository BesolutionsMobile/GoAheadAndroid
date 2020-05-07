package com.example.lenovo.goahead.view.customAdapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.Premission;
import com.example.lenovo.goahead.view.library.progressdialog;
import com.example.lenovo.goahead.view.list.categoriesList;
import com.example.lenovo.goahead.view.view.addNewProduct;
import com.example.lenovo.goahead.view.view.cat;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class addNewProductAdapter extends RecyclerView.Adapter<addNewProductAdapter.categoriesHolder> {
    Context context;
    ArrayList<categoriesList>mylist;
    ViewSwitcher  viewSwitcher;
    int storage_premission_code=1;

    public addNewProductAdapter(Context context, ArrayList<categoriesList> mylist) {
        this.context = context;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public categoriesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.category_item,viewGroup,false);
        categoriesHolder categoriesHolder=new categoriesHolder(view);
        return categoriesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull categoriesHolder viewHolder, final int i) {
        final Premission premission=new Premission();
        Picasso.with(context).load(mylist.get(i).getImage()).into(viewHolder.background);
        viewHolder.title.setText(mylist.get(i).getName().toString());
        final int id=mylist.get(i).getId();
        viewHolder.categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premission.checkPremission(context);
                String categoryId=String.valueOf(mylist.get(i).getId());
                saveNumforSwitch(categoryId);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class categoriesHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView background;
        LinearLayout categories;
        public categoriesHolder(@NonNull View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.txtback);
            background=(ImageView)itemView.findViewById(R.id.categoriesback);
            categories=(LinearLayout)itemView.findViewById(R.id.categoriesItem);
        }
    }

    public void saveNumforSwitch(String num)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("categoryId",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("categoryId", num);
        editor.commit();
    }
}
