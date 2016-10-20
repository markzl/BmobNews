package com.aqtc.bmobnews.presenter;

import com.aqtc.bmobnews.bean.zhihu.ZhiHuDaily;
import com.aqtc.bmobnews.presenter.base.BasePresenter;
import com.aqtc.bmobnews.view.ZhiHuView;
import com.aqtc.bmobnews.view.base.MvpView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by markzl on 2016/10/20.
 * email:1015653112@qq.com
 */

public class ZhiHuPresenter extends BasePresenter<MvpView> {

    public ZhiHuPresenter() {

    }

    public void getDailyData(String type) {

        this.mCompositeSubscription.add(this.zhiHuDataManange.getDailyData(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuDaily>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ZhiHuDaily zhiHuDaily) {
                        ((ZhiHuView) (ZhiHuPresenter.this.getMvpView())).getDailyData(zhiHuDaily);
                    }
                }));
    }

}
