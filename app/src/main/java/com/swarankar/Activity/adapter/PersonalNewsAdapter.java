package com.swarankar.Activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swarankar.Activity.Activity.EditPersonalNews;
import com.swarankar.Activity.Model.News.ModelNewsList;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.R;

import java.util.List;

/**
 * Created by softeaven on 9/6/17.
 */

public class PersonalNewsAdapter extends RecyclerView.Adapter<PersonalNewsAdapter.ViewHolder> {
    Context context;
    List<ModelNewsList> newsList;

    public PersonalNewsAdapter(Context context, List<ModelNewsList> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public PersonalNewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonalNewsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_news_recycler_item, parent, false), this);
    }

    @Override
    public void onBindViewHolder(PersonalNewsAdapter.ViewHolder holder, final int position) {

        if (newsList.get(position).getUsername() != null) {
            holder.txtName.setText(AndroidUtils.wordFirstCap(newsList.get(position).getUsername()));
        } else {
            holder.txtName.setText(newsList.get(position).getUsername());
        }

        holder.txtDatetime.setText(newsList.get(position).getCreatedDate() + " at " + newsList.get(position).getEventTime());
        holder.txtEvetntTitle.setText(AndroidUtils.wordFirstCap(newsList.get(position).getEventName()));
        holder.txtDateSchadual.setText("  " + newsList.get(position).getEventStartDate() + " to " + newsList.get(position).getEventEndDate());
        holder.txtContactNo.setText("  " + newsList.get(position).getContactNo());
        holder.txtContactName.setText(AndroidUtils.wordFirstCap(newsList.get(position).getCntName()));
        holder.txtAddress.setText(AndroidUtils.wordFirstCap(newsList.get(position).getAddress()));
        Glide.with(context).load(newsList.get(position).getImageFront() + "").into(holder.img);
        Glide.with(context).load(newsList.get(position).getImageFront() + "").into(holder.profile_img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EditPersonalNews.class);

                String jobid = newsList.get(position).getId();
                String strNewsitle = newsList.get(position).getEventName();
                String strAddress = newsList.get(position).getAddress();
                String strContactNumbar = newsList.get(position).getContactNo();
                String strContactName = newsList.get(position).getCntName();
                String strEventStartDate = newsList.get(position).getEventStartDate();
                String strEventEndDate = newsList.get(position).getEventEndDate();
                String strEventTime = newsList.get(position).getEventTime();
                String strCreatedDate = newsList.get(position).getCreatedDate();
                String strUsername = newsList.get(position).getUsername();
                String strphotopath = newsList.get(position).getImageFront();

                i.putExtra("jobid", jobid);
                i.putExtra("strNewsitle", strNewsitle);
                i.putExtra("strAddress", strAddress);
                i.putExtra("strEventStartDate", strEventStartDate);
                i.putExtra("strEventEndDate", strEventEndDate);
                i.putExtra("strContactName", strContactName);
                i.putExtra("strContactNumbar", strContactNumbar);
                i.putExtra("strEventTime", strEventTime);
                i.putExtra("strCreatedDate", strCreatedDate);
                i.putExtra("strUsername", strUsername);
                i.putExtra("strphotopath", strphotopath);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDatetime, txtAddress, txtContactName, txtContactNo, txtDateSchadual, txtEvetntTitle;
        ImageView img, profile_img;
        Button btnEdit, btnDelete;

        public ViewHolder(View itemView, PersonalNewsAdapter searchAdapter) {
            super(itemView);


            txtName = (TextView) itemView.findViewById(R.id.news_item_name);
            txtDatetime = (TextView) itemView.findViewById(R.id.news_item_date_time);
            txtAddress = (TextView) itemView.findViewById(R.id.news_item_address);
            txtContactName = (TextView) itemView.findViewById(R.id.news_item_contact_name);
            txtContactNo = (TextView) itemView.findViewById(R.id.news_item_contact_no);
            txtDateSchadual = (TextView) itemView.findViewById(R.id.news_item_shadual_date);
            txtEvetntTitle = (TextView) itemView.findViewById(R.id.news_item_event_title);
            img = (ImageView) itemView.findViewById(R.id.news_item_img);
            profile_img = (ImageView) itemView.findViewById(R.id.news_item_profile);
            btnEdit = (Button) itemView.findViewById(R.id.personal_news_btn_edit);
            btnDelete = (Button) itemView.findViewById(R.id.personal_news_btn_delete);


        }
    }
}