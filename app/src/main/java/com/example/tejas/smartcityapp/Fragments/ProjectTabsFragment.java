package com.example.tejas.smartcityapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tejas.smartcityapp.Adapters.ProjectCategoryAdapter;
import com.example.tejas.smartcityapp.R;

public class ProjectTabsFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ProjectTabsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_tabs, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.project_tabs_fragment_tablayout);
        viewPager = (ViewPager)view.findViewById(R.id.project_tabs_fragment_viewpager);

        ProjectCategoryAdapter adapter=new ProjectCategoryAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
