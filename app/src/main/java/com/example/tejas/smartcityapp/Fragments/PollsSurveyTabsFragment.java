package com.example.tejas.smartcityapp.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tejas.smartcityapp.Adapters.PollsSurveyTabAdapter;
import com.example.tejas.smartcityapp.Adapters.ProjectCategoryAdapter;
import com.example.tejas.smartcityapp.R;

/**
 * Created by dani on 21/03/2018.
 */

public class PollsSurveyTabsFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public PollsSurveyTabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pollssurvey_tabs, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.pollssurvey_tabs_fragment_tablayout);
        viewPager = (ViewPager)view.findViewById(R.id.pollssurvey_tabs_fragment_viewpager);

        PollsSurveyTabAdapter adapter = new PollsSurveyTabAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
