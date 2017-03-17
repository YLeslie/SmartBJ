package com.example.smartbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 寳 on 2017-03-17.
 */

public class SPTools {
    private static final String FILE_NAME = "SmartBJ";

    public static void setBoolean (Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean getBoolean (Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }
}
