package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomerObjectAdapter extends BaseAdapter {

	private final Activity mActivity;
	private final List<ContractFormMngtBean> arrCustomerOJs;
	 
	
	public CustomerObjectAdapter(Activity mActivity,List<ContractFormMngtBean> arrCustomerOJs) {
		this.mActivity = mActivity;
		this.arrCustomerOJs = arrCustomerOJs;
	}
	
	@Override
	public int getCount() {
		return arrCustomerOJs.size();
	}

	@Override
	public Object getItem(int position) {
		return arrCustomerOJs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	static class ViewHolder {
		TextView txtCustomerName;
		TextView txtCustomerCode;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		ContractFormMngtBean customerContractBean = arrCustomerOJs.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater(); 
			mView = inflater.inflate(R.layout.item_customer_object,parent, false);
			holder = new ViewHolder();
			holder.txtCustomerName = (TextView) mView
					.findViewById(R.id.txt_customer_name);
			holder.txtCustomerCode = (TextView) mView
					.findViewById(R.id.txt_customer_code);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		} 
		
		holder.txtCustomerName.setText(customerContractBean.getName());
		holder.txtCustomerCode.setText(customerContractBean.getCode());
		

		return mView; 
	}

}
