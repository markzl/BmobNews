package com.aqtc.bmobnews.model;

import com.aqtc.bmobnews.bean.GankDaily;
import com.aqtc.bmobnews.bean.GankHistroy;
import com.aqtc.bmobnews.data.RetrofitHelper;
import com.aqtc.bmobnews.data.gank.GankInterface;
import com.aqtc.bmobnews.model.imodel.IDailyModel;

import rx.Observable;

/**
 * 作者: markzl
 * 日期: 2016/10/2 16:14
 * 邮箱: 1015653112@qq.com
 */

public class DailyModel implements IDailyModel {

    private static DailyModel instance = null;

    public static DailyModel getInstance() {
        if (instance == null) {
            instance = new DailyModel();
        }
        return instance;
    }

    private DailyModel() {
    }

    /**
     * 查询每日数据
     *
     * @param year
     * @param month
     * @param day
     * @return Observable<GankDaily>
     */
    @Override
    public Observable<GankDaily> getDaily(int year, int month, int day) {

        return RetrofitHelper.getInstance().createService(GankInterface.class).getDaily(year, month, day);
    }

    @Override
    public Observable<GankHistroy> getDateHistroy() {

        return RetrofitHelper.getInstance().createService(GankInterface.class).getDateHistroy();
    }
}
