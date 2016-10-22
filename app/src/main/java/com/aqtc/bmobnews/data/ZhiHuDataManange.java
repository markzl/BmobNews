package com.aqtc.bmobnews.data;

import com.aqtc.bmobnews.bean.zhihu.ZhiHuDaily;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyDetail;
import com.aqtc.bmobnews.data.zhihu.ZhiHuInterface;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by markzl on 2016/10/20.
 * email:1015653112@qq.com
 */

public class ZhiHuDataManange {

    private static ZhiHuDataManange instance;

    private ZhiHuDataManange(){

    }

    public static ZhiHuDataManange getInstance(){
        if(instance==null){
            synchronized (ZhiHuDataManange.class){
                if(instance==null){
                    instance = new ZhiHuDataManange();
                }
            }
        }
        return instance;
    }

    /**
     * 获取知乎最新的新闻数据
     * @return
     */
    public Observable<ZhiHuDaily> getDailyData(String type){
        return ZhiHuRetrofitHelper.getInstance()
                .createService(ZhiHuInterface.class)
                .getDaily(type)
                .filter(new Func1<ZhiHuDaily, Boolean>() {
                    @Override
                    public Boolean call(ZhiHuDaily zhiHuDaily) {
                        return zhiHuDaily != null;
                    }
                });
    }

    /**
     * 获取更多数据
     * @param type 类型
     * @param date
     * @return
     */
    public Observable<ZhiHuDaily> getMoreDailyData(String type,String date){
        return ZhiHuRetrofitHelper.getInstance()
                .createService(ZhiHuInterface.class)
                .getMoreDaily(type,date)
                .filter(new Func1<ZhiHuDaily, Boolean>() {
                    @Override
                    public Boolean call(ZhiHuDaily zhiHuDaily) {
                        return zhiHuDaily!=null;
                    }
                });
    }

    /**
     * 获取详情数据
     * @param id
     * @return
     */
    public Observable<ZhiHuDailyDetail> getZhiHuDailyDetail(long id){
        return ZhiHuRetrofitHelper.getInstance()
                .createService(ZhiHuInterface.class)
                .getDailyDetail(id)
                .filter(new Func1<ZhiHuDailyDetail, Boolean>() {
                    @Override
                    public Boolean call(ZhiHuDailyDetail detail) {
                        return detail!=null;
                    }
                });
    }
}
