package com.swarankar.Activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swarankar.Activity.Model.ModelFamily.Model;
import com.swarankar.R;

import java.util.List;

/**
 * Created by softeaven on 7/3/17.
 */

public class FamilyAdapter  extends RecyclerView.Adapter<FamilyAdapter.ViewHolder> {
    Context context;
    List<Model> brotherList;
    public FamilyAdapter(Context context, List<Model> brotherList) {

        this.context = context;
        this.brotherList = brotherList;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FamilyAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.family_item, parent, false), this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtName.setText(brotherList.get(position).getName());
        holder.txtRelation.setText(brotherList.get(position).getRelation());
    }

    @Override
    public int getItemCount() {
        return brotherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtRelation;
        public ViewHolder(View itemView, FamilyAdapter familyAdapter) {
            super(itemView);

            txtName = (TextView)itemView.findViewById(R.id.family_item_name);


            txtRelation = (TextView)itemView.findViewById(R.id.family_item_relation);
        }
    }
}
