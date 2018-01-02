package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;

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

public class GetPromotionAdapter extends BaseAdapter{
	private final Context mContext;
	private final ArrayList<PromotionTypeBeans> arrPromotionTypeBeans  = new ArrayList<>();
    private final ArrayList<PromotionTypeBeans> arraylist = new ArrayList<>();
	public GetPromotionAdapter(ArrayList<PromotionTypeBeans> arr, Context context) {
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrPromotionTypeBeans.addAll(arr);
		}
		
		
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrPromotionTypeBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrPromotionTypeBeans.get(arg0);
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
		final PromotionTypeBeans promotionTypeBeans = arrPromotionTypeBeans.get(arg0);
		
		if(!CommonActivity.isNullOrEmpty(promotionTypeBeans.getDescription())){
			holder.btninfo.setVisibility(View.VISIBLE);
		}else{
			holder.btninfo.setVisibility(View.GONE);
		}
		
		if(promotionTypeBeans.getCodeName() != null && !promotionTypeBeans.getCodeName().isEmpty()){
			holder.txtpstn.setText(promotionTypeBeans.getCodeName());
		}
		holder.btninfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CommonActivity.createAlertDialog(mContext, promotionTypeBeans.getDescription(), mContext.getString(R.string.app_name)).show();
			}
		});
		
		return mView;
	}
	
	public ArrayList<PromotionTypeBeans> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrPromotionTypeBeans.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrPromotionTypeBeans.addAll(arraylist);
		}else{
			for (PromotionTypeBeans promotionTypeBeans : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(promotionTypeBeans.getCodeName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrPromotionTypeBeans.add(promotionTypeBeans);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrPromotionTypeBeans;
	}
	
	
	
}
