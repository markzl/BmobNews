package com.aqtc.bmobnews.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.TextAdapter;

import butterknife.BindView;

/**
 * Created by markzl on 2016/9/6.
 * email:1015653112@qq.com
 */
public class SlideshowFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    public View getInflaterView(LayoutInflater inflater) {
        View view =inflater.inflate(R.layout.fragment_slide_show,null,false);
        return view;
    }

    @Override
    public void initView() {
        TextAdapter adapter = new TextAdapter(mContext);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

}
