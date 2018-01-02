package com.viettel.bss.viettelpos.v4.sale.adapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseSerial;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSaling;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelAdapter.OnChangeQuantity;
import com.viettel.bss.viettelpos.v4.sale.adapter.StockModelAdapter.OncancelStockModel;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

import java.util.ArrayList;
public class SaleToCustomerAdapter extends BaseAdapter {
	private final String logTag = StockModelAdapter.class.getName();
	private final Activity mActivity;
	private ArrayList<StockModel> lstData;
	private final ArrayList<StockModel> lstBackUp = new ArrayList<>();

	private final OncancelStockModel cancelStockModel;
	private final OnChangeQuantity changeQuantity;
	private final Fragment targetFragment;
	private Boolean isBankplus = false;

	public SaleToCustomerAdapter(Activity mActivity, ArrayList<StockModel> lstStockModel,
			OncancelStockModel cancelStockModel, OnChangeQuantity changeQuantity, Fragment targetFragment,
			Boolean isBankplus) {
		this.mActivity = mActivity;
		this.isBankplus = isBankplus;
		if (lstStockModel != null && !lstStockModel.isEmpty()) {
			this.lstData = lstStockModel;
			lstBackUp.addAll(lstStockModel);
		}

		this.cancelStockModel = cancelStockModel;
		this.changeQuantity = changeQuantity;
		this.targetFragment = targetFragment;
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
		TextView tvQuantitySaling;
		TextView tvStateTheGood;
		View imgDelete;
		View lnStockModel;
		View lnQuantity;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		final ViewHoder hoder;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.sale_stock_model_return_row, arg2, false);
			hoder = new ViewHoder();
			hoder.tvStockModelCode = (TextView) mView.findViewById(R.id.tvStockModelCode);
			// hoder.tvStockModelName = (TextView) mView
			// .findViewById(R.id.tvStockModelName);
			hoder.tvQuantityIssue = (TextView) mView.findViewById(R.id.tvQuantityIssue);
			hoder.tvQuantitySaling = (TextView) mView.findViewById(R.id.tvQuantitySaling); 
			hoder.tvStateTheGood  = (TextView) mView.findViewById(R.id.tvStateTheGood);
			
			hoder.imgDelete = mView.findViewById(R.id.imgDelete);
			hoder.lnStockModel = mView.findViewById(R.id.lnStockModel);
			hoder.lnQuantity = mView.findViewById(R.id.lnQuantity);

			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final StockModel stockModel = lstData.get(position);

		if (stockModel != null) {

			hoder.tvQuantitySaling.setTag(position);
			hoder.tvStockModelCode.setText(stockModel.getStockModelCode() + "-" + stockModel.getStockModelName());
			hoder.tvQuantityIssue.setText(StringUtils.formatMoney(stockModel.getQuantityIssue() + ""));
			hoder.tvStateTheGood.setText(stockModel.getStateName());

			hoder.lnStockModel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (stockModel.isCheckSerial() && stockModel.getQuantitySaling() > 0) {
						Bundle bundle = new Bundle();
						bundle.putSerializable("stockModel", stockModel);
						FragmentChooseSerial fragment = new FragmentChooseSerial();
						fragment.setTargetFragment(targetFragment, FragmentSaleSaling.REQUEST_CHOOSE_SERIAL);
						fragment.setArguments(bundle);
						ReplaceFragment.replaceFragment(mActivity, fragment, false);
					}
				}
			});

			if (stockModel.getQuantitySaling() > 0) {
				hoder.tvQuantitySaling.setText(StringUtils.formatMoney(stockModel.getQuantitySaling() + ""));
				if (!isBankplus) {
					hoder.imgDelete.setVisibility(View.VISIBLE);
					hoder.imgDelete.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View arg0) { 
							cancelStockModel.onCancelStockModelListener(stockModel);
							stockModel.setQuantitySaling(0L);
							for (StockModel stockModelBackUp : lstBackUp) {
								if (stockModelBackUp.getStockModelCode() == stockModel.getStockModelCode()) {
									stockModelBackUp.setQuantitySaling(0L);
								}
							}
							notifyDataSetChanged();
						}
					});

				} else {
					hoder.tvQuantitySaling.setEnabled(false);
					hoder.imgDelete.setVisibility(View.INVISIBLE);
				}

			} else {
				hoder.tvQuantitySaling.setText(mActivity.getResources().getString(R.string.enterNumberToolshop));
				hoder.imgDelete.setVisibility(View.INVISIBLE);
			}

			if (!isBankplus) {
				hoder.tvQuantitySaling.setEnabled(true);
				hoder.tvQuantitySaling.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Log.d("Log", "click Text in stockModel: " + stockModel);
						changeQuantity.onChangeQuantity(stockModel);
					}
				});
			} else {
				hoder.tvQuantitySaling.setEnabled(false);
			}
			
			if (stockModel.getQuantityIssue() == 0) {
				hoder.tvQuantityIssue.setVisibility(View.GONE);
			} else {
				hoder.tvQuantityIssue.setVisibility(View.VISIBLE);
			} 
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