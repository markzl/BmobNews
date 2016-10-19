package com.aqtc.bmobnews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.activity.base.BaseActivity;
import com.aqtc.bmobnews.adapter.DailyDetailAdapter;
import com.aqtc.bmobnews.bean.base.BaseGankData;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by markzl on 2016/9/14.
 * email:1015653112@qq.com
 */
public class DailyDetailActivity extends BaseActivity implements DailyDetailAdapter.OnCardItemClickListener {

    private final static String EXTRA_TITLE = "title";
    private final static String EXTRA_DETAIL = "detail";

    @BindView(R.id.rv_daily_detail)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DailyDetailAdapter detailAdapter;

    public static void startActivity(Context context, String title, ArrayList<ArrayList<BaseGankData>> detail) {

        Intent intent = new Intent(context, DailyDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("detail", detail);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_daily_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initToolBar() {
        if (getDetailTitile() != null) {
            toolbar.setTitle(getDetailTitile());
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    protected void initData() {
        this.detailAdapter = new DailyDetailAdapter(this);
        this.detailAdapter.setOnCardItemClickListener(this);
        this.mRecyclerView.setAdapter(this.detailAdapter);

        if (getDetail() == null) return;
        this.detailAdapter.setList(getDetail());
        this.detailAdapter.notifyDataSetChanged();
    }

    private ArrayList<ArrayList<BaseGankData>> getDetail() {

        Intent intent = getIntent();
        if (intent == null)
            return null;
        return (ArrayList<ArrayList<BaseGankData>>) intent.getSerializableExtra(EXTRA_DETAIL);
    }

    private String getDetailTitile() {
        Intent intent = getIntent();
        if (intent == null)
            return null;
        return intent.getStringExtra(EXTRA_TITLE);
    }

    @Override
    public void onCardItemOnClick(String urlType, String title, String url) {
        WebViewActivity.toURL(this,url,title,urlType);
    }

    @Override
    public void onWelfareOnClick(String url, String title, View v) {
        //WebViewActivity.toURL(this,url,title,v);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}


