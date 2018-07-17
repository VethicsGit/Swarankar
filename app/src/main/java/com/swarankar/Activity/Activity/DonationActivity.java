package com.swarankar.Activity.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.swarankar.Activity.Model.ProfileDetails.ModelProfileDetails;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class DonationActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {
    EditText fullname_edt, amount_edt, purpose_edt;
    Button donation_btn;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Donation");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(this);
        fullname_edt = (EditText) findViewById(R.id.donation_fullname_edt);
        amount_edt = (EditText) findViewById(R.id.donation_amount_edt);
        purpose_edt = (EditText) findViewById(R.id.donation_purpose_edt);
        donation_btn = (Button) findViewById(R.id.donation_btn);

        Checkout.preload(getApplicationContext());

        donation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(DonationActivity.this);
                String fullName = fullname_edt.getText().toString().trim();
                String amount = amount_edt.getText().toString().trim();
                String purpose = purpose_edt.getText().toString().trim();

                if (fullName.isEmpty()) {
                    fullname_edt.setError("Please Enter Name!");
                } else if (amount.isEmpty()) {
                    amount_edt.setError("Please Enter Amount!");
                } else if (purpose.isEmpty()) {
                    purpose_edt.setError("Please Enter Purpose!");
                } else {
                    startPayment();
                }
            }
        });

    }

    private void getData() {
        loading = new ProgressDialog(this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        Call<ModelProfileDetails> call1 = apiService.userInfo1(strUserid);
        call1.enqueue(new Callback<ModelProfileDetails>() {
            @Override
            public void onResponse(Call<ModelProfileDetails> call, final retrofit2.Response<ModelProfileDetails> response) {
                fullname_edt.setText(response.body().getFirstname() + " " + response.body().getLastname());
            }

            @Override
            public void onFailure(Call<ModelProfileDetails> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setImage(R.drawable.logo);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Swarnkar Connect");
            options.put("description", "Donation Amount");
            //You can omit the image option to fetch the image from dashboard
            //options.put("image", "http://demo.vethics.in/swarnkar/uploads/logo/Logo-new.png");
            options.put("currency", "INR");
            options.put("amount", amount_edt.getText().toString().trim() + "00");
            options.put("theme.color", "#f34743");
            options.put("theme.statuscolor", "#f34743");

            /*JSONObject preFill = new JSONObject();
            preFill.put("email", "gohilchirag1602@gmail.com");
            preFill.put("contact", "9429027858");

            options.put("prefill", preFill);*/

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            Log.e("eroor :", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        AndroidUtils.hideSoftKeyboard(this);
        finish();
    }

    @Override
    public void onPaymentSuccess(String s) {
        DialogProfileNotCompleted(s);
        Toast.makeText(this, "Donation Successfull!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Donation Fail!", Toast.LENGTH_LONG).show();
    }

    private void DialogProfileNotCompleted(String s) {

        final Dialog alertDialog = new Dialog(DonationActivity.this);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(DonationActivity.this, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(DonationActivity.this).inflate(R.layout.dialog_profile_not_completed, null);
        Button button_ok;
        ImageView btnClose;
        TextView textview;
        button_ok = (Button) rootView.findViewById(R.id.button_ok);
        textview = (TextView) rootView.findViewById(R.id.textview);
        textview.setText("Thank you for making a donation of " + "\u20B9" + amount_edt.getText().toString().trim() + " towards our society/community.\n Your payment refference id is " + s);
        btnClose = (ImageView) rootView.findViewById(R.id.img_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setContentView(rootView);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();

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
