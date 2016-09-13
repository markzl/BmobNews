package com.aqtc.bmobnews.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by markzl on 2016/9/13.
 * email:1015653112@qq.com
 */
public class GankHistory implements Serializable{

    @SerializedName("error")
    public boolean error;
    @SerializedName("results")
    public ArrayList<Results> results;

    public class Results{

        @SerializedName("objectId")
        public String objectId;
        @SerializedName("content")
        public String content;
        @SerializedName("publishedAt")
        public Date publishedAt;
        @SerializedName("title")
        public String title;
    }

}
