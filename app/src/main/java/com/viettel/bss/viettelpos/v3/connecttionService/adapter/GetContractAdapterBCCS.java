package com.viettel.bss.viettelpos.v3.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bss.viettelpos.v4.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetContractAdapterBCCS extends BaseAdapter{
	private  Context mContext;
	private ArrayList<AccountDTO> arrPstn = new ArrayList<AccountDTO>();
	
	public GetContractAdapterBCCS(ArrayList<AccountDTO> arr, Context context) {
		this.arrPstn = arr;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrPstn.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView txtpstn;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connection_item_pstn, arg2,
					false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvpstn);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		AccountDTO pstn = arrPstn.get(arg0);
		holder.txtpstn.setText(pstn.getAccountNo());
		
		return mView;
	}

	
	
	
}
