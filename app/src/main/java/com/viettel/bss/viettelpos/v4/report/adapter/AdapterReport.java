package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.report.object.ObjectReport;

public class AdapterReport extends BaseAdapter {
	private final Context mContext;
	private final ArrayList<ObjectReport> arrObjectReports;
	public AdapterReport(ArrayList<ObjectReport> arr, Context context) {
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

		TextView txtTitle, txtTotal, txtPercent;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ObjectReport objectReport = arrObjectReports.get(arg0);
		View mView = arg1;
		ViewHolder holder = null;
		String percent = objectReport.getPercent();
		String textPercent = null;
		String textTotal = null;
		textTotal = objectReport.getTotal();
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_report, arg2, false);
			holder = new ViewHolder();

			holder.txtTitle = (TextView) mView.findViewById(R.id.txt_title);
			holder.txtTotal = (TextView) mView.findViewById(R.id.txt_total);
			holder.txtPercent = (TextView) mView.findViewById(R.id.txt_percent);

			if (percent == null || percent.isEmpty()) {
				// holder.txtTotal.setWidth(-1);
				// textTotal = "1000000000000";
				// holder.txtTotal.setText("xxxxxxxxxxxx");
				// holder.txtPercent.setWidth(-2);
				// holder.txtPercent.setVisibility(View.INVISIBLE);
				// resize(holder.txtTotal, 2, 1);
				// resize(holder.txtPercent, 0, 1);
				holder.txtTotal.setLayoutParams(new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
						1f));
				holder.txtPercent
						.setLayoutParams(new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.WRAP_CONTENT, 0f));
			} else {
				textPercent = objectReport.getPercent() + "%";

			}
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		holder.txtTitle.setText(objectReport.getCriteria());

		holder.txtTotal.setText(textTotal);
		holder.txtPercent.setText(textPercent);
		return mView;
	}

	public static void resize(View v, float w, float h) {
		LayoutParams para = v.getLayoutParams();
		para.width *= w;
		para.height *= h;

	}
}
