package com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PromotionUnitVas;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GetPromotionUnitVasAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<PromotionUnitVas> arrPromotionTypeBeans = new ArrayList<PromotionUnitVas>();
    private ArrayList<PromotionUnitVas> arraylist = new ArrayList<PromotionUnitVas>();

	public GetPromotionUnitVasAdapter(ArrayList<PromotionUnitVas> arr, Context context) {
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
		TextView txtpstn;
		Button btninfo;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connectionmobile_item_pakage, arg2, false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			holder.btninfo = (Button) mView.findViewById(R.id.btninfo);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		final PromotionUnitVas promotionTypeBeans = arrPromotionTypeBeans.get(arg0);

		holder.btninfo.setVisibility(View.GONE);

		if (promotionTypeBeans.getPromotionCodeUnitName() != null && !promotionTypeBeans.getPromotionCodeUnitName().isEmpty()) {
			holder.txtpstn.setText(promotionTypeBeans.getPromotionCodeUnit() + "-"+promotionTypeBeans.getPromotionCodeUnitName());
		}

		return mView;
	}

	public ArrayList<PromotionUnitVas> SearchInput(String chartext) {
		chartext = chartext.toLowerCase();
		arrPromotionTypeBeans.clear();
		if (chartext.isEmpty()) {
			Log.d("size arraylist", "" + arraylist.size());
			arrPromotionTypeBeans.addAll(arraylist);
		} else {
			for (PromotionUnitVas promotionTypeBeans : arraylist) {
				if (CommonActivity.convertUnicode2Nosign(promotionTypeBeans.getPromotionCodeUnit() + "-"+promotionTypeBeans.getPromotionCodeUnitName()).toLowerCase()
						.contains(CommonActivity.convertUnicode2Nosign(chartext))) {
					arrPromotionTypeBeans.add(promotionTypeBeans);
				}
			}
		}

		notifyDataSetChanged();
		return arrPromotionTypeBeans;
	}

}
