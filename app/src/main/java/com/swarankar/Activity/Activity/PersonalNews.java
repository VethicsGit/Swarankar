package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.swarankar.Activity.Model.News.ModelNewsList;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.adapter.PersonalNewsAdapter;
import com.swarankar.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalNews extends AppCompatActivity {

    RecyclerView rcPersonalNews;
    //ImageView img_back;
    List<ModelNewsList> newsList;
    PersonalNewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Personal News");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(PersonalNews.this);
                finish();
            }
        });
        rcPersonalNews = (RecyclerView) findViewById(R.id.personal_news_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcPersonalNews.setLayoutManager(linearLayoutManager);
       /* img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        getData();
    }

    private void getData() {
        final ProgressDialog loading = new ProgressDialog(PersonalNews.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();


        String struserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiservice = APIClient.getClient().create(API.class);
        Call<List<ModelNewsList>> call1 = apiservice.newslist1(struserid);
        call1.enqueue(new Callback<List<ModelNewsList>>() {
            @Override
            public void onResponse(Call<List<ModelNewsList>> call, Response<List<ModelNewsList>> response) {

                loading.dismiss();
                newsList = response.body();
                if (newsList.size() > 0) {
                    adapter = new PersonalNewsAdapter(getApplicationContext(), newsList);
                    rcPersonalNews.setAdapter(adapter);
                } else {
                    dialog("You dont have any news to manage", PersonalNews.this);
                }


//                try {
//                    Log.e("newsList",response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<List<ModelNewsList>> call, Throwable t) {
                loading.dismiss();
                dialog("You dont have any news to manage", PersonalNews.this);
//                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dialog(String s, PersonalNews family) {
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

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(PersonalNews.this, PersonalNews.class);
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
