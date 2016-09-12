package com.aqtc.bmobnews.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.view.GlideCircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.orhanobut.logger.Logger;


/**
 * Created by markzl on 2016/9/12.
 * email:1015653112@qq.com
 */
public class GlideUtils {

    private static final String TAG = "GlideUtils";

    /**
     * Glide加载图片
     *
     * @param view
     * @param url
     */
    public static void display(ImageView view, String url) {
        displayUrl(view, url, R.mipmap.img_default_gray);
    }

    /**
     * Glide加载图片
     * @param view
     * @param url
     * @param defaultImageId
     */
    private static void displayUrl(final ImageView view, String url, @DrawableRes int defaultImageId) {

        if (view == null) {
            Logger.e("GlideUtils -> display -> imageView");
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(defaultImageId)
                    .crossFade()
                    .centerCrop()
                    .into(view)
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            if (!view.isShown()) {
                                view.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Glide加载图片
     * @param view
     * @param resId
     */
    public static  void displayNative(final ImageView view,@DrawableRes int resId ){

        if(view==null){
            Logger.e("GlideUtils -> display -> iamgeview is null");
            return;
        }
        Context context=view.getContext();
        if(context instanceof  Activity){
            if(((Activity) context).isFinishing()){
                return;
            }
        }

        try {
            Glide.with(context)
                    .load(resId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .centerCrop()
                    .into(view)
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            if(!view.isShown()){
                                view.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayCircleHeader(ImageView view,@DrawableRes int res){

        if(view==null){
            Logger.e("GlideUtils -> display -> imageview is null");
            return;
        }
        Context context=view.getContext();
        if(context instanceof Activity){
            if(((Activity) context).isFinishing()){
                return;
            }
        }
        try {
            Glide.with(context)
                    .load(res)
                    .centerCrop()
                    .placeholder(R.mipmap.img_default_gray)
                    .bitmapTransform(new GlideCircleTransform(context))
                    .crossFade()
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
