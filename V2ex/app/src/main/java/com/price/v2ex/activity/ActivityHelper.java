package com.price.v2ex.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

/**
 * Created by YC on 15-1-6.
 */
public class ActivityHelper {

    public static void startActivityWithTransition(Activity activity, View sharedElement, String sharedElementName, Intent intent) {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, sharedElement, sharedElementName);
        activity.startActivity(intent, optionsCompat.toBundle());
    }
}
