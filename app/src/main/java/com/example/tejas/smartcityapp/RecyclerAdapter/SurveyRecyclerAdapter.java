package com.example.tejas.smartcityapp.RecyclerAdapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.Items.PollsItem;
import com.example.tejas.smartcityapp.Items.SurveyItem;
import com.example.tejas.smartcityapp.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by dani on 23/03/2018.
 */

public class SurveyRecyclerAdapter extends RecyclerView.Adapter<SurveyRecyclerAdapter.ViewHolder> {

    ArrayList<SurveyItem> arrayList;
    Context context;

    public SurveyRecyclerAdapter(ArrayList<SurveyItem> arrayList, Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public SurveyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.survey_format, parent, false);
        return new SurveyRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SurveyRecyclerAdapter.ViewHolder holder, final int position) {

        String rating = arrayList.get(position).rating;
        String ans = arrayList.get(position).ans;

        holder.question_textview.setText(arrayList.get(position).question);

        if (rating.equals("null")){
            holder.answered_textview.setVisibility(View.GONE);
        }
        else{
            holder.rating.setRating(Float.parseFloat(rating));
            holder.rating.setIsIndicator(TRUE);
            holder.editText.setVisibility(View.GONE);
            holder.answered_textview.setText(ans);
            holder.submitButton.setVisibility(View.GONE);
        }

        /*
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do intents etc.
            }
        });
*/
        holder.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rate;
                Editable text;

                if ((rate = holder.rating.getRating()) > 0){
                    text = holder.editText.getText();
                    submitAnswer(holder, position, String.valueOf(rate), text.toString());
                    Log.v("HELLOOOOOOOOOOO", "BUGGGGG" );
                }
                else {
                    Toast.makeText(context,"Select one option", Toast.LENGTH_SHORT).show();
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
        RatingBar rating;
        EditText editText;
        TextView answered_textview;
        Button submitButton;

        public ViewHolder(View itemView) {
            super(itemView);

            question_textview=itemView.findViewById(R.id.survey_question);
            cardView=itemView.findViewById(R.id.survey_format_cardview);
            rating=itemView.findViewById(R.id.survey_rating);
            editText=itemView.findViewById(R.id.survey_editText);
            answered_textview = itemView.findViewById(R.id.survey_answered);
            submitButton = itemView.findViewById(R.id.survey_submit_button);
        }
    }

    private void submitAnswer(final SurveyRecyclerAdapter.ViewHolder holder, final int position,final String rate, final String answer){
        class SubmitAnswer extends AsyncTask<Void,Void,String> {

            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(SurveyRecyclerAdapter.this.context,"Submitting Answer","Please Wait...",false,
                        false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                if (s.equals("2")){
                    // To remove other options from the card
                    holder.rating.setIsIndicator(TRUE);
                    holder.answered_textview.setVisibility(View.VISIBLE);
                    holder.answered_textview.setText(holder.editText.getText());
                    holder.editText.setVisibility(View.GONE);
                    holder.submitButton.setVisibility(View.GONE);

                    Toast.makeText(context,"Submitted", Toast.LENGTH_SHORT).show();
                }
                else if (s.equals("1")){
                    Toast.makeText(context,"Server Error", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                SharedPreferences prefs = context.getSharedPreferences(AppConstants.CURRENT_USER, MODE_PRIVATE);
                String user_id = prefs.getString("user_id", "0");

                HashMap<String,String> args = new HashMap<>();
                args.put("survey_id",arrayList.get(position).id);
                args.put("user_id",user_id);
                args.put("rating", rate);
                args.put("ans",answer);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(AppConstants.save_survey_answers,args);

                return s;
            }
        }
        SubmitAnswer a = new SubmitAnswer();
        a.execute();
    }
}
