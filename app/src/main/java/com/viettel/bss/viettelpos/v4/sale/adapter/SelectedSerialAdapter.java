package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;

public class SelectedSerialAdapter extends BaseAdapter {

	private final Context context;
	private ArrayList<Serial> lstData;
	private final ArrayList<String> lstSerial;
	private final OnCancelSerialListener cancelSerialListener;

	public interface OnCancelSerialListener {
		void onCancelSerial(Serial serial);
	}

	public SelectedSerialAdapter(Context context, ArrayList<String> lstSerial,
			OnCancelSerialListener cancelSerialListener, Boolean isStockHandset) {
		this.context = context;
		this.lstSerial = lstSerial;
		this.cancelSerialListener = cancelSerialListener;
		lstData = SaleCommons.getRangeSerial(lstSerial, isStockHandset);
	}

	public ArrayList<Serial> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<Serial> lstData) {
		this.lstData = lstData;
	}

	@Override
	public int getCount() {
		if (lstData != null) {
			return lstData.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		if (lstData != null && !lstData.isEmpty()) {
			return lstData.get(arg0);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tvSerial;
		View imgCancel;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		ViewHoder hoder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater.inflate(R.layout.sale_selected_serial_item, arg2,
					false);
			hoder = new ViewHoder();
			hoder.tvSerial = (TextView) mView.findViewById(R.id.tvSerial);
			hoder.imgCancel = mView.findViewById(R.id.imgDelete);
			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final Serial item = lstData.get(position);
		if (item != null) {
			if (item.getFromSerial().equals(item.getToSerial())) {
				hoder.tvSerial.setText(item.getFromSerial());
			} else {
				hoder.tvSerial.setText(item.getFromSerial() + " - "
						+ item.getToSerial());
			}
			hoder.imgCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					cancelSerialListener.onCancelSerial(item);
				}
			});
		}
		return mView;
	}
}
