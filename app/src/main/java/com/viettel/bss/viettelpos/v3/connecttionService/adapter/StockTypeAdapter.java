package com.viettel.bss.viettelpos.v3.connecttionService.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v3.connecttionService.model.StockModelConnectSub;

public class StockTypeAdapter extends BaseAdapter {
	private List<ProductOfferTypeDTO> lstData;
	Context mContext;

	public StockTypeAdapter(ArrayList<ProductOfferTypeDTO> lstData,
			Context context) {
		this.lstData = lstData;
		mContext = context;

	}

	@Override
	public int getCount() {
		return lstData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	static class ViewHolder {
		TextView tvStockType;
		TextView tvSelected;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ProductOfferTypeDTO item = lstData.get(arg0);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.list_item_two_line, arg2, false);
			holder = new ViewHolder();
			holder.tvStockType = (TextView) mView.findViewById(R.id.tv1);
			holder.tvSelected = (TextView) mView.findViewById(R.id.tv2);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		holder.tvStockType.setText(item.getName());
		if (CommonActivity.isNullOrEmpty(item.getStockModel())) {
			holder.tvSelected.setVisibility(View.GONE);
		} else {
			holder.tvSelected.setVisibility(View.VISIBLE);
			StockModelConnectSub sub = item.getStockModel();
			String tmp = sub.getStockModelCode() + " - "
					+ sub.getStockModelName();
			holder.tvSelected.setText(tmp);
		}

		return mView;
		// return null;
	}

	public interface OnChangeSpinerProduct {
		// public void OnChangeSpinerSelect(ObjThongTinHH objThongTinHH);

		public void OnChangeSpinerSelectProduct(
				ProductOfferTypeDTO productOfferTypeDTO,
				ProductOfferingDTO objThongTinHH, int arg2);

	}
}
