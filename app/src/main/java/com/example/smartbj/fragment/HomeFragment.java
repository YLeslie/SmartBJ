package com.example.smartbj.fragment;

import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.smartbj.R;
import com.example.smartbj.utils.ToastUtils;
import com.example.smartbj.widget.GovaffairsTabPage;
import com.example.smartbj.widget.HomeTabPage;
import com.example.smartbj.widget.NewsTabPage;
import com.example.smartbj.widget.SettingTabPage;
import com.example.smartbj.widget.SmartServiceTabPage;
import com.example.smartbj.widget.TabPage;

import butterknife.BindView;

/**
 * Created by 寳 on 2017-03-18.
 */

public class HomeFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.tab_page_container)
    FrameLayout mTabPageContainer;
    @BindView(R.id.tab_group)
    RadioGroup mTabGroup;

    private SparseArray<TabPage> mTabCache = new SparseArray();
    private OnRadioCHeckedListener mListener;
    private TabPage mCurrentTabPage;

    @Override
    protected int getLayoutResID () {
        return R.layout.fragment_home;
    }

    @Override
    protected void init () {
        mTabGroup.setOnCheckedChangeListener(this);
        mTabGroup.check(R.id.tab_home);
    }

    @Override
    public void onCheckedChanged (RadioGroup group, int checkedId) {
        TabPage tabPage = null;
        if (mTabCache.get(checkedId) != null) {
            tabPage = mTabCache.get(checkedId);
        } else {
            tabPage = createTabPage(checkedId);
            ToastUtils.make(getContext(), "创建新的TabPage");
        }
        mCurrentTabPage = tabPage;
        mTabPageContainer.removeAllViews();
        mTabPageContainer.addView(tabPage);
        if (mListener != null) {
            mListener.onRadioChecked(checkedId);
        }
    }

    private TabPage createTabPage (int checkedId) {
        TabPage tabPage = null;
        switch (checkedId) {
            case R.id.tab_home:
                tabPage = new HomeTabPage(getContext());
                tabPage.hide();
                tabPage.setTitle("首页");
                break;
            case R.id.tab_news:
                tabPage = new NewsTabPage(getContext());
                tabPage.setTitle("新闻中心");
                break;
            case R.id.tab_smart_service:
                tabPage = new SmartServiceTabPage(getContext());
                tabPage.setTitle("智慧服务");
                break;
            case R.id.tab_govaffairs:
                tabPage = new GovaffairsTabPage(getContext());
                tabPage.setTitle("政务");
                break;
            case R.id.tab_setting:
                tabPage = new SettingTabPage(getContext());
                tabPage.hide();
                tabPage.setTitle("设置中心");
                break;
        }
        mTabCache.put(checkedId, tabPage);
        tabPage.setOnMenuStatusListener(new TabPage.OnMenuStatusListener() {
            @Override
            public void onMenuStatus () {
                mListener.onMenuStatusChange();
            }
        });
        return tabPage;
    }

    public void switchTo (int postion) {
        ToastUtils.make(getContext(), "跳转到了item" + postion);
        mCurrentTabPage.switchTabPage(postion);
    }

    public interface OnRadioCHeckedListener {
        void onRadioChecked (int checkedId);

        void onMenuStatusChange ();
    }

    public void setOnRadioCHeckedListener (OnRadioCHeckedListener listener) {
        mListener = listener;
    }
}
