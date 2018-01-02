package com.viettel.bss.viettelpos.v4.customer.adapter;

import android.content.Context;
import android.widget.ExpandableListView;

public class SecondLevelExpandableListView extends ExpandableListView{

	public SecondLevelExpandableListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations. 
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
