package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.ReportProgressBean;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReportProgessAdapter extends ArrayAdapter<ReportProgressBean> {

	public ReportProgessAdapter(Context context, List<ReportProgressBean> values) {
		super(context, R.layout.item_report_progess, values);
	}

	static class ViewHolder {
		TextView tvCusName;
		TextView tvBeginCus;
		TextView tvBeginMoney;
		TextView tvBeginCusCharged;
		TextView tvBeginMoneyCharged;
		TextView tvCusCancel;
		TextView tvMoneyCancel;
		TextView tvPercentCustommer;
		TextView tvPercentMoney;
		TextView tvContractFormMngt;
		TextView tvContractFormMngtName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder = null;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.item_report_progess, parent, false);
			holder = new ViewHolder();

			holder.tvCusName = (TextView) v.findViewById(R.id.tvCusName);
			holder.tvBeginCus = (TextView) v.findViewById(R.id.tvBeginCus);
			holder.tvBeginMoney = (TextView) v.findViewById(R.id.tvBeginMoney);
			holder.tvBeginCusCharged = (TextView) v.findViewById(R.id.tvBeginCusCharged);
			holder.tvBeginMoneyCharged = (TextView) v.findViewById(R.id.tvBeginMoneyCharged);
			holder.tvCusCancel = (TextView) v.findViewById(R.id.tvCusCancel);
			holder.tvMoneyCancel = (TextView) v.findViewById(R.id.tvMoneyCancel);
			holder.tvPercentCustommer = (TextView) v.findViewById(R.id.tvPercentCustommer);
			holder.tvPercentMoney = (TextView) v.findViewById(R.id.tvPercentMoney);
			holder.tvContractFormMngt = (TextView) v.findViewById(R.id.tvContractFormMngt);
			holder.tvContractFormMngtName = (TextView) v.findViewById(R.id.tvContractFormMngtName);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		ReportProgressBean reportProgressBean = getItem(position);

		holder.tvPercentCustommer.setText(reportProgressBean.getCustRate());

		Double d = 0.00D;
		try {
			d = Double.parseDouble(reportProgressBean.getChargeRate());
			d = Math.round(d * 100) / 100.00D;
		} catch (Exception e) {
			e.printStackTrace();
		}

		holder.tvPercentMoney.setText(d.toString());
		holder.tvBeginMoney.setText(StringUtils.formatMoney(reportProgressBean.getChargeSta()));
		holder.tvBeginCusCharged.setText(StringUtils.formatMoney(reportProgressBean.getCustPayment()));
		holder.tvCusCancel.setText(StringUtils.formatMoney(reportProgressBean.getCustDebit()));
		holder.tvBeginCus.setText(reportProgressBean.getCustSta());
		holder.tvMoneyCancel.setText(StringUtils.formatMoney(reportProgressBean.getDebit()));
		holder.tvBeginMoneyCharged.setText(StringUtils.formatMoney(reportProgressBean.getPayment()));
		holder.tvCusName.setText(reportProgressBean.getContractName());
		holder.tvContractFormMngt.setText(reportProgressBean.getContractFormMngt());
		holder.tvContractFormMngtName.setText(reportProgressBean.getContractFormMngtName());

		return v;
	}
}
