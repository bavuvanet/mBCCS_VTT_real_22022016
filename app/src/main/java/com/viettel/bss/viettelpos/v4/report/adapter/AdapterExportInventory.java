package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.report.object.ReportOutletBean;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterExportInventory extends BaseAdapter {

	private final Activity mActivity;
	private final ArrayList<ReportOutletBean> listReportOutletBean;

	public AdapterExportInventory(ArrayList<ReportOutletBean> listReportOutletBean, Activity mActivity) {
		this.listReportOutletBean = listReportOutletBean;
		this.mActivity = mActivity;
	}

	@Override
	public int getCount() {
		return listReportOutletBean.size();
	}

	@Override
	public Object getItem(int position) {
		return listReportOutletBean.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	class ViewHolder {
		TextView tv_item_type;
		TextView tv_item_import;
		TextView tv_item_export;
		TextView tv_item_inventory;
		TextView tv_item_export_customers;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ReportOutletBean objectReport = listReportOutletBean.get(position);
		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			mView = inflater.inflate(R.layout.item_report_inventory, parent, false);
			holder = new ViewHolder();
			holder.tv_item_type = (TextView) mView.findViewById(R.id.tv_item_type);
			holder.tv_item_import = (TextView) mView.findViewById(R.id.tv_item_import);
			holder.tv_item_export = (TextView) mView.findViewById(R.id.tv_item_export);
			holder.tv_item_inventory = (TextView) mView.findViewById(R.id.tv_item_inventory);
			holder.tv_item_export_customers = (TextView) mView.findViewById(R.id.tv_item_export_customers); 
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.tv_item_type.setText(objectReport.getStockTypeName());
		holder.tv_item_import.setText(objectReport.getImportQuantity());
		holder.tv_item_export.setText(objectReport.getExportOtherQuantity());
		holder.tv_item_inventory.setText(objectReport.getUnsoldGood());
		holder.tv_item_export_customers.setText(objectReport.getExportCusQuantity());
		return mView;
	}

}
