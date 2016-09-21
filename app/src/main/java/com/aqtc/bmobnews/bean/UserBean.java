package com.aqtc.bmobnews.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public class UserBean implements Serializable{

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("password")
    @Expose
    public String passwrod;
}
