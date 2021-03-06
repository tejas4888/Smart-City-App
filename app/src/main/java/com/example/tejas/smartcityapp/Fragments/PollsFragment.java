package com.example.tejas.smartcityapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.Items.PollsItem;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.RecyclerAdapter.PollsRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dani on 20/03/2018.
 */

/**
 * A simple {@link Fragment} subclass.
 */

public class PollsFragment extends Fragment {

    public PollsFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ArrayList<PollsItem> items;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_polls, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.fragment_polls_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        items=new ArrayList<PollsItem>();
        new PollsFragment.GetProjectList().execute();

        return view;
    }


    private class GetProjectList extends AsyncTask<Void, Void, Void>
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

            SharedPreferences prefs = getActivity().getSharedPreferences(AppConstants.CURRENT_USER, MODE_PRIVATE);
            String user_id = prefs.getString("user_id", "0");

            HashMap<String,String> args = new HashMap<>();

            args.put("user_id",user_id);
            RequestHandler rh = new RequestHandler();

            final String jsonStr = rh.sendPostRequest(AppConstants.get_poll_questions,args);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray feeds = jsonObj.getJSONArray("result");

                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject c = feeds.getJSONObject(i);

                        String poll_id =c.getString("poll_id");
                        String question = c.getString("question");
                        String poll_ans = c.getString("poll_ans");

                        PollsItem object = new PollsItem(poll_id,question,poll_ans);

                        items.add(object);
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
            recyclerView.setAdapter(new PollsRecyclerAdapter(items,getActivity()));
        }
    }


}
