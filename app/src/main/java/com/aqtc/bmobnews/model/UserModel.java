package com.aqtc.bmobnews.model;

import com.aqtc.bmobnews.bean.UserBean;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public class UserModel implements IUserModel{

    @Override
    public void login(final String username, final String password, final OnLoginListener loginListener) {

        //模拟子线程耗时操作
        new Thread(){
            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if("admin".equals(username)&&"1234".equals(password)){
                    UserBean user=new UserBean();
                    user.username=username;
                    user.passwrod=password;
                    loginListener.loginSuccess(user);
                }else{
                    loginListener.loginFailed();
                }
            }
        }.start();
    }
}
