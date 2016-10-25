package com.aqtc.bmobnews.view;

import com.aqtc.bmobnews.bean.gank.base.BaseGankData;
import com.aqtc.bmobnews.view.base.MvpView;

import java.util.ArrayList;

/**
 * author: markzl
 * time: 2016/10/25 11:10
 * email: 1015653112@qq.com
 */

public interface CategoryView extends MvpView{

    /**
     * 获取分类数据成功
     * @param gankDatas
     */
    void onGetCategoryDataSuccess(ArrayList<BaseGankData> gankDatas,int page);
}
