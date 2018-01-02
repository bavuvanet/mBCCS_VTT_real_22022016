package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetFeeBCCSAdapter extends BaseAdapter{
	private final Context mContext;
	private ArrayList<ProductPackageFeeDTO> arrPakageFeeDTO = new ArrayList<>();
	
	public GetFeeBCCSAdapter(ArrayList<ProductPackageFeeDTO> arr, Context context) {
		this.arrPakageFeeDTO = arr;
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return arrPakageFeeDTO.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrPakageFeeDTO.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class ViewHolder {
		TextView namefee,price;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;
		
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.layout_changesim_viewfee_item, arg2,
					false);
			holder = new ViewHolder();
			holder.namefee = (TextView) mView.findViewById(R.id.namefee);
			holder.price = (TextView) mView.findViewById(R.id.price);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		ProductPackageFeeDTO productPackageFeeDTO = arrPakageFeeDTO.get(arg0);

		if(productPackageFeeDTO != null && !CommonActivity.isNullOrEmpty(productPackageFeeDTO.getPrice())){
			holder.namefee.setText(productPackageFeeDTO.getName());
			holder.price.setText(StringUtils.formatMoney(productPackageFeeDTO.getPrice()));
		}

		return mView;
	}

	
	
	
}
