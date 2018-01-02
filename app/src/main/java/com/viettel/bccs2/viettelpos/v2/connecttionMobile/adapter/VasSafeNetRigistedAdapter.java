package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubRelProductBO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VasSafeNetRigistedAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<SubRelProductBO> arrLstProductBO = new ArrayList<SubRelProductBO>();
	
	private OnChangeCheckedSubRelRegisted onChangeCheckedSubRelRegisted;
	private OnSelectPromotion onSelectPromotion;
	
	
	public interface OnChangeCheckedSubRelRegisted {
		public void onChangeCheckedSubRel(SubRelProductBO subRelProductBO);
	}
	
	public interface OnSelectPromotion{
		public void onSelectPromotion(SubRelProductBO subRelProductBO);
	}
	
	public VasSafeNetRigistedAdapter(ArrayList<SubRelProductBO> arr, Context context , OnChangeCheckedSubRelRegisted onChangeCheckedSubRel , OnSelectPromotion mOnSelectPromotion) {
		this.arrLstProductBO = arr;
		this.mContext = context;
		this.onChangeCheckedSubRelRegisted = onChangeCheckedSubRel;
		this.onSelectPromotion = mOnSelectPromotion;
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
		TextView tvpromotion;
		LinearLayout rowVasRegisted;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		final SubRelProductBO subRelProductBO = this.arrLstProductBO.get(arg0);
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_change_vas, arg2,
					false);
			holder = new ViewHolder();
			holder.cbVas = (CheckBox) mView.findViewById(R.id.cbVas);
			holder.imgPromotion = (ImageView) mView.findViewById(R.id.imgPromotion);
			holder.tvNameVas = (TextView) mView.findViewById(R.id.tvNameVas);
			holder.tvpromotion = (TextView) mView.findViewById(R.id.tvpromotion);
			holder.rowVasRegisted = (LinearLayout) mView.findViewById(R.id.rowVasRegisted);
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		
		
		if(!CommonActivity.isNullOrEmpty(subRelProductBO)){
			holder.cbVas.setText(subRelProductBO.getVasCode());
			if(!CommonActivity.isNullOrEmpty(subRelProductBO.getAtrrVas()) && Constant.VAS_SAFE_NET.equals(subRelProductBO.getAtrrVas())){
				holder.imgPromotion.setVisibility(View.VISIBLE);
			}else{
				holder.imgPromotion.setVisibility(View.GONE);
			}
		}
		
		// chon khuyen mai
//		if(!CommonActivity.isNullOrEmpty(subRelProductBO.getAtrrVas()) && Constant.VAS_SAFE_NET.equals(subRelProductBO.getAtrrVas())){
			if(!CommonActivity.isNullOrEmpty(subRelProductBO.getPromotionCode())){
//				holder.tvpromotion.setVisibility(View.VISIBLE);
				holder.tvpromotion.setText(subRelProductBO.getPromotionCode());
			}else{
//				holder.tvpromotion.setVisibility(View.GONE);
				holder.tvpromotion.setText("");
			}
//		}else{
//			subRelProductBO.setPromotionCode("");
//			subRelProductBO.setPromotionName("");
//			holder.tvpromotion.setVisibility(View.GONE);
//			holder.tvpromotion.setText("");
//		}
		holder.imgPromotion.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onSelectPromotion.onSelectPromotion(subRelProductBO);
			}
		});
		holder.cbVas.setChecked(subRelProductBO.isRegisted());
		holder.cbVas.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onChangeCheckedSubRelRegisted.onChangeCheckedSubRel(subRelProductBO);
			}
		});
		
		if(subRelProductBO.isEnable()){
			holder.cbVas.setEnabled(true);
			holder.imgPromotion.setEnabled(true);
		}else{
			holder.cbVas.setEnabled(false);
			holder.imgPromotion.setEnabled(false);
		}
		
		
		
		
		return mView;
	}

	
	
	
}
