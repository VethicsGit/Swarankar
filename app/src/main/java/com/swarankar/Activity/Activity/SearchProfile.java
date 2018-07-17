package com.swarankar.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swarankar.Activity.Model.ModelProfile.ModelProfile;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchProfile extends AppCompatActivity {
    Button mbtnEditProfile, mbtnChanegPass;
    String strUserid;
    ImageView img_user;
    Button btn_sign_out;
    String id;
    ImageView imgback;
    TextView txtName, txtEmail, txtName1, txtEmail1, txtSubCaste, txtGender, txtMartialStatus, txtDOB, txtOccupation, txtCountry, txtState, txtDistrict;
    LinearLayout lvName, lvEmail, lvSubCast, lvGender, lvMaritailStatus, lvDob, lvOccupation, lvCountry, lvState, lvDistrict,
            lvName1, lvEmail1, lvSubCast1, lvGender1, lvMaritailStatus1, lvDob1, lvOccupation1, lvCountry1, lvState1, lvDistrict1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Details");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AndroidUtils.hideSoftKeyboard(SearchProfile.this);
                finish();
            }
        });

        Bundle p = getIntent().getExtras();
        id = p.getString("id");
        strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        getProfileData();
        lvName = (LinearLayout) findViewById(R.id.lv_name);
        lvEmail = (LinearLayout) findViewById(R.id.lv_email);
        lvSubCast = (LinearLayout) findViewById(R.id.lv_subcast);
        lvGender = (LinearLayout) findViewById(R.id.lv_gender);
        lvMaritailStatus = (LinearLayout) findViewById(R.id.lv_marital_status);
        lvDob = (LinearLayout) findViewById(R.id.lv_dob);
        lvOccupation = (LinearLayout) findViewById(R.id.lv_occupation);
        lvCountry = (LinearLayout) findViewById(R.id.lv_country);
        lvState = (LinearLayout) findViewById(R.id.lv_state);
        lvDistrict = (LinearLayout) findViewById(R.id.lv_district);

        lvName1 = (LinearLayout) findViewById(R.id.lv_name1);
        lvEmail1 = (LinearLayout) findViewById(R.id.lv_email1);
        lvSubCast1 = (LinearLayout) findViewById(R.id.lv_subcast1);
        lvGender1 = (LinearLayout) findViewById(R.id.lv_gender1);
        lvMaritailStatus1 = (LinearLayout) findViewById(R.id.lv_marital_status1);
        lvDob1 = (LinearLayout) findViewById(R.id.lv_dob1);
        lvOccupation1 = (LinearLayout) findViewById(R.id.lv_occupation1);
        lvCountry1 = (LinearLayout) findViewById(R.id.lv_country1);
        lvState1 = (LinearLayout) findViewById(R.id.lv_state1);

        imgback = (ImageView) findViewById(R.id.img_back);
        txtName = (TextView) findViewById(R.id.profile_txt_name);
        txtEmail = (TextView) findViewById(R.id.profile_txt_email);
        txtName1 = (TextView) findViewById(R.id.profile_txt_name1);
        txtEmail1 = (TextView) findViewById(R.id.profile_txt_email1);
        txtSubCaste = (TextView) findViewById(R.id.profile_txt_sub_caste);
        txtGender = (TextView) findViewById(R.id.profile_txt_gender);
        txtMartialStatus = (TextView) findViewById(R.id.profile_txt_maratial);
        txtDOB = (TextView) findViewById(R.id.profile_txt_dob);
        txtOccupation = (TextView) findViewById(R.id.profile_txt_occupation);
        txtCountry = (TextView) findViewById(R.id.profile_txt_country);
        txtState = (TextView) findViewById(R.id.profile_txt_state);
        txtDistrict = (TextView) findViewById(R.id.profile_txt_distict);
        img_user = (ImageView) findViewById(R.id.img_user);
        btn_sign_out = (Button) findViewById(R.id.btn_sign_out);

        mbtnEditProfile = (Button) findViewById(R.id.profile_bn_edit);
        mbtnChanegPass = (Button) findViewById(R.id.profile_btn_change_pass);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mbtnChanegPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchProfile.this, ChangePassword.class);
                startActivity(i);
//                getActivity().finish();
            }
        });

        mbtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().finish();
                Intent i = new Intent(SearchProfile.this, EditProfile.class);
                startActivity(i);
            }
        });

