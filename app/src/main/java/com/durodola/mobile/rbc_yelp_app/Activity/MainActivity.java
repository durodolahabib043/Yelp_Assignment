package com.durodola.mobile.rbc_yelp_app.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.durodola.mobile.rbc_yelp_app.Fragment.ResturantFragment;
import com.durodola.mobile.rbc_yelp_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// attach fragment to activity
        ResturantFragment frag = ResturantFragment.newInstance();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.contentlayout, frag);
        transaction.commit();


    }


}
