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

/**
 * Created by YC on 15-1-9.
 */
public class MainFragment extends BaseFragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private FragmentStatePagerAdapter mAdapter;

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
                switch (position) {
                    case 0:
                        return new NodesFragment();
                    case 1:
                        return LatestFragment.newInstance();
                    case 2:
                        return HotFragment.newInstance();
                    default:
                        return null;
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.main_tab_node);
                    case 1:
                        return getString(R.string.main_tab_latest);
                    case 2:
                        return getString(R.string.main_tab_hot);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
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
