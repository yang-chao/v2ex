package com.price.v2ex.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.adapter.TopicAdapter;
import com.price.v2ex.constants.Urls;
import com.price.v2ex.model.Topic;
import com.price.v2ex.request.TopicListRequest;

import java.util.List;


public class HotFragment extends RequestListFragment {

    private OnFragmentInteractionListener mListener;

    public static HotFragment newInstance() {
        HotFragment fragment = new HotFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Request newRequest(boolean refresh) {
        return new TopicListRequest(getActivity(), Urls.HOT, Topic[].class,
           new Response.Listener<List<Topic>>() {
            @Override
            public void onResponse(List<Topic> response) {
                markLastPage(true);
                AdapterHandler.notifyDataSetChanged(getAdapter(), response, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    protected RecyclerView.Adapter onCreateAdapter(Context context) {
        return new TopicAdapter(getActivity());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }

}
