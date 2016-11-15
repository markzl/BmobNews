package com.aqtc.bmobnews.bean.zhihu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * author: markzl
 * time: 2016/11/3 15:04
 * email: 1015653112@qq.com
 */

public class ZhiHuDailyExtra implements Serializable{

    /*long_comments : 长评论总数
    popularity : 点赞总数
    short_comments : 短评论总数
    comments : 评论总数*/
    @SerializedName("long_comments")
    public int long_comments;

    @SerializedName("popularity")
    public int  popularity;

    @SerializedName("short_comments")
    public int  short_comments;

    @SerializedName("comments")
    public int  comments;
}
