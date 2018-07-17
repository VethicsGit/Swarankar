package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.swarankar.Activity.Model.ModelFamily.Model;
import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.Constants;
import com.swarankar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFamily extends AppCompatActivity {

    ImageView imgBack;
    RecyclerView rCFamilyUpdate;
    Button btnUpdate;
    List<String> staticfamily = new ArrayList<>();
    List<String> staticname = new ArrayList<>();
    List<Model> familyList = new ArrayList<>();
    String strRelation;


    ArrayList<String> values = new ArrayList<String>();
    int relationid = 0;

    HashMap<Integer, Integer> hm = new HashMap<>();

    ArrayList<String> spinnervalues = new ArrayList<String>();

    int nameaddornt = 0;

    List<Integer> namedaa = new ArrayList<>();
    List<Integer> spinnerid = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_family);

        imgBack = (ImageView) findViewById(R.id.update_family_image_back);
        btnUpdate = (Button) findViewById(R.id.update_family_btn_update);
        rCFamilyUpdate = (RecyclerView) findViewById(R.id.update_family_recyclerview);


//        staticfamily.add("father");
//        staticfamily.add("mother");
//        staticfamily.add("brother");
//        staticfamily.add("childrens");
//        staticfamily.add("sister");
////        staticfamily.add("sister");
//
//        staticname.add("father");
//        staticname.add("mother");
//        staticname.add("brother");
//        staticname.add("childrens");
//        staticname.add("sister");
////        staticname.add("sister");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getFamilyData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rCFamilyUpdate.setLayoutManager(linearLayoutManager);
//        FamilyUpdateAdapter adapter = new FamilyUpdateAdapter(getApplicationContext(), staticfamily,staticname);
//        rCFamilyUpdate.setAdapter(adapter);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                values.clear();
                spinnervalues.clear();
                nameaddornt = 0;
                Log.e("staticname", String.valueOf(staticname));
                Log.e("staticFamily", String.valueOf(staticfamily));

                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < staticfamily.size(); i++) {

                    JSONObject jsonObject = new JSONObject();


                    try {
                        if (staticfamily.get(i).trim().toLowerCase().equals("father")) {
                            jsonObject.put("relation", "father");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("mother")) {
                            jsonObject.put("relation", "mother");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("brother")) {
                            jsonObject.put("relation", "brother");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("sister")) {
                            jsonObject.put("relation", "sister");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("brother's wife")) {
                            jsonObject.put("relation", "brother_wife");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("brother's children")) {
                            jsonObject.put("relation", "brother_children");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("sister's husband")) {
                            jsonObject.put("relation", "sister_husbund");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("sister's children")) {
                            jsonObject.put("relation", "sister_children");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("wife")) {
                            jsonObject.put("relation", "wife");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("Son")) {
                            jsonObject.put("relation", "Son");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("Daughter")) {
                            jsonObject.put("relation", "Daughter");
                        } else if (staticfamily.get(i).trim().toLowerCase().equals("husband")) {
                            jsonObject.put("relation", "husban");
                        }

                        jsonObject.put("name", staticname.get(i));


                        jsonArray.put(jsonObject);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("jsonArray", jsonArray.toString() + "");

//                    JSONObject studentsObj = new JSONObject();
//                    try {
//                        studentsObj.put("a", jsonArray);
//                        Log.e("jsonObj",studentsObj+"");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                byte[] data = new byte[0];
                try {
                    data = (jsonArray.toString()).getBytes("UTF-8");
                    String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                    Log.e("relationarray", base64);

                   // addFamilyData(base64);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

//                    Log.e("name", String.valueOf( namedaa.get(0)));
//                for (int id :  namedaa) {
//                    EditText t = (EditText) findViewById(id);
//                    if (t.getText().toString().equals(""))
//                    {
//                        nameaddornt = 1;
//                        values.add("0");
////                        t.setError("Enter Name");
//
//                    }
//                    else
//                    {
//                        values.add(t.getText().toString());
//                        Log.e("nameValue....", t.getText().toString()+ "");
//
//                    }
//
//                }


//                Spinner mySpinner=(Spinner) findViewById(0);
//                String text = mySpinner.getSelectedItem().toString();
//                Log.e("spinnervaluessss", text);
////
//                Log.e("spinnerid", String.valueOf(spinnerid));
//                for (int id = 0 ;id<=4; id++)
//                {
//
////                    Spinner mySpinner=(Spinner) findViewById(id);
////                    String text = mySpinner.getSelectedItem().toString();
//                    Spinner t = (Spinner) findViewById(id);
//
//
//
////                    if(!(t.getSelectedItem().toString().trim().equals(null))){
//                        spinnervalues.add(t.getSelectedItem() + "");
////                    }else {
////
////                        Log.e("null", String.valueOf(spinnerid));
////                    }
//
//
//                    Log.e("spinnervaluessss", String.valueOf(spinnervalues));
//
//                }
            }
        });
    }

    /*private void addFamilyData(String base64) {

        final ProgressDialog loading = new ProgressDialog(UpdateFamily.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = apiService.spinner_add_family(strUserid, base64);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                loading.dismiss();


//                try {
//                    Log.e("responseFamily",response.body().string()+"");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                try {
                    String strUrl = response.body().string();
                    JSONObject jsonObject = new JSONObject(strUrl);
                    String res = jsonObject.getString("response");
                    String mes = jsonObject.getString("message");


                    if (res.equals("Success")) {
                        dialog(mes + "", UpdateFamily.this);
                    } else {
                        dialog(mes + "", UpdateFamily.this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();

                Log.e("loginData", t.getMessage() + "");
            }
        });
    }*/


    private void dialog(String s, UpdateFamily family) {
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
                finish();

            }
        });
        alertDialog.show();

    }


    private void getFamilyData() {

        final ProgressDialog loading = new ProgressDialog(UpdateFamily.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiservice = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = apiservice.get_relation_mobile(strUserid);
        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.dismiss();
//
                try {
//                    Log.e("family", response.body().string() + "");
                    String url = response.body().string();
                    Log.e("family", url);
                    JSONObject jsonObj = new JSONObject(url);

                    JSONArray btother = jsonObj.getJSONArray("brother_info");
                    for (int i = 0; i < btother.length(); i++) {
                        JSONObject sis = btother.getJSONObject(i);
                        String name = sis.getString("name");
                        Log.e("bro", "BB" + name);
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


                for (int i = 0; i < familyList.size(); i++) {

                    String relation = familyList.get(i).getRelation();
                    staticfamily.add(relation);
                }

                for (int i = 0; i < familyList.size(); i++) {

                    String relation = familyList.get(i).getName();
                    staticname.add(relation);
                }

                FamilyUpdateAdapter adapter = new FamilyUpdateAdapter(getApplicationContext(), staticfamily, staticname);
                Log.e("familysize", String.valueOf(familyList.size()));
                rCFamilyUpdate.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
            }
        });
    }

    class FamilyUpdateAdapter extends RecyclerView.Adapter<FamilyUpdateAdapter.ViewHolder> {
        public AdapterView.OnItemClickListener onItemClickListener;

        private Context context;
        private List<Model> familyList;
        private String strName;

        private LayoutInflater inflater;
        private List<String> staticfamily;
        private List<String> staticname;


//        public FamilyUpdateAdapter(Context context, List<Model> familyList) {
//            this.context = context;
//            this.familyList = familyList;
//
//        }

        public FamilyUpdateAdapter(Context context, List<String> staticfamily, List<String> staticname) {
            this.context = context;
            this.staticfamily = staticfamily;
            this.staticname = staticname;
//            setHasStableIds(true);

        }

        public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }

        public int getItemCount() {
            return staticfamily.size();

        }


        public FamilyUpdateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
            return new FamilyUpdateAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.update_family_item, parent, false), this);


        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


            strRelation = staticfamily.get(position);


