package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

public class ContractAdapter extends BaseAdapter {

	private List<ChargeContractItem> lisChargeItemObjectDels = new ArrayList<ChargeContractItem>();
	private final Activity context;

	public ContractAdapter(List<ChargeContractItem> arr, Activity context) {
		this.lisChargeItemObjectDels = arr;
		this.context = context;
	}

	@Override
	public int getCount() {
		return lisChargeItemObjectDels.size();
	}

	@Override
	public Object getItem(int position) {
		return lisChargeItemObjectDels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	static class ViewHolder {
		TextView tvContractNo;
		TextView tvIsdn;
		TextView tvAddress;
		TextView tvPayer;
		TextView tvIdNo;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater.inflate(R.layout.layout_contract_item, parent,
					false);
			holder = new ViewHolder();
			holder.tvContractNo = (TextView) mView
					.findViewById(R.id.tvContractNo);
			holder.tvIsdn = (TextView) mView.findViewById(R.id.tvIsdn);
			holder.tvAddress = (TextView) mView.findViewById(R.id.tvAddress);
			holder.tvIdNo = (TextView) mView.findViewById(R.id.tvIdNo);
			holder.tvPayer = (TextView) mView.findViewById(R.id.tvPayer);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		ChargeContractItem item = lisChargeItemObjectDels.get(position);
		String contractNo = item.getContractNo();
		if (CommonActivity.isNullOrEmpty(contractNo)) {
			contractNo = item.getContractId();
		}
		holder.tvContractNo.setText(position + 1 + ". "
				+ context.getString(R.string.sohdpay, contractNo));

		holder.tvIsdn.setText(context.getString(R.string.sub_scriber,
				item.getIsdn()));

		holder.tvAddress.setText(context.getString(R.string.address_notice,
				item.getAddress()));
		holder.tvPayer.setText(context.getString(R.string.namecuspay,
				item.getPayer()));
		holder.tvIdNo.setText(context.getString(R.string.so_cmtnd,
				item.getIdNo() == null ? "" : item.getIdNo()));

		return mView;
	}
}
