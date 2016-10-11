package com.aqtc.bmobnews.data;


import com.aqtc.bmobnews.bean.BaseGankData;
import com.aqtc.bmobnews.bean.GankDaily;
import com.aqtc.bmobnews.model.DailyModel;
import com.aqtc.bmobnews.model.DataModel;
import com.aqtc.bmobnews.presenter.MainPresenter;
import com.aqtc.bmobnews.util.rx.RxUtils;

import java.util.ArrayList;

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
    private DataModel dataModel;

    private DataManange() {

        this.dataModel = new DataModel();
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
    public Observable<ArrayList<GankDaily>> getDailyDataByNetwork(MainPresenter.EasyDate currentDate) {


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
                        return dailyModel.getDaily(easyDate.getYear(), easyDate.getMonth(), easyDate.getDay());
                    }
                })
                .toSortedList(new Func2<GankDaily, GankDaily, Integer>() {
                    @Override
                    public Integer call(GankDaily gankDaily, GankDaily gankDaily2) {
                        return gankDaily2.results.androidData.get(0).publishedAt
                                .compareTo(gankDaily.results.androidData.get(0).publishedAt);
                    }
                })
                .compose(null);
    }

    /**
     * 获取其他类型数据
     *
     * @return
     */
    public Observable<ArrayList<BaseGankData>> getDataByNetwork() {
        return null;
    }

    /**
     * 获取每日详情数据
     *
     * @return
     */
    public Observable<ArrayList<ArrayList<BaseGankData>>> getDailyDetailDataByNetwork() {
        return null;
    }
   /* public void getDailyData() {

        mSubscription.add(RetrofitHelper.getInstance().createService(GankInterface.class).getDailyData(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.i("xys", "doOnSubscibe");
                    }
                })
                .subscribe(new Observer<GankDaily>() {
                    @Override
                    public void onCompleted() {
                        Log.i("xys", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("xys", "Error");
                    }

                    @Override
                    public void onNext(GankDaily gankDaily) {

                        Log.i("xyss", gankDaily.category.toString());
                    }
                }));


    }*/

   /* public Observable<List<GankDaily>> getDailyDataByNetwork(EasyDate currentDate) {
        return Observable.just(currentDate)
                .flatMapIterable(EasyDate::getPastTime)
                .flatMap(easyDate -> {
                            *//*
                             * 感觉Android的数据应该不会为null
                             * 所以以Android的数据为判断是否当天有数据
                             *//*
                    return this.dailyModel.getDaily(easyDate.getYear(),
                            easyDate.getMonth(), easyDate.getDay())
                            .filter(dailyData ->
                                    dailyData.results.androidData != null);
                })
                .toSortedList((dailyData, dailyData2) -> {
                    return dailyData2.results.androidData.get(0).publishedAt.compareTo(
                            dailyData.results.androidData.get(0).publishedAt);
                })
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }
*/
}
