package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PakageChargeBeans;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GetPakageBundleAdapter extends BaseAdapter{
	private final Context mContext;
	private final ArrayList<PakageChargeBeans> arrPakageChargeBeans  = new ArrayList<>();
    private final ArrayList<PakageChargeBeans> arraylist = new ArrayList<>();
	public GetPakageBundleAdapter(ArrayList<PakageChargeBeans> arr, Context context) {
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
		Button btninfo;
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
			holder.btninfo = (Button) mView.findViewById(R.id.btninfo);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		
		final PakageChargeBeans pakageChargeBeans = arrPakageChargeBeans.get(arg0);
		if(!CommonActivity.isNullOrEmpty(pakageChargeBeans.getDescription())){
			holder.btninfo.setVisibility(View.VISIBLE);
		}else{
			holder.btninfo.setVisibility(View.GONE);
		}
			
		
		if(pakageChargeBeans.getOfferName() != null && !pakageChargeBeans.getOfferName().isEmpty()){
			holder.txtpstn.setText(pakageChargeBeans.getOfferName());
		}
		holder.btninfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CommonActivity.createAlertDialog(mContext, pakageChargeBeans.getDescription(), mContext.getString(R.string.app_name)).show();
			}
		});
	
		return mView;
	}
	
	public ArrayList<PakageChargeBeans> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrPakageChargeBeans.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrPakageChargeBeans.addAll(arraylist);
		}else{
			for (PakageChargeBeans pakageChargeBeans : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(pakageChargeBeans.getOfferName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrPakageChargeBeans.add(pakageChargeBeans);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrPakageChargeBeans;
	}
	
	
	
}
