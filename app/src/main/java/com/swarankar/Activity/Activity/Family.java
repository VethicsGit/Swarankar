package com.swarankar.Activity.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.view.MotionEvent;
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
import android.widget.Toast;

import com.swarankar.Activity.Utils.API;
import com.swarankar.Activity.Utils.APIClient;
import com.swarankar.Activity.Utils.AndroidUtils;
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

public class Family extends AppCompatActivity {

    ImageView imgback;
    RecyclerView family_recyclerview;
    int rlsize = 1;
    AddFamilyAdapter addFamilyAdapter;
    Button family_btn_add_more;
    Button btn_update;
    Boolean open = false;

    String name;
    List<String> relationList = new ArrayList<>();
    List<String> nameList = new ArrayList<>();
    StringBuilder strBuilder;
    HashMap<Integer, Integer> hm = new HashMap<>();
    HashMap<Integer, String> hmName = new HashMap<>();

//    EditText edt_name;
//    Spinner spinner_rl;

    List<String> mainlist = new ArrayList<>();

    List<Integer> namedaa = new ArrayList<>();
    ArrayList<String> values = new ArrayList<String>();


    List<Integer> spinnerid = new ArrayList<>();
    ArrayList<String> spinnervalues = new ArrayList<String>();

    int nameaddornt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);
        findView();

        imgback.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nameList.add("name");
    }


    private void findView() {
        mainlist.clear();
        mainlist.add("");

        imgback = (ImageView) findViewById(R.id.family_image_back);
        family_recyclerview = (RecyclerView) findViewById(R.id.family_recyclerview);
        family_btn_add_more = (Button) findViewById(R.id.family_btn_add_more);
        btn_update = (Button) findViewById(R.id.btn_update);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        family_recyclerview.setLayoutManager(linearLayoutManager);
        addFamilyAdapter = new AddFamilyAdapter(getApplicationContext(), mainlist);
        family_recyclerview.setAdapter(addFamilyAdapter);

        family_btn_add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(view instanceof EditText)) {
                    view.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            AndroidUtils.hideSoftKeyboard(Family.this);
                            open = true;
                            return false;
                        }
                    });
                }
                if (open) {

                    mainlist.add("");
                    rlsize = mainlist.size() + 1;

                    addFamilyAdapter.notifyItemInserted(rlsize);
                    family_recyclerview.getRecycledViewPool().clear();

                }


            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                values.clear();
//                spinnervalues.clear();
//                nameaddornt = 0 ;
////
////
//                for (int id : namedaa) {
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
//
//                    }
//
//                }
//
//                for (int id : spinnerid)
//                {
//                    Spinner t = (Spinner) findViewById(id);
//                    spinnervalues.add(t.getSelectedItem()+"");
//                    Log.e("spinnervaluessss", t.getSelectedItem() + "");
//
//                }


