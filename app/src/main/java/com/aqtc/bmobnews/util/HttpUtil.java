package com.aqtc.bmobnews.util;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 * 测试用
 */
public class HttpUtil {

    public static void  getData(final String str, final Handler handler){


        new Thread(){
            @Override
            public void run() {
                String result="";
                InputStream inputStream=null;
                try {
                    URL url = new URL(str);
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(2 * 1000);
                    connection.setConnectTimeout(2*1000);
                    if(connection.getResponseCode()==200){
                        inputStream=connection.getInputStream();
                        result=StreamUtil.streamToString(inputStream);
                    }
                    Message msg=Message.obtain();
                    msg.obj=result;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
