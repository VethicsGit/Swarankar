package com.swarankar.Activity.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swarankar.Activity.Activity.SocietyActivity;
import com.swarankar.Activity.Model.ModelSociety.Society;
import com.swarankar.R;

import java.util.List;

public class SocietyListAdapter extends RecyclerView.Adapter<SocietyListAdapter.ViewHolder> {
    Context context;
    private List<Society> eneSocietyLists;
    private String joined;
    private AdapterView.OnItemClickListener onItemClickListener;
    SocietyActivity activity;

    public SocietyListAdapter(Context context, List<Society> eneSocietyLists) {
        this.context = context;
        this.eneSocietyLists = eneSocietyLists;
        this.activity = (SocietyActivity) context;
    }

    @Override
    public SocietyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SocietyListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adt_society_item, parent, false), this);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private void setItemClick(SocietyListAdapter.ViewHolder viewHolder) {
        this.onItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getAdapterPosition(), viewHolder.getItemId());
    }

    @Override
    public void onBindViewHolder(SocietyListAdapter.ViewHolder holder, final int position) {

        holder.textEname.setText(eneSocietyLists.get(position).getSocietyName());
        holder.textEdate.setText(eneSocietyLists.get(position).getCreatedAt());
        joined = eneSocietyLists.get(position).getJoined();
        Log.e("joined", joined);
        if (joined.equals("yes")) {
            holder.textEname.setText(eneSocietyLists.get(position).getPresentPName());
            holder.textEdate.setText(eneSocietyLists.get(position).getMobile());
            holder.btnJoin.setVisibility(View.GONE);
            holder.btnGetContacts.setVisibility(View.VISIBLE);
            //holder.btnJoin.setBackgroundColor(Color.parseColor("#37000000"));
        } else {
            holder.btnJoin.setVisibility(View.VISIBLE);
            holder.btnGetContacts.setVisibility(View.GONE);
            //holder.btnJoin.setText("Join Now");
            // holder.btnJoin.setBackgroundColor(Color.parseColor("#f06463"));
        }
        Glide.with(context).load("http://demo.vethics.in/swarnkar/uploads/society/" + eneSocietyLists.get(position).getPicture() + "").into(holder.societyImg);
        holder.btnGetContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return eneSocietyLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SocietyListAdapter recyclerViewAdapter;
        private ImageView societyImg;
        private TextView textPlace;
        private TextView textEname;
        private TextView textEdate;
        private Button btnJoin, btnGetContacts;

        public ViewHolder(View itemView, SocietyListAdapter recyclerViewAdapter) {
            super(itemView);

            societyImg = (ImageView) itemView.findViewById(R.id.society_img);
            textEname = (TextView) itemView.findViewById(R.id.society_text_ename);
            textEdate = (TextView) itemView.findViewById(R.id.society_text_edate);
            btnJoin = (Button) itemView.findViewById(R.id.society_btn_join_now);
            btnGetContacts = (Button) itemView.findViewById(R.id.society_btn_get_contacts);
            this.recyclerViewAdapter = recyclerViewAdapter;
            btnJoin.setOnClickListener(this);
        }

        public void onClick(View v) {
            this.recyclerViewAdapter.setItemClick(this);
        }
    }

    private void showDialog(int position) {

        final Dialog alertDialog = new Dialog(activity);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(activity, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        final View rootView = LayoutInflater.from(activity).inflate(R.layout.society_join_dialog1, null);

        TextView txtError = (TextView) rootView.findViewById(R.id.society_join_dialog_error);
        Button btnOk = (Button) rootView.findViewById(R.id.button_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        final ImageView imgClose = (ImageView) rootView.findViewById(R.id.img_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        txtError.setText("President's name : " + eneSocietyLists.get(position).getPresentPName()
                + "\n" + "President contact : " + eneSocietyLists.get(position).getMobile()
                + "\n" + "Secretory name : " + eneSocietyLists.get(position).getPresentSName()
                + "\n" + "Secretory contact : " + eneSocietyLists.get(position).getPresentSMobile()
                + "\n" + "Address :" + eneSocietyLists.get(position).getAddress());
        alertDialog.setContentView(rootView);
        alertDialog.show();

    }

}
