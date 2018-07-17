package com.swarankar.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Emp7 on 12/22/2017.
 */

public class AdvanceSearchFieldAdapter extends BaseAdapter {
    Context context;
    List<String> searchFieldList;

    public AdvanceSearchFieldAdapter(Context context, List<String> searchFieldList) {
        this.context = context;
        this.searchFieldList = searchFieldList;
    }

    @Override
    public int getCount() {
        return searchFieldList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(searchFieldList.get(position));
        return convertView;
    }
}
