package com.example.smartbj.utils;

import android.util.Log;

/**
 * Created by 寳 on 2017-03-21.
 */

public class LoggerUtils {
    private static Boolean b = true;

    public static void d (String TAG, String content) {
        if (!b) {
            return;
        }
        Log.d(TAG, content);
    }
}
