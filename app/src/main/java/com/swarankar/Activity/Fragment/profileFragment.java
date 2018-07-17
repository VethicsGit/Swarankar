package com.swarankar.Activity.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swarankar.Activity.Activity.ChangePassword;
import com.swarankar.Activity.Activity.EditProfile;
import com.swarankar.Activity.Activity.HomeActivity;
import com.swarankar.Activity.Activity.LoginActivity;
import com.swarankar.Activity.Model.ModelProfile.ModelProfile;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import retrofit2.Call;
import retrofit2.Callback;

public class profileFragment extends Fragment {
    Button mbtnEditProfile, mbtnChanegPass;
    String strUserid;
    ImageView img_user;
    Button btn_sign_out;
    TextView txtName, txtEmail, txtName1, txtEmail1, txtSubCaste, txtGender, txtMartialStatus, txtDOB, txtOccupation, txtCountry, txtState, txtDistrict;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment1, container, false);

        strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        getProfileData();

        txtName = (TextView) view.findViewById(R.id.profile_txt_name);
        txtEmail = (TextView) view.findViewById(R.id.profile_txt_email);
        txtName1 = (TextView) view.findViewById(R.id.profile_txt_name1);
        txtEmail1 = (TextView) view.findViewById(R.id.profile_txt_email1);
        txtSubCaste = (TextView) view.findViewById(R.id.profile_txt_sub_caste);
        txtGender = (TextView) view.findViewById(R.id.profile_txt_gender);
        txtMartialStatus = (TextView) view.findViewById(R.id.profile_txt_maratial);
        txtDOB = (TextView) view.findViewById(R.id.profile_txt_dob);
        txtOccupation = (TextView) view.findViewById(R.id.profile_txt_occupation);
        txtCountry = (TextView) view.findViewById(R.id.profile_txt_country);
        txtState = (TextView) view.findViewById(R.id.profile_txt_state);
        txtDistrict = (TextView) view.findViewById(R.id.profile_txt_distict);
        img_user = (ImageView) view.findViewById(R.id.img_user);
        btn_sign_out = (Button) view.findViewById(R.id.btn_sign_out);

/*

        mbtnEditProfile = (Button) view.findViewById(R.id.profile_bn_edit);
        mbtnChanegPass = (Button) view.findViewById(R.id.profile_btn_change_pass);
*/

       /* mbtnChanegPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChangePassword.class);
                startActivity(i);
//                getActivity().finish();
            }
        });*/


      /*  mbtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().finish();
                Intent i = new Intent(getActivity(), EditProfile.class);
                startActivity(i);
            }
        });
*/
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = Constants.loginSharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finishAffinity();

            }
        });


        HomeActivity.mTextToolBar.setText("Profile");


        return view;
    }

    private void getProfileData() {

        Log.e("resprofilr", strUserid + "");

        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<ModelProfile> call1 = apiService.userInfo(strUserid);
        call1.enqueue(new Callback<ModelProfile>() {
            @Override
            public void onResponse(Call<ModelProfile> call, retrofit2.Response<ModelProfile> response) {
                loading.dismiss();


                Log.e("resprofilrff", response.body().getFirstname() + "");

                txtName.setText(response.body().getFirstname() + " " + response.body().getLastname());
                txtName1.setText(response.body().getFirstname() + " " + response.body().getLastname());


                if (!response.body().getEmail().equals("empty")) {
                    txtEmail1.setText(response.body().getEmail());
                    txtEmail.setText(response.body().getEmail());
                }


                txtSubCaste.setText(response.body().getSubcaste());
                txtGender.setText(response.body().getGender());
                txtDOB.setText(response.body().getDob());
                txtOccupation.setText(response.body().getProfession());
                if(response.body().getPicture().isEmpty()){

                }else {
                    Glide.with(getActivity()).load("http://" + response.body().getPicture() + "").into(img_user);
                }


                String maritalstatus = response.body().getMaritalStatus();
                if (maritalstatus != null) {
                    if (!maritalstatus.equals("empty")) {
                        txtMartialStatus.setText(response.body().getMaritalStatus());
                    }

                }

                if (!response.body().getCountry().equals("empty")) {

                    txtCountry.setText(response.body().getCountry());

                }
                if (!response.body().getState().equals("empty")) {

                    txtState.setText(response.body().getState());
                }

                if (!response.body().getCity().equals("empty")) {

                    txtDistrict.setText(response.body().getCity());
                }


                try {
                    Glide.with(getActivity()).load("http://" + response.body().getPicture() + "").into(img_user);

                } catch (Exception e) {
                }


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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



}
