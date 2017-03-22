package com.example.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.smartbj.bean.CategoriesBean;
import com.example.smartbj.network.NetWorkManager;
import com.example.smartbj.network.NetWorkListener;
import com.example.smartbj.network.SmartBJRequest;
import com.example.smartbj.utils.LoggerUtils;
import com.example.smartbj.utils.ToastUtils;

/**
 * Created by 寳 on 2017-03-18.
 */

public class NewsTabPage extends TabPage {
    private static final String TAG = "NewsTabPage";
    private CategoriesBean mCategoriesBean;

    public NewsTabPage (Context context) {
        super(context);
    }

    public NewsTabPage (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void switchTabPage (int postion) {
        View view = null;
        switch (postion) {
            case 0:
                NewsPage newsPage = new NewsPage(getContext());
                newsPage.setData(mCategoriesBean.getData().get(0));
                view = newsPage;
                break;
            case 1:
                view = new TextView(getContext());
                TextView zhuanti = (TextView) view;
                zhuanti.setText("专题");
                break;
            case 2:
                view = new TextView(getContext());
                TextView zutu = (TextView) view;
                zutu.setText("组图");
                break;
            case 3:
                view = new TextView(getContext());
                TextView hudong = (TextView) view;
                hudong.setText("互动");
                break;
        }
        mTapPageContent.removeAllViews();
        mTapPageContent.addView(view);
    }

    @Override
    public void loadDataFromService () {
        String url = "http://10.0.2.2:8080/zhbj/categories.json";
        SmartBJRequest smartBJRequest = new SmartBJRequest(CategoriesBean.class, url, mListener);
        NetWorkManager.sendRequest(smartBJRequest);
    }

    private NetWorkListener<CategoriesBean> mListener = new NetWorkListener<CategoriesBean>(){
        @Override
        public void onResponse (CategoriesBean response) {
            LoggerUtils.d(TAG, "解析成功");
            mCategoriesBean = response;
            switchTabPage(0);
        }

        @Override
        public void onErrorResponse (VolleyError error) {
            super.onErrorResponse(error);
            LoggerUtils.d(TAG, "解析失败");
            ToastUtils.make(getContext(),"请求失败");
        }
    };
    /*private Response.Listener<CategoriesBean> mListener =  new Response.Listener<CategoriesBean>() {

        @Override
        public void onResponse (CategoriesBean response) {
            LoggerUtils.d(TAG, "解析成功");
            mCategoriesBean = response;
            switchTabPage(0);
        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse (VolleyError error) {
            LoggerUtils.d(TAG, "解析失败");
        }
    };*/
}

