package com.aqtc.bmobnews.bean.zhihu.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by markzl on 2016/10/20.
 * email:1015653112@qq.com
 */

public class BaseZhiHuData implements Serializable{

    @SerializedName("images")
    public List<String> images;

    @SerializedName("type")
    public int type;

    @SerializedName("id")
    public long id;

    @SerializedName("ga_prefix")
    public String ga_prefix;

    @SerializedName("title")
    public String title;
}
