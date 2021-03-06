package com.aqtc.bmobnews;

import android.app.Application;
import android.content.Context;

import com.anupcowkur.reservoir.Reservoir;
import com.aqtc.bmobnews.data.gank.GankApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

/**
 * author: markzl
 * time: 2016/10/2 17:04
 * email: 1015653112@qq.com
 */

public class BmobApplication extends Application {

    private static BmobApplication instance = new BmobApplication();
    public boolean log = true;
    public Gson gson;

    public static final long ONE_KB = 1024L;
    public static final long ONE_MB = ONE_KB * 1024L;
    public static final long CACHE_DATA_MAX_SIZE = ONE_MB * 3L;

    public static BmobApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Logger.init();
        this.initGson();
        this.initReservoir();

    }

    private void initGson() {

        this.gson = new GsonBuilder().setDateFormat(GankApi.GANK_DATA_FORMAT).create();
    }

    private void initReservoir() {

        try {
            Reservoir.init(this, CACHE_DATA_MAX_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
