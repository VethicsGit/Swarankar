package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.swarankar.Activity.Model.Notification.ModelNotification;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.adapter.NotificationAdapter;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by softeaven on 6/27/2017.
 */


public class Notification1 extends AppCompatActivity {
    public static final int FLAG_ONGOING_EVENT = 1;
    RecyclerView notificationRecyclerview;
    //ImageView imgBack;
    String strUserid;
    NotificationAdapter adapter;
    List<ModelNotification> notificationList;
    private Parcelable recyclerViewState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(Notification1.this);
                finish();
            }
        });
        getData();

        notificationRecyclerview = (RecyclerView) findViewById(R.id.recyclerView_activity_notification);

       /* imgBack = (ImageView) findViewById(R.id.notification_img_back);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/
    }

    private void getData() {
        final ProgressDialog loading = new ProgressDialog(Notification1.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();


        strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiservice = APIClient.getClient().create(API.class);
        Call<List<ModelNotification>> call1 = apiservice.notification(strUserid);
        call1.enqueue(new Callback<List<ModelNotification>>() {

            @Override
            public void onResponse(Call<List<ModelNotification>> call, Response<List<ModelNotification>> response) {
                loading.dismiss();
                notificationList = new ArrayList<>();
                notificationList = response.body();
                if (notificationList.size() > 0) {
                    adapter = new NotificationAdapter(Notification1.this, notificationList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    notificationRecyclerview.setLayoutManager(linearLayoutManager);
                    notificationRecyclerview.setAdapter(adapter);
                    SetClick();
                } else {
                    dialog("You dont have any notification", Notification1.this);
                }
                //SetClick();
//                try {
//                    Log.e("responsenoti",response.body().string()+"");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<List<ModelNotification>> call, Throwable t) {
                loading.dismiss();
            }
        });

    }

    private void SetClick() {

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Notification1.this, "Notification Clicked!", Toast.LENGTH_SHORT).show();
               /* final ProgressDialog loading = new ProgressDialog(Notification1.this);
                loading.setMessage("Please Wait..");
                loading.setCancelable(false);
                loading.setCanceledOnTouchOutside(false);
                loading.show();

                recyclerViewState = notificationRecyclerview.getLayoutManager().onSaveInstanceState();

                societyId = eneSocietyLists.get(i).getId();
                joinUseId = Constants.loginSharedPreferences.getString(Constants.uid, "");
                memberCounter = eneSocietyLists.get(i).getTotalMemberCounter();
                SocietyName = eneSocietyLists.get(i).getSocietyName();

                API apiService = APIClient.getClient().create(API.class);
                Call<SocietyJoin> call1 = apiService.join_society(societyId, joinUseId, memberCounter, SocietyName);
                call1.enqueue(new Callback<SocietyJoin>() {

                    @Override
                    public void onResponse(Call<SocietyJoin> call, retrofit2.Response<SocietyJoin> response) {
                        loading.dismiss();

                        try {
//                        Log.e("join_society", "" + response.body().string());

                            String res = response.body().getResponse();
                            if (res.equals("Failure")) {
                                String error = response.body().getMessage();
                                showDialog(error);
                            } else {
                                String str = "You successfully join the society";
                                showDialog(str);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(Call<SocietyJoin> call, Throwable t) {
                        loading.dismiss();
                    }
                });*/
            }
        });


    }

    private void dialog(String s, Notification1 family) {
        final Dialog alertDialog = new Dialog(family);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(family, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(family).inflate(R.layout.view_alert_dialog, null);
        Button button_ok;
        ImageView btnClose;
        TextView textview;

        button_ok = (Button) rootView.findViewById(R.id.button_ok);
        textview = (TextView) rootView.findViewById(R.id.textview);
        btnClose = (ImageView) rootView.findViewById(R.id.img_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if (!s.equals("")) {
            textview.setText(s);
        }

        alertDialog.setContentView(rootView);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();

            }
        });
        alertDialog.show();

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_home1) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }*/

//    private void SetClick() {
//        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
////                notificationList.get(i).getUserimage();
//                Intent i1 = new Intent(Notification1.this,SearchProfile.class);
//                i1.putExtra("id","4");
//                startActivity(i1);
//            }
//        });
//    }
}
