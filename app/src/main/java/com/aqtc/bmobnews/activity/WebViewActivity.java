package com.aqtc.bmobnews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.activity.base.BaseToolbarActivity;

import butterknife.BindView;

/**
 * Created by markzl on 2016/10/17.
 * email:1015653112@qq.com
 */

public class WebViewActivity extends BaseToolbarActivity {

    private static final String EXTRA_CATEGORY_URL = "extra_category_url";
    private static final String EXTRA_CATEGORY_TITLE = "extra_category_title";

    private static final String EXTRA_GANK_TYPE = "extra_gank_type";

    private static final int PROGRESS_RATIO = 1000;

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.pb_webview)
    ProgressBar pb_webview;

    @Override
    public void initData() {
        this.enableWebSetting();
        this.webView.loadUrl(this.getUrl());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(this.getUrlTitle());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void enableWebSetting() {
        //enableJavaScript
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //enableCaching
        this.webView.getSettings().setAppCachePath(getFilesDir() + getPackageName() + "/cache");
        this.webView.getSettings().setAppCacheEnabled(true);
        this.webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //enableCustomClients
        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
        this.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                WebViewActivity.this.setProgress(newProgress);
                setProgress(newProgress * PROGRESS_RATIO);
                if (newProgress >= 80) {
                    WebViewActivity.this.pb_webview.setVisibility(View.GONE);
                } else {
                    WebViewActivity.this.pb_webview.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        //enableAdjust
        this.webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.webView.getSettings().setLoadWithOverviewMode(true);
        //zoomedOut
        this.webView.getSettings().setLoadWithOverviewMode(true);
        this.webView.getSettings().setUseWideViewPort(true);
        this.webView.getSettings().setSupportZoom(true);
    }

    private String getUrl() {
        Intent intent = getIntent();
        Log.i("xys",intent.getStringExtra(this.EXTRA_CATEGORY_URL));
        return intent.getStringExtra(this.EXTRA_CATEGORY_URL);

    }

    private String getUrlTitle() {
        Intent intent = getIntent();
        return intent.getStringExtra(this.EXTRA_CATEGORY_TITLE);
    }

    private String getGankType() {
        Intent intent = getIntent();
        return intent.getStringExtra(this.EXTRA_GANK_TYPE);
    }

    public static void toURL(Context context, String url, String title, String type) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_CATEGORY_URL, url);
        intent.putExtra(EXTRA_CATEGORY_TITLE, title);
        intent.putExtra(EXTRA_GANK_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.webView != null)
            this.webView.destroy();
    }
}
