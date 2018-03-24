package com.example.tejas.smartcityapp.RecyclerAdapter;

/**
 * Created by Ritgvika Iyer on 23-03-2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.Items.NewsItem;
import com.example.tejas.smartcityapp.Items.PollsItem;
import com.example.tejas.smartcityapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Boolean.TRUE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.Items.PollsItem;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.UserDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Boolean.TRUE;

/**
 * Created by dani on 20/03/2018.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {

    ArrayList<NewsItem> arrayList;
    Context context;



    public NewsRecyclerAdapter(ArrayList<NewsItem> arrayList, Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public NewsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_newsheadlines, parent, false);
        return new NewsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsRecyclerAdapter.ViewHolder holder, final int position) {


        holder.heading.setText(arrayList.get(position).news_heading);
        holder.date.setText(arrayList.get(position).date);
        holder.content.setText(arrayList.get(position).news_content);

        // holder.question_textview.setText(arrayList.get(position).question);

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView heading;
        CardView cardView;
        TextView date;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);

            heading=itemView.findViewById(R.id.heading);
            cardView=itemView.findViewById(R.id.list_newheadlines_cardview);
            date=itemView.findViewById(R.id.date);
            content=itemView.findViewById(R.id.content);
        }
    }


}




