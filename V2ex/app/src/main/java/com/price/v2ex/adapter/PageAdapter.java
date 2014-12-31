package com.price.v2ex.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.price.v2ex.R;


/**
 * 分页Adapter，使用ProgressBar样式的Footer
 *
 * Created by YangChao on 14-12-23.
 */
public abstract class PageAdapter extends HeaderFooterRecyclerAdapter {

    private boolean mUseFooter = true;

    public void showFooter(boolean show) {
        mUseFooter = show;
    }

    @Override
    public int getBasicItemType(int position) {
        return 0;
    }

    @Override
    public boolean useFooter() {
        return mUseFooter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_footer, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        FooterHolder holder = new FooterHolder(view);
        return holder;
    }

    @Override
    public void onBindFooterView(RecyclerView.ViewHolder holder, int position) {

    }

    class FooterHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public FooterHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(android.R.id.progress);
        }
    }
}
