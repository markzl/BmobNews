package com.aqtc.bmobnews.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.MainAdapter;
import com.aqtc.bmobnews.bean.base.BaseGankData;
import com.aqtc.bmobnews.data.gank.GankApi;
import com.aqtc.bmobnews.data.gank.GankType;
import com.aqtc.bmobnews.presenter.GalleryPresenter;
import com.aqtc.bmobnews.view.GalleryView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by markzl on 2016/9/6.
 * email:1015653112@qq.com
 */
public class GalleryFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, GalleryView, MainAdapter.OnClickListener {

    private static final String tag = "GalleryFragment";

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler)
    RecyclerView mRecylerView;

    // private StaggeredGridAdapter mAdapter;
    private MainAdapter mAdapter;
    private int page = 1;

    private GalleryPresenter mPresenter;
    private int gankType;

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
        // mRefreshLayout.setProgressBackgroundColor(android.R.color.holo_red_light);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        mRecylerView.setLayoutManager(layoutManager);
        mRecylerView.addOnScrollListener(this.getRecyclerViewScrollListener());
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
    @Override
    public void initData() {

        this.mPresenter = new GalleryPresenter();
        this.mPresenter.atthachView(this);
        this.gankType = GankType.welfare;
        this.mAdapter = new MainAdapter(mContext, gankType);
        this.mAdapter.setListener(this);
        this.mRecylerView.setAdapter(mAdapter);
        this.refreshData();
    }

    private void refreshData() {
        refresh(true);
        page = 1;
        this.mPresenter.getData(GankApi.DATA_TYPE_WELFARE, GankApi.DEFAULT_DATA_SIZE, page);
    }

    private void refresh(boolean refresh) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(refresh);
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    public void onGetGalleryDataSuccess(ArrayList<BaseGankData> baseGankDatas) {
        this.mAdapter.addAll(baseGankDatas);
        this.mAdapter.notifyDataSetChanged();
        refresh(false);
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onClickPicture(String url, String title, View view) {

    }
}
