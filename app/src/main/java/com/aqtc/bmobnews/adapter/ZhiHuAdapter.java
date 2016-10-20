package com.aqtc.bmobnews.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewAdapter;
import com.aqtc.bmobnews.adapter.base.EasyRecyclerViewHolder;
import com.aqtc.bmobnews.bean.zhihu.base.BaseZhiHuData;
import com.aqtc.bmobnews.util.GlideUtils;

/**
 * Created by markzl on 2016/10/20.
 * email:1015653112@qq.com
 */

public class ZhiHuAdapter extends EasyRecyclerViewAdapter{

    @Override
    public int[] getItemLayouts() {
        return new int[]{R.layout.item_zhihu_daily_test};
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
        if(baseZhiHuData.images.get(0)!=null){
            GlideUtils.display(dailyIV,baseZhiHuData.images.get(0));
        }
        if(baseZhiHuData.title!=null){
            dailyTitleTV.setText(baseZhiHuData.title);
        }
        if(baseZhiHuData.ga_prefix!=null){
            dailyDateTV.setText(baseZhiHuData.ga_prefix);
        }

    }
}
