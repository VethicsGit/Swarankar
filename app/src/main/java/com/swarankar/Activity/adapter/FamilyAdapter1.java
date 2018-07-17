package com.swarankar.Activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swarankar.Activity.Activity.FamilyActivity;
import com.swarankar.Activity.Activity.UpdateFamily1;
import com.swarankar.Activity.Model.ModelFamily.Model;
import com.swarankar.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FamilyAdapter1 extends RecyclerView.Adapter<FamilyAdapter1.MyViewHolder> {
    Context context;
    private ArrayList<Model> stringArrayList;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;

    public RecyclerClickListner recyclerClickListner;

    public FamilyAdapter1(Context context, ArrayList<Model> stringArrayList, RecyclerClickListner recyclerClickListner) {
        this.context = context;
        this.stringArrayList = stringArrayList;
        this.recyclerClickListner = recyclerClickListner;
    }

    @Override
    public FamilyAdapter1.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*if (viewType == TYPE_FOOTER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blank_footer_family_list, parent, false);
            return new MyViewHolder(itemView);
        }*/
        View view = LayoutInflater.from(context).inflate(R.layout.custom_family_layout, parent, false);
        return new MyViewHolder(view);


    }
/*@Override
    public int getItemViewType(int position) {
        if (position == stringArrayList.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
*/

    @Override
    public void onBindViewHolder(FamilyAdapter1.MyViewHolder holder, final int position) {

        if (position == 0 || position == 1 || position == 2 || position == 3) {
            String upperCase = stringArrayList.get(position).getName().substring(0, 1).toUpperCase() + stringArrayList.get(position).getName().substring(1);
            holder.name_textview.setText(upperCase);
            holder.relation_textview.setText(stringArrayList.get(position).getRelation());
            holder.basic_family_details_layout.setVisibility(View.VISIBLE);
            holder.other_family_details_layout.setVisibility(View.GONE);
            holder.view1.setVisibility(View.GONE);
        } else if (position == stringArrayList.size()) {
            holder.cardView.setCardElevation(0);
            holder.basic_family_details_layout.setVisibility(View.GONE);
            //holder.other_edit_family.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
            holder.other_family_details_layout.setVisibility(View.INVISIBLE);
            holder.son_name_textview.setVisibility(View.GONE);
            holder.daughter_name_textview.setVisibility(View.GONE);
            holder.other_family_details_layout.setMinimumHeight(20);
            holder.view1.setVisibility(View.GONE);
        } else {
            holder.basic_family_details_layout.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
            holder.other_family_details_layout.setVisibility(View.VISIBLE);
            holder.view1.setVisibility(View.VISIBLE);

            holder.relationTag_textview.setText(stringArrayList.get(position).getRelationInfo());
            holder.other_name_textview.setText("Name : " + wordFirstCap(stringArrayList.get(position).getName()) + "");

            if (stringArrayList.get(position).getRelation().equalsIgnoreCase("brother") || stringArrayList.get(position).getRelation().equalsIgnoreCase("sister")) {
                if (stringArrayList.get(position).getPartnerName().equalsIgnoreCase("null") || stringArrayList.get(position).getPartnerName().equalsIgnoreCase("") || stringArrayList.get(position).getPartnerName() == null) {
                    holder.partner_name_textview.setVisibility(View.GONE);
                    holder.son_name_textview.setVisibility(View.GONE);
                    holder.daughter_name_textview.setVisibility(View.GONE);
                } else {
                    holder.partner_name_textview.setVisibility(View.VISIBLE);
                    if (stringArrayList.get(position).getRelation().equalsIgnoreCase("brother")) {
                        holder.partner_name_textview.setText("Wife Name : " + wordFirstCap(stringArrayList.get(position).getPartnerName()) + "");
                    } else if (stringArrayList.get(position).getRelation().equalsIgnoreCase("sister")) {
                        holder.partner_name_textview.setText("Husband Name : " + wordFirstCap(stringArrayList.get(position).getPartnerName()) + "");
                    }
                }
            } else {
                holder.partner_name_textview.setVisibility(View.GONE);
            }

            if (stringArrayList.get(position).getSonName() == null) {
                holder.son_name_textview.setVisibility(View.GONE);
            } else {
                holder.son_name_textview.setVisibility(View.VISIBLE);
                JSONArray son = stringArrayList.get(position).getSonName();
                StringBuilder sb = new StringBuilder();
                try {
                    for (int i = 0; i < son.length(); i++) {
                        sb.append(son.get(i));
                        sb.append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    holder.son_name_textview.setText("Son Name : " + sb + "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (stringArrayList.get(position).getDaughterName() == null) {
                holder.daughter_name_textview.setVisibility(View.GONE);
            } else {
                holder.daughter_name_textview.setVisibility(View.VISIBLE);
                JSONArray daughter = stringArrayList.get(position).getDaughterName();
                StringBuilder sb = new StringBuilder();
                try {
                    for (int i = 0; i < daughter.length(); i++) {
                        sb.append(daughter.get(i));
                        sb.append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    holder.daughter_name_textview.setText("Daughter Name : " + sb + "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        holder.edit_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> passList = new ArrayList<String>();
                for (int j = 0; j < 4; j++) {
                    passList.add(stringArrayList.get(j).getName());
                }
                Intent i = new Intent(context, FamilyActivity.class);
                i.putExtra("ins_upd", "1");
                i.putStringArrayListExtra("updateList", passList);
                context.startActivity(i);
            }
        });

        holder.other_edit_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model model = stringArrayList.get(position);
                Intent i = new Intent(context, UpdateFamily1.class);
                i.putExtra("id", model.getId());
                i.putExtra("name", model.getName());
                i.putExtra("relation", model.getRelation());
                Log.e("relation", model.getRelation());
                if (model.getPartnerName() != null) {
                    i.putExtra("partnerName", model.getPartnerName());
                }
                if (model.getSonName() != null) {
                    ArrayList<String> sonList = new ArrayList<String>();
                    for (int j = 0; j < model.getSonName().length(); j++) {
                        try {
                            sonList.add(model.getSonName().get(j).toString());
                            Log.e("son", sonList.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        i.putStringArrayListExtra("sonList", sonList);
                    }
                }

                if (model.getDaughterName() != null) {
                    ArrayList<String> daughterList = new ArrayList<String>();
                    for (int j = 0; j < model.getDaughterName().length(); j++) {
                        try {
                            daughterList.add(model.getDaughterName().get(j).toString());
                            Log.e("daughter", daughterList.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        i.putStringArrayListExtra("daughterList", daughterList);
                    }
                }

                context.startActivity(i);
            }
        });

        holder.other_delete_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerClickListner.deleteItem(position);
                /*JSONArray idArray = new JSONArray();
                JSONObject idObject = new JSONObject();
                try {
                    idObject.put("id", stringArrayList.get(position).getId());
                    idArray.put(idObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("id", idArray.toString());

                //deleteFamilyData(idArray.toString(), position);
                stringArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, stringArrayList.size());
                notifyDataSetChanged();*/
            }
        });

    }

    /*private void deleteFamilyData(String jsonArray, int position) {
        final ProgressDialog loading = new ProgressDialog(context);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        String strUserid = Constants.loginSharedPreferences.getString(Constants.uid, "");
        API apiService = APIClient.getClient().create(API.class);
        Call<ResponseBody> call1 = null;
        switch (stringArrayList.get(position).getRelation()) {
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
    }*/

    @Override
    public int getItemCount() {
        return stringArrayList.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout basic_family_details_layout, other_family_details_layout;
        TextView name_textview, relation_textview, relationTag_textview, other_name_textview, partner_name_textview, son_name_textview, daughter_name_textview;
        View view, view1;
        ImageButton edit_family, other_edit_family, other_delete_family;
        CardView cardView;

        MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_family);
            name_textview = (TextView) itemView.findViewById(R.id.name_textview);
            relation_textview = (TextView) itemView.findViewById(R.id.relation_textview);
            view = (View) itemView.findViewById(R.id.view);
            edit_family = (ImageButton) itemView.findViewById(R.id.btn_edit_basic_family);
            basic_family_details_layout = (LinearLayout) itemView.findViewById(R.id.basic_family_details_layout);

            other_name_textview = (TextView) itemView.findViewById(R.id.other_name_textview);
            partner_name_textview = (TextView) itemView.findViewById(R.id.other_partner_name_textview);
            son_name_textview = (TextView) itemView.findViewById(R.id.other_son_name_textview);
            daughter_name_textview = (TextView) itemView.findViewById(R.id.other_daughter_name_textview);
            relationTag_textview = (TextView) itemView.findViewById(R.id.other_relation_tag_textview);
            view1 = (View) itemView.findViewById(R.id.view1);
            other_edit_family = (ImageButton) itemView.findViewById(R.id.btn_edit_other_family);
            other_delete_family = (ImageButton) itemView.findViewById(R.id.btn_delete_other_family);
            other_family_details_layout = (LinearLayout) itemView.findViewById(R.id.other_family_details_layout);
        }
    }

    public interface RecyclerClickListner {
        void deleteItem(int position);
    }

    public String wordFirstCap(String str) {
        String[] words = str.trim().split(" ");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (words[i].trim().length() > 0) {
                Log.e("words[i].trim", "" + words[i].trim().charAt(0));
                ret.append(Character.toUpperCase(words[i].trim().charAt(0)));
                ret.append(words[i].trim().substring(1));
                if (i < words.length - 1) {
                    ret.append(' ');
                }
            }
        }

        return ret.toString();
    }

}
