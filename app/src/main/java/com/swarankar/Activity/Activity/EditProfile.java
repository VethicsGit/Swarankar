package com.swarankar.Activity.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.swarankar.Activity.Fragment.HomeFragment;

import com.swarankar.Activity.Model.ModelCity.ModelCity;
import com.swarankar.Activity.Model.ModelCountry.ModelCountry;
import com.swarankar.Activity.Model.ModelProfession.Profession;
import com.swarankar.Activity.Model.ModelProfile.ModelProfile;
import com.swarankar.Activity.Model.ModelState.ModelState;
import com.swarankar.Activity.Model.RegisterResponse.RegisterResponse;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.Utils.FileUtils;
import com.swarankar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    private static final int PHOTO_PICK = 1, MY_READWRITE_REQUEST_CODE = 2, PHOTO_CAPTURE = 3, MY_CAPTURE_REQUEST_CODE = 4;
    private File selectedProfileImage = null;
    private boolean isImageSelected = false;
    private Uri imageUri;
    private String pictureUrl = "", imagedata;

    Spinner spCountry, spState, spCity;
    public List<ModelCountry> CountryList = new ArrayList<>();
    public List<ModelState> StateList;
    public List<ModelCity> CityList;
    public List<String> CastList = new ArrayList<>();
    public List<String> CastListid = new ArrayList<>();
    public List<Profession> professionlist = new ArrayList<>();
    String strUserid;
    String strCountryId, strCountryName, strStateId, strStateName, strCityName, profession, subcast, maritalstatus,
            gender, dob, profileimage ,strCityid;


    private Toolbar toolbar1;
    private ImageView editProfileBack;
    private CircleImageView imgUser;
    private Button btnChangeProfile;
    private EditText edtFname;
    private EditText edtLastname;
    private EditText edtEmail;
    private Spinner spinnerSubcaste;
    private RadioButton radiomale;
    private RadioButton radioFemale;
    private RadioButton radioOther, radiomarried, radio_unmarried;
    private Spinner spinnerMaritalstatus;
    private TextView edtDob;
    private Spinner spinner;
    private TextView textView4;
    private Button btnUpdate;
    Calendar myCalendar;
    int dobyear, dobmonth, dobday;
    ProgressDialog loading;


    @Override
    public void onClick(View v) {
        if (v == btnChangeProfile) {

            registerForContextMenu(btnChangeProfile);
            openContextMenu(btnChangeProfile);

        }
        if (v == radiomale) {

            gender = radiomale.getText().toString().trim();

        }
        if (v == radioFemale) {

            gender = radioFemale.getText().toString().trim();
        }
        if (v == radioOther) {

            gender = radioOther.getText().toString().trim();
        }
        if (v == radiomarried) {

            maritalstatus = "Married";
        }

        if (v == radio_unmarried) {
            maritalstatus = "UnMarried";

        }

        if (v == btnUpdate) {
            if (!isEmpty()) {

                ApiCallUpdateProfile();
            }
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0, 0, 0, "Pick Image");//groupId, itemId, order, title
        menu.add(0, 1, 0, "Capture Image");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                Log.d("msg", "Pick Image");
                if (ContextCompat.checkSelfPermission(EditProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {

                    Log.d("msg", "Pick Photo");

                    Intent intentPick = new Intent(Intent.ACTION_GET_CONTENT);
                    intentPick.setType("image/*");
                    startActivityForResult(Intent.createChooser(intentPick, "Select Action"), PHOTO_PICK);
                } else {
                    // Show rationale and request permission.
                    ActivityCompat.requestPermissions(EditProfile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_READWRITE_REQUEST_CODE);
                }

                break;
            case 1:
                Log.d("msg", "Capture Image");
                if (ContextCompat.checkSelfPermission(EditProfile.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {

                    Log.d("msg", "Capture Photo");

                    Intent intentCapture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File photo = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                    intentCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photo));
                    imageUri = Uri.fromFile(photo);
                    selectedProfileImage = photo;
                    startActivityForResult(intentCapture, PHOTO_CAPTURE);
                } else {
                    // Show rationale and request permission.
                    ActivityCompat.requestPermissions(EditProfile.this, new String[]{Manifest.permission.CAMERA}, MY_CAPTURE_REQUEST_CODE);
                }

                break;
            default:
                break;
        }


        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("msg", "Activity Rersult ");

        if (requestCode == PHOTO_PICK) {

            Bitmap bm = null;
            if (data != null) {
                try {

                    pictureUrl = data.getData().getPath();
                    bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    selectedProfileImage = new File(FileUtils.getPath(EditProfile.this, data.getData()));
                    imgUser.setImageBitmap(bm);
                    isImageSelected = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bm != null) {
                imgUser.setImageBitmap(bm);
                profileimage = AndroidUtils.BitMapToString(bm);

            }

            Log.d("msg", "Pick");

        } else if (requestCode == PHOTO_CAPTURE) {

            Log.d("msg", "CApture");

            Uri selectedImage = imageUri;

            Log.d("msg", "Image Uri : " + imageUri.toString());

            pictureUrl = imageUri.toString();

            getContentResolver().notifyChange(selectedImage, null);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;
            try {
                bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);

                if (bitmap != null) {
                    imgUser.setImageBitmap(bitmap);
                    isImageSelected = true;
                    profileimage = AndroidUtils.BitMapToString(bitmap);

                }

            } catch (Exception e) {
                Toast.makeText(EditProfile.this, "Failed to load", Toast.LENGTH_SHORT)
                        .show();
                Log.e("msg", "Camera " + e.toString());
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_CAPTURE_REQUEST_CODE) {
            if (/*permissions.length >= 1 &&
                    permissions[0] == Manifest.permission.CAMERA &&*/
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentCapture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                File photo = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                intentCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(intentCapture, PHOTO_CAPTURE);
            } else {
                // Permission was denied. Display an error message.
                Toast.makeText(EditProfile.this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == MY_READWRITE_REQUEST_CODE) {
            if (/*permissions.length >= 1 &&
                    permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE &&*/
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentPick = new Intent(Intent.ACTION_GET_CONTENT);
                intentPick.setType("image/*");
                startActivityForResult(Intent.createChooser(intentPick, "Select Action"), PHOTO_PICK);
            } else {
                // Permission was denied. Display an error message.
                Toast.makeText(EditProfile.this, "Read/Write permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void ApiCallUpdateProfile() {

        Log.e("strCountryName", "strCountryName : " + strCountryId);
        Log.e("strStateName", "strStateName : " + strStateId);
        Log.e("strCityName", "strCountryName : " + strCityid);
        Log.e("dob", "dob : " + dob);

        final ProgressDialog loading = new ProgressDialog(EditProfile.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = apiService.updateprofile(strUserid,
                "",
                edtFname.getText().toString().trim(),
                edtLastname.getText().toString().trim(),
                subcast,
                maritalstatus,
                edtDob.getText().toString().trim(),
                gender,
                strCountryId,
                strStateId,
                strCityid,
                profession

        );
        call1.enqueue(new Callback<ResponseBody>() {


            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                loading.dismiss();

                try {
                    Log.e("EditProfile", "" + response.body().string());

                    finishAffinity();


                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("flag", "profile");
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    private boolean isEmpty() {

        String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (edtFname.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter FirstName", Toast.LENGTH_LONG).show();
            return true;
        } else if (edtLastname.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter LastName", Toast.LENGTH_LONG).show();
            return true;
        } else if (edtDob.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Select DateofBirth", Toast.LENGTH_LONG).show();
            return true;
        } /*else if (edtQualification.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Education Qualification", Toast.LENGTH_LONG).show();
            return true;
        } */
        /*else if (edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
            return true;
        } else if (!edtEmail.getText().toString().trim().matches(email_validate)) {
            Toast.makeText(getApplicationContext(), "Enter Valid Email", Toast.LENGTH_LONG).show();
            return true;
        }
*/
        return false;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_new);


        strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");

        findView();
        getProfileData();


    }


    private void getProfileData() {


        loading = new ProgressDialog(EditProfile.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<ModelProfile> call1 = apiService.userInfo(strUserid);
        call1.enqueue(new Callback<ModelProfile>() {
            @Override
            public void onResponse(Call<ModelProfile> call, retrofit2.Response<ModelProfile> response) {
//                loading.dismiss();

                edtFname.setText(response.body().getFirstname());
                edtLastname.setText(response.body().getLastname());
                edtEmail.setText(response.body().getEmail());

                gender = response.body().getGender() + "";
                Log.e("gender", gender + "");
                if (radiomale.getText().toString().trim().equals(gender.toString().trim() + "")) {
                    radiomale.setChecked(true);
                } else if (radioFemale.getText().toString().trim().equals(gender.toString().trim())) {
                    radioFemale.setChecked(true);
                } else if (radioOther.getText().toString().trim().equals(gender.toString().trim())) {
                    radioOther.setChecked(true);
                }

                maritalstatus = response.body().getMaritalStatus();
                Log.e("maritalstatus0", maritalstatus + "");

                if (maritalstatus != null) {
                    if (maritalstatus.trim().equals("Married")) {
                        radiomarried.setChecked(true);
                    }
                    if (maritalstatus.trim().equals("UnMarried")) {
                        radio_unmarried.setChecked(true);
                    }
                }


                edtDob.setText(response.body().getDob());
                getCountry(response.body().getCountry(), response.body().getState(), response.body().getCity());
                getSubcaste(response.body().getSubcaste());
                getProfession(response.body().getProfession());


                loading.dismiss();
            }


            @Override
            public void onFailure(Call<ModelProfile> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });

    }


    private void getCountry(final String countrydd, final String states, final String citys) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCountry>> call1 = apiService.get_country();
        call1.enqueue(new Callback<List<ModelCountry>>() {
            @Override
            public void onResponse(Call<List<ModelCountry>> call, retrofit2.Response<List<ModelCountry>> response) {
//                loading.dismiss();

//
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {

                        CountryList.add(response.body().get(i));
                    }
                }


                final List<String> country = new ArrayList<>();
                if (CountryList.size() > 0) {
                    for (int i = 0; i < CountryList.size(); i++) {
                        country.add(CountryList.get(i).getCountryName());
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter(EditProfile.this, android.R.layout.simple_spinner_item, country);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCountry.setAdapter(aa11);

                for (int j = 0; j < CountryList.size(); j++) {
                    if (CountryList.get(j).getCountryName().toString().trim().equals(countrydd.toString().trim())) {
                        spCountry.setSelection(j);
                    }
                }

                spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        strCountryId = CountryList.get(i).getId();
                        strCountryName = CountryList.get(i).getCountryName();

                        Log.e("countryId", strCountryId);
                        getState(states, citys);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }

            @Override
            public void onFailure(Call<List<ModelCountry>> call, Throwable t) {
//                loading.dismiss();

                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getState(final String statesss, final String cityww) {


//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
//        Toast.makeText(getApplicationContext(),""+strCountryId,Toast.LENGTH_SHORT).show();
        Call<List<ModelState>> call1 = apiService.get_states(strCountryId);
        call1.enqueue(new Callback<List<ModelState>>() {
            @Override
            public void onResponse(Call<List<ModelState>> call, retrofit2.Response<List<ModelState>> response) {

                StateList = new ArrayList<>();
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {

                        StateList.add(response.body().get(i));
                    }
                }


                final List<String> state = new ArrayList<>();
                if (StateList.size() > 0) {
                    for (int i = 0; i < StateList.size(); i++) {
                        state.add(StateList.get(i).getStateName());
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter(EditProfile.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spState.setAdapter(aa11);

                for (int j = 0; j < StateList.size(); j++) {
                    if (StateList.get(j).getStateName().toString().trim().equals(statesss.toString().trim())) {
                        spState.setSelection(j);
                    }
                }
                spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        strStateId = StateList.get(i).getId();
                        strStateName = StateList.get(i).getStateName();
                        getCity(cityww);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }

            @Override
            public void onFailure(Call<List<ModelState>> call, Throwable t) {
                Log.e("states", t.getMessage() + "");
            }
        });
    }

    private void getCity(final String citsy) {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCity>> call1 = apiService.get_city(strStateId);
        call1.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, retrofit2.Response<List<ModelCity>> response) {
//                loading.dismiss();
//

//                try {
//                    Log.e("cityList",response.body().string()+"");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                CityList = new ArrayList<>();
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {

                        CityList.add(response.body().get(i));
                    }
                }


                final List<String> city = new ArrayList<>();
                if (CityList.size() > 0) {
                    for (int i = 0; i < CityList.size(); i++) {
                        city.add(CityList.get(i).getCityName());
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter(EditProfile.this, android.R.layout.simple_spinner_item, city);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCity.setAdapter(aa11);

                for (int j = 0; j < city.size(); j++) {
                    if (city.get(j).toString().trim().equals(citsy.toString().trim())) {
                        spCity.setSelection(j);
                    }
                }

//
                spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                        strCityName = CityList.get(i).getCityName();
                        strCityid = CityList.get(i).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }

            @Override
            public void onFailure(Call<List<ModelCity>> call, Throwable t) {
//                loading.dismiss();
                Log.e("city", t.getMessage() + "");
            }
        });
    }

    private void getProfession(final String professionss) {


//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<Profession>> call1 = apiService.get_profession();
        call1.enqueue(new Callback<List<Profession>>() {


            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<List<Profession>> call, retrofit2.Response<List<Profession>> response) {
//                loading.dismiss();

//                Log.e("get_profession", response.body().size() + "");

                List<String> prStrings = new ArrayList<>();
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        professionlist.add(response.body().get(i));
                    }
                }

                if (professionlist.size() > 0) {
                    for (int i = 0; i < professionlist.size(); i++) {
                        prStrings.add(professionlist.get(i).getProfession());
                    }

                }


                ArrayAdapter aa11 = new ArrayAdapter(EditProfile.this, android.R.layout.simple_spinner_item, prStrings);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(aa11);


                for (int j = 0; j < prStrings.size(); j++) {
                    if (prStrings.get(j).toString().trim().equals(professionss)) {
                        spinner.setSelection(j);
                    }
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        profession = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }

            @Override
            public void onFailure(Call<List<Profession>> call, Throwable t) {
//                loading.dismiss();

                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }


    private void getSubcaste(final String subcaste) {


//        final ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
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
//                    Log.e("js", js.toString(0));
                    for (int i = 0; i < js.length(); i++) {

                        JSONObject person = (JSONObject) js.get(i);
                        String caste = person.getString("subcaste");

                        CastList.add(caste);
                        CastListid.add(person.getString("id"));

                    }

                    ArrayAdapter aa = new ArrayAdapter(EditProfile.this, android.R.layout.simple_spinner_item, CastList);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSubcaste.setAdapter(aa);

                    for (int j = 0; j < CastList.size(); j++) {
                        if (CastList.get(j).toString().trim().equals(subcaste)) {
                            spinnerSubcaste.setSelection(j);
                        }
                    }

                    spinnerSubcaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            subcast = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

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


    private void findView() {

        spCountry = (Spinner) findViewById(R.id.edit_profile_sp_country);
        spState = (Spinner) findViewById(R.id.edit_profile_sp_state);
        spCity = (Spinner) findViewById(R.id.edit_profile_sp_city);

        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        editProfileBack = (ImageView) findViewById(R.id.edit_profile_back11);
        imgUser = (CircleImageView) findViewById(R.id.img_user);
        btnChangeProfile = (Button) findViewById(R.id.btn_change_profile);
        edtFname = (EditText) findViewById(R.id.edt_fname);
        edtLastname = (EditText) findViewById(R.id.edt_lastname);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        spinnerSubcaste = (Spinner) findViewById(R.id.spinner_subcaste);
        radiomale = (RadioButton) findViewById(R.id.radiomale);
        radioFemale = (RadioButton) findViewById(R.id.radio_female);
        radioOther = (RadioButton) findViewById(R.id.radio_other);
        radiomarried = (RadioButton) findViewById(R.id.radiomarried);
        radio_unmarried = (RadioButton) findViewById(R.id.radio_unmarried);
        spinnerMaritalstatus = (Spinner) findViewById(R.id.spinner_maritalstatus);
        edtDob = (TextView) findViewById(R.id.edt_dob);
        spinner = (Spinner) findViewById(R.id.spinner);
        textView4 = (TextView) findViewById(R.id.textView4);
        btnUpdate = (Button) findViewById(R.id.btn_update);


        editProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View ff = EditProfile.this.getCurrentFocus();
                if (ff != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                finish();
            }
        });
        btnChangeProfile.setOnClickListener(this);
        radiomale.setOnClickListener(this);
        radioFemale.setOnClickListener(this);
        radioOther.setOnClickListener(this);
        radio_unmarried.setOnClickListener(this);
        radiomarried.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        List<String> marital = new ArrayList<>();
        marital.add("Married");
        marital.add("Unmarried");
//
//        ArrayAdapter aa112 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, marital);
//        aa112.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerMaritalstatus.setAdapter(aa112);
//
//        spinnerMaritalstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                maritalstatus = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


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
                // TODO Auto-generated method stub
                new DatePickerDialog(EditProfile.this, date, myCalendar
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
        dobmonth = myCalendar.get(Calendar.MONTH);
        dobday = myCalendar.get(Calendar.DAY_OF_MONTH);

        dob = dobday + "/" + dobmonth + "/" + dobyear;

        Log.e("dobdata", dob + "");

    }


}
