package com.viettel.bss.viettelpos.v4.channel.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.ArrayAssetOJ;
import com.viettel.bss.viettelpos.v4.channel.object.AssetDetailHistoryOJ;
import com.viettel.bss.viettelpos.v4.channel.object.AssetTypeOJ;

public class ToolShopExpandAdapter extends BaseExpandableListAdapter {

    private final ArrayList<ArrayAssetOJ> childtems;
	private LayoutInflater inflater;
	private final ArrayList<AssetTypeOJ> parentItems  ;

    public ToolShopExpandAdapter(ArrayList<AssetTypeOJ> parents, ArrayList<ArrayAssetOJ> childItems) {
		this.parentItems = parents;
		this.childtems = childItems;
	}

	public void setInflater(LayoutInflater inflater, Activity activity2) {
		this.inflater = inflater;
    }

	@SuppressLint("InflateParams")
    @Override
	public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ArrayList<AssetDetailHistoryOJ> child = childtems.get(groupPosition).getAssetList();

		TextView tvType = null;
		TextView txtNumber = null;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_tool_shop, null);
		}

		tvType = (TextView) convertView
				.findViewById(R.id.tv_tool_shop_type);
		txtNumber = (TextView) convertView
				.findViewById(R.id.txt_number_tool_shop);
		if(!child.get(childPosition).getNote().equals("null")){
			tvType.setText(child.get(childPosition).getNote()); 
		}
		
		txtNumber.setText(0 + child.get(childPosition).getQty() + "");
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.i("TAG", "CLICK");
			}
		});

		return convertView;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_tool_shop_detail, null);
		}
		TextView tvTypeName = null;
		TextView tvNumberTotal = null;
		tvTypeName = (TextView) convertView.findViewById(R.id.tvTypeName);
		tvNumberTotal = (TextView) convertView.findViewById(R.id.tvNumberTotal);
		tvTypeName.setText(parentItems.get(groupPosition).getName());
		tvNumberTotal.setText(parentItems.get(groupPosition).getQty() + "");
		//((CheckedTextView) convertView).setChecked(true);
		return convertView;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childtems.get(groupPosition).getAssetList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return parentItems.size();
	}

    @Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}