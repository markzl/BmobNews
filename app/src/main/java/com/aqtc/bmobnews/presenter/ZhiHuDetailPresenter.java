package com.aqtc.bmobnews.presenter;

import android.util.Log;

import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyDetail;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyExtra;
import com.aqtc.bmobnews.presenter.base.BasePresenter;
import com.aqtc.bmobnews.view.ZhiHuDetailView;
import com.aqtc.bmobnews.view.base.MvpView;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: markzl
 * time: 2016/10/22 19:19
 * email: 1015653112@qq.com
 */

public class ZhiHuDetailPresenter extends BasePresenter<MvpView> {

    private static ZhiHuDetailPresenter instance;

    private ZhiHuDetailPresenter() {

    }

    public static ZhiHuDetailPresenter getInstance() {
        if (instance == null) {
            synchronized (ZhiHuDetailPresenter.class) {
                if (instance == null) {
                    instance = new ZhiHuDetailPresenter();
                }
            }
        }
        return instance;
    }

    /**
     * 获取每日详情数据
     *
     * @param id
     */
    public void getDailyDetail(long id) {

        this.mCompositeSubscription.add(this.zhiHuDataManange.getZhiHuDailyDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuDailyDetail>() {
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
                            ZhiHuDetailPresenter.this.getMvpView().onFailure(e);
                        }
                    }

                    @Override
                    public void onNext(ZhiHuDailyDetail detail) {
                        ((ZhiHuDetailView) (ZhiHuDetailPresenter.this.getMvpView())).onGetDailyDetailDataSuccess(detail);
                    }
                }));
    }

    /**
     * 获取额外信息
     *
     * @param id
     */
    public void getDailyExtralMessage(long id) {

        this.mCompositeSubscription.add(this.zhiHuDataManange.getDailyExtraById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ZhiHuDailyExtra>() {
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
                            ZhiHuDetailPresenter.this.getMvpView().onFailure(e);
                        }
                    }

                    @Override
                    public void onNext(ZhiHuDailyExtra zhiHuDailyExtra) {
                        ((ZhiHuDetailView) (ZhiHuDetailPresenter.this.getMvpView())).onGetDailyExtralMessageSuccess(zhiHuDailyExtra);
                    }
                }));
    }

}
