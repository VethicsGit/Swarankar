package com.swarankar.Activity.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.swarankar.Activity.Model.ModelGotra.ModelGotra;
import com.swarankar.Activity.Model.ModelProfession.Profession;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView logo;


    Button mBtnSignin, mBtnSignUp;
    public static List<String> CastList = new ArrayList<>();
    public static List<String> CastListid = new ArrayList<>();
    public static List<Profession> professionlist = new ArrayList<>();
    public static List<String> gotraSelfList = new ArrayList<>();


    Handler handler;
    Constants constants;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (Constants.loginSharedPreferences.getBoolean(Constants.LoginStatus, false)) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra("flag", ""));
                finish();
            } else {
                mBtnSignin.setEnabled(true);
                mBtnSignUp.setEnabled(true);
                mBtnSignin.setVisibility(View.VISIBLE);
                mBtnSignUp.setVisibility(View.VISIBLE);
//                finishAffinity();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);


        logo= (ImageView) findViewById(R.id.logo);
        Animation zoomin = AnimationUtils.loadAnimation(MainActivity.this, R.anim.zoom_in);
        logo.startAnimation(zoomin);
        logo.setVisibility(View.VISIBLE);


        constants = new Constants();
        Constants.loginSharedPreferences = getSharedPreferences(Constants.LoginPREFERENCES, MODE_PRIVATE);
         mBtnSignin = (Button) findViewById(R.id.login_btn_signin);
      mBtnSignUp = (Button) findViewById(R.id.login_btn_signup);

   mBtnSignin.setEnabled(false);
       mBtnSignUp.setEnabled(false);

//        splash.setVisibility(View.VISIBLE);
      /*  Animation mAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 1.0f);
*/
       /* Animation mAnimation = new TranslateAnimation(0,100f,0,0f);
        mAnimation.setDuration(1000);
        mAnimation.setRepeatCount(1);
        mAnimation.setRepeatMode(Animation.REVERSE);
        mAnimation.setInterpolator(new LinearInterpolator());
        findViewById(R.id.logo).setAnimation(mAnimation);
*/


        mBtnSignin.setVisibility(View.GONE);
        mBtnSignUp.setVisibility(View.GONE);

        mBtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        getSubcaste();
        getProfession();
        handler = new Handler();
        handler.postDelayed(runnable, 3000L);
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }

    private void getProfession() {

//        final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<Profession>> call1 = apiService.get_profession();
        call1.enqueue(new Callback<List<Profession>>() {


            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<List<Profession>> call, retrofit2.Response<List<Profession>> response) {
//                loading.dismiss();

                try {
                    if (response.isSuccessful()) {
                        professionlist.clear();
                        if (response.body().size() > 0) {
                            for (int i = 0; i < response.body().size(); i++) {
                                professionlist.add(response.body().get(i));
                            }
                        }
                    }


                } catch (Exception e) {
                }


            }

            @Override
            public void onFailure(Call<List<Profession>> call, Throwable t) {
//                loading.dismiss();
                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getSubcaste() {


//        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demo.vethics.in/swarnkar/mobile/Register/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API apiServices = retrofit.create(API.class);

        Call<ResponseBody> call = apiServices.get_subcaste();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                progressDialog.dismiss();

                try {
                    JSONArray js = new JSONArray(response.body().string());
                    Log.e("response", String.valueOf(js));
//                    Log.e("js", js.toString(0));
                    CastList.clear();
                    for (int i = 0; i < js.length(); i++) {

                        JSONObject person = (JSONObject) js.get(i);
                        String caste = person.getString("subcaste");

                        if (i == 0) {
                            CastList.add("Subcaste");
                            CastList.add(caste);
                        } else {
                            CastList.add(caste);
                        }

//                        CastList.add(caste);
//                        CastListid.add(person.getString("id"));

                    }

//                    Log.e("caste", CastList.size() + "CastListid : " + CastListid.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                progressDialog.dismiss();
                t.getStackTrace();
                Log.e("msgfail", "" + t.getMessage());
            }
        });

    }

    private void getGotraSelf(final String gotraSelf) {

        API apiservice = APIClient.getClient().create(API.class);

        Call<List<ModelGotra>> call1 = apiservice.get_gotra_list("gotra");

        call1.enqueue(new Callback<List<ModelGotra>>() {
            @Override
            public void onResponse(Call<List<ModelGotra>> call, Response<List<ModelGotra>> response) {
                gotraSelfList.add("Select");
                for (int i = 0; i < response.body().size(); i++) {
                    gotraSelfList.add(response.body().get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<List<ModelGotra>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
