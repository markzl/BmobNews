package com.aqtc.bmobnews.bean.zhihu;

import com.aqtc.bmobnews.bean.zhihu.base.BaseZhiHuData;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by markzl on 2016/10/20.
 * email:1015653112@qq.com
 */

public class ZhiHuDaily implements Serializable{

    @SerializedName("date")
    public String date;

    @SerializedName("stories")
    public List<BaseZhiHuData> stories;

}
