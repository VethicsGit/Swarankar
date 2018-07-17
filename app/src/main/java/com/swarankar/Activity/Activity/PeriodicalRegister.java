package com.swarankar.Activity.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.swarankar.Activity.Model.ModelState.ModelState;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.Utils.FileUtils;
import com.swarankar.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.swarankar.Activity.Utils.APIClient.BASE_URL;
import static com.swarankar.Activity.Utils.APIClient1.BASE_URL1;
import static com.swarankar.Activity.Utils.FileUtils.isExternalStorageDocument;

public class PeriodicalRegister extends AppCompatActivity {

    private static final boolean IS_CHUNKED = true;

    EditText edPeriodicalName, edChiefname, edContactNo, edEmailAddress, edOfficeAddress, edPincode;
    Spinner spCountry, spState, spCity, spRegisterStatus;
    ImageView imgFront, imgBack, imgBrowsePDF;
    Button btnRegister;
    private String imgPath;

    private static final int RESULT_GALLERY = 0;
    private static final int CAMERA_REQUEST = 2;
    private int PICK_PDF_REQUEST = 1;

    private static final int MY_CAPTURE_REQUEST_CODE = 4;
    public static final String UPLOAD_URL = "";

    String uri;

    public static String imageFront = "", imageBack = "";


    final String[] items = {"Camera", "Gallery"};
    android.support.v7.app.AlertDialog.Builder builder;

    String strCountryId = "", strCountryName = "", strStateId = "", strStateName = "", strCityName = "",
            strCityid = "";
    public List<ModelCountry> CountryList = new ArrayList<>();
    public List<ModelState> StateList;
    public List<ModelCity> CityList;

    String strSelect = "", strRegisterStatus = "";

