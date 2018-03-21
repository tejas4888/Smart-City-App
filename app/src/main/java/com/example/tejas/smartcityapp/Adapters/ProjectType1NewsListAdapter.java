package com.example.tejas.smartcityapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tejas.smartcityapp.Items.ProjectType1Item;
import com.example.tejas.smartcityapp.Items.ProjectType1NewsItem;
import com.example.tejas.smartcityapp.R;

import java.util.ArrayList;

/**
 * Created by Tejas on 21-03-2018.
 */

public class ProjectType1NewsListAdapter extends ArrayAdapter<ProjectType1NewsItem> {

    ArrayList<ProjectType1NewsItem> arrayList;
    Context context;

    public ProjectType1NewsListAdapter(@NonNull Context context, ArrayList<ProjectType1NewsItem> arrayList) {
        super(context, R.layout.project_type1_newsitem,arrayList);

        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.project_type1_newsitem,parent,false);

        TextView date = (TextView) rowView.findViewById(R.id.project_type1_item_news_date);
        TextView time = (TextView) rowView.findViewById(R.id.project_type1_item_news_time);
        TextView headline = (TextView) rowView.findViewById(R.id.project_type1_item_news_headline);

        date.setText(arrayList.get(position).date);
        time.setText(arrayList.get(position).time);
        headline.setText(arrayList.get(position).headline);

        return rowView;
    }
}
