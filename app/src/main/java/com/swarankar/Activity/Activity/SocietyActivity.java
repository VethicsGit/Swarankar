package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.swarankar.Activity.Model.ModelSociety.Society;
import com.swarankar.Activity.Model.ModelSociety.SocietyJoin;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.adapter.SocietyListAdapter;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SocietyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SocietyListAdapter adapter;
    ImageView imgBack;
    List<Society> eneSocietyLists = new ArrayList<>();
    Button btnManage;
    String societyId, joinUseId, memberCounter, SocietyName, strUserid = "";

    TextView txtRegister;
    private Parcelable recyclerViewState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sodiety);

        findView();

        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SocietyActivity.this, PersonalSocietyEvent.class);
                startActivity(i);
            }
        });
    }

    private void findView() {

        recyclerView = (RecyclerView) findViewById(R.id.society_recyclerview);
        btnManage = (Button) findViewById(R.id.btn_society_manage);
        txtRegister = (TextView) findViewById(R.id.text_res_for_society);
        imgBack = (ImageView) findViewById(R.id.news_image_back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SocietyActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ApiCallSocietyList();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SocietyActivity.this, EvenRegister.class);
                startActivity(i);
            }
        });

    }

    private void ApiCallSocietyList() {

        final ProgressDialog loading = new ProgressDialog(SocietyActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        Call<List<Society>> call1 = apiService.get_society(strUserid);
        call1.enqueue(new Callback<List<Society>>() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<List<Society>> call, retrofit2.Response<List<Society>> response) {
                loading.dismiss();

                try {
//                        Log.e("get_society", "" + response.body().string());

                    if (response.isSuccessful()) {
                        eneSocietyLists.clear();


                        for (int i = 0; i < response.body().size(); i++) {
                            eneSocietyLists.add(response.body().get(i));
                        }

                        if (eneSocietyLists.size() > 0) {
                            adapter = new SocietyListAdapter(SocietyActivity.this, eneSocietyLists);
                            recyclerView.setAdapter(adapter);
                            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                            SetClick();
//
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<List<Society>> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    private void SetClick() {

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final ProgressDialog loading = new ProgressDialog(SocietyActivity.this);
                loading.setMessage("Please Wait..");
                loading.setCancelable(false);
                loading.setCanceledOnTouchOutside(false);
                loading.show();

                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

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
                });
            }
        });


    }

    private void showDialog(String error) {


        final Dialog alertDialog = new Dialog(SocietyActivity.this);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        final View rootView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.society_join_dialog1, null);

        TextView txtError = (TextView) rootView.findViewById(R.id.society_join_dialog_error);
        Button btnOk = (Button) rootView.findViewById(R.id.button_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiCallSocietyList();
                /*Intent i = new Intent(getApplicationContext(), SocietyActivity.class);
                startActivity(i);
                finish();*/
                alertDialog.dismiss();
            }
        });
        final ImageView imgClose = (ImageView) rootView.findViewById(R.id.img_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiCallSocietyList();
                alertDialog.dismiss();
            }
        });

        txtError.setText(error);


        alertDialog.setContentView(rootView);
        alertDialog.show();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(SocietyActivity.this, SocietyActivity.class);
        startActivity(i);
        finish();
    }

    @Override
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
    }


}
