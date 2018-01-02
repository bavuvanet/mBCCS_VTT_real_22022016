package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.customer.object.ReasonCusCare;
import com.viettel.bss.viettelpos.v4.customer.object.ReasonCusCareProperty;

public class ReasonCareSubExpandAdapter extends BaseExpandableListAdapter{
	private final LayoutInflater inflater;
    private final ArrayList<ReasonCusCare> lstReasonCusCares;
	private final boolean isMultiChoice;
	
	public ReasonCareSubExpandAdapter(Activity activity,
			ArrayList<ReasonCusCare> lstReasonCusCares, boolean isMultiChoice) {
		this.lstReasonCusCares = lstReasonCusCares;
        this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.isMultiChoice = isMultiChoice;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if(CommonActivity.isNullOrEmptyArray(lstReasonCusCares.get(groupPosition).getLstReasonCusCareProperty())){
			return null;
		}
		
		if(this.lstReasonCusCares.get(groupPosition).isNextLevel() && !this.lstReasonCusCares.get(groupPosition).isMultiChoice()){
			if(CommonActivity.isNullOrEmpty(lstReasonCusCares.get(groupPosition).getLstReasonCusCareProperty().get(0).getValue())){
				return null;
			}
		}
		
		if(!lstReasonCusCares.get(groupPosition).isChecked()){
			return null;
		}
		
		return lstReasonCusCares.get(groupPosition).getLstReasonCusCareProperty().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.boc_item_view, null);
		}
		
		ReasonCusCareProperty reCareDetail = lstReasonCusCares.get(groupPosition).getLstReasonCusCareProperty().get(childPosition);
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvValue = (TextView) convertView.findViewById(R.id.tvValue);
		
		tvName.setText(reCareDetail.getName());
		if(lstReasonCusCares.get(groupPosition).isChecked() || lstReasonCusCares.get(groupPosition).isNextLevel()){
			tvValue.setText(reCareDetail.getNameValue());
		} else {
			tvValue.setText("");
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(CommonActivity.isNullOrEmptyArray(this.lstReasonCusCares.get(groupPosition).getLstReasonCusCareProperty())){
			return 0;
		}
		
		if(this.lstReasonCusCares.get(groupPosition).isNextLevel() && !this.lstReasonCusCares.get(groupPosition).isMultiChoice()){
			if(CommonActivity.isNullOrEmpty(lstReasonCusCares.get(groupPosition).getLstReasonCusCareProperty().get(0).getValue())){
				return 0;
			}
		}
		
		if(!this.lstReasonCusCares.get(groupPosition).isChecked()){
			return 0;
		}
		
		return this.lstReasonCusCares.get(groupPosition).getLstReasonCusCareProperty().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return lstReasonCusCares.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return lstReasonCusCares.size();
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
		final ReasonCusCare item = lstReasonCusCares.get(groupPosition);
		final ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.boc_reason_item, null);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.btnNext = convertView.findViewById(R.id.btnNext);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
			holder.checkBox.setTag(item);
			
			if (isMultiChoice) {
				holder.checkBox.setVisibility(View.VISIBLE);
				holder.btnNext.setVisibility(View.GONE);
			} else {
				if (item.isNextLevel()) {
					holder.checkBox.setVisibility(View.GONE);
					holder.btnNext.setVisibility(View.VISIBLE);
				} else {
					holder.checkBox.setVisibility(View.VISIBLE);
					holder.btnNext.setVisibility(View.GONE);
				}
			}

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(isMultiChoice){
			if(item.getOptionType() != null && item.getOptionType().equals(1)){
				holder.tvName.setText(Html.fromHtml(item.getReasonName() + " " + "<font color=\"#EE0000\">(*)</font>"));
			} else {
				holder.tvName.setText(item.getReasonName());
			}
		} else {
			holder.tvName.setText(item.getReasonName());
		}
		holder.checkBox.setChecked(item.isChecked());
		
		ExpandableListView mExpandableListView = (ExpandableListView) parent;
		mExpandableListView.expandGroup(groupPosition);
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
	
	public ArrayList<ReasonCusCare> getLstData(){
		return lstReasonCusCares;
	}
	
	class ViewHolder {
		TextView tvName;
		View btnNext;
		CheckBox checkBox;
	}

}
