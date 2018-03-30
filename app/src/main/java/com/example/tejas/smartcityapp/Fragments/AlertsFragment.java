package com.example.tejas.smartcityapp.Fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.HttpHandler;
import com.example.tejas.smartcityapp.Items.AlertItem;
import com.example.tejas.smartcityapp.Items.ProjectType1Item;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.RecyclerAdapter.AlertsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertsFragment extends Fragment {


    public AlertsFragment() {
        // Required empty public constructor
    }

    ArrayList<AlertItem> items;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alerts, container, false);

        items=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.fragment_alerts_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new getAlertList().execute();

        return view;
    }

    private class getAlertList extends AsyncTask<Void,Void,Void>
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

            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(AppConstants.get_alerts_list);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray feeds = jsonObj.getJSONArray("result");

                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject c = feeds.getJSONObject(i);

                        String alert_id = c.getString("alert_id");
                        String date = c.getString("date");
                        String time = c.getString("time");
                        String title = c.getString("title");
                        String area = c.getString("area");
                        String type = c.getString("type");
                        String description = c.getString("description");
                        String guidelines = c.getString("guidelines");
                        String helpline = c.getString("helpline");
                        String disclaimer = c.getString("disclaimer");

                        AlertItem object = new AlertItem(alert_id,date,time,title,area,type,description,
                                guidelines,helpline,disclaimer);
                        items.add(object);
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

            if (progressDialog.isShowing())
            {
                progressDialog.hide();
            }
            recyclerView.setAdapter(new AlertsRecyclerAdapter(items,getActivity()));
        }
    }

}
