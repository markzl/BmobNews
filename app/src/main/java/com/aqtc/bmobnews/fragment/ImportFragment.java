package com.aqtc.bmobnews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.activity.DailyDetailActivity;
import com.aqtc.bmobnews.adapter.MainAdapter;
import com.aqtc.bmobnews.bean.GankDaily;
import com.aqtc.bmobnews.constant.URLConstant;
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

    private MainAdapter mAdapter;
    private ArrayList<GankDaily> dailyData_list;

    private boolean isRefreshing=true;
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
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(isRefreshing);
            }
        });
        mRecylerView.setItemAnimator(new DefaultItemAnimator());
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(mContext));
        dailyData_list = new ArrayList<GankDaily>();
        mAdapter = new MainAdapter(mContext, dailyData_list);
        mRecylerView.setAdapter(mAdapter);
    }
    private int totalItemCount;
    private int lastVisiableItem;
  /*  private void setUpLoadData(){

        if(mRecylerView.getLayoutManager() instanceof LinearLayoutManager){

            final LinearLayoutManager layoutManager= (LinearLayoutManager) mRecylerView.getLayoutManager();
            mRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount=layoutManager.getItemCount();
                    lastVisiableItem=layoutManager.findLastVisibleItemPosition();
                    if(!isRefreshing&&lastVisiableItem+1>=totalItemCount){
                        Log.i("xys","上拉刷新");
                    }
                }
            });
        }
    }*/

    @Override
    public void initData() {
        //DataManange.getInstance().getDailyData();
        HttpUtil.getData(URLConstant.DAILY_URL, handler);
    }
    @Override
    public void onRefresh() {
        dailyData_list.clear();
        isRefreshing=!isRefreshing;
        mRefreshLayout.setRefreshing(false);
    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            Gson gson = new Gson();
            GankDaily dailyData = gson.fromJson(result, GankDaily.class);
            dailyData_list.add(dailyData);
            if (mAdapter != null) {
                mAdapter.addData(dailyData_list);
                isRefreshing=!isRefreshing;
                mRefreshLayout.setRefreshing(isRefreshing);
                mAdapter.setOnItemClickListener(new MainAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, GankDaily.DailyResults data) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("detail", data);
                        Intent intent = new Intent(mContext, DailyDetailActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
    };

}
