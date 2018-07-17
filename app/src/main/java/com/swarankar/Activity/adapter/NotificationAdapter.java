package com.swarankar.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swarankar.Activity.Model.Notification.ModelNotification;
import com.swarankar.R;

import java.util.List;

/**
 * Created by softeaven on 6/27/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    Context context;
    List<ModelNotification> notificationList;
    private AdapterView.OnItemClickListener onItemClickListener;

    public NotificationAdapter(Context context, List<ModelNotification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_chat_detail, parent, false), this);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    private void setItemClick(NotificationAdapter.ViewHolder viewHolder) {
        this.onItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getAdapterPosition(), viewHolder.getItemId());
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder holder, int position) {

        holder.txtName.setText(Html.fromHtml(notificationList.get(position).getMessage()));
        holder.txtDate.setText((notificationList.get(position).getDate()));
        Picasso.with(context).load("" + notificationList.get(position).getUserimage())/*.error(R.drawable.placeholder)*/.into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgUser;
        TextView txtName, txtDate;
        NotificationAdapter notificationAdapter;

        public ViewHolder(View itemView, NotificationAdapter notificationAdapter) {
            super(itemView);
            imgUser = (ImageView) itemView.findViewById(R.id.imageview_adapter_item_team_detail);
            txtName = (TextView) itemView.findViewById(R.id.notification_txt_name);
            txtDate = (TextView) itemView.findViewById(R.id.notificatio_textview_date);
            this.notificationAdapter = notificationAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.notificationAdapter.setItemClick(this);
        }
    }


}
