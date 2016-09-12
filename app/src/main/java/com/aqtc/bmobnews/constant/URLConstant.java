package com.aqtc.bmobnews.constant;

/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class URLConstant {

    public static int DEFAULT_SIZE=10;
    public static int DEFAULT_PAGE=1;
    public static String BASE_URL="http://gank.io/api/";
    public static String GALLERY_URL=BASE_URL+"data/"+"福利/"+DEFAULT_SIZE+"/"+DEFAULT_PAGE;


    public static String HISTORY_URL=BASE_URL+"history/content/"+DEFAULT_SIZE+"/"+DEFAULT_PAGE;
    /**
     * 福利分页加载数据url
     * @param page
     * @return
     */
    public static String getGalleryUrl(int page){

       return BASE_URL+"福利/"+DEFAULT_SIZE+"/"+page;
    }
    /**
     * 每日干活分页加载数据url
     * @param page
     * @return
     */
    public static String getHistoryUrl(int page){
        return BASE_URL+"history/content/"+DEFAULT_SIZE+"/"+page;
    }
}
