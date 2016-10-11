package com.aqtc.bmobnews.model.imodel;

import com.aqtc.bmobnews.bean.GankDaily;

import rx.Observable;

/**
 * 作者: markzl
 * 日期: 2016/10/2 16:41
 * 邮箱: 1015653112@qq.com
 */

public interface IDailyModel {

    /**
     * 查询每日数据
     * @param year
     * @param month
     * @param day
     * @return Observable<GankDaily>
     */
    Observable<GankDaily> getDaily(int year,int month,int day);
}
