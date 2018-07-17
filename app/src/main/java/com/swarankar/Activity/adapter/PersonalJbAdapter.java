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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.swarankar.Activity.Activity.JobEdit;
import com.swarankar.Activity.Model.News.NewsPost;
import com.swarankar.Activity.Model.joblist.ModelPersonalJob;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by softeaven on 9/5/17.
 */

public class PersonalJbAdapter extends RecyclerView.Adapter<PersonalJbAdapter.ViewHolder> {
    Context context;
    List<ModelPersonalJob> listJob;
    String jobid;
    String userid;
    final ProgressDialog loading;


    public PersonalJbAdapter(Context context, List<ModelPersonalJob> listJob) {
        this.context = context;
        this.listJob = listJob;
        loading = new ProgressDialog(context);
    }

    @Override
    public PersonalJbAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PersonalJbAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_job_item, parent, false), this);
    }

    @Override
    public void onBindViewHolder(final PersonalJbAdapter.ViewHolder holder, final int position) {


        holder.textJobtitle.setText(AndroidUtils.wordFirstCap(listJob.get(position).getJobTitle()));
        holder.textOrganization.setText(AndroidUtils.wordFirstCap(listJob.get(position).getOrganization()));
        holder.textDesignation.setText(AndroidUtils.wordFirstCap(listJob.get(position).getDesignation()));
        holder.textId.setText(listJob.get(position).getId());
        holder.textMinutedgo.setText(listJob.get(position).getCreatedDate());


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, JobEdit.class);
                String jobid = listJob.get(position).getId();
                String strJobtitle = listJob.get(position).getJobTitle();
                String strAreaOfWork = listJob.get(position).getAreaOfWork();
                String strDepartment = listJob.get(position).getDepartment();
                String strDesignation = listJob.get(position).getDesignation();
                String strContactName = listJob.get(position).getContactName();
                String strContactNumbar = listJob.get(position).getContactNo();
                String strContactEmail = listJob.get(position).getContactEmail();
                String strLastdate = listJob.get(position).getLastDateApply();
                String strQualificatio = listJob.get(position).getReqQualification();
                String strJobDescription = listJob.get(position).getJobDescription();

                i.putExtra("jobid", jobid);
                i.putExtra("strJobtitle", strJobtitle);
                i.putExtra("strAreaOfWork", strAreaOfWork);
                i.putExtra("strDepartment", strDepartment);
                i.putExtra("strDesignation", strDesignation);
                i.putExtra("strContactName", strContactName);
                i.putExtra("strContactNumbar", strContactNumbar);
                i.putExtra("strContactEmail", strContactEmail);
                i.putExtra("strLastdate", strLastdate);
                i.putExtra("strQualificatio", strQualificatio);
                i.putExtra("strJobDescription", strJobDescription);

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobid = listJob.get(position).getId();
                userid = Constants.loginSharedPreferences.getString(Constants.uid, "");

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(false);
                dialog.setMessage("Are you sure you want delete this job?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteJob(context, position);
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

    private void deleteJob(Context context, final int position) {


        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(LoginActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<NewsPost> call1 = apiService.job_delete(jobid, userid);
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
                if (success1.equals("Success")) {
                    listJob.remove(position);
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
        btnClose = (ImageView) rootView.findViewById(R.id.img_close);
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
        return listJob.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textJobtitle;
        private TextView textOrganization;
        private TextView textDesignation;
        private TextView textId;
        private ImageView imgNext;
        private TextView textMinutedgo;
        private Button btnEdit, btnDelete;

        public ViewHolder(View itemView, PersonalJbAdapter searchAdapter) {
            super(itemView);


            textJobtitle = (TextView) itemView.findViewById(R.id.text_jobtitle);
            textOrganization = (TextView) itemView.findViewById(R.id.text_organization);
            textDesignation = (TextView) itemView.findViewById(R.id.text_designation);
            textId = (TextView) itemView.findViewById(R.id.text_id);
            imgNext = (ImageView) itemView.findViewById(R.id.img_next);
            textMinutedgo = (TextView) itemView.findViewById(R.id.text_minutedgo);
            btnEdit = (Button) itemView.findViewById(R.id.img_edit);
            btnDelete = (Button) itemView.findViewById(R.id.img_delete);


        }
    }
}
