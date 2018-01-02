package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContractVerifyAdapter extends BaseAdapter {

	private List<ChargeContractItem> lisChargeItemObjectDels = new ArrayList<>();
	private final Activity mActivity;
	private boolean isPaymentStatus;

	public ContractVerifyAdapter(List<ChargeContractItem> arr, Activity mActivity) {
		this.lisChargeItemObjectDels = arr;
		this.mActivity = mActivity;
	}

	@Override
	public int getCount() {
		return lisChargeItemObjectDels.size();
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
		TextView tvTotCharge;
		TextView tvVerifyStatus;
		TextView tvVerifyDate;
		TextView tvXpos;
		TextView tvYpos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_contract_verify, parent, false);
			holder = new ViewHolder();
			holder.tvTotCharge = (TextView) mView.findViewById(R.id.tvTotCharge);
			holder.tvVerifyStatus = (TextView) mView.findViewById(R.id.tvVerifyStatus);
			holder.tvVerifyDate = (TextView) mView.findViewById(R.id.tvVerifyDate);
			holder.tvXpos = (TextView) mView.findViewById(R.id.tvXpos);
			holder.tvYpos = (TextView) mView.findViewById(R.id.tvYpos);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		ChargeContractItem chargeItemObjectDel = lisChargeItemObjectDels.get(position);
		holder.tvTotCharge.setText(mActivity.getResources().getString(R.string.tv_amount) + ": "
				+ StringUtils.formatMoney(chargeItemObjectDel.getTotCharge()));
		holder.tvVerifyStatus.setText(mActivity.getString(R.string.spnVerifyType) + ": " + chargeItemObjectDel.getVerifyStatus());
		
		if(chargeItemObjectDel.getVerifyDate() != null && !chargeItemObjectDel.getVerifyDate().isEmpty()) {
			holder.tvVerifyDate.setVisibility(View.VISIBLE);
			holder.tvVerifyDate.setText(mActivity.getString(R.string.ver_date) + ": " + chargeItemObjectDel.getVerifyDate());
		} else {
			holder.tvVerifyDate.setVisibility(View.GONE);
		}
		holder.tvXpos.setText(mActivity.getString(R.string.xPos) + ": " + chargeItemObjectDel.getxPos());
		holder.tvYpos.setText(mActivity.getString(R.string.yPos) + ": " + chargeItemObjectDel.getyPos());
		return mView;
	}
}
