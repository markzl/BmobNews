package com.aqtc.bmobnews.model;

import com.aqtc.bmobnews.bean.gank.GankData;
import com.aqtc.bmobnews.data.gank.GankInterface;
import com.aqtc.bmobnews.data.RetrofitHelper;

import rx.Observable;


/**
 * 作者: markzl
 * 日期: 2016/10/2 16:14
 * 邮箱: 1015653112@qq.com
 */

public class DataModel {

    private static DataModel instance = null;

    private DataModel() {}

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    public Observable<GankData> getData(String type, int size, int page){

        return RetrofitHelper.getInstance().createService(GankInterface.class).getData(type,size,page);

    }
}
