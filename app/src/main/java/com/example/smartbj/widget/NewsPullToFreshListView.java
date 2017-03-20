package com.example.smartbj.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import com.example.smartbj.R;
import com.itheima.pulltorefreshlib.PullToRefreshListView;
import com.leon.loopviewpagerlib.FunBanner;

/**
 * Created by 寳 on 2017-03-19.
 */

public class NewsPullToFreshListView extends PullToRefreshListView {

    private int[] imageResIds = {R.mipmap.icon_1, R.mipmap.icon_2, R.mipmap.icon_3, R.mipmap.icon_4, R.mipmap.icon_5};

    public NewsPullToFreshListView (Context context) {
        this(context, null);
    }

    public NewsPullToFreshListView (Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init () {
        setAdapter(mAdapter);
        setMode(Mode.BOTH);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        FunBanner.Builder builder = new FunBanner.Builder(getContext());
        FunBanner funBanner = builder.setEnableAutoLoop(true)
                .setImageResIds(imageResIds)
                .setDotSelectedColor(Color.RED)
                .setHeightWidthRatio(0.5556f)
                .setLoopInterval(5000)
                .setEnableAutoLoop(true)
                .setIndicatorBackgroundColor(R.color.indicator_bg)
                .build();

        getRefreshableView().addHeaderView(funBanner);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount () {
            return 30;
        }

        @Override
        public Object getItem (int position) {
            return null;
        }

        @Override
        public long getItemId (int position) {
            return 0;
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_news_pull_fresh, null);
            }
            return convertView;
        }
    };
}
