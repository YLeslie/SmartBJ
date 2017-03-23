package com.example.smartbj.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by 寳 on 2017-03-21.
 */

public class NetWorkManager {
    private static final int MAX_CACHE_SIZE = 5 * 1024 * 1024;
    private static RequestQueue sRequestQueue;
    private static ImageLoader sImageLoader;

    public static void init (Context context) {
        sRequestQueue = Volley.newRequestQueue(context);
        sImageLoader = new ImageLoader(sRequestQueue, new SmartBJImageCache(MAX_CACHE_SIZE));
    }

    public static void sendRequest (Request request) {
        sRequestQueue.add(request);
    }

    public static ImageLoader getImageLoader () {
        return sImageLoader;
    }
}
