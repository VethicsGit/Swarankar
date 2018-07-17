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

public class UpdateFamily1 extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner_add_family, spinner;
    EditText family_member_name, isMarried_name, editText;
    Button submit_family_member, add_child;//delete_child;
    ProgressDialog loading;
    String gender, strMaritalStatus = "", have_child = null, hus_wife = null, isMarried = null, query = null, id;
    List<String> relationList = new ArrayList<>();
    List<String> relationObject = new ArrayList<>();
    ArrayList<String> editTextList = new ArrayList<>();
    ArrayList<String> spinnerArrayList = new ArrayList<>();
    List<String> editnameList = new ArrayList<>();
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
        setContentView(R.layout.activity_update_family1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Family");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(this);

        spinner_add_family = (Spinner) findViewById(R.id.spinner_add_family);
        family_member_name = (EditText) findViewById(R.id.edt_family_member_name);
        family_member_name.requestFocus();
        AndroidUtils.showSoftKeyboard(UpdateFamily1.this, family_member_name);
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

        Intent i = getIntent();

        List<String> prStrings1 = new ArrayList<>();
        prStrings1.add("Select");
        prStrings1.add("Brother");
        prStrings1.add("Sister");
        prStrings1.add("Wife");
        prStrings1.add("Husband");


        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prStrings1);
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add_family.setAdapter(aa1);

        for (int p = 0; p < prStrings1.size(); p++) {
            if (prStrings1.get(p).equalsIgnoreCase(i.getStringExtra("relation"))) {
                spinner_add_family.setSelection(p);
            }
        }

        spinner_add_family.setEnabled(false);
        id = i.getStringExtra("id");
        family_member_name.setText(i.getStringExtra("name"));

        if (i.getStringExtra("relation").equalsIgnoreCase("brother")) {
            query = "brother";
            tv_ismarried.setText("Is he married?");
            tv_havechild.setText("Does he have a child?");
            isMarried_name.setHint("Enter his wife name");
        } else if (i.getStringExtra("relation").equalsIgnoreCase("sister")) {
            query = "sister";
            tv_ismarried.setText("Is she married?");
            tv_havechild.setText("Does she have a child?");
            isMarried_name.setHint("Enter her husband name");
        } else if (i.getStringExtra("relation").equalsIgnoreCase("wife")) {
            query = "wife";
            isMarried_layout.setVisibility(View.GONE);
            tv_havechild.setText("Do you have a child?");
        } else if (i.getStringExtra("relation").equalsIgnoreCase("husband")) {
            query = "husband";
            isMarried_layout.setVisibility(View.GONE);
            tv_havechild.setText("Do you have a child?");
        }

        if (i.getStringExtra("relation").equalsIgnoreCase("brother") || i.getStringExtra("relation").equalsIgnoreCase("sister")) {
            if (i.getStringExtra("partnerName") == null || i.getStringExtra("partnerName").equalsIgnoreCase("null")) {
                isMarried = "no";
                rb_isMarried_yes_child.setChecked(false);
                rb_isMarried_no_child.setChecked(true);
                isMarried_name.setVisibility(View.GONE);
                wife_child_layout.setVisibility(View.GONE);
                children_layout.setVisibility(View.GONE);
            } else {
                isMarried = "yes";
                rb_isMarried_yes_child.setChecked(true);
                rb_isMarried_no_child.setChecked(false);
                isMarried_name.setText(i.getStringExtra("partnerName"));
            }
        }


        if (i.getStringArrayListExtra("sonList") == null && i.getStringArrayListExtra("daughterList") == null) {
            have_child = "no";
            rb_yes_child.setChecked(false);
            rb_no_child.setChecked(true);
            children_layout.setVisibility(View.GONE);
        } else {
            have_child = "yes";
            rb_yes_child.setChecked(true);
            rb_no_child.setChecked(false);
            children_layout.setVisibility(View.VISIBLE);
        }

        if (i.getStringArrayListExtra("sonList") != null) {
            ArrayList<String> sonList = i.getStringArrayListExtra("sonList");
            for (int j = 0; j < sonList.size(); j++) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.add_more_children_field, null);

                final List<String> prStrings = new ArrayList<>();
                prStrings.add("Select");
                prStrings.add("Son");
                prStrings.add("Daughter");
                Spinner spinner = (Spinner) rowView.findViewById(R.id.spinner_add_child);
                ArrayAdapter<String> aa0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prStrings);
                aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(aa0);

                for (int p = 0; p < prStrings.size(); p++) {
                    if (prStrings.get(p).equalsIgnoreCase("Son")) {
                        spinner.setSelection(p);
                    }
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String strRelation = parent.getItemAtPosition(position).toString();
                        String relation = null;
                        if (strRelation.equals("Select")) {
                            Toast.makeText(UpdateFamily1.this, "Please select child relation!", Toast.LENGTH_LONG).show();
                        } else if (strRelation.equals("Son")) {
                            relation = "son_info";
                            //spinnerArrayList.add(spinner);

                        } else if (strRelation.equals("Daughter")) {
                            relation = "daughter_info";
                            // spinnerArrayList.add(spinner);

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                EditText editText = (EditText) rowView.findViewById(R.id.edt_add_more_child);
                editText.setText(sonList.get(j));
                // Add the new row before the add field button.
                add_more_child_layout.addView(rowView, add_more_child_layout.getChildCount() - 1);

            }

        }

        if (i.getStringArrayListExtra("daughterList") != null) {
            ArrayList<String> daughterList = i.getStringArrayListExtra("daughterList");
            for (int j = 0; j < daughterList.size(); j++) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.add_more_children_field, null);

                final List<String> prStrings = new ArrayList<>();
                prStrings.add("Select");
                prStrings.add("Son");
                prStrings.add("Daughter");
                Spinner spinner = (Spinner) rowView.findViewById(R.id.spinner_add_child);
                ArrayAdapter<String> aa0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prStrings);
                aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(aa0);

                for (int p = 0; p < prStrings.size(); p++) {
                    if (prStrings.get(p).equalsIgnoreCase("Daughter")) {
                        spinner.setSelection(p);
                    }
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String strRelation = parent.getItemAtPosition(position).toString();
                        String relation = null;
                        if (strRelation.equals("Select")) {
                            Toast.makeText(UpdateFamily1.this, "Please select child relation!", Toast.LENGTH_LONG).show();
                        } else if (strRelation.equals("Son")) {
                            relation = "son_info";
                            //spinnerArrayList.add(spinner);

                        } else if (strRelation.equals("Daughter")) {
                            relation = "daughter_info";
                            // spinnerArrayList.add(spinner);

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                EditText editText = (EditText) rowView.findViewById(R.id.edt_add_more_child);
                editText.setText(daughterList.get(j));
                // Add the new row before the add field button.
                add_more_child_layout.addView(rowView, add_more_child_layout.getChildCount() - 1);

            }

        }
        submit_family_member.setOnClickListener(this);
        add_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });
        /*delete_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteViews();
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_family_submit) {
            AndroidUtils.hideSoftKeyboard(this);
            onSubmitClick();
        } else {
            AndroidUtils.hideSoftKeyboard(this);
            Intent i = new Intent(this, FamilyListActivity.class);
            startActivity(i);
            finish();
        }
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
                ArrayAdapter aa0 = new ArrayAdapter(UpdateFamily1.this, android.R.layout.simple_spinner_item, prStrings);
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
        ArrayAdapter<String> aa0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prStrings);
        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String strRelation = parent.getItemAtPosition(position).toString();
                String relation = null;
                if (strRelation.equals("Select")) {
                    Toast.makeText(UpdateFamily1.this, "Please select child relation!", Toast.LENGTH_LONG).show();
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
            add_more_child_layout.removeViewAt(linearArrayList.size());
            add_more_child_layout.removeViewAt(linearArrayList.size());
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
        } else if (isMarried == "yes" && ismarried_name.isEmpty()) {
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

                        Log.e(isMarried, isMarried);

                        switch (query) {
                            case "brother":
                                nameObject.put("id", id);
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
                                nameObject.put("id", id);
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
                        nameObject.put("id", id);
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

    private void addFamilyData(String jsonArray) {

        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient(


        ).create(API.class);
        Call<ResponseBody> call1 = null;
        switch (query) {
            case "brother":
                call1 = apiService.add_brother(strUserid, jsonArray, "1");
                break;
            case "sister":
                call1 = apiService.add_sister(strUserid, jsonArray, "1");
                break;
            case "wife":
                call1 = apiService.add_wife(strUserid, jsonArray, "1");
                break;
            case "husband":
                call1 = apiService.add_husband(strUserid, jsonArray, "1");
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
                        dialog(mes + "", UpdateFamily1.this);
                    } else {
                        dialog(mes + "", UpdateFamily1.this);
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

    private void dialog(String s, UpdateFamily1 family) {
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
                Intent i = new Intent(UpdateFamily1.this, FamilyListActivity.class);
                startActivity(i);
                finish();
            }
        });
        alertDialog.show();

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
