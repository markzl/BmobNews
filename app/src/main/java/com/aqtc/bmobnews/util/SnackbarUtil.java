package com.aqtc.bmobnews.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by markzl on 2016/9/7.
 * email:1015653112@qq.com
 */
public class SnackbarUtil {

    public static void showMessage(View view,String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
