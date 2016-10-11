package com.aqtc.bmobnews.event;

import com.aqtc.bmobnews.bean.UserBean;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public interface OnLoginListener {

    void loginSuccess(UserBean user);

    void loginFailed();
}
