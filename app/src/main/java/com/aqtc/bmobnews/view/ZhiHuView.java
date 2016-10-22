package com.aqtc.bmobnews.view;

import com.aqtc.bmobnews.bean.zhihu.ZhiHuDaily;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyDetail;
import com.aqtc.bmobnews.view.base.MvpView;

/**
 * Created by markzl on 2016/10/20.
 * email:1015653112@qq.com
 */

public interface ZhiHuView extends MvpView{

    /**
     * 获取知乎新闻数据成功
     * @param zhiHuDaily
     */
    void onGetDailyDataSuccess(ZhiHuDaily zhiHuDaily);

    /**
     * 获取更多数据成功
     * @param zhiHuDaily
     */
    void onGetMoreDailyDataSuccess(ZhiHuDaily zhiHuDaily);


}
