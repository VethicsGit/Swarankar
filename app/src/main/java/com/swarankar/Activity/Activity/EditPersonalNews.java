package com.swarankar.Activity.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.swarankar.Activity.Model.News.NewsPost;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPersonalNews extends AppCompatActivity {


    private static final int TIME_DIALOG_ID = 1111;
    private static final int MY_CAPTURE_REQUEST_CODE = 1;
    TextView txtStartDate, txtEndDate, txtTime;
    private static final int RESULT_GALLERY = 0;
    private static final int CAMERA_REQUEST = 1;
    ImageView imgUpload;
    Calendar myCalendar;
    private int hour;
    private String imgPath;
    private int minute;
    String uri;
    String strImage = "";
    int count = 0;
    android.support.v7.app.AlertDialog.Builder builder;


    EditText edEventTitle, edOrganization, edVanue, edContactPerson, edMobilNo;
    Button btnPost;
    //ImageView imgBack;
    final String[] items = {"Camera", "Gallery"};
    String strJobid;
    String strphotopath;
    String strUsername;
    String strCreatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit News");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(EditPersonalNews.this);
                finish();
            }
        });

        Bundle p = getIntent().getExtras();
        strJobid = p.getString("jobid");
        String strNewsitle = p.getString("strNewsitle");
        String strAddress = p.getString("strAddress");
        String strEventStartDate = p.getString("strEventStartDate");
        String strEventEndDate = p.getString("strEventEndDate");
        String strContactName = p.getString("strContactName");
        String strContactNumbar = p.getString("strContactNumbar");
        String strEventTime = p.getString("strEventTime");
        strCreatedDate = p.getString("strCreatedDate");
        strUsername = p.getString("strUsername");
        strImage = p.getString("strphotopath");


        findView();
        if (strImage.equals("")) {
            Glide.with(getApplicationContext()).load(R.drawable.placeholder).into(imgUpload);
        } else {
            Glide.with(getApplicationContext()).load(strImage)/*.error(R.drawable.placeholder)*/.into(imgUpload);
        }

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bitmap = null;
                try {
                    InputStream input = new java.net.URL(strImage).openStream();
                    // Decode Bitmap
                    bitmap = BitmapFactory.decodeStream(input);

                    //bitmap = Glide.with(getApplicationContext()).load(strImage).asBitmap().into(500, 500).get();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null) {

                    strImage = AndroidUtils.BitMapToString(bitmap);
                }
            }
        }.execute();
        txtStartDate.setText(strEventStartDate);
        txtEndDate.setText(strEventEndDate);
        txtTime.setText(strEventTime);
//        imgUpload.setText(strEventStartDate);
        edEventTitle.setText(strNewsitle);
        edOrganization.setText("");
        edVanue.setText(strAddress);
        edContactPerson.setText(strContactName);

        edMobilNo.setText(strContactNumbar);

