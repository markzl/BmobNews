package com.aqtc.bmobnews.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by markzl on 2016/9/29.
 */

public class EasyRecyclerViewHolder extends RecyclerView.ViewHolder {

    /**
     * 用于保存findViewById加载过的view
     */
    private final SparseArray<View> views;
    private View convertView;

    public EasyRecyclerViewHolder(View convertView) {
        super(convertView);
        this.views = new SparseArray<View>();
        this.convertView = convertView;

    }

    /**
     * set the on item click listener
     *
     * @param listener
     * @param position
     */
    public void setOnItemClickListener(final EasyRecyclerViewHolder.OnItemClickListener listener, final int position) {

        if (listener == null) {
            this.itemView.setOnClickListener(null);
        } else {
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position);
                }
            });
        }
    }

    /**
     * set on item long click listener
     *
     * @param listener
     * @param position
     */
    public void setOnItemLongClickListener(final OnItemLongClickListener listener, final int position) {

        if (listener == null) {
            itemView.setOnLongClickListener(null);
        } else itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return listener.onItemLongClick(v, position);
            }
        });
    }
    //之前itemview 点击事件无法获取的原因找到了，setLongClickListener
    // 写成了 setClickListener
//    public void setOnItemLongClickListener(final OnItemLongClickListener listener,final int position) {
//
//        if(listener==null){
//            itemView.setOnClickListener(null);
//        }else{
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemLongClick(v,position);
//                }
//            });
//        }
//    }

    /**
     * Due to the findViewById perfoemance too low
     * The findViewById view will be cached, provide the findViewById next time the same view
     * ViewHolder model for the View
     * 由于findViewById 性能过低
     * findViewId过的view会被缓存下来，以供下次find相同viewId的时候
     * ViewHolder模式 查找子View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(int viewId) {

        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * the click listener callback
     */
    public interface OnItemClickListener {

        /**
         * on item click callback
         *
         * @param convertView
         * @param position
         */
        void onItemClick(View convertView, int position);
    }

    /**
     * the long click listener callback
     */
    public interface OnItemLongClickListener {

        /**
         * on item long click callback
         *
         * @param convertView
         * @param position
         * @return
         */
        boolean onItemLongClick(View convertView, int position);
    }
}
