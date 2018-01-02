package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.work.object.FamilyInforBean;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetFamilyInfoAdapter extends BaseAdapter{
	private final Context mContext;
	private final List<FamilyInforBean> arrFamilyInforBeans  = new ArrayList<>();
    private final List<FamilyInforBean> arraylist = new ArrayList<>();
	private final OnDeleteFamily onDeleteFamily;
	private final OnEditFamily onEditFamily;
	
	public interface OnDeleteFamily {
		void onDeleteFamily(FamilyInforBean familyInforBean);
	}

	public interface OnEditFamily {
		void onEditFamily(FamilyInforBean familyInforBean);
	}
	
	
	public GetFamilyInfoAdapter(List<FamilyInforBean> arr, Context context , OnEditFamily onEditFamily, OnDeleteFamily onDeleteFamily) {
		if(arr != null){
			this.arraylist.addAll(arr);
			this.arrFamilyInforBeans.addAll(arr);
		}
		this.onDeleteFamily = onDeleteFamily;
		this.onEditFamily = onEditFamily;
		
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrFamilyInforBeans.size();
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
		TextView tvarea;
		TextView tvmahd;
		TextView tvtenhd;
		TextView tvphone;
		LinearLayout imgEdit;
		LinearLayout imgDelete;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_family, arg2,
					false);
			holder = new ViewHolder();
			holder.tvarea = (TextView) mView.findViewById(R.id.tvarea);
			holder.tvmahd = (TextView) mView.findViewById(R.id.tvmahd);
			holder.tvtenhd = (TextView) mView.findViewById(R.id.tvtenhd);
			holder.tvphone = (TextView) mView.findViewById(R.id.tvphone);
			holder.imgEdit = (LinearLayout) mView.findViewById(R.id.imgEdit);
			holder.imgDelete = (LinearLayout) mView.findViewById(R.id.imgDelete);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		
		final FamilyInforBean familyInforBean = arrFamilyInforBeans.get(arg0);
		
	
			holder.tvarea.setText(familyInforBean.getAddress());
			holder.tvmahd.setText(familyInforBean.getFamilyInforCode());
			holder.tvtenhd.setText(familyInforBean.getFamilyName());
			holder.tvphone.setText(familyInforBean.getPhone());
			
			holder.imgEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				onEditFamily.onEditFamily(familyInforBean);	
				}
			});
			
			holder.imgDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					onDeleteFamily.onDeleteFamily(familyInforBean);
				}
			});
		
		return mView;
	}
	
	public List<FamilyInforBean> SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		arrFamilyInforBeans.clear();
		if(chartext.isEmpty()){
			Log.d("size arraylist", ""+arraylist.size());
			arrFamilyInforBeans.addAll(arraylist);
		}else{
			for (FamilyInforBean familyInforBean : arraylist) {
				if(CommonActivity.convertUnicode2Nosign(familyInforBean.getFamilyName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					arrFamilyInforBeans.add(familyInforBean);
				}
			}
		}
		
		notifyDataSetChanged();
		return arrFamilyInforBeans;
	}
	
	
}
