package com.swarankar.Activity.Activity;

import android.annotation.TargetApi;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.swarankar.Activity.Model.ModelCity.ModelCity;
import com.swarankar.Activity.Model.ModelCountry.ModelCountry;
import com.swarankar.Activity.Model.ModelProfession.Profession;
import com.swarankar.Activity.Model.ModelState.ModelState;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class searchMaster extends AppCompatActivity {

    Spinner spCountry, spState, spCity, spProfession, spGotraself, spGender, spSubCast, spMaritalStatus;
    // ImageView imgBack;
    EditText edEducation;
    Button btnSearch, btnMore;
    LinearLayout lvstate, lvcity, lvgender, lvGotra, lvProfession, lvsubcast, lvEducation, lvCounty, lvMaritailStatus;
    String strCountryId = "", strCountryName = "", strStateId = "", strStateName = "", strCityName = "", profession = "", subcaste = "",
            gender = "", strGotraSelf = "", strCityid = "", strMaritail = "", stredEducation = "", strpuEducation = "0";
    public List<ModelCountry> CountryList = new ArrayList<>();
    public List<ModelState> StateList;
    public List<ModelCity> CityList;
    public List<Profession> professionlist = new ArrayList<>();
    String name;
    ProgressDialog loading;
    LinearLayout lvFilter;
    LinearLayout lv;
    String strCountry1 = "0", strState1 = "0", strCity1 = "0", strGender = "0", strGotra = "0", strProfession = "0", strSubcast = "0", strEducation = "0", strMaritalcount = "0";
    String strCountry2 = "0", strState2 = "0", strCity2 = "0", strGender2 = "0", strGotra2 = "0", strProfession2 = "0", strSubcast2 = "0", strEducation2 = "0", strMaritalcount2 = "0";

    String select = "0";
    int count = 0;
    LayoutInflater layoutInflater;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //AndroidUtils.hideSoftKeyboard(this);
        SearchActivity.strSearchCount = "0";
        Intent i = new Intent(searchMaster.this, SearchActivity.class);
        startActivity(i);
        //overridePendingTransition(0, 0);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_master);
        lv = (LinearLayout) findViewById(R.id.lv);
//        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        findView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Advance Search");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.strSearchCount = "0";
                Intent i = new Intent(searchMaster.this, SearchActivity.class);
                startActivity(i);
                //AndroidUtils.hideSoftKeyboard(searchMaster.this);
                // overridePendingTransition(0, 0);
                finish();
            }
        });
//        lv.addView(layoutInflater.from(getApplicationContext()).inflate(R.layout.content_search_country, null), 1);

       /* imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.strSearchCount = "0";
                Intent i = new Intent(searchMaster.this, SearchActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();

            }
        });
*/
        Bundle p = getIntent().getExtras();
        name = p.getString("name");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(searchMaster.this, SearchActivity.class);
                i.putExtra("country", strCountryId);
                i.putExtra("state", strStateId);
                i.putExtra("city", strCityid);
                i.putExtra("gender", gender);
                i.putExtra("gotraself", strGotraSelf);
                i.putExtra("subcast", subcaste);
                i.putExtra("profession", profession);
                if (strpuEducation.equals("0")) {
                    i.putExtra("education", "");
                } else {
                    i.putExtra("education", edEducation.getText().toString().trim());
                }
                i.putExtra("name", name);
                i.putExtra("strMaritail", strMaritail);
                SearchActivity.strSearchCount = "1";
                startActivity(i);
                finish();
//                findView();
                btnMore.setVisibility(View.VISIBLE);
                findView();

            }
        });

        lvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                count++;
                Log.e("count", String.valueOf(count));

//                LinearLayout lvstate,lvcity,lvgender,lvGotra,lvProfession,lvsubcast,lvEducation;

                dialog();
//                findView();

