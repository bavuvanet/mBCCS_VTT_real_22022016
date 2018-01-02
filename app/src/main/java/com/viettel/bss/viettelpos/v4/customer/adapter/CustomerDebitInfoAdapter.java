package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.CustIdentityBO;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomerDebitInfoAdapter extends BaseAdapter {
	private final ArrayList<CustIdentityBO> lstCustIdentityBOs;
	private final Context mContext;

	public CustomerDebitInfoAdapter(Context mContext,
			ArrayList<CustIdentityBO> lstCustIdentityBOs) {
		this.mContext = mContext;
		this.lstCustIdentityBOs = lstCustIdentityBOs;
	}

	@Override
	public int getCount() {
		if (CommonActivity.isNullOrEmpty(lstCustIdentityBOs)) {
			return 0;
		}
		return lstCustIdentityBOs.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (CommonActivity.isNullOrEmpty(lstCustIdentityBOs)) {
			return null;
		}
		return lstCustIdentityBOs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		CustIdentityBO item = lstCustIdentityBOs.get(arg0);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_customer_info, arg2,
					false);
			holder = new ViewHolder();
			holder.tvName = (TextView) mView.findViewById(R.id.tvName);
			holder.tvBirthDay = (TextView) mView.findViewById(R.id.tvBirthDay);
			holder.tvIdNo = (TextView) mView.findViewById(R.id.tvIdNo);
			holder.tvAddress = (TextView) mView.findViewById(R.id.tvAddress);
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		holder.tvName.setText(mContext.getResources().getString(R.string.txt_khach_hang, item.getCustomer().getName()));
		holder.tvBirthDay.setText(mContext.getResources().getString(R.string.txt_ngay_sinh, StringUtils.convertDate(item.getCustomer().getBirthDate())));
		holder.tvIdNo.setText(mContext.getResources().getString(R.string.txt_so_giay_to, item.getIdNo()));
		holder.tvAddress.setText(mContext.getResources().getString(R.string.txt_dia_chi, item.getCustomer().getAddress()));
		
		return mView;
	}
	
	class ViewHolder {
		TextView tvName;
		TextView tvBirthDay;
		TextView tvIdNo;
		TextView tvAddress;
	}

}
