package com.swarankar.Activity.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.swarankar.Activity.Model.joblist.JobResponse;
import com.swarankar.Activity.Model.joblist.ModelAreaList;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddJobActivity extends AppCompatActivity implements View.OnClickListener {

    //private ImageView imgBack;
    private EditText activityAddJobEdJob;
    private Spinner activityAddJobSpAreaWork;
    private EditText activityAddJobEdDepartment;
    private EditText activityAddJobEdSelectDesign;
    private EditText activityAddJobEdContactName;
    private EditText activityAddJobEdContactNumber;
    private EditText activityAddJobEdEmail;
    private TextView activityAddJobTxtDate;
    private ImageView activityAddJobImageCalender;
    private EditText activityAddJobEdQualification;
    private EditText activityAddJobEdDescription;
    List<ModelAreaList> areaList = new ArrayList<>();
    String strAreaWork;
    Calendar myCalendar;
    Button btnSubmit;
    String strUserid;

//    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        findViews();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Job");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(this);

        /*imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        Log.e("userid", strUserid);
        String strQualification = "Qualification*";
        Spannable spannableString = new SpannableString(strQualification.toString());
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.color_red)), strQualification.indexOf("*"), strQualification.indexOf("*") + "*".length(), spannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        activityAddJobEdQualification.setHint(spannableString);

        String strJobDecs = "Job Description*";
        Spannable spannableString1 = new SpannableString(strJobDecs.toString());
        spannableString1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.color_red)), strJobDecs.indexOf("*"), strJobDecs.indexOf("*") + "*".length(), spannableString1.SPAN_EXCLUSIVE_EXCLUSIVE);
        activityAddJobEdDescription.setHint(spannableString1);

        myCalendar = Calendar.getInstance();
//        Date today = new Date();
//        CalendarView.setSelectedDate(today);
//        myCalendar.state().edit().setMinimumDate(today).commit();

        activityAddJobImageCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectLastDate();
            }
        });

        activityAddJobTxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectLastDate();
            }
        });


        getAreaWork();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty()) {
                    getData();
                }
            }
        });
    }

    private void SelectLastDate() {
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
        AndroidUtils.hideSoftKeyboard(AddJobActivity.this);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddJobActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//                datePickerDialog.setMinDate(System.currentTimeMillis() - 1000);
//                new DatePickerDialog(AddJobActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private boolean isEmpty() {
        String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (activityAddJobEdJob.getText().toString().isEmpty()) {
            activityAddJobEdJob.setError("Enter job name");
            return true;
        } else if (strAreaWork.equals("Area of Work")) {
            TextView errorText = (TextView) activityAddJobSpAreaWork.getSelectedView();
            errorText.setError("");
            errorText.setText("Select Area of work");
            return true;
        } else if (activityAddJobEdDepartment.getText().toString().isEmpty()) {
            activityAddJobEdDepartment.setError("Enter Department");
            return true;
        } else if (activityAddJobEdSelectDesign.getText().toString().isEmpty()) {
            activityAddJobEdSelectDesign.setError("Enter Designation");
            return true;
        } else if (activityAddJobEdContactName.getText().toString().isEmpty()) {
            activityAddJobEdContactName.setError("Enter Contact Name");
            return true;
        } else if (activityAddJobEdContactNumber.getText().toString().isEmpty()) {
            activityAddJobEdContactNumber.setError("Enter Contact Number");
            return true;
        } else if (!(activityAddJobEdContactNumber.getText().toString().length() == 10)) {
            activityAddJobEdContactNumber.setError("Please Enter valid number");
            return true;
        } else if (activityAddJobEdEmail.getText().toString().isEmpty()) {
            activityAddJobEdEmail.setError("Enter Email");
            return true;
        } else if (!(activityAddJobEdEmail.getText().toString().trim().matches(email_validate))) {
            activityAddJobEdEmail.setError("Enter Valid Email");
            return true;
        } else if (activityAddJobTxtDate.getText().toString().isEmpty()) {
            activityAddJobTxtDate.setError("Enter Date");
            return true;
        } else if (activityAddJobEdQualification.getText().toString().isEmpty()) {
            activityAddJobEdQualification.setError("Enter qualification details");
            return true;
        } else if (activityAddJobEdDescription.getText().toString().isEmpty()) {
            activityAddJobEdDescription.setError("Enter Description details");
            return true;
        } else if (!(activityAddJobEdContactNumber.getText().toString().length() == 10)) {
            activityAddJobEdContactNumber.setError("Please Enter valid number");
            return true;
        }

        return false;
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        activityAddJobTxtDate.setText(sdf.format(myCalendar.getTime()));

//        dobyear = myCalendar.get(Calendar.YEAR);
//        dobmonth = myCalendar.get(Calendar.MONTH);
//        dobday = myCalendar.get(Calendar.DAY_OF_MONTH);
//
//        dob = dobday + "/" + dobmonth + "/" + dobyear;
//
//        Log.e("dobdata", dob + "");

    }

    private void getData() {
        final ProgressDialog loading = new ProgressDialog(AddJobActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();


//"user_id"
//        "job_title",
//        "area_of_work",
//        "department",
//        "designation",
//        "qualification",
//        "last_date_apply",
//        "contact_name",
//        "contact_email",
//        "contact_no",
//        "job_description",
        API apiservice = APIClient.getClient().create(API.class);
        Call<JobResponse> call1 = apiservice.add_jobs(strUserid,
                activityAddJobEdJob.getText().toString(),
                strAreaWork,
                activityAddJobEdDepartment.getText().toString(),
                activityAddJobEdSelectDesign.getText().toString(),
                activityAddJobEdQualification.getText().toString(),
                activityAddJobTxtDate.getText().toString().trim(),
                activityAddJobEdContactName.getText().toString(),
                activityAddJobEdEmail.getText().toString(),
                activityAddJobEdContactNumber.getText().toString(),
                activityAddJobEdDescription.getText().toString());
        call1.enqueue(new Callback<JobResponse>() {

            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                loading.dismiss();


                String strResponse = response.body().getResponse();
                String strMsg = response.body().getMessage();
                if (strResponse.equals("Success")) {

                    buildDialogmno(strMsg + "", AddJobActivity.this);
                } else {
                    buildDialogmno(strMsg + "", AddJobActivity.this);
                }
//                areaList = response.body();
//                serSpinner();
//                try {
//                    Log.e("newsArea",response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {

            }
        });
    }

    private void buildDialogmno(String s, AddJobActivity addJobActivity) {
        final Dialog alertDialog = new Dialog(addJobActivity);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(addJobActivity, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(addJobActivity).inflate(R.layout.view_alert_dialog, null);
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

    private void getAreaWork() {
        final ProgressDialog loading = new ProgressDialog(AddJobActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();


        API apiservice = APIClient.getClient().create(API.class);
        Call<List<ModelAreaList>> call1 = apiservice.get_area_of_work();
        call1.enqueue(new Callback<List<ModelAreaList>>() {

            @Override
            public void onResponse(Call<List<ModelAreaList>> call, Response<List<ModelAreaList>> response) {
                loading.dismiss();

                areaList = response.body();
                serSpinner();
//                try {
//                    Log.e("newsArea",response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onFailure(Call<List<ModelAreaList>> call, Throwable t) {

            }
        });
    }

    private void serSpinner() {
        List<String> areaworkList = new ArrayList<>();
        if (areaList.size() > 0) {
            for (int i = 0; i < areaList.size(); i++) {

                if (i == 0) {
                    areaworkList.add("Area of Work");
                    areaworkList.add(areaList.get(i).getProfessionslist());
                } else {

                    areaworkList.add(areaList.get(i).getProfessionslist());
                }


            }

        }
        ArrayAdapter aa11 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, areaworkList);
        aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityAddJobSpAreaWork.setAdapter(aa11);

        activityAddJobSpAreaWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strAreaWork = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void findViews() {

        //imgBack = (ImageView) findViewById(R.id.add_job_img_back);
//        sv = (ScrollView)findViewById(R.id.scrollView);
        activityAddJobEdJob = (EditText) findViewById(R.id.activity_add_job_ed_job);
        activityAddJobSpAreaWork = (Spinner) findViewById(R.id.activity_add_job_sp_area_work);
        activityAddJobEdDepartment = (EditText) findViewById(R.id.activity_add_job_ed_department);
        activityAddJobEdSelectDesign = (EditText) findViewById(R.id.activity_add_job_ed_select_design);
        activityAddJobEdContactName = (EditText) findViewById(R.id.activity_add_job_ed_contact_name);
        activityAddJobEdContactNumber = (EditText) findViewById(R.id.activity_add_job_ed_contact_number);
        activityAddJobEdEmail = (EditText) findViewById(R.id.activity_add_job_ed_email);
        activityAddJobTxtDate = (TextView) findViewById(R.id.activity_add_job_txt_date);
        activityAddJobImageCalender = (ImageView) findViewById(R.id.activity_add_job_image_calender);
        activityAddJobEdQualification = (EditText) findViewById(R.id.activity_add_job_ed_qualification);
        activityAddJobEdDescription = (EditText) findViewById(R.id.activity_add_job_ed_description);
        btnSubmit = (Button) findViewById(R.id.activity_add_job_btn_submit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_home1) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        AndroidUtils.hideSoftKeyboard(AddJobActivity.this);
        finish();
    }
}
