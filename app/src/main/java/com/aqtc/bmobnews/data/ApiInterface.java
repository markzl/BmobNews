package com.aqtc.bmobnews.data;

import com.aqtc.bmobnews.bean.GankDaily;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public interface ApiInterface {

    @GET("day/{year}/{month}/{day}")
    Observable<GankDaily> getDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day);


}
