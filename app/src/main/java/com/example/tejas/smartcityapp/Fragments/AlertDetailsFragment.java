package com.example.tejas.smartcityapp.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tejas.smartcityapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertDetailsFragment extends Fragment {


    public AlertDetailsFragment() {
        // Required empty public constructor
    }

    TextView title,area,date,time,description,guidelines,helpline;
    ImageView call,add_contact;

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

        call=(ImageView)view.findViewById(R.id.alert_detail_call_img);
        add_contact=(ImageView)view.findViewById(R.id.alert_detail_add_contact_img);

        title.setText(getActivity().getIntent().getStringExtra("title"));
        area.setText(getActivity().getIntent().getStringExtra("area"));
        date.setText(getActivity().getIntent().getStringExtra("date"));
        time.setText(getActivity().getIntent().getStringExtra("time"));
        description.setText(getActivity().getIntent().getStringExtra("description"));
        guidelines.setText(getActivity().getIntent().getStringExtra("guidelines"));
        helpline.setText(getActivity().getIntent().getStringExtra("helpline"));

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

        return view;
    }

}
