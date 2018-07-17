package com.swarankar.Activity.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import com.swarankar.Activity.Activity.EditSociety;
import com.swarankar.Activity.Model.ModelSociety.ModelPersonalSociety;
import com.swarankar.Activity.Model.News.NewsPost;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Champ on 11-09-2017.
 */

public class PersonalSocietyListAdapter  extends RecyclerView.Adapter<PersonalSocietyListAdapter.ViewHolder> {
    Context context;
    List<ModelPersonalSociety> eneSocietyLists;
    String societyid,userid;
    final ProgressDialog loading;
    public AdapterView.OnItemClickListener onItemClickListener;
    public PersonalSocietyListAdapter(Context context, List<ModelPersonalSociety> eneSocietyLists) {
        this.context = context;
        this.eneSocietyLists = eneSocietyLists;
        loading = new ProgressDialog(context);
    }

    @Override
    public PersonalSocietyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonalSocietyListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_society_item, parent, false), this);
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public void setItemClick(PersonalSocietyListAdapter.ViewHolder viewHolder) {
        this.onItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getAdapterPosition(), viewHolder.getItemId());
    }


    @Override
    public void onBindViewHolder(PersonalSocietyListAdapter.ViewHolder holder, final int position) {


        holder.btnEdit.setBackgroundColor(Color.parseColor("#f06463"));
        holder.btnDelete.setBackgroundColor(Color.parseColor("#f06463"));
        holder.textEname.setText(eneSocietyLists.get(position).getSocietyName());
        holder.textEdate.setText(eneSocietyLists.get(position).getCreatedAt());
        Glide.with(context).load(eneSocietyLists.get(position).getPicture() + "").into(holder.societyImg);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                society_id:6
//                society_name:Testing society333333
//                reg_status:Registered33333
//                president_name:President Name333333
//                president_contact_number:321654987333333
//                secretary_name:Secratory Name33333333
//                secretary_contact_number:32165498733333
//                address:This is testing address333333
//                country:3
//                state:333
//                city:3333
//                picture:base64 if image
//
                Intent i = new Intent(context, EditSociety.class);
                String societyid = eneSocietyLists.get(position).getId();

                String societyName = eneSocietyLists.get(position).getSocietyName();
                String address = eneSocietyLists.get(position).getAddress();
                String status = eneSocietyLists.get(position).getRegStatus();
                String presidentname = eneSocietyLists.get(position).getPresentPName();
                String presidentno = eneSocietyLists.get(position).getMobile();
                String secreteryname = eneSocietyLists.get(position).getPresentSName();
                String secreteryno = eneSocietyLists.get(position).getPresentSMobile();
                String country = eneSocietyLists.get(position).getCountry();
                String state = eneSocietyLists.get(position).getState();
                String city = eneSocietyLists.get(position).getCity();
                String picture = eneSocietyLists.get(position).getPicture();


                i.putExtra("societyid", societyid);
                i.putExtra("societyName", societyName);
                i.putExtra("address", address);
                i.putExtra("status", status);
                i.putExtra("presidentname", presidentname);
                i.putExtra("presidentno", presidentno);
                i.putExtra("secreteryname", secreteryname);
                i.putExtra("secreteryno", secreteryno);
                i.putExtra("country", country);
                i.putExtra("state", state);
                i.putExtra("city", city);
                i.putExtra("picture", picture);
                Log.e("picture",picture);
                Log.e("state",state);
                Log.e("city",city);
                Log.e("picture",picture);
                Log.e("status",status);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                societyid = eneSocietyLists.get(position).getId();
                userid = Constants.loginSharedPreferences.getString(Constants.uid,"");




                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(false);
                dialog.setMessage("Are you sure you want delete this society event?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteSociety(context,position);
                    }
                }).setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.create().show();

            }
        });
    }

    private void deleteSociety(Context context, final int position) {

        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(LoginActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<NewsPost> call1 = apiService.delete_society(societyid, userid);
        call1.enqueue(new Callback<NewsPost>() {
            @Override
            public void onResponse(Call<NewsPost> call, retrofit2.Response<NewsPost> response) {
                loading.dismiss();
//                try {
//                    Log.e("responseDelete",response.body().string()+"");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                String success1 = response.body().getResponse();
                if(success1.equals("Success")){
                    eneSocietyLists.remove(position);
                    completeDialog(response.body().getMessage());
                    notifyDataSetChanged();
                }


            }


            @Override
            public void onFailure(Call<NewsPost> call, Throwable t) {
                loading.dismiss();


                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

    private void completeDialog(String message) {

        final Dialog alertDialog = new Dialog(context);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(context, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_profile_not_completed, null);
        Button button_ok;
        ImageView btnClose;
        TextView textview;

        button_ok = (Button) rootView.findViewById(R.id.button_ok);
        textview = (TextView) rootView.findViewById(R.id.textview);
        textview.setText(message);
        btnClose = (ImageView)rootView.findViewById(R.id.img_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setContentView(rootView);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return eneSocietyLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        PersonalSocietyListAdapter recyclerViewAdapter;
        private ImageView societyImg;
        private TextView textPlace;
        private TextView textEname;
        private TextView textEdate;
        private Button btnEdit;
        private Button btnDelete;
        public ViewHolder(View itemView, PersonalSocietyListAdapter recyclerViewAdapter) {
            super(itemView);

            societyImg  =(ImageView)itemView.findViewById(R.id.society_img);
            textEname = (TextView)itemView.findViewById(R.id.society_text_ename);
            textEdate = (TextView)itemView.findViewById(R.id.society_text_edate);
            btnEdit = (Button) itemView.findViewById(R.id.society_btn_edit);
            btnDelete = (Button)itemView.findViewById(R.id.society_btn_delete);
            this.recyclerViewAdapter = recyclerViewAdapter;
//            btnJoin.setOnClickListener(this);
        }
        public void onClick(View v) {
            this.recyclerViewAdapter.setItemClick(this);
        }
    }

}
