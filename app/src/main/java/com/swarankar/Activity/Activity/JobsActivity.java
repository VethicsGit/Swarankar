package com.swarankar.Activity.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.swarankar.Activity.Model.joblist.JobListDatum;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.adapter.JobsAdapter;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class JobsActivity extends AppCompatActivity {

    JobsAdapter jobsAdapter;
    public static List<JobListDatum> arStrings = new ArrayList<>();
    RecyclerView my_recycler_view;
    ImageView img_back;
    ImageView fab_add_job;
    Button btnJobManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        findViews();
    }

    private void findViews() {

        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        img_back = (ImageView) findViewById(R.id.img_back);
        fab_add_job = (ImageView) findViewById(R.id.fab_add_job);
        btnJobManage = (Button) findViewById(R.id.btn_job_manage);
        btnJobManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JobManage.class);
                startActivity(intent);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        fab_add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddJobActivity.class);
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        my_recycler_view.setLayoutManager(linearLayoutManager);
        ApiCalLJobsList();
    }

    private void ApiCalLJobsList() {
        final ProgressDialog loading = new ProgressDialog(JobsActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<List<JobListDatum>> call1 = apiService.fetch_job_data();
        call1.enqueue(new Callback<List<JobListDatum>>() {


            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<List<JobListDatum>> call, retrofit2.Response<List<JobListDatum>> response) {
                loading.dismiss();

                try {

                    if (response.isSuccessful()) {

                        arStrings.clear();
                        if (response.body().size() > 0) {
                            for (int i = 0; i < response.body().size(); i++) {

                                arStrings.add(response.body().get(i));
                            }

                            if (arStrings.size() > 0) {
                                jobsAdapter = new JobsAdapter(getApplicationContext(), arStrings);
                                my_recycler_view.setAdapter(jobsAdapter);
                                setClick();
                            }
                        }
                    }
//                    Log.e("fetch_job_data", response.body().string() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<List<JobListDatum>> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    private void setClick() {

        jobsAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), JobDetailActivity.class);
                intent.putExtra("position", i);
                startActivity(intent);

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
