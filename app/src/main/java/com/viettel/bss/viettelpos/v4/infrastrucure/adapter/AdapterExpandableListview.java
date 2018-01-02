package com.viettel.bss.viettelpos.v4.infrastrucure.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.CustomExpandableListView;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.ObjectExpandable;

public class AdapterExpandableListview extends BaseExpandableListAdapter {
	private final List<ObjectExpandable> objects;
	private final Activity activity;
	private final LayoutInflater inflater;

	public AdapterExpandableListview(Activity activity,
			List<ObjectExpandable> objects) {
		this.objects = objects;
		this.activity = activity;
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public ObjectExpandable getChild(int groupPosition, int childPosition) {
		return objects.get(groupPosition).getObjects().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ObjectExpandable object = getChild(groupPosition, childPosition);
		CustomExpandableListView subObjects = (CustomExpandableListView) convertView;
		if (convertView == null) {
			subObjects = new CustomExpandableListView(activity);
		}
		Adapter2 adapter = new Adapter2(activity, object);
		subObjects.setGroupIndicator(null);
		subObjects.setDivider(new ColorDrawable(android.R.color.transparent));
		subObjects.setDividerHeight(0);
		subObjects.setAdapter(adapter);

		return subObjects;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return objects.get(groupPosition).getObjects().size();
	}

	@Override
	public ObjectExpandable getGroup(int groupPosition) {
		return objects.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return objects.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ObjectExpandable object = getGroup(groupPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listview_element, null);
		}

		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(object.getName());
		TextView total = (TextView) convertView.findViewById(R.id.txt_total);
		TextView unit = (TextView) convertView.findViewById(R.id.txt_unit);
		total.setText(object.getTotal());
		unit.setText(object.getUnit());

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

class Adapter2 extends BaseExpandableListAdapter {
	private final ObjectExpandable object;
	private final LayoutInflater inflater;
	private final Activity activity;

	public Adapter2(Activity activity, ObjectExpandable object) {
		this.activity = activity;
		this.object = object;
		this.inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public ObjectExpandable getChild(int groupPosition, int childPosition) {
		return object.getObjects().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ObjectExpandable object = getChild(0, childPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.child, null);
		}

		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView total = (TextView) convertView.findViewById(R.id.txt_total);
		TextView unit = (TextView) convertView.findViewById(R.id.txt_unit);
		total.setText(object.getTotal());
		unit.setText(object.getUnit());
		Resources r = activity.getResources();
		float px20 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
				r.getDisplayMetrics());
		// name.setPadding(convertView.getPaddingLeft() + (int) px20 * 2, 0, 0,
		// 0);
		name.setCompoundDrawables(null, null, null, null);
		name.setText(object.getName());

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return object.getObjects().size();
	}

	@Override
	public ObjectExpandable getGroup(int groupPosition) {
		return object;
	}

	@Override
	public int getGroupCount() {
		return 1;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@SuppressLint("InflateParams")
    @Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.groupview_child, null);

		}

		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView total = (TextView) convertView.findViewById(R.id.txt_total);
		TextView unit = (TextView) convertView.findViewById(R.id.txt_unit);
		total.setText(object.getTotal());
		unit.setText(object.getUnit());

		Resources r = activity.getResources();
		float px20 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20,
				r.getDisplayMetrics());
		// name.setPadding(convertView.getPaddingLeft() + (int) px20, 0, 0, 0);
		name.setCompoundDrawables(null, null, null, null);
		name.setText(object.getName());

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