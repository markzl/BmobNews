package com.aqtc.bmobnews.data.gank;

import com.aqtc.bmobnews.bean.gank.GankDaily;
import com.aqtc.bmobnews.bean.gank.GankData;
import com.aqtc.bmobnews.bean.gank.GankHistroy;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 * Gank.io提供的Api接口
 */
public interface GankInterface {

    /**
     * @param year
     * @param month
     * @param day
     * @return Observable<GankDaily>
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankDaily> getDaily(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    /**
     * android、ios、welfare、前端、扩展资源、休息视频
     *
     * @param type
     * @param size
     * @param page
     * @return Observable<GankData>
     */
    @GET("data/{type}/{size}/{page}")
    Observable<GankData> getData(@Path("type") String type, @Path("size") int size, @Path("page") int page);

    /**
     * 获取发过干货日期接口
     *
     * @return List<String>
     */
    @GET("day/history")
    Observable<GankHistroy> getDateHistroy();
}
