package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Toancx on 1/25/2017.
 */

public class AutocompleteAdapter<T> extends ArrayAdapter<T> implements Filterable {
    private final List<T> lstDataFilter = new ArrayList<>();
    private final int resource;

    public AutocompleteAdapter(Context context, List<T> lstData) {
        super(context, R.layout.spinner_item, lstData);
        this.resource = R.layout.spinner_item;

        for(T item : lstData){
            lstDataFilter.add(item);
        }
        Log.d("AutocompleteAdapter", "oncreate");
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object listObject = getItem(position);
        viewHolder holder;
        if (convertView != null) {
            holder = (viewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            holder = new viewHolder(convertView);
            convertView.setTag(holder);
        }

        if(listObject instanceof Spin) {
            Spin obj = (Spin) listObject;
            holder.name.setText(obj.getCode() + "-" + obj.getName());
        } else {
            holder.name.setText(listObject.toString());
        }
        return convertView;
    }

    class viewHolder {
        @BindView(R.id.spinner_value)
        TextView name;

        public viewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private final Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<T> lstSuggest = new ArrayList<>();

            if (constraint != null) {
                Log.d("AutocompleteAdapter", "size = " + lstDataFilter.size());
                for (T object : lstDataFilter) {
                    if(object instanceof Spin){
                        Spin obj = (Spin) object;
                        String text = obj.getCode() + "-" + obj.getName();
                        if(text.toLowerCase().contains(constraint.toString().toLowerCase())){
                            lstSuggest.add(object);
                        }
                    } else {
                        if (object.toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            lstSuggest.add(object);
                        }
                    }
                }

                filterResults.values = lstSuggest;
                filterResults.count = lstSuggest.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence contraint, FilterResults results) {
            if (results == null) {
                return;
            }

            List<T> filteredList = (List<T>) results.values;
            if (results.count > 0) {
                clear();
                for (T filteredObject : filteredList) {
                    add(filteredObject);
                }
                notifyDataSetChanged();
            }
        }
    };

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
