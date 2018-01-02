package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GetHTHMAdapter extends BaseAdapter{
	private final Context mContext;
	private final ArrayList<HTHMBeans> arrHTHTM  = new ArrayList<>();
    private final ArrayList<HTHMBeans> arraylist = new ArrayList<>();
	
	private final int MAX_ROW = 20;
	
	public GetHTHMAdapter(ArrayList<HTHMBeans> arr, Context context) {
		//this.arraylist = new ArrayList<PakageChargeBeans>();
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrHTHTM.addAll(arr);
		}
		
		
		mContext = context;
	}
	
	@Override
	public int getCount() {
		
		if(arrHTHTM == null || arrHTHTM.size() == 0){
			return 0;
		}else{
			return arrHTHTM.size();
		}
		
	}

	@Override
	public Object getItem(int arg0) {
		return arrHTHTM.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
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
		
		
		final HTHMBeans hthmBeans = arrHTHTM.get(arg0);
		if(!CommonActivity.isNullOrEmpty(hthmBeans.getDescription())){
			holder.btninfo.setVisibility(View.VISIBLE);
		}else{
			holder.btninfo.setVisibility(View.GONE);
		}
		if(hthmBeans.getCodeName() != null && !hthmBeans.getCodeName().isEmpty()){
			holder.txtpstn.setText(hthmBeans.getCodeName());
		}
		holder.btninfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CommonActivity.createAlertDialog(mContext, hthmBeans.getDescription(), mContext.getString(R.string.app_name)).show();
			}
		});
		
		return mView;
	}
	
	public ArrayList<HTHMBeans> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrHTHTM.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrHTHTM.addAll(arraylist);
		}else{
			for (HTHMBeans hTBeans : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(hTBeans.getCodeName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrHTHTM.add(hTBeans);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrHTHTM;
	}
	
	
	
}
