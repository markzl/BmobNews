package com.aqtc.bmobnews.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqtc.bmobnews.MainActivity;
import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.bean.gank.base.BaseGankData;
import com.aqtc.bmobnews.bean.zhihu.LuanchImageBean;
import com.aqtc.bmobnews.data.GankDataManange;
import com.aqtc.bmobnews.data.ZhiHuDataManange;
import com.aqtc.bmobnews.data.gank.GankApi;
import com.aqtc.bmobnews.data.gank.GankType;
import com.aqtc.bmobnews.util.GlideUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * author: markzl
 * time: 2016/11/3 16:44
 * email: 1015653112@qq.com
 */

public class SplashActivity extends Activity {

    private static final String RESOLUTION = "1080*1776";

    private static final int ANIMATION_DURATION = 2000;

    private static final float SCALE_END = 1.13F;

    @BindView(R.id.iv_launcher)
    ImageView iv_launcher;
    @BindView(R.id.tv_form)
    TextView tv_form;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                if(msg.what==0){
                    animateImage();
                }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        getLauncherImage();
        super.onResume();
    }

    private ZhiHuDataManange dataManange;
    private CompositeSubscription mCompositeSubscription;

    private void getLauncherImage() {
        if (dataManange == null)
            dataManange = ZhiHuDataManange.getInstance();
        if (mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(dataManange.getLuanchImage(RESOLUTION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LuanchImageBean>() {
                    @Override
                    public void onCompleted() {
                        if(mCompositeSubscription!=null){
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("xys","eero");
                        GlideUtils.displayNative(iv_launcher,R.mipmap.default_bg);
                        mHandler.sendEmptyMessageDelayed(0,1000);
                    }

                    @Override
                    public void onNext(LuanchImageBean bean) {
                        GlideUtils.display(iv_launcher, bean.getImg());
                        tv_form.setText(bean.getText());
                        mHandler.sendEmptyMessageDelayed(0,1000);
                    }
                }));
    }

    /**
     * 动画
     */
    private void animateImage(){

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv_launcher,"scaleX",1f,SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv_launcher,"scaleY",1f,SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        if(dataManange!=null)
            dataManange = null;
        if(mCompositeSubscription!=null)
            mCompositeSubscription=null;
        super.onDestroy();
    }
}
