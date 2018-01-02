package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerOJ;

public class CustomerAdapter extends BaseAdapter {
	private final ArrayList<CustomerOJ> arrCustomerOJs;
	private final Context mContext;
	public CustomerAdapter(ArrayList<CustomerOJ> arrCustomerOJs, Context context) {
		this.arrCustomerOJs = arrCustomerOJs;
		mContext = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrCustomerOJs.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	static class ViewHolder {
		TextView txtCustomerName;
		TextView txtCustomerInfo;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		CustomerOJ customer = arrCustomerOJs.get(arg0);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_customer, arg2, false);
			holder = new ViewHolder();
			holder.txtCustomerName = (TextView) mView
					.findViewById(R.id.txt_customer_name);
			holder.txtCustomerInfo = (TextView) mView
					.findViewById(R.id.txt_customer_info_more);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.txtCustomerName.setText(customer.getName());
		// holder.txtCustomerInfo.setText(customer.toString());

		return mView;
		// return null;
	}

}
