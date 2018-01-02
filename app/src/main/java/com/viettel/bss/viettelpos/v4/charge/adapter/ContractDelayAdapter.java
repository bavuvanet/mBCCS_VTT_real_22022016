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

public class ContractDelayAdapter extends BaseAdapter {

	private List<ChargeContractItem> lisChargeItemObjectDels = new ArrayList<>();
	private final Activity mActivity;

    public ContractDelayAdapter(List<ChargeContractItem> arr, Activity mActivity, boolean _isPaymentStatus) {
		this.lisChargeItemObjectDels = arr;
		this.mActivity = mActivity;
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
		TextView tvAddress;
		TextView tvCusName;
		TextView tvContractNo;
		TextView tvDebt;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_contract_delay, parent, false);
			holder = new ViewHolder();
			holder.tvAddress = (TextView) mView.findViewById(R.id.tvAddress);
			holder.tvCusName = (TextView) mView.findViewById(R.id.tvCusName);
			holder.tvContractNo = (TextView) mView.findViewById(R.id.tvContractNo);
			holder.tvDebt = (TextView) mView.findViewById(R.id.tvDebt);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		ChargeContractItem obj = lisChargeItemObjectDels.get(position);
		
		holder.tvAddress.setText(obj.getAddress());
		holder.tvCusName.setText(obj.getCustomerName());
		holder.tvContractNo.setText(obj.getContractNo());
		if (obj.getDebit() != null) {
			holder.tvDebt.setText(StringUtils.formatMoney(obj.getDebit()));
		}
		return mView;
	} 
}
