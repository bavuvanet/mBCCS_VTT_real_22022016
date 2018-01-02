package com.viettel.bss.viettelpos.v4.report.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.report.object.ReportChanelCareDetail;
import com.viettel.bss.viettelpos.v4.report.object.ReportHeader;

import java.util.ArrayList;

public class AdapterExpandReport extends BaseExpandableListAdapter {
	private final LayoutInflater inflater;
	private final Activity activity;
	private final ArrayList<ReportHeader> lisHeaders;
	
	public AdapterExpandReport(Activity activity,
			ArrayList<ReportHeader> objects) {
		this.lisHeaders = objects;
		this.activity = activity;
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return lisHeaders.get(groupPosition).getLisChanelCareDetails().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		ReportChanelCareDetail reCareDetail = lisHeaders.get(groupPosition).getLisChanelCareDetails().get(childPosition);
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.report_layout_child, null);
		}
		 
		TextView tvmaten = (TextView) convertView.findViewById(R.id.tvmaten);
		TextView tvloaidb = (TextView) convertView.findViewById(R.id.tvloaidiemban);
		TextView tvdiachi = (TextView) convertView.findViewById(R.id.tvdiachi);
		
		if(reCareDetail != null){
			tvmaten.setText(reCareDetail.getNameChanel());
			tvloaidb.setText(reCareDetail.getChanelType());
			tvdiachi.setText(reCareDetail.getAddress());
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.lisHeaders.get(groupPosition).getLisChanelCareDetails().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return lisHeaders.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return lisHeaders.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@SuppressLint("InflateParams")
    @SuppressWarnings("unused")
	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
        // set group header
		ReportHeader reportHeader = lisHeaders.get(groupPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.report_item_chanel, null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.tvreport);
		Resources r = activity.getResources();
		float px20 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
				r.getDisplayMetrics());
		name.setCompoundDrawables(null, null, null, null);
		name.setText(Html.fromHtml(reportHeader.getName()));
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	
	

}
