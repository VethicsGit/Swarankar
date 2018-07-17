package com.swarankar.Activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.swarankar.Activity.Activity.Family;
import com.swarankar.Activity.Model.ModelFamily.Model;
import com.swarankar.Activity.Model.joblist.JobListDatum;
import com.swarankar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by softeaven on 9/4/17.
 */

//public class FamilyUpdateAdapter extends RecyclerView.Adapter<FamilyUpdateAdapter.ViewHolder> {
//    public AdapterView.OnItemClickListener onItemClickListener;
//
//    private Context context;
//    private List<Model>familyList;
//    private String strRelation,strName;
//    public static List<Integer> namedaa = new ArrayList<>();
//
//
//
//    public static List<Integer> spinnerid = new ArrayList<>();
//
//
//    public FamilyUpdateAdapter(Context context, List<Model> familyList) {
//        this.context = context;
//        this.familyList = familyList;
//
//    }
//
//    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
//        this.onItemClickListener = listener;
//    }
//
//    public int getItemCount() {
//        return familyList.size();
//
//    }
//
//    public void setItemClick(FamilyUpdateAdapter.ViewHolder viewHolder) {
//        this.onItemClickListener.onItemClick(null, viewHolder.itemView, viewHolder.getAdapterPosition(), viewHolder.getItemId());
//    }
//
//    public FamilyUpdateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new FamilyUpdateAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.update_family_item, parent, false), this);
//    }
//
//    public void onBindViewHolder(final FamilyUpdateAdapter.ViewHolder viewHolder, int position) {
//
//
//        strRelation = familyList.get(position).getRelation();
//        strName = familyList.get(position).getName();
//        viewHolder.edName.setText(strName);
//
//        List<String> prStrings = new ArrayList<>();
//        prStrings.add("Select");
//        prStrings.add("father");
//        prStrings.add("mother");
//        prStrings.add("brother");
//        prStrings.add("sister");
//        prStrings.add("wife");
//        prStrings.add("husband");
//        prStrings.add("childrens");
//        prStrings.add("brothers wife");
//        prStrings.add("brothers children");
//        prStrings.add("sisters husband");
//        prStrings.add("sisters children");
//
//
//
//        ArrayAdapter aa0 = new ArrayAdapter(context, android.R.layout.simple_spinner_item, prStrings);
//        aa0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        viewHolder.spRelation.setAdapter(aa0);
//
//        for (int j = 0; j <prStrings.size(); j++) {
//            if (prStrings.get(j).toString().trim().equals(strRelation)) {
//                viewHolder.spRelation.setSelection(j);
//            }
//
//        }
////
//        viewHolder.spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                ((TextView) viewHolder.spRelation.getSelectedView()).setTextColor(Color.BLACK);
//                strRelation = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//
//        viewHolder.edName.setId(position);
//        viewHolder.spRelation.setId((11+position));
//
//        spinnerid.add(viewHolder.spRelation.getId());
//        namedaa.add(  viewHolder.edName.getId());
//
////        Log.d("edittextid", holder.spinner_rl.getId() + "");
//
//
//
//
//    }
//
//    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements View.OnClickListener {
//
//        EditText edName;
//        Spinner spRelation;
//        public ViewHolder(View itemView, FamilyUpdateAdapter recyclerViewAdapter) {
//            super(itemView);
//
//            edName = (EditText)itemView.findViewById(R.id.update_family_edt_name);
//            spRelation = (Spinner)itemView.findViewById(R.id.update_family_spinner_rl);
//
//            itemView.setOnClickListener(this);
//        }
//
//        public void onClick(View v) {
////            this.recyclerViewAdapter.setItemClick(this);
//        }
//    }
//}
