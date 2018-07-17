package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.swarankar.Activity.Model.ModelEventDetails.ModelEventDetails;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.TouchImageView;
import com.swarankar.Activity.adapter.EventDtailsImageAdapter;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class EventDetail extends AppCompatActivity {

    //ImageView imgBack;
    TextView txtDescription, txtTitle, txtVenue, txtStartdate, txtEnddate;
    List<String> imgList = new ArrayList<>();
    RecyclerView rcImage;
    EventDtailsImageAdapter adapter;
    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event Details");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(EventDetail.this);
                finish();
            }
        });
        //imgBack = (ImageView) findViewById(R.id.edit_profile_back11);
        txtDescription = (TextView) findViewById(R.id.text_description1);
        txtTitle = (TextView) findViewById(R.id.text_eventtitle);
        txtVenue = (TextView) findViewById(R.id.text_eventVenue);
        txtStartdate = (TextView) findViewById(R.id.text_evenrt_start);
        txtEnddate = (TextView) findViewById(R.id.text_event_end);

        rcImage = (RecyclerView) findViewById(R.id.event_details_recyclerview);
        rcImage.setNestedScrollingEnabled(false);


       /* imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        getData();
    }

    private void getData() {
        final ProgressDialog loading = new ProgressDialog(EventDetail.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String strEventID = getIntent().getStringExtra("id");

        Log.e("id", strEventID);
        API apiService = APIClient.getClient().create(API.class);
        Call<ModelEventDetails> call1 = apiService.get_event_details(strEventID);
        call1.enqueue(new Callback<ModelEventDetails>() {


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ModelEventDetails> call, retrofit2.Response<ModelEventDetails> response) {
                loading.dismiss();

                String strDescription = response.body().getEventDescription();
                String strTitle = response.body().getEventTitle();
//                String strImage = response.body(). getEventImage();
                String strVenue = response.body().getEventVenue();
                String strEventStart = response.body().getEventStart();
                String strEventEnd = response.body().getEventEnd();
                String strCreatDate = response.body().getCreatedDate();
                String strUpdateDate = response.body().getUpdatedDate();

                txtDescription.setText((Html.fromHtml(strDescription)));
                txtTitle.setText(strTitle);
                txtVenue.setText(strVenue);
                txtStartdate.setText(strEventStart);
                txtEnddate.setText(strEventEnd);

                imgList = response.body().getImages();
                if (imgList.size() > 0) {
                    int numberOfColumns = 3;
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(EventDetail.this, numberOfColumns);
                    rcImage.setLayoutManager(linearLayoutManager);
                    adapter = new EventDtailsImageAdapter(EventDetail.this, imgList);
                }

                rcImage.setAdapter(adapter);

//                Log.e("size", String.valueOf(imgList.size()));
                SetClick();
//                        try {
//                            Log.e("ResponseBody",response.body().string()+"");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

            }

            @Override
            public void onFailure(Call<ModelEventDetails> call, Throwable t) {
                loading.dismiss();
            }


        });


    }

    private void SetClick() {

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Dialog alertDialog = new Dialog(EventDetail.this);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(EventDetail.this, R.drawable.drawable_back_dialog));
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(alertDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                alertDialog.getWindow().setAttributes(lp);
                final View rootView = LayoutInflater.from(EventDetail.this).inflate(R.layout.event_image_fullscreen, null);
                final TouchImageView img_photoworld;

                img_photoworld = (TouchImageView) rootView.findViewById(R.id.img_photoworld);

                try {


                    Picasso.with(EventDetail.this).load(imgList.get(i)).memoryPolicy(MemoryPolicy.NO_CACHE).resize(500, 500).onlyScaleDown().into(img_photoworld);
                    Log.e("image", imgList.get(i));
//                    Glide.with(EventDetail.this).load(imgList.get(i)).asBitmap().into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                            img_photoworld.setImageBitmap(resource);
//                        }
//                    });
                } catch (Exception e) {
                    Log.e("image", e.getMessage());
                }


                alertDialog.setContentView(rootView);
                alertDialog.show();
            }
        });
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
