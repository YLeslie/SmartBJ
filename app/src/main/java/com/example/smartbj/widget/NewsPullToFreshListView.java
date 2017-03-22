package com.example.smartbj.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.example.smartbj.R;
import com.example.smartbj.activity.DetailActivity;
import com.example.smartbj.bean.NewsListBean;
import com.example.smartbj.network.NetWorkListener;
import com.example.smartbj.network.NetWorkManager;
import com.example.smartbj.network.SmartBJRequest;
import com.example.smartbj.utils.LoggerUtils;
import com.example.smartbj.utils.SPTools;
import com.example.smartbj.utils.ToastUtils;
import com.itheima.pulltorefreshlib.PullToRefreshBase;
import com.itheima.pulltorefreshlib.PullToRefreshListView;
import com.leon.loopviewpagerlib.FunBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 寳 on 2017-03-19.
 */

public class NewsPullToFreshListView extends PullToRefreshListView {

    private static final String TAG = "NewsPullToFreshListView";
    private int[] imageResIds = {R.mipmap.icon_1, R.mipmap.icon_2, R.mipmap.icon_3, R.mipmap.icon_4, R.mipmap.icon_5};
    private String mUrl;
    private NewsListBean mNewsListBean;
    private FunBanner mFunBanner;
    private List<NewsListBean.DataBean.NewsBean> mNewsList;

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
        FunBanner.Builder builder = new FunBanner.Builder(getContext());
        mFunBanner = builder.setEnableAutoLoop(true)
                .setImageResIds(imageResIds)
                .setDotSelectedColor(Color.RED)
                .setHeightWidthRatio(0.5f)
                .setLoopInterval(3000)
                .setEnableAutoLoop(true)
                .setIndicatorBackgroundColor(R.color.indicator_bg)
                .build();
        getRefreshableView().addHeaderView(mFunBanner);
        setOnRefreshListener(mOnRefreshListener2);
        setOnItemClickListener(mOnItemClickListener);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
            position = position - 2;
            NewsListBean.DataBean.NewsBean newsBean = mNewsList.get(position);
            String key = String.valueOf(newsBean.getId());
            SPTools.setBoolean(getContext(), key, true);
            mAdapter.notifyDataSetChanged();

            startNewDetail(newsBean.getUrl());
        }
    };

    private void startNewDetail (String url) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("newUrl", url);
        getContext().startActivity(intent);
    }

    private OnRefreshListener2<ListView> mOnRefreshListener2 = new OnRefreshListener2<ListView>() {
        @Override
        public void onPullDownToRefresh (PullToRefreshBase<ListView> pullToRefreshBase) {
            SmartBJRequest<NewsListBean> request = new SmartBJRequest<>(NewsListBean.class, mUrl, mListener);
            NetWorkManager.sendRequest(request);
            LoggerUtils.d(TAG, "onPullDownToRefresh: 下拉刷新");
        }

        @Override
        public void onPullUpToRefresh (PullToRefreshBase<ListView> pullToRefreshBase) {
            loadMoreNews();
        }
    };

    private void loadMoreNews () {
        String more = mNewsListBean.getData().getMore();
        LoggerUtils.d(TAG, "loadMoreNews: 上拉加载更多  " + more.toString());

        if (more.length() > 0) {
            String moreUrl = "http://10.0.2.2:8080/zhbj" + more;
            SmartBJRequest request = new SmartBJRequest(NewsListBean.class, moreUrl, mMoreListener);
            NetWorkManager.sendRequest(request);
        } else {
            Toast.makeText(getContext(), "没有更多的数据", Toast.LENGTH_SHORT).show();
        }

    }

    private NetWorkListener<NewsListBean> mMoreListener = new NetWorkListener<NewsListBean>() {
        @Override
        public void onResponse (NewsListBean response) {
            List<NewsListBean.DataBean.NewsBean> moreNews = response.getData().getNews();
            mNewsList.addAll(moreNews);
            mAdapter.notifyDataSetChanged();
            onRefreshComplete();
        }
    };

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount () {
            if (mNewsListBean != null) {
                mNewsList = mNewsListBean.getData().getNews();
                return mNewsList.size();
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
        public View getView (int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_news_pull_fresh, null);
                holder = new ViewHolder();
                holder.imageView = (NetworkImageView) convertView.findViewById(R.id.iv_new_list_icon);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_new_title);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tv_new_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NewsListBean.DataBean.NewsBean newsBean = mNewsList.get(position);
            holder.tvTitle.setText(newsBean.getTitle());
            holder.tvTitle.setTextColor(Color.BLACK);
            holder.tvTime.setText(newsBean.getPubdate());
            holder.imageView.setImageUrl(newsBean.getListimage(), NetWorkManager.getImageLoader());

            if (SPTools.getBoolean(getContext(), String.valueOf(newsBean.getId()), false)) {
                holder.tvTitle.setTextColor(Color.GRAY);
                holder.tvTime.setTextColor(Color.GRAY);
            } else {
                holder.tvTitle.setTextColor(Color.BLACK);
                holder.tvTime.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    };

    public void setUrl (String url) {
        mUrl = url;
        SmartBJRequest<NewsListBean> request = new SmartBJRequest<>(NewsListBean.class, mUrl, mListener);
//        Volley.newRequestQueue(getContext()).add(request);
        NetWorkManager.sendRequest(request);
    }

    private NetWorkListener<NewsListBean> mListener = new NetWorkListener<NewsListBean>() {
        @Override
        public void onResponse (NewsListBean response) {
            LoggerUtils.d(TAG, response.toString());
            mNewsListBean = response;
            final List<String> topImageList = new ArrayList<>();
            final List<String> titleList = new ArrayList<>();
            List<NewsListBean.DataBean.TopnewsBean> topnewsBeanList = mNewsListBean.getData().getTopnews();
            for (int i = 0; i < topnewsBeanList.size(); i++) {
                topImageList.add(topnewsBeanList.get(i).getTopimage());
                titleList.add(topnewsBeanList.get(i).getTitle());
            }
            post(new Runnable() {
                @Override
                public void run () {
                    mFunBanner.setImageUrlsAndTitles(topImageList, titleList);
                }
            });
            LoggerUtils.d(TAG, "onResponse: 下拉刷新完成");
            mAdapter.notifyDataSetChanged();
            onRefreshComplete();
        }

        @Override
        public void onErrorResponse (VolleyError error) {
            ToastUtils.make(getContext(), "请求失败");
        }
    };

   /* Response.Listener<NewsListBean> mListener = new Response.Listener<NewsListBean>() {

        @Override
        public void onResponse (NewsListBean response) {
            LoggerUtils.d(TAG, response.toString());
            mNewsListBean = response;
        }
    };
    Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse (VolleyError error) {

        }
    };*/

    public class ViewHolder {
        NetworkImageView imageView;
        TextView tvTitle;
        TextView tvTime;
    }
}
