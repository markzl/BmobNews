package com.aqtc.bmobnews.message;

import android.util.Log;

import com.aqtc.bmobnews.bean.GankDialy;
import com.aqtc.bmobnews.constant.URLConstant;
import com.aqtc.bmobnews.model.IDailyModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by markzl on 2016/9/13.
 * email:1015653112@qq.com
 */
public class DataManange {

    public static void getData(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(URLConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IDailyModel model=retrofit.create(IDailyModel.class);
        Call<GankDialy> call=model.getDaily(2015,11,12);
        call.enqueue(new Callback<GankDialy>() {
            @Override
            public void onResponse(Call<GankDialy> call, Response<GankDialy> response) {
                Log.e("xys","normalGet:"+response.body().error);
            }

            @Override
            public void onFailure(Call<GankDialy> call, Throwable t) {

            }
        });
    }
}
