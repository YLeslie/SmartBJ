package com.example.smartbj.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.smartbj.R;
import com.example.smartbj.bean.CategoriesBean;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by 寳 on 2017-03-19.
 */

public class NewsPage extends LinearLayout {
    private static final String TAG = "NewsPage";

    private static final String[] CONTENT = new String[]{"Recent", "Artists", "Albums", "Songs", "Playlists", "Genres", "Itheima", "Chuanzhi"};
    private CategoriesBean.DataBean mData;
    private TabPageIndicator mIndicator;

    public NewsPage (Context context) {
        this(context, null);
    }

    public NewsPage (Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setData (CategoriesBean.DataBean data) {
        mData = data;
        mIndicator.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }

    private void init () {
        View.inflate(getContext(), R.layout.view_news_page, this);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(mAdapter);
        mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
    }

    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount () {
//            return CONTENT.length;
            if (mData != null) {
                return mData.getChildren().size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject (View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem (ViewGroup container, int position) {
            /*TextView textView = new TextView(getContext());
            textView.setText(CONTENT[position]);*/
            NewsPullToFreshListView newsPullToFreshListView = new NewsPullToFreshListView(getContext());
            String refreshUrl = "http://10.0.2.2:8080/zhbj" + mData.getChildren().get(position).getUrl();
            newsPullToFreshListView.setUrl(refreshUrl);
            Log.d(TAG, "instantiateItem: 设置下拉刷新url " + refreshUrl);
            container.addView(newsPullToFreshListView);
            return newsPullToFreshListView;
        }

        @Override
        public void destroyItem (ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle (int position) {
//            return CONTENT[position % CONTENT.length].toUpperCase();
            return mData.getChildren().get(position).getTitle();
        }
    };
}
