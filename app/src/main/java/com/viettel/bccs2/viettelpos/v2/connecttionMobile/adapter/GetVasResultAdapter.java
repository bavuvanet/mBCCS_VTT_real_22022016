package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.MBCCSVasResultBO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetVasResultAdapter extends BaseAdapter{
	private  Context mContext;
	private ArrayList<MBCCSVasResultBO> arrPakageFeeDTO = new ArrayList<MBCCSVasResultBO>();
	
	public GetVasResultAdapter(ArrayList<MBCCSVasResultBO> arr, Context context) {
		this.arrPakageFeeDTO = arr;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrPakageFeeDTO.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrPakageFeeDTO.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class ViewHolder {
		TextView namefee,price;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.layout_vas_detail_item, arg2,
					false);
			holder = new ViewHolder();
			holder.namefee = (TextView) mView.findViewById(R.id.namefee);
			holder.price = (TextView) mView.findViewById(R.id.price);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		MBCCSVasResultBO productPackageFeeDTO = arrPakageFeeDTO.get(arg0);
		holder.namefee.setText(productPackageFeeDTO.getVasCode());
		holder.price.setText(StringUtils.formatMoney(productPackageFeeDTO.getDescription()));
		return mView;
	}

	
	
	
}
