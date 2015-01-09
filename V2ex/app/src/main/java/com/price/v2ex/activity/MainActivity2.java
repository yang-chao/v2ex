package com.price.v2ex.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.price.v2ex.R;
import com.price.v2ex.base.ActivityHelper;
import com.price.v2ex.fragment.HotFragment;
import com.price.v2ex.fragment.LatestFragment;

public class MainActivity2 extends BaseActivity {

    private ViewPager mViewPager;
    private FragmentStatePagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentManager fm = getSupportFragmentManager();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
