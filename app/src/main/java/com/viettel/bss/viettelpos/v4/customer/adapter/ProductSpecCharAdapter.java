package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.ProductSpecCharObj;
import com.viettel.bss.viettelpos.v4.customer.object.ProductSpecCharValueObj;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductSpecCharAdapter extends BaseAdapter{
	private ArrayList<ProductSpecCharObj> lstData = new ArrayList<>();
	private final Context mContext;
	
	public ProductSpecCharAdapter(Context mContext, ArrayList<ProductSpecCharObj> lstData){
		this.lstData = lstData;
		this.mContext = mContext;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(CommonActivity.isNullOrEmptyArray(lstData)){
			return 0;
		}
		
		return lstData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(CommonActivity.isNullOrEmptyArray(lstData)){
			return null;
		}
		
		return lstData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ProductSpecCharObj item = lstData.get(position);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.layout_item_collect_cus_info, arg2,
					false);
			holder = new ViewHolder();
			holder.tvName = (TextView) mView.findViewById(R.id.tvName);
			holder.tvValue = (TextView) mView.findViewById(R.id.tvValue);
			holder.lnValue = (LinearLayout) mView.findViewById(R.id.lnValue);
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		holder.tvName.setText(item.getName());
		if(CommonActivity.isNullOrEmpty(item.getValue())){
			holder.lnValue.setVisibility(View.GONE);
		} else {
			holder.lnValue.setVisibility(View.VISIBLE);
		}

		String preSpin = "";
		for (int i = 0; i < item
				.getLstProductSpecCharValueObjs().size(); i++) {
			ProductSpecCharValueObj productSpecCharValueObj = item
					.getLstProductSpecCharValueObjs().get(i);
			if (!CommonActivity
					.isNullOrEmpty(item.getValue())) {
				if (item.getValue().startsWith(productSpecCharValueObj.getValue() + "_")) {
					preSpin = productSpecCharValueObj.getName();
					break;
				}
			}
		}

		String value = item.getValueName();
		if(value != null && value.contains("_")){
			value = value.substring(value.indexOf("_") + 1);
		}

		if(!CommonActivity.isNullOrEmptyArray(item.getLstProductSpecCharValueObjs())){
			if(!CommonActivity.isNullOrEmpty(item.getValueName())){
				for(ProductSpecCharValueObj proSpecCharValueObj : item.getLstProductSpecCharValueObjs()){
					if(proSpecCharValueObj.getValue().equals(item.getValue())){
						value = proSpecCharValueObj.getName();
						if(value != null && value.contains("_")){
							value = value.substring(value.indexOf("_") + 1);
						}
						if(!CommonActivity.isNullOrEmpty(preSpin)){
							value = preSpin + ": " + value;
						}
						holder.tvValue.setText(value);
						return mView;
					}
				}
			}
		}
		
		Log.d(getClass().getSimpleName(), item.getValue() == null ? "null" : item.getValue());
		Log.d(getClass().getSimpleName(), item.getValueName() == null ? "null" : item.getValueName());
//		holder.tvValue.setText(item.getValueName());
		if(!CommonActivity.isNullOrEmpty(preSpin)){
			value = preSpin + ": " + value;
		}
		holder.tvValue.setText(value);
		return mView;
	}
	
	public ArrayList<ProductSpecCharObj> getLstData(){
		return lstData;
	}
	
	class ViewHolder{
		TextView tvName;
		TextView tvValue;
		LinearLayout lnValue;
	}

}
