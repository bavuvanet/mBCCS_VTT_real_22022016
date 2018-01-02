package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.MPaymentContractBean;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContractPaymentHistoryAdapter extends BaseAdapter {

	private List<MPaymentContractBean> lstMPaymentContractBean = new ArrayList<>();
	
	private final Activity activity;

	public ContractPaymentHistoryAdapter(List<MPaymentContractBean> arr, Activity _activity) {
		this.lstMPaymentContractBean = arr;
		this.activity = _activity;
	}

	@Override
	public int getCount() {
		return  lstMPaymentContractBean.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	static class ViewHolder {

		TextView txtAmount;
		TextView txtCreateDate;
		TextView txtPaymentTypeName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.item_contract_payment_history, parent, false);
			holder = new ViewHolder();
			holder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);
			holder.txtCreateDate = (TextView) convertView.findViewById(R.id.tvCreateDate);
			holder.txtPaymentTypeName = (TextView) convertView.findViewById(R.id.txtPaymentTypeName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		MPaymentContractBean obj = lstMPaymentContractBean.get(position);
		holder.txtAmount.setText(obj.getAmount());
		holder.txtCreateDate.setText(obj.getCreateDate());
		holder.txtPaymentTypeName.setText(obj.getPaymentTypeName());

		return convertView;
	} 
}
