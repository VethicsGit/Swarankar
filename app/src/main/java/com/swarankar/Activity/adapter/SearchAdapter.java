package com.swarankar.Activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swarankar.Activity.Activity.SearchProfile;
import com.swarankar.Activity.Activity.SearchResultActivity;
import com.swarankar.Activity.Model.ModelSearch.ModelSearch;
import com.swarankar.Activity.Utils.AndroidUtils;
import com.swarankar.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    private List<ModelSearch> searchList;
    SearchResultActivity activity;

    public SearchAdapter(Context context, List<ModelSearch> searchList) {
        this.context = context;
        this.searchList = searchList;
        this.activity = (SearchResultActivity) context;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false), this);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {

        final String strid = searchList.get(position).getId();
        Picasso.with(context).load("" + searchList.get(position).getPicture())/*.error(R.drawable.placeholder)*/.into(holder.imgUser);
        holder.txtName.setText(AndroidUtils.wordFirstCap(searchList.get(position).getFirstname()) + " " + AndroidUtils.wordFirstCap(searchList.get(position).getLastname()));
        holder.txtWork.setText(AndroidUtils.wordFirstCap(searchList.get(position).getProfession()));
        holder.txtAddress.setText(searchList.get(position).getPHouseNo() + "," + searchList.get(position).getPArea() + "," + searchList.get(position).getCity());
        holder.btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SearchProfile.class);
                i.putExtra("id", strid);
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUser;
        TextView txtName, txtWork, txtAddress;
        Button btnViewProfile;

        public ViewHolder(View itemView, SearchAdapter searchAdapter) {
            super(itemView);

            imgUser = (ImageView) itemView.findViewById(R.id.search_img_user);
            txtName = (TextView) itemView.findViewById(R.id.search_txt_name);
            txtWork = (TextView) itemView.findViewById(R.id.search_txt_work);
            txtAddress = (TextView) itemView.findViewById(R.id.search_txt_city);
            btnViewProfile = (Button) itemView.findViewById(R.id.search_btn_view_profile);
        }
    }
}