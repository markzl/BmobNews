package com.aqtc.bmobnews.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.bean.UserBean;
import com.aqtc.bmobnews.presenter.LoginPresenter;
import com.aqtc.bmobnews.view.IUserLoginView;

import butterknife.BindView;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public class UserLoginActivity extends BaseActivity implements IUserLoginView{


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.email_register_button)
    Button mEmailSignInButton;
    @BindView(R.id.register_form)
    View mLoginFormView;
    @BindView(R.id.pb_register)
    View mProgressView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
    private LoginPresenter presenter=new LoginPresenter(this);
    @Override
    public void initView(Bundle savedInstanceState) {

        mEmailSignInButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                presenter.login();
            }
        });
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public String getUserName() {
        return mEmailView.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString().trim();
    }

    @Override
    public void clearText() {
        mEmailView.setText("");
        mPasswordView.setText("");
    }

    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(UserBean user) {

    }

    @Override
    public void showFailedError() {

    }
}
