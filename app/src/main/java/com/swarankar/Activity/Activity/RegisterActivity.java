package com.swarankar.Activity.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.swarankar.Activity.Model.ModelLogin.ModelLogin;
import com.swarankar.Activity.Model.RegisterResponse.RegisterResponse;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginButton loginButton;
    TextView txtLoginFb, txtTwitter;
    private CallbackManager callbackManager;
    String name = "", email = "";
    public static String fbId;
    Twitter twitter;
    RequestToken requestToken = null;
    //    twitter4j.auth.AccessToken accessToken;
    String oauth_url, oauth_verifier, profile_url;
    Dialog auth_dialog;
    WebView web;

    AccessToken accessToken;

    String Date = null;
    List<String> CastList = new ArrayList<>();

    private ImageView imageView;
    private EditText edtFname;
    private EditText edtLname;
    private Spinner spinnerSubcaste;
    private Spinner spinnerProfession;
    private EditText edtPassword;
    private Spinner spinnerGender;
    private TextView edtDob;
    private ImageView imageView2;
    private EditText edtQualification;
    private EditText edtEmail;
    private EditText edtMobile;
    private Button btnSignup;
    private TextView textForgotpass;
    private TextView textAlreadyMember;
    private TextView registerFbDuplicate;
    private LoginButton connectWithFbButton;
    private EditText edConfirmPassword;
    private TextView registerTxtTwitter;
    Calendar myCalendar;
    int dobyear, dobmonth, dobday;
    String subcaste, profession, gender;
    String dob;
    CheckBox cbTermsCondition;
    TextView tvTermsCondition;

    private void findViews() {
        imageView = (ImageView) findViewById(R.id.imageView);
        edtFname = (EditText) findViewById(R.id.edt_fname);
        AndroidUtils.showSoftKeyboard(RegisterActivity.this, edtFname);
        edtLname = (EditText) findViewById(R.id.edt_lname);
        spinnerSubcaste = (Spinner) findViewById(R.id.spinner_subcaste);
        spinnerProfession = (Spinner) findViewById(R.id.spinner_profession);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edConfirmPassword = (EditText) findViewById(R.id.register_edt_confirm_password);
        spinnerGender = (Spinner) findViewById(R.id.spinner_gender);
        edtDob = (TextView) findViewById(R.id.edt_dob);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        edtQualification = (EditText) findViewById(R.id.edt_qualification);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtMobile = (EditText) findViewById(R.id.edt_mobile);

        cbTermsCondition = (CheckBox) findViewById(R.id.cb_terms_condition);
        tvTermsCondition = (TextView) findViewById(R.id.tv_terms_condition);
        /*tvTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, TermsnConditionActivity.class);
                startActivity(i);
            }
        });*/
        tvTermsCondition.setMovementMethod(LinkMovementMethod.getInstance());

        btnSignup = (Button) findViewById(R.id.btn_signup);
        textForgotpass = (TextView) findViewById(R.id.text_forgotpass);
        textAlreadyMember = (TextView) findViewById(R.id.text_already_member);

        textAlreadyMember.setPaintFlags(textAlreadyMember.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        registerFbDuplicate = (TextView) findViewById(R.id.register_fb_duplicate);
        connectWithFbButton = (LoginButton) findViewById(R.id.connectWithFbButton);
        registerTxtTwitter = (TextView) findViewById(R.id.register_txt_twitter);

        btnSignup.setOnClickListener(this);
        connectWithFbButton.setOnClickListener(this);

        textForgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        textAlreadyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        ArrayAdapter<String> adt = new ArrayAdapter(getApplicationContext(), R.layout.dynamic_spinner_itemupload2, MainActivity.CastList);
//        adt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerSubcaste.setAdapter(adt);

        /*Sub Caste*/
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, MainActivity.CastList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubcaste.setAdapter(aa);

        spinnerSubcaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subcaste = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*Profession*/
        List<String> prStrings = new ArrayList<>();
        if (MainActivity.professionlist.size() > 0) {
            for (int i = 0; i < MainActivity.professionlist.size(); i++) {

                if (i == 0) {
                    prStrings.add("Profession");
                    prStrings.add(MainActivity.professionlist.get(i).getProfession());
                } else {
                    prStrings.add(MainActivity.professionlist.get(i).getProfession());
                }
            }
        }

        ArrayAdapter aa11 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, prStrings);
        aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfession.setAdapter(aa11);

        spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView errorText = (TextView) spinnerProfession.getSelectedView();

                profession = adapterView.getItemAtPosition(i).toString();
                if (profession.equalsIgnoreCase("others")) {
                    if (errorText != null) {
                        errorText.setTextColor(Color.parseColor("#17a6c8"));
                        errorText.setTypeface(null, Typeface.BOLD);
                    }
                } else {
                    if (errorText != null) {
                        errorText.setTextColor(Color.parseColor("#808080"));
                        errorText.setTypeface(null, Typeface.NORMAL);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*Gender*/
        List<String> genderlidt = new ArrayList<>();
        genderlidt.add("Gender");
        genderlidt.add("Male");
        genderlidt.add("Female");
        genderlidt.add("Other");

        ArrayAdapter aa112 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderlidt);
        aa112.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(aa112);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                gender = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edtDob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(RegisterActivity.this);
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtDob.setText(sdf.format(myCalendar.getTime()));

        dobyear = myCalendar.get(Calendar.YEAR);
        dobmonth = myCalendar.get(Calendar.MONTH) + 1;
        dobday = myCalendar.get(Calendar.DAY_OF_MONTH);

        dob = dobday + "/" + dobmonth + "/" + dobyear;

        Log.e("dobdata", dob + "");

    }

    @Override
    public void onClick(View v) {
        if (v == btnSignup) {

            if (!isEmpty()) {
                AndroidUtils.hideSoftKeyboard(this);
                ApiCallRegister();

               /* if (edtPassword.getText().toString().equals(edConfirmPassword.getText().toString())) {
                    ApiCallRegister();
                } else {
                    Toast.makeText(getApplicationContext(), "Password and confirm password doesnot match", Toast.LENGTH_SHORT).show();
                }*/
            }

        } else if (v == connectWithFbButton) {

        }
    }

    private boolean isEmpty() {

        String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (edtFname.getText().toString().isEmpty()) {
            edtFname.setError("Enter FirstName");
            return true;
        } else if (edtLname.getText().toString().isEmpty()) {
            edtLname.setError("Enter LastName");
            return true;
        } else if (subcaste.trim().equals("Subcaste")) {
            TextView errorText = (TextView) spinnerSubcaste.getSelectedView();
            errorText.setError("");
            errorText.setText("Select Subcaste");
            return true;
        } else if (profession.trim().equals("Profession")) {
            TextView errorText = (TextView) spinnerProfession.getSelectedView();
            errorText.setError("");
            errorText.setText("Select Profession");
            return true;
        } else if (edtPassword.getText().toString().isEmpty()) {
            edtPassword.setError("Enter Password");
            return true;
        } else if (edtPassword.getText().toString().length() < 8) {
            edtPassword.setError("Password must contains 8 characters");
            return true;
        } else if (edConfirmPassword.getText().toString().isEmpty()) {
            edConfirmPassword.setError("Enter Confirm Password");
            return true;
        } else if (edConfirmPassword.getText().toString().length() < 8) {
            edConfirmPassword.setError("Confirm Password must contain 8 characters");
            return true;
        } else if (!edtPassword.getText().toString().trim().equals(edConfirmPassword.getText().toString().trim())) {
            edConfirmPassword.setError("Passwords do not match!");

            /*edtPassword.setText("");
            edConfirmPassword.setText("");*/
            return true;
        } else if (gender.trim().equals("Gender")) {
            TextView errorText = (TextView) spinnerGender.getSelectedView();
            errorText.setError("");
            errorText.setText("Select Gender");
            return true;
        } else if (edtDob.getText().toString().isEmpty()) {
            edtDob.setError("Select Date of Birth");
            return true;
        } else if (edtQualification.getText().toString().isEmpty()) {
            edtQualification.setError("Enter Education Qualification");
            return true;
        } else if (!edtEmail.getText().toString().trim().isEmpty() && !edtEmail.getText().toString().trim().matches(email_validate)) {
            edtEmail.setError("Enter Valid Email Address!");
            return true;
        } else if (edtMobile.getText().toString().isEmpty()) {
            edtMobile.setError("Enter Mobile Number");
            return true;
        } else if (edtMobile.length() < 10) {
            edtMobile.setError("Enter Valid 10 Digit Mobile Number");
            return true;
        } else if (!cbTermsCondition.isChecked()) {
            Toast.makeText(this, "Please accept the terms and conditions!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;

    }

    private void ApiCallRegister() {

        final ProgressDialog loading = new ProgressDialog(RegisterActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(RegisterActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<RegisterResponse> call1 = apiService.register(edtFname.getText().toString().trim(),
                edtLname.getText().toString().trim(),
                gender,
                subcaste,
                edtQualification.getText().toString().trim(),
                profession,
                dob,
                edtEmail.getText().toString().trim(),
                edtMobile.getText().toString().trim(),
                "",
                edtPassword.getText().toString().trim());
        call1.enqueue(new Callback<RegisterResponse>() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response) {
                loading.dismiss();

                try {
                    Log.e("RegisterData", "" + response.body().toString());

                    if (response.isSuccessful()) {
                        String status = response.body().getResponse();
                        String Message = response.body().getMessage();
                        Log.e("status", status);

                        if (status.equals("Success")) {
                            AndroidUtils.hideSoftKeyboard(RegisterActivity.this);
                            Toast.makeText(getApplicationContext(), "" + Message, Toast.LENGTH_LONG).show();
                            DialogProfileNotCompleted();
                            /*Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();*/
                        } else {
                            Toast.makeText(getApplicationContext(), Message + "", Toast.LENGTH_LONG).show();

                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                loading.dismiss();

                Log.e("RegistreData", t.getMessage() + "");
            }
        });
    }

    private void DialogProfileNotCompleted() {

        final Dialog alertDialog = new Dialog(this);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(this, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(this).inflate(R.layout.dialog_otp, null);
        Button button_ok;
        ImageView btnClose;
        final EditText edt_otp;
        TextView resendOTP;

        button_ok = (Button) rootView.findViewById(R.id.button_ok);
        resendOTP = (TextView) rootView.findViewById(R.id.tv_resend_otp);
        edt_otp = (EditText) rootView.findViewById(R.id.edt_otp);
        btnClose = (ImageView) rootView.findViewById(R.id.img_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setContentView(rootView);
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                ResendOtp();
                //Toast.makeText(RegisterActivity.this, "Otp Sent!", Toast.LENGTH_LONG).show();
            }
        });
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_otp.getText().toString().trim().isEmpty()) {
                    edt_otp.setError("Enter OTP!");
                } else {
                    AndroidUtils.hideSoftKeyboard(RegisterActivity.this);
                    alertDialog.dismiss();
                    getLogin(edt_otp.getText().toString().trim());
                }

            }
        });
        alertDialog.show();
    }

    private void ResendOtp() {
        final ProgressDialog loading = new ProgressDialog(RegisterActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(RegisterActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<RegisterResponse> call1 = apiService.sendOtp(edtMobile.getText().toString().trim());
        call1.enqueue(new Callback<RegisterResponse>() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response) {
                loading.dismiss();

                try {
                    Log.e("Login Data", "" + response.body().toString());

                    if (response.isSuccessful()) {

                        String status = response.body().getResponse();
                        String Message = response.body().getMessage();
                        Log.e("status", status);

                        if (status.equals("Success")) {
                            Toast.makeText(getApplicationContext(), "" + Message, Toast.LENGTH_LONG).show();
                            DialogProfileNotCompleted();
                        } else {
                            Toast.makeText(getApplicationContext(), Message + "", Toast.LENGTH_LONG).show();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                loading.dismiss();

                Log.e("RegistreData", t.getMessage() + "");
            }
        });
    }

    private void getLogin(String otp) {
        String strMobileNumber = edtMobile.getText().toString().trim();
        //String strPassword = edPassword.getText().toString().trim();
        Log.e("id", strMobileNumber);
        //Log.e("pass", strPassword);

        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(LoginActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<ModelLogin> call1 = apiService.confirmOtp(strMobileNumber, otp);
        call1.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, retrofit2.Response<ModelLogin> response) {
                loading.dismiss();

                String strResponse = response.body().getResponse();
                String strMessage = response.body().getMessage();
                Log.e("strMessage", strMessage + "");
                String strUserId = String.valueOf(response.body().getUserId());

//                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
//                editor.putString("useid", strUserId);
//                editor.commit();

                Log.e("userid", strUserId);
                if (strResponse.equals("Success")) {
                    AndroidUtils.hideSoftKeyboard(RegisterActivity.this);
                    SharedPreferences.Editor editor = Constants.loginSharedPreferences.edit();
                    editor.putString(Constants.uid, strUserId);
                    editor.putString(Constants.profile_completion, response.body().getProfile_completion());
                    editor.putBoolean(Constants.LoginStatus, true);
                    editor.apply();

//                    Toast.makeText(getApplicationContext(), "" + strMessage, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                    i.putExtra("flag", "");
                    startActivity(i);
                    finishAffinity();

                } else {
                    Constants.buildDialogmno(strMessage + "", RegisterActivity.this);
                }

            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                loading.dismiss();

                Constants.buildDialogmno("", RegisterActivity.this);
                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register_new);

        findViews();
    }

    @Override
    protected void onResume() {
        super.onResume();

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.connectWithFbButton);
        loginButton.setReadPermissions("public_profile", "email", "user_friends");
        txtLoginFb = (TextView) findViewById(R.id.register_fb_duplicate);
        txtLoginFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                facebookLoginCall();

            }
        });
    }

    private void facebookLoginCall() {

        LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this,
                Arrays.asList("public_profile", "email", "user_birthday", "user_location"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        accessToken = AccessToken.getCurrentAccessToken();
                        onSuccessFullLogIn(accessToken);

                    }

                    @Override
                    public void onCancel() {
                        Log.d("msg", "Cancel Login");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("msg", "Facebook Error : " + error.toString());

                    }
                });
    }

    public void onSuccessFullLogIn(final AccessToken accessToken) {

        Bundle params = new Bundle();
        params.putString("fields", "id,name,first_name,last_name,age_range,link,locale,timezone,updated_time,verified,location,email,birthday,gender,picture.type(large)");
        new GraphRequest(accessToken, "me", params,
                HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                if (response != null) {
                    try {
                        JSONObject data = response.getJSONObject();

                        Log.e("msg", "Fb data : " + data.toString());
//                        SharedPreferences.Editor editor = constants.loginSharedPreferences.edit();
                        String id = "";
                        String name = "";
                        String password = "";
                        String email = "";
                        String contact = "";
                        String age = "";
                        String picture = "";
                        String gender = "";
//
                        if (data.has("id")) {
                            id = data.getString("id");
                        }

                        if (data.has("gender")) {
                            gender = data.getString("gender");
                        }
                        if (data.has("picture")) {
                            picture = data.getJSONObject("picture")
                                    .getJSONObject("data")
                                    .getString("url");

                            if (picture.startsWith("http://")
                                    || picture
                                    .startsWith("https://")) {
                                picture = picture.replace(
                                        "http://", "").trim();
                                picture = picture.replace(
                                        "https://", "").trim();
                            }

                        }
                        if (data.has("name")) {
                            name = data.getString("name");

                        }
                        if (data.has("email")) {
                            email = data.getString("email");

                        }
                        if (data.has("location")) {

//                            editor.putString("city", data
//                                    .getJSONObject("location")
//                                    .getString("name"));

                        }
                        if (data.has("age_range")) {

                            age = data.getJSONObject("age_range").getString("min");

                        }

                        apiCallfblogin(id, name, picture, email);

                        LoginManager.getInstance().logOut();

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                    }
                }
            }
        }).executeAsync();

    }

    private void apiCallfblogin(String id, String name, String picture, String email) {

        final ProgressDialog loading = new ProgressDialog(RegisterActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(RegisterActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = apiService.fblogin(id,
                name,
                email,
                picture
        );
        call1.enqueue(new Callback<ResponseBody>() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                loading.dismiss();

                try {
                    Log.e("fblogincall", "" + response.body().string());

                  /*  if (response.isSuccessful()) {

                        String status = response.body().getResponse();
                        String Mewssage = response.body().getMessage();
                        String strUserId = String.valueOf(response.body().getUserId());
                        Log.e("sssds", strUserId);

                        if (status.equals("Success")) {

                            SharedPreferences.Editor editor = Constants.loginSharedPreferences.edit();
                            editor.putString(Constants.uid, strUserId);
                            editor.putBoolean(Constants.LoginStatus, true);
                            editor.commit();


                            Toast.makeText(getApplicationContext(), "" + Mewssage, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            i.putExtra("flag", "");
                            startActivity(i);
                            finishAffinity();

                        } else {
                            Toast.makeText(getApplicationContext(), Mewssage + "", Toast.LENGTH_LONG).show();

                        }

                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();

                Log.e("RegistreData", t.getMessage() + "");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
