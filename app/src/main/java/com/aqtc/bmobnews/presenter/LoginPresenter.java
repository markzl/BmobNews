package com.aqtc.bmobnews.presenter;

import android.os.Handler;

import com.aqtc.bmobnews.bean.UserBean;
import com.aqtc.bmobnews.model.IUserModel;
import com.aqtc.bmobnews.model.OnLoginListener;
import com.aqtc.bmobnews.model.UserModel;
import com.aqtc.bmobnews.view.IUserLoginView;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public class LoginPresenter {

    private IUserModel userModel;

    private IUserLoginView userLoginView;
    private Handler mHandler=new Handler();

    public LoginPresenter(IUserLoginView userLoginView){
        this.userLoginView=userLoginView;
        this.userModel=new UserModel();
    }

    public void login(){

        userLoginView.showLoading();
        userModel.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final UserBean user) {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                //需要在UI线程执行
                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });
            }
        });
    }
}
