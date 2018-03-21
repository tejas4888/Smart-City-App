package com.example.tejas.smartcityapp.RecyclerAdapter;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.example.tejas.smartcityapp.Items.PollsItem;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.UserDetailsActivity;

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
    public void onBindViewHolder(final PollsRecyclerAdapter.ViewHolder holder, final int position) {

        holder.question_textview.setText(arrayList.get(position).question);

        /*
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do intents etc.
            }
        });*/

        holder.radioButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.radioButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.radioButtonCantsay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.radioButtonYes.isChecked()){
                    submitAnswer(arrayList.get(position).id, 1, position, holder);
                }
                else if (holder.radioButtonNo.isChecked()){
                    submitAnswer(arrayList.get(position).id, 2, position, holder);
                }
                else if (holder.radioButtonCantsay.isChecked()){
                    submitAnswer(arrayList.get(position).id, 3, position, holder);
                }
                else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView question_textview;
        CardView cardView;
        RadioButton radioButtonYes, radioButtonNo, radioButtonCantsay;
        Button submitButton;

        public ViewHolder(View itemView) {
            super(itemView);

            question_textview=itemView.findViewById(R.id.polls_question);
            cardView=itemView.findViewById(R.id.polls_item_cardview);
            radioButtonYes = itemView.findViewById(R.id.polls_yes);
            radioButtonNo = itemView.findViewById(R.id.polls_no);
            radioButtonCantsay = itemView.findViewById(R.id.polls_cantsay);
            submitButton = itemView.findViewById(R.id.polls_submit_button);
        }


    }

    private void submitAnswer(final String id, int answer, final int position, final ViewHolder holder){
        class SubmitAnswer extends AsyncTask<Void,Void,String>{

            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(PollsRecyclerAdapter.this.context,"Submitting Answer","Please Wait...",false,
                        false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                // To remove other options from the card
                if (holder.radioButtonYes.isChecked()){
                    holder.radioButtonNo.setVisibility(View.GONE);
                    holder.radioButtonCantsay.setVisibility(View.GONE);
                    holder.submitButton.setVisibility(View.GONE);
                }
                else if (holder.radioButtonNo.isChecked()){
                    holder.radioButtonYes.setVisibility(View.GONE);
                    holder.radioButtonCantsay.setVisibility(View.GONE);
                    holder.submitButton.setVisibility(View.GONE);
                }
                else if (holder.radioButtonCantsay.isChecked()){
                    holder.radioButtonYes.setVisibility(View.GONE);
                    holder.radioButtonNo.setVisibility(View.GONE);
                    holder.submitButton.setVisibility(View.GONE);
                }
                else {
                    // To remove that card
                    PollsRecyclerAdapter.this.arrayList.remove(position);
                    PollsRecyclerAdapter.this.notifyItemRemoved(position);
                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                return null;
            }
        }
        SubmitAnswer a = new SubmitAnswer();
        a.execute();
    }
}
