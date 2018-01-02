package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.work.object.DataComboboxBeanJson;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MultiCheckAdapter extends BaseAdapter {
	Context context;
	ArrayList<DataComboboxBeanJson> lstData = new ArrayList<DataComboboxBeanJson>();
	ArrayList<DataComboboxBeanJson> lstTmp = new ArrayList<DataComboboxBeanJson>();
	private OnChangeCheckedStateMulti onChangeCheckedState;

	public interface OnChangeCheckedStateMulti {
		public void onChangeCheckedMulti(DataComboboxBeanJson saleTrans , ArrayList<DataComboboxBeanJson> lstData);
	}

	public MultiCheckAdapter(Context context, ArrayList<DataComboboxBeanJson> lstData,
			OnChangeCheckedStateMulti onChangeCheckedState) {
		this.context = context;
		if (lstData != null) {
			this.lstData.addAll(lstData);
			this.lstTmp.addAll(lstData);
		}
		this.onChangeCheckedState = onChangeCheckedState;
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
		TextView tvCode;
		CheckBox checkBox;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHoder holder;
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.multi_select_item,
					viewGroup, false);
			holder = new ViewHoder();
			holder.tvCode = (TextView) row.findViewById(R.id.tvCode);
			holder.checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			row.setTag(holder);
		} else {
			holder = (ViewHoder) row.getTag();
		}
		final DataComboboxBeanJson item = lstData.get(position);
		
		if(item.isCheckApprove()){
			holder.checkBox.setEnabled(false);
		}else{
			holder.checkBox.setEnabled(true);
		}
		
		if (item != null) {
			holder.tvCode.setText(item.getName());
			holder.checkBox.setChecked(item.isChecked());
			holder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					onChangeCheckedState.onChangeCheckedMulti(item , lstData);
				}
			});
		}
		return row;
	}

	public void filter(String charText) {
		lstData.clear();
		if (charText.isEmpty()) {
			lstData.addAll(lstTmp);
		} else {
			for (DataComboboxBeanJson item : lstTmp) {
				if (CommonActivity.convertUnicode2Nosign(item.getName().toLowerCase())
						.contains(CommonActivity.convertUnicode2Nosign(charText.toLowerCase()))) {
					lstData.add(item);
				}
			}

			// lstData.addAll(SaleCommons.getRangeSerial(lstSerial));
		}
		notifyDataSetChanged();
	}
}
