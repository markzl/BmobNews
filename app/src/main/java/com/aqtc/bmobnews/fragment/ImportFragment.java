package com.aqtc.bmobnews.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.MainAdapter;
import com.aqtc.bmobnews.bean.GankDialy;
import com.aqtc.bmobnews.constant.URLConstant;
import com.aqtc.bmobnews.message.DataManange;
import com.aqtc.bmobnews.util.HttpUtil;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by markzl on 2016/9/6.
 * email:1015653112@qq.com
 */
public class ImportFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecylerView;

    GankDialy bean;
    MainAdapter mAdapter;

    @Override
    public View getInflaterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_import, null, false);
    }

    @Override
    public void initView() {

        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setProgressBackgroundColor(android.R.color.holo_red_light);

    }

    @Override
    public void initData() {
        DataManange.getData();
        ArrayList<GankDialy> datas = new ArrayList<GankDialy>();
        HttpUtil.getData(URLConstant.DAILY_URL, handler);
    }

    @Override
    public void onRefresh() {

    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Gson gson = new Gson();
            GankDialy data = gson.fromJson(result, GankDialy.class);
            ArrayList<GankDialy> datas = new ArrayList<GankDialy>();
            datas.add(data);
            for (String str : datas.get(0).category) {
                Log.i("xyyx", str);
            }

            if (mAdapter == null) {
                mRecylerView.setLayoutManager(new LinearLayoutManager(mContext));
                mAdapter = new MainAdapter(mContext, datas);
                mRecylerView.setAdapter(mAdapter);
            }else {
                mAdapter.addData(datas);
            }

        }
    };

}
