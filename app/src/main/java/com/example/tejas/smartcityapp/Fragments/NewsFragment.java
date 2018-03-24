package com.example.tejas.smartcityapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tejas.smartcityapp.Adapters.ProjectType1NewsListAdapter;
import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.HttpHandler;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.Items.NewsItem;
import com.example.tejas.smartcityapp.Items.ProjectType1Item;
import com.example.tejas.smartcityapp.Items.ProjectType1NewsItem;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.RecyclerAdapter.NewsRecyclerAdapter;
import com.example.tejas.smartcityapp.RecyclerAdapter.ProjectType1RecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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

public class NewsFragment extends Fragment {

    public NewsFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ArrayList<NewsItem> items;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        items=new ArrayList<NewsItem>();
        //NewsFragment nf=new NewsFragment();
        GetNews gn=new GetNews();
        //nf.GetNews().execute();
        gn.execute();

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

            //SharedPreferences prefs = getActivity().getSharedPreferences(AppConstants.CURRENT_USER, MODE_PRIVATE);
           // String user_id = prefs.getString("user_id", "0");

            HashMap<String,String> args = new HashMap<>();

            //args.put("user_id",user_id);
            RequestHandler rh = new RequestHandler();

            final String jsonStr = rh.sendPostRequest(AppConstants.get_news,args);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray feeds = jsonObj.getJSONArray("result");

                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject c = feeds.getJSONObject(i);

                        String news_id =c.getString("news_id");
                        String news_heading=c.getString("news_heading");
                        String news_content = c.getString("news_content");
                        String date = c.getString("date");

                        NewsItem object = new NewsItem(news_id,news_heading,news_content,date);

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
            recyclerView.setAdapter(new NewsRecyclerAdapter(items,getActivity()));
        }
    }


}








/*
public class NewsFragment extends Fragment {

    ListView news_listView;
    CardView newCard;
    ArrayList<ProjectType1NewsItem> newsItemArrayList;
    String JSON_NEWS_STRING="";

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        // Bundle extras = getActivity().getBundle().getExtras();
        //String value1 = extras.getString("OUR_TEXT_KEY");
        if(getArguments()!=null){
            getProjectNews(getArguments().getString("project_id"));
        }else{
            Log.e("TItle"," ----------- -- -- -----------------    ARgumen not present");
        }

        HttpHandler sh = new HttpHandler();

        String jsonStr = sh.makeServiceCall(AppConstants.get_type1_projects);
        Log.e("Title", " --- ------- - -  Onstart() m ethod");

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray feeds = jsonObj.getJSONArray("result");

                Log.e("Title", " --- ------- - -  Newfragment try/catch onstart()");

                for (int i = 0; i < feeds.length(); i++) {
                    JSONObject c = feeds.getJSONObject(i);

                    String date = c.getString("date");
                    String time = c.getString("time");
                    String description = c.getString("description");
                    String headline = c.getString("headline");
                    Log.e("Title","----- - - - - - --  -     "+date+"\t"+time+"\t"+description+"\t"+headline);

                }


            } catch (final JSONException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Couldn't get data from Server. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }else{
            Log.e("Title", " ------- Error : jsonStr is null ");
        }

        newCard = (CardView)view.findViewById(R.id.details_project_type1_newscard);
        news_listView=(ListView)view.findViewById(R.id.details_project_type1_newslist);
        newsItemArrayList=new ArrayList<>();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
     View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getProjectNews(final String project_id) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_NEWS_STRING = s;
                readNews(JSON_NEWS_STRING);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> args = new HashMap<>();
                args.put("project_id",project_id);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(AppConstants.get_type1_project_news,args);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void readNews(String JSON_NEWS_STRING){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_NEWS_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String date = jo.getString("date");
                String time = jo.getString("time");
                String headline = jo.getString("headline");
                String description = jo.getString("description");

                newsItemArrayList.add(new ProjectType1NewsItem(date,time,headline,description));
            }

            if (newsItemArrayList.size()==0)
            {
                newCard.setVisibility(View.GONE);
            }else {
                ProjectType1NewsListAdapter adapter=new ProjectType1NewsListAdapter(getContext(),newsItemArrayList);
                news_listView.setAdapter(adapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}*/
