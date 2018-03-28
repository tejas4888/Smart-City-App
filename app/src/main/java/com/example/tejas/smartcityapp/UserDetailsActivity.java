package com.example.tejas.smartcityapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.example.tejas.smartcityapp.HelperClasses.RequestHandler;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDetailsActivity extends AppCompatActivity {

    RadioButton radioButton1,radioButton2;
    EditText editText_contact;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5;
    LinearLayout checkLayout;
    Button btnSubmit;
    private FirebaseAuth firebaseAuth;

    EditText editText_address;
    CheckBox checkBox_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        radioButton1=(RadioButton)findViewById(R.id.userdetails_radio1);
        radioButton2=(RadioButton)findViewById(R.id.userdetails_radio2);

        checkBox1=(CheckBox)findViewById(R.id.userdetails_check_1);
        checkBox2=(CheckBox)findViewById(R.id.userdetails_check_2);
        checkBox3=(CheckBox)findViewById(R.id.userdetails_check_3);
        checkBox4=(CheckBox)findViewById(R.id.userdetails_check_4);
        checkBox5=(CheckBox)findViewById(R.id.userdetails_check_5);

        checkLayout=(LinearLayout)findViewById(R.id.userdetails_checkLayout);

        btnSubmit=(Button)findViewById(R.id.userdetails_btnSubmit);

        editText_contact=(EditText)findViewById(R.id.userdetails_editText_Contact);

        editText_address=(EditText)findViewById(R.id.userdetails_address_edittext);
        checkBox_email=(CheckBox)findViewById(R.id.userdetails_mail_checkbox);

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

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

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

                if (checkBox_email.isChecked())
                {
                    email_notification="1";
                }else
                {
                    email_notification="0";
                }
                addUser(firebaseAuth.getCurrentUser().getDisplayName(),firebaseAuth.getCurrentUser().getEmail(),contact,type,types,
                        address_text,email_notification);
            }
        });
    }

    private void addUser(final String name, final String email, final String contact, final int type, final int types[],
                         final String address, final String email_notfication)
    {
        class AddUser extends AsyncTask<Void,Void,String>
        {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(UserDetailsActivity.this,"Adding User","Please Wait...",false,
                false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                SharedPreferences prefs = getSharedPreferences(AppConstants.LOGIN_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor=prefs.edit();
                editor.putString(email,"1");
                editor.commit();

                prefs = getSharedPreferences(AppConstants.CURRENT_USER, MODE_PRIVATE);
                editor=prefs.edit();
                editor.putString("user_id",s);
                editor.putString("name",name);
                editor.putString("email",email);
                editor.putString("contact",contact);
                editor.putString("address",address);
                editor.putString("type",String.valueOf(type));
                editor.putString("type1",String.valueOf(types[0]));
                editor.putString("type2",String.valueOf(types[1]));
                editor.putString("type3",String.valueOf(types[2]));
                editor.putString("type4",String.valueOf(types[3]));
                editor.putString("type5",String.valueOf(types[4]));
                editor.commit();

                Intent intent = new Intent(UserDetailsActivity.this,MainActivity.class);
                startActivity(intent);
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
                String user_id = rh.sendPostRequest(AppConstants.add_user, params);

                Log.v("USER ADD",user_id);
                return user_id;
            }
        }

        AddUser a=new AddUser();
        a.execute();
    }
}
