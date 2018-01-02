package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.report.object.DataStaffResult;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterReportStaffIncome extends BaseAdapter {
	private final Context mContext;
	private final List<DataStaffResult> arrObjectReports;

	public AdapterReportStaffIncome(Context context, List<DataStaffResult> arr) {
		this.mContext = context;
		arrObjectReports = arr;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrObjectReports.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		TextView  tv_cus_cnqual_criteria, valueDay, valueMonth,tv_targets;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		DataStaffResult obj = arrObjectReports.get(position);
		Log.d(Constant.TAG, "getView position: " + position + " "+ obj);

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_report_staff_income, viewGroup, false);
			holder = new ViewHolder();

			holder.valueDay = (TextView) mView.findViewById(R.id.valueDay);
			holder.valueMonth = (TextView) mView.findViewById(R.id.valueMonth);
			holder.tv_cus_cnqual_criteria = (TextView) mView.findViewById(R.id.tv_cus_cnqual_criteria);
			holder.tv_targets = (TextView) mView.findViewById(R.id.tv_targets); 

			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		
		holder.valueDay.setText(obj.getValueDay().toString());
		holder.valueMonth.setText(obj.getValueMonth().toString()); 
		holder.tv_targets.setText(obj.getTargetValue().toString());
		return mView;
	}
}
