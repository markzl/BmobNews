package com.aqtc.bmobnews.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * author: markzl
 * time: 2016/10/22 11:08
 * email: 1015653112@qq.com
 */

public class ZhiHuDailyDetail implements Serializable {

    @SerializedName("body")
    public String body;

    @SerializedName("image_source")
    public String image_source;

    @SerializedName("title")
    public String title;

    @SerializedName("image")
    public String image;

    @SerializedName("share_url")
    public String share_url;

    @SerializedName("ga_prefix")
    public String ga_prefix;

    @SerializedName("images")
    public List<String> images;

    @SerializedName("id")
    public long id;

    @SerializedName("js")
    public List<String> js;

    @SerializedName("css")
    public List<String> css;

}
