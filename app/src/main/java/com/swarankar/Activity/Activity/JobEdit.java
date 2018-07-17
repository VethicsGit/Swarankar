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

public class JobEdit extends AppCompatActivity {

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
    private Button btnSubmit;
    List<ModelAreaList> areaList = new ArrayList<>();
    Calendar myCalendar;
    String strAreaOfWork;
    String jobid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Job");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(JobEdit.this);
                finish();
            }
        });
//

        Bundle p = getIntent().getExtras();
        jobid = p.getString("jobid");
        String strJobtitle = p.getString("strJobtitle");
        strAreaOfWork = p.getString("strAreaOfWork");
        Log.e("strAreaOfWork", strAreaOfWork);
        String strDepartment = p.getString("strDepartment");
        String strDesignation = p.getString("strDesignation");
        String strContactName = p.getString("strContactName");
        String strContactNumbar = p.getString("strContactNumbar");
        String strContactEmail = p.getString("strContactEmail");
        String strLastdate = p.getString("strLastdate");
        String strQualificatio = p.getString("strQualificatio");
        String strJobDescription = p.getString("strJobDescription");


        findView();
        /*imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        getAreaWork();

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

        activityAddJobImageCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidUtils.hideSoftKeyboard(JobEdit.this);
                DatePickerDialog datePickerDialog = new DatePickerDialog(JobEdit.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            }
        });

        activityAddJobEdJob.setText(strJobtitle);
        activityAddJobEdDepartment.setText(strDepartment);
        activityAddJobEdSelectDesign.setText(strDesignation);
        activityAddJobEdContactName.setText(strContactName);
        activityAddJobEdContactNumber.setText(strContactNumbar);
        activityAddJobEdEmail.setText(strContactEmail);
        activityAddJobTxtDate.setText(strLastdate);
        activityAddJobEdQualification.setText(strQualificatio);
        activityAddJobEdDescription.setText(strJobDescription);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty()) {
                    getData();
                }
            }
        });


    }

    private void getData() {
        final ProgressDialog loading = new ProgressDialog(JobEdit.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");

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

        Log.e("title", activityAddJobEdJob.getText().toString());
        API apiservice = APIClient.getClient().create(API.class);
        Call<JobResponse> call1 = apiservice.update_job(strUserid,
                jobid,
                activityAddJobEdJob.getText().toString(),
                strAreaOfWork,
                activityAddJobEdDepartment.getText().toString(),
                activityAddJobEdSelectDesign.getText().toString(),
                activityAddJobEdQualification.getText().toString(),
                activityAddJobTxtDate.getText().toString().trim(),
                activityAddJobEdContactName.getText().toString(),
                activityAddJobEdEmail.getText().toString(),
                activityAddJobEdContactNumber.getText().toString(),
                activityAddJobEdDescription.getText().toString(), "346711", "04/09/2017");
        call1.enqueue(new Callback<JobResponse>() {

            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                loading.dismiss();


                String strResponse = response.body().getResponse();
                String strMsg = response.body().getMessage();
                if (strResponse.equals("Success")) {

                    buildDialogmno(strMsg + "", JobEdit.this);
                } else {
                    buildDialogmno(strMsg + "", JobEdit.this);
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

    private void buildDialogmno(String s, JobEdit jobEdit) {
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

    private boolean isEmpty() {
        String email_validate = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (activityAddJobEdJob.getText().toString().isEmpty()) {
            activityAddJobEdJob.setError("Enter job name");
            return true;
        } else if (strAreaOfWork.equals("Area of Work")) {
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
            activityAddJobEdEmail.setError("Enter ValidEmail");
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

    private void getAreaWork() {

        final ProgressDialog loading = new ProgressDialog(JobEdit.this);
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

    private void findView() {
        //imgBack = (ImageView) findViewById(R.id.img_back);
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


        for (int j = 0; j < areaworkList.size(); j++) {
            if (areaworkList.get(j).toString().trim().equals(strAreaOfWork)) {
                activityAddJobSpAreaWork.setSelection(j);
            }
        }

        activityAddJobSpAreaWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                strAreaOfWork = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

