package com.swarankar.Activity.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.swarankar.Activity.Model.ModelSearch.ModelSearch;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.adapter.SearchAdapter;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchResultActivity extends AppCompatActivity {
    RecyclerView rvSearchResult;
    List<ModelSearch> searchList;

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

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvSearchResult = (RecyclerView) findViewById(R.id.search_recyclerview);
        firstNameList = getIntent().getStringArrayListExtra("firstname");
        lastNameList = getIntent().getStringArrayListExtra("lastname");
        emailList = getIntent().getStringArrayListExtra("email");
        mobileNumberList = getIntent().getStringArrayListExtra("mobile");
        subcasteList = getIntent().getStringArrayListExtra("subcaste");
        memberIdList = getIntent().getStringArrayListExtra("memberid");
        gotraSelfList = getIntent().getStringArrayListExtra("gotraself");
        gotraMotherList = getIntent().getStringArrayListExtra("gotramother");
        maritalstatusList = getIntent().getStringArrayListExtra("maritalsstatus");
        dobList = getIntent().getStringArrayListExtra("dob");
        professionList = getIntent().getStringArrayListExtra("profession");
        professionOrgNameList = getIntent().getStringArrayListExtra("professionorganization");
        professionStatusList = getIntent().getStringArrayListExtra("professionstatus");
        professionIndustryList = getIntent().getStringArrayListExtra("professionindustry");
        professionDesignationList = getIntent().getStringArrayListExtra("professiondesignation");
        officeLocalityList = getIntent().getStringArrayListExtra("officelocality");
        officeCountryList = getIntent().getStringArrayListExtra("officecountry");
        officeStateList = getIntent().getStringArrayListExtra("officestate");
        officeDistrictList = getIntent().getStringArrayListExtra("officedistrict");
        educationalQualificationList = getIntent().getStringArrayListExtra("educationalqualification");
        educationalInstituteList = getIntent().getStringArrayListExtra("educationalinstitute");
        statusEducationList = getIntent().getStringArrayListExtra("educationstatus");
        areaStudyList = getIntent().getStringArrayListExtra("areastudy");
        resConstituencyList = getIntent().getStringArrayListExtra("resconstituency");
        resLocalityList = getIntent().getStringArrayListExtra("reslocality");
        resWardList = getIntent().getStringArrayListExtra("resward");
        resVillageList = getIntent().getStringArrayListExtra("resvillage");
        resTehsilList = getIntent().getStringArrayListExtra("restehsil");
        residenceCountryList = getIntent().getStringArrayListExtra("rescountry");
        residenceStateList = getIntent().getStringArrayListExtra("resstate");
        residenceDistrictList = getIntent().getStringArrayListExtra("resdistrict");
        permanentConstituencyList = getIntent().getStringArrayListExtra("perconstituency");
        permanentLocalityList = getIntent().getStringArrayListExtra("perlocality");
        permanentWardList = getIntent().getStringArrayListExtra("perward");
        permanentVillageList = getIntent().getStringArrayListExtra("pervillage");
        permanentTehsilList = getIntent().getStringArrayListExtra("pertehsil");
        permanentCountryList = getIntent().getStringArrayListExtra("percountry");
        permanentStateList = getIntent().getStringArrayListExtra("perstate");
        permanentDistrictList = getIntent().getStringArrayListExtra("perdistrict");

        Log.e("json", getIntent().getStringExtra("searchRequest"));
        //getSearchresults();
    }

    private void getSearchresults() {
        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");

        final ProgressDialog loading = new ProgressDialog(SearchResultActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<List<ModelSearch>> call1 = apiService.advance_search(strUserid,
                "",
                getIntent().getStringExtra("searchRequest"));
        call1.enqueue(new Callback<List<ModelSearch>>() {
            @Override
            public void onResponse(Call<List<ModelSearch>> call, retrofit2.Response<List<ModelSearch>> response) {
                loading.dismiss();

                searchList = new ArrayList<>();
                if (response.body() != null) {
                    Log.e("search :", response.body().toString());
                    searchList = response.body();
                    if (searchList.size() > 0) {
                        Log.e("searchlist", String.valueOf(searchList.size()));
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        rvSearchResult.setLayoutManager(linearLayoutManager);
                        SearchAdapter adapter = new SearchAdapter(SearchResultActivity.this, searchList);
                        rvSearchResult.setAdapter(adapter);
                    }
                } else {
                    Constants.buildDialogmno("No data found", SearchResultActivity.this);
                }
            }

            @Override
            public void onFailure(Call<List<ModelSearch>> call, Throwable t) {
                loading.dismiss();
                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

}
