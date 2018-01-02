package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

public class InfoCustomerChargeDelAdapter extends BaseAdapter {

	private List<ChargeContractItem> lisChargeItemObjectDels = new ArrayList<>();
	private final Activity mActivity;
	private final boolean isPaymentStatus;

	public InfoCustomerChargeDelAdapter(List<ChargeContractItem> arr, Activity mActivity, boolean _isPaymentStatus) {
		this.lisChargeItemObjectDels = arr;
		this.mActivity = mActivity;
		this.isPaymentStatus = _isPaymentStatus;
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
		TextView tvContractNo;
		TextView tvPhoneNumber;
		TextView tvAmount;
		TextView paymentStatus;
		LinearLayout lnPaymentStatus;

		TextView tvContractStatus;
		LinearLayout lnContractStatus;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_charge_customer_by_ctv, parent, false);
			holder = new ViewHolder();
			holder.tvContractNo = (TextView) mView.findViewById(R.id.tvContractNo);
			holder.tvPhoneNumber = (TextView) mView.findViewById(R.id.tvPhoneNumber);
			holder.tvAmount = (TextView) mView.findViewById(R.id.tvAmount);
			holder.paymentStatus = (TextView) mView.findViewById(R.id.paymentStatus);

			holder.lnPaymentStatus = (LinearLayout) mView.findViewById(R.id.lnPaymentStatus);

			holder.tvContractStatus = (TextView) mView.findViewById(R.id.tvContractStatus);
			holder.lnContractStatus = (LinearLayout) mView.findViewById(R.id.lnContractStatus);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		ChargeContractItem chargeItemObjectDel = lisChargeItemObjectDels.get(position);
		holder.tvContractNo.setText(chargeItemObjectDel.getContractNo());
		holder.tvPhoneNumber.setText(chargeItemObjectDel.getIsdn());
		holder.tvAmount.setText(StringUtils.formatMoney(chargeItemObjectDel.getTotCharge()));

		if (!isPaymentStatus) {
			holder.lnPaymentStatus.setVisibility(View.GONE);
		} else {
			if (chargeItemObjectDel.getPaymentStatus() != null && !chargeItemObjectDel.getPaymentStatus().isEmpty()) {
				int paymentStatus = Integer.parseInt(chargeItemObjectDel.getPaymentStatus());
				if (paymentStatus > 0) {
					holder.lnPaymentStatus.setVisibility(View.VISIBLE);
					String payment_status = mActivity.getResources().getStringArray(R.array.payment_status)[paymentStatus];
					holder.paymentStatus.setText(payment_status);
				}
			}
		}

		if (chargeItemObjectDel.getStatus() == null || chargeItemObjectDel.getStatus().isEmpty()) {
			holder.lnContractStatus.setVisibility(View.GONE);
		} else {
			holder.lnContractStatus.setVisibility(View.VISIBLE);
			int contractStatus = Integer.parseInt(chargeItemObjectDel.getStatus());
			String contract_status = mActivity.getResources().getStringArray(R.array.contract_status)[contractStatus];
			holder.tvContractStatus.setText(contract_status);
			if (contractStatus == 0 || contractStatus == 3) {
				holder.tvContractStatus.setTextColor(Color.RED);
			} else {
				holder.tvContractStatus.setTextColor(Color.BLACK);
			}
		}

		return mView;
	}
}
