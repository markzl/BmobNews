package com.aqtc.bmobnews.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.bean.GankDialy;
import com.aqtc.bmobnews.constant.URLConstant;
import com.aqtc.bmobnews.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    @Override
    public View getInflaterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_import,null,false);
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
        bean=new GankDialy();
        HttpUtil.getData(URLConstant.HISTORY_URL,handler);
    }

    @Override
    public void onRefresh() {

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
                bean.results = new ArrayList<GankDialy.Results>();
                for (int i = 0; i < results_array.length(); i++) {
                    JSONObject jsonObj = results_array.getJSONObject(i);
                    GankDialy.Results results1 = bean.new Results();
                    results1.id = jsonObj.getString("_id");
                    results1.content=jsonObj.getString("content");
                    results1.publishedAt=jsonObj.getString("publishedAt");
                    results1.title=jsonObj.getString("title");
                    bean.results.add(results1);
                    Log.i("xys",results1.title);
                }

              /*  if (mAdapter == null) {
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
                    mRecylerView.setLayoutManager(layoutManager);
                    mAdapter = new StaggeredGridAdapter(mContext, bean.results);
                    mRecylerView.setAdapter(mAdapter);
                }
                mAdapter.addData(bean.results);*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
