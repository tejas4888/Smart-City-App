package com.example.tejas.smartcityapp.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tejas.smartcityapp.Items.VolunteerItem;
import com.example.tejas.smartcityapp.R;

import java.util.ArrayList;

/**
 * Created by Tejas on 30-03-2018.
 */

public class VolunteerRecyclerAdapter extends RecyclerView.Adapter<VolunteerRecyclerAdapter.ViewHolder> {

    ArrayList<VolunteerItem> arrayList;
    Context context;

    public VolunteerRecyclerAdapter(ArrayList<VolunteerItem> arrayList,Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public VolunteerRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.volunteer_detail_item, parent, false);
        return new VolunteerRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VolunteerRecyclerAdapter.ViewHolder holder, final int position) {
        holder.volunteer_name.setText(arrayList.get(position).name);

        holder.call_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + arrayList.get(position).contact));
                context.startActivity(callIntent);
            }
        });

        holder.add_contact_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, arrayList.get(position).name);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE,  arrayList.get(position).contact);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView volunteer_name;
        ImageView call_img,add_contact_image;

        public ViewHolder(View itemView) {
            super(itemView);

            volunteer_name=(TextView)itemView.findViewById(R.id.volunteer_item_name);
            call_img=(ImageView)itemView.findViewById(R.id.volunteer_item_call);
            add_contact_image=(ImageView)itemView.findViewById(R.id.volunteer_item_add_contact);
        }
    }
}
