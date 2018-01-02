package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StockViewAdapter extends BaseAdapter {

	private final Activity mActivity;
	private ArrayList<StockModel> lstData;
	private final ArrayList<StockModel> lstBackUp = new ArrayList<>();

	public StockViewAdapter(Activity mActivity, ArrayList<StockModel> lstStockModel) {
		this.mActivity = mActivity;
		if (lstStockModel != null && !lstStockModel.isEmpty()) {
			this.lstData = lstStockModel;
			lstBackUp.addAll(lstStockModel);
		}
	}
	
	public void refreshDataStock () { 
		for (StockModel stockModel : lstData) {
			for (StockModel stockModel2 : lstBackUp) {
				if (stockModel.getStockModelId() == stockModel2.getStockModelId()) {
					stockModel2.setQuantitySaling(stockModel.getQuantitySaling());
				}
			}
		}  
		notifyDataSetChanged();
	}
	
	public void updateNumberReturntheGood (Long numberSerial,StockModel stockModel) {
		for (StockModel curentStockModel : lstData) {
			if (curentStockModel.getStockModelId() == stockModel.getStockModelId()) {
				stockModel.setQuantitySaling(numberSerial);
				curentStockModel.setQuantitySaling(numberSerial);
			}  
		}  
	} 
	
	public ArrayList<StockModel> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<StockModel> lstData) {
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
		// TextView tvStockModelName;
		TextView tvQuantityIssue;
//		TextView tvQuantitySaling;
		TextView tvStateTheGood;
		TextView tvQuantity;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		final ViewHoder hoder;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.sale_stock_model_view_stock, arg2, false);
			hoder = new ViewHoder();
			hoder.tvStockModelCode = (TextView) mView.findViewById(R.id.tvStockModelCode);
			// hoder.tvStockModelName = (TextView) mView
			// .findViewById(R.id.tvStockModelName);
			hoder.tvQuantityIssue = (TextView) mView.findViewById(R.id.tvQuantityIssue);
//			hoder.tvQuantitySaling = (TextView) mView.findViewById(R.id.tvQuantitySaling); 
			hoder.tvStateTheGood  = (TextView) mView.findViewById(R.id.tvStateTheGood);
			hoder.tvQuantity = (TextView) mView.findViewById(R.id.tvQuantity);
			
			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final StockModel stockModel = lstData.get(position);

		if (stockModel != null) {

//			hoder.tvQuantitySaling.setTag(position);
			hoder.tvStockModelCode.setText(stockModel.getStockModelCode() + "-" + stockModel.getStockModelName());
			hoder.tvQuantityIssue.setText(StringUtils.formatMoney(stockModel.getQuantityIssue() + ""));
			hoder.tvStateTheGood.setText(stockModel.getStateName());
			hoder.tvQuantity.setText(StringUtils.formatMoney(stockModel.getQuantity() + ""));
		}

		return mView;
	}

	/**
	 * Loc lay danh sach hang hoa da chon
	 * 
	 * @param isViewStockModel
	 */
	public ArrayList<StockModel> filter(Boolean isViewStockModel) {
		lstData.clear();
		if (isViewStockModel) {
			if (lstBackUp != null && !lstBackUp.isEmpty()) {
				for (StockModel item : lstBackUp) {
					if (item.getQuantitySaling() > 0) {
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
	public void SearchInput(String chartext) {
		chartext = chartext.toLowerCase();
		lstData.clear();
		if (chartext.length() == 0) {
			Log.d("size arraylist", "" + lstBackUp.size());
			lstData.addAll(lstBackUp);
		} else {
			for (StockModel stockModel : lstBackUp) {
				if (CommonActivity.convertUnicode2NosignString(stockModel.getStockModelName()).toLowerCase()
						.contains(CommonActivity.convertUnicode2NosignString(chartext))
						|| CommonActivity.convertUnicode2NosignString(stockModel.getStockModelCode()).toLowerCase()
								.contains(CommonActivity.convertUnicode2NosignString(chartext))) {
					lstData.add(stockModel);
				}

			}
		}
		notifyDataSetChanged();
	}

}
