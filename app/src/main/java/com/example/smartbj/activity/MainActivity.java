package com.example.smartbj.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.smartbj.R;
import com.example.smartbj.fragment.HomeFragment;
import com.example.smartbj.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * Created by 寳 on 2017-03-17.
 */
public class MainActivity extends SlidingFragmentActivity {

    private HomeFragment mHomeFragment;
    private MenuFragment mMenuFragment;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置中间
        initContentView();
        //设置左边的menu
        initLeftMenu();
        //设置侧滑菜单
        initSlidingMenu();
    }

    private void initSlidingMenu () {
        final SlidingMenu sm = getSlidingMenu();
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setMode(SlidingMenu.TOUCHMODE_MARGIN);

        //回调监听
        mHomeFragment.setOnRadioCHeckedListener(new HomeFragment.OnRadioCHeckedListener() {
            //首页和设置中心不显示侧滑开关
            @Override
            public void onRadioChecked (int checkedId) {
                if (checkedId == R.id.tab_home || checkedId == R.id.tab_setting) {
                    sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                } else {
                    sm.setTouchModeAbove(SlidingMenu.LEFT);
                }
            }

            //点击侧滑开关切换侧滑菜单的状态
            @Override
            public void onMenuStatusChange () {
                sm.toggle();
            }
        });

        mMenuFragment.setOnMenuClickListener(new MenuFragment.OnMenuClickListener() {
            @Override
            public void onMenuClick (int postion, int lastPostion) {
                sm.toggle();
                if (postion != lastPostion & mHomeFragment != null) {
                    mHomeFragment.switchTo(postion);
                }
            }
        });
    }

    private void initContentView () {
        setContentView(R.layout.content_frame);
        mHomeFragment = new HomeFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mHomeFragment)
                .commit();
    }

    private void initLeftMenu () {
        setBehindContentView(R.layout.menu_frame);
        FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
        mMenuFragment = new MenuFragment();
        t.replace(R.id.menu_frame, mMenuFragment);
        t.commit();
    }
}
