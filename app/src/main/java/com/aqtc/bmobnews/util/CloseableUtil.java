package com.aqtc.bmobnews.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class CloseableUtil {

    public static void closeQuitely(Closeable closeable){

        if(closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
