package com.price.v2ex.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.price.v2ex.R;
import com.price.v2ex.volley.VolleyManager;
import com.price.v2ex.adapter.PageAdapter;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.VERTICAL;

/**
 * Created by YC on 14-12-30.
 */
public abstract class RequestListFragment<T> extends RequestFragment<T> implements SwipeRefreshLayout.OnRefreshListener  {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView.LayoutManager mLayoutManager;

    protected int mPageIndex = 0;
    protected boolean mIsLastPage = false;

    private RecyclerView.OnScrollListener mOnListScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == SCROLL_STATE_IDLE && !mIsLastPage) {
                int lastVisiblePos = -1;
                if (mLayoutManager instanceof LinearLayoutManager) {
                    lastVisiblePos = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                }
                int totalCount = mAdapter.getItemCount();

                if (lastVisiblePos == (totalCount - 1)) {
                    requestNetData(false);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 对于列表数据使用{@link #newRequest(boolean refresh, Response.Listener listener, Response.ErrorListener errorListener)}
     *
     * @param listener
     * @param errorListener
     * @return
     */
    @Override
    protected final Request onCreateRequest(Response.Listener listener, Response.ErrorListener errorListener) {
        return null;
    }

    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mSwipeLayout.setColorSchemeColors(getResources().getColor(R.color.theme_default_primary_dark));
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = onCreateLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = onCreateAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnScrollListener(mOnListScrollListener);

        requestNetData(true);
    }

    @Override
    public void onRefresh() {
        requestNetData(true);
    }

    @Override
    public void onResponse(T response) {
        super.onResponse(response);
        if (mSwipeLayout != null) {
            mSwipeLayout.setRefreshing(false);
        }
    }

    /**
     * 请求网络数据
     *
     * @param refresh true表示刷新列表，false表示加载更多（翻页）
     */
    protected void requestNetData(boolean refresh) {
        if (refresh) {
            mPageIndex = 0;
            mIsLastPage = false;
        } else {
            mPageIndex++;
            if (mAdapter instanceof PageAdapter) {
                ((PageAdapter) mAdapter).showFooter(true);
            }
        }
        Request request = newRequest(refresh, this, this);
        if (request != null) {
            if (mSwipeLayout != null) {
                mSwipeLayout.setRefreshing(true);
            }
            VolleyManager.addRequest(getActivity(), request);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return mSwipeLayout;
    }

    public void markLastPage(boolean isLastPage) {
        mIsLastPage = isLastPage;
        if (mIsLastPage && mAdapter instanceof PageAdapter) {
            ((PageAdapter) mAdapter).showFooter(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSwipeLayout = null;
    }

    protected RecyclerView.LayoutManager onCreateLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    protected abstract Request newRequest(final boolean refresh, Response.Listener<T> listener, Response.ErrorListener errorListener);

    protected abstract RecyclerView.Adapter onCreateAdapter(Context context);

}
