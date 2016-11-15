package com.aqtc.bmobnews.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewAdapter;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewHolder;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyExtra;
import com.aqtc.bmobnews.bean.zhihu.base.BaseZhiHuData;
import com.aqtc.bmobnews.data.ZhiHuDataManange;
import com.aqtc.bmobnews.util.GlideUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by markzl on 2016/10/20.
 * email:1015653112@qq.com
 */

public class ZhiHuAdapter extends EasyRecyclerViewAdapter{


    @Override
    public int[] getItemLayouts() {
        return new int[]{R.layout.item_gank_daily};
    }

    @Override
    public int getRecyclerViewItemType(int position) {
        return 0;
    }

    @Override
    public void onBindRecyclerViewHolder(EasyRecyclerViewHolder viewHolder, int position) {
        loadZhiHuDaily(viewHolder,position);
    }

    private void loadZhiHuDaily(EasyRecyclerViewHolder easyRecyclerViewHolder, int position){

        BaseZhiHuData baseZhiHuData = this.getItem(position);
        ImageView dailyIV = easyRecyclerViewHolder.findViewById(R.id.iv_daily_image);
        TextView dailyDateTV = easyRecyclerViewHolder.findViewById(R.id.daily_date_tv);
        TextView dailyTitleTV = easyRecyclerViewHolder.findViewById(R.id.tv_daily_title);

        TextView androidTagTV = easyRecyclerViewHolder.findViewById(R.id.daily_android_tag_tv);
        TextView iOSTagTV = easyRecyclerViewHolder.findViewById(R.id.daily_ios_tag_tv);
        TextView jsTagTV = easyRecyclerViewHolder.findViewById(R.id.daily_js_tag_tv);
        androidTagTV.setVisibility(View.GONE);
        iOSTagTV.setVisibility(View.GONE);
        jsTagTV.setVisibility(View.GONE);
        if(baseZhiHuData.images.get(0)!=null){
            GlideUtils.display(dailyIV,baseZhiHuData.images.get(0));
        }
        if(baseZhiHuData.title!=null){
            dailyTitleTV.setText(baseZhiHuData.title);
        }
        if(baseZhiHuData.ga_prefix!=null){
            dailyDateTV.setText(baseZhiHuData.ga_prefix.substring(0,2)+"."+baseZhiHuData.ga_prefix.substring(2,4)+"."+baseZhiHuData.ga_prefix.substring(4,6));
        }



    }
}
