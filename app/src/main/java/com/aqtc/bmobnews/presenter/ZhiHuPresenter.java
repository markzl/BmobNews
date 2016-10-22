package com.aqtc.bmobnews.presenter;

import android.util.Log;

import com.aqtc.bmobnews.bean.zhihu.ZhiHuDaily;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyDetail;
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

    /**
     * 获取每日数据
     *
     * @param type
     */
    public void getDailyData(String type) {

        this.mCompositeSubscription.add(this.zhiHuDataManange.getDailyData(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuDaily>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null) {
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.i("xys", e.getMessage());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        } finally {
                            ZhiHuPresenter.this.getMvpView().onFailure(e);
                        }
                    }

                    @Override
                    public void onNext(ZhiHuDaily zhiHuDaily) {
                        ((ZhiHuView) (ZhiHuPresenter.this.getMvpView())).onGetDailyDataSuccess(zhiHuDaily);
                    }
                }));
    }

    /**
     * 获取更多数据
     *
     * @param type
     * @param date
     */
    public void getMoreDailyData(String type, String date) {

        this.mCompositeSubscription.add(this.zhiHuDataManange.getMoreDailyData(type, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuDaily>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null) {
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            Log.i("xys", e.getMessage());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        } finally {
                            ZhiHuPresenter.this.getMvpView().onFailure(e);
                        }
                    }

                    @Override
                    public void onNext(ZhiHuDaily zhiHuDaily) {
                        ((ZhiHuView) (ZhiHuPresenter.this.getMvpView())).onGetMoreDailyDataSuccess(zhiHuDaily);
                    }
                }));
    }



}
