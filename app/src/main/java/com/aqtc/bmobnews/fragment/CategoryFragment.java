package com.aqtc.bmobnews.fragment;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.aqtc.bmobnews.R;
import com.aqtc.bmobnews.data.gank.GankApi;
import com.aqtc.bmobnews.fragment.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * author: markzl
 * time: 2016/10/25 10:44
 * email: 1015653112@qq.com
 */

public class CategoryFragment extends BaseFragment {

    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<String> titles = Arrays.asList(GankApi.DATA_TYPE_ANDROID,
            GankApi.DATA_TYPE_IOS, GankApi.DATA_TYPE_JS,
            GankApi.DATA_TYPE_APP,  GankApi.DATA_TYPE_EXTEND_RESOURCES);

    public static CategoryFragment newInstance(){
        return new CategoryFragment();
    }

    @Override
    public View getInflaterView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_category, null, false);
    }

    @Override
    public void initView() {
        if(Build.VERSION.SDK_INT>=21){
            this.getActivity().findViewById(R.id.app_bar_layout).setElevation(0);
        }
        mViewPager.setAdapter(new CategoryPageAdapter(getChildFragmentManager()));
        mViewPager.setOffscreenPageLimit(1);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class CategoryPageAdapter extends FragmentStatePagerAdapter {

        public CategoryPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CategorySimpleFragment.newInstance(titles.get(position));
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
