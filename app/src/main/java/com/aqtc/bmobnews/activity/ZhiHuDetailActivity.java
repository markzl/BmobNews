package com.aqtc.bmobnews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.activity.base.BaseToolbarActivity;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyDetail;
import com.aqtc.bmobnews.bean.zhihu.ZhiHuDailyExtra;
import com.aqtc.bmobnews.presenter.ZhiHuDetailPresenter;
import com.aqtc.bmobnews.util.GlideUtils;
import com.aqtc.bmobnews.util.HtmlUtil;
import com.aqtc.bmobnews.util.SnackbarUtil;
import com.aqtc.bmobnews.view.ZhiHuDetailView;

import butterknife.BindView;


/**
 * author: markzl
 * time: 2016/10/22 11:11
 * email: 1015653112@qq.com
 */

public class ZhiHuDetailActivity extends BaseToolbarActivity implements ZhiHuDetailView {

    private final static String ZHIHU_EXTRA_ID = "zhihu_daily_id";
    private final static String ZHIHU_EXTRA_TITLE = "zhihu_daily_title";

    @BindView(R.id.coll_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.detail_image)
    ImageView mDetailImage;

    @BindView(R.id.detail_title)
    TextView mDetailTitle;

    @BindView(R.id.detail_source)
    TextView mDetailSource;

    @BindView(R.id.detail_web_view)
    WebView mDetailWebView;

    private ZhiHuDetailPresenter mZhiHuDetailPresenter;

    public static void startActivity(Context context, long id, String title) {

        Intent intent = new Intent(context, ZhiHuDetailActivity.class);
        intent.putExtra(ZHIHU_EXTRA_ID, id);
        intent.putExtra(ZHIHU_EXTRA_TITLE, title);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = this.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mCollapsingToolbarLayout.setTitleEnabled(true);
        mActionBar.setTitle(this.getDailyDetailTitle());
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initData() {
        long id = this.getDailyDetailID();
        this.mZhiHuDetailPresenter = ZhiHuDetailPresenter.getInstance();
        this.mZhiHuDetailPresenter.atthachView(this);
        this.mZhiHuDetailPresenter.getDailyDetail(id);
    }

    public long getDailyDetailID() {
        Intent intent = getIntent();
        if (intent == null)
            return 0L;
        return (long) intent.getSerializableExtra(ZHIHU_EXTRA_ID);
    }

    public String getDailyDetailTitle() {
        Intent intent = getIntent();
        if (intent == null)
            return null;
        return intent.getStringExtra(ZHIHU_EXTRA_TITLE);
    }

    private long id;

    /**
     * 获取新闻详情数据成功
     *
     * @param zhiHuDailyDetail
     */
    @Override
    public void onGetDailyDetailDataSuccess(ZhiHuDailyDetail zhiHuDailyDetail) {
        if (zhiHuDailyDetail.id != 0) {
            id = zhiHuDailyDetail.id;
        }
        if (zhiHuDailyDetail.image != null) {
            GlideUtils.display(this.mDetailImage, zhiHuDailyDetail.image);
        }
        if (zhiHuDailyDetail.title != null) {
            this.mDetailTitle.setText(zhiHuDailyDetail.title);
        }
        if (zhiHuDailyDetail.image_source != null) {
            this.mDetailSource.setText(zhiHuDailyDetail.image_source);
        }
        //设置webview内容加载
        if (zhiHuDailyDetail.body != null) {
            String htmlData = HtmlUtil.createHtmlData(zhiHuDailyDetail);
            this.mDetailWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
        }
    }

    @Override
    public void onGetDailyExtralMessageSuccess(ZhiHuDailyExtra zhiHuDailyExtra) {
        Log.i("xys",zhiHuDailyExtra.short_comments+"");
        Log.i("xys",zhiHuDailyExtra.long_comments+"");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_zhihu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_share:
                SnackbarUtil.showMessage(mDetailWebView, "action_share");
                break;
            case R.id.action_star:
                break;
            case R.id.action_comment:
                if (id != 0) {
                    Log.i("xys","hello");
                    this.mZhiHuDetailPresenter.getDailyExtralMessage(id);
                }
                break;
            case R.id.action_praise:
                break;
        }
        return true;
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    protected void onDestroy() {
        this.mZhiHuDetailPresenter.detachView();
        super.onDestroy();
    }
}
