package com.example.tejas.smartcityapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProjectType1DetailsActivity extends AppCompatActivity {

    TextView title,description,department,city;
    ImageView imageView;
    Button interested_btn,submit_btn;

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
}
