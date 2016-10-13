package com.aqtc.bmobnews.view;

import com.aqtc.bmobnews.bean.base.BaseGankData;
import com.aqtc.bmobnews.view.base.MvpView;

import java.util.ArrayList;

/**
 * Created by markzl on 2016/10/13.
 * email:1015653112@qq.com
 */

public interface GalleryView extends MvpView {

    /**
     * 获取福利类型数据成功
     *
     * @param baseGankDatas
     */
    void onGetGalleryDataSuccess(ArrayList<BaseGankData> baseGankDatas, boolean refresh);
}
