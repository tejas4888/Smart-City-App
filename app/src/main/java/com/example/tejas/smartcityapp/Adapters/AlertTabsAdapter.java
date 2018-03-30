package com.example.tejas.smartcityapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tejas.smartcityapp.Fragments.AlertDetailsFragment;
import com.example.tejas.smartcityapp.Fragments.VolunteerDetailsFragment;

/**
 * Created by Tejas on 30-03-2018.
 */

public class AlertTabsAdapter extends FragmentPagerAdapter {


    public AlertTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0)
        {
            return new AlertDetailsFragment();
        }else
        {
            return new VolunteerDetailsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Details";
        }  else {
            return "Volunteers";
        }
    }
}
