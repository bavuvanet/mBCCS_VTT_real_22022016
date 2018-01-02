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
import com.viettel.bss.viettelpos.v4.channel.object.DetailAccountPaymentOJ;


public class DetailPaymentAccountAdapter extends BaseAdapter {
	private final ArrayList<DetailAccountPaymentOJ> arrayListManager;
	private final Context mContext;
	public DetailPaymentAccountAdapter (ArrayList<DetailAccountPaymentOJ> arrayList, Context context){
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
		TextView tvStockName;
		TextView tvTotalDeposit;
		
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;
		if(mView == null){
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_layout_details_payment_account, arg2,false);
			holder = new ViewHolder();
			
			holder.tvStockName = (TextView) mView.findViewById(R.id.tvStockName);
			holder.tvTotalDeposit = (TextView) mView.findViewById(R.id.tvTotalDeposit);
			
			mView.setTag(holder);
		}else {
			holder = (ViewHolder) mView.getTag();
		}
		
		DetailAccountPaymentOJ object = arrayListManager.get(arg0);
		
		if(object != null){
			holder.tvStockName.setText(object.getName());
			holder.tvTotalDeposit.setText(""+object.getToalDeposit());
	    }
		return mView;
	}
	
}
