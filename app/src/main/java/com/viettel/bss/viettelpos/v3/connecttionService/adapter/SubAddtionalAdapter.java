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
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;

public class SubAddtionalAdapter extends BaseAdapter {
	private List<SubscriberDTO> lstData;
	Context mContext;

	public SubAddtionalAdapter(ArrayList<SubscriberDTO> lstData, Context context) {
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
		TextView tv1;
		TextView tv2;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		SubscriberDTO item = lstData.get(arg0);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.list_item_two_line, arg2, false);
			holder = new ViewHolder();
			holder.tv1 = (TextView) mView.findViewById(R.id.tv1);
			holder.tv2 = (TextView) mView.findViewById(R.id.tv2);

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}

		holder.tv1.setText(item.getServiceTypeName());
		if (CommonActivity.isNullOrEmpty(item.getIsdn())) {
			holder.tv2.setVisibility(View.GONE);
		} else {

			holder.tv2.setText(item.getIsdn() + " - " + item.getIsdn());
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
