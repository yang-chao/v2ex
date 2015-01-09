package com.price.v2ex.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.price.v2ex.R;

/**
 * Created by YC on 15-1-9.
 */
public class TopicsFragment extends BaseFragment {

    private ViewPager mViewPager;
    private FragmentStatePagerAdapter mAdapter;

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topics, container, false);
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
                        return LatestFragment.newInstance();
                    case 1:
                        return HotFragment.newInstance();
                    default:
                        return null;
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.main_tab_latest);
                    case 1:
                        return getString(R.string.main_tab_hot);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
        mViewPager.setAdapter(mAdapter);
    }

}
