package com.aqtc.bmobnews.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aqtc.bmobnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by markzl on 2016/10/17.
 * email:1015653112@qq.com
 */

public abstract class BaseToolbarActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            mToolbar.setElevation(0.0f);
        }
        initToolBar();
        initData();
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void initToolBar();

    public abstract void initData();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
