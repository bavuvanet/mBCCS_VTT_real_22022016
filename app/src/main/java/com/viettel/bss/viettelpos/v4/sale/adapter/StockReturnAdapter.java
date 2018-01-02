package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseSerialReturnTheGood;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSaling;
import com.viettel.bss.viettelpos.v4.sale.object.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransSerialDTO;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StockReturnAdapter extends BaseAdapter {

	private final String logTag = StockModelAdapter.class.getName();
	private final Activity mActivity;
	private ArrayList<ProductOfferingDTO> lstData;
	private final ArrayList<ProductOfferingDTO> lstBackUp = new ArrayList<>();

	private final OncancelStockModel cancelStockModel;
	private final OnChangeQuantity changeQuantity;
	private final Fragment targetFragment;
	private Boolean isBankplus = false;

	
	public interface OncancelStockModel {
		void onCancelStockModelListener(ProductOfferingDTO stockModel);
	}

	public interface OnChangeQuantity {
		void onChangeQuantity(ProductOfferingDTO stockModel);
	}
	
	public StockReturnAdapter(Activity mActivity, ArrayList<ProductOfferingDTO> lstStockModel,
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

	public void refreshDataStock() {
		for (ProductOfferingDTO stockModel : lstData) {
			for (ProductOfferingDTO stockModel2 : lstBackUp) {
				if (stockModel.getProductOfferingId().compareTo(stockModel2.getProductOfferingId()) == 0) {
					stockModel2.setQuantitySaling(stockModel.getQuantitySaling());
				}
			}
		}
		notifyDataSetChanged();
	}

	public void updateNumberReturntheGood(Long numberSerial, ProductOfferingDTO stockModel) {
		for (ProductOfferingDTO curentStockModel : lstData) {
			if (curentStockModel.getProductOfferingId().compareTo(stockModel.getProductOfferingId()) == 0) {
				stockModel.setQuantitySaling(numberSerial);
				curentStockModel.setQuantitySaling(numberSerial);
			}
		}
	}

	public ArrayList<ProductOfferingDTO> getLstData() {
		return lstData;
	}
	public ArrayList<StockModel> getLstData(String test) {
		return new ArrayList<>();
	}
	public void setLstData(ArrayList<ProductOfferingDTO> lstData) {
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
			hoder.tvStateTheGood = (TextView) mView.findViewById(R.id.tvStateTheGood);

			hoder.imgDelete = mView.findViewById(R.id.imgDelete);
			hoder.lnStockModel = mView.findViewById(R.id.lnStockModel);
			hoder.lnQuantity = mView.findViewById(R.id.lnQuantity);

			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final ProductOfferingDTO stockModel = lstData.get(position);

		if (stockModel != null) {

			hoder.tvQuantitySaling.setTag(position);
			hoder.tvStockModelCode.setText(stockModel.getCode() + "-" + stockModel.getName());
			hoder.tvQuantityIssue.setText(StringUtils.formatMoney(stockModel.getQuantity() + ""));
			hoder.tvStateTheGood.setText(stockModel.getStateIdName());

			hoder.lnStockModel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (stockModel.getCheckSerial().compareTo(1L) == 0 && stockModel.getQuantitySaling() > 0) {
						Bundle bundle = new Bundle();
						bundle.putSerializable("stockModel", stockModel);
						ArrayList<StockTransSerialDTO> listSerialPut = new ArrayList<>();
						listSerialPut.addAll(stockModel.getmListSerialSelection());
						bundle.putSerializable("listSerial", listSerialPut);
						FragmentChooseSerialReturnTheGood fragment = new FragmentChooseSerialReturnTheGood();
						fragment.setTargetFragment(targetFragment, FragmentSaleSaling.REQUEST_CHOOSE_SERIAL);
						fragment.setArguments(bundle);
						ReplaceFragment.replaceFragment(mActivity, fragment, false);
					}
				}
			});

			if (stockModel.getQuantitySaling().intValue() > 0) {
				hoder.tvQuantitySaling.setText(StringUtils.formatMoney(stockModel.getQuantitySaling() + ""));
				if (!isBankplus) {
					hoder.imgDelete.setVisibility(View.VISIBLE);
					hoder.imgDelete.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View arg0) {
							cancelStockModel.onCancelStockModelListener(stockModel);
							stockModel.setQuantitySaling(0L);
							for (ProductOfferingDTO stockModelBackUp : lstBackUp) {
								if (stockModelBackUp.getCode().equals(stockModel.getCode())) {
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

			if (stockModel.getQuantity().compareTo(0L) == 0) {
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
	public ArrayList<ProductOfferingDTO> filter(Boolean isViewStockModel) {
		lstData.clear();
		if (isViewStockModel) {
			if (lstBackUp != null && !lstBackUp.isEmpty()) {
				for (ProductOfferingDTO item : lstBackUp) {
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
			for (ProductOfferingDTO stockModel : lstBackUp) {
				if (CommonActivity.convertUnicode2NosignString(stockModel.getName()).toLowerCase()
						.contains(CommonActivity.convertUnicode2NosignString(chartext))
						|| CommonActivity.convertUnicode2NosignString(stockModel.getCode()).toLowerCase()
								.contains(CommonActivity.convertUnicode2NosignString(chartext))) {
					lstData.add(stockModel);
				}

			}
		}
		notifyDataSetChanged();
	}

}
