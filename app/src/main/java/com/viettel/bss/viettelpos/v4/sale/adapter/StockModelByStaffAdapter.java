package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.StockModelByStaff;

public class StockModelByStaffAdapter extends BaseAdapter {

	public static final int SHOW_TYPE_ORDER = 1;
	public static final int SHOW_TYPE_CHOOSE_GOODS = 2;
	private final String logTag = StockModelByStaffAdapter.class.getName();
	private final Activity mActivity;
	private ArrayList<StockModelByStaff> lstData;
	private final ArrayList<StockModelByStaff> lstBackUp = new ArrayList<>();

	private final OncancelStockModel cancelStockModel;
	private final OnChangeQuantity changeQuantity;
	private final int showType;

	public interface OncancelStockModel {
		void onCancelStockModelListener(StockModelByStaff stockModel);
	}

	public interface OnChangeQuantity {
		void onChangeQuantity(StockModelByStaff stockModel);
	}

	public StockModelByStaffAdapter(Activity mActivity,
			ArrayList<StockModelByStaff> lstStockModel, int showType,
			OncancelStockModel cancelStockModel, OnChangeQuantity changeQuantity) {
		this.showType = showType;
		this.mActivity = mActivity;
		if (lstStockModel != null && !lstStockModel.isEmpty()) {
			this.lstData = lstStockModel;
			lstBackUp.addAll(lstStockModel);
		}

		this.cancelStockModel = cancelStockModel;
		this.changeQuantity = changeQuantity;
	}

	public void refreshDataStock() {
		for (StockModelByStaff stockModel : lstData) {
			for (StockModelByStaff stockModel2 : lstBackUp) {
				if (stockModel.getStockModelId() == stockModel2
						.getStockModelId()) {
					// stockModel2.setQuantitySaling(stockModel
					// .getQuantitySaling());
				}
			}
		}
		notifyDataSetChanged();
	}

	public void updateNumberReturntheGood(Long numberSerial,
			StockModelByStaff stockModel) {
		for (StockModelByStaff curentStockModel : lstData) {
			if (curentStockModel.getStockModelId() == stockModel
					.getStockModelId()) {
				// stockModel.setQuantitySaling(numberSerial);
				// curentStockModel.setQuantitySaling(numberSerial);
			}
		}
	}

	public ArrayList<StockModelByStaff> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<StockModelByStaff> lstData) {
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
		View lnStockModel;
		View lnRoot;
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
			hoder.lnStockModel = mView.findViewById(R.id.lnStockModel);
			hoder.lnRoot = mView.findViewById(R.id.lnRoot);
			hoder.tvPrice = (TextView) mView.findViewById(R.id.tvPrice);

			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final StockModelByStaff stockModel = lstData.get(position);

		if (stockModel != null) {
			hoder.tvStockModelCode.setText(stockModel.getStockModelCode() + "-"
					+ stockModel.getName());

			if (stockModel.getQuantity() != null
					&& stockModel.getQuantity() > 0) {
				hoder.tvQuantitySaling.setText(StringUtils
						.formatMoney(stockModel.getQuantity() + ""));

			} else {
				hoder.tvQuantitySaling.setText(mActivity
						.getText(R.string.input_number_return));

			}

			if (showType == SHOW_TYPE_ORDER) {
				hoder.imgDelete.setVisibility(View.VISIBLE);
				hoder.imgDelete.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						cancelStockModel.onCancelStockModelListener(stockModel);
					}
				});
			} else {
				hoder.imgDelete.setVisibility(View.GONE);

			}
			if (stockModel.getPrice() != null) {
				hoder.tvPrice.setText(mActivity.getString(R.string.price,
						StringUtils.formatMoney(stockModel.getPrice()
								.toString())));
			} else {
				hoder.tvPrice.setText(mActivity
						.getString(R.string.price, "N/A"));
			}

			hoder.tvQuantitySaling.setEnabled(false);
			hoder.lnRoot.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Log.d("Log", "click Text in stockModel: " + stockModel);
					if (changeQuantity != null) {
						changeQuantity.onChangeQuantity(stockModel);
					}
				}
			});

		}

		return mView;
	}

	/**
	 * Loc lay danh sach hang hoa da chon
	 * 
	 * @param isViewStockModel
	 */
	public ArrayList<StockModelByStaff> filter(Boolean isViewStockModel) {
		lstData.clear();
		if (isViewStockModel) {
			if (lstBackUp != null && !lstBackUp.isEmpty()) {
				for (StockModelByStaff item : lstBackUp) {
					if (item.getQuantity() != null && item.getQuantity() > 0) {
						lstData.add(item);
					}
				}
			}
		} else {
			lstData.addAll(lstBackUp);
		}
		notifyDataSetChanged();
		return lstData;
	}

	// fill ter object local
	public void searchInput(String chartext) {
		chartext = chartext.toLowerCase();
		lstData.clear();
		if (chartext.length() == 0) {
			Log.d("size arraylist", "" + lstBackUp.size());
			lstData.addAll(lstBackUp);
		} else {
			for (StockModelByStaff stockModel : lstBackUp) {
				if (CommonActivity
						.convertUnicode2NosignString(stockModel.getName())
						.toLowerCase()
						.contains(
								CommonActivity
										.convertUnicode2NosignString(chartext))
						|| CommonActivity
								.convertUnicode2NosignString(
										stockModel.getStockModelCode())
								.toLowerCase()
								.contains(
										CommonActivity
												.convertUnicode2NosignString(chartext))) {
					lstData.add(stockModel);
				}

			}
		}
		notifyDataSetChanged();
	}
}
