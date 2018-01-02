package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ReasonBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class ReasonBeanAdapter extends ArrayAdapter<ReasonBean> {

	public ReasonBeanAdapter(Context context, List<ReasonBean> lst) {
	       super(context, 0, lst);
	}
	
	private static class ViewHolder {
        TextView name;
        TextView code;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
	   ReasonBean reasonBean = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       ViewHolder viewHolder; // view lookup cache stored in tag
       if (convertView == null) {
          viewHolder = new ViewHolder();
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.item_customer_object, parent, false);
          viewHolder.name = (TextView) convertView.findViewById(R.id.txt_customer_name);
          viewHolder.code = (TextView) convertView.findViewById(R.id.txt_customer_code);
          convertView.setTag(viewHolder);
       } else {
           viewHolder = (ViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object
       viewHolder.name.setText(reasonBean.getName());
       viewHolder.code.setText(reasonBean.getCode());
       // Return the completed view to render on screen
       return convertView;
   }
}
