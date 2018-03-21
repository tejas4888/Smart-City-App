package com.example.tejas.smartcityapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejas.smartcityapp.Adapters.ProjectType1NewsListAdapter;
import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.Items.ProjectType1NewsItem;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectType1DetailsActivity extends AppCompatActivity {

    TextView title,description,department,city;
    ImageView imageView;
    Button interested_btn,submit_btn;
    ListView news_listView;
    CardView newCard;
    ArrayList<ProjectType1NewsItem> newsItemArrayList;
    String JSON_NEWS_STRING="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_type1_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imageView=(ImageView)findViewById(R.id.details_project_type1_image);
        title=(TextView)findViewById(R.id.details_project_type1_title);
        description=(TextView)findViewById(R.id.details_project_type1_description);
        department=(TextView)findViewById(R.id.details_project_type1_department);
        city=(TextView)findViewById(R.id.details_project_type1_city);

        getProjectNews(getIntent().getStringExtra("Project_id"));

        newCard = (CardView)findViewById(R.id.details_project_type1_newscard);
        news_listView=(ListView)findViewById(R.id.details_project_type1_newslist);
        newsItemArrayList=new ArrayList<>();

        try
        {
            title.setText(getIntent().getStringExtra("Title"));
            description.setText(getIntent().getStringExtra("Description"));
            department.setText(getIntent().getStringExtra("Department"));
            city.setText(getIntent().getStringExtra("City"));
            Picasso.get().load(getIntent().getStringExtra("Image_url")).into(imageView);
        }catch (Exception e)
        {

        }
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
                Log.v("NEWS",JSON_NEWS_STRING);
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
                ProjectType1NewsListAdapter adapter=new ProjectType1NewsListAdapter(this,newsItemArrayList);
                news_listView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
