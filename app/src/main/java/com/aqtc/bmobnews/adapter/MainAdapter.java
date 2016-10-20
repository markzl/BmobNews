package com.aqtc.bmobnews.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewAdapter;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewHolder;
import com.aqtc.bmobnews.bean.gank.base.BaseGankData;
import com.aqtc.bmobnews.bean.gank.GankDaily;
import com.aqtc.bmobnews.data.constant.CodeConstant;
import com.aqtc.bmobnews.data.gank.GankApi;
import com.aqtc.bmobnews.data.gank.GankType;
import com.aqtc.bmobnews.util.DateUtils;
import com.aqtc.bmobnews.util.GlideUtils;
import com.aqtc.bmobnews.widget.RatioImageView;

/**
 * Created by markzl on 2016/10/2.
 */

public class MainAdapter extends EasyRecyclerViewAdapter {

    private final static int LAYOUT_TYPE_DAILY = 0;
    private final static int LAYOUT_TYPE_WELFARE = 1;
    private final static int LAYOUT_TYPE_TECHNOLOGY = 2;

    private Context context;

    private int type;
    private OnClickListener listener;

    public MainAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public int[] getItemLayouts() {
        return new int[]{R.layout.item_gank_daily, R.layout.item_gank_welfare};
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

    @Override
    public int getRecyclerViewItemType(int position) {
        //android、ios、js、resource、app是一套布局
        switch (this.type) {
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

        int layoutType = this.getRecyclerViewItemType(position);
        switch (layoutType) {
            case LAYOUT_TYPE_DAILY:
                this.loadingDaily(viewHolder, position);
                break;
            case LAYOUT_TYPE_WELFARE:
                this.loadingWelfare(viewHolder, position);
                break;
            case LAYOUT_TYPE_TECHNOLOGY:
                this.loadingTechnology(viewHolder, position);
                break;


        }
    }

    /**
     * 加载每日干货类型数据(Gank)
     *
     * @param easyRecyclerViewHolder
     * @param position
     */
    private void loadingDaily(EasyRecyclerViewHolder easyRecyclerViewHolder, int position) {


        GankDaily dailyData = this.getItem(position);
        if (dailyData == null) return;
        ImageView dailyIV = easyRecyclerViewHolder.findViewById(R.id.iv_daily_image);
        TextView dailyDateTV = easyRecyclerViewHolder.findViewById(R.id.daily_date_tv);
        TextView dailyTitleTV = easyRecyclerViewHolder.findViewById(R.id.tv_daily_title);

        TextView androidTagTV = easyRecyclerViewHolder.findViewById(R.id.daily_android_tag_tv);
        TextView iOSTagTV = easyRecyclerViewHolder.findViewById(R.id.daily_ios_tag_tv);
        TextView jsTagTV = easyRecyclerViewHolder.findViewById(R.id.daily_js_tag_tv);

        //标题 和 日期
        //如果没有视频的title和date就找福利的title和date，实在没有就完！
        if (dailyData.results.videoData != null && dailyData.results.videoData.size() > 0) {
            BaseGankData video = dailyData.results.videoData.get(0);
            dailyTitleTV.setText(video.desc.trim());
            dailyDateTV.setText(
                    DateUtils.date2String(video.publishedAt.getTime(), CodeConstant.DAILY_DATE_FORMAT));
        } else if (dailyData.results.welfareData != null && dailyData.results.welfareData.size() > 0) {
            BaseGankData welfare = dailyData.results.welfareData.get(0);
            dailyTitleTV.setText(welfare.desc.trim());
            dailyDateTV.setText(DateUtils.date2String(welfare.publishedAt.getTime(), CodeConstant.DAILY_DATE_FORMAT));
        } else {
            dailyTitleTV.setText("这期没福利了，安心学习吧！");
            dailyDateTV.setText("");
        }

        // 图片
        if (dailyData.results.welfareData != null && dailyData.results.welfareData.size() > 0) {
            final BaseGankData welfare = dailyData.results.welfareData.get(0);
            GlideUtils.display(dailyIV, welfare.url);
            dailyIV.setOnClickListener(v -> {
                if (MainAdapter.this.listener != null) {
                    MainAdapter.this.listener.onClickPicture(welfare.url, welfare.desc, v);
                }
            });
        } else {
            GlideUtils.displayNative(dailyIV, R.mipmap.img_default_gray);
        }

        if (dailyData.category == null) {
            androidTagTV.setVisibility(View.GONE);
            iOSTagTV.setVisibility(View.GONE);
            jsTagTV.setVisibility(View.GONE);
            return;
        }
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

    /**
     * 加载每日福利数据(welfare)
     *
     * @param easyRecyclerViewHolder
     * @param position
     */
    private void loadingWelfare(EasyRecyclerViewHolder easyRecyclerViewHolder, int position) {

        BaseGankData baseGankData = this.getItem(position);
        if (baseGankData == null) return;
        RatioImageView welfareIV = easyRecyclerViewHolder.findViewById(R.id.iv_welfare);
        TextView descTV = easyRecyclerViewHolder.findViewById(R.id.tv_desc);

        if (position % 2 == 0) {
            welfareIV.setImageRatio(0.7f);
        } else {
            welfareIV.setImageRatio(0.6f);
        }

        if (TextUtils.isEmpty(baseGankData.url)) {
            GlideUtils.displayNative(welfareIV, R.mipmap.img_default_gray);
        } else {
            GlideUtils.display(welfareIV, baseGankData.url);
        }
        if (!TextUtils.isEmpty(baseGankData.desc)) {
            descTV.setText(baseGankData.desc);
        } else {
            descTV.setText("卧槽，有美女");
        }
    }

    /**
     * 加载技术类型数据(Android、ios、前端、拓展资源、App)
     *
     * @param easyRecyclerViewHolder
     * @param postion
     */
    private void loadingTechnology(EasyRecyclerViewHolder easyRecyclerViewHolder, int postion) {

    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {

        void onClickPicture(String url, String title, View view);
    }
}
