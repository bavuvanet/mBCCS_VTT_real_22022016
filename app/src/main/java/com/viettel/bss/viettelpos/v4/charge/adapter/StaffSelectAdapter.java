package com.viettel.bss.viettelpos.v4.charge.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeEmployeeOJ;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnChangeCheckedState;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class StaffSelectAdapter extends BaseAdapter {

	private final Context context;
	private final List<ChargeEmployeeOJ> lstData;
	private final OnChangeCheckedState<ChargeEmployeeOJ> onChangeCheckedState;

	
	public StaffSelectAdapter(Context context, List<ChargeEmployeeOJ> lstData, OnChangeCheckedState<ChargeEmployeeOJ> onChangeCheckedState) {
		this.context = context;
		this.lstData = lstData;
		this.onChangeCheckedState = onChangeCheckedState;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (lstData != null) {
			return lstData.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if (lstData != null && !lstData.isEmpty()) {
			return lstData.get(arg0);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	static class ViewHoder {
		TextView tv_staffCode;
		TextView tv_staffName;
		TextView tv_isdn;
		CheckBox cb_checkBox;
	}

	@Override
	public View getView(final int position, View convertview, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		View row = convertview;
		final ViewHoder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.layout_charge_employee_select,
					viewGroup, false);

			holder = new ViewHoder();
			holder.tv_staffCode = (TextView) row.findViewById(R.id.tv_staffCode);

			holder.tv_staffName = (TextView) row.findViewById(R.id.tv_staffName);
			holder.tv_isdn = (TextView) row.findViewById(R.id.tv_isdn);
			holder.cb_checkBox = (CheckBox) row.findViewById(R.id.cb_checkBox);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		
		final ChargeEmployeeOJ item = lstData.get(position);
		if (item != null) {
			holder.tv_staffCode.setText(item.getStaffCode());
			holder.tv_staffName.setText(item.getNameEmpoyee());
			holder.tv_isdn.setText(item.getIsdn());
			holder.cb_checkBox.setChecked(item.isChecked());
			if (CommonActivity.isNullOrEmpty(item.getIsdn())) {
				holder.cb_checkBox.setEnabled(false);
			} else {
				holder.cb_checkBox.setEnabled(true);
				holder.cb_checkBox.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						onChangeCheckedState.onChangeChecked(item, position);
					}
				});
			}
		}
		return row;
	}

}
