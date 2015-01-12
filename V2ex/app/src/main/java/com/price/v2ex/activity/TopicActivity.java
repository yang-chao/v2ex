package com.price.v2ex.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.price.v2ex.R;
import com.price.v2ex.common.ActivityHelper;
import com.price.v2ex.fragment.TopicFragment;

public class TopicActivity extends ActionBarActivity {

    private static final String PARAM_ID = "param_id";

    public static void startTopicActivity(Context context, String topicId, View sharedElement, String sharedElementName) {
        Intent intent = new Intent(context, TopicActivity.class);
        intent.putExtra(PARAM_ID, topicId);

        ActivityHelper.startActivityWithTransition((Activity) context, sharedElement, sharedElementName, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        if (savedInstanceState == null) {
            String topicId = getIntent().getStringExtra(PARAM_ID);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, TopicFragment.newInstance(topicId))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.a, menu);
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
