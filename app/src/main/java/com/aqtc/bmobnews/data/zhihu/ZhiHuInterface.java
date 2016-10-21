package com.aqtc.bmobnews.data.zhihu;

        import com.aqtc.bmobnews.bean.zhihu.ZhiHuDaily;

        import retrofit2.http.GET;
        import retrofit2.http.Path;
        import rx.Observable;

/**
 * Created by markzl on 2016/10/20.
 * email:1015653112@qq.com
 */

public interface ZhiHuInterface {

    /**
     * 获取type类型最新数据集合
     * @param type 数据类型
     * @return
     */
    @GET("{type}/latest")
    Observable<ZhiHuDaily> getDaily(@Path("type") String type);

    /**
     * 根据type 和date 获取当日数据
     * @param type 数据类型
     * @param date 日期
     * @return
     */
    @GET("{type}/before/{date}")
    Observable<ZhiHuDaily> getMoreDaily(@Path("type") String type ,@Path("date") String date);


}
