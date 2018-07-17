package com.swarankar.Activity;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.swarankar.Activity.Activity.PeriodicalRegister;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient1;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Champ on 25-09-2017.
 */

public class MyService extends Service {

    public static int counter = 0;


    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "First Service was Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {

//        final ProgressDialog loading = new ProgressDialog(MyService.this);
//        loading.setMessage("Please Wait..");
//        loading.setCancelable(false);
//        loading.setCanceledOnTouchOutside(false);
//        loading.show();

//        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(7, TimeUnit.MINUTES)
//                .connectTimeout(7, TimeUnit.MINUTES)
//                .build();
//
////        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL1).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
//        API apiservice = retrofit.create(API.class);
        API apiservice = APIClient1.getClient().create(API.class);
        Call<ResponseBody> call1 = apiservice.api_addprogram("1",
                "1",
               "1",
                "1",
                "",
                PeriodicalRegister.imageFront,
                PeriodicalRegister.imageBack);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                addNews: {"response":"Success","message":"Your data inserted Successfully.."}
//                loading.dismiss();
//                String status = response.body().getResponse();
//                if(status.equals("Success")){
//                    String msg = response.body().getMessage();
//                    Log.e("msg",msg);
////                    Toast.makeText(getApplicationContext(),"complete",Toast.LENGTH_SHORT).show();
////                    dialog(msg + "", NewsPostActivity.this);
//                }else {
//
//                    String msg = response.body().getMessage();
//                    Log.e("msg",msg);
//                    dialog(msg + "", NewsPostActivity.this);
//                }
                try {
                    Log.e("addNews",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stopSelf();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                stopSelf();
//                loading.dismiss();
                Log.e("error", String.valueOf(t));
            }
        });


    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
    }


}