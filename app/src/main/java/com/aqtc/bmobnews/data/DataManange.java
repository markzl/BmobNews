package com.aqtc.bmobnews.data;


import android.util.Log;

import com.aqtc.bmobnews.bean.GankDaily;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by markzl on 2016/9/20.
 * email:1015653112@qq.com
 */
public class DataManange {

    public static DataManange instance = null;
    private int year=2016;
    private int month=9;
    private int day=20;
    private CompositeSubscription mSubscription = new CompositeSubscription();


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

    public void getDailyData() {

        mSubscription.add(RetrofitHelper.getInstance().createService(ApiInterface.class).getDailyData(year, month, day)
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


    }

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
