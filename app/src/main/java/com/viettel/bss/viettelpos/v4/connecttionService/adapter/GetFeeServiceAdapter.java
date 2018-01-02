package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.MpServiceFeeBeans;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetFeeServiceAdapter extends BaseAdapter{

	
	private final Context mContext;
	private ArrayList<MpServiceFeeBeans> arrMpServiceFeeBeans = new ArrayList<>();
	
	public GetFeeServiceAdapter(ArrayList<MpServiceFeeBeans> arr, Context context){
		this.arrMpServiceFeeBeans = arr;
		this.mContext = context;
	}
	

	@Override
	public int getCount() {
		return arrMpServiceFeeBeans.size();
	}


	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	private class Viewholer{
		TextView txtfeename;
		TextView txtamount;
		TextView txtmadvbanhang;

	}
	
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		Viewholer holder;
		View mView = arg1;
		
		if(mView == null){
			
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connnection_product_item, arg2,
					false);
			holder = new Viewholer();
			holder.txtfeename = (TextView) mView.findViewById(R.id.txtfeename);
			holder.txtamount = (TextView) mView.findViewById(R.id.txtamount);
			holder.txtmadvbanhang = (TextView)mView.findViewById(R.id.txtmadvbanhang);
			mView.setTag(holder);
		}else{
			holder = (Viewholer) mView.getTag();
		}
		MpServiceFeeBeans mpServiceFeeBeans = this.arrMpServiceFeeBeans.get(arg0);
		
		holder.txtfeename.setText(mContext.getResources().getString(R.string.feename) + " " + mpServiceFeeBeans.getFeeName());
		holder.txtamount.setText(mContext.getResources().getString(R.string.amountservice) + " " + StringUtils.formatMoney(mpServiceFeeBeans.getAmount()));
		holder.txtmadvbanhang.setText(mContext.getResources().getString(R.string.saleServiceId) + " " + mpServiceFeeBeans.getSaleServiceId());
		return mView;
	}

	
	
}
