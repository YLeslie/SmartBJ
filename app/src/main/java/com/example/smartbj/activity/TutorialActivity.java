package com.example.smartbj.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.smartbj.R;
import com.example.smartbj.utils.SPTools;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;

/**
 * Created by 寳 on 2017-03-17.
 */
public class TutorialActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private static final String IS_FIRST_IN = "isFirstIn";
    @BindView(R.id.vp_tutorial)
    ViewPager mVpTutorial;
    int[] mImages = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    @BindView(R.id.btn_tutorial)
    Button mButton;
    @BindView(R.id.indicator)
    CirclePageIndicator mIndicator;


    @Override
    protected int getLayoutResID () {
        return R.layout.activity_tutorial;
    }

    @Override
    protected void init () {
        mVpTutorial.setAdapter(mAdapter);
        mVpTutorial.addOnPageChangeListener(this);
        mIndicator.setViewPager(mVpTutorial);
        mButton.setOnClickListener(this);
    }

    private PagerAdapter mAdapter = new PagerAdapter() {
        @Override
        public int getCount () {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject (View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem (ViewGroup container, int position) {

            ImageView mPage = new ImageView(TutorialActivity.this);
            mPage.setImageResource(mImages[position]);
            mPage.setScaleType(ImageView.ScaleType.FIT_XY);
            mVpTutorial.addView(mPage);
            return mPage;
        }

        @Override
        public void destroyItem (ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    @Override
    public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected (int position) {
        if (position == mImages.length - 1) {
            mButton.setVisibility(View.VISIBLE);
        } else {
            mButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged (int state) {

    }

    @Override
    public void onClick (View v) {
        startActivity(HomeActivity.class);
        SPTools.setBoolean(TutorialActivity.this, IS_FIRST_IN, false);
    }

    @Override
    protected void startActivity (Class activity) {
        super.startActivity(activity);
    }
}
