package com.aqtc.bmobnews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import com.aqtc.bmobnews.fragment.GalleryFragment;
import com.aqtc.bmobnews.fragment.ImportFragment;
import com.aqtc.bmobnews.fragment.SlideshowFragment;
import com.aqtc.bmobnews.fragment.ToolsFragment;
import com.aqtc.bmobnews.util.SnackbarUtil;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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
    public void initToolBar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFragment() {

        fragments = new Fragment[]{new ImportFragment(), new GalleryFragment(),
                new SlideshowFragment(), new ToolsFragment()};
        switchFragment(0, "BmobNews");
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_camera:
                switchFragment(0, "BmobNews");
                break;
            case R.id.nav_gallery:
                switchFragment(1, "Gallery");
                break;
            case R.id.nav_slideshow:
                switchFragment(2, "Slideshow");
                break;
            case R.id.nav_manage:
                switchFragment(3, "Tools");
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
}
