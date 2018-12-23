package com.example.lenovo.goahead.view.library;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class savedId {

    public String getId(Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences("id",MODE_PRIVATE);
        String id=sharedPreferences.getString("id","");
        return id;
        }
        public boolean getUserBoolean(Context context)
        {
            SharedPreferences sharedPreferences=context.getSharedPreferences("id",MODE_PRIVATE);
            boolean value=sharedPreferences.getBoolean("islogin",false);
            return value;
        }
}
