package com.aqtc.bmobnews.data;

import com.aqtc.bmobnews.bean.BaseGankData;
import com.aqtc.bmobnews.bean.GankDaily;
import com.aqtc.bmobnews.bean.GankData;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public interface GankInterface {

    /**
     * @param year
     * @param month
     * @param day
     * @return Observable<GankDaily>
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankDaily> getDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

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
}
