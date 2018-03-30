package com.example.tejas.smartcityapp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.example.tejas.smartcityapp.LoginActivity;
import com.example.tejas.smartcityapp.MainActivity;
import com.example.tejas.smartcityapp.R;
import com.example.tejas.smartcityapp.UserDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */

public class MyHomeFragment extends Fragment {

    RadioButton radioButton1,radioButton2;
    EditText editText_contact;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5;
    LinearLayout checkLayout;
    Button btnSubmit;

    EditText editText_address;
    TextView helloName;

    public MyHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_home, container, false);

        radioButton1=(RadioButton)view.findViewById(R.id.userdetails_radio1);
        radioButton2=(RadioButton)view.findViewById(R.id.userdetails_radio2);

        checkBox1=(CheckBox)view.findViewById(R.id.userdetails_check_1);
        checkBox2=(CheckBox)view.findViewById(R.id.userdetails_check_2);
        checkBox3=(CheckBox)view.findViewById(R.id.userdetails_check_3);
        checkBox4=(CheckBox)view.findViewById(R.id.userdetails_check_4);
        checkBox5=(CheckBox)view.findViewById(R.id.userdetails_check_5);

        checkLayout=(LinearLayout)view.findViewById(R.id.userdetails_checkLayout);

        btnSubmit=(Button)view.findViewById(R.id.userdetails_btnSubmit);

        editText_contact=(EditText)view.findViewById(R.id.userdetails_editText_Contact);

        editText_address=(EditText)view.findViewById(R.id.userdetails_address_edittext);
        helloName = view.findViewById(R.id.userdetails_hello_name);


        final SharedPreferences prefs = getContext().getSharedPreferences(AppConstants.CURRENT_USER, MODE_PRIVATE);

        helloName.setText("Hello, "+prefs.getString("name", "0"));
        editText_contact.setText(prefs.getString("contact", "0"));
        editText_address.setText(prefs.getString("address", "0"));

        if (prefs.getString("type", "0").equals("1")){
            radioButton1.setVisibility(View.VISIBLE);
            radioButton2.setVisibility(View.GONE);
            radioButton1.setChecked(true);
        }else {
            radioButton1.setVisibility(View.GONE);
            radioButton2.setVisibility(View.VISIBLE);
            radioButton2.setChecked(true);
            checkLayout.setVisibility(View.VISIBLE);

            checkBox1.setVisibility(View.GONE);
            checkBox2.setVisibility(View.GONE);
            checkBox3.setVisibility(View.GONE);
            checkBox4.setVisibility(View.GONE);
            checkBox5.setVisibility(View.GONE);

            if (prefs.getString("type1", "0").equals("1")){
                checkBox1.setVisibility(View.VISIBLE);
                checkBox1.setChecked(true);
            }
            if (prefs.getString("type2", "0").equals("1")){
                checkBox2.setVisibility(View.VISIBLE);
                checkBox2.setChecked(true);
            }
            if (prefs.getString("type3", "0").equals("1")){
                checkBox3.setVisibility(View.VISIBLE);
                checkBox3.setChecked(true);
            }
            if (prefs.getString("type4", "0").equals("1")){
                checkBox4.setVisibility(View.VISIBLE);
                checkBox4.setChecked(true);
            }
            if (prefs.getString("type5", "0").equals("1")){
                checkBox5.setVisibility(View.VISIBLE);
                checkBox5.setChecked(true);
            }
        }


        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLayout.setVisibility(View.VISIBLE);
            }
        });

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLayout.setVisibility(View.GONE);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int types[]=new int[5];
                int type;
                String contact;

                if (editText_contact.getText().toString().length()==8 || editText_contact.getText().toString().length()==10)
                {
                    contact=editText_contact.getText().toString();
                }else
                {
                    return;
                }

                if (radioButton1.isChecked())
                {
                    type=1;
                }else{
                    type=2;
                }

                if (type==2)
                {
                    if (checkBox1.isChecked())
                    {
                        types[0]=1;
                    }
                    if (checkBox2.isChecked())
                    {
                        types[1]=1;
                    }
                    if (checkBox3.isChecked())
                    {
                        types[2]=1;
                    }
                    if (checkBox4.isChecked())
                    {
                        types[3]=1;
                    }
                    if (checkBox5.isChecked())
                    {
                        types[4]=1;
                    }

                    if (!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked() && !checkBox4.isChecked() &&
                            !checkBox5.isChecked() )
                    {
                        return;
                    }
                }

                String address_text="";
                String email_notification="";

                if (editText_address.getText().toString().length()==0)
                {
                    return;
                }else{
                    address_text=editText_address.getText().toString();
                }


                updateUser(prefs.getString("name", "0"),prefs.getString("email", "0"),contact,type,types,
                           address_text,email_notification);
            }
        });

        return view;
    }

    private void updateUser(final String name, final String email, final String contact, final int type, final int types[],
                         final String address, final String email_notfication)
    {
        class UpdateUser extends AsyncTask<Void,Void,String>
        {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(getContext(),"Updating User","Please Wait...",false,
                        false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                if (s == "User Added Successfully") {
                    SharedPreferences prefs = getContext().getSharedPreferences(AppConstants.CURRENT_USER, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor = prefs.edit();
                    editor.putString("user_id", s);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("contact", contact);
                    editor.putString("address", address);
                    editor.putString("type", String.valueOf(type));
                    editor.putString("type1", String.valueOf(types[0]));
                    editor.putString("type2", String.valueOf(types[1]));
                    editor.putString("type3", String.valueOf(types[2]));
                    editor.putString("type4", String.valueOf(types[3]));
                    editor.putString("type5", String.valueOf(types[4]));
                    editor.commit();
                }

                Toast.makeText(getActivity(), s,Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("contact",contact);
                params.put("type",String.valueOf(type));
                params.put("address",String.valueOf(address));
                params.put("email_notification",String.valueOf(email_notfication));

                for (int i=0;i<5;i++)
                {
                    String param="check".concat(String.valueOf(i+1));

                    if (types[i]==1)
                    {
                        params.put(param,"1");
                    }
                    else
                    {
                        params.put(param,"0");
                    }
                }

                RequestHandler rh = new RequestHandler();
                String user_id = rh.sendPostRequest(AppConstants.update_user, params);

                return user_id;
            }
        }

        UpdateUser a=new UpdateUser();
        a.execute();
    }
}