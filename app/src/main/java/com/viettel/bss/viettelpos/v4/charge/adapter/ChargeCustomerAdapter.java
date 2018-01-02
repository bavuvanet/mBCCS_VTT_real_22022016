package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeCustomerOJ;



public class ChargeCustomerAdapter extends BaseAdapter {
	private ArrayList<ChargeCustomerOJ> arrChargeCustomerOJs = new ArrayList<>();
	private final Context mContext;

	public ChargeCustomerAdapter(ArrayList<ChargeCustomerOJ> arr,
			Context context) {
		this.arrChargeCustomerOJs = arr;
		mContext = context;
	}

	@Override
	public int getCount() {
		return arrChargeCustomerOJs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	static class ViewHolder {
		CheckBox checkBox;
		TextView txtCustomerInfo;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		final ChargeCustomerOJ customer = arrChargeCustomerOJs.get(position);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater
					.inflate(R.layout.item_charge_customernew, arg2, false);
			holder = new ViewHolder();
			holder.checkBox = (CheckBox) mView.findViewById(R.id.chk_customer);
			
			holder.txtCustomerInfo = (TextView) mView
					.findViewById(R.id.txt_customer_info);
			holder.checkBox.setChecked(customer.getChecked());
			mView.setTag(holder);
			holder.checkBox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					 CheckBox cb = (CheckBox) v ; 
					 ChargeCustomerOJ chargeCustomerOJ = (ChargeCustomerOJ) cb.getTag();
					 chargeCustomerOJ.setChecked(cb.isChecked());
				}
			});
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.txtCustomerInfo.setText(mContext.getResources().getString(R.string.namecus) +"  "+ customer.getNameCustomer() + "\n\n"
									+  mContext.getResources().getString(R.string.addrescus)+"  "+ customer.getAddr() + "\n\n"
									+  mContext.getResources().getString(R.string.isdncus) +"  "+customer.getISDN() + "\n\n"
									+  mContext.getResources().getString(R.string.servicecus) +"  "+customer.getServiceName());

		holder.checkBox.setChecked(customer.getChecked());
		holder.checkBox.setTag(customer);
		//		final Boolean isChecked =  holder.checkBox.isChecked();
//		holder.checkBox.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				customer.setChecked(!isChecked);
//			}
//		});
		return mView;
	}

}
