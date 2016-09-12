package com.aqtc.bmobnews.bean;

import java.util.List;

/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class ImageBean {

    public boolean error;
    public List<Results> results;

    public class Results{

        public String id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String  who;

    }
}

