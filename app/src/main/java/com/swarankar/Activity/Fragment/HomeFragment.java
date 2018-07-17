package com.swarankar.Activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swarankar.Activity.Activity.Family;
import com.swarankar.Activity.Activity.HomeActivity;
import com.swarankar.Activity.Activity.JobsActivity;
import com.swarankar.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    LinearLayout lvFamily;
    LinearLayout lv_profile, lv_jobs;
    TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        HomeActivity.mTextToolBar.setText("Swarnkar Connect");

        lvFamily = (LinearLayout) view.findViewById(R.id.home_layout_family);
        lv_profile = (LinearLayout) view.findViewById(R.id.lv_profile);
        lv_jobs = (LinearLayout) view.findViewById(R.id.lv_jobs);
        text = (TextView) view.findViewById(R.id.text);

        text.setText("SOCIETY & PERIODICALS");


        lv_profile.setOnClickListener(this);
        lvFamily.setOnClickListener(this);
        lv_jobs.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onClick(View view) {

        if (view == lv_profile) {
            Profile1Fragment m2 = new Profile1Fragment();
            FragmentManager fm1 = getActivity().getSupportFragmentManager();
            FragmentTransaction ft2 = fm1.beginTransaction();
            ft2.replace(R.id.content_frame, m2);
            ft2.addToBackStack("Profile");
            ft2.commit();
        }
        if (view == lvFamily) {
            Intent i = new Intent(getActivity(), Family.class);
            startActivity(i);
        }
        if (view == lv_jobs) {
            Intent i = new Intent(getActivity(), JobsActivity.class);
            startActivity(i);
        }

    }
}
