package com.example.tejas.smartcityapp.Fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.Items.NewsItem;
import com.example.tejas.smartcityapp.Items.VolunteerItem;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.RecyclerAdapter.NewsRecyclerAdapter;
import com.example.tejas.smartcityapp.RecyclerAdapter.VolunteerRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

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

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volunteer_details, container, false);

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.volunteer_detail_swipeRefresh);
        recyclerView=(RecyclerView)view.findViewById(R.id.volunteer_detail_recycler);

        volunteerItems=new ArrayList<>();

        GetNews obj=new GetNews();
        obj.execute();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(new VolunteerRecyclerAdapter(volunteerItems,getContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                volunteerItems.clear();
                GetNews obj=new GetNews();
                obj.execute();
            }
        });

        return view;
    }

    private class GetNews extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Fetching data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HashMap<String,String> args = new HashMap<>();
            args.put("alert_id",getActivity().getIntent().getStringExtra("alert_id"));
            RequestHandler rh = new RequestHandler();

            final String jsonStr = rh.sendPostRequest(AppConstants.get_volunteers,args);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray feeds = jsonObj.getJSONArray("result");

                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject c = feeds.getJSONObject(i);

                        String name =c.getString("name");
                        String contact=c.getString("contact");

                        VolunteerItem object=new VolunteerItem(name,contact);
                        volunteerItems.add(object);
                    }

                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Couldn't get data from Server. Please try again later", Toast.LENGTH_LONG).show();
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

            if (progressDialog.isShowing())
            {
                progressDialog.hide();
            }
            recyclerView.setAdapter(new VolunteerRecyclerAdapter(volunteerItems,getActivity()));
        }
    }


}
