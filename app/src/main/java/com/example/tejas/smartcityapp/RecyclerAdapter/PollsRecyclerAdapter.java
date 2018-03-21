package com.example.tejas.smartcityapp.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tejas.smartcityapp.Items.PollsItem;
import com.example.tejas.smartcityapp.R;

import java.util.ArrayList;

/**
 * Created by dani on 20/03/2018.
 */

public class PollsRecyclerAdapter extends RecyclerView.Adapter<PollsRecyclerAdapter.ViewHolder> {

    ArrayList<PollsItem> arrayList;
    Context context;

    public PollsRecyclerAdapter(ArrayList<PollsItem> arrayList, Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public PollsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.polls_format, parent, false);
        return new PollsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PollsRecyclerAdapter.ViewHolder holder, int position) {

        holder.question_textview.setText(arrayList.get(position).question);

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
        TextView question_textview;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            question_textview=itemView.findViewById(R.id.polls_question);
            cardView=itemView.findViewById(R.id.polls_item_cardview);
        }
    }

}
