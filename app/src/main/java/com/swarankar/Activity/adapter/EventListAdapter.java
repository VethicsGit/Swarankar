package com.swarankar.Activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swarankar.Activity.Activity.EventDetail;
import com.swarankar.Activity.Model.EventList;
import com.swarankar.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends Adapter<EventListAdapter.ViewHolder> {
    public OnItemClickListener onItemClickListener;
    List<EventList> araList;
    private Context context;


    public EventListAdapter(Context  context, List<EventList> araList) {
        this.context = context;
        this.araList = araList;
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public int getItemCount() {
        return this.araList.size();
    }

    public void setItemClick(ViewHolder viewHolder) {
        this.onItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getAdapterPosition(), viewHolder.getItemId());
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.events_item, parent, false), this);
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.textPlace.setText("Place: " + araList.get(position).getEventVenue());
        viewHolder.textEname.setText(araList.get(position).getEventTitle());
        viewHolder.textEdate.setText(araList.get(position).getEventStart());

        viewHolder.event_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                final ProgressDialog loading = new ProgressDialog(context.);
//                loading.setMessage("Please Wait..");
//                loading.setCancelable(false);
//                loading.setCanceledOnTouchOutside(false);
//                loading.show();

                String eventID = araList.get(position).getId();

                Intent i = new Intent(context.getApplicationContext(), EventDetail.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id",eventID);
                context.startActivity(i);
            }
        });


        byte[] data = Base64.decode(araList.get(position).getEventDescription(), Base64.DEFAULT);
        try {
            String text = new String(data, "UTF-8");
//            Log.e("des",text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {

//            Log.e("img","http://demo.vethics.in/swarnkar/uploads/event/" + araList.get(position).getEventImage() + "");
            Glide.with(context).load("http://demo.vethics.in/swarnkar/uploads/event/" + araList.get(position).getEventImage() + "").into(viewHolder.eventImg);
        } catch (Exception e) {
        }

    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {

        EventListAdapter recyclerViewAdapter;
        private ImageView eventImg;
        private TextView textPlace;
        private TextView textEname;
        private TextView textEdate;
        private Button btnSeeEvents;
        private RelativeLayout event_layout;


        public ViewHolder(View itemView, EventListAdapter recyclerViewAdapter) {
            super(itemView);


            eventImg = (ImageView) itemView.findViewById(R.id.event_img);
            textPlace = (TextView) itemView.findViewById(R.id.text_place);
            textEname = (TextView) itemView.findViewById(R.id.text_ename);
            textEdate = (TextView) itemView.findViewById(R.id.text_edate);
            btnSeeEvents = (Button) itemView.findViewById(R.id.btn_see_events);
            event_layout =itemView.findViewById(R.id.event_layout);
            this.recyclerViewAdapter = recyclerViewAdapter;
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            this.recyclerViewAdapter.setItemClick(this);
        }
    }
}
