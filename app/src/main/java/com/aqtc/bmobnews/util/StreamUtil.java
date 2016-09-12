package com.aqtc.bmobnews.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class StreamUtil {

    public static String streamToString(InputStream inputStream) {

        String result = "";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;

        try {
            while ((length = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, length);
                out.flush();
            }
            result=out.toString();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseableUtil.closeQuitely(out);
        }
        return result;
    }
}
