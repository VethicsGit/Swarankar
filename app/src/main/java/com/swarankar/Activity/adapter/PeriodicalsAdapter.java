package com.swarankar.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swarankar.Activity.Model.ModelPeriodicals.Periodical;
import com.swarankar.R;

import java.util.List;

/**
 * Created by softeaven on 7/3/17.
 */

public class PeriodicalsAdapter extends RecyclerView.Adapter<PeriodicalsAdapter.ViewHolder> {
    public AdapterView.OnItemClickListener onItemClickListener;
    List<Periodical> periodicalsList;
    private Context context;



    public PeriodicalsAdapter(Context context, List<Periodical> periodicalsList) {
        this.context = context;
        this.periodicalsList = periodicalsList;
    }


    public int getItemCount() {
        return 5;
    }



    public PeriodicalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PeriodicalsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.periodicals_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
     /*   holder.txtTitle.setText(periodicalsList.get(position).getPeriodicalName());
        Glide.with(context).load("http://demo.vethics.in/swarnkar/uploads/periodicals/" + periodicalsList.get(position).getImageFront() + "").into(holder.frontImg);
        Glide.with(context).load("http://demo.vethics.in/swarnkar/uploads/periodicals/" + periodicalsList.get(position).getImageBack() + "").into(holder.backIma ge);*/
     Glide.with(context).load("http://huronuc.ca/Assets/website/images/FASS/FASS%20Achievements/vprwinter2016-front_cover.jpg").into(holder.frontImg);
     Glide.with(context).load("http://www.victorianweb.org/art/design/books/142.jpg").into(holder.backImage);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle;
        ImageView frontImg,backImage;
        Button btnDownload;
        PeriodicalsAdapter recyclerViewAdapter;
        public ViewHolder(View itemView) {
            super(itemView);
            
            this.recyclerViewAdapter = recyclerViewAdapter; 
            frontImg = (ImageView)itemView.findViewById(R.id.periodicals_item_front_image);
            backImage = (ImageView)itemView.findViewById(R.id.periodicals_item_back_image);
            txtTitle =itemView.findViewById(R.id.periodicals_item_txt_title);
            btnDownload = (Button)itemView.findViewById(R.id.periodicals_item_btn_download);
            btnDownload.setOnClickListener(this);
        }
        public void onClick(View v) {
            this.recyclerViewAdapter.setItemClick(this);
        }
    }

    private void setItemClick(ViewHolder viewHolder) {
        this.onItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getAdapterPosition(), viewHolder.getItemId());
    }

}
