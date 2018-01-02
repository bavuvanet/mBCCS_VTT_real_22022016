package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PromotionUnitVas;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;

import java.util.ArrayList;

public class GetPromotionTypeDTOAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<PromotionTypeBeans> arrPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();;
	private ArrayList<PromotionTypeBeans> arraylist = new ArrayList<PromotionTypeBeans>();

	public GetPromotionTypeDTOAdapter(ArrayList<PromotionTypeBeans> arr, Context context) {
		if (arr != null) {
			this.arraylist.addAll(arr);
			this.arrPromotionTypeBeans.addAll(arr);
		}

		mContext = context;
	}

	@Override
	public int getCount() {
		return arrPromotionTypeBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrPromotionTypeBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView tvname;
		TextView tvchargmonth;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_promotion, arg2, false);
			holder = new ViewHolder();
			holder.tvname = (TextView) mView.findViewById(R.id.tvname);
			holder.tvchargmonth = (TextView) mView.findViewById(R.id.tvchargmonth);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		final PromotionTypeBeans promotionTypeBeans = arrPromotionTypeBeans.get(arg0);
		if(!CommonActivity.isNullOrEmpty(promotionTypeBeans)){
			holder.tvname.setText(promotionTypeBeans.getCodeName());
			holder.tvchargmonth.setText(promotionTypeBeans.getChargeMonthlyName());
		}
		return mView;
	}



}
