package com.example.smartbj.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 寳 on 2017-03-18.
 */

public class ToastUtils {
    private static boolean b = false;

    public static void make (Context context, String str) {
        if (!b) {
            return;
        }
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
