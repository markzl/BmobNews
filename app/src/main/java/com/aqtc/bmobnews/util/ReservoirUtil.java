package com.aqtc.bmobnews.util;

import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirDeleteCallback;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * Created by markzl on 2016/10/14.
 * email:1015653112@qq.com
 */

public class ReservoirUtil {

    private static final String TAG = "ReservoirUtils";

    private static ReservoirUtil instance;

    public synchronized static ReservoirUtil getInstance() {

        if (instance == null) {
            instance = new ReservoirUtil();
        }
        return instance;
    }

    public void put(String key, Object object) {

        if (object == null) return;

        Reservoir.putAsync(key, object, new ReservoirPutCallback() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "Put success: key=" + key + " object=" + object.getClass());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public boolean contains(String key) {

        try {
            return Reservoir.contains(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void delete(String key) {

        if (this.contains(key)) {
            try {
                Reservoir.delete(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh(String key, Object object) {

        if (this.contains(key)) {

            Reservoir.deleteAsync(key, new ReservoirDeleteCallback() {
                @Override
                public void onSuccess() {
                    ReservoirUtil.this.put(key, object);
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            ReservoirUtil.this.put(key, object);
        }
    }

    public <T> Observable<T> get(String key, Class<T> clz) {

        return Reservoir.getAsync(key, clz);
    }

    public <T> Observable<T> get(Class<T> clz) {

        String key = clz.getSimpleName();
        return get(key, clz);
    }

    public <T> void get(final String key, final Type typeOfT, final ReservoirGetCallback<T> callback) {

        Reservoir.getAsync(key, typeOfT, callback);
    }
}
