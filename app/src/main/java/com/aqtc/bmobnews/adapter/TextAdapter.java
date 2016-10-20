package com.aqtc.bmobnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqtc.bmobnews.R;

/**
 * Created by markzl on 2016/10/20.
 * email:1015653112@qq.com
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ListHolder> {

    private Context context;

    public TextAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_zhihu_daily,parent,false);
        return new ListHolder(view){

        };
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class ListHolder extends RecyclerView.ViewHolder{

        public ListHolder(View itemView) {
            super(itemView);
        }
    }
}
