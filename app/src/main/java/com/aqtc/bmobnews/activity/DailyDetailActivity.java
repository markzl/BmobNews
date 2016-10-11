package com.aqtc.bmobnews.activity;

import android.os.Bundle;
import android.util.Log;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.bean.GankDaily;

/**
 * Created by markzl on 2016/9/14.
 * email:1015653112@qq.com
 */
public class DailyDetailActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        GankDaily.DailyResults data= (GankDaily.DailyResults) getIntent().getExtras().getSerializable("detail");
        Log.i("xys",data.androidData.get(0).desc);
    }

    @Override
    public void initToolBar() {

    }
}
