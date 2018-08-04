package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    TextView txtLoginFb, txtForgotPassword;
    private CallbackManager callbackManager;
    String name = "", email = "";
    TextView mtxtSignup;
    public static String fbId;
    Button btnLogin;
    EditText edMobileNumber, edPassword;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        findView();

        mtxtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
                Log.e("login", edMobileNumber.getTag().toString().trim() + edMobileNumber.getText().toString().trim());
                if (!Empty()) {
                    AndroidUtils.hideSoftKeyboard(LoginActivity.this);
                    //ApiCallRegister();
                    getLogin();
                } else {

                    Toast.makeText(LoginActivity.this, "Fill all the details!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(i);
            }
        });

    }

    private void ApiCallRegister() {

        final ProgressDialog loading = new ProgressDialog(LoginActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(RegisterActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<RegisterResponse> call1 = apiService.sendOtp(edMobileNumber.getText().toString().trim());
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

    private void getLogin() {
        String strMobileNumber = edMobileNumber.getTag().toString() + edMobileNumber.getText().toString().trim();
        String strPassword = edPassword.getText().toString().trim();
        Log.e("id", strMobileNumber);
        //Log.e("pass", strPassword);


        final ProgressDialog loading = new ProgressDialog(LoginActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(LoginActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<ModelLogin> call1 = apiService.login(strMobileNumber, strPassword);
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
                    AndroidUtils.hideSoftKeyboard(LoginActivity.this);
                    SharedPreferences.Editor editor = Constants.loginSharedPreferences.edit();
                    editor.putString(Constants.uid, strUserId);
                    editor.putString(Constants.profile_completion, response.body().getProfile_completion());
                    editor.putBoolean(Constants.LoginStatus, true);
                    editor.apply();

//                    Toast.makeText(getApplicationContext(), "" + strMessage, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    i.putExtra("flag", "");
                    startActivity(i);
                    finishAffinity();

                } else {
                    Constants.buildDialogmno(strMessage + "", LoginActivity.this);
                }

            }


            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t) {
                loading.dismiss();

                Constants.buildDialogmno("", LoginActivity.this);
                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

    private boolean Empty() {

        String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (edMobileNumber.getText().toString().isEmpty()) {
            edMobileNumber.setError("Enter Mobile Number");
            return true;
        } /*else if (edMobileNumber.length() < 10) {
            edMobileNumber.setError("Enter Valid 10 Digit Mobile Number");
            return true;
        } */ else if (edPassword.getText().toString().isEmpty()) {
            edPassword.setError("Enter Password");
            return true;
        }
        return false;
    }

    private void findView() {

        mtxtSignup = (TextView) findViewById(R.id.login_txt_signup);
        txtForgotPassword = (TextView) findViewById(R.id.login_txt_forgot_password);
        edMobileNumber = (EditText) findViewById(R.id.login_ed_mobile_number);
        AndroidUtils.showSoftKeyboard(LoginActivity.this, edMobileNumber);
        edPassword = (EditText) findViewById(R.id.login_ed_password);
        btnLogin = (Button) findViewById(R.id.login_btn_login);

        mtxtSignup.setPaintFlags(mtxtSignup.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }

    @Override
    protected void onResume() {
        super.onResume();

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.connectWithFbButton);
        loginButton.setReadPermissions("public_profile", "email", "user_friends");
        txtLoginFb = (TextView) findViewById(R.id.login_txt_fb_login_duplicate);
        txtLoginFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                facebookLoginCall();

//                loginButton.performClick();
//
//                loginButton.setPressed(true);
//
//                loginButton.invalidate();
//
//                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//
//
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        if (AccessToken.getCurrentAccessToken() != null) {
//                            RequestData();
//                        }
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//
//                    }
//
//                });
//
//                loginButton.setPressed(false);
//
//                loginButton.invalidate();

            }
        });
    }

    private void facebookLoginCall() {

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
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
//                      SharedPreferences.Editor editor = constants.loginSharedPreferences.edit();
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
                                    || picture.startsWith("https://")) {
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
                    }
                }
            }
        }).executeAsync();

    }

    private void apiCallfblogin(String id, String name, String picture, String email) {


        final ProgressDialog loading = new ProgressDialog(LoginActivity.this);
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

                Log.e("Register Data : ", t.getMessage() + "");
            }
        });

    }

    private void RequestData() {

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();

                Log.e("response", String.valueOf(json));
                try {
                    if (json != null) {

                        fbId = json.getString("id").toString();
                        if (json.has("email")) {
                            email = json.getString("email").toString();
                        }

                        name = json.getString("name").toString();


//                        SharedPreferences.Editor editor = Constants.loginSharedPreferences.edit();
//                        editor.putString(Constants.uid, strUserId);
//                        editor.putBoolean(Constants.LoginStatus, true);
//                        editor.commit();

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        i.putExtra("flag", "");
                        startActivity(i);
                        finishAffinity();

                        LoginManager.getInstance().logOut();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
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
                ApiCallRegister();
                Toast.makeText(LoginActivity.this, "Otp Sent Successfully!", Toast.LENGTH_LONG).show();
            }
        });
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_otp.getText().toString().trim().isEmpty()) {
                    edt_otp.setError("Enter OTP!");
                } else {
                    AndroidUtils.hideSoftKeyboard(LoginActivity.this);
                    alertDialog.dismiss();
                    getLogin();
                }

            }
        });
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
