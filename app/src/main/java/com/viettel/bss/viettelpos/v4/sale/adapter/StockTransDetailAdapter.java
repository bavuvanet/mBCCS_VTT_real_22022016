package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransDetail;

public class StockTransDetailAdapter extends BaseAdapter {
	private final Context context;
	private final ArrayList<StockTransDetail> lstData;

	public StockTransDetailAdapter(Context context,
			ArrayList<StockTransDetail> lstData) {
		this.context = context;
		this.lstData = lstData;
	}

	@Override
	public int getCount() {
		if (lstData != null) {
			return lstData.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if (lstData != null && !lstData.isEmpty()) {
			return lstData.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tvStockModelCode;
		TextView tvStockModelName;
		TextView tvQuantity;
		TextView tvState;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		View mView = convertView;
		ViewHoder hoder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater.inflate(R.layout.stock_trans_detail_item, arg2,
					false);

			hoder = new ViewHoder();
			hoder.tvStockModelCode = (TextView) mView
					.findViewById(R.id.tvStockModelCode);
			hoder.tvStockModelName = (TextView) mView
					.findViewById(R.id.tvStockModelName);
			hoder.tvQuantity = (TextView) mView
					.findViewById(R.id.tvStockModelQuantity);
			hoder.tvState = (TextView) mView
					.findViewById(R.id.tvStockModelState);

			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final StockTransDetail item = lstData.get(position);
		if (item != null) {
			hoder.tvStockModelCode.setText(item.getStockModelCode());
			hoder.tvStockModelName.setText(item.getStockModelName());
			hoder.tvQuantity.setText(item.getQuantity() + " "
					+ item.getUnitName());
			Long stateId = item.getStateId();
			String stateName = "";
			if (stateId == null) {
				stateName = "";
			}
			if (stateId.compareTo(1L) == 0) {
				// Hang moi
				stateName = context.getResources()
						.getString(R.string.state_new);
			} else if (stateId.compareTo(2L) == 0) {
				stateName = context.getResources()
						.getString(R.string.state_old);
				// Hang cu
			} else if (stateId.compareTo(3L) == 0) {
				stateName = context.getResources().getString(
						R.string.state_broken);
				// Hang hong
			}
			hoder.tvState.setText(stateName);
		}
		return mView;
	}
}
