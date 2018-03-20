package com.example.tejas.smartcityapp.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tejas.smartcityapp.Items.ProjectType1Item;
import com.example.tejas.smartcityapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Tejas on 20-03-2018.
 */

public class ProjectType1RecyclerAdapter extends RecyclerView.Adapter<ProjectType1RecyclerAdapter.ViewHolder> {

    ArrayList<ProjectType1Item> arrayList;
    Context context;

    public ProjectType1RecyclerAdapter(ArrayList<ProjectType1Item> arrayList, Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public ProjectType1RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_type1_item, parent, false);
        return new ProjectType1RecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectType1RecyclerAdapter.ViewHolder holder, int position) {

        holder.department_textview.setText(arrayList.get(position).department);
        holder.title_textview.setText(arrayList.get(position).title);
        Picasso.get().load(arrayList.get(position).img_url).into(holder.project_imageview);

        /*
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do intents etc.
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView department_textview,title_textview;
        ImageView project_imageview;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);

            department_textview=itemView.findViewById(R.id.project_type1_item_departmenttext);
            title_textview=itemView.findViewById(R.id.project_type1_item_titletext);
            project_imageview=itemView.findViewById(R.id.project_type1_item_image);
            cardView=itemView.findViewById(R.id.project_type1_item_cardview);
        }
    }
}
