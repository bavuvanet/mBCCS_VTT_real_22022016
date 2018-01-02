package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetSerialAdapter extends BaseAdapter {
	private final Context mContext;
	private final ArrayList<String> arrSerial = new ArrayList<>();
    private final ArrayList<String> arraylist = new ArrayList<>();

	public GetSerialAdapter(ArrayList<String> arr, Context context) {
		if (arr != null) {
			this.arraylist.addAll(arr);
			this.arrSerial.addAll(arr);
		}
		mContext = context;
	}

	@Override
	public int getCount() {
		return arrSerial.size();
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
		TextView txtpstn;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		View mView = arg1;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connectionmobile_item_pakage,
					arg2, false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		String serial = arrSerial.get(arg0);
		if (!CommonActivity.isNullOrEmpty(serial)) {
			holder.txtpstn.setText(serial);
		}

		return mView;
	}

	public ArrayList<String> SearchInput(String chartext) {
		chartext = chartext.toLowerCase();
		arrSerial.clear();
		if (chartext.isEmpty()) {
			Log.d("size arraylist", "" + arraylist.size());
			arrSerial.addAll(arraylist);
		} else {
			for (String customerGroupBeans : arraylist) {
				if (CommonActivity
						.convertUnicode2Nosign(
								customerGroupBeans)
						.toLowerCase()
						.contains(
								CommonActivity.convertUnicode2Nosign(chartext))) {
					arrSerial.add(customerGroupBeans);
				}
			}
		}

		notifyDataSetChanged();
		return arrSerial;
	}

}
