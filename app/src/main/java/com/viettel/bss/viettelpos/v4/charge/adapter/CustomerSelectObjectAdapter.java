package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerSelectObjectAdapter extends ArrayAdapter<ContractFormMngtBean> {
	private Context mContext;
	public OnCheckSelectObject onCheckSelectObject;
	private ArrayList<ContractFormMngtBean> lsContractFormMngtBeans = new ArrayList<ContractFormMngtBean>();
	private ArrayList<ContractFormMngtBean> arrayList;
	public interface OnCheckSelectObject {
		public void onCheckSelectObject(ContractFormMngtBean contractFormMngtBean);
	}
	
	public CustomerSelectObjectAdapter(
			ArrayList<ContractFormMngtBean> lsContractFormMngtBeans, Context context , OnCheckSelectObject onCheckSelectObject) {
		super(context, R.layout.item_select_customer_object, lsContractFormMngtBeans);
		mContext = context;
		this.onCheckSelectObject = onCheckSelectObject;
		this.arrayList = new ArrayList<ContractFormMngtBean>();
		if (lsContractFormMngtBeans != null) {
			this.lsContractFormMngtBeans.addAll(lsContractFormMngtBeans);
			this.arrayList = lsContractFormMngtBeans;
		}
	}
	
	class ViewHolder {
		CheckBox cbCustomerName;
		TextView tvCustomerCode;
		ImageView btnNext;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_select_customer_object, parent, false);
			holder = new ViewHolder();
			holder.cbCustomerName = (CheckBox) mView.findViewById(R.id.cbCustomerName);
			holder.tvCustomerCode = (TextView) mView.findViewById(R.id.tvCustomerCode);
			holder.btnNext = (ImageView) mView.findViewById(R.id.btnNext);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		final ContractFormMngtBean item = getItem(position);
		holder.tvCustomerCode.setVisibility(View.VISIBLE);
		if(item != null){
			if(!CommonActivity.isNullOrEmptyArray(item.getLstContractFormMngtBeanPayServ()) && item.getLstContractFormMngtBeanPayServ().size() > 0){
				holder.btnNext.setVisibility(View.VISIBLE);
				holder.cbCustomerName.setVisibility(View.INVISIBLE);
				
			}else{
				holder.btnNext.setVisibility(View.INVISIBLE);
				holder.cbCustomerName.setVisibility(View.VISIBLE);
			}
//			holder.tvCustomerCode.setText(item.getCode() + "_"+item.getName());
			holder.tvCustomerCode.setText(item.getName());
			holder.cbCustomerName.setText("");
			holder.cbCustomerName.setChecked(item.isCheck());
			holder.cbCustomerName.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					onCheckSelectObject.onCheckSelectObject(item);
				}
			});	
		}
		

		return mView;
	}
	public void SearchInput(String chartext){
		chartext = chartext.toLowerCase();
		lsContractFormMngtBeans.clear();
		if(chartext.length() == 0){
			Log.d("size arraylist", ""+arrayList.size());
			lsContractFormMngtBeans.addAll(arrayList);
		}else{
			for (ContractFormMngtBean vInfoOjectMerge : arrayList) {
//				if(CommonActivity.convertUnicode2Nosign((vInfoOjectMerge.getCode()+"_"+vInfoOjectMerge.getName()).toLowerCase()).contains(CommonActivity.convertUnicode2Nosign(chartext))){
//					lsContractFormMngtBeans.add(vInfoOjectMerge);
//				}
				if(CommonActivity.convertUnicode2Nosign(vInfoOjectMerge.getName()).toLowerCase().contains(CommonActivity.convertUnicode2Nosign(chartext))){
					lsContractFormMngtBeans.add(vInfoOjectMerge);
				}
			}
		}
		notifyDataSetChanged();
	}
}
