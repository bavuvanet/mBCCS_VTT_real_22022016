package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeContractItem;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContractPaymentAdapter extends BaseAdapter {

	private List<ChargeContractItem> lisChargeItemObjectDels = new ArrayList<>();
	private final Activity mActivity;
	private final boolean isPaymentStatus;

	public ContractPaymentAdapter(List<ChargeContractItem> arr, Activity mActivity, boolean _isPaymentStatus) {
		this.lisChargeItemObjectDels = arr;
		this.mActivity = mActivity;
		this.isPaymentStatus = _isPaymentStatus;
	}

	@Override
	public int getCount() {
		return  lisChargeItemObjectDels.size();
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
		TextView tvPhoneNumberContact;
		TextView tvCusName;
		TextView tvAddress;
		TextView tvTotalPayment;
		TextView tvPaymentStatus;
		TextView tvContractMngt;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_contract_payment, parent, false);
			holder = new ViewHolder();
			holder.tvPhoneNumberContact = (TextView) mView.findViewById(R.id.tvPhoneNumberContact);
			holder.tvCusName = (TextView) mView.findViewById(R.id.tvCusName);
			holder.tvAddress = (TextView) mView.findViewById(R.id.tvAddress);
			holder.tvTotalPayment = (TextView) mView.findViewById(R.id.tvTotalPayment);
			holder.tvPaymentStatus = (TextView) mView.findViewById(R.id.tvPaymentStatus);
			holder.tvContractMngt = (TextView) mView.findViewById(R.id.tvContractMngt);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		ChargeContractItem obj = lisChargeItemObjectDels.get(position);
		
		holder.tvPhoneNumberContact.setText(obj.getIsdn());
		holder.tvCusName.setText(obj.getPayer());
		holder.tvAddress.setText(obj.getAddress());
		holder.tvTotalPayment.setText(StringUtils.formatMoney(obj.getTotCharge()));
		holder.tvContractMngt.setText(obj.getContractFormMngtName());
		if (!isPaymentStatus) {
			holder.tvPaymentStatus.setVisibility(View.GONE); 
		} else {
			holder.tvPaymentStatus.setVisibility(View.VISIBLE);	
//			Log.e(Constant.TAG, "chargeItemObjectDel.getPaymentStatus(): " + chargeItemObjectDel.getPaymentStatus());
			if (!CommonActivity.isNullOrEmpty(obj.getPaymentStatus())) {
				int paymentStatus = Integer.parseInt(obj.getPaymentStatus());
				String payment_status = mActivity.getResources().getStringArray(R.array.payment_status)[paymentStatus];
				holder.tvPaymentStatus.setText(payment_status);
			}
		}

		return mView;
	} 
}
