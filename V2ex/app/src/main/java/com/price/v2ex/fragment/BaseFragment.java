package com.price.v2ex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.price.v2ex.R;

/**
 * Created by YC on 14-12-30.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        RelativeLayout rootView = (RelativeLayout) view.findViewById(R.id.root);
        RelativeLayout.LayoutParams params;

        // content
        View contentView = onCreateContentView(inflater, container, savedInstanceState);
        if (contentView != null) {
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rootView.addView(contentView, params);
        }

        // progress
        View progressView = inflater.inflate(R.layout.base_progressbar, container, false);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        rootView.addView(progressView, params);

        return view;
    }

    protected void showProgress(boolean show) {
        if (!isAdded()) {
            return;
        }
        getView().findViewById(android.R.id.progress).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    protected abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

}
