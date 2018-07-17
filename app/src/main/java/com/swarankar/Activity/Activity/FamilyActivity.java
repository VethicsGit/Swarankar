package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.swarankar.Activity.Model.ProfileDetails.ModelProfileDetails;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.Activity.Utils.Constants;
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

public class FamilyActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText grand_father_name, grand_mother_name, father_name, mother_name;
    Button family_submit;
    ProgressDialog loading;
    String insert_update = "0";

    String[] relations = {"Grand Father", "Grand Mother", "Father", "Mother"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Family Details");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_48dp);
        toolbar.setNavigationOnClickListener(this);

        grand_father_name = (EditText) findViewById(R.id.edt_grand_father_name);
        grand_father_name.requestFocus();
        AndroidUtils.showSoftKeyboard(FamilyActivity.this, grand_father_name);
        grand_mother_name = (EditText) findViewById(R.id.edt_grand_mother_name);
        father_name = (EditText) findViewById(R.id.edt_father_name);
        mother_name = (EditText) findViewById(R.id.edt_mother_name);
        family_submit = (Button) findViewById(R.id.btn_family_submit);

        getData();

        Intent i = getIntent();
        if (i!=null) {

            if (i.hasExtra("ins_upd")) {
                String ins_upd = i.getStringExtra("ins_upd");
                if (ins_upd.equals("1")) {
                    ArrayList<String> updateList = i.getStringArrayListExtra("updateList");
                    Log.e("updateList", updateList.toString());
                    if (!updateList.isEmpty()) {
                        insert_update = "1";
                        grand_father_name.setText(AndroidUtils.wordFirstCap(updateList.get(0)));
                        grand_mother_name.setText(AndroidUtils.wordFirstCap(updateList.get(1)));
                        father_name.setText(AndroidUtils.wordFirstCap(updateList.get(2)));
                        mother_name.setText(AndroidUtils.wordFirstCap(updateList.get(3)));
                    } else {
                        insert_update = "0";
                    }
                }
            }
        }


        family_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtils.hideSoftKeyboard(FamilyActivity.this);

                String grandfathername = grand_father_name.getText().toString().trim();
                String grandmothername = grand_mother_name.getText().toString().trim();
                String fathername = father_name.getText().toString().trim();
                String mothername = mother_name.getText().toString().trim();
                //String wifename = wife_husband_name.getText().toString().trim();

                if (grandfathername.isEmpty()) {
                    grand_father_name.setError("Please Enter Grand Father Name!");
                } else if (grandmothername.isEmpty()) {
                    grand_mother_name.setError("Please Enter Grand Mother Name!");
                } else if (fathername.isEmpty()) {
                    father_name.setError("Please Enter Father Name!");
                } else if (mothername.isEmpty()) {
                    mother_name.setError("Please Enter Mother Name!");
                } else {
                    ArrayList<String> relationList = new ArrayList<>();
                    relationList.add(grandfathername);
                    relationList.add(grandmothername);
                    relationList.add(fathername);
                    relationList.add(mothername);

                    ArrayList<String> relationNameList = new ArrayList<>();
                    /*relationNameList.add("grandfather_info");
                    relationNameList.add("grandmother_info");
                    relationNameList.add("father_info");
                    relationNameList.add("mother_info");*/

                    String[] familynames = {grandfathername, grandmothername, fathername, mothername};
                    try {
                        for (int i = 0; i < relationList.size(); i++) {
                            JSONObject jsonObject = new JSONObject();
                            JSONArray jsonArray = new JSONArray();
                            jsonObject.put("name", relationList.get(i));
                            jsonArray.put(jsonObject);
                            relationNameList.add(jsonArray.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    addFamilyData(relationNameList);
                }
            }
        });

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
            }

            @Override
            public void onFailure(Call<ModelProfileDetails> call, Throwable t) {
                loading.dismiss();
                Log.e("loginData", t.getMessage() + "");
            }
        });

    }

    private void addFamilyData(ArrayList<String> relationList) {
        Log.e("relationList", relationList.toString());

        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        // TODO My Changes
        Call<ResponseBody> call1 = apiService.add_family(strUserid, relationList.get(0), relationList.get(1), relationList.get(2), relationList.get(3), "0");
        Log.e("call1", call1.toString());

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                loading.dismiss();

                try {
                    Log.e("respone", response.body().string());
                    String strUrl = response.body().string();
                    Log.e("response1", strUrl);
                    JSONObject jsonObject = new JSONObject(strUrl);
                    String res = jsonObject.getString("response");
                    String mes = jsonObject.getString("message");

                    if (res.equals("Success")) {
                        dialog(mes + "", FamilyActivity.this);
                    } else {
                        dialog(mes + "", FamilyActivity.this);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(FamilyActivity.this, FamilyListActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Log.e("loginData", t.getMessage() + "");
            }
        });
    }

    private void dialog(String s, FamilyActivity familyActivity) {
        final Dialog alertDialog = new Dialog(familyActivity);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().getDecorView().setBackground(ContextCompat.getDrawable(familyActivity, R.drawable.drawable_back_dialog));
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;

        alertDialog.getWindow().setAttributes(lp);
        View rootView = LayoutInflater.from(familyActivity).inflate(R.layout.view_alert_dialog, null);
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
                finish();

            }
        });
        alertDialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        AndroidUtils.hideSoftKeyboard(this);
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        AndroidUtils.hideSoftKeyboard(this);
        Intent i = new Intent(this, HomeActivity.class);
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
