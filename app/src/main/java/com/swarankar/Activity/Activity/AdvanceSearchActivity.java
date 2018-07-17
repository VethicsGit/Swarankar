package com.swarankar.Activity.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.swarankar.Activity.Model.ModelCity.ModelCity;
import com.swarankar.Activity.Model.ModelCountry.ModelCountry;
import com.swarankar.Activity.Model.ModelGotra.ModelGotra;
import com.swarankar.Activity.Model.ModelProfession.ModelProfessionSociety;
import com.swarankar.Activity.Model.ModelProfession.Profession;
import com.swarankar.Activity.Model.ModelState.ModelState;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.PrefixEditText;
import com.swarankar.Activity.adapter.AdvanceSearchFieldAdapter;
import com.swarankar.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdvanceSearchActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAdvanceSearch, btnAddFilter, btnClearFilter;
    LinearLayout llDynamicSearch;
    ArrayList<String> firstNameList = new ArrayList<>();
    ArrayList<String> lastNameList = new ArrayList<>();
    ArrayList<String> mobileNumberList = new ArrayList<>();
    ArrayList<String> emailList = new ArrayList<>();
    ArrayList<String> memberIdList = new ArrayList<>();
    ArrayList<String> subcasteList = new ArrayList<>();
    ArrayList<String> gotraSelfList = new ArrayList<>();
    ArrayList<String> gotraMotherList = new ArrayList<>();
    ArrayList<String> maritalstatusList = new ArrayList<>();
    ArrayList<String> dobList = new ArrayList<>();
    ArrayList<String> professionList = new ArrayList<>();
    ArrayList<String> professionOrgNameList = new ArrayList<>();
    ArrayList<String> professionStatusList = new ArrayList<>();
    ArrayList<String> professionIndustryList = new ArrayList<>();
    ArrayList<String> professionDesignationList = new ArrayList<>();
    ArrayList<String> officeLocalityList = new ArrayList<>();
    ArrayList<String> educationalQualificationList = new ArrayList<>();
    ArrayList<String> educationalInstituteList = new ArrayList<>();
    ArrayList<String> statusEducationList = new ArrayList<>();
    ArrayList<String> areaStudyList = new ArrayList<>();
    ArrayList<String> resLocalityList = new ArrayList<>();
    ArrayList<String> resWardList = new ArrayList<>();
    ArrayList<String> resConstituencyList = new ArrayList<>();
    ArrayList<String> resVillageList = new ArrayList<>();
    ArrayList<String> resTehsilList = new ArrayList<>();
    ArrayList<String> permanentLocalityList = new ArrayList<>();
    ArrayList<String> permanentWardList = new ArrayList<>();
    ArrayList<String> permanentConstituencyList = new ArrayList<>();
    ArrayList<String> permanentVillageList = new ArrayList<>();
    ArrayList<String> permanentTehsilList = new ArrayList<>();
    ArrayList<String> officeCountryList = new ArrayList<>();
    ArrayList<String> officeStateList = new ArrayList<>();
    ArrayList<String> officeDistrictList = new ArrayList<>();
    ArrayList<String> residenceCountryList = new ArrayList<>();
    ArrayList<String> residenceStateList = new ArrayList<>();
    ArrayList<String> residenceDistrictList = new ArrayList<>();
    ArrayList<String> permanentCountryList = new ArrayList<>();
    ArrayList<String> permanentStateList = new ArrayList<>();
    ArrayList<String> permanentDistrictList = new ArrayList<>();
    private Calendar myCalendar;
    private String selectedDOB;
    String selectedCountry, selectedState, selectedDistrict;

    private ArrayList<ModelCountry> CountryList = new ArrayList<>();
    private ArrayList<ModelState> StateList = new ArrayList<>();
    private ArrayList<ModelCity> CityList = new ArrayList<>();
    private String selectedGotraSelf, selectedSubcaste, selectedGotraMother, selectedProfession, selectedMaritalStatus, selectedProfessionStatus, selectedProfessionIndustry, selectedAreaStudy, selecteEducationStatus;
    private ArrayList<Profession> professionlist = new ArrayList<>();
    private ArrayList<String> CastList = new ArrayList<>();
    private ArrayList<ModelProfessionSociety> ProfessionIndustryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Advance Search");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AndroidUtils.hideSoftKeyboard(AdvanceSearchActivity.this);
                finish();
            }
        });

        btnAddFilter = (Button) findViewById(R.id.btn_add_filter);
        btnClearFilter = (Button) findViewById(R.id.btn_clear_filter);
        btnAdvanceSearch = (Button) findViewById(R.id.btn_advance_search);
        llDynamicSearch = (LinearLayout) findViewById(R.id.ll_dynamic_search);
        btnAddFilter.setOnClickListener(this);
        btnAdvanceSearch.setOnClickListener(this);
        btnClearFilter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnAddFilter) {
            dialog();
        } else if (v == btnAdvanceSearch) {
            submitSearch();
        } else if (v == btnClearFilter) {
            llDynamicSearch.removeAllViews();
            btnClearFilter.setVisibility(View.GONE);
        }
    }

    private void dialog() {

        final Dialog alertDialog = new Dialog(AdvanceSearchActivity.this);
        alertDialog.setCanceledOnTouchOutside(false);
        //alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setTitle("Search by");
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(AdvanceSearchActivity.this, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(AdvanceSearchActivity.this).inflate(R.layout.advance_search_dialog, null);
        ListView lvAdvanceSearchFields;

        lvAdvanceSearchFields = (ListView) rootView.findViewById(R.id.lvAdvanceSearchFields);
        final List<String> sflist = Arrays.asList(getResources().getStringArray(R.array.advance_search_fields));
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_list_item_1, sflist);
        AdvanceSearchFieldAdapter advanceSearchFieldAdapter = new AdvanceSearchFieldAdapter(AdvanceSearchActivity.this, sflist);
        lvAdvanceSearchFields.setAdapter(advanceSearchFieldAdapter);
        alertDialog.setContentView(rootView);

        lvAdvanceSearchFields.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                alertDialog.dismiss();
                /*if (llDynamicSearch.getChildCount() > 0) {*/
                btnClearFilter.setVisibility(View.VISIBLE);
                /*} else {
                    btnClearFilter.setVisibility(View.GONE);
                }*/
                if (sflist.get(position).equalsIgnoreCase("First Name")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llfname);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    et.setBackgroundColor(Color.parseColor("#ffffff"));
                    et.setId(R.id.firstname);

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Last Name")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llname);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    et.setId(R.id.lastname);

                    et.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(AdvanceSearchActivity.this, sflist.get(position), Toast.LENGTH_SHORT).show();
                        }
                    });
                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Mobile Number")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llmobile);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    et.setId(R.id.mobilenumber);

                    et.setInputType(InputType.TYPE_CLASS_PHONE);
                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Email")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llemail);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.email);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Member ID")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llmemberid);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    PrefixEditText et = new PrefixEditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    et.setTag("SW-");
                    et.setInputType(InputType.TYPE_CLASS_NUMBER);
                    et.setId(R.id.memberid);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Profession Organization Name")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llprofessionorganization);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.professionorganizationname);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Profession Designation")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llprofessiondesignation);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.professiondesignation);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Office Locality/Area")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llofficelocality);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.officelocality);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Educational Qualification")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.lleducationqualification);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.educationalqulification);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Educational Name of Institution")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.lleducationinstitute);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.educationalinstitute);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Residential Locality/Area")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llreslocality);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.residencelocality);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Residential Ward No.")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llresward);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.residenceward);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Residential Constituency")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llresconstituency);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.residenceconstituency);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Residential Village")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llresvillage);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.residencevillage);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Residential Tehsil")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llrestehsil);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.residencetehsil);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Permanent Address Locality/Area")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llperlocality);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.permanentlocality);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Permanent Address Ward No.")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llperward);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.permanentward);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Permanent Address Constituency")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llperconstituency);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.permanentconstituency);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Permanent Address Village")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llpervillage);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.permanentvillage);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Permanent Address Tehsil")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llpertehsil);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));

                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    EditText et = new EditText(AdvanceSearchActivity.this);
                    et.setHint(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    et.setId(R.id.permanenttehsil);
                    et.setHintTextColor(Color.parseColor("#cccccc"));
                    et.setBackgroundColor(Color.parseColor("#ffffff"));

                    layout.addView(tv);
                    layout.addView(et);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Date of Birth")) {
                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText("Select " + sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#cccccc"));
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectDOB(tv);
                        }
                    });

                    llDynamicSearch.addView(tv);
                } else if (sflist.get(position).equalsIgnoreCase("Sub Caste")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llsubcaste);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }
                    });
                    SearchableSpinner spnrSubcaste = new SearchableSpinner(AdvanceSearchActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(0, 10, 0, 10);
                    spnrSubcaste.setLayoutParams(p);
                    spnrSubcaste.setTitle("Select " + sflist.get(position));
                    spnrSubcaste.setId(R.id.subcaste);
//                    spnrSubcaste.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    getSubcaste(spnrSubcaste);
                    layout.addView(tv);
                    layout.addView(spnrSubcaste);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Gotra (Self)")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llgotraself);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    SearchableSpinner spnrSubcaste = new SearchableSpinner(AdvanceSearchActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(0, 10, 0, 10);
                    spnrSubcaste.setLayoutParams(p);
                    spnrSubcaste.setTitle("Select " + sflist.get(position));
                    spnrSubcaste.setId(R.id.gotraself);
//                    spnrSubcaste.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    getGotraSelf(spnrSubcaste);
                    layout.addView(tv);
                    layout.addView(spnrSubcaste);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Gotra (Mother)")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llgotramother);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    tv.setTextColor(Color.parseColor("#000000"));
                    SearchableSpinner spnrSubcaste = new SearchableSpinner(AdvanceSearchActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(0, 10, 0, 10);
                    spnrSubcaste.setLayoutParams(p);
                    spnrSubcaste.setTitle("Select " + sflist.get(position));
                    spnrSubcaste.setId(R.id.gotramother);
//                    spnrSubcaste.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    getGotraMother(spnrSubcaste);
                    layout.addView(tv);
                    layout.addView(spnrSubcaste);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Marital Status")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llmaritalstatus);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    SearchableSpinner spnrSubcaste = new SearchableSpinner(AdvanceSearchActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(0, 10, 0, 10);
                    spnrSubcaste.setLayoutParams(p);
                    spnrSubcaste.setTitle("Select " + sflist.get(position));
                    spnrSubcaste.setId(R.id.maritalstatus);
//                    spnrSubcaste.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    List<String> msList = new ArrayList<>();
                    msList.add("Select Marital Status");
                    msList.add("Married");
                    msList.add("Unmarried");
                    ArrayAdapter<String> aa0 = new ArrayAdapter<>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, msList);
                    aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnrSubcaste.setAdapter(aa0);

                    spnrSubcaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedMaritalStatus = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    layout.addView(tv);
                    layout.addView(spnrSubcaste);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Profession")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llprofession);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    SearchableSpinner spnrSubcaste = new SearchableSpinner(AdvanceSearchActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(0, 10, 0, 10);
                    spnrSubcaste.setLayoutParams(p);
                    spnrSubcaste.setTitle("Select " + sflist.get(position));
                    spnrSubcaste.setId(R.id.profession1);
//                    spnrSubcaste.setBackground(getResources().getDrawable(R.drawable.spinner_background));
                    getProfession(spnrSubcaste);
                    layout.addView(tv);
                    layout.addView(spnrSubcaste);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Profession Status")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llprofessionstatus);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    SearchableSpinner spnrSubcaste = new SearchableSpinner(AdvanceSearchActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(0, 10, 0, 10);
                    spnrSubcaste.setLayoutParams(p);
                    spnrSubcaste.setTitle("Select " + sflist.get(position));
                    spnrSubcaste.setId(R.id.professionstatus);
//                    spnrSubcaste.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    List<String> professionstatus = new ArrayList<>();
                    professionstatus.add("Select Profession Status");
                    professionstatus.add("Working");
                    professionstatus.add("Retired");
                    professionstatus.add("Others");

                    ArrayAdapter aa0 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, professionstatus);
                    aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnrSubcaste.setAdapter(aa0);

                    spnrSubcaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedProfessionStatus = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    layout.addView(tv);
                    layout.addView(spnrSubcaste);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Status of Education")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llstatuseducational);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    SearchableSpinner spnrSubcaste = new SearchableSpinner(AdvanceSearchActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(0, 10, 0, 10);
                    spnrSubcaste.setLayoutParams(p);
                    spnrSubcaste.setTitle("Select " + sflist.get(position));
                    spnrSubcaste.setId(R.id.statuseducational);
