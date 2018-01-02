package com.viettel.bss.viettelpos.v4.connecttionMobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;

import java.util.List;

public class IsdnAutoAdapter extends BaseAdapter {

	private final String logTag = IsdnAutoAdapter.class.getName();
	private final Context context;
	private List<Spin> lstData;



	public List<Spin> getLstData() {
		return lstData;
	}

	public void setLstData(List<Spin> lstData) {
		this.lstData = lstData;
	}

	public IsdnAutoAdapter(Context context,
                           List<Spin> lstData) {
		this.context = context;
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
		if (CommonActivity.isNullOrEmpty(lstData)) {
			return null;
		}
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private static class ViewHoder {
		EditText tvIsdn;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		mView = inflater.inflate(R.layout.item_isdn_auto, arg2, false);
		final EditText tvIsdn = (EditText) mView.findViewById(R.id.editisdn);
		final Spin item = lstData.get(position);
		tvIsdn.setText(item.getIsdnCalled());
		tvIsdn.setInputType(InputType.TYPE_CLASS_PHONE);
		tvIsdn.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				item.setIsdnCalled(arg0.toString());

			}
		});
		return mView;
	}
}
