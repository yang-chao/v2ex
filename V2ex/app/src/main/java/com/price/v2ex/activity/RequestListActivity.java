package com.price.v2ex.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.price.v2ex.R;
import com.price.v2ex.common.BaseActivity;
import com.price.v2ex.volley.VolleyManager;
import com.price.v2ex.adapter.PageAdapter;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.OnScrollListener;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by YC on 14/12/20.
 */
public abstract class RequestListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    private LinearLayoutManager mLayoutManager;

    protected int mPageIndex = 0;
    protected boolean mIsLastPage = false;

    private OnScrollListener mOnListScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == SCROLL_STATE_IDLE && !mIsLastPage) {
                int lastVisiblePos = mLayoutManager.findLastVisibleItemPosition();
                int totalCount = mAdapter.getItemCount();

                if (lastVisiblePos == (totalCount - 1)) {
                    requestData(false);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = onCreateContentView();
        if (view == null) {
            setContentView(R.layout.activity_list);
        } else {
            setContentView(view);
        }

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeLayout.setOnRefreshListener(this);
//        mSwipeLayout.setColorSchemeColors(getResources().getColor(R.color.base_orange));

        mRecyclerView = (RecyclerView) findViewById(android.R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = onCreateAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(mOnListScrollListener);

        requestData(true);
    }

    protected View onCreateContentView() {
        return null;
    }

    @Override
    public void onRefresh() {
        requestData(true);
    }

    /**
     * 请求网络数据
     *
     * @param refresh true表示刷新列表，false表示加载更多（翻页）
     */
    protected void requestData(boolean refresh) {
        if (refresh) {
            mPageIndex = 0;
            mIsLastPage = false;
        } else {
            mPageIndex++;
            if (mAdapter instanceof PageAdapter) {
                ((PageAdapter) mAdapter).showFooter(true);
            }
        }
        Request request = newRequest(refresh);
        if (request != null) {
            if (mSwipeLayout != null) {
                mSwipeLayout.setRefreshing(true);
            }
            VolleyManager.addRequest(this, request);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public Adapter getAdapter() {
        return mAdapter;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return mSwipeLayout;
    }

    public void setIsLastPage(boolean isLastPage) {
        mIsLastPage = isLastPage;
    }

    protected abstract Request newRequest(final boolean refresh);

    protected abstract Adapter onCreateAdapter(Context context);

    @Override
    protected void onResume() {
        super.onResume();
    }
}
