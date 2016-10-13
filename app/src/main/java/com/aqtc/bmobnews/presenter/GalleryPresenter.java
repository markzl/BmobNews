package com.aqtc.bmobnews.presenter;

import com.aqtc.bmobnews.bean.base.BaseGankData;
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

    public void getData(String type, int size, int page) {

        this.mCompositeSubscription.add(this.mDataManager.getDataByNetwork(type, size, page)
                .subscribe(new Subscriber<ArrayList<BaseGankData>>() {
                    @Override
                    public void onCompleted() {
                        if(mCompositeSubscription!=null){
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<BaseGankData> baseGankDatas) {

                        if(GalleryPresenter.this.getMvpView()!=null){

                            ((GalleryView)GalleryPresenter.this.getMvpView()).onGetGalleryDataSuccess(baseGankDatas);
                        }
                    }
                }));
    }
}