    String basePdf;
    Uri pdfpath;
    public static String strSelectedPdf;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodical_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register Periodicals");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(PeriodicalRegister.this);
                finish();
            }
        });
        findview();
        getOfficeCountry();
        getRegister();


        /*imgBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new android.support.v7.app.AlertDialog.Builder(PeriodicalRegister.this);
                builder.setTitle("Select Your Photo");

                strSelect = "back";
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which] == "Gallery") {

                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(galleryIntent, RESULT_GALLERY);


                        } else if (items[which] == "Camera") {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            } else {
                                ActivityCompat.requestPermissions(PeriodicalRegister.this, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAPTURE_REQUEST_CODE);
                            }


                        }

                    }
                });
                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();
            }
        });
        imgFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                builder = new android.support.v7.app.AlertDialog.Builder(PeriodicalRegister.this);
                builder.setTitle("Select Your Photo");

                strSelect = "front";
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which] == "Gallery") {

                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(galleryIntent, RESULT_GALLERY);


                        } else if (items[which] == "Camera") {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            } else {
                                ActivityCompat.requestPermissions(PeriodicalRegister.this, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAPTURE_REQUEST_CODE);
                            }


                        }

                    }
                });
                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();

            }
        });

        imgBrowsePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

//                Intent i = new Intent(PeriodicalRegister.this, MyService.class);
//                startService(i);
//                finish();


//                uploadMultipart();
//                pdfData();
//                Intent i = new Intent(PeriodicalRegister.this, MyService.class);
//                startService(i);
//                if (!isEmpty()) {
//
//
//
//                }
//                sendData();
            }
        });


    }


    public void uploadMultipart() {

        String path = null;

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {


//        }else{


//        File file = new File(filePath.getPath());
//        filePath = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".my.package.name.provider", file);

//                path = filePath.getPath();
//             path = getRealPathFromURI(this,filePath);


        path = FilePath.getPath(PeriodicalRegister.this, filePath);
//                 path =  getPDFPath(filePath);

//            Log.e("path",path);
//    }
//          path =  getPDFPath(filePath);
//        String path =  FileUtils.getPath(getApplicationContext(), filePath);
        if (path == null) {

            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();

                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                        .addFileToUpload(path, "pdf") //Adding file
                        .addParameter("name", "nammmmm11") //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void pdfData() {
        final ProgressDialog loading = new ProgressDialog(PeriodicalRegister.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build();


        String path;
        path = FilePath.getPath(PeriodicalRegister.this, filePath);

//        strSelectedPdf =  FileUtils.getPath(getApplicationContext(), pdfpath);
//        String path = FilePath.getPath(this, filePath);
//        String path =  getFilePathFromContentUri(filePath);

//        String path =  getPDFPath(filePath);

//        File myFile = new File(strSelectedPdf);
//        File file = FileUtils.getFile(this, pdfpath);
//        File file = new File(pdfpath.getP);
//        Log.e("strSelectedPdf", strSelectedPdf);

//        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(pdfpath)), path);
        if (path == null) {

            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("pdf"), path);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", edPeriodicalName.getText().toString(), requestFile);

        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL1).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        API apiservice = retrofit.create(API.class);
        Call<ResponseBody> call1 = apiservice.uploadfiledemo(edPeriodicalName.getText().toString(), fileToUpload);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                addNews: {"response":"Success","message":"Your data inserted Successfully.."}
                loading.dismiss();
//                String status = response.body().getResponse();
//                if(status.equals("Success")){
//                    String msg = response.body().getMessage();
//                    Log.e("msg",msg);
////                    Toast.makeText(getApplicationContext(),"complete",Toast.LENGTH_SHORT).show();
////                    dialog(msg + "", NewsPostActivity.this);
//                }else {
//
//                    String msg = response.body().getMessage();
//                    Log.e("msg",msg);
//                    dialog(msg + "", NewsPostActivity.this);
//                }
                try {
                    Log.e("addNews", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Log.e("error", String.valueOf(t));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getPDFPath(Uri filePath) {


        if (isExternalStorageDocument(filePath)) {
            final String docId = DocumentsContract.getDocumentId(filePath);
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
            return Environment.getExternalStorageDirectory() + "/" + split[1];
        } else {
            final String id = DocumentsContract.getDocumentId(filePath);
            final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void sendData() {
        final ProgressDialog loading = new ProgressDialog(PeriodicalRegister.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
//        API apiService = APIClient.getClient().create(API.class);


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.MINUTES)
                .connectTimeout(3, TimeUnit.MINUTES)
                .build();

        strSelectedPdf = FileUtils.getPath(getApplicationContext(), pdfpath);
//        File myFile = new File(strSelectedPdf);
        File file = FileUtils.getFile(this, pdfpath);
        Log.e("strSelectedPdf", strSelectedPdf);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(pdfpath)), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("pdf", edPeriodicalName.getText().toString(), requestFile);

        String userid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        RequestBody userid1 = RequestBody.create(MediaType.parse("text"), userid);
        RequestBody name = RequestBody.create(MediaType.parse("text"), edPeriodicalName.getText().toString());
        RequestBody strRegisterStatus1 = RequestBody.create(MediaType.parse("text"), strRegisterStatus);
        RequestBody edChiefname1 = RequestBody.create(MediaType.parse("text"), edChiefname.getText().toString());
        RequestBody edContactNo1 = RequestBody.create(MediaType.parse("text"), edContactNo.getText().toString());
        RequestBody edEmailAddress1 = RequestBody.create(MediaType.parse("text"), edEmailAddress.getText().toString());
        RequestBody edOfficeAddress1 = RequestBody.create(MediaType.parse("text"), edOfficeAddress.getText().toString());
        RequestBody strCountryName1 = RequestBody.create(MediaType.parse("text"), strCountryName);
        RequestBody strStateName1 = RequestBody.create(MediaType.parse("text"), strStateName);
        RequestBody strCityName1 = RequestBody.create(MediaType.parse("text"), strCityName);
        RequestBody edPincode1 = RequestBody.create(MediaType.parse("text"), edPincode.getText().toString());
        RequestBody imageFront1 = RequestBody.create(MediaType.parse("text"), imageFront);
        RequestBody imageBack1 = RequestBody.create(MediaType.parse("text"), imageBack);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        API apiservice = retrofit.create(API.class);
        Call<ResponseBody> call1 = apiservice.register_periodical(userid1, name,
                strRegisterStatus1,
                edChiefname1,
                edContactNo1,
                edEmailAddress1,
                edOfficeAddress1,
                strCountryName1,
                strStateName1,
                strCityName1,
                edPincode1,
                imageFront1, imageBack1, requestFile);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                loading.dismiss();

                try {
                    Log.e("response", response.body().string() + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                String strRespose = response.body().getResponse();
//                String mes = response.body().getMessage();
//                if(strRespose.equals("Success")){
//                    dialog(mes + "",ForgotPassword.this);
//                }
//                else {
//                    dialog(mes + "", ForgotPassword.this);
//                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

    private void getRegister() {
        List<String> genderlidt = new ArrayList<>();
        genderlidt.add("Select");
        genderlidt.add("Register");
        genderlidt.add("Unregister");


        ArrayAdapter aa112 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderlidt);
        aa112.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRegisterStatus.setAdapter(aa112);


        spRegisterStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override


            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strRegisterStatus = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean isEmpty() {

        String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (edPeriodicalName.getText().toString().isEmpty()) {

            edPeriodicalName.setError("Enter Periodical");
//            Toast.makeText(getApplicationContext(), "Enter FirstName", Toast.LENGTH_LONG).show();
            return true;
        } else if (strRegisterStatus.equals("Select")) {


//            TextView errorText = (TextView) spCity.getSelectedView();
//            errorText.setError("");
//            errorText.setText("Select City");

            Toast.makeText(getApplicationContext(), "Select Register", Toast.LENGTH_LONG).show();
            return true;
        } else if (edChiefname.getText().toString().isEmpty()) {

            edChiefname.setError("Enter Chief name");

//            Toast.makeText(getApplicationContext(), "Enter LastName", Toast.LENGTH_LONG).show();
            return true;
        } else if (edContactNo.getText().toString().isEmpty()) {


            edContactNo.setError("Enter contact no");

//            Toast.makeText(getApplicationContext(), "Select Subcaste", Toast.LENGTH_LONG).show();
            return true;
        } else if (!(edContactNo.getText().toString().length() == 10)) {
            edContactNo.setError("Please Enter valid number");
            return true;
        } else if (edEmailAddress.getText().toString().isEmpty()) {

            edEmailAddress.setError("Enter Email");
//            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
            return true;
        } else if (!edEmailAddress.getText().toString().trim().matches(email_validate)) {
            edEmailAddress.setError("Enter ValidEmail");
//            Toast.makeText(getApplicationContext(), "Enter Valid Email", Toast.LENGTH_LONG).show();
            return true;
        } else if (edOfficeAddress.getText().toString().isEmpty()) {


            edOfficeAddress.setError("Enter office Address");

//            Toast.makeText(getApplicationContext(), "Select Subcaste", Toast.LENGTH_LONG).show();
            return true;
        } else if (strCountryName.equals("Select")) {


            TextView errorText = (TextView) spCountry.getSelectedView();
            errorText.setError("");
            errorText.setText("Select Country");

//            Toast.makeText(getApplicationContext(), "Select Subcaste", Toast.LENGTH_LONG).show();
            return true;
        } else if (strCityName.equals("Select")) {


            TextView errorText = (TextView) spState.getSelectedView();
            errorText.setError("");
            errorText.setText("Select State");

//            Toast.makeText(getApplicationContext(), "Select Subcaste", Toast.LENGTH_LONG).show();
            return true;
        } else if (strCityName.equals("Select")) {


            TextView errorText = (TextView) spCity.getSelectedView();
            errorText.setError("");
            errorText.setText("Select City");

//            Toast.makeText(getApplicationContext(), "Select Subcaste", Toast.LENGTH_LONG).show();
            return true;
        } else if (!edPincode.getText().toString().trim().equals("")) {
            edPincode.setError("Enter pincode");
//            Toast.makeText(getApplicationContext(), "Enter Valid Email", Toast.LENGTH_LONG).show();
            return true;
        } else if (imageFront.equals("")) {


//            TextView errorText = (TextView) spCity.getSelectedView();
//            errorText.setError("");
//            errorText.setText("Select City");

            Toast.makeText(getApplicationContext(), "Select Front Image", Toast.LENGTH_LONG).show();
            return true;
        } else if (imageBack.equals("")) {


//            TextView errorText = (TextView) spCity.getSelectedView();
//            errorText.setError("");
//            errorText.setText("Select City");

            Toast.makeText(getApplicationContext(), "Select back Image", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;

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

    private void findview() {
        edPeriodicalName = (EditText) findViewById(R.id.periodical_ed_periodical_name);
        edChiefname = (EditText) findViewById(R.id.periodica_ed_cheif_name);
        edContactNo = (EditText) findViewById(R.id.periodical_ed_contact_name);
        edEmailAddress = (EditText) findViewById(R.id.periodical_ed_email_address);
        edOfficeAddress = (EditText) findViewById(R.id.periodical_ed_office_address);
        spCountry = (Spinner) findViewById(R.id.periodical_spinner_country);
        spState = (Spinner) findViewById(R.id.periodical_spinner_state);
        spCity = (Spinner) findViewById(R.id.periodical_spinner_city);
        imgFront = (ImageView) findViewById(R.id.periodical_imageview_front);
        imgBack = (ImageView) findViewById(R.id.periodical_imageview_back);
        btnRegister = (Button) findViewById(R.id.periodicals_button_register);
        // imgBack1 = (ImageView) findViewById(R.id.periodicals_image_back);
        spRegisterStatus = (Spinner) findViewById(R.id.periodical_register_status);
        edPincode = (EditText) findViewById(R.id.periodical_ed_pincode);
        imgBrowsePDF = (ImageView) findViewById(R.id.periodical_imageview_pdf);
    }

    private void getOfficeCountry() {


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

                final List<String> state = new ArrayList<>();
                state.add("Select");


                ArrayAdapter aa11 = new ArrayAdapter(PeriodicalRegister.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spState.setAdapter(aa11);


                final List<String> city = new ArrayList<>();
                city.add("Select");


                ArrayAdapter aa1 = new ArrayAdapter(PeriodicalRegister.this, android.R.layout.simple_spinner_item, city);
                aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCity.setAdapter(aa1);


                final List<String> country = new ArrayList<>();
                if (CountryList.size() > 0) {
                    for (int i = 0; i < CountryList.size(); i++) {
                        if (i == 0) {
                            country.add("Select");
                            country.add(CountryList.get(i).getCountryName());

                        } else {
                            country.add(CountryList.get(i).getCountryName());
                        }
                    }

                }

                ArrayAdapter a = new ArrayAdapter(PeriodicalRegister.this, android.R.layout.simple_spinner_item, country);
                a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCountry.setAdapter(a);
                getState();


                spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (country.get(i).equals("Select")) {

                            strCountryName = "";

                        } else {
                            strCountryId = CountryList.get(i - 1).getId();
                            strCountryName = CountryList.get(i - 1).getCountryName();
                            getState();
                        }


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

                ArrayAdapter aa11 = new ArrayAdapter(PeriodicalRegister.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spState.setAdapter(aa11);


                spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (StateList.get(i).equals("Select")) {
                            strStateName = "";
                            getCity();
                        } else {
                            strStateId = StateList.get(i).getId();
                            strStateName = StateList.get(i).getStateName();
                            getCity();
                        }
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

                ArrayAdapter aa11 = new ArrayAdapter(PeriodicalRegister.this, android.R.layout.simple_spinner_item, city);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCity.setAdapter(aa11);


//
                spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        if (CityList.get(i).equals("Select")) {
                            strCityName = "";
                        } else {
                            strCityName = CityList.get(i).getCityName();
                            strCityid = CityList.get(i).getId();
                        }

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {


            if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {


                filePath = data.getData();


//                File file = new File(Environment.getExternalStorageDirectory()+data.getData().getPath());
//                filePath = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".my.package.name.provider", file);


            }


//


            if (requestCode == RESULT_GALLERY) {

                uri = String.valueOf(data.getData());

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.parse(uri));

                    if (strSelect.equals("back")) {
                        imgBack.setImageBitmap(bitmap);
                        imageBack = AndroidUtils.BitMapToString(bitmap);
                    } else {
                        imgFront.setImageBitmap(bitmap);
                        imageFront = AndroidUtils.BitMapToString(bitmap);
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_REQUEST) {


                uri = getImagePath();
                if (strSelect.equals("back")) {
                    imgBack.setImageBitmap(decodeFile1(uri));
                    imageBack = AndroidUtils.BitMapToString(decodeFile1(uri));
                } else {
                    imgFront.setImageBitmap(decodeFile1(uri));
                    imageFront = AndroidUtils.BitMapToString(decodeFile1(uri));
                }
//                imgFront.setImageBitmap(decodeFile1(uri));

//                ProfileImage = AndroidUtils.BitMapToString(decodeFile1(uri));
//


            }


        }


    }

    public String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    public Bitmap decodeFile1(String path) {
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
