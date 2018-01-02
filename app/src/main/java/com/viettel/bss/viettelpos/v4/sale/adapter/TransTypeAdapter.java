package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.viettel.bss.viettelpos.v4.object.FormBean;

public class TransTypeAdapter extends BaseAdapter {
	private final List<FormBean> lstData;
	private final Context context;
	private final CheckedTextView title;

	public TransTypeAdapter(List<FormBean> lstData, Context context,
			CheckedTextView title) {
		super();
		this.title = title;
		this.lstData = lstData;
		this.context = context;
	}

	@Override
	public int getCount() {
		if (lstData == null) {
			return 0;
		}
		return lstData.size();
	}

	@Override
	public Object getItem(int arg0) {

		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHolder {
		CheckedTextView tv;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View mView = arg1;
		ViewHolder holder = null;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater.inflate(
					android.R.layout.simple_list_item_multiple_choice, arg2,
					false);
			holder = new ViewHolder();
			holder.tv = (CheckedTextView) mView
					.findViewById(android.R.id.text1);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		final FormBean item = lstData.get(arg0);
		holder.tv.setText(item.getName());
		holder.tv.setChecked(item.getChecked());
		holder.tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				item.setChecked(!item.getChecked());
				notifyDataSetChanged();
				boolean allCheck = true;
				for (FormBean tmp : lstData) {
					if (!tmp.getChecked()) {
						allCheck = false;
						break;
					}
				}
				if (allCheck) {
					title.setChecked(true);

				} else {
					title.setChecked(false);
				}
			}
		});
		return mView;
	}

}
