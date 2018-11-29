package com.example.lenovo.goahead.view.view;

import android.content.Intent;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.lenovo.goahead.R;

public class select extends AppCompatActivity {

    LinearLayout categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        goToCategories();
    }
public void goToCategories()
{
    categories=(LinearLayout)findViewById(R.id.categories);
    categories.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(select.this,categories.class));
            }
    });

}
}
