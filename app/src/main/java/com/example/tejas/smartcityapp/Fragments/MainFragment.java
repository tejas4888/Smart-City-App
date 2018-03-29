package com.example.tejas.smartcityapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tejas.smartcityapp.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    CarouselView carouselView;
    TextView city_description_textview;

    public static MainFragment newInstance(){
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        carouselView=(CarouselView)view.findViewById(R.id.fragment_main_carousel);
        carouselView.setPageCount(3);
        carouselView.setViewListener(carouselViewListener);

        city_description_textview=(TextView)view.findViewById(R.id.fragment_main_city_description);
        city_description_textview.setText(R.string.city_description);

        return view;
    }

    ViewListener carouselViewListener=new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            LinearLayout carousel_linear_container=(LinearLayout) getActivity().findViewById(R.id.carousel_item_linear_container);

            View carouselItemView=getActivity().getLayoutInflater().inflate(R.layout.fragment_main_carousel_item,carousel_linear_container,false);
            KenBurnsView carousel_image=(KenBurnsView) carouselItemView.findViewById(R.id.carousel_item_kenburns);

            if (position==0)
            {
                carousel_image.setImageResource(R.drawable.home_carousel_1);
            }
            else if (position==1)
            {
                carousel_image.setImageResource(R.drawable.home_carousel_2);
            }else
            {
                carousel_image.setImageResource(R.drawable.home_carousel_3);
            }

            return carouselItemView;
        }
    };

}
