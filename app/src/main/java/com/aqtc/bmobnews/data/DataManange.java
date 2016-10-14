package com.aqtc.bmobnews.data;


import com.aqtc.bmobnews.bean.GankDaily;
import com.aqtc.bmobnews.bean.GankData;
import com.aqtc.bmobnews.bean.base.BaseGankData;
import com.aqtc.bmobnews.model.DailyModel;
import com.aqtc.bmobnews.model.DataModel;
import com.aqtc.bmobnews.presenter.MainPresenter;
import com.aqtc.bmobnews.util.rx.RxUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public class DataManange {

    public static DataManange instance = null;
    /*  private int year=2016;
      private int month=9;
      private int day=20;*/
    // private CompositeSubscription mSubscription = new CompositeSubscription();
    private final DailyModel dailyModel;
    private final DataModel dataModel;

    private DataManange() {

        this.dataModel = DataModel.getInstance();
        this.dailyModel = DailyModel.getInstance();
    }

    public static DataManange getInstance() {

        if (instance == null) {
            synchronized (DataManange.class) {
                if (instance == null) {
                    instance = new DataManange();
                }
            }
        }
        return instance;
    }

    /**
     * 获取每日数据集合
     *
     * @return
     */
    public Observable<List<GankDaily>> getDailyDataByNetwork(MainPresenter.EasyDate currentDate) {


        return Observable.just(currentDate)
                .flatMapIterable(new Func1<MainPresenter.EasyDate, Iterable<MainPresenter.EasyDate>>() {
                    @Override
                    public Iterable<MainPresenter.EasyDate> call(MainPresenter.EasyDate easyDate) {

                        return easyDate.getPastTime();
                    }
                })
                .flatMap(new Func1<MainPresenter.EasyDate, Observable<GankDaily>>() {
                    @Override
                    public Observable<GankDaily> call(MainPresenter.EasyDate easyDate) {
                        return dailyModel
                                .getDaily(easyDate.getYear(), easyDate.getMonth(), easyDate.getDay())
                                .filter(new Func1<GankDaily, Boolean>() {
                                    @Override
                                    public Boolean call(GankDaily gankDaily) {
                                        return gankDaily.results.androidData!=null;
                                    }
                                });
                    }
                })
                .toSortedList(new Func2<GankDaily, GankDaily, Integer>() {
                    @Override
                    public Integer call(GankDaily gankDaily, GankDaily gankDaily2) {
                        return gankDaily2.results.androidData.get(0).publishedAt
                                .compareTo(gankDaily.results.androidData.get(0).publishedAt);
                    }
                })
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    /**
     * 获取其他类型数据
     *
     * @return
     */
    public Observable<ArrayList<BaseGankData>> getDataByNetwork(String type, int size, int page) {
        return dataModel.getData(type, size, page)
                .map(new Func1<GankData, ArrayList<BaseGankData>>() {
                    @Override
                    public ArrayList<BaseGankData> call(GankData gankData) {
                        return gankData.results;
                    }
                })
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    /**
     * 获取每日详情数据
     *
     * @return
     */
    public Observable<ArrayList<ArrayList<BaseGankData>>> getDailyDetailDataByNetwork() {
        return null;
    }

}
