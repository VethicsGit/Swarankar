package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.swarankar.Activity.Model.News.NewsPost;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.R;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgotPassword extends AppCompatActivity {

    EditText edEmail;
    Button btnSend;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edEmail = (EditText) findViewById(R.id.forgot_pass_email);
        btnSend = (Button) findViewById(R.id.forgot_pass_btn_send_link);
        imgBack = (ImageView) findViewById(R.id.forgot_pass_image_back);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtils.hideSoftKeyboard(ForgotPassword.this);

                if (!isEmpty()) {
                    sendData();
                }

            }
        });
    }


    private boolean isEmpty() {

        String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (edEmail.getText().toString().trim().isEmpty()) {
            edEmail.setError("Enter Email");
            return true;
        } else if (!edEmail.getText().toString().trim().matches(email_validate)) {
            edEmail.setError("Enter Valid Email");
            return true;
        }

        return false;

    }

    private void sendData() {

        final ProgressDialog loading = new ProgressDialog(ForgotPassword.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);


//
        Call<NewsPost> call1 = apiService.f_pass(edEmail.getText().toString());
        call1.enqueue(new Callback<NewsPost>() {
            @Override
            public void onResponse(Call<NewsPost> call, retrofit2.Response<NewsPost> response) {
                loading.dismiss();

//                        try {
//                            Log.e("response",response.body().string()+"");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                String strRespose = response.body().getResponse();
                String mes = response.body().getMessage();
                if (strRespose.equals("Success")) {
                    dialog(mes + "", ForgotPassword.this);
                } else {
                    dialog(mes + "", ForgotPassword.this);
                }

            }


            @Override
            public void onFailure(Call<NewsPost> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });
    }


    private void dialog(String s, ForgotPassword family) {
        final Dialog alertDialog = new Dialog(family);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(family, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(family).inflate(R.layout.view_alert_dialog, null);
        Button button_ok;
        ImageView btnClose;
        TextView textview;

        button_ok = (Button) rootView.findViewById(R.id.button_ok);
        textview = (TextView) rootView.findViewById(R.id.textview);
        btnClose = (ImageView) rootView.findViewById(R.id.img_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if (!s.equals("")) {
            textview.setText(s);
        }

        alertDialog.setContentView(rootView);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();

            }
        });
        alertDialog.show();

    }
}
