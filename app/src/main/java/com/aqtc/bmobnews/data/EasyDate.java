package com.aqtc.bmobnews.data;

import com.aqtc.bmobnews.data.constant.GankApi;
import com.aqtc.bmobnews.util.DateUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 查每日干货需要的特殊的类
 */
public class EasyDate implements Serializable {

    public static int page=1;
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

    public List<EasyDate> getPastTime() {
        List<EasyDate> easyDates = new ArrayList<>();
        for (int i = 0; i < GankApi.DEFAULT_DAILY_SIZE; i++) {
                /*
                 * - (page * DateUtils.ONE_DAY) 翻到哪页再找 一页有DEFAULT_DAILY_SIZE这么长
                 * - i * DateUtils.ONE_DAY 往前一天一天 找呀找
                 */
            long time = this.calendar.getTimeInMillis() - ((page - 1) * GankApi.DEFAULT_DAILY_SIZE * DateUtils.ONE_DAY) - i * DateUtils.ONE_DAY;
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            EasyDate date = new EasyDate(c);
            easyDates.add(date);
        }
        return easyDates;
    }

}