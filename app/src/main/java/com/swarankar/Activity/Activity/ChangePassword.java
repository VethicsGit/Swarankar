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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.swarankar.Activity.Model.ModelChangePassword.ModelChangePassword;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import retrofit2.Call;
import retrofit2.Callback;

public class ChangePassword extends AppCompatActivity {


    EditText edCurrentPass, edNewPass, edConfirmPass;
    Button btnUpdate;
    //ImageView imgBack;
    String strCurrentpass, strNewpass, strConfirmpass, strUserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        findView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(ChangePassword.this);
                finish();
            }
        });
        strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strCurrentpass = edCurrentPass.getText().toString();
                strNewpass = edNewPass.getText().toString();
                strConfirmpass = edConfirmPass.getText().toString();
                if (!isEmpty()) {
                    if (strNewpass.equals(strConfirmpass)) {
                        datachangePass();
                    } else {
                        Toast.makeText(getApplicationContext(), "your password and confirm password did not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        /*imgBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                View ff = ChangePassword.this.getCurrentFocus();
                if (ff != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                finish();
            }
        });*/
    }

    private boolean isEmpty() {


        if (edCurrentPass.getText().toString().trim().isEmpty()) {
//            edCurrentPass.setError("Enter Current Password");
            Toast.makeText(getApplicationContext(), "Enter Current Password", Toast.LENGTH_SHORT).show();
            return true;
        } else if (edNewPass.getText().toString().trim().isEmpty()) {
//            edNewPass.setError("Enter new Password");
            Toast.makeText(getApplicationContext(), "Enter new Password", Toast.LENGTH_SHORT).show();
            return true;
        } else if (edNewPass.getText().toString().trim().length() < 8) {
//            edNewPass.setError("Enter minimum eight character Password");
            Toast.makeText(getApplicationContext(), "Password must contains 8 characters", Toast.LENGTH_SHORT).show();
            return true;
        } else if (edConfirmPass.getText().toString().trim().isEmpty()) {
//            edConfirmPass.setError("Enter confirm Password");
            Toast.makeText(getApplicationContext(), "Enter confirm Password", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;

    }

    private void datachangePass() {


        Log.e("crnt", strCurrentpass);
        Log.e("cid", strUserid);


        final ProgressDialog loading = new ProgressDialog(ChangePassword.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<ModelChangePassword> call1 = apiService.changePass(strCurrentpass, strNewpass, strUserid);
        call1.enqueue(new Callback<ModelChangePassword>() {
            @Override
            public void onResponse(Call<ModelChangePassword> call, retrofit2.Response<ModelChangePassword> response) {
                loading.dismiss();


                String strResposne = response.body().getResponse();
                String strMsg = response.body().getResponse();

                if (strResposne.equals("Success")) {
                    if (getCurrentFocus() != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }
                    Toast.makeText(getApplicationContext(), "Password change successfully...", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(ChangePassword.this, HomeActivity.class);
//                    i.putExtra("flag", "profile");
//                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Your current password is wrong", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<ModelChangePassword> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

    private void findView() {
        edCurrentPass = (EditText) findViewById(R.id.change_pass_ed_current_pass);
        edNewPass = (EditText) findViewById(R.id.change_pass_ed_new_pass);
        edConfirmPass = (EditText) findViewById(R.id.change_pass_ed_confirm_pass);
        btnUpdate = (Button) findViewById(R.id.change_pass_btn_update);
        //imgBack = (ImageView) findViewById(R.id.change_pass_img_back);
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