//            final ModelUpdateFamily model = new ModelUpdateFamily();
//            model.setRelation(strRelation);
//            relatioList.add(model);


            Log.e("strRlation", strRelation);
            strName = staticname.get(position);
            viewHolder.edName.setText(strName);

            viewHolder.edName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    Log.e("RelatioName", String.valueOf(staticname));
                    String name = viewHolder.edName.getText().toString();
                    staticname.set(position, name);
                    Log.e("RelatioName", String.valueOf(staticname));
                }
            });


//            viewHolder.spRelation.setId(relationid);
//            Log.e("relationid", String.valueOf(relationid));
//            relationid++;

//            spinnerid.add(viewHolder.spRelation.getId());
//
//
//            namedaa.add(viewHolder.edName.getId());

            List<String> prStrings = new ArrayList<>();
            prStrings.add("Select");
            prStrings.add("Father");
            prStrings.add("Mother");
            prStrings.add("Brother");
            prStrings.add("Sister");
            prStrings.add("Wife");
            prStrings.add("Husband");
            prStrings.add("Son");
            prStrings.add("Daughter");
            prStrings.add("Brother's wife");
            prStrings.add("Brother's children");
            prStrings.add("Sister's Husband");
            prStrings.add("Sister's children");


            ArrayAdapter aa0 = new ArrayAdapter(context, R.layout.dynamic_spinner_itemupload2, prStrings);
            aa0.setDropDownViewResource(R.layout.spinner_dropdown_item);
            viewHolder.spRelation.setAdapter(aa0);

            for (int j = 0; j < prStrings.size(); j++) {


                String strrel = "";


                if (strRelation.equals("father")) {
                    strrel = "Father";
                } else if (strRelation.equals("mother")) {
                    strrel = "Mother";
                } else if (strRelation.equals("brother")) {
                    strrel = "Brother";
                } else if (strRelation.equals("sister")) {
                    strrel = "Sister";
                } else if (strRelation.equals("Brother's wife")) {
                    strrel = "Brother's wife";
                } else if (strRelation.equals("Brother's Children")) {
                    strrel = "Brother's children";
                } else if (strRelation.equals("Sister's husband")) {
                    strrel = "Sister's Husband";
                } else if (strRelation.equals("Sister's Children")) {
                    strrel = "Sister's children";
                } else if (strRelation.equals("wife")) {
                    strrel = "Wife";
                } else if (strRelation.equals("son")) {
                    strrel = "Son";
                } else if (strRelation.equals("Daughter")) {
                    strrel = "daughter";
                } else if (strRelation.equals("husband")) {
                    strrel = "Husband";
                }

                if (prStrings.get(j).toString().trim().equals(strrel)) {
//                    viewHolder.spRelation.setSelection(j);
                    hm.put(position, j);
                    Log.e("hm", String.valueOf(hm));

                }
                if (hm.containsKey(position)) {


                    viewHolder.spRelation.setSelection(hm.get(position));

                }

            }


            viewHolder.spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    ((TextView) view).setTextColor(Color.BLACK);

                    strRelation = adapterView.getItemAtPosition(i).toString();
                    staticfamily.set(position, strRelation);
                    Log.e("RelatioArray1", String.valueOf(staticfamily));


                    hm.put(position, i);

                    Log.e("hm1", String.valueOf(hm));
