package com.aqtc.bmobnews.view;

import com.aqtc.bmobnews.bean.GankDaily;
import com.aqtc.bmobnews.view.base.MvpView;

import java.util.List;

/**
 * Created by markzl on 2016/10/12.
 * email:1015653112@qq.com
 */

public interface ImportView extends MvpView {

    /**
     * 查询每日干货成功
     *
     * @param dailyData
     * @param refresh
     */
    void onGetDailySuccess(List<GankDaily> dailyData, boolean refresh);

}
