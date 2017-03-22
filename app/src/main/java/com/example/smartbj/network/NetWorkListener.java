package com.example.smartbj.network;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by 寳 on 2017-03-21.
 */

public class NetWorkListener<T> implements Response.Listener<T>,Response.ErrorListener{
    @Override
    public void onErrorResponse (VolleyError error) {

    }

    @Override
    public void onResponse (T response) {

    }
}
