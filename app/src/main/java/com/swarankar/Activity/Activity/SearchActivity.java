package com.swarankar.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.swarankar.Activity.Model.ModelSearch.ModelSearch;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.adapter.SearchAdapter;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity {

    LinearLayout lvSearch1, lvSearch2;
    Button btnSearch;
    RecyclerView searchRecyclerview;
    ImageView imgDropDown;
    public static String strSearchCount = "0";
    EditText edName;

    LinearLayout lvFilter;
    String country, state, city, gender, gotraself, subcast, profession, education, name, maritial1;
    List<ModelSearch> searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);
        findView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(SearchActivity.this);
                finish();
            }
        });
        if (strSearchCount.equals("0")) {

        } else {
            Bundle p = getIntent().getExtras();
            country = p.getString("country");
            state = p.getString("state");
            city = p.getString("city");
            gender = p.getString("gender");
            gotraself = p.getString("gotraself");
            subcast = p.getString("subcast");
            profession = p.getString("profession");
            education = p.getString("education");
            name = p.getString("name");
            maritial1 = p.getString("strMaritail");
            strSearchCount = "0";

            String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
            Log.e("strUserid", strUserid);
            Log.e("name", name);
            Log.e("country", country);
            Log.e("state", state);
            Log.e("city", city);
            Log.e("gender", gender);
            Log.e("gotraself", gotraself);
            Log.e("subcast", subcast);
            Log.e("profession", profession);
            Log.e("maritial1", maritial1);

            final ProgressDialog loading = new ProgressDialog(SearchActivity.this);
            loading.setMessage("Please Wait..");
            loading.setCancelable(false);
            loading.setCanceledOnTouchOutside(false);
            loading.show();

//            if(country.equals("Select")){
//                country = "";
//            }
//            if(state.equals("Select")){
//                state = "";
//            }
//            if(city.equals("Select")){
//                city = "";
//            }
//            if(gender.equals("Select")){
//                gender = "";
//            }
//            if(gotraself.equals("Select")){
//                gotraself = "";
//            }
//            if(profession.equals("Select")){
//                profession = "";
//            }
//            if(subcast.equals("Subcaste")){
//                subcast = "";
//            }

//        final ProgressDialog loading = ProgressDialog.show(LoginActivity.this, "Fetching Data", "Please Wait..", false);
            API apiService = APIClient.getClient().create(API.class);
            Call<List<ModelSearch>> call1 = apiService.search(strUserid,
                    "",
                    name,
                    country,
                    state,
                    city,
                    subcast,
                    profession,
                    gender,
                    education,
                    gotraself,
                    maritial1);
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
                            searchRecyclerview.setLayoutManager(linearLayoutManager);
                            SearchAdapter adapter = new SearchAdapter(getApplicationContext(), searchList);
                            searchRecyclerview.setAdapter(adapter);
                        }
                    } else {
                        Constants.buildDialogmno("No data found", SearchActivity.this);
                    }
                }


                @Override
                public void onFailure(Call<List<ModelSearch>> call, Throwable t) {
                    loading.dismiss();
                    Log.e("loginData", t.getMessage() + "");
                }
            });
        }


        lvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchActivity.this, searchMaster.class);
                i.putExtra("name", edName.getText().toString());
                startActivity(i);
                finish();
//                lvSearch2.setVisibility(View.VISIBLE);
//                lvSearch1.setVisibility(View.GONE);
            }
        });


       /* imgBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                AndroidUtils.hideSoftKeyboard(SearchActivity.this);
                finish();
            }
        });*/

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edName.getText().toString().trim().isEmpty()) {
                    edName.setError("Enter Search Criteria!");
                } else {
                    AndroidUtils.hideSoftKeyboard(SearchActivity.this);
                    String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
                    final ProgressDialog loading = new ProgressDialog(SearchActivity.this);
                    loading.setMessage("Please Wait..");
                    loading.setCancelable(false);
                    loading.setCanceledOnTouchOutside(false);
                    loading.show();
//

//        final ProgressDialog loading = ProgressDialog.show(LoginActivity.this, "Fetching Data", "Please Wait..", false);
                    API apiService = APIClient.getClient().create(API.class);
                    Call<List<ModelSearch>> call1 = apiService.search(strUserid,
                            "",
                            edName.getText().toString(),
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "");
                    call1.enqueue(new Callback<List<ModelSearch>>() {
                        @Override
                        public void onResponse(Call<List<ModelSearch>> call, retrofit2.Response<List<ModelSearch>> response) {
                            loading.dismiss();
                            searchList = new ArrayList<>();
                            if (response.body() != null) {
                                searchList = response.body();
                                if (searchList.size() > 0) {
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                    searchRecyclerview.setLayoutManager(linearLayoutManager);
                                    SearchAdapter adapter = new SearchAdapter(getApplicationContext(), searchList);
                                    searchRecyclerview.setAdapter(adapter);
                                }
                            } else {
                                Constants.buildDialogmno("No data found", SearchActivity.this);
                            }


//                        lvSearch1.setVisibility(View.VISIBLE);
//                        lvSearch2.setVisibility(View.GONE);
//                        String strResponse = response.body().getResponse();
//                        String strMessage = response.body().getMessage();
//                        Log.e("strMessage", strMessage + "");
//                        String strUserId = String.valueOf(response.body().getUserId());

//                        try {
//                            Log.e("response",response.body().string()+"");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }

//
                        }

                        @Override
                        public void onFailure(Call<List<ModelSearch>> call, Throwable t) {
                            loading.dismiss();
                            Log.e("loginData", t.getMessage() + "");
                        }
                    });
                }
            }
        });
    }

    private void findView() {
        lvSearch1 = (LinearLayout) findViewById(R.id.layout_serarch1);
        lvSearch2 = (LinearLayout) findViewById(R.id.layout_serarch2);
        searchRecyclerview = (RecyclerView) findViewById(R.id.search_recyclerview);
        //imgBack = (ImageView) findViewById(R.id.search_image_back);
        imgDropDown = (ImageView) findViewById(R.id.img_search_category);
        btnSearch = (Button) findViewById(R.id.search_btn_search1);
        edName = (EditText) findViewById(R.id.search_ed_name);
        edName.requestFocus();
        AndroidUtils.showSoftKeyboard(SearchActivity.this, edName);

        lvFilter = (LinearLayout) findViewById(R.id.seach_layout_modify_filter);
//        imgClose = (ImageView)findViewById(R.id.close_search);

    }



   /* @Override
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
    }*/
}
