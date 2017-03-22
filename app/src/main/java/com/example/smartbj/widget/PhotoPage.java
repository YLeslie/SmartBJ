package com.example.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.smartbj.R;
import com.example.smartbj.bean.PhotoBean;
import com.example.smartbj.network.NetWorkListener;
import com.example.smartbj.network.NetWorkManager;
import com.example.smartbj.network.SmartBJRequest;
import com.example.smartbj.utils.LoggerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 寳 on 2017-03-22.
 */

public class PhotoPage extends LinearLayout {

    private static final String TAG = "PhotoPage";

    @BindView(R.id.lv_photo)
    ListView mLvPhoto;
    @BindView(R.id.gv_photo)
    GridView mGvPhoto;
    private String mData;
    private PhotoBean mPhotoBean;

    public PhotoPage (Context context) {
        this(context, null);
    }

    public PhotoPage (Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(getContext(), R.layout.view_photo_page, this);
        ButterKnife.bind(this, this);
        init();
    }

    private void init () {
        mLvPhoto.setAdapter(mAdapter);
        mGvPhoto.setAdapter(mAdapter);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount () {
            if (mPhotoBean != null) {
                return mPhotoBean.getData().getNews().size();
            }
            return 0;
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
        public View getView (int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_photo_list, null);
                holder = new ViewHolder();
                holder.mImageView = (NetworkImageView) convertView.findViewById(R.id.iv_photo);
                holder.mTextView = (TextView) convertView.findViewById(R.id.tv_photo);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PhotoBean.DataBean.NewsBean newsBean = mPhotoBean.getData().getNews().get(position);
            holder.mImageView.setImageUrl(newsBean.getListimage(), NetWorkManager.getImageLoader());
            holder.mTextView.setText(newsBean.getTitle());
            return convertView;
        }
    };

    public void changeStyle (boolean isList) {
        if (isList) {
            mLvPhoto.setVisibility(VISIBLE);
            mGvPhoto.setVisibility(View.GONE);
        } else {
            mLvPhoto.setVisibility(View.GONE);
            mGvPhoto.setVisibility(View.VISIBLE);
        }
    }

    private class ViewHolder {
        NetworkImageView mImageView;
        TextView mTextView;
    }

    public void setData (String data) {
        mData = data;
        String url = "http://10.0.2.2:8080/zhbj" + mData;
        SmartBJRequest<PhotoBean> request = new SmartBJRequest<>(PhotoBean.class, url, mListener);
        NetWorkManager.sendRequest(request);
    }

    private NetWorkListener<PhotoBean> mListener = new NetWorkListener<PhotoBean>() {
        @Override
        public void onResponse (PhotoBean response) {
            mPhotoBean = response;
            LoggerUtils.d(TAG, response.getData().getNews().get(0).getListimage());
            mAdapter.notifyDataSetChanged();
        }
    };
}
