package com.aqtc.bmobnews.data;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public class RetrofitHelper {

    private static final String GANK_API = "http://gank.io/api/";

    Retrofit retrofit;
    public static RetrofitHelper instance=null;

    public RetrofitHelper(){
        retrofit=new Retrofit.Builder()
                .baseUrl(GANK_API)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }


    public <T> T createService(Class<T> clz){
        return retrofit.create(clz);
    }
}
