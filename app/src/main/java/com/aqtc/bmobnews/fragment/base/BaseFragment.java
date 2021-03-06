package com.aqtc.bmobnews.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by markzl on 2016/9/7.
 * email:1015653112@qq.com
 */
public abstract class BaseFragment extends Fragment {

    public Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=getInflaterView(inflater);
        ButterKnife.bind(this,view);
        mContext=inflater.getContext();
        initView();
        initData();
        return view;
    }
    public abstract View getInflaterView(LayoutInflater inflater);

    public abstract void initView();

    public abstract void initData();
}
