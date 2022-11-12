package com.pharmware.epharma;

import android.widget.Filter;

import java.util.ArrayList;


public class CustomFilter extends Filter {

    MyAdapter adapter;
    ArrayList<Model> filterList;

    public CustomFilter(ArrayList<Model> filterList, MyAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<Model> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getEiname().toUpperCase().contains(constraint)) {
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
        adapter.models = (ArrayList<Model>) results.values;
        adapter.notifyDataSetChanged();
    }
}
