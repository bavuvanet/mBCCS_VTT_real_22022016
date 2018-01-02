package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class CustomerObjectNewAdapter extends ArrayAdapter<Spin> {

	private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

	public CustomerObjectNewAdapter(Context context, List<Spin> array, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
		super(context, R.layout.item_customer_object_new, array);
		this.onCheckedChangeListener = onCheckedChangeListener;
	}

	static class ViewHolder {
		CheckBox cbCustomerName;
		TextView tvCustomerCode;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Spin spin = getItem(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_customer_object_new, parent, false);
			holder = new ViewHolder();
			holder.cbCustomerName = (CheckBox) mView.findViewById(R.id.cbCustomerName);
			holder.tvCustomerCode = (TextView) mView.findViewById(R.id.tvCustomerCode);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		Log.d(this.getClass().getSimpleName(), "getView " + position + " name " + spin.getName() + " id " + spin.getId());

		holder.cbCustomerName.setOnCheckedChangeListener(null);
		if(spin.getId() != null && spin.getId().equalsIgnoreCase("1")) {
			holder.cbCustomerName.setChecked(true);
		} else {
			holder.cbCustomerName.setChecked(false);
		}
		
		holder.cbCustomerName.setText(spin.getName());
		holder.tvCustomerCode.setText(spin.getCode());

		holder.cbCustomerName.setTag(position);
		holder.cbCustomerName.setOnCheckedChangeListener(onCheckedChangeListener);

		return mView;
	}

}
