package com.aqtc.bmobnews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.activity.WebViewActivity;
import com.aqtc.bmobnews.adapter.MainAdapter;
import com.aqtc.bmobnews.adapter.base.EasyBorderDividerItemDecoration;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewHolder;
import com.aqtc.bmobnews.bean.gank.base.BaseGankData;
import com.aqtc.bmobnews.data.gank.GankTypeDict;
import com.aqtc.bmobnews.fragment.base.BaseFragment;
import com.aqtc.bmobnews.presenter.GankPresenter;
import com.aqtc.bmobnews.view.CategoryView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * author: markzl
 * time: 2016/10/25 11:28
 * email: 1015653112@qq.com
 */

public class CategorySimpleFragment extends BaseFragment implements CategoryView, SwipeRefreshLayout.OnRefreshListener, EasyRecyclerViewHolder.OnItemClickListener {

    private final static String EXTRA_TYPE = "gank_type";
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private boolean isRefreshStatus = false;
    private GankPresenter mPresenter;
    private MainAdapter mAdapter;
    private int gankType;
    private EasyBorderDividerItemDecoration dataDecoration;
    private int page_index = 1;

    public static CategorySimpleFragment newInstance(String type) {
        CategorySimpleFragment mFragment = new CategorySimpleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TYPE, type);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public View getInflaterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_gank, null, false);
    }

    @Override
    public void initView() {

        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        this.dataDecoration = new EasyBorderDividerItemDecoration(
                this.getResources().getDimensionPixelOffset(R.dimen.data_border_divider_height),
                this.getResources().getDimensionPixelOffset(R.dimen.data_border_padding_infra_spans));
        mRecyclerView.addItemDecoration(this.dataDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRefreshLayout.setOnRefreshListener(this);
        this.mRecyclerView.addOnScrollListener(this.getRecyclerViewScrollListener());
    }

    /**
     * 获取RecyclerView的滑动监听器
     *
     * @return OnScrollListener
     */
    private RecyclerView.OnScrollListener getRecyclerViewScrollListener() {

        return new RecyclerView.OnScrollListener() {

            private boolean toLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    //不滚动
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //最后完成显示的item的position正好是最后一条数据的index
                        if (toLast && manager.findLastCompletelyVisibleItemPosition() == manager.getItemCount() - 1) {
                            //加载更多数据
                            loadMoreRequest();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                this.toLast = dy > 0;
            }
        };
    }

    @Override
    public void initData() {
        String type = getArguments().getString(EXTRA_TYPE);
        gankType = GankTypeDict.urlType2TypeDict.get(type);
        this.mPresenter = new GankPresenter();
        this.mPresenter.atthachView(this);
        this.mAdapter = new MainAdapter(mContext, this.gankType);
        this.mAdapter.setOnItemClickListener(this);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.refreshData();
    }

    /**
     * 刷新或者是下拉刷新
     */
    private void refreshData() {
        if (!isRefreshStatus) {
            isRefreshStatus = !isRefreshStatus;
            this.refresh(isRefreshStatus);
            this.mPresenter.getCategoryData(GankTypeDict.type2UrlTypeDict.get(gankType), 1);
        }
    }

    private void loadMoreRequest() {
        if (!isRefreshStatus) {
            page_index++;
            isRefreshStatus = !isRefreshStatus;
            this.refresh(isRefreshStatus);
            this.mPresenter.getCategoryData(GankTypeDict.type2UrlTypeDict.get(gankType), page_index);
        }
    }

    private void refresh(final boolean refresh) {

        if (mRefreshLayout == null) return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(refresh);
            }
        }, 500);

    }

    @Override
    public void onRefresh() {
        this.refreshData();
    }

    @Override
    public void onGetCategoryDataSuccess(ArrayList<BaseGankData> gankDatas, int page) {

        if (page == 1) {
            this.mAdapter.setRefreshData(gankDatas);
        } else {
            this.mAdapter.addAll(gankDatas);
        }
        isRefreshStatus = !isRefreshStatus;
        this.refresh(isRefreshStatus);
    }

    @Override
    public void onItemClick(View convertView, int position) {
        Object o = this.mAdapter.getItem(position);
        if(o instanceof BaseGankData){
            BaseGankData baseGankData = (BaseGankData) o;
            WebViewActivity.toURL(mContext,baseGankData.url,baseGankData.desc,baseGankData.type);
        }
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onDestroy() {
        this.mPresenter.detachView();
        super.onDestroy();
    }

}
