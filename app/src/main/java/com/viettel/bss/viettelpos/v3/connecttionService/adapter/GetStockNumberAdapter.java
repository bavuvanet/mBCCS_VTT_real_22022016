package com.viettel.bss.viettelpos.v3.connecttionService.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.StockNumberDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import java.util.ArrayList;
import java.util.List;

public class GetStockNumberAdapter extends BaseAdapter{
	private final Activity mContext;
	private final List<StockNumberDTO> arrPakageChargeBeans  = new ArrayList<>();
    private final List<StockNumberDTO> arraylist = new ArrayList<>();
	public GetStockNumberAdapter(List<StockNumberDTO> arr, Activity context) {
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
		return arraylist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class ViewHolder {
		TextView txtpstn;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			mView = inflater.inflate(R.layout.connectionmobile_item_pakage, arg2,
					false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		StockNumberDTO pakageChargeBeans = arrPakageChargeBeans.get(arg0);
		if(pakageChargeBeans.getIsdn() != null && !pakageChargeBeans.getIsdn().isEmpty()){
			holder.txtpstn.setText(pakageChargeBeans.getIsdn());
		}
		
		
		return mView;
	}
	
	public List<StockNumberDTO> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrPakageChargeBeans.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrPakageChargeBeans.addAll(arraylist);
		}else{
			for (StockNumberDTO pakageChargeBeans : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(pakageChargeBeans.getIsdn()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrPakageChargeBeans.add(pakageChargeBeans);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrPakageChargeBeans;
	}
	
	
	
}
