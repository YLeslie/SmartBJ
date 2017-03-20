package com.example.smartbj.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.smartbj.R;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by 寳 on 2017-03-19.
 */

public class NewsPage extends LinearLayout {

    private static final String[] CONTENT = new String[]{"Recent", "Artists", "Albums", "Songs", "Playlists", "Genres", "Itheima", "Chuanzhi"};

    public NewsPage (Context context) {
        this(context, null);
    }

    public NewsPage (Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init () {
        View.inflate(getContext(), R.layout.view_news_page, this);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(mAdapter);
        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }

    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount () {
            return CONTENT.length;
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
            container.addView(newsPullToFreshListView);
            return newsPullToFreshListView;
        }

        @Override
        public void destroyItem (ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle (int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }
    };
}
