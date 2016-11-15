package com.aqtc.bmobnews.data;

import com.aqtc.bmobnews.BmobApplication;
import com.aqtc.bmobnews.data.gank.GankApi;
import com.aqtc.bmobnews.data.gank.GankInterface;
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
    public static final String BASE_MEIZITU_URL = "http://www.mzitu.com/";

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

    private RetrofitHelper() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(7676, TimeUnit.MILLISECONDS);

        if (BmobApplication.getInstance().log) {
            okHttpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    //Logger.d(chain.request().urlString());
                    return response;
                }
            });
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(GankApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(BmobApplication.getInstance().gson))
                .build();

    }

    /**
     * 根据接口对象获取Service
     *
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }

    /**
     * 获取妹子图数据
     * @return
     */
    public static GankInterface getMeiziService(){

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_MEIZITU_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  mRetrofit.create(GankInterface.class);
    }
}