//                    spnrSubcaste.setBackground(getResources().getDrawable(R.drawable.spinner_background));
                    List<String> educationstatus = new ArrayList<>();
                    educationstatus.add("Select Education Status");
                    educationstatus.add("Completed");
                    educationstatus.add("Pursuing");
                    educationstatus.add("Incomplete");

                    ArrayAdapter aa0 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, educationstatus);
                    aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnrSubcaste.setAdapter(aa0);

                    spnrSubcaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selecteEducationStatus = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    layout.addView(tv);
                    layout.addView(spnrSubcaste);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Area of Study")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llareastudy);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    SearchableSpinner spnrSubcaste = new SearchableSpinner(AdvanceSearchActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(0, 10, 0, 10);
                    spnrSubcaste.setLayoutParams(p);
                    spnrSubcaste.setTitle("Select " + sflist.get(position));
                    spnrSubcaste.setId(R.id.areastudy);
//                    spnrSubcaste.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    List<String> areastudy = new ArrayList<>();
                    areastudy.add("Select Area of Study");
                    areastudy.add("Architecture/Design");
                    areastudy.add("Arts and Humanities");
                    areastudy.add("Basics Sciences");
                    areastudy.add("Business & Commerce");
                    areastudy.add("Diploma/Certification Courses");
                    areastudy.add("Education");
                    areastudy.add("Engineering");
                    areastudy.add("Research");
                    areastudy.add("Hotel Management");
                    areastudy.add("Information Sciences");
                    areastudy.add("Journalism & Communication");
                    areastudy.add("Law");
                    areastudy.add("Management");
                    areastudy.add("Medical Sciences");
                    areastudy.add("Planning & Design");
                    areastudy.add("Others");

                    ArrayAdapter aa0 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, areastudy);
                    aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnrSubcaste.setAdapter(aa0);

                    spnrSubcaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedAreaStudy = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    layout.addView(tv);
                    layout.addView(spnrSubcaste);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Profession Industry")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.llprofessionindustry);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });
                    SearchableSpinner spnrSubcaste = new SearchableSpinner(AdvanceSearchActivity.this);
                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.setMargins(0, 10, 0, 10);
                    spnrSubcaste.setLayoutParams(p);
                    spnrSubcaste.setTitle("Select " + sflist.get(position));
                    spnrSubcaste.setId(R.id.professionindustry);
