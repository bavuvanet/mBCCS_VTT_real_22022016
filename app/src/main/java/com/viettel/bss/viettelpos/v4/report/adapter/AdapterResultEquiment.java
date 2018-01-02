package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.report.object.ResultEquipments;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterResultEquiment extends BaseAdapter {
	private final Activity mActivity;
	private final ArrayList<ResultEquipments> arrResultEquipments;

	public AdapterResultEquiment(ArrayList<ResultEquipments> arrResultEquipments, Activity mActivity) {
		this.mActivity = mActivity;
		this.arrResultEquipments = arrResultEquipments;
	}

	@Override
	public int getCount() {
		return arrResultEquipments.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arrResultEquipments.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	static class ViewHolder {
		TextView tv_report_name; 
		TextView tv_quantity;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		final ResultEquipments objectReport = arrResultEquipments.get(arg0);
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_resultequirement, arg2, false);
			holder = new ViewHolder(); 
			holder.tv_report_name = (TextView) mView.findViewById(R.id.tv_report_name); 
			holder.tv_quantity = (TextView) mView.findViewById(R.id.tv_quantity);
			
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.tv_report_name.setText(objectReport.getStaffCode() + " - " + objectReport.getStaffName()); 
		if (objectReport.getObjectType().equals("1") || objectReport.getIsEquipment().equalsIgnoreCase("false")) {
			holder.tv_quantity.setVisibility(View.GONE); 
		} else {
			holder.tv_quantity.setText(mActivity.getString(R.string.tv_quantity) + ": " + objectReport.getArrSalePoint().size());
		} 
		return mView;
	}
}
