package com.viettel.bss.viettelpos.v4.charge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.DebitSub;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import java.util.List;

public class DebitSubAdapter extends ArrayAdapter<DebitSub> {

	public DebitSubAdapter(Context context, List<DebitSub> lst) {
	       super(context, R.layout.sub_debit_item, lst);
	}
	
	private static class ViewHolder {
		TextView tvIsdn;
        TextView tvCharge;
        RadioButton radio;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
        DebitSub item = getItem(position);
       // Check if an existing view is being reused, otherwise inflate the view
       ViewHolder holder; // view lookup cache stored in tag
       if (convertView == null) {
          holder = new ViewHolder();
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.sub_debit_item, parent, false);
         
          holder.tvIsdn = (TextView) convertView.findViewById(R.id.tvIsdn);
          holder.tvCharge = (TextView) convertView.findViewById(R.id.tvCharge);
          holder.radio = (RadioButton) convertView.findViewById(R.id.radioButton);
          convertView.setTag(holder);
       } else {
          holder = (ViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object
       holder.tvIsdn.setText(position + 1 +". " + item.getIsdn());
        holder.tvCharge.setText(StringUtils.formatMoney(item.getStaOfCycle()));
        holder.radio.setChecked(item.getCheck());
       // Return the completed view to render on screen
       return convertView;
   }
}
