package com.swarankar.Activity.Activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swarankar.Activity.Model.ModelPeriodicals.Periodical;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.adapter.PeriodicalsAdapter;
import com.swarankar.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Periodicals extends AppCompatActivity {

    private static final int MY_READWRITE_REQUEST_CODE = 1;
    RecyclerView periodicalsRecyclerview;
    ImageView imgFab;
    List<Periodical> periodicalsList = new ArrayList<>();
    PeriodicalsAdapter adapter;
    String title;
    int downloadedSize = 0, totalsize;
    ProgressBar progressBar;
    private int progress = 10;
    Notification notification;
    NotificationManager notificationManager;


    float per = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodicals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Periodicals");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(Periodicals.this);
                finish();
            }
        });
        findView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        periodicalsRecyclerview.setLayoutManager(linearLayoutManager);


        imgFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Periodicals.this, PeriodicalRegister.class);
                startActivity(i);
            }
        });
        /*imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/


        if (ContextCompat.checkSelfPermission(Periodicals.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            getData();

        } else {
            // Show rationale and request permission.
            ActivityCompat.requestPermissions(Periodicals.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_READWRITE_REQUEST_CODE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_READWRITE_REQUEST_CODE) {
            if (/*permissions.length >= 1 &&
                    permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE &&*/
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getData();


            } else {
                // Permission was denied. Display an error message.
                Toast.makeText(Periodicals.this, "Read/Write permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getData() {


        final ProgressDialog loading = new ProgressDialog(Periodicals.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiservice = APIClient.getClient().create(API.class);
        Call<List<Periodical>> call1 = apiservice.periodicals();
        call1.enqueue(new Callback<List<Periodical>>() {
            @Override
            public void onResponse(Call<List<Periodical>> call, Response<List<Periodical>> response) {
                loading.dismiss();
//                try {
//                    Log.e("periodicalsResponse",response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                periodicalsList = response.body();
                adapter = new PeriodicalsAdapter(getApplicationContext(), periodicalsList);
                periodicalsRecyclerview.setAdapter(adapter);
                SetClick();

            }

            @Override
            public void onFailure(Call<List<Periodical>> call, Throwable t) {

            }
        });

    }

    private void SetClick() {

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                final String pdf = periodicalsList.get(i).getPdf();

                title = periodicalsList.get(i).getPeriodicalName();


                pdfDwnload(pdf);


            }
        });
    }

    private void pdfDwnload(final String pdf1) {


        final ProgressDialog loading = new ProgressDialog(Periodicals.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setCanceledOnTouchOutside(false);
        loading.show();


//        Intent intent = new Intent();
//        final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
//        notification = new Notification(R.drawable.ic_menu_camera, "Uploading file", System.currentTimeMillis());
//        notification.flags = notification.flags | Notification1.FLAG_ONGOING_EVENT;
//        notification.contentView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.download_progress);
//        notification.contentIntent = pendingIntent;
//        notification.contentView.setImageViewResource(R.id.status_icon, R.drawable.ic_menu_share);
//        notification.contentView.setTextViewText(R.id.status_text, "simulation in progress");
//        notification.contentView.setProgressBar(R.id.status_progress, 100, progress, false);
//
//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
//
//        notificationManager.notify(42, notification);


        new Thread(new Runnable() {
            public void run() {


                Uri path;
                if (Build.VERSION.SDK_INT >= 24) {
                    path = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".my.package.name.provider", downloadFile(pdf1));
                } else {
                    path = Uri.fromFile(downloadFile(pdf1));
                    Log.e("path", String.valueOf(path));
                }

//                for (int i = 1; i < 100; i++) {
//                    progress++;
//                    notification.contentView.setProgressBar(R.id.status_progress, 100, progress, false);
//                    notificationManager.notify(42, notification);
//                    try {
//                        Thread.sleep(100);
//
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//                notificationManager.notify(42, notification);


//


//


                try {
                    loading.dismiss();
//                    Intent notificationIntent = new Intent(context, HomeActivity.class);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    startActivity(intent);
                    finish();

                } catch (ActivityNotFoundException e) {
//                    loading.dismiss();
                    notificationManager.cancel(42);
                    Toast.makeText(getApplicationContext(), "PDF Reader application is not installed in your device", Toast.LENGTH_LONG).show();
                }
            }
        }).start();
    }

    File downloadFile(String dwnload_file_path) {
        File file = null;
        try {

            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            // connect
            urlConnection.connect();

            // set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile();
//

            // create a new file, to save the downloaded file

            file = new File(SDCardRoot, title + ".pdf");

            FileOutputStream fileOutput = new FileOutputStream(file);

            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            // this is the total size of the file which we are
            // downloading
            totalsize = urlConnection.getContentLength();

//
//            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;
//
            while ((bufferLength = inputStream.read(buffer)) > 0) {


                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;


                per = ((float) downloadedSize / totalsize) * 100;

                progress = (int) per;

//                notification.contentView.setProgressBar(R.id.status_progress, 100, progress, false);
//                notificationManager.notify(42, notification);

            }
            // close the output stream when complete //
            fileOutput.close();


        } catch (final MalformedURLException e) {
            Toast.makeText(getApplicationContext(), "Some error occured. Press back and try again.", Toast.LENGTH_LONG).show();
        } catch (final IOException e) {
            Toast.makeText(getApplicationContext(), "Some error occured. Press back and try again.", Toast.LENGTH_LONG).show();
        } catch (final Exception e) {
            Toast.makeText(getApplicationContext(), "Failed to download image. Please check your internet connection.", Toast.LENGTH_LONG).show();
        }
        return file;
    }

    private void findView() {
        periodicalsRecyclerview = (RecyclerView) findViewById(R.id.periodicals_recyclerview);
        // imgBack = (ImageView) findViewById(R.id.periodicals_image_back);
        imgFab = (ImageView) findViewById(R.id.periodicals_fab);
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
