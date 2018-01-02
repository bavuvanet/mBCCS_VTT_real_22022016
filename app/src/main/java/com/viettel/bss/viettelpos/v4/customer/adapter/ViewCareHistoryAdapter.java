package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;
import java.util.Map;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.ViewCareHistoryBO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ViewCareHistoryAdapter extends BaseExpandableListAdapter {
	private final Activity activity;
	private final Map<Long, ArrayList<ViewCareHistoryBO>> lstData;
	private final LayoutInflater inflater;

	// constructor
	public ViewCareHistoryAdapter(Activity activity, Map<Long, ArrayList<ViewCareHistoryBO>> lstData) {
		this.activity = activity;
		this.lstData = lstData;
		
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return this.lstData.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {		
		SecondLevelExpandableListView seExpandableListView = new SecondLevelExpandableListView(activity);
		seExpandableListView.setAdapter(new SecondLevelViewHistoryAdapter(activity, this.lstData.get((long) groupPosition)));
		//		seExpandableListView.setGroupIndicator(null);
		return seExpandableListView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
//		Log.d(getClass().getSimpleName(), "getChildrenCount groupPosition = " + groupPosition + ", size = " + this.lstData.get(Long.valueOf(groupPosition)).size());
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this.lstData.get(groupPosition).get(0);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.lstData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.parent_view, null);
		}
		
		final ViewCareHistoryBO viewCareHistoryBO = this.lstData.get((long) groupPosition).get(0);
	    TextView textViewGroupName = (TextView) convertView.findViewById(R.id.textViewGroupName);
	    textViewGroupName.setText(viewCareHistoryBO.getLevelName());
		
//		ExpandableListView mExpandableListView = (ExpandableListView) parent;
//	    mExpandableListView.expandGroup(groupPosition);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
