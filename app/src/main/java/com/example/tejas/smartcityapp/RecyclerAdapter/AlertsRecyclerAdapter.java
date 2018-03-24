package com.example.tejas.smartcityapp.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tejas.smartcityapp.Items.AlertItem;
import com.example.tejas.smartcityapp.R;

import java.util.ArrayList;

/**
 * Created by Tejas on 24-03-2018.
 */

public class AlertsRecyclerAdapter extends RecyclerView.Adapter<AlertsRecyclerAdapter.ViewHolder> {

    ArrayList<AlertItem> arrayList;
    Context context;

    public AlertsRecyclerAdapter(ArrayList<AlertItem> arrayList,Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public AlertsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alerts_item, parent, false);
        return new AlertsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlertsRecyclerAdapter.ViewHolder holder, int position) {
        holder.date.setText(arrayList.get(position).date);
        holder.time.setText(arrayList.get(position).time);
        holder.title.setText(arrayList.get(position).title);
        holder.area.setText(arrayList.get(position).area);

        if (arrayList.get(position).type.equals("1"))
        {
            int theme= Color.parseColor("#FFA500");
            holder.type.setBackgroundColor(theme);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Intent intent=new Intent(context,);
                    intent.putExtra("alert_id",arrayList.get(position).alert_id);
                    context.startActivity(intent);
                */
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView date,time,title,area;
        View type;

        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.alerts_item_cardview);
            date=itemView.findViewById(R.id.alerts_item_date);
            time=itemView.findViewById(R.id.alerts_item_time);
            title=itemView.findViewById(R.id.alerts_item_title);
            area=itemView.findViewById(R.id.alerts_item_area);
            type=itemView.findViewById(R.id.alerts_item_category);
        }
    }


}
