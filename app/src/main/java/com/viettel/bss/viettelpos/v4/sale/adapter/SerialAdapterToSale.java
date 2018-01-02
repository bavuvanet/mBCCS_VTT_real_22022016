package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SerialAdapterToSale extends BaseAdapter {

	private final Activity mActivity;
	// ArrayList<Serial> lstData;

	private ArrayList<Serial> lstData;
	private final ArrayList<Serial> lstBackUp = new ArrayList<>();

	private final OnCancelSerialSaleReturnListener cancelSerialListener;
	private final boolean isHidenCancelButton;

	public interface OnCancelSerialSaleReturnListener {
		void onCancelSerial(Serial serial);
	}

	public SerialAdapterToSale(Activity mActivity, ArrayList<Serial> lstSerial,
			OnCancelSerialSaleReturnListener cancelSerialListener, boolean isHidenCancelButton) {
		this.mActivity = mActivity;
		this.cancelSerialListener = cancelSerialListener;
		this.isHidenCancelButton = isHidenCancelButton;
		if (lstSerial != null && !lstSerial.isEmpty()) {
			this.lstData = lstSerial;
			lstBackUp.addAll(lstSerial);
		}
	}
	
	
	

	public ArrayList<Serial> getLstData() {
		return lstData;
	}




	public void setLstData(ArrayList<Serial> lstData) {
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
		TextView tvSerial;
		View imgCancel;
		View viewLine;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		ViewHoder hoder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.sale_selected_serial_item, arg2, false);
			hoder = new ViewHoder();
			hoder.tvSerial = (TextView) mView.findViewById(R.id.tvSerial);
			hoder.imgCancel = mView.findViewById(R.id.imgDelete);
			hoder.viewLine = mView.findViewById(R.id.lineView);
			if (isHidenCancelButton) {
				hoder.imgCancel.setVisibility(View.GONE);
				hoder.viewLine.setVisibility(View.GONE);
			} else {
				hoder.imgCancel.setVisibility(View.VISIBLE);
				hoder.viewLine.setVisibility(View.VISIBLE);
			}
			
			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final Serial item = lstData.get(position);
		if (item != null) {
			if (item.getFromSerial().equals(item.getToSerial())) {
				hoder.tvSerial.setText(item.getFromSerial());
			} else {
				hoder.tvSerial.setText(item.getFromSerial() + " - " + item.getToSerial());
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

	// fill ter object local
	public void SearchInput(String chartext,StockModel stockModel2) { 
		try {
			chartext = chartext.toLowerCase();
			if (lstData == null) {
				lstData = new ArrayList<>();
			} else {
				lstData.clear();
			} 
			notifyDataSetChanged();
			if (chartext.length() == 0) {
				Log.d("size arraylist", "" + lstBackUp.size());
				lstData.addAll(lstBackUp);
			} else {
				for (Serial serial : lstBackUp) { 
					Log.d("Log","from serial search" + serial.getFromSerial() + "to search serial:" + serial.getToSerial()); 
					if (serial.getFromSerial().equals(serial.getToSerial())) {
						if (serial.getFromSerial().toLowerCase().contains(chartext)) {
							lstData.add(serial);
						} 
					} else {  
						for (long i = 0L; i < serial.getNumber(); i++) {  
							long fromSerial = Long.parseLong(serial.getFromSerial());
							fromSerial += i; 
							String strNumberSerial = Long.toString(fromSerial); 
							if (stockModel2.getStockTypeId() == 6) { // serial co so 0 dang truoc nen goi ham nomal de them 
								strNumberSerial = SaleCommons.normalSerial(strNumberSerial); 
							}  
							
							if (strNumberSerial.contains(chartext)) {
								lstData.add(serial); 
								break;
							}
						} 
					}
				}
			}
			notifyDataSetChanged();
			
		} catch (Exception e) {
			Log.d(Constant.TAG, "SerialSaleReturnAdapter SearchInput error search " , e);
		} 
	}
}

