package com.aqtc.bmobnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.bean.ImageBean;
import com.aqtc.bmobnews.util.GlideUtils;
import com.aqtc.bmobnews.view.RatioImageView;

import java.util.Collections;
import java.util.List;

/**
 * Created by mark_zl on 2016/7/12.
 */
public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.ListHolder> {

    private Context context;
    private List<ImageBean.Results> results;

    public StaggeredGridAdapter(Context context, List<ImageBean.Results> results) {
        this.context = context;
        this.results = results;
    }
    public void addData(List<ImageBean.Results> result_list){
       /* for (ImageBean.Results result:result_list) {
            this.results.add(result);
        }*/
        this.results.addAll(result_list);
        Collections.reverse(this.results);
        this.notifyDataSetChanged();
    }
    @Override
    public StaggeredGridAdapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery, parent, false);

        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(StaggeredGridAdapter.ListHolder holder, int position) {
        holder.setData(position);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {

        private RatioImageView pic;
        private TextView name;

        public ListHolder(View itemView) {
            super(itemView);
            pic = (RatioImageView) itemView.findViewById(R.id.card_item_pic);
            name = (TextView) itemView.findViewById(R.id.tv_name);
        }

        public void setData(int position) {

            if (position % 2 == 0) {
                pic.setImageRatio(0.7f);
            } else {
                pic.setImageRatio(0.6f);
            }
            String pic_url = results.get(position).url;

            String pic_desc = results.get(position).desc;
            name.setText(pic_desc);
            // 图片
            if (!TextUtils.isEmpty(pic_url)) {
                GlideUtils.display(pic,pic_url);
            } else {
               GlideUtils.displayNative(pic,R.mipmap.ic_launcher);
            }

        }
    }
}
