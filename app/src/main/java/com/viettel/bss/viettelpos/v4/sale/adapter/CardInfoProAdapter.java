package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.sale.object.CardInfoProvisioningDTO;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CardInfoProAdapter extends BaseAdapter {
	private ArrayList<CardInfoProvisioningDTO> lisCardInfoProvisioningDTOs = new ArrayList<>();
	private Context context = null;

	public CardInfoProAdapter(
			ArrayList<CardInfoProvisioningDTO> arrGetOrderObjects,
			Context context) {
		this.context = context;
		this.lisCardInfoProvisioningDTOs = arrGetOrderObjects;
	}

	@Override
	public int getCount() {

		if (lisCardInfoProvisioningDTOs == null) {
			return 0;
		} else {
			return lisCardInfoProvisioningDTOs.size();
		}

	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView tvstatus;
		TextView tvprice;
		TextView tvdateexpired;
		TextView tvdateUsed;
		TextView tvisdn;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final CardInfoProvisioningDTO cardInfoProvisioningDTO = this.lisCardInfoProvisioningDTOs
				.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater
					.inflate(R.layout.item_card_info_pro, parent, false);
			holder = new ViewHolder();
			holder.tvstatus = (TextView) mView.findViewById(R.id.tvstatus);
			holder.tvprice = (TextView) mView.findViewById(R.id.tvprice);
			holder.tvdateexpired = (TextView) mView
					.findViewById(R.id.tvdateexpired);
			holder.tvdateUsed = (TextView) mView.findViewById(R.id.tvdateUsed);
			holder.tvisdn = (TextView) mView.findViewById(R.id.tvisdn);
			mView.setTag(holder);

		} else {
			holder = (ViewHolder) mView.getTag();
		}

		String[] listStringitem = context.getResources().getStringArray(
				R.array.status_items);
		if (!CommonActivity.isNullOrEmpty(cardInfoProvisioningDTO
				.getCardSuspended())) {
			int pos = Integer.parseInt(cardInfoProvisioningDTO
					.getCardSuspended());
			holder.tvstatus.setText(listStringitem[pos]);
		}

		holder.tvprice.setText(cardInfoProvisioningDTO.getCardValue());
		holder.tvdateexpired.setText(cardInfoProvisioningDTO.getCardExpired());
		holder.tvdateUsed.setText(cardInfoProvisioningDTO.getDateUsed());
		if(!CommonActivity.isNullOrEmpty(cardInfoProvisioningDTO.getIsdn())){
			holder.tvisdn.setText(CommonActivity.formatIsdn(cardInfoProvisioningDTO.getIsdn()));
		}else{
			holder.tvisdn.setText("");
		}
		
		return mView;
	}

}
