package com.pharmware.epharma;

import android.widget.Filter;

import java.util.ArrayList;


public class CustomFilterTrans extends Filter {

    MyAdapterTrans adapter;
    ArrayList<Modeltrans> filterList;

    public CustomFilterTrans(ArrayList<Modeltrans> filterList, MyAdapterTrans adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<Modeltrans> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getGtranid().toUpperCase().contains(constraint)) {
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.models = (ArrayList<Modeltrans>) results.values;
        adapter.notifyDataSetChanged();
    }
}
