package com.aqtc.bmobnews.presenter;

import android.util.Log;

import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.aqtc.bmobnews.bean.gank.GankDaily;
import com.aqtc.bmobnews.bean.gank.base.BaseGankData;
import com.aqtc.bmobnews.data.constant.CodeConstant;
import com.aqtc.bmobnews.data.gank.GankApi;
import com.aqtc.bmobnews.data.gank.GankType;
import com.aqtc.bmobnews.presenter.base.BasePresenter;
import com.aqtc.bmobnews.util.DateUtils;
import com.aqtc.bmobnews.util.ReservoirUtil;
import com.aqtc.bmobnews.view.CategoryView;
import com.aqtc.bmobnews.view.ImportView;
import com.aqtc.bmobnews.view.base.MvpView;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: markzl
 * time: 2016/10/2 18:05
 * email: 1015653112@qq.com
 */

public class GankPresenter extends BasePresenter<MvpView> {

    private int page;
    private EasyDate currentDate;
    private ReservoirUtil reservoirUtil;

    public GankPresenter() {

        reservoirUtil = new ReservoirUtil();
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        currentDate = new EasyDate(calendar);
        page = 1;
    }

    /**
     * 设置查询第几页
     *
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 获取当前第几页
     *
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * 获取每日数据
     *
     * @param isRefresh
     * @param oldPage
     */
    public void getDaily(final boolean isRefresh, int oldPage) {

        if (oldPage != -1) {
            this.page = 1;
        }
        this.mCompositeSubscription.add(mDataManager.getDailyDataByNetwork(currentDate)
                .subscribe(new Subscriber<List<GankDaily>>() {
                    @Override
                    public void onCompleted() {
                        if (GankPresenter.this.mCompositeSubscription != null) {
                            GankPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        try {
                            Logger.d(e.getMessage());
                        } catch (Throwable e1) {
                            e1.getMessage();
                        } finally {
                            if (isRefresh) {
                                Type resultType = new TypeToken<List<GankDaily>>() {
                                }.getType();

                                GankPresenter.this.reservoirUtil.get(GankType.daily + "", resultType, new ReservoirGetCallback<List<GankDaily>>() {
                                    @Override
                                    public void onSuccess(List<GankDaily> gankDailies) {
                                        //如果有缓存显示缓存数据
                                        if (GankPresenter.this.getMvpView() != null) {
                                            ((ImportView) (GankPresenter.this.getMvpView())).onGetDailySuccess(gankDailies, isRefresh);
                                        }
                                        if (GankPresenter.this.getMvpView() != null) {
                                            GankPresenter.this.getMvpView().onFailure(e);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                            } else {
                                //加载更多失败
                                GankPresenter.this.getMvpView().onFailure(e);
                            }
                        }
                    }

                    @Override
                    public void onNext(List<GankDaily> gankDailies) {

                        if (isRefresh) {
                            GankPresenter.this.reservoirUtil.refresh(GankType.daily + "", gankDailies);
                        }
                        if (GankPresenter.this.getMvpView() != null) {
                            ((ImportView) GankPresenter.this.getMvpView()).onGetDailySuccess(gankDailies, isRefresh);
                        }
                    }
                }));
    }

    /**
     * 获取每日详情数据
     *
     * @param dailyResults
     */
    public void getDailyDetail(final GankDaily.DailyResults dailyResults) {

        this.mCompositeSubscription.add(this.mDataManager.getDailyDetailDataByDailyResults(dailyResults)
                .subscribe(new Subscriber<ArrayList<ArrayList<BaseGankData>>>() {
                    @Override
                    public void onCompleted() {
                        if (GankPresenter.this.mCompositeSubscription != null) {
                            GankPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            if (GankPresenter.this.getMvpView() != null) {
                                GankPresenter.this.getMvpView().onFailure(e);
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(ArrayList<ArrayList<BaseGankData>> results) {

                        if (GankPresenter.this.getMvpView() != null) {
                            ((ImportView) (GankPresenter.this.getMvpView())).onGetDailyDetail(DateUtils.date2String(dailyResults.welfareData.get(0).publishedAt.getTime(),
                                    CodeConstant.DAILY_DATE_FORMAT), results);
                        }
                    }
                }));
    }

    /**
     * 获取分类数据
     *
     * @param type
     * @param oldPage
     */
    public void getCategoryData(String type, int oldPage) {

        this.mCompositeSubscription.add(this.mDataManager.getDataByNetwork(type, GankApi.DEFAULT_DATA_SIZE, oldPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<BaseGankData>>() {
                    @Override
                    public void onCompleted() {
                        if (mCompositeSubscription != null) {
                            mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            e.getMessage();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        } finally {
                            GankPresenter.this.getMvpView().onFailure(e);
                        }
                    }

                    @Override
                    public void onNext(ArrayList<BaseGankData> baseGankDatas) {
                        ((CategoryView) (GankPresenter.this.getMvpView())).onGetCategoryDataSuccess(baseGankDatas, oldPage);
                    }
                }));
    }

    /**
     * 日期获取的特殊类
     */
    public class EasyDate implements Serializable {

        private Calendar calendar;

        public EasyDate(Calendar calendar) {
            this.calendar = calendar;
        }

        public int getYear() {
            return calendar.get(Calendar.YEAR);
        }

        public int getMonth() {
            return calendar.get(Calendar.MONTH) + 1;
        }

        public int getDay() {
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        /**
         * 获取与当前日期前10天的日期集合
         *
         * @return List<EasyDate>
         */
        public List<EasyDate> getPastTime() {

            List<EasyDate> easyDates = new ArrayList<>();
            for (int i = 0; i < GankApi.DEFAULT_DAILY_SIZE; i++) {

                long time = this.calendar.getTimeInMillis() - (page - 1) * GankApi.DEFAULT_DAILY_SIZE * DateUtils.ONE_DAY - (10+i)* DateUtils.ONE_DAY;
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(time);
                EasyDate date = new EasyDate(c);
                easyDates.add(date);
            }
            return easyDates;
        }
    }

}
