package com.swarankar.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.swarankar.Activity.Model.joblist.JobListDatum;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.R;

import java.util.List;

public class JobsAdapter extends Adapter<JobsAdapter.ViewHolder> {
    public OnItemClickListener onItemClickListener;
    List<JobListDatum> araList;
    private Context context;

    public JobsAdapter(Context activity, List<JobListDatum> araList) {
        this.context = activity;
        this.araList = araList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public int getItemCount() {
//        return 10;
        return this.araList.size();
    }

    public void setItemClick(ViewHolder viewHolder) {
        this.onItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getAdapterPosition(), viewHolder.getItemId());
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adt_job_activity, parent, false), this);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.textJobtitle.setText(AndroidUtils.wordFirstCap(araList.get(position).getJobTitle()));
        viewHolder.textOrganization.setText(AndroidUtils.wordFirstCap(araList.get(position).getOrganization()));
        viewHolder.textDesignation.setText(AndroidUtils.wordFirstCap(araList.get(position).getDesignation()));
        viewHolder.textId.setText(AndroidUtils.wordFirstCap(araList.get(position).getId()));
        viewHolder.textMinutedgo.setText(AndroidUtils.wordFirstCap(araList.get(position).getCreatedDate()));


    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {

        JobsAdapter recyclerViewAdapter;
        private TextView textJobtitle;
        private TextView textOrganization;
        private TextView textDesignation;
        private TextView textId;
        private ImageView imgNext;
        private TextView textMinutedgo;


        public ViewHolder(View itemView, JobsAdapter recyclerViewAdapter) {
            super(itemView);

            textJobtitle = (TextView) itemView.findViewById(R.id.text_jobtitle);
            textOrganization = (TextView) itemView.findViewById(R.id.text_organization);
            textDesignation = (TextView) itemView.findViewById(R.id.text_designation);
            textId = (TextView) itemView.findViewById(R.id.text_id);
            imgNext = (ImageView) itemView.findViewById(R.id.img_next);
            textMinutedgo = (TextView) itemView.findViewById(R.id.text_minutedgo);
            this.recyclerViewAdapter = recyclerViewAdapter;

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            this.recyclerViewAdapter.setItemClick(this);
        }
    }
}
