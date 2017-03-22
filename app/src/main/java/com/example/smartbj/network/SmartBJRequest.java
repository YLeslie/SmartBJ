package com.example.smartbj.network;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.example.smartbj.utils.LoggerUtils;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by 寳 on 2017-03-21.
 */

public class SmartBJRequest<T> extends JsonRequest<T> {
    private static final String TAG = "SmartBJRequest";
    private Gson mGson = new Gson();
    private Class<T> mClazz;

    public SmartBJRequest (Class clazz, String url, NetWorkListener<T> listener) {
        super(Method.GET, url, null, listener, listener);
        mClazz = clazz;
    }

    @Override
    protected Response parseNetworkResponse (NetworkResponse response) {
        byte[] data = response.data;
        try {
            String result = new String(data, PROTOCOL_CHARSET);
            LoggerUtils.d(TAG, "parseNetworkResponse: " + result);
            T bean = mGson.fromJson(result, mClazz);
            //解析网络响应中的缓存头
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
            return Response.success(bean, cacheEntry);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
