package com.example.tejas.smartcityapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tejas.smartcityapp.Items.MainFragmentProjectItem;
import com.example.tejas.smartcityapp.Items.ProjectType1NewsItem;
import com.example.tejas.smartcityapp.R;

import java.util.ArrayList;

/**
 * Created by Tejas on 29-03-2018.
 */

public class MainFragmentProjectListAdapter extends ArrayAdapter<MainFragmentProjectItem> {

    ArrayList<MainFragmentProjectItem> arrayList;
    Context context;

    public MainFragmentProjectListAdapter(@NonNull Context context, ArrayList<MainFragmentProjectItem> arrayList) {
        super(context, R.layout.project_item_main_fragment,arrayList);

        this.arrayList=arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.project_item_main_fragment,parent,false);

        TextView project_id = (TextView) rowView.findViewById(R.id.project_item_main_fragment_project_id);
        TextView project_title = (TextView) rowView.findViewById(R.id.project_item_main_fragment_project_title);

        project_id.setText(arrayList.get(position).project_id);
        project_title.setText(arrayList.get(position).project_name);

        return rowView;
    }
}
