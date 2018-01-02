package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.report.object.BonusCommTransaction;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class AdapterReportMoneyInfo extends BaseAdapter {

	private final Activity mActivity;
	private final ArrayList<BonusCommTransaction> listBonusCommTransaction;

	public AdapterReportMoneyInfo(Activity mActivity,
			ArrayList<BonusCommTransaction> listBonusCommTransaction) {
		super();
		this.mActivity = mActivity;
		this.listBonusCommTransaction = listBonusCommTransaction;
	}

	@Override
	public int getCount() {
		return listBonusCommTransaction.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listBonusCommTransaction.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView tv_billDate;
		TextView tv_groupName;
		TextView tv_service;
		TextView tv_total_money; 
		TextView tv_number_subscribers;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_report_money_info, parent,
					false);
			holder = new ViewHolder();
			holder.tv_billDate = (TextView) mView
					.findViewById(R.id.tv_billDate);
			holder.tv_groupName = (TextView) mView
					.findViewById(R.id.tv_groupName);
			holder.tv_service = (TextView) mView
					.findViewById(R.id.tv_service);
			holder.tv_total_money = (TextView) mView
					.findViewById(R.id.tv_total_money);
			holder.tv_number_subscribers = (TextView) mView.findViewById(R.id.tv_number_subscribers);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		BonusCommTransaction mObject = listBonusCommTransaction.get(position);
		if (mObject != null) {
			holder.tv_billDate.setText(mObject.getBillDate());
			holder.tv_groupName.setText(mObject.getGroupName());
			holder.tv_service.setText(mObject.getService()); 
			holder.tv_total_money.setText(StringUtils.formatMoney(mObject.getTotalMoneyStr()));
			holder.tv_number_subscribers.setText(mObject.getQuantity());
		}
		return mView;
	}
}
