package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SerialTransAdapter extends BaseAdapter {

	private final Activity activity;
	private final ArrayList<Serial> mListSerial;

	public SerialTransAdapter(Activity mActivity, ArrayList<Serial> mListSerial) {
		this.activity = mActivity;
		this.mListSerial = mListSerial;
	}

	@Override
	public int getCount() {
		return mListSerial.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListSerial.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHoder hoder = null;
		if (mView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			mView = inflater.inflate(R.layout.sale_selected_serial_item, parent,
					false);
			hoder = new ViewHoder();
			hoder.tvSerial = (TextView) mView.findViewById(R.id.tvSerial);
			hoder.imgCancel = mView.findViewById(R.id.imgDelete);
			hoder.lineView  = mView.findViewById(R.id.lineView);
			hoder.imgCancel.setVisibility(View.GONE);
			hoder.lineView.setVisibility(View.GONE);
			
			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final Serial item = mListSerial.get(position);
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

	static class ViewHoder {
		TextView tvSerial;
		View imgCancel;
		View lineView;
	}

}
