package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.swarankar.Activity.Model.ProfileDetails.ModelProfileDetails;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFamilyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spinner_add_family, spinner;
    EditText family_member_name, isMarried_name, editText;
    Button submit_family_member, add_child, delete_child;
    ProgressDialog loading;
    String gender, strMaritalStatus = "", have_child = null, hus_wife = null, isMarried = null, query = null;
    List<String> relationList = new ArrayList<>();
    List<String> relationObject = new ArrayList<>();
    List<String> editnameList = new ArrayList<>();
    ArrayList<String> editTextList = new ArrayList<>();
    ArrayList<String> spinnerArrayList = new ArrayList<>();
    ArrayList<LinearLayout> linearArrayList = new ArrayList<>();

    TextView tv_ismarried, tv_havechild;

    HashMap<String, String> childMap = new HashMap<>();
    LinearLayout wife_child_layout, children_layout, add_more_child_layout, isMarried_layout, main_add_family_layout;
    RadioGroup rg_child, rg_isMarried_child;
    RadioButton rb_yes_child, rb_no_child, rb_isMarried_yes_child, rb_isMarried_no_child;

    int rb = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Family");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(this);
        spinner_add_family = (Spinner) findViewById(R.id.spinner_add_family);
        family_member_name = (EditText) findViewById(R.id.edt_family_member_name);
        family_member_name.requestFocus();
        isMarried_name = (EditText) findViewById(R.id.edt_isMarried_name);
        submit_family_member = (Button) findViewById(R.id.btn_family_submit);
        wife_child_layout = (LinearLayout) findViewById(R.id.wife_child_layout);
        main_add_family_layout = (LinearLayout) findViewById(R.id.main_add_family_layout);
        isMarried_layout = (LinearLayout) findViewById(R.id.isMarried_layout);
        children_layout = (LinearLayout) findViewById(R.id.children_layout);
        add_more_child_layout = (LinearLayout) findViewById(R.id.add_more_child_layout);
        tv_havechild = (TextView) findViewById(R.id.tv_have_child);
        tv_ismarried = (TextView) findViewById(R.id.tv_isMarried_have_child);

        rg_child = (RadioGroup) findViewById(R.id.rg_child);
        rb_yes_child = (RadioButton) findViewById(R.id.rb_yes_child);
        rb_no_child = (RadioButton) findViewById(R.id.rb_no_child);
        add_child = (Button) findViewById(R.id.btn_add_child);
        //delete_child = (Button) findViewById(R.id.btn_delete_child);

        rg_isMarried_child = (RadioGroup) findViewById(R.id.rg_isMarried_child);
        rb_isMarried_yes_child = (RadioButton) findViewById(R.id.rb_isMarried_yes_child);
        rb_isMarried_no_child = (RadioButton) findViewById(R.id.rb_isMarried_no_child);

        getData();

        spinner_add_family.setOnItemSelectedListener(this);
        submit_family_member.setOnClickListener(this);

        add_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
                //addViews();
            }
        });
        /*delete_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteViews();
            }
        });*/
    }

    private void addView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.add_more_children_field, null);
        // Add the new row before the add field button.
        add_more_child_layout.addView(rowView, add_more_child_layout.getChildCount());
    }

    public void onDelete(View v) {
        add_more_child_layout.removeView((View) v.getParent());
        if (add_more_child_layout.getChildCount() == 0) {
            rb = 0;
            have_child = "no";
            linearArrayList.clear();
            spinnerArrayList.clear();
            editTextList.clear();
            children_layout.setVisibility(View.GONE);
            add_more_child_layout.removeAllViewsInLayout();
            rb_no_child.setChecked(true);
            rb_yes_child.setChecked(false);
        }
    }

    private void getData() {
        loading = new ProgressDialog(this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        // TODO My Changes
        Call<ModelProfileDetails> call1 = apiService.userInfo1(strUserid);
        call1.enqueue(new Callback<ModelProfileDetails>() {
            @Override
            public void onResponse(Call<ModelProfileDetails> call, final Response<ModelProfileDetails> response) {
                loading.dismiss();
                gender = response.body().getGender() + "";
                strMaritalStatus = response.body().getMaritalStatus();

                List<String> prStrings = new ArrayList<>();
                prStrings.add("Select");
                prStrings.add("Brother");
                prStrings.add("Sister");

                if (strMaritalStatus.equals("Married")) {
                    if (gender.equals("male")) {
                        hus_wife = "Wife";
                        prStrings.add("Wife");
                    } else if (gender.equals("female")) {
                        hus_wife = "Husband";
                        prStrings.add("Husband");
                    }

                } else {
                    wife_child_layout.setVisibility(View.GONE);
                }
                ArrayAdapter aa0 = new ArrayAdapter(AddFamilyActivity.this, android.R.layout.simple_spinner_item, prStrings);
                aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_add_family.setAdapter(aa0);

                Log.e("gender ", gender + strMaritalStatus);

            }

            @Override
            public void onFailure(Call<ModelProfileDetails> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String strRelation = parent.getItemAtPosition(position).toString();

        rb_isMarried_no_child.setChecked(false);
        rb_isMarried_yes_child.setChecked(false);
        rb_no_child.setChecked(false);
        rb_yes_child.setChecked(false);

        if (strRelation.equals("Select")) {
            main_add_family_layout.setVisibility(View.GONE);
            family_member_name.setVisibility(View.GONE);
            submit_family_member.setVisibility(View.GONE);
        } else if (strRelation.equals("Brother")) {
            query = "brother";
            main_add_family_layout.setVisibility(View.VISIBLE);
            family_member_name.setVisibility(View.VISIBLE);
            family_member_name.requestFocus();
            AndroidUtils.showSoftKeyboard(AddFamilyActivity.this, family_member_name);
            submit_family_member.setVisibility(View.VISIBLE);
            isMarried_layout.setVisibility(View.VISIBLE);
            wife_child_layout.setVisibility(View.GONE);
            children_layout.setVisibility(View.GONE);
            tv_ismarried.setText("Is he married?");
            tv_havechild.setText("Does he have a child?");
            isMarried_name.setHint("Enter his wife name");
            isMarried_name.setVisibility(View.GONE);

            int a = position + 1;
            if (relationList.size() >= a) {
                relationList.set(position, strRelation);
            } else {
                relationList.add(0, strRelation);
            }
            family_member_name.setHint("Enter " + strRelation + " Name");
        } else if (strRelation.equals("Sister")) {
            query = "sister";
            main_add_family_layout.setVisibility(View.VISIBLE);
            family_member_name.setVisibility(View.VISIBLE);
            family_member_name.requestFocus();
            AndroidUtils.showSoftKeyboard(AddFamilyActivity.this, family_member_name);
            submit_family_member.setVisibility(View.VISIBLE);
            isMarried_layout.setVisibility(View.VISIBLE);
            wife_child_layout.setVisibility(View.GONE);
            children_layout.setVisibility(View.GONE);
            tv_ismarried.setText("Is she married?");
            tv_havechild.setText("Does she have a child?");
            isMarried_name.setHint("Enter her husband name");
            isMarried_name.setVisibility(View.GONE);

            int a = position + 1;
            if (relationList.size() >= a) {
                relationList.set(position, strRelation);
            } else {
                relationList.add(0, strRelation);
            }
            family_member_name.setHint("Enter " + strRelation + " Name");
        } else if (strRelation.equals("Wife")) {
            query = "wife";
            main_add_family_layout.setVisibility(View.VISIBLE);
            family_member_name.setVisibility(View.VISIBLE);
            family_member_name.requestFocus();
            AndroidUtils.showSoftKeyboard(AddFamilyActivity.this, family_member_name);
            submit_family_member.setVisibility(View.VISIBLE);
            isMarried_layout.setVisibility(View.GONE);
            wife_child_layout.setVisibility(View.VISIBLE);
            children_layout.setVisibility(View.GONE);
            tv_havechild.setText("Do you have a child?");

            int a = position + 1;
            if (relationList.size() >= a) {
                relationList.set(position, strRelation);
            } else {
                relationList.add(0, strRelation);
            }
            family_member_name.setHint("Enter " + strRelation + " Name");
        } else if (strRelation.equals("Husband")) {
            query = "husband";
            main_add_family_layout.setVisibility(View.VISIBLE);
            family_member_name.setVisibility(View.VISIBLE);
            family_member_name.requestFocus();
            AndroidUtils.showSoftKeyboard(AddFamilyActivity.this, family_member_name);
            submit_family_member.setVisibility(View.VISIBLE);
            isMarried_layout.setVisibility(View.GONE);
            wife_child_layout.setVisibility(View.VISIBLE);
            children_layout.setVisibility(View.GONE);
            tv_havechild.setText("Do you have a child?");

            int a = position + 1;
            if (relationList.size() >= a) {
                relationList.set(position, strRelation);
            } else {
                relationList.add(0, strRelation);
            }
            family_member_name.setHint("Enter " + strRelation + " Name");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_family_submit) {
            AndroidUtils.hideSoftKeyboard(AddFamilyActivity.this);
            onSubmitClick();
        } else {
           // AndroidUtils.hideSoftKeyboard(AddFamilyActivity.this);
            /*Intent i = new Intent(this, FamilyListActivity.class);
            startActivity(i);*/
            finish();
        }

    }

    private void addFamilyData(String jsonArray) {

        final ProgressDialog loading = new ProgressDialog(AddFamilyActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = null;
        switch (query) {
            case "brother":
                call1 = apiService.add_brother(strUserid, jsonArray, "0");
                break;
            case "sister":
                call1 = apiService.add_sister(strUserid, jsonArray, "0");
                break;
            case "wife":
                call1 = apiService.add_wife(strUserid, jsonArray, "0");
                break;
            case "husband":
                call1 = apiService.add_husband(strUserid, jsonArray, "0");
                break;
        }

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                loading.dismiss();

                try {
                    String strUrl = response.body().string();
                    JSONObject jsonObject = new JSONObject(strUrl);
                    Log.e("response", strUrl);
                    String res = jsonObject.getString("response");
                    String mes = jsonObject.getString("message");

                    if (res.equals("Success")) {
                        dialog(mes + "", AddFamilyActivity.this);
                    } else {
                        dialog(mes + "", AddFamilyActivity.this);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

    private void dialog(String s, AddFamilyActivity family) {
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
                Intent i = new Intent(AddFamilyActivity.this, FamilyListActivity.class);
                startActivity(i);
                finish();
            }
        });
        alertDialog.show();

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rb_yes_child:
                if (checked) {
                    have_child = "yes";
                    children_layout.setVisibility(View.VISIBLE);
                    if (rb == 0) {
                        addView();
                        rb = 1;
                    }
                }
                break;
            case R.id.rb_no_child:
                if (checked) {
                    rb = 0;
                    have_child = "no";
                    linearArrayList.clear();
                    spinnerArrayList.clear();
                    editTextList.clear();
                    children_layout.setVisibility(View.GONE);
                    add_more_child_layout.removeAllViewsInLayout();
                }
                break;

            case R.id.rb_isMarried_yes_child:
                if (checked) {
                    isMarried = "yes";
                    isMarried_name.setVisibility(View.VISIBLE);
                    wife_child_layout.setVisibility(View.VISIBLE);
                    children_layout.setVisibility(View.GONE);
                }
                break;

            case R.id.rb_isMarried_no_child:
                if (checked) {
                    isMarried = "no";
                    rb_yes_child.setChecked(false);
                    rb_no_child.setChecked(false);
                    isMarried_name.setVisibility(View.GONE);
                    wife_child_layout.setVisibility(View.GONE);
                    children_layout.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void addViews() {
        final List<String> prStrings = new ArrayList<>();
        prStrings.add("Select");
        prStrings.add("Son");
        prStrings.add("Daughter");
        add_more_child_layout.setWeightSum(2);
        spinner = new Spinner(this);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1));
        ArrayAdapter<String> aa0 = new ArrayAdapter<String>(AddFamilyActivity.this, android.R.layout.simple_spinner_item, prStrings);
        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String strRelation = parent.getItemAtPosition(position).toString();
                String relation = null;
                if (strRelation.equals("Select")) {
                    Toast.makeText(AddFamilyActivity.this, "Please select child relation!", Toast.LENGTH_LONG).show();
                } else if (strRelation.equals("Son")) {
                    relation = "son_info";
                    //spinnerArrayList.add(spinner);

                } else if (strRelation.equals("Daughter")) {
                    relation = "daughter_info";
                    //spinnerArrayList.add(spinner);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add_more_child_layout.addView(spinner);

        editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1));
        editText.setHint("Enter Child Name");
        //editTextList.add(editText);
        add_more_child_layout.addView(editText);
        linearArrayList.add(add_more_child_layout);
        Log.e("layout", linearArrayList.toString());
    }

    private void deleteViews() {
        if (linearArrayList.size() > 0) {
            add_more_child_layout.removeViewAt(linearArrayList.size() - 1);
            add_more_child_layout.removeViewAt(linearArrayList.size() - 1);
            linearArrayList.remove(linearArrayList.size() - 1);
            if (linearArrayList.size() == 0) {
                rb = 0;
                have_child = "no";
                linearArrayList.clear();
                spinnerArrayList.clear();
                editTextList.clear();
                children_layout.setVisibility(View.GONE);
                rb_no_child.setChecked(true);
                rb_yes_child.setChecked(false);
            }
        }
        /*spinnerArrayList.remove(spinnerArrayList.size() - 1);
        editTextList.remove(editTextList.size() - 1);
        add_more_child_layout.removeViewAt(linearArrayList.size() - 1);*/
    }

    public void onSubmitClick() {
        final String member_name = family_member_name.getText().toString().trim();
        final String ismarried_name = isMarried_name.getText().toString().trim();

        JSONObject sonObject = new JSONObject();
        JSONObject daughterObject = new JSONObject();
        String editname = null;
        SearchableSpinner spinner = null;
        spinnerArrayList.clear();
        for (int i = 0; i < add_more_child_layout.getChildCount(); i++) {
            View view = add_more_child_layout.getChildAt(i);
            spinner = (SearchableSpinner) view.findViewById(R.id.spinner_add_child);
            EditText editText = (EditText) view.findViewById(R.id.edt_add_more_child);
            editname = editText.getText().toString().trim();
            if (spinner.getSelectedItem().toString().equalsIgnoreCase("select") || editname.isEmpty()) {
                spinnerArrayList.add("false");
            } else {
                if (spinner.getSelectedItem().toString().equalsIgnoreCase("son")) {
                    try {
                        sonObject.put("" + i, editname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (spinner.getSelectedItem().toString().equalsIgnoreCase("daughter")) {
                    try {
                        daughterObject.put("" + i, editname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                spinnerArrayList.add("true");
            }
        }

        if (family_member_name.getVisibility() == View.VISIBLE && member_name.isEmpty()) {
            family_member_name.setError("Please Enter Name!");
        } else if (isMarried_layout.getVisibility() == View.VISIBLE && isMarried == null) {
            Toast.makeText(this, "Please select married option!", Toast.LENGTH_LONG).show();
        } else if (isMarried_layout.getVisibility() == View.VISIBLE && isMarried.equalsIgnoreCase("yes") && ismarried_name.isEmpty()) {
            isMarried_name.setError("Please Enter Name!");
        } else if (wife_child_layout.getVisibility() == View.VISIBLE && have_child == null) {
            Toast.makeText(this, "Please select child option!", Toast.LENGTH_LONG).show();
        } else {
            if (spinnerArrayList.contains("false")) {
                Toast.makeText(this, "Fill all child details correctly!", Toast.LENGTH_SHORT).show();
            } else {
                JSONObject nameObject = new JSONObject();
                try {
                    nameObject.put("name", member_name);
                    JSONObject memberObject = new JSONObject();
                    JSONObject childObject = new JSONObject();

                    if (isMarried_layout.getVisibility() == View.VISIBLE && isMarried.equalsIgnoreCase("yes")) {
                        //nameObject.put("isMarried", "yes");
                        Log.e("ismarried", isMarried);
                        switch (query) {
                            case "brother":
                                nameObject.put("wife_name", ismarried_name);
                                if (have_child.equalsIgnoreCase("yes")) {
                                    //memberObject.put("haveChild", "yes");
                                    childObject.put("son", sonObject);
                                    childObject.put("daughter", daughterObject);

                                    nameObject.put("children_info", childObject);
                                } else {
                                    //nameObject.put("haveChild", "no");
                                }
                                //nameObject.put("wife_info", memberObject);
                                break;

                            case "sister":
                                nameObject.put("husband_name", ismarried_name);
                                if (have_child.equalsIgnoreCase("yes")) {
                                    //memberObject.put("haveChild", "yes");
                                    childObject.put("son", sonObject);
                                    childObject.put("daughter", daughterObject);
                                    nameObject.put("children_info", childObject);
                                } else {
                                    //nameObject.put("haveChild", "no");
                                }
                                //nameObject.put("husband_info", memberObject);
                                break;

                        }

                    } else if (isMarried_layout.getVisibility() == View.VISIBLE && isMarried.equalsIgnoreCase("no")) {
                        Log.e("ismarried1", isMarried);

                    } else if (isMarried_layout.getVisibility() == View.GONE) {
                        switch (query) {

                            case "wife":
                                //memberObject.put("husband_name", ismarried_name);
                                if (have_child.equalsIgnoreCase("yes")) {
                                    //memberObject.put("haveChild", "yes");
                                    childObject.put("son", sonObject);
                                    childObject.put("daughter", daughterObject);

                                    nameObject.put("children_info", childObject);
                                } else {
                                    //nameObject.put("haveChild", "no");
                                }
                                //nameObject.put("wife_info", memberObject);
                                break;

                            case "husband":
                                // memberObject.put("wife_name", ismarried_name);
                                if (have_child.equalsIgnoreCase("yes")) {
                                    //memberObject.put("haveChild", "yes");
                                    childObject.put("son", sonObject);
                                    childObject.put("daughter", daughterObject);

                                    nameObject.put("children_info", childObject);
                                } else {
                                    //nameObject.put("haveChild", "no");
                                }
                                //nameObject.put("wife_info", memberObject);
                                break;
                        }
                        // nameObject.put("isMarried", "no");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                relationObject.add(nameObject.toString());
                Log.e("relation", relationObject.toString());
                /*editTextList.clear();
                spinnerArrayList.clear();*/

                addFamilyData(relationObject.toString());
            }

        }
    }

    @Override
    public void onBackPressed() {
        AndroidUtils.hideSoftKeyboard(this);
        Intent i = new Intent(this, FamilyListActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
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
 /*Log.e("editname", sonObject.toString());

         for (int i = 0; i < editTextList.size(); i++) {
        if (editTextList.size() == spinnerArrayList.size()) {
        strings[i] = editTextList.get(i).getText().toString();
        childMap.put(spinnerArrayList.get(i).getSelectedItem().toString(), editTextList.get(i).getText().toString());
        Log.e("edittext", strings[i]);
        Log.e("chilDMap", childMap.toString());
        }
        }

        String[] sstrings = new String[spinnerArrayList.size()];

        for (int i = 0; i < spinnerArrayList.size(); i++) {
        sstrings[i] = spinnerArrayList.get(i).getSelectedItem().toString();

        if (sstrings[i].equalsIgnoreCase("Son")) {
        try {
        for (int j = 0; j == i; j++) {
        sonObject.put("" + j, editTextList.get(i).getText().toString());
        }
        } catch (JSONException e) {
        e.printStackTrace();
        }
        }
        if (sstrings[i].equalsIgnoreCase("Daughter")) {
        try {
        daughterObject.put("" + i, editTextList.get(i).getText().toString());
        } catch (JSONException e) {
        e.printStackTrace();
        }
        }
        Log.e("spinner", sstrings[i]);
        }

        *//*for (String sd : sstrings) {
            if (sd.equalsIgnoreCase("son")) {
                try {
                    for (int j = 0; j == spinnerArrayList.size(); j++) {
                        sonObject.put("" + j, editTextList.get(i).getText().toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (sd.equalsIgnoreCase("Daughter")) {
                try {
                    for (int j = 0; j == spinnerArrayList.size(); j++) {
                        daughterObject.put("" + j, editTextList.get(j).getText().toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
*//*
*/