package com.swarankar.Activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.swarankar.Activity.Activity.EventDetail;
import com.swarankar.Activity.Model.EventList;
import com.swarankar.Activity.Model.ModelEventDetails.ModelEventDetails;
import com.swarankar.R;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit2.Callback;

/**
 * Created by softeaven on 9/2/17.
 */

public class EventDtailsImageAdapter extends RecyclerView.Adapter<EventDtailsImageAdapter.ViewHolder> {
    public AdapterView.OnItemClickListener onItemClickListener;
    List<String> imgList;
    private Context context;

    public  EventDtailsImageAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
    }


    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public int getItemCount() {
        return this.imgList.size();
    }

    public void setItemClick(ViewHolder viewHolder) {
        this.onItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getAdapterPosition(), viewHolder.getItemId());
    }

    public EventDtailsImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventDtailsImageAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_image_item, parent, false), this);
    }

    public void onBindViewHolder(EventDtailsImageAdapter.ViewHolder viewHolder, final int position) {


        Picasso.with(context).load(imgList.get(position)).memoryPolicy(MemoryPolicy.NO_CACHE).resize(500,500).into(viewHolder.img);




    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements View.OnClickListener {

      ImageView img;
        EventDtailsImageAdapter recyclerViewAdapter;
        public ViewHolder(View itemView, EventDtailsImageAdapter recyclerViewAdapter) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.recyclerViewAdapter = recyclerViewAdapter;
            img = (ImageView)itemView.findViewById(R.id.event_item_image);
        }

        @Override
        public void onClick(View view) {
              recyclerViewAdapter.setItemClick(this);
        }

//
    }
}