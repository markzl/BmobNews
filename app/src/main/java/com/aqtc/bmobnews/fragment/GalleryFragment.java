package com.aqtc.bmobnews.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.StaggeredGridAdapter;
import com.aqtc.bmobnews.bean.ImageBean;
import com.aqtc.bmobnews.constant.URLConstant;
import com.aqtc.bmobnews.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by markzl on 2016/9/6.
 * email:1015653112@qq.com
 */
public class GalleryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private String tag = "GalleryFragment";

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecylerView;
    private Context mContext;
    private ImageBean bean = new ImageBean();
    StaggeredGridAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_gallery, null, false);
        ButterKnife.bind(this, view);
        HttpUtil.getData(URLConstant.GALLERY_URL, handler);
        initView();
        return view;
    }

    private void initView() {
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setProgressBackgroundColor(android.R.color.holo_red_light);

    }

    int page=1;

    @Override
    public void onRefresh() {

        page++;
        HttpUtil.getData(URLConstant.getGalleryUrl(page),handler);
        Log.i("xys","+++++++"+URLConstant.getGalleryUrl(page));
        mRefreshLayout.setRefreshing(false);

    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            try {
                JSONObject json = new JSONObject(result);
                Boolean error = json.getBoolean("error");
                JSONArray results_array = new JSONArray(json.getString("results"));
                bean.error = error;
                bean.results = new ArrayList<ImageBean.Results>();
                for (int i = 0; i < results_array.length(); i++) {
                    JSONObject jsonObj = results_array.getJSONObject(i);
                    ImageBean.Results results1 = bean.new Results();
                    results1.id = jsonObj.getString("_id");
                    results1.createdAt = jsonObj.getString("createdAt");
                    results1.desc = jsonObj.getString("desc");
                    results1.publishedAt = jsonObj.getString("publishedAt");
                    results1.source = jsonObj.getString("source");
                    results1.type = jsonObj.getString("type");
                    results1.url = jsonObj.getString("url");
                    results1.used = jsonObj.getBoolean("used");
                    results1.who = jsonObj.getString("who");
                    bean.results.add(results1);
                }

                if (mAdapter == null) {
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
                    mRecylerView.setLayoutManager(layoutManager);
                    mAdapter = new StaggeredGridAdapter(mContext, bean.results);
                    mRecylerView.setAdapter(mAdapter);
                }
                mAdapter.addData(bean.results);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