//                Log.e("name", values.size() + "");
//                Log.e("relation", spinnerid.size() + "");


                if (nameaddornt == 1) {
                    Toast.makeText(Family.this, "Enter Valid Data", Toast.LENGTH_LONG).show();
                } else {
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < relationList.size(); i++) {

                        JSONObject jsonObject = new JSONObject();

                        try {
                            if (relationList.get(i).trim().toLowerCase().equals("father")) {
                                jsonObject.put("relation", "father");
                                Log.e("json enter ", jsonObject.toString() + "");

                            } else if (relationList.get(i).trim().toLowerCase().equals("mother")) {
                                jsonObject.put("relation", "mother");
                                Log.e("json enter ", jsonObject.toString() + "");

                            } else if (relationList.get(i).trim().toLowerCase().equals("brother")) {
                                jsonObject.put("relation", "brother");
                                Log.e("json enter ", jsonObject.toString() + "");

                            } else if (relationList.get(i).trim().toLowerCase().equals("sister")) {
                                jsonObject.put("relation", "sister");
                                Log.e("json enter ", jsonObject.toString() + "");

                            } else if (relationList.get(i).trim().toLowerCase().equals("brother's wife")) {
//                                Toast.makeText(getApplicationContext(),"brother wife",Toast.LENGTH_SHORT).show();
                                jsonObject.put("relation", "brother_wife");
                                Log.e("json enter ", jsonObject.toString() + "");

                            } else if (relationList.get(i).trim().toLowerCase().equals("brother's children")) {
                                jsonObject.put("relation", "brother_children");
                                Log.e("json enter ", jsonObject.toString() + "");

                            } else if (relationList.get(i).trim().toLowerCase().equals("sister's husband")) {
                                jsonObject.put("relation", "sister_husbund");
                                Log.e("json enter ", jsonObject.toString() + "");

                            } else if (relationList.get(i).trim().toLowerCase().equals("sister's children")) {
                                jsonObject.put("relation", "sister_children");
                            } else if (relationList.get(i).trim().toLowerCase().equals("wife")) {
                                jsonObject.put("relation", "wife");
                            } else if (relationList.get(i).trim().toLowerCase().equals("son")) {
                                jsonObject.put("relation", "son");
                            } else if (relationList.get(i).trim().toLowerCase().equals("daughter")) {
                                jsonObject.put("relation", "daughter");
                            } else if (relationList.get(i).trim().toLowerCase().equals("husband")) {
                                jsonObject.put("relation", "husband");
                            }
                            jsonObject.put("name", nameList.get(i));
                            Log.e("jsonobject ", jsonObject.toString() + "");

                            jsonArray.put(jsonObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    nameList.clear();
                    relationList.clear();
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

                        //addFamilyData(base64);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                }

            }
        });


    }


    /*private void addFamilyData(String jsonArray) {

        final ProgressDialog loading = new ProgressDialog(Family.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = apiService.spinner_add_family(strUserid, jsonArray);
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
                    Log.e("response", strUrl);
                    String res = jsonObject.getString("response");
                    String mes = jsonObject.getString("message");

                    if (res.equals("Success")) {
                        dialog(mes + "", Family.this);
                    } else {
                        dialog(mes + "", Family.this);
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
    }
*/
    private void dialog(String s, Family family) {
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


    public class AddFamilyAdapter extends RecyclerView.Adapter<AddFamilyAdapter.ViewHolder> {
        Context context;
        List<String> itemcount;

        public AddFamilyAdapter(Context context, List<String> brotherList) {

//            namedaa.clear();
//            spinnervalues.clear();
            this.context = context;
            itemcount = brotherList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adt_add_family, parent, false), this);
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
//            Log.e("itemcount", String.valueOf(itemcount.size()));

//            holder.edt_name.setId(position);
//            holder.spinner_rl.setId(Integer.parseInt(("s"+position)));
//
//            spinnerid.add(holder.spinner_rl.getId());
//            namedaa.add(holder.edt_name.getId());


            holder.edt_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.e("charSequence", String.valueOf(charSequence));
                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    Log.e("RelatioName", String.valueOf(staticname));

                    name = holder.edt_name.getText().toString();
//                    Log.e("nameList", String.valueOf(nameList.size()));

                    int a = position + 1;
//                    Log.e("name", String.valueOf(name)+" p"+String.valueOf(a));
                    if (nameList.size() >= a) {
                        nameList.set(position, name);
                        hmName.put(position, name);
                    } else {
                        nameList.add(position, name);
                        hmName.put(position, name);
                    }

//                    Log.e("nameList", String.valueOf(nameList));


//                    nameList.set(position,name);
//                    Log.e("RelatioName", String.valueOf(staticname));
                }
            });

            int b = position + 1;

            if (nameList.size() >= b) {
                if (hm.containsKey(position)) {
                    holder.edt_name.setText(hmName.get(position));
                }
            }

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
            holder.spinner_rl.setAdapter(aa0);

            if (hm.containsKey(position)) {
                holder.spinner_rl.setSelection(hm.get(position));
            }


            holder.spinner_rl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String strRelation = adapterView.getItemAtPosition(i).toString();
                    if (strRelation.equals("Select")) {

                    } else {
                        int a = position + 1;
                        Log.e("pos", "" + position);
                        Log.e("relation List", "" + relationList);
                        if (relationList.size() >= a) {
                            relationList.set(position, strRelation);
                        } else {
                            relationList.add(0, strRelation);
                        }
                        hm.put(position, i);
                        Log.e("name", String.valueOf(strRelation) + " p" + String.valueOf(a));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return itemcount.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            EditText edt_name;
            Spinner spinner_rl;

            public ViewHolder(View itemView, AddFamilyAdapter familyAdapter) {
                super(itemView);

                edt_name = (EditText) itemView.findViewById(R.id.edt_name);
                spinner_rl = (Spinner) itemView.findViewById(R.id.spinner_rl);
            }
        }
    }
}
