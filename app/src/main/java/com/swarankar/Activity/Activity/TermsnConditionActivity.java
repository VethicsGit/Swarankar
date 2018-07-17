package com.swarankar.Activity.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.swarankar.R;

public class TermsnConditionActivity extends AppCompatActivity implements View.OnClickListener {
    WebView tvTermsandConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsn_condition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Terms and Conditions");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(this);

        tvTermsandConditions = (WebView) findViewById(R.id.tv_terms_condition);
        tvTermsandConditions.loadData(getResources().getString(R.string.terms_condidtions), "text/html", null);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