//        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
//        Log.e("id",strUserid);


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//

                if (!isEmpty()) {
                    setData();
                }
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
                if (count == 1) {
                    updateLabel();
                } else {
                    updateLabel2();
                }

            }

        };

       /* imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtils.hideSoftKeyboard(EditPersonalNews.this);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPersonalNews.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                count = 1;
            }
        });

        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtils.hideSoftKeyboard(EditPersonalNews.this);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPersonalNews.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                count = 2;
            }
        });
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtils.hideSoftKeyboard(EditPersonalNews.this);
                showDialog(TIME_DIALOG_ID);
            }
        });
        imgUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new android.support.v7.app.AlertDialog.Builder(EditPersonalNews.this);
                builder.setTitle("Select Your Photo");


                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which] == "Gallery") {

                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(galleryIntent, RESULT_GALLERY);


                        } else if (items[which] == "Camera") {
                            if (ContextCompat.checkSelfPermission(EditPersonalNews.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                            } else {
                                ActivityCompat.requestPermissions(EditPersonalNews.this, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAPTURE_REQUEST_CODE);
                            }


                        }

                    }
                });
                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();
            }

        });

    }

    private boolean isEmpty() {

        if (edEventTitle.getText().toString().isEmpty()) {
            edEventTitle.setError("Please Enter event title");
            return true;
        } else if (edOrganization.getText().toString().isEmpty()) {
            edOrganization.setError("Please Enter name of Organization");
            return true;
        } else if (edVanue.getText().toString().isEmpty()) {
            edVanue.setError("Please Enter Venue");
            return true;
        } else if (edMobilNo.getText().toString().isEmpty()) {
            edMobilNo.setError("Please Enter Mobile number");
            return true;
        } else if (!(edMobilNo.getText().toString().length() == 10)) {
            edMobilNo.setError("Please Enter valid number");
            return true;
        } else if (txtStartDate.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Select Start Date", Toast.LENGTH_SHORT).show();
            return true;
        } else if (txtEndDate.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Select End Date", Toast.LENGTH_SHORT).show();
            return true;
        } else if (txtTime.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Select time Date", Toast.LENGTH_SHORT).show();
            return true;
        } else if (edContactPerson.getText().toString().isEmpty()) {
            edContactPerson.setError("Please Enter Contact Person");
            return true;
        } else if (strImage.equals("")) {
            Toast.makeText(getApplicationContext(), "Please upload the image", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void setData() {
        final ProgressDialog loading = new ProgressDialog(EditPersonalNews.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        Log.e("image", strImage);

        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiservice = APIClient.getClient().create(API.class);
        Call<NewsPost> call1 = apiservice.newslist_update(strUserid, strJobid,
                strImage,
                edEventTitle.getText().toString(),
                edMobilNo.getText().toString(),
                edContactPerson.getText().toString(),
                txtStartDate.getText().toString(),
                txtEndDate.getText().toString(),
                txtTime.getText().toString().trim(), edVanue.getText().toString());
        call1.enqueue(new Callback<NewsPost>() {
            @Override
            public void onResponse(Call<NewsPost> call, Response<NewsPost> response) {
                loading.dismiss();
//                addNews: {"response":"Success","message":"Your data inserted Successfully.."}
                loading.dismiss();
                String status = response.body().getResponse();
                if (status.equals("Success")) {
                    String msg = response.body().getMessage();
                    buildDialogmno(msg + "", EditPersonalNews.this);
                    finish();

                } else {
                    String msg = response.body().getMessage();
                    buildDialogmno(msg + "", EditPersonalNews.this);
                }
//                try {
//                    Log.e("addNews",response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<NewsPost> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    private void buildDialogmno(String s, EditPersonalNews jobEdit) {
        final Dialog alertDialog = new Dialog(jobEdit);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(jobEdit, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(jobEdit).inflate(R.layout.view_alert_dialog, null);
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

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtStartDate.setText(sdf.format(myCalendar.getTime()));

//        dobyear = myCalendar.get(Calendar.YEAR);
//        dobmonth = myCalendar.get(Calendar.MONTH);
//        dobday = myCalendar.get(Calendar.DAY_OF_MONTH);
//
//        dob = dobday + "/" + dobmonth + "/" + dobyear;
//
//        Log.e("dobdata", dob + "");

    }

    private void updateLabel2() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtEndDate.setText(sdf.format(myCalendar.getTime()));

//        dobyear = myCalendar.get(Calendar.YEAR);
//        dobmonth = myCalendar.get(Calendar.MONTH);
//        dobday = myCalendar.get(Calendar.DAY_OF_MONTH);
//
//        dob = dobday + "/" + dobmonth + "/" + dobyear;
//
//        Log.e("dobdata", dob + "");

    }

    private void findView() {
        txtStartDate = (TextView) findViewById(R.id.news_post_start_date);
        txtEndDate = (TextView) findViewById(R.id.news_post_end_date);
        txtTime = (TextView) findViewById(R.id.news_post_time);
        imgUpload = (ImageView) findViewById(R.id.image_upload);
        edEventTitle = (EditText) findViewById(R.id.news_post_event_title);
        edOrganization = (EditText) findViewById(R.id.news_post_name_of_organization);
        edVanue = (EditText) findViewById(R.id.news_post_vanue);
        edContactPerson = (EditText) findViewById(R.id.news_post_contact_person);
        btnPost = (Button) findViewById(R.id.new_post_btn_post);
        edMobilNo = (EditText) findViewById(R.id.news_post_mobile_no);
        //imgBack = (ImageView) findViewById(R.id.news_post_image_back);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:

                // set time picker as current time
                return new TimePickerDialog(this, timePickerListener, hour, minute,
                        false);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;

            updateTime(hour, minute);
//            utilTime(hour);

        }

    };

    private static String utilTime(int value) {

        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);


        // Append in a StringBuilder

        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        txtTime.setText(aTime);
    }

    public Uri setImageUri() {

        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".my.package.name.provider", file);
//        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return photoURI;
    }


    public String getImagePath() {
        return imgPath;
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
