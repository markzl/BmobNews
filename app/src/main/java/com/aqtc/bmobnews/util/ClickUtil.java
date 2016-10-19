package com.aqtc.bmobnews.util;

import android.util.Log;

/**
 * Created by markzl on 2016/10/18.
 * email:1015653112@qq.com
 */

public class ClickUtil {

    private static final String TAG = ClickUtil.class.getSimpleName();
    private static long lastClickTime = 0L;
    private static final boolean isDebug = true;
    private static final String BLANK_LOG = "\t";

    private ClickUtil() {

    }

    public static boolean isFastDoubleClick() {

        long nowTime = System.currentTimeMillis();
        if (isDebug) {
            Log.d(TAG, "nowTime:" + nowTime);
            Log.d(TAG, "lastClickTime:" + lastClickTime);
            Log.d(TAG, "时间间隔:" + (nowTime - lastClickTime));
        }
        if ((nowTime - lastClickTime) < 500) {

            if (isDebug) {
                Log.d(TAG, "快速点击");
                Log.d(TAG, BLANK_LOG);
            }
            return true;
        } else {
            lastClickTime = nowTime;
            if (isDebug) {
                Log.d(TAG, "lastClickTime:" + lastClickTime);
                Log.d(TAG, "不是快速点击");
                Log.d(TAG, BLANK_LOG);
            }
            return false;
        }

    }

}
