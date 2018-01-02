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
import com.viettel.bss.viettelpos.v4.channel.object.SaleTransDetailOJ;

public class ChannelSaleTransDetailAdapter extends BaseAdapter {
	private final ArrayList<SaleTransDetailOJ> arrayListManager;
	private final Context mContext;
	public ChannelSaleTransDetailAdapter (ArrayList<SaleTransDetailOJ> arrayList, Context context){
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
		TextView tv_name_stock;
		TextView tv_quantity;
		TextView tv_amount;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;
		if(mView == null){
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_layout_sale_trans_details, arg2,false);
			holder = new ViewHolder();
			
			holder.tv_name_stock = (TextView) mView.findViewById(R.id.tv_name_stock);
			holder.tv_quantity = (TextView) mView.findViewById(R.id.tv_quantity);
			holder.tv_amount = (TextView) mView.findViewById(R.id.tv_amount);
			mView.setTag(holder);
		}else {
			holder = (ViewHolder) mView.getTag();
		}
		
		SaleTransDetailOJ object = arrayListManager.get(arg0);
		
		if(object != null){
			holder.tv_name_stock.setText(object.getStockName());
			holder.tv_quantity.setText(""+object.getQuantity());
			holder.tv_amount.setText(""+object.getAmount());
		}
		return mView;
	}
	
}
