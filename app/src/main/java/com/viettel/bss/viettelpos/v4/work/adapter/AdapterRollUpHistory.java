package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.work.object.RollUpBO;

public class AdapterRollUpHistory extends BaseAdapter {
	private List<RollUpBO> lstData;
	private Context conText;

	public AdapterRollUpHistory(Context context, List<RollUpBO> lstData) {
		this.conText = context;
		this.lstData = lstData;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (lstData != null) {
			return lstData.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (lstData != null) {
			return lstData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	static class ViewHolder {
		TextView tvProgramName;
		TextView tvDateTime;
		TextView tvAddress;
		TextView tvRegisterDate;
		TextView tvNote;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) conText).getLayoutInflater();
			mView = inflater.inflate(R.layout.register_roll_up_item, parent,
					false);
			holder = new ViewHolder();
			holder.tvProgramName = (TextView) mView
					.findViewById(R.id.tvProgramName);
			holder.tvNote = (TextView) mView.findViewById(R.id.tvNote);
			holder.tvAddress = (TextView) mView.findViewById(R.id.tvAddress);
			holder.tvDateTime = (TextView) mView.findViewById(R.id.tvDateTime);
			holder.tvRegisterDate = (TextView) mView
					.findViewById(R.id.tvRegisterDate);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		RollUpBO item = lstData.get(position);
		if (item != null) {
			Date dateTime = DateTimeUtils.convertDateFromSoap(item
					.getRollUpTime());
			holder.tvDateTime.setText(DateTimeUtils.convertDateTimeToString(
					dateTime, "dd/MM/yyyy HH:mm:ss"));
			holder.tvAddress.setText(item.getAddress());
			holder.tvRegisterDate.setVisibility(View.GONE);
			holder.tvProgramName.setText(item.getCodeNameProgram());
			if (!CommonActivity.isNullOrEmpty(item.getNote())) {
				holder.tvNote.setVisibility(View.VISIBLE);
				holder.tvNote.setText(item.getNote());
			} else {
				holder.tvNote.setVisibility(View.GONE);
			}
		}
		return mView;
	}
}
