package com.example.tejas.smartcityapp.Fragments;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tejas.smartcityapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterBottomSheetFragment extends BottomSheetDialogFragment {


    public FilterBottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false);



        return view;
    }

}
