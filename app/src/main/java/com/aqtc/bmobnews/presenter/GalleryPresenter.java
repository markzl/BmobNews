package com.aqtc.bmobnews.presenter;

import com.aqtc.bmobnews.bean.gank.base.BaseGankData;
import com.aqtc.bmobnews.data.gank.GankApi;
import com.aqtc.bmobnews.presenter.base.BasePresenter;
import com.aqtc.bmobnews.view.GalleryView;
import com.aqtc.bmobnews.view.base.MvpView;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by markzl on 2016/10/13.
 * email:1015653112@qq.com
 */

public class GalleryPresenter extends BasePresenter<MvpView> {

    private int page;

    public GalleryPresenter() {
        page = 1;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void getData(boolean refresh, int oldPage) {

        if (oldPage != -1) {
            this.page = 1;
        }
        this.mCompositeSubscription.add(this.mDataManager.getDataByNetwork(GankApi.DATA_TYPE_WELFARE, GankApi.DEFAULT_DATA_SIZE, page)
                .subscribe(new Subscriber<ArrayList<BaseGankData>>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null) {
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<BaseGankData> baseGankDatas) {

                        if (GalleryPresenter.this.getMvpView() != null) {

                            ((GalleryView) GalleryPresenter.this.getMvpView()).onGetGalleryDataSuccess(baseGankDatas,refresh);
                        }
                    }
                }));
    }
}