//                if(count ==1){
//                    lvstate.setVisibility(View.VISIBLE);
//                }
//                if(count ==2){
//                    lvcity.setVisibility(View.VISIBLE);
//                }
//                if(count ==3){
//                    lvgender.setVisibility(View.VISIBLE);
//                }if(count ==4){
//                    lvGotra.setVisibility(View.VISIBLE);
//                }
//                if(count ==5){
//                    lvProfession.setVisibility(View.VISIBLE);
//                }
//                if(count ==6){
//                    lvsubcast.setVisibility(View.VISIBLE);
//                }if(count ==7){
//                    lvEducation.setVisibility(View.VISIBLE);
//                }

            }
        });

    }

    private void getMaritialStatus() {
        List<String> genderlidt = new ArrayList<>();
        genderlidt.add("Select");
        genderlidt.add("Married");
        genderlidt.add("UnMarried");

        ArrayAdapter aa112 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderlidt);
        aa112.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaritalStatus.setAdapter(aa112);

        spMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).toString().equals("Select")) {
                    strMaritail = "";
                } else {
                    strMaritail = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void dialog() {

        final Dialog alertDialog = new Dialog(searchMaster.this);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(searchMaster.this, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(searchMaster.this).inflate(R.layout.search_dialog, null);
        Button button_ok;
        ImageView btnClose;
        final TextView state, city, gender, gotraself, profession, subcast, education, country, maritail;

        button_ok = (Button) rootView.findViewById(R.id.button_ok);
        country = (TextView) rootView.findViewById(R.id.country);
        city = (TextView) rootView.findViewById(R.id.city);
        gender = (TextView) rootView.findViewById(R.id.gender);
        gotraself = (TextView) rootView.findViewById(R.id.Gotra_self);
        profession = (TextView) rootView.findViewById(R.id.profession);
        subcast = (TextView) rootView.findViewById(R.id.subcast);
        education = (TextView) rootView.findViewById(R.id.education);
        state = (TextView) rootView.findViewById(R.id.state);
        maritail = (TextView) rootView.findViewById(R.id.maritail);
        btnClose = (ImageView) rootView.findViewById(R.id.img_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if (strCountry1.equals("1")) {
            country.setVisibility(View.GONE);
        }
        if (strState1.equals("1")) {
            state.setVisibility(View.GONE);
        }
        if (strCity1.equals("1")) {
            city.setVisibility(View.GONE);
        }
        if (strGender.equals("1")) {
            gender.setVisibility(View.GONE);
        }
        if (strGotra.equals("1")) {
            gotraself.setVisibility(View.GONE);
        }
        if (strProfession.equals("1")) {
            profession.setVisibility(View.GONE);
        }
        if (strEducation.equals("1")) {
            education.setVisibility(View.GONE);
        }
        if (strCountry1.equals("1")) {
            country.setVisibility(View.GONE);
        }
        if (strSubcast.equals("1")) {
            subcast.setVisibility(View.GONE);
        }
        if (strMaritalcount.equals("1")) {
            maritail.setVisibility(View.GONE);
        }

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                lvCounty.setVisibility(View.VISIBLE);
//                lvstate.setVisibility(View.VISIBLE);
//                lvcity.setVisibility(view.VISIBLE);
//                country.setVisibility(View.GONE);
//                state.setVisibility(View.GONE);
//                city.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
                lvFilter.setVisibility(View.GONE);
                strCountry1 = "1";
                strState1 = "1";
                strCity1 = "1";
                strCountry2 = "2";
                findView();
                if (count == 7) {
                    btnMore.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });

        subcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    lvsubcast.setVisibility(View.VISIBLE);
                subcast.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
                lvFilter.setVisibility(View.GONE);
                strSubcast = "1";
                strSubcast2 = "2";
                findView();
                if (count == 7) {
                    btnMore.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    lvCounty.setVisibility(View.VISIBLE);
//                    lvstate.setVisibility(View.VISIBLE);
//                    lvcity.setVisibility(view.VISIBLE);
                country.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                city.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
                lvFilter.setVisibility(View.GONE);
                strCountry1 = "1";
                strState1 = "1";
                strCity1 = "1";
                strCountry2 = "2";
                findView();
                if (count == 7) {
                    btnMore.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    lvCounty.setVisibility(View.VISIBLE);
//                    lvstate.setVisibility(View.VISIBLE);
//                    lvcity.setVisibility(view.VISIBLE);
                country.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                city.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
                lvFilter.setVisibility(View.GONE);
                strCountry1 = "1";
                strState1 = "1";
                strCity1 = "1";
                strCountry2 = "2";
                findView();
                if (count == 7) {
                    btnMore.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    lvgender.setVisibility(View.VISIBLE);
                gender.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
                lvFilter.setVisibility(View.GONE);
                strGender = "1";
                strGender2 = "2";
                findView();

                if (count == 7) {
                    btnMore.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });
        gotraself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    lvGotra.setVisibility(View.VISIBLE);
                gotraself.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
                lvFilter.setVisibility(View.GONE);
                strGotra = "1";
                strGotra2 = "2";
                findView();
                if (count == 7) {
                    btnMore.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });
        profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    lvProfession.setVisibility(View.VISIBLE);
                profession.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
                lvFilter.setVisibility(View.GONE);
                strProfession = "1";
                strProfession2 = "2";
                findView();
                if (count == 7) {
                    btnMore.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });
        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    lvEducation.setVisibility(View.VISIBLE);
                education.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
                lvFilter.setVisibility(View.GONE);
                strEducation = "1";
                strEducation2 = "2";
                findView();
                if (count == 7) {
                    btnMore.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });
        maritail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                lvMaritailStatus.setVisibility(View.VISIBLE);
                maritail.setVisibility(View.GONE);
                btnMore.setVisibility(View.VISIBLE);
                lvFilter.setVisibility(View.GONE);
                strMaritalcount = "1";
                strMaritalcount2 = "2";
                findView();

                if (count == 7) {
                    btnMore.setVisibility(View.GONE);
                }
                alertDialog.dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

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

    private void findView() {

        if (strCountry2.equals("2")) {

            lv.addView(layoutInflater.from(searchMaster.this).inflate(R.layout.content_search_country, null), count);
            lvCounty = (LinearLayout) findViewById(R.id.lv_country);
            lvstate = (LinearLayout) findViewById(R.id.lv_state);
            lvcity = (LinearLayout) findViewById(R.id.lv_city);
            spCountry = (Spinner) findViewById(R.id.search_sp_country);
            spState = (Spinner) findViewById(R.id.search_sp_state);
            spCity = (Spinner) findViewById(R.id.search_sp_city);
            getOfficeCountry();
            strCountry2 = "0";
            count++;
        }

        if (strGender2.equals("2")) {
            lv.addView(layoutInflater.from(searchMaster.this).inflate(R.layout.content_search_gender, null), count);
            lvgender = (LinearLayout) findViewById(R.id.lv_gender);
            spGender = (Spinner) findViewById(R.id.search_spinner_gender);
            getGender();
            strGender2 = "0";
            count++;
        }
        if (strGotra2.equals("2")) {
            lv.addView(layoutInflater.from(searchMaster.this).inflate(R.layout.content_search_gotra_self, null), count);
            lvGotra = (LinearLayout) findViewById(R.id.lv_gotra);
            spGotraself = (Spinner) findViewById(R.id.search_spinner_gotra_self);
            getGotraSelf();
            strGotra2 = "0";
            count++;
        }
        if (strProfession2.equals("2")) {
            lv.addView(layoutInflater.from(searchMaster.this).inflate(R.layout.content_search_profession, null), count);
            lvProfession = (LinearLayout) findViewById(R.id.lv_profession);
            spProfession = (Spinner) findViewById(R.id.search_spinner_profession);
            getProfession();
            strProfession2 = "0";
            count++;
        }
        if (strSubcast2.equals("2")) {
            lv.addView(layoutInflater.from(searchMaster.this).inflate(R.layout.content_search_subcast, null), count);
            lvsubcast = (LinearLayout) findViewById(R.id.lv_subcast);
            spSubCast = (Spinner) findViewById(R.id.search_spinner_subcast);
            geSubcast();
            strSubcast2 = "0";
            count++;

        }
        if (strMaritalcount2.equals("2")) {
            lv.addView(layoutInflater.from(searchMaster.this).inflate(R.layout.content_search_marital_status, null), count);
            lvMaritailStatus = (LinearLayout) findViewById(R.id.lv_marital_status);
            spMaritalStatus = (Spinner) findViewById(R.id.search_spinner_maritial_status);
            getMaritialStatus();
            strMaritalcount2 = "0";
            count++;
        }
        if (strEducation2.equals("2")) {
            lv.addView(layoutInflater.from(searchMaster.this).inflate(R.layout.content_search_education, null), count);
            lvEducation = (LinearLayout) findViewById(R.id.lv_education);
            edEducation = (EditText) findViewById(R.id.search_ed_Education);
            count++;
            strEducation2 = "0";
            strpuEducation = "1";

        }

        //imgBack = (ImageView) findViewById(R.id.search1_image_back);
        btnSearch = (Button) findViewById(R.id.search_btn_search2);
        btnMore = (Button) findViewById(R.id.btn_more);
        lvFilter = (LinearLayout) findViewById(R.id.lv_filter);
    }

    private void geSubcast() {

        ArrayAdapter aa0 = new ArrayAdapter(searchMaster.this, android.R.layout.simple_spinner_item, MainActivity.CastList);
        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubCast.setAdapter(aa0);

        spSubCast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).toString().equals("Subcaste")) {
                    subcaste = "";
                } else {
                    subcaste = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getGotraSelf() {

        List<String> prStrings = new ArrayList<>();
        prStrings.add("Select");
        prStrings.add("gotra1");
        prStrings.add("gotra2");
        prStrings.add("gotra3");
        ArrayAdapter aa0 = new ArrayAdapter(searchMaster.this, android.R.layout.simple_spinner_item, prStrings);
        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGotraself.setAdapter(aa0);

        spGotraself.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("Select")) {
                    strGotraSelf = "";
                } else {
                    strGotraSelf = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getGender() {
        List<String> genderlidt = new ArrayList<>();
        genderlidt.add("Select");
        genderlidt.add("Male");
        genderlidt.add("Female");

        ArrayAdapter aa112 = new ArrayAdapter(searchMaster.this, android.R.layout.simple_spinner_item, genderlidt);
        aa112.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(aa112);

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).toString().equals("Select")) {
                    gender = "";
                } else {
                    gender = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getProfession() {

//        final ProgressDialog loading = ProgressDialog.show(EditProfile.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<Profession>> call1 = apiService.get_profession();
        call1.enqueue(new Callback<List<Profession>>() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<List<Profession>> call, retrofit2.Response<List<Profession>> response) {
//                loading.dismiss();

//                Log.e("get_profession", response.body().size() + "");

                List<String> prStrings = new ArrayList<>();
                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        professionlist.add(response.body().get(i));
                    }
                }

                if (professionlist.size() > 0) {
                    for (int i = 0; i < professionlist.size(); i++) {
                        if (i == 0) {
                            prStrings.add("Select");
                            prStrings.add(professionlist.get(i).getProfession());
                        } else {
                            prStrings.add(professionlist.get(i).getProfession());
                        }
                    }

                }

                ArrayAdapter aa11 = new ArrayAdapter(searchMaster.this, android.R.layout.simple_spinner_item, prStrings);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spProfession.setAdapter(aa11);

                spProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (adapterView.getItemAtPosition(i).toString().equals("Select")) {
                            profession = "";
                        } else {
                            profession = adapterView.getItemAtPosition(i).toString();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Profession>> call, Throwable t) {
//                loading.dismiss();

                Log.e("get_professionfail", t.getMessage() + "");
            }
        });
    }

    private void getOfficeCountry() {
        final ProgressDialog loading = ProgressDialog.show(searchMaster.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCountry>> call1 = apiService.get_country();
        call1.enqueue(new Callback<List<ModelCountry>>() {
            @Override
            public void onResponse(Call<List<ModelCountry>> call, retrofit2.Response<List<ModelCountry>> response) {
                loading.dismiss();

                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        CountryList.add(response.body().get(i));
                    }
                }

                final List<String> state = new ArrayList<>();
                state.add("Select");

                ArrayAdapter aa11 = new ArrayAdapter(searchMaster.this, android.R.layout.simple_spinner_item, state);
                aa11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spState.setAdapter(aa11);

                final List<String> city = new ArrayList<>();
                city.add("Select");

                ArrayAdapter aa1 = new ArrayAdapter(searchMaster.this, android.R.layout.simple_spinner_item, city);
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

                ArrayAdapter a = new ArrayAdapter(searchMaster.this, android.R.layout.simple_spinner_item, country);
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

        final ProgressDialog loading = ProgressDialog.show(searchMaster.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
//        Toast.makeText(getApplicationContext(),""+strCountryId,Toast.LENGTH_SHORT).show();
        Call<List<ModelState>> call1 = apiService.get_states(strCountryId);
        call1.enqueue(new Callback<List<ModelState>>() {
            @Override
            public void onResponse(Call<List<ModelState>> call, retrofit2.Response<List<ModelState>> response) {
                loading.dismiss();
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

                ArrayAdapter aa11 = new ArrayAdapter(searchMaster.this, android.R.layout.simple_spinner_item, state);
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
                loading.dismiss();
                Log.e("states", t.getMessage() + "");
            }
        });

    }

    private void getCity() {

        final ProgressDialog loading = ProgressDialog.show(searchMaster.this, "Fetching Data", "Please Wait..", false);
        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelCity>> call1 = apiService.get_city(strStateId);
        call1.enqueue(new Callback<List<ModelCity>>() {
            @Override
            public void onResponse(Call<List<ModelCity>> call, retrofit2.Response<List<ModelCity>> response) {
                loading.dismiss();

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

                ArrayAdapter aa11 = new ArrayAdapter(searchMaster.this, android.R.layout.simple_spinner_item, city);
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
                loading.dismiss();
                Log.e("city", t.getMessage() + "");
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