//                    spnrSubcaste.setBackground(getResources().getDrawable(R.drawable.spinner_background));
                    getProfessionIndustry(spnrSubcaste);
                    layout.addView(tv);
                    layout.addView(spnrSubcaste);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Office Location")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.officelocation);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });

                    SearchableSpinner spnrOCountry = new SearchableSpinner(AdvanceSearchActivity.this);
                    spnrOCountry.setLayoutParams(cp);
                    spnrOCountry.setTitle("Select Office Country");
                    spnrOCountry.setId(R.id.officecountry);
//                    spnrOCountry.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    SearchableSpinner spnrOState = new SearchableSpinner(AdvanceSearchActivity.this);
                    spnrOState.setLayoutParams(cp);
                    spnrOState.setTitle("Select Office State");
                    spnrOState.setId(R.id.officestate);
//                    spnrOState.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    SearchableSpinner spnrOCity = new SearchableSpinner(AdvanceSearchActivity.this);
                    spnrOCity.setLayoutParams(cp);
                    spnrOCity.setTitle("Select Office District");
                    spnrOCity.setId(R.id.officedistrict);
//                    spnrOCity.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    getOfficeCountry(spnrOCountry, spnrOState, spnrOCity);

                    layout.addView(tv);
                    layout.addView(spnrOCountry);
                    layout.addView(spnrOState);
                    layout.addView(spnrOCity);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Residential Location")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.residencelocation);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });

                    SearchableSpinner spnrRCountry = new SearchableSpinner(AdvanceSearchActivity.this);
                    spnrRCountry.setLayoutParams(cp);
                    spnrRCountry.setTitle("Select Residence Country");
                    spnrRCountry.setId(R.id.residencecountry);
//                    spnrRCountry.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    SearchableSpinner spnrRState = new SearchableSpinner(AdvanceSearchActivity.this);
                    spnrRState.setLayoutParams(cp);
                    spnrRState.setTitle("Select Residence State");
                    spnrRState.setId(R.id.residencestate);
//                    spnrRState.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    SearchableSpinner spnrRCity = new SearchableSpinner(AdvanceSearchActivity.this);
                    spnrRCity.setLayoutParams(cp);
                    spnrRCity.setTitle("Select Residence District");
                    spnrRCity.setId(R.id.residencedistrict);
//                    spnrRCity.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    getRCountry(spnrRCountry, spnrRState, spnrRCity);

                    layout.addView(tv);
                    layout.addView(spnrRCountry);
                    layout.addView(spnrRState);
                    layout.addView(spnrRCity);
                    llDynamicSearch.addView(layout);
                } else if (sflist.get(position).equalsIgnoreCase("Permanent Address Location")) {
                    LinearLayout layout = new LinearLayout(AdvanceSearchActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setId(R.id.permanentlocation);

                    LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    cp.setMargins(0, 10, 0, 10);
                    layout.setBackgroundColor(Color.parseColor("#aaaaaa"));
                    layout.setPadding(10, 5, 10, 5);
                    layout.setLayoutParams(cp);

                    final TextView tv = new TextView(AdvanceSearchActivity.this);
                    tv.setText(sflist.get(position));
                    //et.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    //tv.setId(R.id.dob);
                    tv.setTextSize(18);
                    tv.setPadding(10, 10, 0, 10);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete, 0);
                    tv.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= tv.getRight() - tv.getTotalPaddingRight()) {
                                    // your action for drawable click event
                                    llDynamicSearch.removeView((View) tv.getParent());
                                    setClearVisibility();
                                    // Toast.makeText(AdvanceSearchActivity.this, "" + llDynamicSearch.indexOfChild(tv), Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                            }
                            return true;
                        }

                    });

                    SearchableSpinner spnrPCountry = new SearchableSpinner(AdvanceSearchActivity.this);
                    spnrPCountry.setLayoutParams(cp);
                    spnrPCountry.setTitle("Select Permanent Country");
                    spnrPCountry.setId(R.id.permanentcountry);
//                    spnrPCountry.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    SearchableSpinner spnrPState = new SearchableSpinner(AdvanceSearchActivity.this);
                    spnrPState.setLayoutParams(cp);
                    spnrPState.setTitle("Select Permanent State");
                    spnrPState.setId(R.id.permanentstate);
//                    spnrPState.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    SearchableSpinner spnrPCity = new SearchableSpinner(AdvanceSearchActivity.this);
                    spnrPCity.setLayoutParams(cp);
                    spnrPCity.setTitle("Select Permanent District");
                    spnrPCity.setId(R.id.permanentdistrict);
