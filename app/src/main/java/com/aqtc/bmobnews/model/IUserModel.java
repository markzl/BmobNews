package com.aqtc.bmobnews.model;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public interface IUserModel {

    void login(String username,String password,OnLoginListener loginListener);
}
