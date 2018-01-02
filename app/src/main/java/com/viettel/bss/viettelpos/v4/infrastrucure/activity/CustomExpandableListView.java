package com.viettel.bss.viettelpos.v4.infrastrucure.activity;

import android.content.Context;
import android.widget.ExpandableListView;

public class CustomExpandableListView extends ExpandableListView {
	public CustomExpandableListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * Adjust height
		 */
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(500,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