//                    spnrPCity.setBackground(getResources().getDrawable(R.drawable.spinner_background));

                    getPCountry(spnrPCountry, spnrPState, spnrPCity);

                    layout.addView(tv);
                    layout.addView(spnrPCountry);
                    layout.addView(spnrPState);
                    layout.addView(spnrPCity);
                    llDynamicSearch.addView(layout);
                }
            }
        });

        alertDialog.show();

    }

    private void setClearVisibility() {
        if (llDynamicSearch.getChildCount() == 0) {
            btnClearFilter.setVisibility(View.GONE);
        }
    }

    private void selectDOB(final TextView tv) {
        //AndroidUtils.hideSoftKeyboard(AdvanceSearchActivity.this);
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(tv);
            }

        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(AdvanceSearchActivity.this, date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    private void updateLabel(TextView tv) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tv.setText(sdf.format(myCalendar.getTime()));

        int dobyear = myCalendar.get(Calendar.YEAR);
        int dobmonth = myCalendar.get(Calendar.MONTH) + 1;
        int dobday = myCalendar.get(Calendar.DAY_OF_MONTH);

        selectedDOB = dobday + "/" + dobmonth + "/" + dobyear;
        Log.e("dobdata", selectedDOB + "");
    }

    private void getGotraMother(final Spinner spnrGotraMother) {
        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiservice = APIClient.getClient().create(API.class);

        Call<List<ModelGotra>> call1 = apiservice.get_gotra_list("gotra");

        call1.enqueue(new Callback<List<ModelGotra>>() {
            @Override
            public void onResponse(Call<List<ModelGotra>> call, Response<List<ModelGotra>> response) {
                loading.dismiss();
                gotraMotherList.add("Select Gotra(Mother)");
                for (int i = 0; i < response.body().size(); i++) {
                    gotraMotherList.add(response.body().get(i).getName());
                }
                ArrayAdapter<String> aa0 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, gotraMotherList);
                aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrGotraMother.setAdapter(aa0);

                spnrGotraMother.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedGotraMother = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelGotra>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Toast.makeText(AdvanceSearchActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getGotraSelf(final Spinner spinnerGotraSelf) {
        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiservice = APIClient.getClient().create(API.class);

        Call<List<ModelGotra>> call1 = apiservice.get_gotra_list("gotra");

        call1.enqueue(new Callback<List<ModelGotra>>() {
            @Override
            public void onResponse(Call<List<ModelGotra>> call, Response<List<ModelGotra>> response) {
                loading.dismiss();
                gotraSelfList.add("Select Gotra(Self)");
                for (int i = 0; i < response.body().size(); i++) {
                    gotraSelfList.add(response.body().get(i).getName());
                }

                ArrayAdapter<String> aa0 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, gotraSelfList);
                aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerGotraSelf.setAdapter(aa0);

                spinnerGotraSelf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedGotraSelf = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelGotra>> call, Throwable t) {
                loading.dismiss();
                //getGotraSelf(spinnerGotraSelf);
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Toast.makeText(AdvanceSearchActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProfession(final Spinner spinnerProfession) {

        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<Profession>> call1 = apiService.get_profession();
        call1.enqueue(new Callback<List<Profession>>() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<List<Profession>> call, Response<List<Profession>> response) {
                loading.dismiss();
                if (response.body().size() > 0) {
                    professionlist.addAll(response.body());
                }

                final List<String> Profession = new ArrayList<>();
                if (professionlist.size() > 0) {
                    for (int i = 0; i < professionlist.size(); i++) {
                        if (i == 0) {
                            Profession.add("Select Profession");
                            Profession.add(professionlist.get(i).getProfession());
                        } else {
                            Profession.add(professionlist.get(i).getProfession());
                        }
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, Profession);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProfession.setAdapter(aa11);

                spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedProfession = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Profession>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getSubcaste(final Spinner spnrSubcaste) {

        final ProgressDialog progressDialog = new ProgressDialog(AdvanceSearchActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://demo.vethics.in/swarnkar/mobile/Register/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API apiServices = retrofit.create(API.class);

        Call<ResponseBody> call = apiServices.get_subcaste();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                try {
                    JSONArray js = new JSONArray(response.body().string());
                    Log.e("response", String.valueOf(js));
                    CastList.clear();
                    for (int i = 0; i < js.length(); i++) {

                        JSONObject person = (JSONObject) js.get(i);
                        String caste = person.getString("subcaste");

                        if (i == 0) {
                            CastList.add("Select Subcaste");
                            CastList.add(caste);
                        } else {
                            CastList.add(caste);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> aa0 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, CastList);
                aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrSubcaste.setAdapter(aa0);

                spnrSubcaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedSubcaste = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.getStackTrace();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("msgfail", "" + t.getMessage());
            }
        });

    }

    private void getProfessionIndustry(final Spinner spnrProfessionIndustry) {
        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiservice = APIClient.getClient().create(API.class);

        Call<List<ModelProfessionSociety>> call1 = apiservice.get_professional_Society("tbl_professionslist");

        call1.enqueue(new Callback<List<ModelProfessionSociety>>() {
            @Override
            public void onResponse(Call<List<ModelProfessionSociety>> call, Response<List<ModelProfessionSociety>> response) {
                loading.dismiss();
                if (response.body().size() > 0) {
                    ProfessionIndustryList.addAll(response.body());
                }
                final List<String> newProfInd = new ArrayList<>();
                newProfInd.add("Select Profession Industry");
                for (int i = 0; i < response.body().size(); i++) {
                    newProfInd.add(response.body().get(i).getProfessionslist());
                }

                ArrayAdapter aa0 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, newProfInd);
                aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrProfessionIndustry.setAdapter(aa0);

                spnrProfessionIndustry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedProfessionIndustry = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelProfessionSociety>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Toast.makeText(AdvanceSearchActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOfficeCountry(final Spinner spnrCountry, final Spinner spnrState, final Spinner spnrDistrict) {
        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCountry>> call1 = apiService.get_country();
        call1.enqueue(new Callback<List<ModelCountry>>() {
            @Override
            public void onResponse(Call<List<ModelCountry>> call, Response<List<ModelCountry>> response) {
                loading.dismiss();

                if (response.body().size() > 0) {
                    CountryList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                state.add("Select State");

                ArrayAdapter<String> aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrState.setAdapter(aa11);

                final List<String> city = new ArrayList<>();
                city.add("Select District");

                ArrayAdapter aa1 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, city);
                aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrDistrict.setAdapter(aa1);

                final List<String> country = new ArrayList<>();
                if (CountryList.size() > 0) {
                    for (int i = 0; i < CountryList.size(); i++) {
                        if (i == 0) {
                            country.add("Select Country");
                            country.add(CountryList.get(i).getCountryName());
                        } else {
                            country.add(CountryList.get(i).getCountryName());
                        }
                    }

                }

                ArrayAdapter<String> a = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, country);
                a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrCountry.setAdapter(a);

                //getState(states, citys);
/*
                for (int j = 0; j < country.size(); j++) {
                    if (country.get(j).trim().equals(countrydd.trim())) {
                        spnrCountry.setSelection(j);
                    }
                }*/

                spnrCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (country.get(i).equalsIgnoreCase("Select Country")) {

                        } else {
                            selectedCountry = CountryList.get(i - 1).getCountryName();
                            Log.e("selectcountry", selectedCountry + CountryList.get(i).getId());
                            getOfficeState(spnrState, spnrDistrict, CountryList.get(i - 1).getId());

                           /* strOCountryId = CountryList.get(i - 1).getId();
                            strOCountryName = CountryList.get(i - 1).getCountryName();
                            getState(states, citys);*/
                            //officeCountryList.add(CountryList.get(i - 1).getCountryName());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCountry>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getOfficeState(final Spinner spnrState, final Spinner spnrDistrict, String strOCountryId) {

        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelState>> call1 = apiService.get_states(strOCountryId);
        call1.enqueue(new Callback<List<ModelState>>() {
            @Override
            public void onResponse(Call<List<ModelState>> call, Response<List<ModelState>> response) {
                loading.dismiss();
                StateList = new ArrayList<>();
                if (response.body().size() > 0) {
                    StateList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                if (StateList.size() > 0) {
                    for (int i = 0; i < StateList.size(); i++) {
                        state.add(StateList.get(i).getStateName());
                    }
                }

                ArrayAdapter aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrState.setAdapter(aa11);

               /* for (int j = 0; j < state.size(); j++) {
                    if (state.get(j).trim().equalsIgnoreCase(statesss.trim())) {
                        spnrState.setSelection(j);
                    }
                }*/
                spnrState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (StateList.get(i).equals("Select State")) {
                            //getCity("");
                        } else {
                            selectedState = StateList.get(i).getStateName();
                            Log.e("selectstate", selectedState + StateList.get(i).getId());
                            getOfficeCity(spnrDistrict, StateList.get(i).getId());
                            /*strOStateId = StateList.get(i).getId();
                            strOStateName = StateList.get(i).getStateName();
                            getCity(cityww);*/
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<ModelState>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("states", t.getMessage() + "");
            }
        });
    }

    private void getOfficeCity(final Spinner spnrDistrict, final String strOStateId) {

        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCity>> call1 = apiService.get_city(strOStateId);
        call1.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, Response<List<ModelCity>> response) {
                loading.dismiss();

                CityList = new ArrayList<>();
                if (response.body().size() > 0) {
                    CityList.addAll(response.body());
                }

                final List<String> city = new ArrayList<>();
                if (CityList.size() > 0) {
                    for (int i = 0; i < CityList.size(); i++) {
                        city.add(CityList.get(i).getCityName());
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, city);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrDistrict.setAdapter(aa11);

                /*for (int j = 0; j < city.size(); j++) {
                    if (city.get(j).trim().equalsIgnoreCase(citsy.trim())) {
                        spnrDistrict.setSelection(j);

                    }
                }*/
                spnrDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedDistrict = CityList.get(i).getCityName();
                        Log.e("selectDistrict", selectedDistrict + CityList.get(i).getId());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCity>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("city", t.getMessage() + "");
            }
        });

    }

    private void getRCountry(final Spinner spnrCountry, final Spinner spnrState, final Spinner spnrDistrict) {
        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCountry>> call1 = apiService.get_country();
        call1.enqueue(new Callback<List<ModelCountry>>() {
            @Override
            public void onResponse(Call<List<ModelCountry>> call, Response<List<ModelCountry>> response) {
                loading.dismiss();

                if (response.body().size() > 0) {
                    CountryList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                state.add("Select State");

                ArrayAdapter<String> aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrState.setAdapter(aa11);

                final List<String> city = new ArrayList<>();
                city.add("Select District");

                ArrayAdapter aa1 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, city);
                aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrDistrict.setAdapter(aa1);

                final List<String> country = new ArrayList<>();
                if (CountryList.size() > 0) {
                    for (int i = 0; i < CountryList.size(); i++) {
                        if (i == 0) {
                            country.add("Select Country");
                            country.add(CountryList.get(i).getCountryName());
                        } else {
                            country.add(CountryList.get(i).getCountryName());
                        }
                    }

                }

                ArrayAdapter<String> a = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, country);
                a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrCountry.setAdapter(a);

                //getState(states, citys);
/*
                for (int j = 0; j < country.size(); j++) {
                    if (country.get(j).trim().equals(countrydd.trim())) {
                        spnrCountry.setSelection(j);
                    }
                }*/

                spnrCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (country.get(i).equalsIgnoreCase("Select Country")) {

                        } else {
                            selectedCountry = CountryList.get(i - 1).getCountryName();
                            Log.e("selectcountry", selectedCountry + CountryList.get(i).getId());
                            getRState(spnrState, spnrDistrict, CountryList.get(i - 1).getId());

                           /* strOCountryId = CountryList.get(i - 1).getId();
                            strOCountryName = CountryList.get(i - 1).getCountryName();
                            getState(states, citys);*/
                            //officeCountryList.add(CountryList.get(i - 1).getCountryName());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCountry>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getRState(final Spinner spnrState, final Spinner spnrDistrict, String strOCountryId) {

        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelState>> call1 = apiService.get_states(strOCountryId);
        call1.enqueue(new Callback<List<ModelState>>() {
            @Override
            public void onResponse(Call<List<ModelState>> call, Response<List<ModelState>> response) {
                loading.dismiss();
                StateList = new ArrayList<>();
                if (response.body().size() > 0) {
                    StateList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                if (StateList.size() > 0) {
                    for (int i = 0; i < StateList.size(); i++) {
                        state.add(StateList.get(i).getStateName());
                    }
                }

                ArrayAdapter aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrState.setAdapter(aa11);

               /* for (int j = 0; j < state.size(); j++) {
                    if (state.get(j).trim().equalsIgnoreCase(statesss.trim())) {
                        spnrState.setSelection(j);
                    }
                }*/
                spnrState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (StateList.get(i).equals("Select State")) {
                            //getCity("");
                        } else {
                            selectedState = StateList.get(i).getStateName();
                            Log.e("selectstate", selectedState + StateList.get(i).getId());
                            getRCity(spnrDistrict, StateList.get(i).getId());
                            /*strOStateId = StateList.get(i).getId();
                            strOStateName = StateList.get(i).getStateName();
                            getCity(cityww);*/
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<ModelState>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("states", t.getMessage() + "");
            }
        });
    }

    private void getRCity(final Spinner spnrDistrict, final String strOStateId) {

        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCity>> call1 = apiService.get_city(strOStateId);
        call1.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, Response<List<ModelCity>> response) {
                loading.dismiss();

                CityList = new ArrayList<>();
                if (response.body().size() > 0) {
                    CityList.addAll(response.body());
                }

                final List<String> city = new ArrayList<>();
                if (CityList.size() > 0) {
                    for (int i = 0; i < CityList.size(); i++) {
                        city.add(CityList.get(i).getCityName());
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, city);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrDistrict.setAdapter(aa11);

                /*for (int j = 0; j < city.size(); j++) {
                    if (city.get(j).trim().equalsIgnoreCase(citsy.trim())) {
                        spnrDistrict.setSelection(j);

                    }
                }*/
                spnrDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedDistrict = CityList.get(i).getCityName();
                        Log.e("selectDistrict", selectedDistrict + CityList.get(i).getId());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCity>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("city", t.getMessage() + "");
            }
        });

    }

    private void getPCountry(final Spinner spnrCountry, final Spinner spnrState, final Spinner spnrDistrict) {
        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCountry>> call1 = apiService.get_country();
        call1.enqueue(new Callback<List<ModelCountry>>() {
            @Override
            public void onResponse(Call<List<ModelCountry>> call, Response<List<ModelCountry>> response) {
                loading.dismiss();

                if (response.body().size() > 0) {
                    CountryList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                state.add("Select State");

                ArrayAdapter<String> aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrState.setAdapter(aa11);

                final List<String> city = new ArrayList<>();
                city.add("Select District");

                ArrayAdapter aa1 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, city);
                aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrDistrict.setAdapter(aa1);

                final List<String> country = new ArrayList<>();
                if (CountryList.size() > 0) {
                    for (int i = 0; i < CountryList.size(); i++) {
                        if (i == 0) {
                            country.add("Select Country");
                            country.add(CountryList.get(i).getCountryName());
                        } else {
                            country.add(CountryList.get(i).getCountryName());
                        }
                    }

                }

                ArrayAdapter<String> a = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, country);
                a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrCountry.setAdapter(a);

                //getState(states, citys);
/*
                for (int j = 0; j < country.size(); j++) {
                    if (country.get(j).trim().equals(countrydd.trim())) {
                        spnrCountry.setSelection(j);
                    }
                }*/

                spnrCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (country.get(i).equalsIgnoreCase("Select Country")) {

                        } else {
                            selectedCountry = CountryList.get(i - 1).getCountryName();
                            Log.e("selectcountry", selectedCountry + CountryList.get(i).getId());
                            getPState(spnrState, spnrDistrict, CountryList.get(i - 1).getId());

                           /* strOCountryId = CountryList.get(i - 1).getId();
                            strOCountryName = CountryList.get(i - 1).getCountryName();
                            getState(states, citys);*/
                            //officeCountryList.add(CountryList.get(i - 1).getCountryName());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCountry>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getPState(final Spinner spnrState, final Spinner spnrDistrict, String strOCountryId) {

        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelState>> call1 = apiService.get_states(strOCountryId);
        call1.enqueue(new Callback<List<ModelState>>() {
            @Override
            public void onResponse(Call<List<ModelState>> call, Response<List<ModelState>> response) {
                loading.dismiss();
                StateList = new ArrayList<>();
                if (response.body().size() > 0) {
                    StateList.addAll(response.body());
                }

                final List<String> state = new ArrayList<>();
                if (StateList.size() > 0) {
                    for (int i = 0; i < StateList.size(); i++) {
                        state.add(StateList.get(i).getStateName());
                    }
                }

                ArrayAdapter aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrState.setAdapter(aa11);

               /* for (int j = 0; j < state.size(); j++) {
                    if (state.get(j).trim().equalsIgnoreCase(statesss.trim())) {
                        spnrState.setSelection(j);
                    }
                }*/
                spnrState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (StateList.get(i).equals("Select State")) {
                            //getCity("");
                        } else {
                            selectedState = StateList.get(i).getStateName();
                            Log.e("selectstate", selectedState + StateList.get(i).getId());
                            getPCity(spnrDistrict, StateList.get(i).getId());
                            /*strOStateId = StateList.get(i).getId();
                            strOStateName = StateList.get(i).getStateName();
                            getCity(cityww);*/
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<ModelState>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("states", t.getMessage() + "");
            }
        });
    }

    private void getPCity(final Spinner spnrDistrict, final String strOStateId) {

        final ProgressDialog loading = ProgressDialog.show(AdvanceSearchActivity.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCity>> call1 = apiService.get_city(strOStateId);
        call1.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, Response<List<ModelCity>> response) {
                loading.dismiss();

                CityList = new ArrayList<>();
                if (response.body().size() > 0) {
                    CityList.addAll(response.body());
                }

                final List<String> city = new ArrayList<>();
                if (CityList.size() > 0) {
                    for (int i = 0; i < CityList.size(); i++) {
                        city.add(CityList.get(i).getCityName());
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter<String>(AdvanceSearchActivity.this, android.R.layout.simple_spinner_item, city);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrDistrict.setAdapter(aa11);

                /*for (int j = 0; j < city.size(); j++) {
                    if (city.get(j).trim().equalsIgnoreCase(citsy.trim())) {
                        spnrDistrict.setSelection(j);

                    }
                }*/
                spnrDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedDistrict = CityList.get(i).getCityName();
                        Log.e("selectDistrict", selectedDistrict + CityList.get(i).getId());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<ModelCity>> call, Throwable t) {
                loading.dismiss();
                llDynamicSearch.removeViewAt(llDynamicSearch.getChildCount() - 1);
                Log.e("city", t.getMessage() + "");
            }
        });

    }

    private void submitSearch() {

        firstNameList.clear();
        lastNameList.clear();
        mobileNumberList.clear();
        emailList.clear();
        memberIdList.clear();
        subcasteList.clear();
        gotraSelfList.clear();
        gotraMotherList.clear();
        maritalstatusList.clear();
        dobList.clear();
        professionList.clear();
        professionOrgNameList.clear();
        professionStatusList.clear();
        professionIndustryList.clear();
        professionDesignationList.clear();
        officeLocalityList.clear();
        officeCountryList.clear();
        officeStateList.clear();
        officeDistrictList.clear();
        educationalQualificationList.clear();
        statusEducationList.clear();
        areaStudyList.clear();
        resLocalityList.clear();
        resWardList.clear();
        resConstituencyList.clear();
        resVillageList.clear();
        resTehsilList.clear();
        residenceCountryList.clear();
        residenceStateList.clear();
        residenceDistrictList.clear();
        permanentLocalityList.clear();
        permanentWardList.clear();
        permanentConstituencyList.clear();
        permanentVillageList.clear();
        permanentTehsilList.clear();
        permanentCountryList.clear();
        permanentStateList.clear();
        permanentDistrictList.clear();

        for (int i = 0; i < llDynamicSearch.getChildCount(); i++) {
            View view = llDynamicSearch.getChildAt(i);
            if (view.getId() == R.id.llfname) {
                EditText editText = (EditText) view.findViewById(R.id.firstname);
                if (!editText.getText().toString().trim().isEmpty()) {
                    firstNameList.add(editText.getText().toString().trim());
                }

            } else if (view.getId() == R.id.llname) {
                EditText editText = (EditText) view.findViewById(R.id.lastname);
                if (!editText.getText().toString().trim().isEmpty()) {
                    lastNameList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llmobile) {
                EditText editText = (EditText) view.findViewById(R.id.mobilenumber);
                if (!editText.getText().toString().trim().isEmpty()) {
                    mobileNumberList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llemail) {
                EditText editText = (EditText) view.findViewById(R.id.email);
                if (!editText.getText().toString().trim().isEmpty()) {
                    emailList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llmemberid) {
                EditText editText = (EditText) view.findViewById(R.id.memberid);
                if (!editText.getText().toString().trim().isEmpty()) {
                    memberIdList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.lldob) {
                TextView tvdob = (TextView) view.findViewById(R.id.dob);
                if (!tvdob.getText().toString().trim().equalsIgnoreCase("Select Date of Birth")) {
                    dobList.add(tvdob.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llprofessionorganization) {
                EditText editText = (EditText) view.findViewById(R.id.professionorganizationname);
                if (!editText.getText().toString().trim().isEmpty()) {
                    professionOrgNameList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llprofessiondesignation) {
                EditText editText = (EditText) view.findViewById(R.id.professiondesignation);
                if (!editText.getText().toString().trim().isEmpty()) {
                    professionDesignationList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llofficelocality) {
                EditText editText = (EditText) view.findViewById(R.id.officelocality);
                if (!editText.getText().toString().trim().isEmpty()) {
                    officeLocalityList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.lleducationqualification) {
                EditText editText = (EditText) view.findViewById(R.id.educationalqulification);
                if (!editText.getText().toString().trim().isEmpty()) {
                    educationalQualificationList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.lleducationinstitute) {
                EditText editText = (EditText) view.findViewById(R.id.educationalinstitute);
                if (!editText.getText().toString().trim().isEmpty()) {
                    educationalInstituteList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llreslocality) {
                EditText editText = (EditText) view.findViewById(R.id.residencelocality);
                if (!editText.getText().toString().trim().isEmpty()) {
                    resLocalityList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llresward) {
                EditText editText = (EditText) view.findViewById(R.id.residenceward);
                if (!editText.getText().toString().trim().isEmpty()) {
                    resWardList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llresconstituency) {
                EditText editText = (EditText) view.findViewById(R.id.residenceconstituency);
                if (!editText.getText().toString().trim().isEmpty()) {
                    resConstituencyList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llresvillage) {
                EditText editText = (EditText) view.findViewById(R.id.residencevillage);
                if (!editText.getText().toString().trim().isEmpty()) {
                    resVillageList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llrestehsil) {
                EditText editText = (EditText) view.findViewById(R.id.residencetehsil);
                if (!editText.getText().toString().trim().isEmpty()) {
                    resTehsilList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llperlocality) {
                EditText editText = (EditText) view.findViewById(R.id.permanentlocality);
                if (!editText.getText().toString().trim().isEmpty()) {
                    permanentLocalityList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llperward) {
                EditText editText = (EditText) view.findViewById(R.id.permanentward);
                if (!editText.getText().toString().trim().isEmpty()) {
                    permanentWardList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llperconstituency) {
                EditText editText = (EditText) view.findViewById(R.id.permanentconstituency);
                if (!editText.getText().toString().trim().isEmpty()) {
                    permanentConstituencyList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llpervillage) {
                EditText editText = (EditText) view.findViewById(R.id.permanentvillage);
                if (!editText.getText().toString().trim().isEmpty()) {
                    permanentVillageList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llpertehsil) {
                EditText editText = (EditText) view.findViewById(R.id.permanenttehsil);
                if (!editText.getText().toString().trim().isEmpty()) {
                    permanentTehsilList.add(editText.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llsubcaste) {
                SearchableSpinner spnrSubcaste = (SearchableSpinner) view.findViewById(R.id.subcaste);
                TextView tvCountry = (TextView) spnrSubcaste.getSelectedView();
                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Subcaste")) {
                    subcasteList.add(tvCountry.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llgotraself) {
                SearchableSpinner spnrSubcaste = (SearchableSpinner) view.findViewById(R.id.gotraself);
                TextView tvCountry = (TextView) spnrSubcaste.getSelectedView();
                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Gotra(Self)")) {
                    gotraSelfList.add(tvCountry.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llgotramother) {
                SearchableSpinner spnrSubcaste = (SearchableSpinner) view.findViewById(R.id.gotramother);
                TextView tvCountry = (TextView) spnrSubcaste.getSelectedView();
                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Gotra(Mother)")) {
                    gotraMotherList.add(tvCountry.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llmaritalstatus) {
                SearchableSpinner spnrSubcaste = (SearchableSpinner) view.findViewById(R.id.maritalstatus);
                TextView tvCountry = (TextView) spnrSubcaste.getSelectedView();
                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Marital Status")) {
                    maritalstatusList.add(tvCountry.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llprofession) {
                SearchableSpinner spnrSubcaste = (SearchableSpinner) view.findViewById(R.id.profession1);
                TextView tvCountry = (TextView) spnrSubcaste.getSelectedView();
                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Profession")) {
                    professionList.add(tvCountry.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llprofessionstatus) {
                SearchableSpinner spnrSubcaste = (SearchableSpinner) view.findViewById(R.id.professionstatus);
                TextView tvCountry = (TextView) spnrSubcaste.getSelectedView();
                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Profession Status")) {
                    professionStatusList.add(tvCountry.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llprofessionindustry) {
                SearchableSpinner spnrSubcaste = (SearchableSpinner) view.findViewById(R.id.professionindustry);
                TextView tvCountry = (TextView) spnrSubcaste.getSelectedView();
                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Profession Industry")) {
                    professionIndustryList.add(tvCountry.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llstatuseducational) {
                SearchableSpinner spnrSubcaste = (SearchableSpinner) view.findViewById(R.id.statuseducational);
                TextView tvCountry = (TextView) spnrSubcaste.getSelectedView();
                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Education Status")) {
                    statusEducationList.add(tvCountry.getText().toString().trim());
                }
            } else if (view.getId() == R.id.llareastudy) {
                SearchableSpinner spnrSubcaste = (SearchableSpinner) view.findViewById(R.id.areastudy);
                TextView tvCountry = (TextView) spnrSubcaste.getSelectedView();
                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Area of Study")) {
                    areaStudyList.add(tvCountry.getText().toString().trim());
                }
            } else if (view.getId() == R.id.officelocation) {
                SearchableSpinner spnrCountry = (SearchableSpinner) view.findViewById(R.id.officecountry);
                SearchableSpinner spnrState = (SearchableSpinner) view.findViewById(R.id.officestate);
                SearchableSpinner spnrDistrict = (SearchableSpinner) view.findViewById(R.id.officedistrict);
                TextView tvCountry = (TextView) spnrCountry.getSelectedView();
                TextView tvState = (TextView) spnrState.getSelectedView();
                TextView tvDistrict = (TextView) spnrDistrict.getSelectedView();

                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Country")) {
                    officeCountryList.add(tvCountry.getText().toString().trim());
                }

                if (!tvState.getText().toString().trim().equalsIgnoreCase("Select State")) {
                    officeStateList.add(tvState.getText().toString().trim());
                }

                if (!tvDistrict.getText().toString().trim().equalsIgnoreCase("Select District")) {
                    officeDistrictList.add(tvDistrict.getText().toString().trim());
                }
            } else if (view.getId() == R.id.residencelocation) {
                SearchableSpinner spnrCountry = (SearchableSpinner) view.findViewById(R.id.residencecountry);
                SearchableSpinner spnrState = (SearchableSpinner) view.findViewById(R.id.residencestate);
                SearchableSpinner spnrDistrict = (SearchableSpinner) view.findViewById(R.id.residencedistrict);
                TextView tvCountry = (TextView) spnrCountry.getSelectedView();
                TextView tvState = (TextView) spnrState.getSelectedView();
                TextView tvDistrict = (TextView) spnrDistrict.getSelectedView();

                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Country")) {
                    residenceCountryList.add(tvCountry.getText().toString().trim());
                }

                if (!tvState.getText().toString().trim().equalsIgnoreCase("Select State")) {
                    residenceStateList.add(tvState.getText().toString().trim());
                }

                if (!tvDistrict.getText().toString().trim().equalsIgnoreCase("Select District")) {
                    residenceDistrictList.add(tvDistrict.getText().toString().trim());
                }
            } else if (view.getId() == R.id.permanentlocation) {
                SearchableSpinner spnrCountry = (SearchableSpinner) view.findViewById(R.id.permanentcountry);
                SearchableSpinner spnrState = (SearchableSpinner) view.findViewById(R.id.permanentstate);
                SearchableSpinner spnrDistrict = (SearchableSpinner) view.findViewById(R.id.permanentdistrict);
                TextView tvCountry = (TextView) spnrCountry.getSelectedView();
                TextView tvState = (TextView) spnrState.getSelectedView();
                TextView tvDistrict = (TextView) spnrDistrict.getSelectedView();

                if (!tvCountry.getText().toString().trim().equalsIgnoreCase("Select Country")) {
                    permanentCountryList.add(tvCountry.getText().toString().trim());
                }

                if (!tvState.getText().toString().trim().equalsIgnoreCase("Select State")) {
                    permanentStateList.add(tvState.getText().toString().trim());
                }

                if (!tvDistrict.getText().toString().trim().equalsIgnoreCase("Select District")) {
                    permanentDistrictList.add(tvDistrict.getText().toString().trim());
                }
            }
        }
        /*Log.e("ofcountry", officeCountryList.toString());
        Log.e("ofstate", officeStateList.toString());
        Log.e("ofcity", officeDistrictList.toString());
        Log.e("rescountry", residenceCountryList.toString());
        Log.e("resstate", residenceStateList.toString());
        Log.e("rescity", residenceDistrictList.toString());
        Log.e("permcountry", permanentCountryList.toString());
        Log.e("permstate", permanentStateList.toString());
        Log.e("permcity", permanentDistrictList.toString());
        Log.e("firstname", firstNameList.toString());
        Log.e("lastname", lastNameList.toString());
        Log.e("mobile", mobileNumberList.toString());
        Log.e("email", emailList.toString());
        Log.e("memberid", memberIdList.toString());
        Log.e("dob", dobList.toString());
        Log.e("professionorganization", professionOrgNameList.toString());
        Log.e("professiondesignation", professionDesignationList.toString());
        Log.e("officelocality", officeLocalityList.toString());
        Log.e("educationalqulification", educationalQualificationList.toString());
        Log.e("educationalinstitute", educationalInstituteList.toString());
        Log.e("residencelocality", resLocalityList.toString());
        Log.e("residenceward", resWardList.toString());
        Log.e("residenceconstituency", resConstituencyList.toString());
        Log.e("residencevillage", resVillageList.toString());
        Log.e("residencetehsil", resTehsilList.toString());
        Log.e("permanentlocality", permanentLocalityList.toString());
        Log.e("permanentward", permanentWardList.toString());
        Log.e("permanentconstituency", permanentConstituencyList.toString());
        Log.e("permanentvillage", permanentVillageList.toString());
        Log.e("permanenttehsil", permanentTehsilList.toString());
        Log.e("subcaste", subcasteList.toString());
        Log.e("gotraself", gotraSelfList.toString());
        Log.e("gotramother", gotraMotherList.toString());
        Log.e("marital status", maritalstatusList.toString());
        Log.e("profession1", professionList.toString());
        Log.e("professionstatus", professionStatusList.toString());
        Log.e("professionindustry", professionIndustryList.toString());
        Log.e("educationstatus", statusEducationList.toString());
        Log.e("areastudy", areaStudyList.toString());*/

        Intent i = new Intent(AdvanceSearchActivity.this, SearchResultActivity.class);
        /*i.putStringArrayListExtra("firstname", firstNameList);
        i.putStringArrayListExtra("lastname", lastNameList);
        i.putStringArrayListExtra("mobile", mobileNumberList);
        i.putStringArrayListExtra("email", emailList);
        i.putStringArrayListExtra("memberid", memberIdList);
        i.putStringArrayListExtra("subcaste", subcasteList);
        i.putStringArrayListExtra("gotraself", gotraSelfList);
        i.putStringArrayListExtra("gotramother", gotraMotherList);
        i.putStringArrayListExtra("maritalsstatus", maritalstatusList);
        i.putStringArrayListExtra("dob", dobList);
        i.putStringArrayListExtra("profession", professionList);
        i.putStringArrayListExtra("professionorganization", professionOrgNameList);
        i.putStringArrayListExtra("professionstatus", professionStatusList);
        i.putStringArrayListExtra("professionindustry", professionIndustryList);
        i.putStringArrayListExtra("professiondesignation", professionDesignationList);
        i.putStringArrayListExtra("officelocality", officeLocalityList);
        i.putStringArrayListExtra("officecountry", officeCountryList);
        i.putStringArrayListExtra("officestate", officeStateList);
        i.putStringArrayListExtra("officedistrict", officeDistrictList);
        i.putStringArrayListExtra("educationalqualification", educationalQualificationList);
        i.putStringArrayListExtra("educationalinstitute", educationalInstituteList);
        i.putStringArrayListExtra("educationstatus", statusEducationList);
        i.putStringArrayListExtra("areastudy", areaStudyList);
        i.putStringArrayListExtra("resconstituency", resConstituencyList);
        i.putStringArrayListExtra("reslocality", resLocalityList);
        i.putStringArrayListExtra("resward", resWardList);
        i.putStringArrayListExtra("resvillage", resVillageList);
        i.putStringArrayListExtra("restehsil", resTehsilList);
        i.putStringArrayListExtra("rescountry", residenceCountryList);
        i.putStringArrayListExtra("resstate", residenceStateList);
        i.putStringArrayListExtra("resdistrict", residenceDistrictList);
        i.putStringArrayListExtra("perconstituency", resConstituencyList);
        i.putStringArrayListExtra("perlocality", permanentLocalityList);
        i.putStringArrayListExtra("perward", permanentWardList);
        i.putStringArrayListExtra("pervillage", permanentVillageList);
        i.putStringArrayListExtra("pertehsil", permanentTehsilList);
        i.putStringArrayListExtra("percountry", permanentCountryList);
        i.putStringArrayListExtra("perstate", permanentStateList);
        i.putStringArrayListExtra("perdistrict", permanentDistrictList);*/

        JSONObject jsonObject1 = new JSONObject();

        if (firstNameList.size() > 0) {
            try {
                jsonObject1.put("firstName", firstNameList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (lastNameList.size() > 0) {
            try {
                jsonObject1.put("lastName", lastNameList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (mobileNumberList.size() > 0) {
            try {
                jsonObject1.put("mobileNumber", mobileNumberList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (emailList.size() > 0) {
            try {
                jsonObject1.put("email", emailList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (memberIdList.size() > 0) {
            try {
                jsonObject1.put("memberId", memberIdList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (subcasteList.size() > 0) {
            try {
                jsonObject1.put("subCaste", subcasteList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (gotraSelfList.size() > 0) {
            try {
                jsonObject1.put("gotraSelf", gotraSelfList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (gotraMotherList.size() > 0) {
            try {
                jsonObject1.put("gotraMother", gotraMotherList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (maritalstatusList.size() > 0) {
            try {

                jsonObject1.put("maritalStatus", maritalstatusList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (dobList.size() > 0) {
            try {
                jsonObject1.put("dateofBirth", dobList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (professionList.size() > 0) {
            try {
                jsonObject1.put("profession", professionList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (professionOrgNameList.size() > 0) {
            try {
                jsonObject1.put("professionOrganization", professionOrgNameList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (professionStatusList.size() > 0) {
            try {
                jsonObject1.put("professionStatus", professionStatusList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (professionIndustryList.size() > 0) {
            try {
                jsonObject1.put("professionIndustry", professionIndustryList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (professionDesignationList.size() > 0) {
            try {
                jsonObject1.put("professionDesignation", professionDesignationList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (officeLocalityList.size() > 0) {
            try {
                jsonObject1.put("officeLocality", officeLocalityList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (officeCountryList.size() > 0) {
            try {
                jsonObject1.put("officeCountry", officeCountryList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (officeStateList.size() > 0) {
            try {
                jsonObject1.put("officeState", officeStateList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (officeDistrictList.size() > 0) {
            try {
                jsonObject1.put("officeDistrict", officeDistrictList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (educationalQualificationList.size() > 0) {
            try {
                jsonObject1.put("educationQualification", educationalQualificationList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (educationalInstituteList.size() > 0) {
            try {
                jsonObject1.put("educationalInstitute", educationalInstituteList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (statusEducationList.size() > 0) {
            try {
                jsonObject1.put("educationStatus", statusEducationList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (areaStudyList.size() > 0) {
            try {
                jsonObject1.put("areaStudy", areaStudyList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (resLocalityList.size() > 0) {
            try {
                jsonObject1.put("residenceLocality", resLocalityList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (resWardList.size() > 0) {
            try {
                jsonObject1.put("residenceWard", resWardList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (resConstituencyList.size() > 0) {
            try {
                jsonObject1.put("residenceConstituency", resConstituencyList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (resVillageList.size() > 0) {
            try {
                jsonObject1.put("residenceVillage", resVillageList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (resTehsilList.size() > 0) {
            try {
                jsonObject1.put("residenceTehsil", resTehsilList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (residenceCountryList.size() > 0) {
            try {
                jsonObject1.put("residenceCountry", residenceCountryList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (residenceStateList.size() > 0) {
            try {
                jsonObject1.put("residenceState", residenceStateList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (residenceDistrictList.size() > 0) {
            try {
                jsonObject1.put("residenceDistrict", residenceDistrictList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (permanentLocalityList.size() > 0) {
            try {
                jsonObject1.put("permanentLocality", permanentLocalityList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (permanentWardList.size() > 0) {
            try {
                jsonObject1.put("permanentWard", permanentWardList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (permanentConstituencyList.size() > 0) {
            try {
                jsonObject1.put("permanentConstituency", permanentConstituencyList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (permanentVillageList.size() > 0) {
            try {
                jsonObject1.put("permanentVillage", permanentVillageList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (permanentTehsilList.size() > 0) {
            try {
                jsonObject1.put("permanentTehsil", permanentTehsilList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (permanentCountryList.size() > 0) {
            try {
                jsonObject1.put("permanentCountry", permanentCountryList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (permanentStateList.size() > 0) {
            try {
                jsonObject1.put("permanentState", permanentStateList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (permanentDistrictList.size() > 0) {
            try {
                jsonObject1.put("permanentDistrict", permanentDistrictList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e("fname-json-object", jsonObject1.toString());

        i.putExtra("searchRequest", jsonObject1.toString());
        startActivity(i);
    }

}
