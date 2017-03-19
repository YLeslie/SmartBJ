package com.example.smartbj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.smartbj.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 寳 on 2017-03-18.
 */

public class TabPage extends RelativeLayout implements View.OnClickListener {
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tap_page_content)
    FrameLayout mTapPageContent;
    private OnMenuStatusListener mListener;
//    private boolean isClose = true;

    public TabPage (Context context) {
        this(context, null);
    }

    public TabPage (Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(getContext(), R.layout.view_tap_page, this);
        ButterKnife.bind(this, this);
        mImage.setOnClickListener(this);
    }

    public void hide () {
        mImage.setVisibility(View.GONE);
    }

    public void setTitle (String title) {
        mTvTitle.setText(title);
    }

    public void switchTabPage (int postion) {
    }

    @Override
    public void onClick (View v) {
        if (mListener != null) {
            mListener.onMenuStatus();
//            isClose = !isClose;
        }
    }

    public interface OnMenuStatusListener {
        void onMenuStatus ();
    }

    public void setOnMenuStatusListener (OnMenuStatusListener listener) {
        mListener = listener;
    }
}