//        btn_sign_out.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                SharedPreferences.Editor editor = Constants.loginSharedPreferences.edit();
//                editor.clear();
//                editor.apply();
//                startActivity(new Intent(SearchProfile.this, LoginActivity.class));
//                finishAffinity();
//
//            }
//        });

        HomeActivity.mTextToolBar.setText("Profile");

    }

    private void getProfileData() {

        final ProgressDialog loading = new ProgressDialog(SearchProfile.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<ModelProfile> call1 = apiService.userInfo(id);
        call1.enqueue(new Callback<ModelProfile>() {
            @Override
            public void onResponse(Call<ModelProfile> call, retrofit2.Response<ModelProfile> response) {
                loading.dismiss();

                // Log.e("email", response.body().getGender());

                if (!response.body().getEmail().isEmpty()) {
                    txtEmail1.setText(AndroidUtils.wordFirstCap(response.body().getEmail()));
                    txtEmail.setText(AndroidUtils.wordFirstCap(response.body().getEmail()));
                }
//                txtSubCaste.setText(response.body().getSubcaste());
//                txtGender.setText(response.body().getGender());
//                txtDOB.setText(response.body().getDob());
//                txtOccupation.setText(response.body().getProfession());

//                    Glide.with(SearchProfile.this).load( response.body().getPicture())/*.error(R.drawable.placeholder)*/.into(img_user);

                String maritalstatus = response.body().getMaritalStatus();
                if (maritalstatus != null) {
                    if (!maritalstatus.isEmpty()) {
                        txtMartialStatus.setText(response.body().getMaritalStatus());

                    }

                }
                if (!response.body().getFirstname().isEmpty() && !response.body().getLastname().isEmpty()) {

                    txtName.setText(AndroidUtils.wordFirstCap(response.body().getFirstname()) + " " + AndroidUtils.wordFirstCap(response.body().getLastname()));
                    txtName1.setText(AndroidUtils.wordFirstCap(response.body().getFirstname()) + " " + AndroidUtils.wordFirstCap(response.body().getLastname()));
                    lvName.setVisibility(View.VISIBLE);
                    lvName1.setVisibility(View.VISIBLE);
                }

                if (!response.body().getSubcaste().isEmpty()) {
                    txtSubCaste.setText(AndroidUtils.wordFirstCap(response.body().getSubcaste()));
                    lvSubCast.setVisibility(View.VISIBLE);
                    lvSubCast1.setVisibility(View.VISIBLE);
                }

                if (!response.body().getGender().isEmpty()) {
                    txtGender.setText(AndroidUtils.wordFirstCap(response.body().getGender()));
                    lvGender.setVisibility(View.VISIBLE);
                    lvGender1.setVisibility(View.VISIBLE);
                }

                if (!response.body().getDob().isEmpty()) {

                    txtDOB.setText(response.body().getDob());
                    lvDob.setVisibility(View.VISIBLE);
                    lvDob1.setVisibility(View.VISIBLE);

                }

                if (!response.body().getProfession().isEmpty()) {

                    txtOccupation.setText(AndroidUtils.wordFirstCap(response.body().getProfession()));
                    lvOccupation.setVisibility(View.VISIBLE);
                    lvOccupation1.setVisibility(View.VISIBLE);

                }
                if (!response.body().getCountry().isEmpty()) {

                    txtCountry.setText(AndroidUtils.wordFirstCap(response.body().getCountry()));
                    lvCountry.setVisibility(View.VISIBLE);
                    lvCountry1.setVisibility(View.VISIBLE);

                }
                if (!response.body().getState().isEmpty()) {

                    txtState.setText(AndroidUtils.wordFirstCap(response.body().getState()));
                    lvState.setVisibility(View.VISIBLE);
                    lvState1.setVisibility(View.VISIBLE);
                }

                if (!response.body().getCity().isEmpty()) {

                    txtDistrict.setText(AndroidUtils.wordFirstCap(response.body().getCity()));
                    lvDistrict.setVisibility(View.VISIBLE);
                }

                Picasso.with(SearchProfile.this).load("" + response.body().getPicture())/*.error(R.drawable.placeholder)*/.into(img_user);

//                try {
//
//                    Log.e("info",response.body().string()+ "");
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//                }

            }

            @Override
            public void onFailure(Call<ModelProfile> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
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
