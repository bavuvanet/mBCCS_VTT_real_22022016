package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubRelProductBO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class VasSafeNetUnRigistedAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<SubRelProductBO> arrLstProductBO = new ArrayList<SubRelProductBO>();
	
	private OnChangeCheckedSubRelUnRegisted onChangeCheckedSubRelUnRegisted;

	public interface OnChangeCheckedSubRelUnRegisted {
		public void onChangeCheckedSubRelUnRigisted(SubRelProductBO subRelProductBO);
	}
	public VasSafeNetUnRigistedAdapter(ArrayList<SubRelProductBO> arr, Context context , OnChangeCheckedSubRelUnRegisted onChangeCheckedSubRel) {
		this.arrLstProductBO = arr;
		this.mContext = context;
		this.onChangeCheckedSubRelUnRegisted = onChangeCheckedSubRel;
	}
	
	
	@Override
	public int getCount() {
		return arrLstProductBO.size();
	}

	@Override
	public Object getItem(int possition) {
		return arrLstProductBO.get(possition);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	class ViewHolder{
		CheckBox cbVas;
		ImageView imgPromotion;
		TextView tvNameVas;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_change_vas, arg2,
					false);
			holder = new ViewHolder();
			holder.cbVas = (CheckBox) mView.findViewById(R.id.cbVas);
			holder.imgPromotion = (ImageView) mView.findViewById(R.id.imgPromotion);
			holder.tvNameVas = (TextView) mView.findViewById(R.id.tvNameVas);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		final SubRelProductBO subRelProductBO = this.arrLstProductBO.get(arg0);
		if(!CommonActivity.isNullOrEmpty(subRelProductBO)){
			holder.cbVas.setText(subRelProductBO.getVasCode());
//			if(!CommonActivity.isNullOrEmpty(subRelProductBO.getAtrrVas()) && "VAS_SAFE_NET".equals(subRelProductBO.getAtrrVas())){
//				holder.imgPromotion.setVisibility(View.VISIBLE);
//			}else{
				holder.imgPromotion.setVisibility(View.GONE);
//			}
		}
		holder.cbVas.setChecked(subRelProductBO.isRegisted());
		holder.cbVas.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onChangeCheckedSubRelUnRegisted.onChangeCheckedSubRelUnRigisted(subRelProductBO);
			}
		});
		
		return mView;
	}
	
	
	
}
