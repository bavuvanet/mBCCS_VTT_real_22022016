package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.sale.object.StockCardFullDTO;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CardInfoAdapter extends BaseAdapter {
	private ArrayList<StockCardFullDTO> listCardFullDTOs = new ArrayList<>();
	private Context context = null;

	public CardInfoAdapter(ArrayList<StockCardFullDTO> arrGetOrderObjects,
			Context context) {
		this.context = context;
		this.listCardFullDTOs = arrGetOrderObjects;
	}

	@Override
	public int getCount() {
		return listCardFullDTOs.size();
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
		TextView tvserial;
		TextView tvmodel;
		TextView tvnamestock;
		TextView tvisdn;
		TextView tvstatus;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final StockCardFullDTO stockCardFullDTO = this.listCardFullDTOs
				.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_card_info, parent, false);
			holder = new ViewHolder();
			holder.tvserial = (TextView) mView.findViewById(R.id.tvserial);
			holder.tvmodel = (TextView) mView.findViewById(R.id.tvmodel);
			holder.tvnamestock = (TextView) mView.findViewById(R.id.tvnamestock);
			holder.tvisdn = (TextView) mView.findViewById(R.id.tvisdn);
			holder.tvstatus = (TextView) mView.findViewById(R.id.tvstatus);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		
		holder.tvserial.setText(stockCardFullDTO.getSerial());
		holder.tvmodel.setText(stockCardFullDTO.getStockModelName());
		holder.tvnamestock.setText(stockCardFullDTO.getOwnerName());
		holder.tvisdn.setText(stockCardFullDTO.getIsdn());
		
		String[] listStringitem = context.getResources().getStringArray(
				R.array.status_items);
		if (!CommonActivity.isNullOrEmpty(stockCardFullDTO.getStatus())) {
			int pos = Integer.parseInt(stockCardFullDTO.getStatus());
			holder.tvstatus.setText(listStringitem[pos]);
		}
		
//		holder.tvstatus.setText(stockCardFullDTO.getStatus());
		
		return mView;
	}

}
