package com.aqtc.bmobnews.data;


import com.aqtc.bmobnews.bean.gank.GankDaily;
import com.aqtc.bmobnews.bean.gank.GankData;
import com.aqtc.bmobnews.bean.gank.base.BaseGankData;
import com.aqtc.bmobnews.model.DailyModel;
import com.aqtc.bmobnews.model.DataModel;
import com.aqtc.bmobnews.presenter.GankPresenter;
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
public class GankDataManange {

    public static GankDataManange instance = null;
    /*  private int year=2016;
      private int month=9;
      private int day=20;*/
    // private CompositeSubscription mSubscription = new CompositeSubscription();
    private final DailyModel dailyModel;
    private final DataModel dataModel;

    private GankDataManange() {

        this.dataModel = DataModel.getInstance();
        this.dailyModel = DailyModel.getInstance();
    }

    public static GankDataManange getInstance() {

        if (instance == null) {
            synchronized (GankDataManange.class) {
                if (instance == null) {
                    instance = new GankDataManange();
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
    public Observable<List<GankDaily>> getDailyDataByNetwork(GankPresenter.EasyDate currentDate) {


        return Observable.just(currentDate)
                .flatMapIterable(new Func1<GankPresenter.EasyDate, Iterable<GankPresenter.EasyDate>>() {
                    @Override
                    public Iterable<GankPresenter.EasyDate> call(GankPresenter.EasyDate easyDate) {

                        return easyDate.getPastTime();
                    }
                })
                .flatMap(new Func1<GankPresenter.EasyDate, Observable<GankDaily>>() {
                    @Override
                    public Observable<GankDaily> call(GankPresenter.EasyDate easyDate) {
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
    public Observable<ArrayList<ArrayList<BaseGankData>>> getDailyDetailDataByDailyResults(GankDaily.DailyResults dailyResults) {


        return Observable.just(dailyResults).map(new Func1<GankDaily.DailyResults, ArrayList<ArrayList<BaseGankData>>>() {
            @Override
            public ArrayList<ArrayList<BaseGankData>> call(GankDaily.DailyResults results) {

                ArrayList<ArrayList<BaseGankData>> cardData = new ArrayList<ArrayList<BaseGankData>>();
                if(dailyResults.welfareData!=null&&dailyResults.welfareData.size()>0){
                    cardData.add(dailyResults.welfareData);
                }
                if(dailyResults.androidData!=null&&dailyResults.androidData.size()>0){
                    cardData.add(dailyResults.androidData);
                }
                if(dailyResults.iosData!=null && dailyResults.iosData.size()>0){
                    cardData.add(dailyResults.iosData);
                }
                if(dailyResults.jsData!=null&& dailyResults.jsData.size()>0){
                    cardData.add(dailyResults.jsData);
                }
                if(dailyResults.videoData!=null&&dailyResults.videoData.size()>0){
                    cardData.add(dailyResults.videoData);
                }
                if(dailyResults.resourcesData!=null&&dailyResults.resourcesData.size()>0){
                    cardData.add(dailyResults.resourcesData);
                }
                if(dailyResults.appData!=null && dailyResults.appData.size()>0){
                    cardData.add(dailyResults.appData);
                }
                return cardData;
            }
        }).compose(RxUtils.applyIOToMainThreadSchedulers());
    }

}
