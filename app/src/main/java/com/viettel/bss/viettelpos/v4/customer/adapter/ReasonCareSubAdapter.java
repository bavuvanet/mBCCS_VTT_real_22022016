package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.ReasonCusCare;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ReasonCareSubAdapter extends BaseAdapter {
	private ArrayList<ReasonCusCare> lstData;
	private final Context mContext;
    //	private OnClickListener checkBoxListener;
	private final boolean isMultiChoice;

	public interface OnNextReason {
    }

	public interface OnCheckReason {
    }

	public ReasonCareSubAdapter(ArrayList<ReasonCusCare> lstData, boolean isMultiChoice, Context context,
			OnNextReason onNextReason, OnCheckReason onCheckReason) {
		this.lstData = lstData;
		mContext = context;
        this.isMultiChoice = isMultiChoice;
//		this.checkBoxListener = checkBoxListener;
	}

	public ArrayList<ReasonCusCare> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<ReasonCusCare> lstData) {
		this.lstData = lstData;
	}

	@Override
	public int getCount() {
		if (CommonActivity.isNullOrEmpty(lstData)) {
			return 0;
		}
		return lstData.size();
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
		return 0;
	}

	class ViewHolder {
		TextView tvName;
		View btnNext;
		CheckBox checkBox;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ReasonCusCare item = lstData.get(arg0);
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(R.layout.boc_reason_item, arg2, false);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.btnNext = convertView.findViewById(R.id.btnNext);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
			holder.checkBox.setTag(item);
			
			if (isMultiChoice) {
				holder.checkBox.setVisibility(View.VISIBLE);
				holder.btnNext.setVisibility(View.GONE);
			} else {
				if (item.isNextLevel()) {
					holder.checkBox.setVisibility(View.GONE);
					holder.btnNext.setVisibility(View.VISIBLE);
				} else {
					holder.checkBox.setVisibility(View.VISIBLE);
					holder.btnNext.setVisibility(View.GONE);
				}
			}

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvName.setText(item.getReasonName());
		holder.checkBox.setChecked(item.isChecked());
		return convertView;
	}

}