//
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


//            String[] items = strRelation.split(",");
//            for (String item : items) {
//
//                spinnervalues.add(item);
//            }
//            Log.e("spinnervalues", String.valueOf(spinnervalues));


//            Log.e("relation", String.valueOf(viewHolder.spRelation.getSelectedItem()));


//            viewHolder.edName.setId(position);


//            Spinner mySpinner=(Spinner) findViewById(11+position);
//            String text = mySpinner.getSelectedItem().toString();

//            Log.e("position", String.valueOf(11+position));
//            Log.e("relation1111",viewHolder.spRelation.);

//            namedaa.add( viewHolder.edName.getId());
//            Log.e("arrayid...", String.valueOf(spinnerid));
//            Log.e("arrayid111...", String.valueOf(namedaa));


        }

        public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements View.OnClickListener {

            EditText edName;
            TextView txtid;
            Spinner spRelation;

            public ViewHolder(View itemView, FamilyUpdateAdapter familyUpdateAdapter) {
                super(itemView);

                edName = (EditText) itemView.findViewById(R.id.update_family_edt_name);
                spRelation = (Spinner) itemView.findViewById(R.id.update_spinner);
                txtid = (TextView) itemView.findViewById(R.id.txtid);


                itemView.setOnClickListener(this);
            }


            public void onClick(View v) {
//            this.recyclerViewAdapter.setItemClick(this);
            }
        }
    }
}



