package com.price.v2ex.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.price.v2ex.R;
import com.price.v2ex.constants.Urls;
import com.price.v2ex.provider.V2exContract.Nodes;

import java.util.ArrayList;

/**
 * Created by YC on 15-1-16.
 */
public class TopicListFragmentHelper {

    public static final String COLUMN_ID_HOT = "hot";
    public static final String COLUMN_ID_LATEST = "latest";
    public static final String COLUMN_ID_NORMAL = "normal";

    public interface TabItemFragment {

        public String getTitle();

        public Fragment getFragment();
    }


    public static ArrayList<TabItemFragment> getFragments(Context context) {
        ArrayList<TabItemFragment> fragments = new ArrayList<TabItemFragment>();
        // 默认节点
        fragments.addAll(getDefaultFragments(context));

        // 用户选择节点
        Uri uri = Nodes.CONTENT_URI;
        String[] projections = {Nodes.NODE_ID, Nodes.NODE_NAME};
        String selection = Nodes.NODE_STATUS + "=?";
        String[] selectionArgs = {"1"};
        final Cursor cursor = context.getContentResolver().query(uri, projections, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String nodeId = cursor.getString(cursor.getColumnIndex(Nodes.NODE_ID));
                final String url = String.format(Urls.NODE_TOPICS, nodeId);
                TabItemFragment tabItemFragment = new TabItemFragment() {

                    @Override
                    public String getTitle() {
                        return cursor.getString(cursor.getColumnIndex(Nodes.NODE_TITLE));
                    }

                    @Override
                    public Fragment getFragment() {
                        return TopicListFragment.newInstance(url, TopicListFragmentHelper.COLUMN_ID_NORMAL);
                    }
                };
                fragments.add(tabItemFragment);
            }
            cursor.close();
        }

        return fragments;
    }

    private static ArrayList<TabItemFragment> getDefaultFragments(final Context context) {
        ArrayList<TabItemFragment> fragments = new ArrayList<TabItemFragment>();

        // 所有节点
        TabItemFragment tabItemFragment = new TabItemFragment() {

            @Override
            public String getTitle() {
                return context.getString(R.string.main_tab_node);
            }

            @Override
            public Fragment getFragment() {
                return new NodesFragment();
            }
        };
        fragments.add(tabItemFragment);

        // 热门
        tabItemFragment = new TabItemFragment() {

            @Override
            public String getTitle() {
                return context.getString(R.string.main_tab_hot);
            }

            @Override
            public Fragment getFragment() {
                return TopicListFragment.newInstance(Urls.HOT, TopicListFragmentHelper.COLUMN_ID_HOT);
            }
        };
        fragments.add(tabItemFragment);

        // 最新
        tabItemFragment = new TabItemFragment() {

            @Override
            public String getTitle() {
                return context.getString(R.string.main_tab_latest);
            }

            @Override
            public Fragment getFragment() {
                return TopicListFragment.newInstance(Urls.LATEST, TopicListFragmentHelper.COLUMN_ID_LATEST);
            }
        };
        fragments.add(tabItemFragment);

        return fragments;
    }

}
