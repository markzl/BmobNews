package com.aqtc.bmobnews.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.MainAdapter;
import com.aqtc.bmobnews.bean.GankDaily;
import com.aqtc.bmobnews.data.gank.GankType;
import com.aqtc.bmobnews.presenter.MainPresenter;
import com.aqtc.bmobnews.util.SnackbarUtil;
import com.aqtc.bmobnews.view.ImportView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by markzl on 2016/9/6.
 * email:1015653112@qq.com
 */
public class ImportFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener, MainAdapter.OnClickListener, ImportView {

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecylerView;


    private boolean isRefreshing = true;

    private MainPresenter mPresenter;
    private MainAdapter mAdapter;
    private int gankType;
    private List<GankDaily> dailyData_list;

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
        mRecylerView.setItemAnimator(new DefaultItemAnimator());
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(mContext));
        this.mRecylerView.addOnScrollListener(this.getRecyclerViewScrollListener());

    }


    @Override
    public void initData() {
        this.mPresenter = new MainPresenter();
        this.mPresenter.atthachView(this);
        //获取类型为每日推荐数据类型
        this.gankType = GankType.daily;
        this.mAdapter = new MainAdapter(mContext, this.gankType);
        this.mAdapter.setListener(this);
        mRecylerView.setAdapter(mAdapter);
        this.refreshData(this.gankType);

    }

    private RecyclerView.OnScrollListener getRecyclerViewScrollListener() {

        return new RecyclerView.OnScrollListener() {

            private boolean toLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if(layoutManager instanceof LinearLayoutManager){
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    //不滚动
                    if(newState == RecyclerView.SCROLL_STATE_IDLE){
                        //最后完成显示的item的position正好是最后一条数据的index
                        if(toLast&&manager.findLastCompletelyVisibleItemPosition()==manager.getItemCount()-1){
                            //加载更多数据
                        }
                    }
                }else if(layoutManager instanceof StaggeredGridLayoutManager){

                    StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
                    //不滚动
                    if(newState == RecyclerView.SCROLL_STATE_IDLE){
                       //StaggeredGridLayoutManager最底部可能有两个Item
                        //所以只要判断两个之后有一个正好是最后一条数据的index就OK
                        int[] bottom = manager.findLastCompletelyVisibleItemPositions(new int[2]);
                        int lastItemCount =manager.getItemCount()-1;
                        if(toLast&&(bottom[0]==lastItemCount||bottom[1]==lastItemCount)){
                            //加载更多数据
                        }
                    }
                }



            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //dy: y轴滑动方向 dx: x轴滑动方向 firstVisiable - lastVisiable
                if(dy>0){
                    this.toLast=true;
                    Log.i("xys","上拉加载更多");
                }else{
                    //停止滑动或者是下拉刷新数据
                    this.toLast=false;
                    Log.i("xys","下拉刷新数据");
                }
            }
        };
    }

    /**
     * 刷新或者是下拉刷新
     *
     * @param gankType
     */
    private void refreshData(int gankType) {

        this.refresh(true);
        this.mPresenter.setPage(1);
        //刷新数据
        this.mPresenter.getDaily(true, GankType.DONT_SWITCH);
    }

    @Override
    public void onRefresh() {
        refreshData(GankType.daily);
    }

    private void refresh(final boolean refresh) {
        if (mRefreshLayout == null) return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(refresh);
            }
        }, 1000);

    }


    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onClickPicture(String url, String title, View view) {

    }

    private int emptyCount = 0;

    /**
     * 查询每日干货成功是否刷新
     *
     * @param dailyData
     * @param refresh   是否刷新
     */
    @Override
    public void onGetDailySuccess(List<GankDaily> dailyData, boolean refresh) {

        if (refresh) {
            this.emptyCount = 0;
            this.mAdapter.setRefreshData(dailyData);
            SnackbarUtil.showMessage(mRefreshLayout, "已经是最新数据了");
        } else {
            this.mAdapter.addAll(dailyData);
        }
        if (dailyData.size() == 0)
            this.emptyCount++;
        this.refresh(false);
    }

    @Override
    public void onDestroy() {
        this.mPresenter.detachView();
        super.onDestroy();
    }
}
