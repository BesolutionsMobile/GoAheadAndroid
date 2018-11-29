package com.example.lenovo.goahead.view.Model;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.lenovo.goahead.R;
import com.example.lenovo.goahead.view.interfaces.interfaceMVP;
import com.example.lenovo.goahead.view.list.catList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class catModel implements interfaceMVP.interfaces.Model{

    @Override
    public ArrayList getdata() {
        ArrayList<catList>arrayList=new ArrayList<catList>();

        return arrayList;
    }
}
