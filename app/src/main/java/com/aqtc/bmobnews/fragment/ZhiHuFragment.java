package com.aqtc.bmobnews.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.activity.ZhiHuDetailActivity;
import com.aqtc.bmobnews.adapter.ZhiHuAdapter;
import com.aqtc.bmobnews.adapter.base.EasyBorderDividerItemDecoration;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewHolder;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDaily;
import com.aqtc.bmobnews.bean.zhihu.base.BaseZhiHuData;
import com.aqtc.bmobnews.data.zhihu.ZhiHuType;
import com.aqtc.bmobnews.fragment.base.BaseFragment;
import com.aqtc.bmobnews.presenter.ZhiHuPresenter;
import com.aqtc.bmobnews.util.SnackbarUtil;
import com.aqtc.bmobnews.view.ZhiHuView;

import butterknife.BindView;

/**
 * Created by markzl on 2016/9/6.
 * email:1015653112@qq.com
 */
public class ZhiHuFragment extends BaseFragment implements ZhiHuView, SwipeRefreshLayout.OnRefreshListener, EasyRecyclerViewHolder.OnItemClickListener {

    private ZhiHuPresenter mPresenter;
    private ZhiHuAdapter mAdapter;

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String mCurrentTime;
    private Boolean mIsRefreshing = false;
    private EasyBorderDividerItemDecoration mDataDecoration;

    @Override
    public View getInflaterView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_tools, null, false);
        return view;
    }

    @Override
    public void initView() {

        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        this.mDataDecoration = new EasyBorderDividerItemDecoration(
                this.getResources().getDimensionPixelOffset(R.dimen.data_border_divider_height),
                this.getResources().getDimensionPixelOffset(R.dimen.data_border_padding_infra_spans));
        this.mRecyclerView.addItemDecoration(this.mDataDecoration);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        this.mRecyclerView.addOnScrollListener(this.getScrollListener());
        this.mAdapter = new ZhiHuAdapter();
        this.mAdapter.setOnItemClickListener(this);

    }

    @Override
    public void initData() {

        this.mRecyclerView.setAdapter(mAdapter);
        this.mPresenter = new ZhiHuPresenter();
        this.mPresenter.atthachView(this);
        this.refreshData();

    }

    @Override
    public void onRefresh() {
        this.refreshData();
    }

    private RecyclerView.OnScrollListener getScrollListener() {
        return new RecyclerView.OnScrollListener() {

            private boolean toLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (toLast && manager.findLastCompletelyVisibleItemPosition() == manager.getItemCount() - 1) {
                            //加载更多
                            ZhiHuFragment.this.loadMoreData();
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

    /**
     * 是否显示进度条
     *
     * @param isRefresh
     */
    private void showProgress(boolean isRefresh) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ZhiHuFragment.this.mSwipeRefreshLayout.setRefreshing(isRefresh);
            }
        }, 500);
    }

    /**
     * 刷新当前数据
     */
    private void refreshData() {
        if (!mIsRefreshing) {
            this.mIsRefreshing = !mIsRefreshing;
            this.showProgress(mIsRefreshing);
            this.mPresenter.getDailyData(ZhiHuType.DATA_TYPE_NEWS);
        }
    }

    /**
     * 加载更多数据
     * 获取
     */
    private void loadMoreData() {
        if (!mIsRefreshing) {
            this.mIsRefreshing = !mIsRefreshing;
            this.showProgress(mIsRefreshing);
            this.mPresenter.getMoreDailyData(ZhiHuType.DATA_TYPE_NEWS, mCurrentTime);
        }

    }


    @Override
    public void onGetDailyDataSuccess(ZhiHuDaily zhiHuDaily) {
        if (zhiHuDaily.stories.size() > 0) {
            mCurrentTime = zhiHuDaily.date;
            mIsRefreshing = !mIsRefreshing;
            showProgress(mIsRefreshing);
            mAdapter.setRefreshData(zhiHuDaily.stories);
            SnackbarUtil.showMessage(this.mRecyclerView, "刷新数据成功T-T");
        }
    }

    @Override
    public void onGetMoreDailyDataSuccess(ZhiHuDaily zhiHuDaily) {
        if (zhiHuDaily.stories.size() > 0) {
            mCurrentTime = zhiHuDaily.date;
            mIsRefreshing = !mIsRefreshing;
            showProgress(mIsRefreshing);
            mAdapter.addAll(zhiHuDaily.stories);
        }
    }


    @Override
    public void onItemClick(View convertView, int position) {
        BaseZhiHuData zhiHuData = mAdapter.getItem(position);
        long id = zhiHuData.id;
        ZhiHuDetailActivity.startActivity(this.mContext,id,zhiHuData.title);
    }



    @Override
    public void onFailure(Throwable e) {
        SnackbarUtil.showMessage(this.mRecyclerView, "获取数据失败T~T");
    }

    @Override
    public void onDestroy() {

        this.mPresenter.detachView();
        super.onDestroy();
    }


}
