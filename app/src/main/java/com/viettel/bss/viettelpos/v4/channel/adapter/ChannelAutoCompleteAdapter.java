package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.login.object.Staff;

class ChannelAutoCompleteAdapter extends ArrayAdapter<Staff> implements Filterable {
	private final Context context;
	 private final ArrayList<Staff> arrStaff;
	 
	public ChannelAutoCompleteAdapter(Context context,
			int textViewResourceId, ArrayList<Staff> arrStaff) {
		super(context, textViewResourceId, arrStaff);
		this.context = context;
		this.arrStaff = arrStaff;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		//return super.getDropDownView(position, convertView, parent);
		TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
       
        label.setText(arrStaff.get(position).getNameStaff());
       
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
		TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
       
        label.setText(arrStaff.get(position).getNameStaff());
        label.setTag(arrStaff.get(position));
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
	}
	
}
