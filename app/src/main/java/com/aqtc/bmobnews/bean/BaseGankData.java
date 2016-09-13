
package com.aqtc.bmobnews.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class BaseGankData implements Serializable {

    // 发布人
    @SerializedName("who")
    public String who;

    // 发布时间
    @SerializedName("publishedAt")
    public Date publishedAt;

    // 标题
    @SerializedName("desc")
    public String desc;

    // 类型， 一般都是"福利"
    @SerializedName("type")
    public String type;

    // 图片url
    @SerializedName("url")
    public String url;

    // 是否可用
    @SerializedName("used")
    public Boolean used;

    // 对象id
    @SerializedName("objectId")
    public String objectId;

    // 创建时间
    @SerializedName("createdAt")
    public Date createdAt;

    // 更新时间
    @SerializedName("updatedAt")
    public Date updatedAt;
}
