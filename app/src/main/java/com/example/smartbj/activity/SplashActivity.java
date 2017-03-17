package com.example.smartbj.activity;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartbj.R;
import com.example.smartbj.utils.SPTools;

import butterknife.BindView;

/**
 * Created by 寳 on 2017-03-17.
 */

public class SplashActivity extends BaseActivity implements Animation.AnimationListener {
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    private Context mContext;
    private static final String IS_FIRST_IN = "isFirstIn";

    @Override
    protected int getLayoutResID () {
        return R.layout.activity_splash;
    }

    @Override
    protected void init () {
        mContext = this;
        startAnimation();
    }

    private void startAnimation () {
        AnimationSet set = new AnimationSet(false);
        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(ra);
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(sa);
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        set.addAnimation(aa);
        set.setDuration(1000);
        mIvSplash.setAnimation(set);
        set.start();

        set.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart (Animation animation) {
    }

    @Override
    public void onAnimationEnd (Animation animation) {
        boolean isFirstIn = SPTools.getBoolean(mContext, IS_FIRST_IN);
        if (isFirstIn) {
            startActivity(TutorialActivity.class);
            Toast.makeText(mContext, "第一次进入", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(HomeActivity.class);
        }
    }

    @Override
    public void onAnimationRepeat (Animation animation) {
    }

    @Override
    protected void startActivity (Class activity) {
        super.startActivity(activity);
    }
}
