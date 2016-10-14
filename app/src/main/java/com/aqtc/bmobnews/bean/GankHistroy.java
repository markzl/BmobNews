package com.aqtc.bmobnews.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by markzl on 2016/10/14.
 * email:1015653112@qq.com
 */

public class GankHistroy {

    @SerializedName("error")
    public boolean error;

    @SerializedName("results")
    public ArrayList<String> results;
}
