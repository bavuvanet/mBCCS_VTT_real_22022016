package com.viettel.bss.viettelpos.v4.sale.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.report.object.AttributeObjectBO;
import com.viettel.bss.viettelpos.v4.report.object.AttributeObjectBOArray;

public class AdapterListServiceDetail extends BaseAdapter {

	private final Context mContext;
	private final List<AttributeObjectBOArray> lstData;

	public AdapterListServiceDetail(Context context,
			List<AttributeObjectBOArray> lstData) {

		this.mContext = context;
		this.lstData = lstData;

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

	class ViewHolders {
		public TextView textView;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup viewGroup) {
		View row = convertview;
		final ViewHolders holder;
		if (row == null) {

			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(R.layout.layout_single_textview, viewGroup,
					false);
			holder = new ViewHolders();
			holder.textView = (TextView) row.findViewById(R.id.textView1);

			row.setTag(holder);
		} else {
			holder = (ViewHolders) row.getTag();
		}

		final AttributeObjectBOArray item = lstData.get(position);
		List<AttributeObjectBO> lstAtt = item.getItem();
		StringBuilder str = new StringBuilder();
		if (!CommonActivity.isNullOrEmpty(lstAtt)) {
			for (AttributeObjectBO att : lstAtt) {
				if (CommonActivity.isNullOrEmpty(att.getColumnDescription())
						|| CommonActivity.isNullOrEmpty(att.getColumnValue())) {
					continue;
				}
				str.append(att.getColumnDescription()).append(": ")
						.append(att.getColumnValue()).append("\n");
			}
		}
		holder.textView.setText(str);
		return row;
	}
}
