package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.object.DataMapping;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateAccountInfoAdapter extends BaseAdapter {
	private List<DataMapping> lstData = new ArrayList<>();
	private final Context mContext;

	public UpdateAccountInfoAdapter(Context mContext, List<DataMapping> lstData) {
		this.mContext = mContext;
		this.lstData = lstData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final DataMapping item = lstData.get(position);
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(R.layout.item_update_account_info,
					arg2, false);

			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.edtValue = (EditText) convertView
					.findViewById(R.id.edtValue);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.position = position;
		holder.tvName.setText(item.getName());

		holder.edtValue.setVisibility(View.VISIBLE);
		holder.edtValue.setText(item.getValue());

		holder.edtValue.addTextChangedListener(new TextWatcher() {

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
			public void afterTextChanged(Editable editable) {
				// TODO Auto-generated method stub
				getLstData().get(holder.position).setValue(editable.toString());
			}
		});

		return convertView;
	}

	public List<DataMapping> getLstData() {
		return lstData;
	}

	public void setLstData(List<DataMapping> lstData) {
		this.lstData = lstData;
	}

	private class ViewHolder {
		TextView tvName;
		EditText edtValue;
		int position;
	}
}
