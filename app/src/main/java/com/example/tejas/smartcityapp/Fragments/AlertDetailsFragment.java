package com.example.tejas.smartcityapp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.ProjectType1DetailsActivity;
import com.example.tejas.smartcityapp.R;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertDetailsFragment extends Fragment {


    public AlertDetailsFragment() {
        // Required empty public constructor
    }

    TextView title,area,date,time,description,guidelines,helpline,volunteer_disclaimer;
    ImageView call,add_contact;
    Button volunteer_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert_details, container, false);

        title=(TextView)view.findViewById(R.id.alert_detail_title);
        area=(TextView)view.findViewById(R.id.alert_detail_area);
        date=(TextView)view.findViewById(R.id.alert_detail_date);
        time=(TextView)view.findViewById(R.id.alert_detail_time);
        description=(TextView)view.findViewById(R.id.alert_detail_description);
        guidelines=(TextView)view.findViewById(R.id.alert_detail_guidelines);
        helpline=(TextView)view.findViewById(R.id.alert_detail_helpline);
        volunteer_disclaimer=(TextView)view.findViewById(R.id.alert_detail_volunteer_disclaimer) ;

        call=(ImageView)view.findViewById(R.id.alert_detail_call_img);
        add_contact=(ImageView)view.findViewById(R.id.alert_detail_add_contact_img);

        volunteer_button=(Button)view.findViewById(R.id.alert_detail_volunteer_btn);

        title.setText(getActivity().getIntent().getStringExtra("title"));
        area.setText(getActivity().getIntent().getStringExtra("area"));
        date.setText(getActivity().getIntent().getStringExtra("date"));
        time.setText(getActivity().getIntent().getStringExtra("time"));
        description.setText(getActivity().getIntent().getStringExtra("description"));
        guidelines.setText(getActivity().getIntent().getStringExtra("guidelines"));
        helpline.setText(getActivity().getIntent().getStringExtra("helpline"));
        volunteer_disclaimer.setText(getActivity().getIntent().getStringExtra("disclaimer"));

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + getActivity().getIntent().getStringExtra("helpline")));
                getActivity().startActivity(callIntent);
            }
        });

        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, getActivity().getIntent().getStringExtra("title")+" Helpline");
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, "" + getActivity().getIntent().getStringExtra("helpline"));
                getActivity().startActivity(intent);
            }
        });

        volunteer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volunteerCheck();
            }
        });

        return view;
    }

    private void volunteerCheck()
    {
        class GetJSON2 extends AsyncTask<Void, Void, String> {

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(getActivity(),"Checking Submission","Please Wait...",false,
                        false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                if (s.equals("0")) {
                    Toast.makeText(getActivity(), "Already Volunteered!", Toast.LENGTH_SHORT).show();
                }else if(s.equals("1")){
                    Toast.makeText(getActivity(), "You're now a volunteer!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> args = new HashMap<>();
                SharedPreferences prefs = getActivity().getSharedPreferences(AppConstants.CURRENT_USER, MODE_PRIVATE);
                String user_id = prefs.getString("user_id", "0");

                args.put("user_id",user_id);
                args.put("alert_id",getActivity().getIntent().getStringExtra("alert_id"));

                Log.v("User_id",user_id);
                RequestHandler rh = new RequestHandler();

                String s="";
                s = rh.sendPostRequest(AppConstants.add_volunteers,args);
                return s;
            }
        }
        GetJSON2 gj = new GetJSON2();
        gj.execute();
    }

}
