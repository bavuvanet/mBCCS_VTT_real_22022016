package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.SaleTransOJ;

class ChannelSaleTransHistoryAdapter_back extends BaseAdapter{
	private final ArrayList<SaleTransOJ> arrayListManager;
	private final Context mContext;
	public ChannelSaleTransHistoryAdapter_back (ArrayList<SaleTransOJ> arrayList, Context context){
		this.arrayListManager = arrayList;
		this.mContext = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayListManager.size();
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
	static class ViewHolder{
		TextView txt_date_sale;
		TextView txt_amout_tax;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;
		if(mView == null){
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_layout_sale_trans, arg2,false);
			holder = new ViewHolder();
			
			holder.txt_date_sale = (TextView) mView.findViewById(R.id.txt_date_sale);
			holder.txt_amout_tax = (TextView) mView.findViewById(R.id.txt_amout_tax);
			mView.setTag(holder);
		}else {
			holder = (ViewHolder) mView.getTag();
		}
		SaleTransOJ saleTransOBJ = arrayListManager.get(arg0);
		
		if(saleTransOBJ != null){
			holder.txt_date_sale.setText(saleTransOBJ.getSaleTransDate());
			holder.txt_amout_tax.setText(Float.toString(saleTransOBJ.getAmoutTax()));
			
		}
		return mView;
	}
}
