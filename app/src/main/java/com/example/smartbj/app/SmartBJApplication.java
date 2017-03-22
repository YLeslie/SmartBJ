package com.example.smartbj.app;

import android.app.Application;

import com.example.smartbj.network.NetWorkManager;

/**
 * Created by 寳 on 2017-03-21.
 */

public class SmartBJApplication extends Application {
    @Override
    public void onCreate () {
        super.onCreate();
        NetWorkManager manager = new NetWorkManager();
        manager.init(getApplicationContext());
    }
}
