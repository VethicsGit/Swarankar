package com.swarankar.Activity.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.R;

public class JobDetailActivity extends AppCompatActivity {

    int position;
    private Toolbar toolbar1;
    //private ImageView editProfileBack11;
    private TextView textJobtitle;
    private TextView textOrganization;
    private TextView textDesignation;
    private TextView textId;
    private TextView textQualification;
    private TextView textLastdateofappy;
    private TextView textApply;
    private TextView textAppyAndContact;
    private TextView textDescription;
    private TextView textMinuteago;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Details");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AndroidUtils.hideSoftKeyboard(JobDetailActivity.this);
                finish();
            }
        });
        position = getIntent().getIntExtra("position", 0);
        findViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void findViews() {
        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        //editProfileBack11 = (ImageView) findViewById(R.id.edit_profile_back11);
        textJobtitle = (TextView) findViewById(R.id.text_jobtitle);
        textOrganization = (TextView) findViewById(R.id.text_organization);
        textDesignation = (TextView) findViewById(R.id.text_designation);
        textId = (TextView) findViewById(R.id.text_id);
        textQualification = (TextView) findViewById(R.id.text_qualification);
        textLastdateofappy = (TextView) findViewById(R.id.text_lastdateofappy);
        textApply = (TextView) findViewById(R.id.text_apply);
        textAppyAndContact = (TextView) findViewById(R.id.text_appy_and_contact);
        textDescription = (TextView) findViewById(R.id.text_description);
        textMinuteago = (TextView) findViewById(R.id.text_minuteago);

        /*editProfileBack11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        SetData(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetData(int position) {

        textApply.setText("Apply & contact :");
        textJobtitle.setText(AndroidUtils.wordFirstCap(JobsActivity.arStrings.get(position).getJobTitle()));
        textOrganization.setText(AndroidUtils.wordFirstCap(JobsActivity.arStrings.get(position).getOrganization()));
        textDesignation.setText(AndroidUtils.wordFirstCap(JobsActivity.arStrings.get(position).getDesignation()));
        textId.setText(JobsActivity.arStrings.get(position).getId());
        textQualification.setText(AndroidUtils.wordFirstCap(JobsActivity.arStrings.get(position).getReqQualification()));
        textLastdateofappy.setText(JobsActivity.arStrings.get(position).getLastDateApply());
        textAppyAndContact.setText(AndroidUtils.wordFirstCap(JobsActivity.arStrings.get(position).getContactName()) + "," + AndroidUtils.wordFirstCap(JobsActivity.arStrings.get(position).getContactEmail() + "," + JobsActivity.arStrings.get(position).getContactNo()));
        textDescription.setText(AndroidUtils.wordFirstCap(JobsActivity.arStrings.get(position).getJobDescription()));
        textMinuteago.setText(JobsActivity.arStrings.get(position).getCreatedDate());

//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            Date dt = formatter.parse(JobsActivity.arStrings.get(position).getCreatedDate());
//            CharSequence output = DateUtils.getRelativeTimeSpanString (dt.getTime());
//            textMinuteago.setText(output.toString());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            textMinuteago.setText("");
//        }
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
