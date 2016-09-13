package com.aqtc.bmobnews.model;

import com.aqtc.bmobnews.bean.GankDialy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by markzl on 2016/9/13.
 * email:1015653112@qq.com
 */
public interface IDailyModel {

    @GET("day/{year}/{month}/{day}")
    Call<GankDialy> getDaily(@Path("year") int year,@Path("month") int month,@Path("day") int day);
}
