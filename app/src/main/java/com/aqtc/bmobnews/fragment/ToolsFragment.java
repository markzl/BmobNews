package com.aqtc.bmobnews.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.ZhiHuAdapter;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDaily;
import com.aqtc.bmobnews.data.zhihu.ZhiHuType;
import com.aqtc.bmobnews.presenter.ZhiHuPresenter;
import com.aqtc.bmobnews.view.ZhiHuView;

import butterknife.BindView;

/**
 * Created by markzl on 2016/9/6.
 * email:1015653112@qq.com
 */
public class ToolsFragment extends BaseFragment implements ZhiHuView{

    private ZhiHuPresenter mPresenter;
    private ZhiHuAdapter mAdapter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    public View getInflaterView(LayoutInflater inflater) {
        View view =inflater.inflate(R.layout.fragment_tools,null,false);
        return view;
    }

    @Override
    public void initView() {
        mAdapter = new ZhiHuAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter);
        this.mPresenter = new ZhiHuPresenter();
        mPresenter.atthachView(this);
        mPresenter.getDailyData(ZhiHuType.DATA_TYPE_NEWS);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onDestroy() {

        this.mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void getDailyData(ZhiHuDaily zhiHuDailies) {
        mAdapter.setList(zhiHuDailies.stories);
        mAdapter.notifyDataSetChanged();
    }
}
