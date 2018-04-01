package com.example.tejas.smartcityapp.Fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.HttpHandler;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.Items.ProjectType1Item;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.RecyclerAdapter.ProjectType1RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProjectsFragment extends Fragment {


    public MyProjectsFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    ArrayList<ProjectType1Item> items;
    ProgressDialog progressDialog;
    FloatingActionButton filter_fab;
    CheckBox check1,check2,check3,check4,check5;
    Button filter_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_type1, container, false);

        //Toast.makeText(getActivity(),"HERE",Toast.LENGTH_SHORT).show();
        items=new ArrayList<ProjectType1Item>();

        recyclerView=(RecyclerView)view.findViewById(R.id.fragment_project_type1_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new MyProjectsFragment.GetProjectList().execute();
        filter_fab=(FloatingActionButton)view.findViewById(R.id.fragment_project_type1_filter_fab);
        filter_fab.setVisibility(View.GONE);
        /*
        filter_fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                View bottomSheetView = getLayoutInflater().inflate(R.layout.fragment_filter_bottom_sheet, null);

                final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(bottomSheetView);
                dialog.show();

                check1=(CheckBox)bottomSheetView.findViewById(R.id.bottom_sheet_filter_check1);
                check2=(CheckBox)bottomSheetView.findViewById(R.id.bottom_sheet_filter_check2);
                check3=(CheckBox)bottomSheetView.findViewById(R.id.bottom_sheet_filter_check3);
                check4=(CheckBox)bottomSheetView.findViewById(R.id.bottom_sheet_filter_check4);
                check5=(CheckBox)bottomSheetView.findViewById(R.id.bottom_sheet_filter_check5);

                filter_button=(Button)bottomSheetView.findViewById(R.id.bottom_sheet_filter_apply_btn);

                filter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "CLick", Toast.LENGTH_SHORT).show();

                        int c1=0,c2=0,c3=0,c4=0,c5=0;
                        if (check1.isChecked())
                        {
                            c1=1;
                        }
                        if (check2.isChecked())
                        {
                            c2=1;
                        }
                        if (check3.isChecked())
                        {
                            c3=1;
                        }
                        if (check4.isChecked())
                        {
                            c4 = 1;
                        }
                        if (check5.isChecked())
                        {
                            c5=1;
                        }
                        dialog.dismiss();
                        getCategorizedData(c1,c2,c3,c4,c5);
                    }
                });
            }
        });
        */
        return view;
    }

    private void getCategorizedData(final int check1,final int check2,final int check3,final int check4,final int check5)
    {
        class GetJSON2 extends AsyncTask<Void,Void,String>
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
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.v("STRING",s);
                items.clear();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray("result");

                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jo = result.getJSONObject(i);
                        String project_id = jo.getString("project_id");
                        String title = jo.getString("title");
                        String description = jo.getString("description");
                        String department = jo.getString("department");
                        String city = jo.getString("city");
                        String image = jo.getString("image");

                        items.add(new ProjectType1Item(project_id,title,description,department,city,image));
                    }
                    recyclerView.setAdapter(new ProjectType1RecyclerAdapter(items,getActivity()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (progressDialog.isShowing())
                {
                    progressDialog.hide();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> args = new HashMap<>();
                args.put("check1",String.valueOf(check1));
                args.put("check2",String.valueOf(check2));
                args.put("check3",String.valueOf(check3));
                args.put("check4",String.valueOf(check4));
                args.put("check5",String.valueOf(check5));

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(AppConstants.get_type1_projects_categorized,args);
                return s;
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }


    private void readProjectData(String JSON_NEWS_STRING){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_NEWS_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String project_id = jo.getString("project_id");
                String title = jo.getString("title");
                String description = jo.getString("description");
                String department = jo.getString("department");
                String city = jo.getString("city");
                String image = jo.getString("image");

                items.add(new ProjectType1Item(project_id,title,description,department,city,image));
            }
            recyclerView.setAdapter(new ProjectType1RecyclerAdapter(items,getActivity()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
