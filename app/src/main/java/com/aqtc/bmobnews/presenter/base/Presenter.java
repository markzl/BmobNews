package com.aqtc.bmobnews.presenter.base;

import com.aqtc.bmobnews.view.base.MvpView;

/**
 * 作者: markzl
 * 日期: 2016/10/2 15:55
 * 邮箱: 1015653112@qq.com
 */

public interface Presenter<V extends MvpView> {

    /**
     * 绑定View层
     * @param mvpView
     */
    void atthachView(V mvpView);

    /**
     * 解除绑定
     */
    void detachView();
}
