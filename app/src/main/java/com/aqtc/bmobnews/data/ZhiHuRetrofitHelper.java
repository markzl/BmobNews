package com.aqtc.bmobnews.data;

import com.aqtc.bmobnews.BmobApplication;
import com.aqtc.bmobnews.data.zhihu.ZhiHuType;
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
public class ZhiHuRetrofitHelper {

    private Retrofit retrofit;
    public static ZhiHuRetrofitHelper instance = null;

    public static ZhiHuRetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (ZhiHuRetrofitHelper.class) {
                if (instance == null) {
                    instance = new ZhiHuRetrofitHelper();
                }
            }
        }
        return instance;
    }

    private ZhiHuRetrofitHelper() {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(7676, TimeUnit.MILLISECONDS);

        if (BmobApplication.getInstance().log) {
            okHttpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    //Logger.d(chain.request().urlString());
                    return response;
                }
            });
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(ZhiHuType.BASE_URL)
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
}
