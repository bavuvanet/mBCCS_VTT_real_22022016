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

public class AvailableSerialAdapter extends BaseAdapter {
	private final Context context;
	private ArrayList<Serial> lstData;
	private final ArrayList<String> lstTmp = new ArrayList<>();
	private final Boolean isStockCard;

    public AvailableSerialAdapter(Context context, ArrayList<Serial> lstData,
			Boolean isStockCard, Boolean isStockHandset) {
		this.context = context;
		this.lstData = lstData;
		if (lstData != null) {
			for (Serial serial : lstData) {
				lstTmp.addAll(SaleCommons.splitSerial(serial, isStockCard));
			}

		}
		this.isStockCard = isStockCard;
    }

	public ArrayList<Serial> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<Serial> lstData) {
		if (lstData != null && !lstData.isEmpty()) {
			for (Serial serial : lstData) {
				lstTmp.addAll(SaleCommons.splitSerial(serial, isStockCard));
			}
		}
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
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		ViewHoder hoder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater.inflate(R.layout.sale_available_serial_item, arg2,
					false);
			hoder = new ViewHoder();
			hoder.tvSerial = (TextView) mView.findViewById(R.id.tvSerial);

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
		}
		return mView;
	}

	/**
	 * filter list
	 * 
	 * @param charText
	 */
	public void filter(String charText, Boolean isStockHandset) {
		lstData.clear();
		if (charText.isEmpty()) {
			lstData.addAll(SaleCommons.getRangeSerial(lstTmp, isStockHandset));
		} else {
			ArrayList<String> lstSerial = new ArrayList<>();

			for (String string : lstTmp) {
				if (string.contains(charText)) {
					lstSerial.add(string);
				}
			}

			ArrayList<Serial> lstSerialArr = SaleCommons.getRangeSerial(lstTmp,
					isStockHandset);
			if (lstSerialArr != null) {
				for (String strSerial : lstSerial) {
					for (int i = 0; i < lstSerialArr.size(); i++) {
						Serial tmp = lstSerialArr.get(i);
						if (SaleCommons.checkInRange(tmp, strSerial,
								isStockHandset)) {
							lstData.add(tmp);
							lstSerialArr.remove(i);
							i--;

						}

					}
				}

			}
			// lstData.addAll(SaleCommons.getRangeSerial(lstSerial));
		}
		notifyDataSetChanged();
	}
}
