package com.example.smartbj.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.smartbj.R;
import com.example.smartbj.utils.LoggerUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 寳 on 2017-03-22.
 */

public class DetailActivity extends BaseActivity {
    private static final String TAG = "DetailActivity";
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.iv_size)
    ImageView mIvSize;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private int mSelectedSize = 2;
    private String[] itemsId = {"最小", "较小", "正常", "较大", "最大"};
    private WebSettings mWebSettings;

    @Override
    protected int getLayoutResID () {
        return R.layout.activity_detail;
    }

    @Override
    protected void init () {
        ButterKnife.bind(this, this);
        loadNewDetail();
    }

    private void loadNewDetail () {
        mProgressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        String url = intent.getStringExtra("newUrl");
        LoggerUtils.d(TAG, url);
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged (WebView view, int newProgress) {
            LoggerUtils.d(TAG, newProgress + "");
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            }
        }
    };

    @OnClick({R.id.iv_back, R.id.iv_share, R.id.iv_size})
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
            case R.id.iv_size:
                changeTextSize();
                break;
        }
    }

    private void changeTextSize () {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("字体大小");
        dialog.setSingleChoiceItems(itemsId, mSelectedSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        mWebSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;
                    case 1:
                        mWebSettings.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 2:
                        mWebSettings.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        mWebSettings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 4:
                        mWebSettings.setTextSize(WebSettings.TextSize.LARGEST);
                        break;
                }
                mSelectedSize = which;
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
