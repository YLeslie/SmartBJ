package com.example.smartbj.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by 寳 on 2017-03-18.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResID(), null);
        ButterKnife.bind(this, root);
        init();
        return root;
    }
    protected void init () {
    }

    protected abstract int getLayoutResID ();
}
