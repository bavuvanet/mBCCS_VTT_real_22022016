package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ContractPromotionObj;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContractPromotionAdapter extends BaseAdapter {

	private final Activity activity;
	private final List<ContractPromotionObj> arrContract;

	public ContractPromotionAdapter(List<ContractPromotionObj> arrContract,
			Activity activity) {
		this.arrContract = arrContract;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return arrContract.size();
	}

	@Override
	public Object getItem(int position) {
		return arrContract.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	static class ViewHolder {
		TextView tvCustomerName;
		TextView tvContractNo;
		TextView tvPromotionName;
		TextView tvMonthPromotion;
		TextView tvPromotionIsdn;
		TextView tvPromotioncode;
		TextView tvPromotionnamekm;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_contract_promotion, parent,
					false);
			holder = new ViewHolder();
			holder.tvCustomerName = (TextView) mView
					.findViewById(R.id.tvCustomerName);
			holder.tvContractNo = (TextView) mView
					.findViewById(R.id.tvContractNo);
			holder.tvPromotionName = (TextView) mView
					.findViewById(R.id.tvPromotionName);
			holder.tvMonthPromotion = (TextView) mView
					.findViewById(R.id.tvMonthPromotion);
			holder.tvPromotionIsdn = (TextView) mView
					.findViewById(R.id.tvPromotionIsdn);
			holder.tvPromotioncode = (TextView) mView.findViewById(R.id.tvPromotioncode);
			holder.tvPromotionnamekm = (TextView) mView.findViewById(R.id.tvPromotionnamekm);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		ContractPromotionObj contract = arrContract.get(position);
		if (contract != null) {
			holder.tvCustomerName.setText(contract.getCustomerName());
			holder.tvContractNo.setText(contract.getContractCode());
			holder.tvPromotionName.setText(contract.getStartTimePromotions());
			holder.tvMonthPromotion.setText(contract.getEndTimePromotions());
			holder.tvPromotionIsdn.setText(contract.getIsdn());
			holder.tvPromotioncode.setText(contract.getPromotionCode());
			holder.tvPromotionnamekm.setText(contract.getPromotionName());
		}
		return mView;
	}

}
