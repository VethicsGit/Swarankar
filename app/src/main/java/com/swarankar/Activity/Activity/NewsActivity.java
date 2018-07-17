package com.swarankar.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.swarankar.Activity.Model.News.ModelNewsList;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.adapter.NewsAdapter;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {


    RecyclerView newsRecyclerview;
    NewsAdapter adapter;
    ImageView imgBack;
    Button btnNewsManage;
    List<ModelNewsList> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsRecyclerview = (RecyclerView) findViewById(R.id.news_recyclerview);
        imgBack = (ImageView) findViewById(R.id.news_image_back);
        btnNewsManage = (Button) findViewById(R.id.btn_news_manage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        newsRecyclerview.setLayoutManager(linearLayoutManager);


        getNewsData();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView fab = (ImageView) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewsActivity.this, NewsPostActivity.class);
                startActivity(i);
            }
        });

        btnNewsManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewsActivity.this, PersonalNews.class);
                startActivity(i);
            }
        });
    }

    private void getNewsData() {

        final ProgressDialog loading = new ProgressDialog(NewsActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();


        API apiservice = APIClient.getClient().create(API.class);
        Call<List<ModelNewsList>> call1 = apiservice.newslist();
        call1.enqueue(new Callback<List<ModelNewsList>>() {
            @Override
            public void onResponse(Call<List<ModelNewsList>> call, Response<List<ModelNewsList>> response) {

                loading.dismiss();
                newsList = response.body();
                adapter = new NewsAdapter(getApplicationContext(), newsList);
                newsRecyclerview.setAdapter(adapter);
//                try {
//                    Log.e("newsList",response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<List<ModelNewsList>> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
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
