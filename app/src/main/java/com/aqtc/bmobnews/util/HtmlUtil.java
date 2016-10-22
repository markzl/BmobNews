package com.aqtc.bmobnews.util;

import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyDetail;

import java.util.List;

/**
 * author: markzl
 * time: 2016/10/22 21:38
 * email: 1015653112@qq.com
 */

public class HtmlUtil {

    //css样式,隐藏header
    private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";

    //css style tag,需要格式化
    private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";

    // js script tag,需要格式化
    private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

    public static final String MIME_TYPE = "text/html; charset=utf-8";

    public static final String ENCODING = "utf-8";

    private HtmlUtil() {

    }

    /**
     * 根据css链接生成Link标签
     *
     * @param url
     * @return
     */
    public static String createCssTag(String url) {
        return String.format(NEEDED_FORMAT_CSS_TAG, url);
    }

    /**
     * 根据多个css链接生成Link标签
     * @param urls
     * @return
     */
    public static String createCssTag(List<String> urls) {
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createCssTag(url));
        }
        return sb.toString();
    }

    /**
     * 根据js链接生成Script标签
     * @param url
     * @return
     */
    public static String createJsTag(String url){
        return String.format(NEEDED_FORMAT_JS_TAG,url);
    }

    /**
     * 根据多个js链接生成Script标签
     * @param urls
     * @return
     */
    public static String createJsTag(List<String> urls){
        final StringBuilder sb = new StringBuilder();
        for (String url:urls) {
            sb.append(createJsTag(url));
        }
        return sb.toString();
    }

    /**
     * 根据样式标签，html标签，js标签
     * 生成完整的HTML文档
     * @param html
     * @param css
     * @param js
     * @return
     */
    private static String createHtmlData(String html,String css,String js){
        return css.concat(HIDE_HEADER_STYLE).concat(html).concat(js);
    }

    /**
     * 生成完整的HTML文档
     * @param detail
     * @return
     */
    public static String createHtmlData(ZhiHuDailyDetail detail){
        final String css = HtmlUtil.createCssTag(detail.css);
        final String js = HtmlUtil.createJsTag(detail.js);
        return createHtmlData(detail.body,css,js);
    }

}
