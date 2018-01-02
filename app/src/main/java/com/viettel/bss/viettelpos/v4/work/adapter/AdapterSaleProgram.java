package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.work.object.SaleProgramDTO;

public class AdapterSaleProgram extends BaseAdapter {
	private List<SaleProgramDTO> lstData;
	private Context mContext;

	public AdapterSaleProgram(Context context, List<SaleProgramDTO> array) {
		this.mContext = context;
		this.lstData = array;

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
		TextView tvName;
		TextView tvDuration;
		TextView tvState;
		TextView tvNo;
		TextView tvRegister;
		LinearLayout lnRoot;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.roll_up_program_item, parent,
					false);
			holder = new ViewHolder();
			holder.tvName = (TextView) mView.findViewById(R.id.tvProgramName);
			holder.tvDuration = (TextView) mView.findViewById(R.id.tvDuration);
			holder.tvState = (TextView) mView.findViewById(R.id.tvState);
			holder.tvNo = (TextView) mView.findViewById(R.id.tvNo);
			holder.tvRegister = (TextView) mView.findViewById(R.id.tvRegister);
			holder.lnRoot = (LinearLayout) mView.findViewById(R.id.lnRoot);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		SaleProgramDTO item = lstData.get(position);
		if (item != null) {
			holder.tvName.setText(item.getCodeNameProgram());

			holder.tvDuration.setText(item.getDuration());
			holder.tvNo.setText(position + 1 + "");
			String state = "";
			if (item.getNumOfRollUp() > 0) {
				state = mContext.getString(R.string.roll_up_num,
						item.getNumOfRollUp());
			} else {
				state = mContext.getString(R.string.roll_up_empty);
			}
			holder.tvState.setText(state);
			Date fromDate = DateTimeUtils.convertDateFromSoap(item
					.getEffectDatetime());
			Date now = new Date();
			if (fromDate != null
					&& DateTimeUtils.truncDate(fromDate).after(now)) {
				holder.tvRegister.setText(mContext
						.getString(R.string.program_not_effect));
			} else if (item.getRegisterToday() > 0) {
				holder.tvRegister.setVisibility(View.VISIBLE);
				holder.tvRegister.setText(mContext
						.getString(R.string.register_roll_up_today));
			} else {
				holder.tvRegister.setVisibility(View.GONE);
			}

			switch (item.getNumOfRollUp()) {
			case 0:
				holder.lnRoot.setBackgroundColor(mContext.getResources()
						.getColor(R.color.white));
				break;
			case 1:
				holder.lnRoot.setBackgroundColor(mContext.getResources()
						.getColor(R.color.yellow));
				break;
			case 2:
				holder.lnRoot.setBackgroundColor(mContext.getResources()
						.getColor(R.color.roll_up_blue));
				break;
			default:
				break;
			}
		}

		return mView;
	}
}
