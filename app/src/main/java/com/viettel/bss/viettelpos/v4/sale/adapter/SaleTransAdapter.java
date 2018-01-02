package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.SaleTrans;

public class SaleTransAdapter extends BaseAdapter {
	private final Context context;
	private final List<SaleTrans> lstData = new ArrayList<>();
	private final List<SaleTrans> lstTmp = new ArrayList<>();
	private final OnChangeCheckedState onChangeCheckedState;

	public interface OnChangeCheckedState {
		void onChangeChecked(SaleTrans saleTrans);
	}

	public SaleTransAdapter(Context context, List<SaleTrans> lstData,
			OnChangeCheckedState onChangeCheckedState) {
		this.context = context;
		if (lstData != null) {
			this.lstData.addAll(lstData);
			this.lstTmp.addAll(lstData);
		}
		this.onChangeCheckedState = onChangeCheckedState;
	}

	@Override
	public int getCount() {
		if (lstData == null) {
			return 0;
		} else {
			return lstData.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tvCusName;
		TextView tvCode;
		TextView tvPreTax;
		TextView tvDiscount;
		TextView tvTax;
		TextView tvTotalMoney;
		CheckBox checkBox;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.create_invoice_sale_trans_row,
					viewGroup, false);

			holder = new ViewHoder();
			holder.tvCusName = (TextView) row.findViewById(R.id.tvCusName);

			holder.tvCode = (TextView) row.findViewById(R.id.tvCode);
			holder.tvDiscount = (TextView) row.findViewById(R.id.tvDiscount);
			holder.tvPreTax = (TextView) row.findViewById(R.id.tvPreTax);
			holder.tvTotalMoney = (TextView) row
					.findViewById(R.id.tvTotalMoney);
			holder.tvTax = (TextView) row.findViewById(R.id.tvTax);
			holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		final SaleTrans item = lstData.get(position);
		if (item != null) {
			holder.tvCode.setText(item.getSaleTransCode());
			holder.tvCusName.setText(item.getCustName());
			holder.tvDiscount.setText(context.getResources().getString(
					R.string.discount)
					+ ": " + StringUtils.formatMoney(item.getDiscount()));
			holder.tvTotalMoney.setText(context.getResources().getString(
					R.string.tv_amount)
					+ ": " + StringUtils.formatMoney(item.getAmountTax()));
			holder.tvPreTax.setText(context.getResources().getString(
					R.string.moneyBeforTax)
					+ ": " + StringUtils.formatMoney(item.getAmountNotTax()));
			holder.tvTax.setText(context.getResources().getString(
					R.string.moneyTax)
					+ ": " + StringUtils.formatMoney(item.getTax()));
			holder.checkBox.setChecked(item.isChecked());
			holder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					onChangeCheckedState.onChangeChecked(item);
				}
			});
		}
		return row;
	}

	public void filter(String charText) {
		lstData.clear();
		if (charText.isEmpty()) {
			lstData.addAll(lstTmp);
		} else {
			for (SaleTrans item : lstTmp) {
				if (item.getSaleTransCode().toLowerCase()
						.contains(charText.toLowerCase())) {
					lstData.add(item);
				}
			}

			// lstData.addAll(SaleCommons.getRangeSerial(lstSerial));
		}
		notifyDataSetChanged();
	}
}
