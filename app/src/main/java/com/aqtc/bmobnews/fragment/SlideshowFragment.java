package com.aqtc.bmobnews.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqtc.bmobnews.R;

/**
 * Created by markzl on 2016/9/6.
 * email:1015653112@qq.com
 */
public class SlideshowFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_slide_show,null,false);
        //initData();
        return view;
    }

}
