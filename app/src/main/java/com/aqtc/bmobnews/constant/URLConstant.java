package com.aqtc.bmobnews.constant;

/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class URLConstant {

    public static int DEFAULT_SIZE=10;
    public static int DEFAULT_PAGE=1;

    public static String YEAR="2016";
    public static String MONTH="09";
    public static String DAY="08";

    public static String BASE_URL="http://gank.io/api/";
    public static String GALLERY_URL=BASE_URL+"data/福利/"+DEFAULT_SIZE+"/"+DEFAULT_PAGE;


    public static String HISTORY_URL=BASE_URL+"history/content/"+DEFAULT_SIZE+"/"+DEFAULT_PAGE;

    public static String DAILY_URL=BASE_URL+"day/2016/09/13";
    /**
     * 福利分页加载数据url
     * @param page
     * @return
     */
    public static String getGalleryUrl(int page){

       return BASE_URL+"data/福利/"+DEFAULT_SIZE+"/"+page;
    }
    /**
     * 每日干货分页加载数据url
     * @param page
     * @return
     */
    public static String getHistoryUrl(int page){
        return BASE_URL+"history/content/"+DEFAULT_SIZE+"/"+page;
    }

    public static String getDailyUrl(String year,String month,String day){

        return BASE_URL+"day/"+year+"/"+month+"/"+day;
    }
}
