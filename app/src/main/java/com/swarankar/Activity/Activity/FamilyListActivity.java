package com.swarankar.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.swarankar.Activity.Model.ModelFamily.Model;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.adapter.FamilyAdapter1;
import com.swarankar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamilyListActivity extends AppCompatActivity implements View.OnClickListener, FamilyAdapter1.RecyclerClickListner {
    RecyclerView rv_family_list;
    ImageView fab_add_family;
    ArrayList<Model> familyList = new ArrayList<>();
    String validate = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Family Details");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(this);

        rv_family_list = (RecyclerView) findViewById(R.id.rv_family_list);

        getFamilyData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_family_list.setLayoutManager(linearLayoutManager);
        /*FamilyAdapter1 familyAdapter1 = new FamilyAdapter1(this, familyList);
        rv_family_list.setAdapter(familyAdapter1);*/

        fab_add_family = (ImageView) findViewById(R.id.fab_add_family);
        if (validate.equalsIgnoreCase("false")) {
            fab_add_family.setVisibility(View.GONE);
        }
        fab_add_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FamilyListActivity.this, AddFamilyActivity.class);
                i.putExtra("query", (String[]) null);
                startActivity(i);
            }
        });

    }

    private void getFamilyData() {
        familyList.clear();
        final ProgressDialog loading = new ProgressDialog(FamilyListActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        Log.e(strUserid, strUserid);
        API apiservice = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = apiservice.get_relation_mobile(strUserid);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.dismiss();

                try {
                    String url = response.body().string();
                    JSONObject jsonObj = new JSONObject(url);
                    String validate = jsonObj.getString("validate");

                    if (validate.equalsIgnoreCase("true")) {
                        validate = "true";
                        fab_add_family.setVisibility(View.VISIBLE);
                        JSONObject nameObject = jsonObj.getJSONObject("grandfather_info");
                        String name4 = nameObject.getString("name");
                        Model m1 = new Model();
                        m1.setName(name4);
                        m1.setRelation("Grand Father");
                        familyList.add(m1);

                        JSONObject nameObject1 = jsonObj.getJSONObject("grandmother_info");
                        String name1 = nameObject1.getString("name");
                        Model m2 = new Model();
                        m2.setName(name1);
                        m2.setRelation("Grand Mother");
                        familyList.add(m2);

                        JSONObject nameObject2 = jsonObj.getJSONObject("father_info");
                        String name2 = nameObject2.getString("name");
                        Model m3 = new Model();
                        m3.setName(name2);
                        m3.setRelation("Father");
                        familyList.add(m3);

                        JSONObject nameObject3 = jsonObj.getJSONObject("mother_info");
                        String name3 = nameObject3.getString("name");
                        Model m4 = new Model();
                        m4.setName(name3);
                        m4.setRelation("Mother");
                        familyList.add(m4);

                        /*Husband Info*/
                        if (jsonObj.has("husband_info")) {
                            Model husbandModel = new Model();
                            JSONObject husbandObject = jsonObj.getJSONObject("husband_info");
                            String husbandName = husbandObject.getString("name");
                            if (husbandName.isEmpty()) {

                            } else {
                                if (husbandObject.has("children_info")) {
                                    JSONObject childObject = husbandObject.getJSONObject("children_info");

                                    if (childObject.has("son")) {
                                        JSONArray sonArray = childObject.getJSONArray("son");
                                        husbandModel.setSonName(sonArray);
                                    }

                                    if (childObject.has("daughter")) {
                                        JSONArray daughterArray = childObject.getJSONArray("daughter");
                                        husbandModel.setDaughterName(daughterArray);
                                    }
                                }
                                husbandModel.setName(husbandName);
                                husbandModel.setRelation("Husband");
                                husbandModel.setRelationInfo("Husband Information");
                                familyList.add(husbandModel);
                            }


                        }

                        /*Wife Info*/
                        if (jsonObj.has("wife_info")) {
                            Model wifeModel = new Model();
                            JSONObject wifeObject = jsonObj.getJSONObject("wife_info");
                            String wifeName = wifeObject.getString("name");
                            if (wifeName.isEmpty()) {

                            } else {

                                if (wifeObject.has("children_info")) {
                                    JSONObject childObject = wifeObject.getJSONObject("children_info");

                                    if (childObject.has("son")) {
                                        JSONArray sonArray = childObject.getJSONArray("son");
                                        wifeModel.setSonName(sonArray);
                                    }

                                    if (childObject.has("daughter")) {
                                        JSONArray daughterArray = childObject.getJSONArray("daughter");
                                        wifeModel.setDaughterName(daughterArray);
                                    }
                                }
                                wifeModel.setName(wifeName);
                                wifeModel.setRelation("Wife");
                                wifeModel.setRelationInfo("Wife Information");
                                familyList.add(wifeModel);
                            }
                        }


                        /*Brother Info*/
                        JSONArray brotherArray = jsonObj.getJSONArray("brother_info");
                        if (brotherArray != null) {
                            for (int j = 0; j < brotherArray.length(); j++) {
                                Model brotherModel = new Model();
                                brotherModel.setRelation("Brother");
                                brotherModel.setRelationInfo("Brother Information");
                                JSONObject brotherObject = brotherArray.getJSONObject(j);
                                String id = brotherObject.getString("id");
                                brotherModel.setId(id);
                                String brotherName = brotherObject.getString("name");
                                brotherModel.setName(brotherName);

                                if (brotherObject.has("wife_name")) {

                                    String wife_name = brotherObject.getString("wife_name");
                                    if (!wife_name.equalsIgnoreCase("") || wife_name != null || !wife_name.equalsIgnoreCase("null") || !wife_name.isEmpty()) {
                                        brotherModel.setPartnerName(wife_name);

                                    }
                                }

                                if (brotherObject.has("children_info")) {
                                    JSONObject childObject = brotherObject.getJSONObject("children_info");

                                    if (childObject.has("son")) {
                                        JSONArray sonArray = childObject.getJSONArray("son");
                                        brotherModel.setSonName(sonArray);
                                    }
                                    if (childObject.has("daughter")) {
                                        JSONArray daughterArray = childObject.getJSONArray("daughter");
                                        brotherModel.setDaughterName(daughterArray);
                                    }
                                }

                                familyList.add(brotherModel);
                            }
                        }


                        /*Sister Info*/
                        JSONArray sisterArray = jsonObj.getJSONArray("sister_info");
                        if (sisterArray != null) {
                            for (int j = 0; j < sisterArray.length(); j++) {
                                Model sisterModel = new Model();
                                sisterModel.setRelation("Sister");
                                sisterModel.setRelationInfo("Sister Information");
                                JSONObject sisterObject = sisterArray.getJSONObject(j);
                                String id = sisterObject.getString("id");
                                sisterModel.setId(id);
                                String sisterName = sisterObject.getString("name");
                                sisterModel.setName(sisterName);

                                if (sisterObject.has("husband_name")) {
                                    String husband_name = sisterObject.getString("husband_name");
                                    if (!husband_name.equalsIgnoreCase("") || !husband_name.isEmpty()) {
                                        sisterModel.setPartnerName(husband_name);
                                    }
                                    sisterModel.setPartnerName(husband_name);
                                }

                                if (sisterObject.has("children_info")) {
                                    JSONObject childObject = sisterObject.getJSONObject("children_info");

                                    if (childObject.has("son")) {
                                        JSONArray sonArray = childObject.getJSONArray("son");
                                        sisterModel.setSonName(sonArray);
                                    }
                                    if (childObject.has("daughter")) {
                                        JSONArray daughterArray = childObject.getJSONArray("daughter");
                                        sisterModel.setDaughterName(daughterArray);
                                    }
                                }
                                familyList.add(sisterModel);
                            }
                        }
/*                        FamilyAdapter1 familyAdapter1 = new FamilyAdapter1(FamilyListActivity.this, familyList, FamilyListActivity.this);
                        rv_family_list.setAdapter(familyAdapter1);*/
                    } else if (validate.equalsIgnoreCase("false")) {
                        validate = "false";
                        fab_add_family.setVisibility(View.GONE);
                        Intent i = new Intent(FamilyListActivity.this, FamilyActivity.class);
                        i.putExtra("ins_upd", "0");
                        startActivity(i);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("j.........", e.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONException", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void deleteItem(int position) {
        JSONArray idArray = new JSONArray();
        JSONObject idObject = new JSONObject();
        try {
            idObject.put("id", familyList.get(position).getId());
            idArray.put(idObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("id", idArray.toString());

        deleteFamilyData(idArray.toString(), position);
        familyList.remove(position);
        getFamilyData();
    }

    private void deleteFamilyData(String jsonArray, int position) {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = null;
        switch (familyList.get(position).getRelation()) {
            case "Brother":
                call1 = apiService.add_brother(strUserid, jsonArray, "2");
                break;
            case "Sister":
                call1 = apiService.add_sister(strUserid, jsonArray, "2");
                break;
            case "Wife":
                call1 = apiService.add_wife(strUserid, jsonArray, "2");
                break;
            case "Husband":
                call1 = apiService.add_husband(strUserid, jsonArray, "2");
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
                        //dialog(mes + "", AddFamilyActivity.this);
                    } else {
                        //dialog(mes + "", AddFamilyActivity.this);
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

    /*@Override
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
