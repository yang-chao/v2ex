package com.price.v2ex.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.price.v2ex.R;


/**
 * Created by YC on 14-10-27.
 */
public class BaseActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActivityHelper.initFragment(this, getIntent(), R.id.content_frame);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
