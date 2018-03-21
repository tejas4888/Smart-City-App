package com.example.tejas.smartcityapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tejas.smartcityapp.Fragments.PollsFragment;

/**
 * Created by dani on 20/03/2018.
 */

public class PollsSurveyTabAdapter extends FragmentPagerAdapter {

    public PollsSurveyTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PollsFragment();
        }  else {
            return new PollsFragment();
            //return new SurveyFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Polls";
        }  else {
            return "Survey";
        }
    }

}
