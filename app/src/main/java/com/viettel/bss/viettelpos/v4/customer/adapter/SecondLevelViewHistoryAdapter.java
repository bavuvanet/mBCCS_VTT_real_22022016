package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerCareSubResultBO;
import com.viettel.bss.viettelpos.v4.customer.object.ViewCareHistoryBO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

class SecondLevelViewHistoryAdapter extends BaseExpandableListAdapter{
	private final LayoutInflater inflater;
    private final ArrayList<ViewCareHistoryBO> lstData;
	
	public SecondLevelViewHistoryAdapter(Activity activity, ArrayList<ViewCareHistoryBO> lstData){
        this.lstData = lstData;
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		if(CommonActivity.isNullOrEmptyArray(lstData.get(groupPosition).getLstCusCareSubResult())){
			return null;
		}
		
		CustomerCareSubResultBO  obj = lstData.get(groupPosition).getLstCusCareSubResult().get(childPosition);
		if(CommonActivity.isNullOrEmpty(obj) || CommonActivity.isNullOrEmpty(obj.getValue())){
			return null;
		}
		return lstData.get(groupPosition).getLstCusCareSubResult().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		Log.d(getClass().getSimpleName(), "childPosition = " + childPosition + ", groupPosition = " + groupPosition);
		return childPosition;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.child_second_view, null);
		}
		final CustomerCareSubResultBO cuSubResultBO = this.lstData.get(groupPosition).getLstCusCareSubResult().get(childPosition);
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvValue = (TextView) convertView.findViewById(R.id.tvValue);
		
		tvName.setText(cuSubResultBO.getPropertyName());		
		tvValue.setText(cuSubResultBO.getValue());
		return convertView;
	}

	@Override
	public int getChildrenCount(final int groupPosition) {
		// TODO Auto-generated method stub
		if(CommonActivity.isNullOrEmptyArray(this.lstData.get(groupPosition).getLstCusCareSubResult())){
			return 0;
		}
		
		boolean isExist = false;
		for(CustomerCareSubResultBO cusCareSubResultBO : this.lstData.get(groupPosition).getLstCusCareSubResult()){
			if(!CommonActivity.isNullOrEmpty(cusCareSubResultBO.getValue())){
				isExist = true;
				break;
			}
		}
		
		if(!isExist){
			return 0;
		}
		
		return this.lstData.get(groupPosition).getLstCusCareSubResult().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this.lstData.get(groupPosition);
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
			convertView = inflater.inflate(R.layout.child_view, null);
		}

		// get the textView reference and set the value
		ViewCareHistoryBO viewCareHistoryBO = this.lstData.get(groupPosition);
		TextView textView = (TextView) convertView.findViewById(R.id.textViewChild);
		textView.setText(viewCareHistoryBO.getReasonName());
		
//		ExpandableListView mExpandableListView = (ExpandableListView) parent;
//		if(CommonActivity.isNullOrEmptyArray(viewCareHistoryBO.getLstCusCareSubResult())){
//			mExpandableListView.setGroupIndicator(null);
//		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
}
