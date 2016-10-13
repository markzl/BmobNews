package com.aqtc.bmobnews.data;

import com.aqtc.bmobnews.EasyApplication;
import com.aqtc.bmobnews.data.gank.GankApi;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public class RetrofitHelper {


    private Retrofit retrofit;
    public static RetrofitHelper instance = null;

    public static RetrofitHelper getInstance() {
        if (instance == null) {

                if (instance == null) {
                    instance = new RetrofitHelper();
                }
        }
        return instance;
    }

    private RetrofitHelper() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(7676, TimeUnit.MILLISECONDS);

        if (EasyApplication.getInstance().log) {
            okHttpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    Logger.d(chain.request().urlString());
                    return response;
                }
            });
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(GankApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(EasyApplication.getInstance().gson))
                .build();

    }

    /**
     * 根据接口对象获取Service
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }
}
