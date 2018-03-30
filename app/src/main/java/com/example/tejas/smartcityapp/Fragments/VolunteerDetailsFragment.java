package com.example.tejas.smartcityapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tejas.smartcityapp.Items.VolunteerItem;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.RecyclerAdapter.VolunteerRecyclerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolunteerDetailsFragment extends Fragment {


    public VolunteerDetailsFragment() {
        // Required empty public constructor
    }

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ArrayList<VolunteerItem> volunteerItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volunteer_details, container, false);

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.volunteer_detail_swipeRefresh);
        recyclerView=(RecyclerView)view.findViewById(R.id.volunteer_detail_recycler);

        volunteerItems=new ArrayList<>();
        volunteerItems.add(new VolunteerItem("Tejas Chheda","9930209301"));
        volunteerItems.add(new VolunteerItem("Mithil Dani","022123456789"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new VolunteerRecyclerAdapter(volunteerItems,getContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

}
