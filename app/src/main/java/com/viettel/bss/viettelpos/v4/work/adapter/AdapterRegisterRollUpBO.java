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
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.work.object.RegisterRollUpBO;

public class AdapterRegisterRollUpBO extends BaseAdapter {
	private List<RegisterRollUpBO> lstData;
	private Context conText;
	private OnCancelRegister onCancelRegister;

	public interface OnCancelRegister {
		void onCancelRegister(RegisterRollUpBO bo);
	}

	public AdapterRegisterRollUpBO(Context context,
			List<RegisterRollUpBO> lstData, OnCancelRegister onCancelRegister) {
		this.onCancelRegister = onCancelRegister;
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
		TextView tvDuration;
		TextView tvAddress;
		TextView tvRegisterDate;
		TextView tvNote;
		View imgCancel;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) conText).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_roll_up_history, parent,
					false);
			holder = new ViewHolder();
			holder.tvAddress = (TextView) mView.findViewById(R.id.tvAddress);
			holder.tvDuration = (TextView) mView.findViewById(R.id.tvDuration);
			holder.tvNote = (TextView) mView.findViewById(R.id.tvNote);
			holder.tvRegisterDate = (TextView) mView
					.findViewById(R.id.tvRegisterDate);
			holder.imgCancel = mView.findViewById(R.id.imgCancel);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		final RegisterRollUpBO item = lstData.get(position);
		if (item != null) {
			holder.tvNote.setText(item.getNote());
			holder.tvDuration.setText(item.getDuration());
			holder.tvAddress.setText(item.getAddress());
			Date registerTime = DateTimeUtils.convertDateFromSoap(item
					.getRegisterTime());
			holder.tvRegisterDate.setText(conText.getString(
					R.string.register_time, DateTimeUtils
							.convertDateTimeToString(registerTime,
									"dd/MM/yyyy HH:mm:ss")));
			Date fromDate = DateTimeUtils.convertDateFromSoap(item
					.getRegisterFromDate());
			fromDate = DateTimeUtils.truncDate(fromDate);
			Date now = DateTimeUtils.truncDate(new Date());
			if (!fromDate.after(now)) {
				holder.imgCancel.setVisibility(View.GONE);
			} else {
				holder.imgCancel.setVisibility(View.VISIBLE);
			}
			holder.imgCancel.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					onCancelRegister.onCancelRegister(item);

				}
			});
		}

		return mView;

	}
}
