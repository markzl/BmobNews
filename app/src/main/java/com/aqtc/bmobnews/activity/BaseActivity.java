package com.aqtc.bmobnews.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by markzl on 2016/9/7.
 * email:1015653112@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //系统的StatusBar
        //StatusBarCompat.compatShading(this);
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void initToolBar();

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
