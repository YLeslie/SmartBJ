package com.example.smartbj.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.smartbj.R;

import butterknife.BindView;

/**
 * Created by 寳 on 2017-03-18.
 */

public class MenuFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.lv_menu)
    ListView mLvMenu;
    private String[] mMenuTitles = {"新闻", "专题", "组图", "互动"};
    private int lastPostion;
    private OnMenuClickListener mListener;

    @Override
    protected int getLayoutResID () {
        return R.layout.menu_frame;
    }

    @Override
    protected void init () {
        mLvMenu.setAdapter(mAdapter);
        mLvMenu.setOnItemClickListener(this);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount () {
            return mMenuTitles.length;
        }

        @Override
        public Object getItem (int position) {
            return mMenuTitles[position];
        }

        @Override
        public long getItemId (int position) {
            return 0;
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_menu, null);
            }
            ((TextView) convertView).setText(mMenuTitles[position]);
            if (position == 0) {
                convertView.setEnabled(true);
            } else {
                convertView.setEnabled(false);
            }
            return convertView;
        }
    };

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
        if (position == lastPostion) {
            return;
        }
        parent.getChildAt(position).setEnabled(true);
        parent.getChildAt(lastPostion).setEnabled(false);
        if (mListener != null) {
            mListener.onMenuClick(position, lastPostion);
        }
        lastPostion = position;
    }

    public interface OnMenuClickListener {
        void onMenuClick (int postion, int lastPostion);
    }

    public void setOnMenuClickListener (OnMenuClickListener listener) {
        mListener = listener;
    }
}
