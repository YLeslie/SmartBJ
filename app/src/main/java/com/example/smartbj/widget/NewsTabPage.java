package com.example.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
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
        TextView textView = new TextView(getContext());
        switch (postion) {
            case 0:
                textView.setText("新闻");
                break;
            case 1:
                textView.setText("专题");
                break;
            case 2:
                textView.setText("组图");
                break;
            case 3:
                textView.setText("互动");
                break;
        }
        mTapPageContent.removeAllViews();
        mTapPageContent.addView(textView);
    }
}

