package com.aqtc.bmobnews.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by markzl on 2016/9/29.
 */

public abstract class EasyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList mList;
    private EasyRecyclerViewHolder.OnItemClickListener onItemClickListener = null;
    private EasyRecyclerViewHolder.OnItemLongClickListener onItemLongClickListener = null;

    public EasyRecyclerViewAdapter() {
        this.mList = new ArrayList();
    }

    /**
     * Please return RecyclerView loading layout Id array
     * 请返回RecyclerView 加载的布局Id数组
     *
     * @return
     */
    public abstract int[] getItemLayouts();

    /**
     * Please write judgment logic when more layout
     * and not write when single layout
     * 如果是多布局的话，请写判断逻辑
     * 但布局可以不写
     *
     * @param position
     * @return
     */
    public abstract int getRecyclerViewItemType(int position);

    /**
     * butt joint the onBindViewHolder and
     * If you want to write logic in onBindViewHolder,
     * you can write here
     * 对接onBindViewHolder
     * onBindViewHolder里的逻辑写在这里
     *
     * @param viewHolder
     * @param position
     */
    public abstract void onBindRecyclerViewHolder(EasyRecyclerViewHolder viewHolder, int position);


    public <T> T getItem(int position) {

        return (T) mList.get(position);
    }

    public void setList(List list) {
        this.mList.clear();
        if (list == null) return;
        this.mList.addAll(list);
    }

    /**
     * 刷新数据并更新列表
     *
     * @param list
     */
    public void setRefreshData(List list) {

        this.mList.clear();
        if (list == null) return;
        this.mList.addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * 清空列表
     */
    public void clear() {
        mList.clear();
        //this.notifyDataSetChanged();
    }

    /**
     * list.remove(Object)对象
     *
     * @param o
     */
    public void remove(Object o) {
        mList.remove(o);
        this.notifyDataSetChanged();
    }

    /**
     * 添加Collection对象，
     *
     * @param list
     */
    public void addAll(Collection list) {
        if (list == null) return;
        this.mList.addAll(list);
        this.notifyDataSetChanged();
    }

    public List getList() {
        return this.mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0) return null;
        if (this.getItemLayouts() == null) return null;
        int[] layoutIds = this.getItemLayouts();
        if (layoutIds.length < 1) return null;
        int layoutId;
        if (layoutIds.length == 1) {
            layoutId = layoutIds[0];
        } else {
            layoutId = layoutIds[viewType];
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new EasyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        try {
            EasyRecyclerViewHolder easyRecyclerViewHolder = (EasyRecyclerViewHolder) holder;
            this.onBindRecyclerViewHolder(easyRecyclerViewHolder, position);
            easyRecyclerViewHolder.setOnItemClickListener(onItemClickListener, position);
            easyRecyclerViewHolder.setOnItemLongClickListener(onItemLongClickListener, position);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemViewType(int position) {
        return this.getRecyclerViewItemType(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 设置自定义单点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(EasyRecyclerViewHolder.OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按点击事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(EasyRecyclerViewHolder.OnItemLongClickListener onItemLongClickListener) {

        this.onItemLongClickListener = onItemLongClickListener;
    }


}
