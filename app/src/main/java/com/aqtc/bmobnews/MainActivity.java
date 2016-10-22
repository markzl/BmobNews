package com.aqtc.bmobnews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.aqtc.bmobnews.activity.AboutActivity;
import com.aqtc.bmobnews.activity.LoginActivity;
import com.aqtc.bmobnews.activity.RegisterActivity;
import com.aqtc.bmobnews.activity.base.BaseActivity;
import com.aqtc.bmobnews.fragment.WelfareFragment;
import com.aqtc.bmobnews.fragment.ImportFragment;
import com.aqtc.bmobnews.fragment.SlideshowFragment;
import com.aqtc.bmobnews.fragment.ZhiHuFragment;
import com.aqtc.bmobnews.util.SnackbarUtil;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private Fragment[] fragments;
    private int currentTabIndex;
    private long exitTime;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public int getLayoutId() {
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        return R.layout.activity_main;
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initFragment();
        //注意只有通过获取headview的方式才能取到头布局中的id
        Button btn_login = (Button) navigationView.getHeaderView(0).findViewById(R.id.btn_login);
        Button btn_register = (Button) navigationView.getHeaderView(0).findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        fab.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        //Bmob.initialize(this, "ea2eab1f5e98a6742880828b66cb7a10");
    }

    @Override
    protected void initData() {
    }

    @Override
    public void initToolBar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFragment() {

        fragments = new Fragment[]{ new ZhiHuFragment(),new ImportFragment(), new WelfareFragment(),
                new SlideshowFragment()};
        switchFragment(0, "每日日报");
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    public void switchFragment(int index, String title) {

        toolbar.setTitle(title);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fragments[currentTabIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.fl_contain, fragments[index]);
        }
        transaction.show(fragments[index]).commit();
        currentTabIndex = index;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.btn_register:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.fab:
                Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_camera:
                switchFragment(0, "每日日报");
                break;
            case R.id.nav_gallery:
                switchFragment(1, "每日干货");
                break;
            case R.id.nav_slideshow:
                switchFragment(2, "每日福利");
                break;
            case R.id.nav_manage:
                switchFragment(3, "每日推荐");
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            logoutApp();
        }
        return true;
    }

    private void logoutApp() {

        if (System.currentTimeMillis() - exitTime > 2000) {
            SnackbarUtil.showMessage(drawer, "再按一次返回键退出BmobNews");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
