
package com.aqtc.bmobnews.bean.gank;

import com.aqtc.bmobnews.bean.gank.base.BaseGankData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Android:IOS:JS:APP:ALL分类数据
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class GankData extends Error implements Serializable {

    @SerializedName("error")
    public boolean error;

    @SerializedName("results")
    public ArrayList<BaseGankData> results;
}

