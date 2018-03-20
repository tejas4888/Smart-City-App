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
import com.example.tejas.smartcityapp.Items.ProjectType1Item;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.RecyclerAdapter.ProjectType1RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectType1Fragment extends Fragment {


    public ProjectType1Fragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ArrayList<ProjectType1Item> items;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_type1, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.fragment_project_type1_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        items=new ArrayList<ProjectType1Item>();
        new GetProjectList().execute();

        return view;
    }


    private class GetProjectList extends AsyncTask<Void,Void,Void>
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

            String jsonStr = sh.makeServiceCall(AppConstants.get_type1_projects);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray feeds = jsonObj.getJSONArray("result");

                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject c = feeds.getJSONObject(i);

                        String project_id = c.getString("project_id");
                        String title = c.getString("title");
                        String description = c.getString("description");
                        String department = c.getString("department");
                        String city = c.getString("city");
                        String img_url = c.getString("image");

                        ProjectType1Item object = new ProjectType1Item(project_id,title,description,department,city,img_url);

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
            recyclerView.setAdapter(new ProjectType1RecyclerAdapter(items,getActivity()));
        }
    }
}
