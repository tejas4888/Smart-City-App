package com.example.tejas.smartcityapp.Fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejas.smartcityapp.Adapters.MainFragmentAlertListAdapter;
import com.example.tejas.smartcityapp.Adapters.MainFragmentProjectListAdapter;
import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.HttpHandler;
import com.example.tejas.smartcityapp.Items.MainFragmentAlertItem;
import com.example.tejas.smartcityapp.Items.MainFragmentProjectItem;
import com.example.tejas.smartcityapp.Items.ProjectType1Item;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.RecyclerAdapter.ProjectType1RecyclerAdapter;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    CarouselView carouselView;
    TextView city_description_textview;

    ArrayList<MainFragmentProjectItem> project_items;
    ArrayList<MainFragmentAlertItem> alert_items;

    ListView listView_project,listView_alerts;

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

        listView_project=(ListView)view.findViewById(R.id.fragment_main_project_list);
        listView_alerts=(ListView)view.findViewById(R.id.fragment_main_alert_list);

        project_items=new ArrayList<>();
        alert_items=new ArrayList<>();

        GetProjectList obj=new GetProjectList();
        obj.execute();

        GetAlertList obj2=new GetAlertList();
        obj2.execute();

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

    private class GetProjectList extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(AppConstants.get_projects_main);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray feeds = jsonObj.getJSONArray("result");

                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject c = feeds.getJSONObject(i);

                        String project_id = c.getString("project_id");
                        String title = c.getString("title");

                        MainFragmentProjectItem object = new MainFragmentProjectItem(project_id,title);

                        project_items.add(object);
                    }

                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Couldn't get data from Server. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            listView_project.setAdapter(new MainFragmentProjectListAdapter(getActivity(),project_items));
        }
    }

    private class GetAlertList extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(AppConstants.get_alerts_main);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray feeds = jsonObj.getJSONArray("result");

                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject c = feeds.getJSONObject(i);

                        String title = c.getString("title");

                        MainFragmentAlertItem object = new MainFragmentAlertItem(title);

                        alert_items.add(object);
                    }

                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Couldn't get data from Server. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listView_alerts.setAdapter(new MainFragmentAlertListAdapter(getActivity(),alert_items));
        }
    }


}
