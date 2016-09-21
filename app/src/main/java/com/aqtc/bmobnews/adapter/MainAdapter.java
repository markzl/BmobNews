package com.aqtc.bmobnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.bean.BaseGankData;
import com.aqtc.bmobnews.bean.GankDaily;
import com.aqtc.bmobnews.constant.GankApi;
import com.aqtc.bmobnews.util.GlideUtils;

import java.util.ArrayList;

/**
 * Created by markzl on 2016/9/13.
 * email:1015653112@qq.com
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ListHodler> implements View.OnClickListener {

    private Context context;
    private ArrayList<GankDaily> gankDialies;

    public MainAdapter(Context context, ArrayList<GankDaily> gankDialies){
        this.context=context;
        this.gankDialies=gankDialies;
    }
    public void addData(ArrayList<GankDaily> gankDialies){

        this.gankDialies.addAll(gankDialies);
        this.notifyDataSetChanged();
    }
    @Override
    public ListHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_daily, parent, false);
        view.setOnClickListener(this);
        return new ListHodler(view);
    }

    @Override
    public void onBindViewHolder(ListHodler holder, int position) {
        holder.setData(position);
        holder.itemView.setTag(gankDialies.get(position).results);
    }

    @Override
    public int getItemCount() {
        Log.i("adapter",gankDialies.size()+"");
        return gankDialies.size();
    }

    @Override
    public void onClick(View v) {
        if(mListener!=null) {
            mListener.onItemClick(v, (GankDaily.DailyResults) v.getTag());
        }
    }

    class ListHodler extends RecyclerView.ViewHolder{
        private ImageView dailyIV;
        TextView dailyTitleTV;
        TextView dailyDateTV;
        TextView androidTagTV;
        TextView iOSTagTV;
        TextView jsTagTV;
        public ListHodler(View itemView) {
            super(itemView);
            dailyIV = (ImageView) itemView.findViewById(R.id.iv_daily_image);
            dailyDateTV = (TextView) itemView.findViewById(R.id.daily_date_tv);
            dailyTitleTV = (TextView) itemView.findViewById(R.id.tv_daily_title);

            androidTagTV = (TextView) itemView.findViewById(R.id.daily_android_tag_tv);
            iOSTagTV = (TextView) itemView.findViewById(R.id.daily_ios_tag_tv);
            jsTagTV = (TextView) itemView.findViewById(R.id.daily_js_tag_tv);
        }
        public void setData(int position){

            GankDaily dailyData = gankDialies.get(position);
            if (dailyData == null) return;
            if (dailyData.results.videoData != null && dailyData.results.videoData.size() > 0) {
                BaseGankData video = dailyData.results.videoData.get(0);
                dailyTitleTV.setText(video.desc.trim());
             /*   dailyDateTV.setText(
                        DateUtils.date2String(video.publishedAt.getTime(), Constant.DAILY_DATE_FORMAT));*/
            } else if (dailyData.results.welfareData != null &&
                    dailyData.results.welfareData.size() > 0) {
                BaseGankData welfare = dailyData.results.welfareData.get(0);
                dailyTitleTV.setText(welfare.desc.trim());
              /*  dailyDateTV.setText(DateUtils.date2String(welfare.publishedAt.getTime(),
                        Constant.DAILY_DATE_FORMAT));*/
            } else {
                dailyTitleTV.setText("这期没福利了，安心学习吧！");
                dailyDateTV.setText("");
            }

            // 图片
            if (dailyData.results.welfareData != null && dailyData.results.welfareData.size() > 0) {

                final BaseGankData welfare = dailyData.results.welfareData.get(0);
                GlideUtils.display(dailyIV, welfare.url);
            } else {
                GlideUtils.displayNative(dailyIV, R.mipmap.img_default_gray);
            }

        /*
         * 标签 ListView 和 RecyclerView 都要写else 因为复用问题
         * 忧伤
         */
            if (dailyData.category == null) {
                androidTagTV.setVisibility(View.GONE);
                iOSTagTV.setVisibility(View.GONE);
                jsTagTV.setVisibility(View.GONE);
            } else {
                if (dailyData.category.contains(GankApi.DATA_TYPE_ANDROID)) {
                    androidTagTV.setVisibility(View.VISIBLE);
                } else {
                    androidTagTV.setVisibility(View.GONE);
                }
                if (dailyData.category.contains(GankApi.DATA_TYPE_IOS)) {
                    iOSTagTV.setVisibility(View.VISIBLE);
                } else {
                    iOSTagTV.setVisibility(View.GONE);
                }
                if (dailyData.category.contains(GankApi.DATA_TYPE_JS)) {
                    jsTagTV.setVisibility(View.VISIBLE);
                } else {
                    jsTagTV.setVisibility(View.GONE);
                }
            }
        }
    }

    public  interface OnRecyclerViewItemClickListener{
        void onItemClick(View view,GankDaily.DailyResults data);
    }
    private OnRecyclerViewItemClickListener mListener;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener mListener){
        this.mListener=mListener;
    }
}
