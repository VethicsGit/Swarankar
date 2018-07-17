package com.swarankar.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swarankar.Activity.Model.News.ModelNewsList;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    Context context;
    List<ModelNewsList> newsList;

    public NewsAdapter(Context context, List<ModelNewsList> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_item, parent, false), this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

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
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDatetime, txtAddress, txtContactName, txtContactNo, txtDateSchadual, txtEvetntTitle;
        ImageView img, profile_img;

        public ViewHolder(View itemView, NewsAdapter newsAdapter) {
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

        }
    }
}
