package com.swarankar.Activity.Activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.swarankar.Activity.Model.EventList;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.adapter.EventListAdapter;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class EventsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EventListAdapter adapter;
    //ImageView imgBack;
    List<EventList> eneEventLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(EventsActivity.this);
                finish();
            }
        });
        findView();
    }

    private void findView() {

        recyclerView = (RecyclerView) findViewById(R.id.events_recyclerview);
        //imgBack = (ImageView) findViewById(R.id.event_img_back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ApiCallEventsList();
        /*imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

    }

    private void ApiCallEventsList() {

        final ProgressDialog loading = new ProgressDialog(EventsActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<List<EventList>> call1 = apiService.get_event();
        call1.enqueue(new Callback<List<EventList>>() {


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<List<EventList>> call, retrofit2.Response<List<EventList>> response) {
                loading.dismiss();

                try {
//                    Log.e("get_event", "" + response.body().string());

                    if (response.isSuccessful()) {
                        eneEventLists.clear();

                        for (int i = 0; i < response.body().size(); i++) {
                            eneEventLists.add(response.body().get(i));
                        }

                        if (eneEventLists.size() > 0) {
                            adapter = new EventListAdapter(getApplicationContext(), eneEventLists);
                            recyclerView.setAdapter(adapter);
                            SetClick();
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<List<EventList>> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    private void SetClick() {

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });


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
}
