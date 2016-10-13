package com.aqtc.bmobnews.bean;

import com.aqtc.bmobnews.bean.base.BaseGankData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class GankDaily implements Serializable{

    @SerializedName("category")
    public ArrayList<String> category;

    @SerializedName("error")
    public boolean error;

    @SerializedName("results")
    public DailyResults results;

    public class DailyResults implements Serializable{

        @SerializedName("福利")
        public ArrayList<BaseGankData> welfareData;

        @SerializedName("Android")
        public ArrayList<BaseGankData> androidData;

        @SerializedName("iOS")
        public ArrayList<BaseGankData> iosData;

        @SerializedName("前端")
        public ArrayList<BaseGankData> jsData;

        @SerializedName("休息视频")
        public ArrayList<BaseGankData> videoData;

        @SerializedName("拓展资源")
        public ArrayList<BaseGankData> resourcesData;

        @SerializedName("App")
        public ArrayList<BaseGankData> appData;

        @SerializedName("瞎推荐")
        public ArrayList<BaseGankData> recommendData;
    }

}
