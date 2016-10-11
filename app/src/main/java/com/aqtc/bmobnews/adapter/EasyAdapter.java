package com.aqtc.bmobnews.adapter;

import android.content.Context;
import android.view.View;

import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewAdapter;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewHolder;
import com.aqtc.bmobnews.data.constant.GankType;

/**
 * Created by markzl on 2016/10/2.
 */

public class EasyAdapter extends EasyRecyclerViewAdapter{

    public final static int LAYOUT_TYPE_DAILY = 0;
    public final static int LAYOUT_TYPE_TECHNOLOGY = 1;
    public final static int LAYOUT_TYPE_WELFARE = 2;

    private Context context;

    private int type;
    private View.OnClickListener listener;

    public EasyAdapter(Context context, int type){

        this.context=context;
        this.type=type;
    }

    @Override
    public int[] getItemLayouts() {
        return new int[]{};
    }

    @Override
    public int getRecyclerViewItemType(int position) {
        //android、ios、js、resource、app是一套布局
        switch (this.type){
            case GankType.daily:
                return LAYOUT_TYPE_DAILY;
            case GankType.android:
            case GankType.ios:
            case GankType.js:
            case GankType.resources:
            case GankType.app:
            return LAYOUT_TYPE_TECHNOLOGY;
            case GankType.welfare:
            return LAYOUT_TYPE_WELFARE;

        }
        return 0;
    }

    @Override
    public void onBindRecyclerViewHolder(EasyRecyclerViewHolder viewHolder, int position) {

    }

    /**
     * 加载每日干货类型数据(Gank)
     * @param easyRecyclerViewHolder
     * @param position
     */
    private void loadingDaily(EasyRecyclerViewHolder easyRecyclerViewHolder, int position){

    }

    /**
     * 加载技术类型数据(Android、ios、前端、拓展资源、App)
     * @param easyRecyclerViewHolder
     * @param postion
     */
    private void loadingTechnology(EasyRecyclerViewHolder easyRecyclerViewHolder, int postion){

    }

    /**
     * 加载每日福利数据(welfare)
     * @param easyRecyclerViewHolder
     * @param position
     */
    private void loadingWelfare(EasyRecyclerViewHolder easyRecyclerViewHolder, int position){

    }
    public int getType() {
        return type;
    }

    /**
     * 用于切换数据类型，从而切换数据源(url)
     *
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    public void setListener(View.OnClickListener listener){
        this.listener=listener;
    }

    public interface OnClickListener{

        void onClickPicture(String url,String title,View view);
    }
}
