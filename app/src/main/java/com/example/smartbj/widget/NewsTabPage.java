package com.example.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 寳 on 2017-03-18.
 */

public class NewsTabPage extends TabPage {
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
                view = new NewsPage(getContext());
//                NewsPage newsPage = (NewsPage) view;
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
}

