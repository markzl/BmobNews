package com.aqtc.bmobnews.presenter.base;

import com.aqtc.bmobnews.data.GankDataManange;
import com.aqtc.bmobnews.data.ZhiHuDataManange;
import com.aqtc.bmobnews.view.base.MvpView;

import rx.subscriptions.CompositeSubscription;

/**
 * 作者: markzl
 * 日期: 2016/10/2 15:57
 * 邮箱: 1015653112@qq.com
 */

public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;
    public CompositeSubscription mCompositeSubscription;
    public GankDataManange mDataManager;

    public ZhiHuDataManange zhiHuDataManange;

    @Override
    public void atthachView(T mvpView) {
        this.mMvpView = mvpView;
        this.mCompositeSubscription = new CompositeSubscription();
        this.mDataManager = GankDataManange.getInstance();
        this.zhiHuDataManange = ZhiHuDataManange.getInstance();
    }

    @Override
    public void detachView() {
        this.mMvpView = null;
        this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
        this.mDataManager = null;
        this.zhiHuDataManange = null;
    }

    /**
     * 判断View是否绑定
     *
     * @return
     */
    public boolean isViewAttached() {

        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    /**
     * 如果未绑定抛出异常
     */
    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
