package com.viettel.bss.viettelpos.v4.commons;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.EditText;
public class UI {
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}
	
	public static boolean validateInput(View view) {
		boolean valid = true;
		
		ViewGroup viewGroup = (ViewGroup) view;
		
		for(int i = 0; i < viewGroup.getChildCount(); ++i) {
		    View child = viewGroup.getChildAt(i);
		    if(child instanceof EditText) {
				Log.e(Constant.TAG, "UI validateInput " + valid);

		    	EditText editText = (EditText) child;
		    	String input = editText.getText().toString();
		    	
		    	if(editText.getVisibility() == View.VISIBLE) {
			    	if(input == null || input.isEmpty() || "".equalsIgnoreCase(input))  {
			    		valid = false;
			    		break;
			    	}
		    	}
		    }
		}
		return valid;
	}
	
}
