package com.viettel.bss.viettelpos.v4.report.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.report.object.ReportHeader;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterReportListChanel extends BaseAdapter {

	private final Context mContext;
	private final ArrayList<ReportHeader> lisHeaders;

	public AdapterReportListChanel(Context mContext,
			ArrayList<ReportHeader> lisHeaders) {
		super();
		this.mContext = mContext;
		this.lisHeaders = lisHeaders;
	}

	@Override
	public int getCount() {
		return lisHeaders.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	class ViewHolder {
		TextView tvCriteriaName;
		ImageView imgRaw;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View mView = convertView;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_layout_collect_info, parent,
					false);
			holder = new ViewHolder();
			holder.tvCriteriaName = (TextView) mView
					.findViewById(R.id.tvCriteriaName);
			holder.imgRaw = (ImageView) mView.findViewById(R.id.imgRaw);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		ReportHeader mObject = lisHeaders.get(position);
		if (mObject != null) {
			holder.tvCriteriaName.setText(Html.fromHtml(mObject.getName()));
		}
		return mView;
	}
}
