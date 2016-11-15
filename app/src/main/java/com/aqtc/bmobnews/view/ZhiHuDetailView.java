package com.aqtc.bmobnews.view;

import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyDetail;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyExtra;
import com.aqtc.bmobnews.view.base.MvpView;

/**
 * author: markzl
 * time: 2016/10/22 19:16
 * email: 1015653112@qq.com
 */

public interface ZhiHuDetailView extends MvpView {

    /**
     * 获取新闻详情数据
     * 成功
     *
     * @param zhiHuDailyDetail
     */
    void onGetDailyDetailDataSuccess(ZhiHuDailyDetail zhiHuDailyDetail);

    /**
     * 获取新闻额外数据
     * （评论+点赞数）
     *
     * @param zhiHuDailyExtra
     */
    void onGetDailyExtralMessageSuccess(ZhiHuDailyExtra zhiHuDailyExtra);

}
