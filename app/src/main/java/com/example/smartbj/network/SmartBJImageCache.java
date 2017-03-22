package com.example.smartbj.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by 寳 on 2017-03-21.
 */

public class SmartBJImageCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public SmartBJImageCache (int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf (String key, Bitmap value) {
        return value.getByteCount();
    }

    @Override
    public Bitmap getBitmap (String url) {
        return get(url);
    }

    @Override
    public void putBitmap (String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
