package com.aqtc.bmobnews.view;

import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyDetail;
import com.aqtc.bmobnews.view.base.MvpView;

/**
 * author: markzl
 * time: 2016/10/22 19:16
 * email: 1015653112@qq.com
 */

public interface ZhiHuDetailView extends MvpView {

    /**
     * 获取新闻详情数据
     *
     * @param zhiHuDailyDetail
     */
    void onGetDailyDetailDataSuccess(ZhiHuDailyDetail zhiHuDailyDetail);
}
