package com.aqtc.bmobnews.view;

import com.aqtc.bmobnews.bean.UserBean;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public interface UserLoginView {

    String getUserName();

    String getPassword();

    void clearText();

    void showLoading();

    void hideLoading();

    void toMainActivity(UserBean user);

    void showFailedError();

}
