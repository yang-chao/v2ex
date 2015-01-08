package com.price.v2ex.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;


public class ActivityHelper {

	public static final String FRAGMENT_ARGS = "fragment_arg";
	public static final String FRAGMENT_NAME = "fragment_name";
	public static final String FRAGMENT_TAG = "fragment_tag";
	

	public static void startActivity(Context context, Fragment fragment) {
		Intent intent = new Intent(context, BaseActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(FRAGMENT_NAME, fragment.getClass().getName());
		bundle.putString(FRAGMENT_TAG, fragment.getClass().getName());
		bundle.putBundle(FRAGMENT_ARGS, fragment.getArguments());
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

    public static void startActivity(Context context, String fragmentName, String fragmentTag, Bundle arguments) {
        Intent intent = new Intent(context, BaseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_NAME, fragmentName);
        bundle.putString(FRAGMENT_TAG, fragmentTag);
        bundle.putBundle(FRAGMENT_ARGS, arguments);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
	
	public static void startActivityForResult(Fragment host, Fragment target) {
		Intent intent = new Intent(host.getActivity(), BaseActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(FRAGMENT_NAME, target.getClass().getName());
		bundle.putString(FRAGMENT_TAG, target.getClass().getName());
		bundle.putBundle(FRAGMENT_ARGS, target.getArguments());
		intent.putExtras(bundle);
//		host.startActivityForResult(intent, MainActivityHelper.REQUEST_CODE_COUPON);
	}
	
	/**
	 * Get the intent to start fragment.
	 * 
	 * @param context
	 * @param clss
	 * @param fragmentName
	 * @param fragmentTag
	 * @param args
	 * @return
	 */
	public static Intent getIntent(Context context, Class<?> clss, String fragmentName, String fragmentTag, Bundle args) {
		Intent intent = new Intent(context, clss);
		Bundle bundle = new Bundle();
		bundle.putString(FRAGMENT_NAME, fragmentName);
		bundle.putString(FRAGMENT_TAG, fragmentTag);
		bundle.putBundle(FRAGMENT_ARGS, args);
		intent.putExtras(bundle);
		return intent;
	}

	public static void initFragment(FragmentActivity activity, Intent intent, int containerId) {
		if (intent == null) {
			return;
		}

		Bundle bundle = intent.getExtras();
		if (bundle == null) {
			return;
		}
		
		String fragmentName = bundle.getString(FRAGMENT_NAME);
		String fragmentTag = bundle.getString(FRAGMENT_TAG);
		Bundle fragmentArgs = bundle.getBundle(FRAGMENT_ARGS);
		
		FragmentManager fm = activity.getSupportFragmentManager();
		Fragment fragment = fm.findFragmentByTag(fragmentTag);
		if (fragment == null) {
			FragmentTransaction ft = fm.beginTransaction();
			fragment = Fragment.instantiate(activity, fragmentName, fragmentArgs);
			ft.add(containerId, fragment, fragmentName);
			ft.commit();
		} else {
			if(fragment.isDetached()) {
				FragmentTransaction ft = fm.beginTransaction();
	            ft.attach(fragment);
	            ft.commit();
			}
		}
	}

    public static void startActivityWithTransition(Activity activity, View sharedElement, String sharedElementName, Intent intent) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, sharedElement, sharedElementName);
        activity.startActivity(intent, optionsCompat.toBundle());
    }
}
