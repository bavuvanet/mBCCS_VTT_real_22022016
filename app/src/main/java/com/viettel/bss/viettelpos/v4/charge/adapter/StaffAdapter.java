package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class StaffAdapter extends BaseAdapter {

	private final Activity mActivity;
	private final List<Staff> lstStaff;
	 
	
	public StaffAdapter(Activity mActivity, List<Staff> arr) {
		this.mActivity = mActivity;
		this.lstStaff = arr;
	}
	
	@Override
	public int getCount() {
		return lstStaff.size();
	}

	@Override
	public Object getItem(int position) {
		return lstStaff.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	static class ViewHolder {
		TextView txtCustomerName;
		TextView txtCustomerCode;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		Staff staff = lstStaff.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater(); 
			mView = inflater.inflate(R.layout.item_customer_object,parent, false);
			holder = new ViewHolder();
			holder.txtCustomerName = (TextView) mView
					.findViewById(R.id.txt_customer_name);
			holder.txtCustomerCode = (TextView) mView
					.findViewById(R.id.txt_customer_code);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		} 
		
		holder.txtCustomerName.setText(staff.getName());
		holder.txtCustomerCode.setText(staff.getStaffCode());

		return mView; 
	}

}
