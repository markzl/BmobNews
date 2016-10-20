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

    @GET("{type}/latest")
    Observable<ZhiHuDaily> getDaily(@Path("type") String type);

}
