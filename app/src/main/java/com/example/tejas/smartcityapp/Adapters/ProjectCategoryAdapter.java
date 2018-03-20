package com.example.tejas.smartcityapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tejas.smartcityapp.Fragments.ProjectTabsFragment;
import com.example.tejas.smartcityapp.Fragments.ProjectType1Fragment;
import com.example.tejas.smartcityapp.Fragments.ProjectType2Fragment;

/**
 * Created by Tejas on 20-03-2018.
 */

public class ProjectCategoryAdapter extends FragmentPagerAdapter {

    public ProjectCategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ProjectType1Fragment();
        }  else {
            return new ProjectType2Fragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Type 1";
        }  else {
            return "Type 2";
        }
    }

}
