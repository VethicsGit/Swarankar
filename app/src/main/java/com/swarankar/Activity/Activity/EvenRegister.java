package com.swarankar.Activity.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.swarankar.Activity.Model.ModelCity.ModelCity;
import com.swarankar.Activity.Model.ModelCountry.ModelCountry;
import com.swarankar.Activity.Model.ModelEventDetails.ModeleventRegister;
import com.swarankar.Activity.Model.ModelState.ModelState;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class EvenRegister extends AppCompatActivity {

    EditText edName, edPresidentname, edPresidentNo, edSecreteryname, edSecreteryno, edAddress, edPincode;
    Spinner spCountry, spState, spCity, spRegisterStatus;
    Button btnPost;
    String strCountryId, strCountryName, strStateId, strStateName, strCityName, profession, subcaste,
            strStatus, strGotraSelf, strCityid;
    public List<ModelCountry> CountryList = new ArrayList<>();
    public List<ModelState> StateList;
    public List<ModelCity> CityList;
    ProgressDialog loading;
    final String[] items = {"Camera", "Gallery"};
    ImageView imgUpload;
    android.support.v7.app.AlertDialog.Builder builder;
    private static final int RESULT_GALLERY = 0;
    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAPTURE_REQUEST_CODE = 1;
    private String imgPath;
    String uri;
    String strImage = "";
    String strUserID;
    //ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register Society");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(EvenRegister.this);
                finish();
            }
        });
        findView();
        getdata();
        grtRegisterStatus();

        /*imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new android.support.v7.app.AlertDialog.Builder(EvenRegister.this);
                builder.setTitle("Select Your Photo");


                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which] == "Gallery") {

                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(galleryIntent, RESULT_GALLERY);


                        } else if (items[which] == "Camera") {
                            if (ContextCompat.checkSelfPermission(EvenRegister.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            } else {
                                ActivityCompat.requestPermissions(EvenRegister.this, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAPTURE_REQUEST_CODE);
                            }


                        }

                    }
                });
                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();

            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty()) {
                    postData();
                }


            }
        });
    }

    private boolean isEmpty() {
//        EditText edName,edPresidentname,edPresidentNo,edSecreteryname,edSecreteryno,edAddress,edPincode;
        String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (edName.getText().toString().isEmpty()) {
            edName.setError("Enter Society name");
            return true;

        } else if (edPresidentname.getText().toString().isEmpty()) {
            edPresidentname.setError("Enter President Name");
            return true;
        } else if (edPresidentNo.getText().toString().isEmpty()) {
            edPresidentNo.setError("Enter President Number");
            return true;
        } else if (!(edPresidentNo.getText().toString().length() == 10)) {
            edPresidentNo.setError("Please Enter valid number");
            return true;
        } else if (edSecreteryname.getText().toString().isEmpty()) {
            edSecreteryname.setError("Enter Secretary Name");
            return true;
        } else if (edSecreteryno.getText().toString().isEmpty()) {
            edSecreteryno.setError("Enter Secretory Number");
            return true;
        } else if (!(edSecreteryno.getText().toString().length() == 10)) {
            edSecreteryno.setError("Please Enter valid number");
            return true;
        } else if (edAddress.getText().toString().isEmpty()) {
            edAddress.setError("Enter Adress");
            return true;
        } else if (strImage.equals("")) {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    private void postData() {
        strUserID = Constants.loginSharedPreferences.getString(Constants.uid, "");

        final ProgressDialog loading = new ProgressDialog(EvenRegister.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

//        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);


//                (@Field("user_id") String userid,
//                        @Field("society_name") String  society_name,
//                        @Field("reg_status") String  reg_status,
//                        @Field("president_name") String  president_name,
//                        @Field("president_contact_number") String  president_contact_number,
//                        @Field("secretary_name") String  secretary_name,
//                        @Field("secretary_contact_number") String  secretary_contact_number,
//                        @Field("address") String  address,
//                        @Field("country") String  country,
//                        @Field("state") String  state,
//                        @Field("city") String  city,
//                        @Field("picture") String  picture);
        Call<ModeleventRegister> call1 = apiService.addSociety(strUserID,
                edName.getText().toString(),
                strStatus,
                edPresidentname.getText().toString(),
                edPresidentNo.getText().toString(),
                edSecreteryname.getText().toString(),
                edSecreteryno.getText().toString(),
                edAddress.getText().toString(),
                strCountryName,
                strStateName,
                strCityName, strImage);
        call1.enqueue(new Callback<ModeleventRegister>() {
            @Override
            public void onResponse(Call<ModeleventRegister> call, retrofit2.Response<ModeleventRegister> response) {
                loading.dismiss();

//                        try {
//                            Log.e("response",response.body().string()+"");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

                String strRespose = response.body().getResponse();
                String mes = response.body().getMessage();
                if (strRespose.equals("Success")) {
                    dialog(mes + "", EvenRegister.this);
                } else {
                    dialog(mes + "", EvenRegister.this);
                }

            }


            @Override
            public void onFailure(Call<ModeleventRegister> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

    private void dialog(String s, EvenRegister family) {
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


    public Uri setImageUri() {

        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
//        Uri imgUri = Uri.fromFile(file);
        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".my.package.name.provider", file);
        this.imgPath = file.getAbsolutePath();
        return photoURI;
    }


    public String getImagePath() {
        return imgPath;
    }

    private void grtRegisterStatus() {

        List<String> genderlidt = new ArrayList<>();
        genderlidt.add("Register");
        genderlidt.add("Unregister");


        ArrayAdapter aa112 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderlidt);
        aa112.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRegisterStatus.setAdapter(aa112);

        spRegisterStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strStatus = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getdata() {
        loading = ProgressDialog.show(EvenRegister.this, "Fetching Data", "Please Wait..", false);
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

                ArrayAdapter aa11 = new ArrayAdapter(EvenRegister.this, android.R.layout.simple_spinner_item, country);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCountry.setAdapter(aa11);


                spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        strCountryId = CountryList.get(i).getId();
                        strCountryName = CountryList.get(i).getCountryName();

                        Log.e("countryId", strCountryId);

                        getState();


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


    private void getState() {


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

                ArrayAdapter aa11 = new ArrayAdapter(EvenRegister.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spState.setAdapter(aa11);


                spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        strStateId = StateList.get(i).getId();
                        strStateName = StateList.get(i).getStateName();
                        getCity();
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

    private void getCity() {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCity>> call1 = apiService.get_city(strStateId);
        call1.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, retrofit2.Response<List<ModelCity>> response) {
                loading.dismiss();
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

                ArrayAdapter aa11 = new ArrayAdapter(EvenRegister.this, android.R.layout.simple_spinner_item, city);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCity.setAdapter(aa11);


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

    private void findView() {
        edName = (EditText) findViewById(R.id.event_reg_society_name);
        edName.requestFocus();
        AndroidUtils.showSoftKeyboard(EvenRegister.this, edName);
        edPresidentname = (EditText) findViewById(R.id.event_register_president_name);
        edPresidentNo = (EditText) findViewById(R.id.event_register_president_no);
        edSecreteryname = (EditText) findViewById(R.id.event_register_secretary_name);
        edSecreteryno = (EditText) findViewById(R.id.event_register_secretary_no);
        edAddress = (EditText) findViewById(R.id.ed_reg_address);
        edPincode = (EditText) findViewById(R.id.event_reg_ed_pincode);
        spCountry = (Spinner) findViewById(R.id.event_reg_spinner_country);
        spState = (Spinner) findViewById(R.id.event_reg_spinner_state);
        spCity = (Spinner) findViewById(R.id.event_reg_spinner_city);
        spRegisterStatus = (Spinner) findViewById(R.id.event_reg_spinner_register_status);
        btnPost = (Button) findViewById(R.id.event_regiser_btn_post);
        imgUpload = (ImageView) findViewById(R.id.event_register_image_upload);
        //imgBack = (ImageView) findViewById(R.id.register_image_back);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == RESULT_GALLERY) {

                uri = String.valueOf(data.getData());

                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(uri));
//                    imgUpload.setImageBitmap(bitmap);
//                    strImage = AndroidUtils.BitMapToString(bitmap);

                    imgUpload.setImageBitmap(AndroidUtils.decodeUri(getApplicationContext(), Uri.parse(uri), 300));
                    strImage = AndroidUtils.BitMapToString(AndroidUtils.decodeUri(getApplicationContext(), Uri.parse(uri), 300));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_REQUEST) {


                uri = getImagePath();
                imgUpload.setImageBitmap(decodeFile(uri));

                strImage = AndroidUtils.BitMapToString(decodeFile(uri));
//                Uri selectedImage = imageUri;
//
//                Log.d("msg", "Image Uri : " + imageUri.toString());
//
//                pictureUrl = imageUri.toString();
//
//                getContentResolver().notifyChange(selectedImage, null);
//                ContentResolver cr = getContentResolver();
//                Bitmap bitmap;
//                try {
//                    bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
//
//                    if (bitmap != null) {
//                        imgUser.setImageBitmap(bitmap);
//                        isImageSelected = true;
//                        profileimage = AndroidUtils.BitMapToString(bitmap);
//
//                    }
//
//                } catch (Exception e) {
//                    Toast.makeText(EditProfile.this, "Failed to load", Toast.LENGTH_SHORT)
//                            .show();
//                    Log.e("msg", "Camera " + e.toString());
//                }


            }


        }
    }

    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_CAPTURE_REQUEST_CODE) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

        }
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
