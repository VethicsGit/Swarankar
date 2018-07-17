package com.swarankar.Activity.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.swarankar.Activity.Model.ModelFamily.Model;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.Activity.adapter.FamilyAdapter;
import com.swarankar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Family1Activity extends AppCompatActivity {
    //    {"brother_info":[],"father_info":[],"mother_info":[],"sister_info":[],"child_info":[],"wife_info":[],"husband_info":[]}
    RecyclerView familyRecyclerviewFamily, familyRecyclerviewSister, familyRecyclerviewMother, familyRecyclerviewFather, familyRecyclerviewWife, familyRecyclerviewChild, familyRecyclerviewHusband;
    ImageView imgback;

    List<Model> familyList = new ArrayList<>();
//    List<BrotherInfo> brotherList = new ArrayList<>();
//    List<SisterInfo> sisterList = new ArrayList<>();
//    List<MotherInfo> motherList = new ArrayList<>();
//    List<FatherInfo> fatherList = new ArrayList<>();
//    List<> childList = new ArrayList<>();
//    List<> wifeList = new ArrayList<>();
//    List<SisterInfo> husbandList = new ArrayList<>();


    Button btn_add, btn_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family1);

        findView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        familyRecyclerviewFamily.setLayoutManager(linearLayoutManager);
        imgback.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getFamilyData();
    }

    private void findView() {

        btn_add = (Button) findViewById(R.id.btn_edit);
        btn_update = (Button) findViewById(R.id.family1Activity_btn_update);
        imgback = (ImageView) findViewById(R.id.family1_image_back);
        familyRecyclerviewFamily = (RecyclerView) findViewById(R.id.family_recyclerview_brother);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Family1Activity.this, Family.class);
                startActivity(intent);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Family1Activity.this, UpdateFamily.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        Intent i = new Intent(Family1Activity.this, Family1Activity.class);
        finish();
        startActivity(i);
    }

    private void getFamilyData() {

        final ProgressDialog loading = new ProgressDialog(Family1Activity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        Log.e("userid :", strUserid);
        API apiservice = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = apiservice.get_relation_mobile(strUserid);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.dismiss();
//                try {
//                    Log.e("family",response.body().string()+"");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                 brotherList.addAll(response.body().getBrotherInfo());
//                 sisterList.addAll(response.body().getSisterInfo());
////                husbandList.addAll(response.body().getFatherInfo());
////                wifeList.addAll(response.body().getWifeInfo());
//
//                motherList.addAll(response.body().getMotherInfo());
//                fatherList.addAll(response.body().getFatherInfo());
//
//                Log.e("sissize", String.valueOf(sisterList.size()));
//
//
//                FamilyAdapter  adapter = new FamilyAdapter(getApplicationContext(),brotherList);
//                familyRecyclerviewFamily.setAdapter(adapter);

                try {
//                    Log.e("family", response.body().string() + "");
                    String url = response.body().string();
                    Log.e("family", url);
                    JSONObject jsonObj = new JSONObject(url);

                    JSONArray btother = jsonObj.getJSONArray("brother_info");
                    for (int i = 0; i < btother.length(); i++) {
                        JSONObject sis = btother.getJSONObject(i);
                        String name = sis.getString("name");
                        String relation = sis.getString("relation");

                        String btotherwife = sis.getString("brotherwife");
                        String btothechild = sis.getString("child");

                        if (btotherwife.length() > 0) {
                            Model m = new Model();
                            m.setName(btotherwife);
                            m.setRelation("Brother's wife");
                            familyList.add(m);
                        }

                        if (btothechild.length() > 0) {
                            Model m = new Model();
                            m.setName(btothechild);
                            m.setRelation("Brother's Children");
                            familyList.add(m);
                        }

                        Model m = new Model();
                        m.setName(name);
                        m.setRelation(relation);
                        familyList.add(m);
                    }

                    JSONArray father = jsonObj.getJSONArray("father_info");
                    for (int i = 0; i < father.length(); i++) {
                        JSONObject sis = father.getJSONObject(i);
                        String name = sis.getString("name");
                        String relation = sis.getString("relation");

                        Model m = new Model();
                        m.setName(name);
                        m.setRelation(relation);
                        familyList.add(m);
                    }


                    JSONArray mother = jsonObj.getJSONArray("mother_info");
                    for (int i = 0; i < mother.length(); i++) {
                        JSONObject sis = mother.getJSONObject(i);
                        String name = sis.getString("name");
                        String relation = sis.getString("relation");

                        Model m = new Model();
                        m.setName(name);
                        m.setRelation(relation);
                        familyList.add(m);
                    }

                    JSONArray sister = jsonObj.getJSONArray("sister_info");
                    for (int i = 0; i < sister.length(); i++) {
                        JSONObject sis = sister.getJSONObject(i);
                        String name = sis.getString("name");

                        String relation = sis.getString("relation");


                        String sisterrwife = sis.getString("s_husbund_name");
                        String sisterchild = sis.getString("s_child");

                        if (sisterrwife.length() > 0) {
                            Model m = new Model();
                            m.setName(sisterrwife);
                            m.setRelation("Sister's husband");
                            familyList.add(m);
                        }

                        if (sisterchild.length() > 0) {
                            Model m = new Model();
                            m.setName(sisterchild);
                            m.setRelation("Sister's Children");
                            familyList.add(m);
                        }
                        Model m = new Model();
                        m.setName(name);
                        m.setRelation(relation);

                        familyList.add(m);
                    }


                    JSONArray child = jsonObj.getJSONArray("child_info");
                    for (int i = 0; i < child.length(); i++) {
                        JSONObject sis = child.getJSONObject(i);
                        String name = sis.getString("name");
                        String relation = sis.getString("relation");

                        Model m = new Model();
                        m.setName(name);
                        m.setRelation(relation);
                        familyList.add(m);
                    }

                    JSONArray wife = jsonObj.getJSONArray("wife_info");
                    for (int i = 0; i < wife.length(); i++) {
                        JSONObject sis = wife.getJSONObject(i);
                        String name = sis.getString("name");
                        String relation = sis.getString("relation");

                        Model m = new Model();
                        m.setName(name);
                        m.setRelation(relation);
                        familyList.add(m);
                    }
                    JSONArray husband = jsonObj.getJSONArray("husband_info");
                    for (int i = 0; i < husband.length(); i++) {
                        JSONObject sis = husband.getJSONObject(i);
                        String name = sis.getString("name");
                        String relation = sis.getString("relation");

                        Model m = new Model();
                        m.setName(name);
                        m.setRelation(relation);
                        familyList.add(m);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("j.........", e.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSONException", e.getMessage());
                }
                FamilyAdapter adapter = new FamilyAdapter(getApplicationContext(), familyList);
                Log.e("familysize", String.valueOf(familyList.size()));
                familyRecyclerviewFamily.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
            }
        });
    }


}
