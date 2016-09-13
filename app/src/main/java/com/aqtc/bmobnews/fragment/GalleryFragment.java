package com.aqtc.bmobnews.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.StaggeredGridAdapter;
import com.aqtc.bmobnews.bean.GankData;
import com.aqtc.bmobnews.constant.URLConstant;
import com.aqtc.bmobnews.util.HttpUtil;
import com.google.gson.Gson;
import butterknife.BindView;

/**
 * Created by markzl on 2016/9/6.
 * email:1015653112@qq.com
 */
public class GalleryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private String tag = "GalleryFragment";

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecylerView;

    private StaggeredGridAdapter mAdapter;
    private int page = 1;

    @Override
    public View getInflaterView(LayoutInflater inflater) {

        return inflater.inflate(R.layout.fragment_gallery, null, false);
    }

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
        HttpUtil.getData(URLConstant.GALLERY_URL, handler);
    }


    @Override
    public void onRefresh() {
        page++;
        HttpUtil.getData(URLConstant.getGalleryUrl(page), handler);
        Log.i("xys", "+++++++" + URLConstant.getGalleryUrl(page));
        mRefreshLayout.setRefreshing(false);
    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Gson gson = new Gson();
            GankData data = gson.fromJson(result, GankData.class);
            if (mAdapter == null) {
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
                mRecylerView.setLayoutManager(layoutManager);
                mAdapter = new StaggeredGridAdapter(mContext, data.results);
                mRecylerView.setAdapter(mAdapter);
            }else {
                mAdapter.addData(data.results);
            }

        }
    };

}
