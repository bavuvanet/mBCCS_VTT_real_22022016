package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOrderChannelDetail;

public class SaleOrderChannelDetailAdapter extends BaseAdapter {

	private final String logTag = SaleOrderChannelDetailAdapter.class.getName();
	private final Activity mActivity;
	private List<SaleOrderChannelDetail> lstData;

	public SaleOrderChannelDetailAdapter(Activity mActivity,
			List<SaleOrderChannelDetail> lstStockModel) {
		this.mActivity = mActivity;
		if (lstStockModel != null && !lstStockModel.isEmpty()) {
			this.lstData = lstStockModel;
		}

	}

	public List<SaleOrderChannelDetail> getLstData() {
		return lstData;
	}

	public void setLstData(List<SaleOrderChannelDetail> lstData) {
		this.lstData = lstData;
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
		TextView tvStockModelCode;
		TextView tvQuantitySaling;
		View imgDelete;
		TextView tvPrice;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		final ViewHoder hoder;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.sale_stock_model_by_staff_row,
					arg2, false);
			hoder = new ViewHoder();
			hoder.tvStockModelCode = (TextView) mView
					.findViewById(R.id.tvStockModelCode);
			hoder.tvQuantitySaling = (TextView) mView
					.findViewById(R.id.tvQuantitySaling);
			hoder.imgDelete = mView.findViewById(R.id.imgDelete);
			hoder.tvPrice = (TextView) mView.findViewById(R.id.tvPrice);
			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final SaleOrderChannelDetail stockModel = lstData.get(position);
		if (stockModel != null) {
			hoder.tvStockModelCode.setText(stockModel.getStockModelCode() + "-"
					+ stockModel.getStockModelName());
			hoder.tvQuantitySaling.setText(StringUtils.formatMoney(stockModel
					.getQuantityOrder() + ""));
			hoder.imgDelete.setVisibility(View.GONE);
			hoder.tvPrice.setVisibility(View.GONE);
			hoder.tvQuantitySaling.setEnabled(false);
		}
		return mView;
	}
}
