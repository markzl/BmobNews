package com.aqtc.bmobnews.bean;

import java.util.List;

/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class GankDialy {

    public boolean error;

    public List<Results> results;

    public class Results{

        public String id;
        public String content;
        public String publishedAt;
        public String title;
    }
}
