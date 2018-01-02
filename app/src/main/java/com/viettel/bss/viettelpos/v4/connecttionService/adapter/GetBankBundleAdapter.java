package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.Bank;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetBankBundleAdapter extends BaseAdapter{
	private final Context mContext;
	private final ArrayList<Bank> arrPakageChargeBeans  = new ArrayList<>();
    private final ArrayList<Bank> arraylist = new ArrayList<>();
	public GetBankBundleAdapter(ArrayList<Bank> arr, Context context) {
		//this.arraylist = new ArrayList<PakageChargeBeans>();
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrPakageChargeBeans.addAll(arr);
		}
		
		
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrPakageChargeBeans.size();
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
			mView = inflater.inflate(R.layout.connectionmobile_item_pakage, arg2,
					false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		Bank pakageChargeBeans = arrPakageChargeBeans.get(arg0);
		if(pakageChargeBeans.getName() != null && !pakageChargeBeans.getName().isEmpty()){
			holder.txtpstn.setText(pakageChargeBeans.getName());
		}
		
		
		return mView;
	}
	
	public ArrayList<Bank> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrPakageChargeBeans.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrPakageChargeBeans.addAll(arraylist);
		}else{
			for (Bank pakageChargeBeans : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(pakageChargeBeans.getName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrPakageChargeBeans.add(pakageChargeBeans);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrPakageChargeBeans;
	}
	
	
	
}
