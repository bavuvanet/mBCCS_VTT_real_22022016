package com.viettel.bss.viettelpos.v4.sale.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseSerial;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSaling;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

import java.util.ArrayList;

public class StockModelAdapter extends BaseAdapter {

	private final String logTag = StockModelAdapter.class.getName();
	private final Context context;
	private final ArrayList<StockModel> lstData = new ArrayList<>();
	ArrayList<StockModel> lstTmp = new ArrayList<>();
	private final OncancelStockModel cancelStockModel;
	private final OnChangeQuantity changeQuantity;
	private final Fragment targetFragment;
	private Boolean isBankplus = false;

	public interface OncancelStockModel {
		void onCancelStockModelListener(StockModel stockModel);
	}

	public interface OnChangeQuantity {
		void onChangeQuantity(StockModel stockModel);
	}

	public StockModelAdapter(Context context,
			ArrayList<StockModel> lstStockModel,
			OncancelStockModel cancelStockModel,
                                     OnChangeQuantity changeQuantity, Fragment targetFragment,
			Boolean isBankplus) {
		this.context = context;
		this.isBankplus = isBankplus;
		if (lstStockModel != null && !lstStockModel.isEmpty()) {
			this.lstData.addAll(lstStockModel);
		}

		this.cancelStockModel = cancelStockModel;
		this.changeQuantity = changeQuantity;
		this.targetFragment = targetFragment;
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
		// TextView tvStockModelName;
		TextView tvQuantityIssue;
		TextView tvQuantitySaling;
		TextView tvSerialWarring;
		View imgDelete;
		View lnStockModel;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		final ViewHoder hoder;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater
					.inflate(R.layout.sale_stock_model_row, arg2, false);
			hoder = new ViewHoder();
			hoder.tvStockModelCode = (TextView) mView
					.findViewById(R.id.tvStockModelCode);
			// hoder.tvStockModelName = (TextView) mView
			// .findViewById(R.id.tvStockModelName);
			hoder.tvQuantityIssue = (TextView) mView
					.findViewById(R.id.tvQuantityIssue);
			hoder.tvQuantitySaling = (TextView) mView
					.findViewById(R.id.tvQuantitySaling);
			hoder.tvSerialWarring = (TextView) mView
					.findViewById(R.id.tvSerialWarring);
			hoder.imgDelete = mView.findViewById(R.id.imgDelete);
			hoder.lnStockModel = mView.findViewById(R.id.lnStockModel);

			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final StockModel stockModel = lstData.get(position);

		if (stockModel != null) {
			hoder.tvQuantitySaling.setTag(position);
			hoder.tvStockModelCode.setText(stockModel.getStockModelCode() + "-"
					+ stockModel.getStockModelName());
			if (stockModel.getQuantityIssue() != null) {
				hoder.tvQuantityIssue.setText(StringUtils
						.formatMoney(stockModel.getQuantityIssue() + ""));
			} else {
				hoder.tvQuantityIssue.setText("");
			}

			hoder.lnStockModel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (stockModel.getQuantityIssue() == null) {
						return;
					}
					if (stockModel.isCheckSerial()
							&& stockModel.getQuantitySaling() > 0) {
						Bundle bundle = new Bundle();
						bundle.putSerializable("stockModel", stockModel);
						FragmentChooseSerial fragment = new FragmentChooseSerial();
						fragment.setTargetFragment(targetFragment,
								FragmentSaleSaling.REQUEST_CHOOSE_SERIAL);
						fragment.setArguments(bundle);
						ReplaceFragment.replaceFragment((Activity) context,
								fragment, false);
					}
				}
			});
			if (stockModel.getQuantitySaling() > 0) {
				hoder.tvQuantitySaling.setText(StringUtils
						.formatMoney(stockModel.getQuantitySaling() + ""));
				if (!isBankplus) {
					hoder.imgDelete.setVisibility(View.VISIBLE);
					hoder.imgDelete
							.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View arg0) {
									cancelStockModel
											.onCancelStockModelListener(stockModel);
								}
							});

				} else {
					hoder.tvQuantitySaling.setEnabled(false);
					hoder.imgDelete.setVisibility(View.INVISIBLE);
				}

			} else {
				hoder.tvQuantitySaling.setText("");
				hoder.imgDelete.setVisibility(View.INVISIBLE);
			}
			if (stockModel.getQuantitySaling() != null) {
				if (!isBankplus) {
					hoder.tvQuantitySaling.setEnabled(true);
					hoder.tvQuantitySaling
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View arg0) {
									changeQuantity.onChangeQuantity(stockModel);
								}
							});
				} else {
					hoder.tvQuantitySaling.setEnabled(false);
				}
				if (stockModel.isFullySerial()) {
					hoder.tvSerialWarring.setVisibility(View.GONE);
				} else {
					hoder.tvSerialWarring.setVisibility(View.VISIBLE);
				}
			}

		}
		return mView;
	}

	/**
	 * Loc lay danh sach hang hoa da chon
	 * 
	 * @param isViewStockModel
	 */
	public void filter(Boolean isViewStockModel, ArrayList<StockModel> lstItem) {
		lstData.clear();
		if (isViewStockModel) {
			if (lstItem != null && !lstItem.isEmpty()) {
				for (StockModel item : lstItem) {
					if (item.getQuantitySaling() > 0) {
						lstData.add(item);
					}
				}
			}
		} else {
			lstData.addAll(lstItem);
		}
		notifyDataSetChanged();

	}
}
