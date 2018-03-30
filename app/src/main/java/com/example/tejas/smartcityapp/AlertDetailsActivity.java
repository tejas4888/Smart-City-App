package com.example.tejas.smartcityapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tejas.smartcityapp.Adapters.AlertTabsAdapter;
import com.example.tejas.smartcityapp.Adapters.ProjectCategoryAdapter;

public class AlertDetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);

        tabLayout = (TabLayout)findViewById(R.id.alert_details_tablayout);
        viewPager = (ViewPager)findViewById(R.id.alert_details_viewpager);

        AlertTabsAdapter adapter=new AlertTabsAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
