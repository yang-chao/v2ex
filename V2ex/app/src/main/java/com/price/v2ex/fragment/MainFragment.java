package com.price.v2ex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.price.v2ex.R;
import com.price.v2ex.common.view.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Created by YC on 15-1-9.
 */
public class MainFragment extends BaseFragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private FragmentStatePagerAdapter mAdapter;

    private ArrayList<TopicListFragmentHelper.TabItemFragment> mFragments = new ArrayList<TopicListFragmentHelper.TabItemFragment>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragments = TopicListFragmentHelper.getFragments(getActivity());
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        mAdapter = new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position).getFragment();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragments.get(position).getTitle();
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mAdapter);

        SlidingTabLayout.TabColorizer tabColorizer = new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(android.R.color.white);
            }

            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(android.R.color.transparent);
            }

        };
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabColorizer(tabColorizer);
        mSlidingTabLayout.setCustomTabView(R.layout.base_tab_view, R.id.tab_title);
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
    }

}
